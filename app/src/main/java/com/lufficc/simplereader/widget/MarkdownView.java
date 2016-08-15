package com.lufficc.simplereader.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lufficc.simplereader.activity.SingleImageActivity;

/**
 * Created by lcc_luffy on 2016/8/7.
 */

public class MarkdownView extends WebView {
    private boolean pageFinished = false;
    private String markdown;
    private NestedScrollingChildHelper mChildHelper;

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
        if (pageFinished) {
            loadUrl("javascript:parseMarkdown(\"" + markdown.replace("\n", "\\n").replace("\"", "\\\"").replace("'", "\\'") + "\", " + gfm + ")");
        } else {
            this.markdown = markdown;
        }
    }


    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void init(Context context) {
        if (isInEditMode())
            return;
        if (Build.VERSION.SDK_INT >= 21) {
            WebView.enableSlowWholeDocumentDraw();
        }
        mChildHelper = new NestedScrollingChildHelper(this);

        getSettings().setJavaScriptEnabled(true);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        addJavascriptInterface(new JsInterface(), "android");
        setWebViewClient(new MarkdownWebViewClient());
        loadUrl("file:///android_asset/markdown.html");
    }


    /*@Override
    public void setNestedScrollingEnabled(boolean enabled) {
        mChildHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return mChildHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return mChildHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        mChildHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return mChildHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        return mChildHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return mChildHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return mChildHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return mChildHelper.dispatchNestedPreFling(velocityX, velocityY);
    }*/

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
