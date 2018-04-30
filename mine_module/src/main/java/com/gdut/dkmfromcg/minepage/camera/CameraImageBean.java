package com.gdut.dkmfromcg.minepage.camera;

import android.net.Uri;


/**
 * Created by dkmFromCG on 2018/4/3.
 * function:存储一些中间值
 */

public final class CameraImageBean {

    private Uri mPath = null;

    private static final CameraImageBean INSTANCE = new CameraImageBean();

    public static CameraImageBean getInstance(){
        return INSTANCE;
    }

    public Uri getPath() {
        return mPath;
    }

    public void setPath(Uri mPath) {
        this.mPath = mPath;
    }
}
