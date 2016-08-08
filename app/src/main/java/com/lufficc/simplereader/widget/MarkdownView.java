package com.lufficc.simplereader.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.lufficc.simplereader.activity.SingleImageActivity;

/**
 * Created by lcc_luffy on 2016/8/7.
 */

public class MarkdownView extends FrameLayout {
    private WebView webView;

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

    public void parseMarkdown(String markdown, boolean gfm) {
        webView.loadUrl("javascript:parseMarkdown(\"" + markdown.replace("\n", "\\n").replace("\"", "\\\"").replace("'", "\\'") + "\", " + gfm + ")");
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void init(Context context) {
        if (isInEditMode())
            return;
        if (Build.VERSION.SDK_INT >= 21) {
            WebView.enableSlowWholeDocumentDraw();
        }
        webView = new WebView(context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.addJavascriptInterface(new JsInterface(), "android");
        webView.setWebViewClient(new MarkdownWebViewClient());
        webView.loadUrl("file:///android_asset/markdown.html");
        addView(webView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
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
            if (onPageFinishedListener != null) {
                onPageFinishedListener.onPageFinished();
            }
        }

    }

    public void setOnPageFinishedListener(MarkdownView.onPageFinishedListener onPageFinishedListener) {
        this.onPageFinishedListener = onPageFinishedListener;
    }

    private onPageFinishedListener onPageFinishedListener;

    public interface onPageFinishedListener {
        void onPageFinished();
    }
}
