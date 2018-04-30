package com.gdut.dkmfromcg.okjbec.camera;

import android.net.Uri;

import com.gdut.dkmfromcg.commonlib.fragments.PermissionCheckerFragment;
import com.gdut.dkmfromcg.commonlib.util.file.FileUtil;

/**
 * Created by dkmFromCG on 2018/4/3.
 * function:
 */

public class CameraHelper {

    public static Uri createCropFile() {
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckerFragment fragment) {
        new CameraHelperImpl(fragment).openCameraDialog();
    }
}
