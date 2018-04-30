package com.gdut.dkmfromcg.launcherpage.banner;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * Created by dkmFromCG on 2018/3/13.
 * function:
 */

public class LocalImageHolderCreator implements CBViewHolderCreator<LocalImageHolder> {


    @Override
    public LocalImageHolder createHolder() {
        return new LocalImageHolder();
    }
}
