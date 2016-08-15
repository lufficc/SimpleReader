package com.lufficc.simplereader.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lufficc.simplereader.R;
import com.lufficc.simplereader.activity.MarkdownActivity;
import com.lufficc.simplereader.adapter.ArticleListAdapter;
import com.lufficc.simplereader.api.Api;
import com.lufficc.simplereader.model.Article;
import com.lufficc.simplereader.model.Category;
import com.lufficc.simplereader.model.PagedResult;
import com.lufficc.simplereader.net.RetrofitManager;
import com.lufficc.simplereader.util.HttpStatus;
import com.lufficc.simplereader.widget.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Category category;

    private View noMoreDataView;

    ArticleListAdapter adapter;
    private View rootView;
    private Unbinder unbinder;
    private PagedResult<List<Article>> pagedResult;

    public static ArticleFragment newInstance(Category category) {
        ArticleFragment articleFragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putSerializable("category", category);
        articleFragment.setArguments(args);
        return articleFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = (Category) getArguments().getSerializable("category");
        }
        rootView = LayoutInflater.from(this.getContext()).inflate(R.layout.fragment_article, null);
        unbinder = ButterKnife.bind(this, rootView);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration());
        getData();
    }

    private void initAdapter() {
        adapter = new ArticleListAdapter();
        adapter.setLoadingView(getActivity().getLayoutInflater().inflate(R.layout.laod_more_view, (ViewGroup) recyclerView.getParent(), false));
        adapter.setOnLoadMoreListener(this);
        adapter.openLoadMore(10, true);
        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                MarkdownActivity.showMarkdown(view.getContext(), adapter.getData().get(i).getId());
            }
        });
    }

    private void getData() {
        Api api = RetrofitManager.api();
        Call<PagedResult<List<Article>>> call = api.getArticlesByCategory(category.getId(), pagedResult == null ? 0 : pagedResult.getNumber());
        call.enqueue(new Callback<PagedResult<List<Article>>>() {
            @Override
            public void onResponse(Call<PagedResult<List<Article>>> call, Response<PagedResult<List<Article>>> response) {
                if (response.isSuccessful()) {
                    pagedResult = response.body();
                    if (pagedResult.getCode() == HttpStatus.OK) {
                        if (pagedResult.isFirst()) {
                            if (pagedResult.getContent().isEmpty()) {
                                if (noMoreDataView == null)
                                    noMoreDataView = getActivity()
                                            .getLayoutInflater()
                                            .inflate(R.layout.no_more_view, (ViewGroup) recyclerView.getParent(), false);
                                adapter.setEmptyView(true, noMoreDataView);
                            } else {
                                adapter.setNewData(pagedResult.getContent());
                            }
                        } else {
                            adapter.notifyDataChangedAfterLoadMore(pagedResult.getContent(), !pagedResult.isLast());
                            if (pagedResult.isLast()) {
                                if (noMoreDataView == null)
                                    noMoreDataView = getActivity()
                                            .getLayoutInflater()
                                            .inflate(R.layout.no_more_view, (ViewGroup) recyclerView.getParent(), false);
                                adapter.addFooterView(noMoreDataView);
                            }
                        }
                    }
                } else {
                    Log.i("main", "error," + response.code());
                }
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<PagedResult<List<Article>>> call, Throwable t) {
                Log.i("main", t.toString());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        if (pagedResult != null)
            pagedResult.setNumber(0);
        adapter.removeAllFooterView();
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (pagedResult == null)
            return;
        pagedResult.setNumber(pagedResult.getNumber() + 1);
        getData();
    }
}
