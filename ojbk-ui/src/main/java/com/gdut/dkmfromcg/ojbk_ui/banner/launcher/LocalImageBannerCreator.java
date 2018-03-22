package com.gdut.dkmfromcg.ojbk_ui.banner.launcher;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.gdut.dkmfromcg.ojbk_ui.R;

import java.util.ArrayList;

/**
 * Created by dkmFromCG on 2018/3/20.
 * function:
 */

public class LocalImageBannerCreator {
    public static void setDefault(ConvenientBanner<Integer> convenientBanner,
                                  ArrayList<Integer> bannersImgIds,
                                  OnItemClickListener clickListener) {
        convenientBanner
                .setPages(new LocalImageHolderCreator(), bannersImgIds)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(clickListener)
                .setCanLoop(false);
    }
}
