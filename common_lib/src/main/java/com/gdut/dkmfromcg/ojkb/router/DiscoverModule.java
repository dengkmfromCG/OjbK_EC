package com.gdut.dkmfromcg.ojkb.router;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.gdut.dkmfromcg.ojkb.fragments.BaseFragment;

/**
 * Created by dkmFromCG on 2018/4/29.
 * function:
 */

public interface DiscoverModule extends IProvider {

    BaseFragment getDisCoverFragment();
}
