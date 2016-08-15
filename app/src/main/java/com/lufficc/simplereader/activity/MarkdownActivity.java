package com.lufficc.simplereader.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.lufficc.simplereader.R;
import com.lufficc.simplereader.base.BaseActivity;
import com.lufficc.simplereader.model.Markdown;
import com.lufficc.simplereader.model.Result;
import com.lufficc.simplereader.net.RetrofitManager;
import com.lufficc.simplereader.util.HttpStatus;
import com.lufficc.simplereader.widget.MarkdownView;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarkdownActivity extends BaseActivity {
    @BindView(R.id.markdownView)
    MarkdownView markdownView;
    private long article_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        article_id = getIntent().getLongExtra("article_id", 0);
        /*markdownView.parseMarkdown(markdown, true);*/
    }

    private void getMarkdown() {
        RetrofitManager
                .api()
                .getMarkdown(article_id)
                .enqueue(new Callback<Result<Markdown>>() {
                    @Override
                    public void onResponse(Call<Result<Markdown>> call, Response<Result<Markdown>> response) {
                        if (response.isSuccessful() && response.body().getCode() == HttpStatus.OK)
                            markdownView.parseMarkdown(response.body().getContent().getMarkdown(), true);
                        else {
                            Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result<Markdown>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_markdown;
    }

    public static void showMarkdown(Context context, long article_id) {
        Intent intent = new Intent(context, MarkdownActivity.class);
        intent.putExtra("article_id", article_id);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
