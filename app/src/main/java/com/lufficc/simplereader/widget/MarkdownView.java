package com.lufficc.simplereader.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.lufficc.simplereader.activity.SingleImageActivity;

/**
 * Created by lcc_luffy on 2016/8/7.
 */

public class MarkdownView extends WebView {
    private boolean pageFinished = false;


    public MarkdownView(Context context) {
        super(context);
        init(context);
    }

    public MarkdownView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MarkdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private String markdown;

    public void parseMarkdown(String markdown, boolean gfm) {
        if (pageFinished) {
            loadUrl("javascript:parseMarkdown(\"" + markdown.replace("\n", "\\n").replace("\"", "\\\"").replace("'", "\\'") + "\", " + gfm + ")");
        } else {
            this.markdown = markdown;
        }
    }

    private float lastX, lastY;


    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void init(Context context) {
        if (isInEditMode())
            return;
        if (Build.VERSION.SDK_INT >= 21) {
            WebView.enableSlowWholeDocumentDraw();
        }
        getSettings().setJavaScriptEnabled(true);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        addJavascriptInterface(new JsInterface(), "android");
        setWebViewClient(new MarkdownWebViewClient());
        loadUrl("file:///android_asset/markdown.html");
    }

    private class JsInterface {
        @JavascriptInterface
        public void onImageClickListener(String src) {
            SingleImageActivity.showImage(getContext(), src);
        }
    }

    private final class MarkdownWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pageFinished = true;
            if (markdown != null) {
                parseMarkdown(markdown, true);
                markdown = null;
            }
        }
    }
}
