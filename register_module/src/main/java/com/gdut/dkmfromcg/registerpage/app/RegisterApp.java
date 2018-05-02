package com.gdut.dkmfromcg.registerpage.app;

import android.app.Application;
import android.content.Context;

import com.gdut.dkmfromcg.commonlib.app.IAppLife;
import com.gdut.dkmfromcg.commonlib.app.IModuleConfig;
import com.gdut.dkmfromcg.commonlib.router.IAppModule;
import com.gdut.dkmfromcg.commonlib.router.RouterPath;
import com.gdut.dkmfromcg.commonlib.util.router.RouterUtil;

import java.util.List;

/**
 * Created by dkmFromCG on 2018/4/29.
 * function:
 */

public class RegisterApp implements IAppLife, IModuleConfig {


    private static IAppModule appModule;

    public static IAppModule getAppModule() {
        return appModule;
    }

    @Override
    public void attachBaseContext(Context tx) {

    }

    @Override
    public void onCreate(Application application) {
        appModule = RouterUtil.navigation(RouterPath.APP);
    }

    @Override
    public void onTerminate(Application application) {
        appModule = null;
    }

    @Override
    public void injectAppLifecycle(Context context, List<IAppLife> appLifeList) {
        appLifeList.add(this);
    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycleCallbacks) {

    }

}
