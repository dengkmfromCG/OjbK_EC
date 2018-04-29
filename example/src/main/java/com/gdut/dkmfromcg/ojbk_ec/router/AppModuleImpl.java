package com.gdut.dkmfromcg.ojbk_ec.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gdut.dkmfromcg.ojbk_ec.fragments.MainFragment;
import com.gdut.dkmfromcg.ojkb.fragments.BaseFragment;
import com.gdut.dkmfromcg.ojkb.router.AppModule;
import com.gdut.dkmfromcg.ojkb.router.RouterPath;

/**
 * Created by dkmFromCG on 2018/4/29.
 * function:
 */

@Route(path = RouterPath.APP)
public class AppModuleImpl implements AppModule {

    @Override
    public BaseFragment getMainFragment() {
        return new MainFragment();
    }

    @Override
    public void init(Context context) {

    }
}
