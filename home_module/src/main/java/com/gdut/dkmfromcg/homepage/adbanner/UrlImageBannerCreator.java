package com.gdut.dkmfromcg.homepage.adbanner;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.gdut.dkmfromcg.homepage.R;


import java.util.ArrayList;

/**
 * Created by dkmFromCG on 2018/3/20.
 * function:
 */

public class UrlImageBannerCreator {

    public static void setDefault(ConvenientBanner<String> convenientBanner,
                                  ArrayList<String> bannersImgUrls,
                                  OnItemClickListener clickListener) {

        convenientBanner
                .setPages(new UrlImageHolderCreator(), bannersImgUrls)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(clickListener)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);

    }
}
