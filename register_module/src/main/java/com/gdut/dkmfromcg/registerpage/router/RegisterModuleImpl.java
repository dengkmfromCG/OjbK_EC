package com.gdut.dkmfromcg.registerpage.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gdut.dkmfromcg.commonlib.fragments.BaseFragment;
import com.gdut.dkmfromcg.commonlib.router.IRegisterModule;
import com.gdut.dkmfromcg.commonlib.router.RouterPath;
import com.gdut.dkmfromcg.registerpage.LogInFragment;

/**
 * Created by dkmFromCG on 2018/4/29.
 * function:
 */

@Route(path = RouterPath.REGISTER)
public class RegisterModuleImpl implements IRegisterModule {


    @Override
    public void init(Context context) {

    }


    @Override
    public BaseFragment getLogInFragment() {
        return new LogInFragment();
    }
}
