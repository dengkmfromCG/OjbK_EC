package com.gdut.dkmfromcg.minepage.camera;

import com.yalantis.ucrop.UCrop;


/**
 * Created by dkmFromCG on 2018/4/3.
 * function: 请求码存储
 */

public class RequestCodes {
    public static final int TAKE_PHOTO = 4;
    public static final int PICK_PHOTO = 5;
    public static final int CROP_PHOTO = UCrop.REQUEST_CROP;
    public static final int CROP_ERROR = UCrop.RESULT_ERROR;
    public static final int SCAN = 7;
}
