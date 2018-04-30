package com.gdut.dkmfromcg.launcherpage.app;

import android.app.Application;
import android.content.Context;

import com.gdut.dkmfromcg.commonlib.app.IAppLife;
import com.gdut.dkmfromcg.commonlib.app.IModuleConfig;
import com.gdut.dkmfromcg.commonlib.router.AppModule;
import com.gdut.dkmfromcg.commonlib.router.RegisterModule;
import com.gdut.dkmfromcg.commonlib.router.RouterPath;
import com.gdut.dkmfromcg.commonlib.util.router.RouterUtil;

import java.util.List;

/**
 * Created by dkmFromCG on 2018/4/25.
 * function:
 */

public class LauncherApp implements IAppLife, IModuleConfig {

    private static final String TAG = "LauncherApp";


    private static RegisterModule registerModule = null;
    private static AppModule appModule = null;

    public static RegisterModule getRegisterModule() {
        return registerModule;
    }

    public static AppModule getAppModule() {
        return appModule;
    }

    @Override
    public void attachBaseContext(Context tx) {

    }

    @Override
    public void onCreate(Application application) {
        /**
         * 为该Module设置 theme
         * application.setTheme();
         */

        //做一些该Module内的数据初始化,比如
        registerModule = RouterUtil.navigation(RouterPath.REGISTER);
        appModule = RouterUtil.navigation(RouterPath.APP);
    }

    @Override
    public void onTerminate(Application application) {
        //做 该Module内的数据的清除,防止内存溢出
        registerModule = null;
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
