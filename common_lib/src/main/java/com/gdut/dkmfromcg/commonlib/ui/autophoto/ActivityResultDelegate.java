package com.gdut.dkmfromcg.commonlib.ui.autophoto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.gdut.dkmfromcg.commonlib.app.GlideApp;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.ImageEngine;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dkmFromCG on 2018/5/2.
 * function:
 */

public class ActivityResultDelegate {

    private static final String TAG = "ActivityResultDelegate";

    private static final int REQUEST_CODE_CHOOSE = 23;//定义请求码常量

    private final View.OnClickListener onLocalPicClickListener;
    private final AutoPhotoView autoPhotoView;


    public ActivityResultDelegate(final @NonNull Activity activity, @NonNull final AutoPhotoView autoPhotoView) {
        this.autoPhotoView = autoPhotoView;
        this.onLocalPicClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matisse.from(activity)
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .maxSelectable(autoPhotoView.getMaxPicNum())
                        .thumbnailScale(0.85f)
                        .imageEngine(new MyGlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
                autoPhotoView.getAddDialog().dismiss();
            }
        };
        autoPhotoView.setOnLocalPicClickListener(onLocalPicClickListener);
    }

    public ActivityResultDelegate(final @NonNull Fragment fragment, @NonNull final AutoPhotoView autoPhotoView) {
        this.autoPhotoView = autoPhotoView;
        this.onLocalPicClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matisse.from(fragment)
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .maxSelectable(autoPhotoView.getMaxPicNum())
                        .thumbnailScale(0.85f)
                        .imageEngine(new MyGlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
                autoPhotoView.getAddDialog().dismiss();
            }
        };
        autoPhotoView.setOnLocalPicClickListener(onLocalPicClickListener);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> uris = Matisse.obtainResult(data);
            this.autoPhotoView.setPhotoUriList(uris);
        }
    }

    private class MyGlideEngine implements ImageEngine {

        @Override
        public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
            GlideApp.with(context)
                    .asBitmap()  // some .jpeg files are actually gif
                    .load(uri)
                    .override(resize, resize)
                    .centerCrop()
                    .into(imageView);
        }

        @Override
        public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
            GlideApp.with(context)
                    .asBitmap()
                    .load(uri)
                    .placeholder(placeholder)
                    .override(resize, resize)
                    .centerCrop()
                    .into(imageView);
        }

        @Override
        public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
            GlideApp.with(context)
                    .asBitmap()
                    .load(uri)
                    .override(resizeX, resizeY)
                    .priority(Priority.HIGH)
                    .into(imageView);
        }

        @Override
        public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
            GlideApp.with(context)
                    .asBitmap()
                    .load(uri)
                    .override(resizeX, resizeY)
                    .priority(Priority.HIGH)
                    .into(imageView);
        }

        @Override
        public boolean supportAnimatedGif() {
            return true;
        }
    }

}
