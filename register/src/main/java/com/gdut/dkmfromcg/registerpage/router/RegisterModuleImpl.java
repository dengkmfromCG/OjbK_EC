package com.gdut.dkmfromcg.registerpage.router;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gdut.dkmfromcg.ojkb.fragments.BaseFragment;
import com.gdut.dkmfromcg.ojkb.router.RegisterModule;
import com.gdut.dkmfromcg.ojkb.router.RouterPath;
import com.gdut.dkmfromcg.registerpage.LogInFragment;

/**
 * Created by dkmFromCG on 2018/4/29.
 * function:
 */

@Route(path = RouterPath.REGISTER)
public class RegisterModuleImpl implements RegisterModule {


    @Override
    public void init(Context context) {

    }


    @Override
    public BaseFragment getLogInFragment() {
        return new LogInFragment();
    }
}
