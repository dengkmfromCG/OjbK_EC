package com.gdut.dkmfromcg.okjbec.launcher.launcherScroll;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * Created by dkmFromCG on 2018/3/13.
 * function:
 */

public class ScrollLauncherHolderCreator implements CBViewHolderCreator<ScrollLauncherHolder> {

    @Override
    public ScrollLauncherHolder createHolder() {
        return new ScrollLauncherHolder();
    }
}
