package com.lufficc.simplereader;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.lufficc.simplereader.adapter.FragmentAdapter;
import com.lufficc.simplereader.base.BaseActivity;
import com.lufficc.simplereader.model.Category;
import com.lufficc.simplereader.model.Result;
import com.lufficc.simplereader.net.RetrofitManager;
import com.lufficc.simplereader.util.HttpStatus;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    FragmentAdapter fragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Call<Result<List<Category>>> call = RetrofitManager.api().getCategories();
        call.enqueue(new Callback<Result<List<Category>>>() {
            @Override
            public void onResponse(Call<Result<List<Category>>> call, Response<Result<List<Category>>> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCode() == HttpStatus.OK) {
                        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), response.body().getContent());
                        viewPager.setAdapter(fragmentAdapter);
                        tabLayout.setupWithViewPager(viewPager);
                    }
                }
            }

            @Override
            public void onFailure(Call<Result<List<Category>>> call, Throwable t) {
                /*Snackbar.make(toolbar, t.toString(), Snackbar.LENGTH_SHORT).show();*/
            }
        });

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

}
