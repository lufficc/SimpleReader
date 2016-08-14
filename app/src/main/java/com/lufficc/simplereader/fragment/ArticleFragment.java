package com.lufficc.simplereader.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lufficc.simplereader.R;
import com.lufficc.simplereader.adapter.SimpleAdapter;
import com.lufficc.simplereader.api.Api;
import com.lufficc.simplereader.model.Article;
import com.lufficc.simplereader.model.Category;
import com.lufficc.simplereader.model.PagedResult;
import com.lufficc.simplereader.net.RetrofitManager;
import com.lufficc.simplereader.util.HttpStatus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private Category category;

    SimpleAdapter simpleAdapter;
    private View rootView;
    private Unbinder unbinder;

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
        recyclerView.setAdapter(simpleAdapter = new SimpleAdapter());
        getData();
    }

    private void getData() {
        Api api = RetrofitManager.api();
        Call<PagedResult<List<Article>>> call = api.getArticlesByCategory(category.getId());
        call.enqueue(new Callback<PagedResult<List<Article>>>() {
            @Override
            public void onResponse(Call<PagedResult<List<Article>>> call, Response<PagedResult<List<Article>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == HttpStatus.OK) {
                        simpleAdapter.setData(response.body().getContent());
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
        getData();
    }
}
