package com.gdut.dkmfromcg.ojbk_ec.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gdut.dkmfromcg.ojbk_ec.fragments.MainFragment;
import com.gdut.dkmfromcg.commonlib.fragments.BaseFragment;
import com.gdut.dkmfromcg.commonlib.router.AppModule;
import com.gdut.dkmfromcg.commonlib.router.RouterPath;

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
