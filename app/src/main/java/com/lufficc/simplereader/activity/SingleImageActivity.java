package com.lufficc.simplereader.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lufficc.simplereader.R;
import com.lufficc.simplereader.base.BaseActivity;
import com.lufficc.simplereader.widget.TouchImageView;

import butterknife.BindView;

public class SingleImageActivity extends BaseActivity {
    @BindView(R.id.touchImageView)
    TouchImageView touchImageView;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url = getIntent().getStringExtra("url");
        Glide.with(this)
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        touchImageView.setImageBitmap(resource);
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_single_image;
    }

    public static void showImage(Context context, String url) {
        Intent intent = new Intent(context, SingleImageActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

}
