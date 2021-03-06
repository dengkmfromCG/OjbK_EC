package com.gdut.dkmfromcg.homepage.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gdut.dkmfromcg.homepage.HomeFragment;
import com.gdut.dkmfromcg.commonlib.fragments.BaseFragment;
import com.gdut.dkmfromcg.commonlib.router.IHomeModule;
import com.gdut.dkmfromcg.commonlib.router.RouterPath;

/**
 * Created by dkmFromCG on 2018/4/29.
 * function:
 */
@Route(path = RouterPath.HOME_MODULE)
public class HomeModuleImpl implements IHomeModule {
    @Override
    public BaseFragment getHomeFragment() {
        return new HomeFragment();
    }

    @Override
    public void init(Context context) {

    }
}
