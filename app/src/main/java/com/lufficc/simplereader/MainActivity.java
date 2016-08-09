package com.lufficc.simplereader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.lufficc.simplereader.adapter.SimpleAdapter;
import com.lufficc.simplereader.api.Api;
import com.lufficc.simplereader.base.BaseActivity;
import com.lufficc.simplereader.model.Article;
import com.lufficc.simplereader.model.PagedResult;
import com.lufficc.simplereader.net.RetrofitManager;
import com.lufficc.simplereader.util.HttpStatus;
import com.lufficc.simplereader.widget.MarkdownView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    RecyclerView recyclerView;
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(simpleAdapter = new SimpleAdapter());
        Api api = RetrofitManager.get().create(Api.class);
        Call<PagedResult<List<Article>>> call = api.getArticles();
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
            }

            @Override
            public void onFailure(Call<PagedResult<List<Article>>> call, Throwable t) {
                Log.i("main", t.toString());
            }
        });
    }
}
