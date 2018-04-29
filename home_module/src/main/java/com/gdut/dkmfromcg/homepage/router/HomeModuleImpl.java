package com.gdut.dkmfromcg.homepage.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gdut.dkmfromcg.homepage.HomeFragment;
import com.gdut.dkmfromcg.ojkb.fragments.BaseFragment;
import com.gdut.dkmfromcg.ojkb.router.HomeModule;
import com.gdut.dkmfromcg.ojkb.router.RouterPath;

/**
 * Created by dkmFromCG on 2018/4/29.
 * function:
 */
@Route(path = RouterPath.HOME_MODULE)
public class HomeModuleImpl implements HomeModule {
    @Override
    public BaseFragment getHomeFragment() {
        return new HomeFragment();
    }

    @Override
    public void init(Context context) {

    }
}
