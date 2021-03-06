package com.gdut.dkmfromcg.discoverpage.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gdut.dkmfromcg.discoverpage.DiscoverFragment;
import com.gdut.dkmfromcg.commonlib.fragments.BaseFragment;
import com.gdut.dkmfromcg.commonlib.router.IDiscoverModule;
import com.gdut.dkmfromcg.commonlib.router.RouterPath;

/**
 * Created by dkmFromCG on 2018/4/29.
 * function:
 */
@Route(path = RouterPath.DISCOVER_MODULE)
public class DiscoverModuleImpl implements IDiscoverModule {
    @Override
    public void init(Context context) {

    }

    @Override
    public BaseFragment getDisCoverFragment() {
        return new DiscoverFragment();
    }
}
