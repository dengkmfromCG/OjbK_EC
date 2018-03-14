package com.gdut.dkmfromcg.ojkb.ui.loader;

import android.content.Context;

import com.gdut.dkmfromcg.ojbk_ui.progressing.SpinKitView;
import com.gdut.dkmfromcg.ojbk_ui.progressing.SpriteFactory;
import com.gdut.dkmfromcg.ojbk_ui.progressing.Style;
import com.gdut.dkmfromcg.ojbk_ui.progressing.sprite.Sprite;


/**
 * Created by dkmFromCG on 2018/3/13.
 * function:
 */

public final class ProgressingCreator {

    public static SpinKitView create(Style style, Context context){
        SpinKitView spinKitView=new SpinKitView(context);
        Sprite drawable= SpriteFactory.create(style);
        spinKitView.setIndeterminateDrawable(drawable);
        return spinKitView;
    }

}
