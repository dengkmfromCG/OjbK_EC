package com.gdut.dkmfromcg.homepage.adbanner;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * Created by dkmFromCG on 2018/3/20.
 * function:
 */

public class UrlImageHolderCreator implements CBViewHolderCreator<UrlImageHolder> {
    @Override
    public UrlImageHolder createHolder() {
        return new UrlImageHolder();
    }
}
