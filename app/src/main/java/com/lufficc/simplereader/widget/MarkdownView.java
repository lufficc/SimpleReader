package com.lufficc.simplereader.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lufficc.simplereader.activity.SingleImageActivity;

/**
 * Created by lcc_luffy on 2016/8/7.
 */

public class MarkdownView extends WebView implements NestedScrollingChild {
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

    // NestedScrollingChild

    @Override
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
    }


    private int mLastMotionY;

    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];

    private int mNestedYOffset;
/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = false;

        MotionEvent trackedEvent = MotionEvent.obtain(event);

        final int action = MotionEventCompat.getActionMasked(event);

        if (action == MotionEvent.ACTION_DOWN) {
            mNestedYOffset = 0;
        }

        int y = (int) event.getY();

        event.offsetLocation(0, mNestedYOffset);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = y;
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                result = super.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = mLastMotionY - y;

                if (dispatchNestedPreScroll(0, deltaY, mScrollConsumed, mScrollOffset)) {
                    deltaY -= mScrollConsumed[1];
                    trackedEvent.offsetLocation(0, mScrollOffset[1]);
                    mNestedYOffset += mScrollOffset[1];
                }

                int oldY = getScrollY();
                mLastMotionY = y - mScrollOffset[1];
                if (deltaY < 0) {
                    int newScrollY = Math.max(0, oldY + deltaY);
                    deltaY -= newScrollY - oldY;
                    if (dispatchNestedScroll(0, newScrollY - deltaY, 0, deltaY, mScrollOffset)) {
                        mLastMotionY -= mScrollOffset[1];
                        trackedEvent.offsetLocation(0, mScrollOffset[1]);
                        mNestedYOffset += mScrollOffset[1];
                    }
                }

                trackedEvent.recycle();
                result = super.onTouchEvent(trackedEvent);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                stopNestedScroll();
                result = super.onTouchEvent(event);
                break;
        }
        Log.i("main",y+"");
        return result;
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
