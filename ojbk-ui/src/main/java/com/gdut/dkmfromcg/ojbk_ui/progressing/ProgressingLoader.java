package com.gdut.dkmfromcg.ojbk_ui.progressing;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.gdut.dkmfromcg.ojbk_ui.R;
import com.gdut.dkmfromcg.ojkb.util.dimen.DimenUtil;

import java.util.ArrayList;

/**
 * Created by dkmFromCG on 2018/4/1.
 * function:
 */

public class ProgressingLoader {

    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>(); //用于快速释放所有的Dialog

    private static final Style DEFAULT_STYLE = Style.ROTATING_PLANE;

    public static void showLoading(Context context) {
        showLoading(context, DEFAULT_STYLE);
    }

    public static void showLoading(Context context, Style type) {

        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.dialog);
        final SpinKitView spinKitView = ProgressingCreator.create(type, context);
        dialog.setContentView(spinKitView);

        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();

        final Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null) {
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.height = lp.height + deviceHeight / LOADER_OFFSET_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void stopLoading() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
    }

}
