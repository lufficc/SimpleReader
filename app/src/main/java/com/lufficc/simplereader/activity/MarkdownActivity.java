package com.lufficc.simplereader.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lufficc.simplereader.R;
import com.lufficc.simplereader.base.BaseActivity;
import com.lufficc.simplereader.widget.MarkdownView;

import butterknife.BindView;

public class MarkdownActivity extends BaseActivity {
    @BindView(R.id.markdownView)
    MarkdownView markdownView;
    private String markdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        markdown = getIntent().getStringExtra("markdown");
        markdownView.parseMarkdown(markdown, true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_markdown;
    }

    public static void showMarkdown(Context context, String md) {
        Intent intent = new Intent(context, MarkdownActivity.class);
        intent.putExtra("markdown", md);
        context.startActivity(intent);
    }

}
