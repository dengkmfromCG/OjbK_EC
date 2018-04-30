package com.gdut.dkmfromcg.commonlib.ui.loading;

import android.content.Context;

import com.gdut.dkmfromcg.commonlib.ui.loading.sprite.Sprite;


/**
 * Created by dkmFromCG on 2018/4/1.
 * function:
 */

public final class LoadingCreator {

    public static SpinKitView create(Style style, Context context){
        SpinKitView spinKitView=new SpinKitView(context);
        Sprite drawable= SpriteFactory.create(style);
        spinKitView.setIndeterminateDrawable(drawable);
        return spinKitView;
    }

}
