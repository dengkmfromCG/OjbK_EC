package com.gdut.dkmfromcg.minepage.camera;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.gdut.dkmfromcg.commonlib.fragments.PermissionCheckerFragment;
import com.gdut.dkmfromcg.commonlib.util.file.FileUtil;
import com.gdut.dkmfromcg.minepage.R;


import java.io.File;

/**
 * Created by dkmFromCG on 2018/4/3.
 * function:
 */

public class CameraHelperImpl implements View.OnClickListener{

    private final AlertDialog DIALOG;
    private final PermissionCheckerFragment FRAGMENT;

    public CameraHelperImpl(PermissionCheckerFragment fragment) {
        this.FRAGMENT = fragment;
        this.DIALOG = new AlertDialog.Builder(fragment.getContext()).create();
    }


    final void openCameraDialog() {
        DIALOG.show();
        final Window window = DIALOG.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_camera_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            params.dimAmount = 0.5f;
            window.setAttributes(params);

            window.findViewById(R.id.photodialog_tv_cancel).setOnClickListener(this);
            window.findViewById(R.id.photodialog_tv_take).setOnClickListener(this);
            window.findViewById(R.id.photodialog_tv_native).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.photodialog_tv_cancel) {
            DIALOG.cancel();
        } else if (id == R.id.photodialog_tv_take) {
            takePhoto();
            DIALOG.cancel();
        } else if (id == R.id.photodialog_tv_native) {
            pickPhoto();
            DIALOG.cancel();
        }
    }

    private String getPhotoName() {
        return FileUtil.getFileNameByTime("IMG", "jpg");
    }

    private File getFileByPath(String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    private void pickPhoto() {

        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        FRAGMENT.startActivityForResult
                (Intent.createChooser(intent, "选择获取图片的方式"), RequestCodes.PICK_PHOTO);
    }

    private void takePhoto() {
        final String currentPhotoName = getPhotoName();
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final File tempFile = new File(FileUtil.CAMERA_PHOTO_DIR, currentPhotoName);

        //兼容7.0及以上的写法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            final ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getPath());
            final Uri uri = FRAGMENT.getContext().getContentResolver().
                    insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            //需要讲Uri路径转化为实际路径
            final File realFile = getFileByPath(FileUtil.getRealFilePath(FRAGMENT.getContext(), uri));
            final Uri realUri = Uri.fromFile(realFile);
            CameraImageBean.getInstance().setPath(realUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        } else {
            final Uri fileUri = Uri.fromFile(tempFile);
            CameraImageBean.getInstance().setPath(fileUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        }
        FRAGMENT.startActivityForResult(intent, RequestCodes.TAKE_PHOTO);
    }
}
