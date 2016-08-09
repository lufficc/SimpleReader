package com.lufficc.simplereader.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lufficc.simplereader.R;
import com.lufficc.simplereader.widget.MarkdownView;

public class MarkdownActivity extends AppCompatActivity {
    MarkdownView markdownView;
    private String markdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markdown);
        markdownView = (MarkdownView) findViewById(R.id.markdownView);
        markdown = getIntent().getStringExtra("markdown");
        markdownView.parseMarkdown(markdown, true);
    }

    public static void showMarkdown(Context context, String md) {
        Intent i = new Intent(context, MarkdownActivity.class);
        i.putExtra("markdown", md);
        context.startActivity(i);
    }

}
