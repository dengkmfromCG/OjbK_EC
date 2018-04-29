package com.gdut.dkmfromcg.homepage.adbanner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by dkmFromCG on 2018/3/20.
 * function:
 */

public class UrlImageHolder implements Holder<String>{

    private AppCompatImageView mImageView = null;
    //图片加载策略
    private static final RequestOptions BANNER_OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .centerCrop();

    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .apply(BANNER_OPTIONS)
                .into(mImageView);
    }

}
