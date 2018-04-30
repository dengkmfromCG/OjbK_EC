package com.gdut.dkmfromcg.minepage.app;

import android.app.Application;
import android.content.Context;

import com.gdut.dkmfromcg.commonlib.app.IAppLife;
import com.gdut.dkmfromcg.commonlib.app.IModuleConfig;

import java.util.List;

/**
 * Created by dkmFromCG on 2018/4/30.
 * function:
 */

public class MineApp implements IAppLife,IModuleConfig {
    @Override
    public void attachBaseContext(Context tx) {

    }

    @Override
    public void onCreate(Application application) {

    }

    @Override
    public void onTerminate(Application application) {

    }

    @Override
    public void injectAppLifecycle(Context context, List<IAppLife> appLifeList) {

    }

    @Override
    public void injectActivityLifecycle(Context context, List<Application.ActivityLifecycleCallbacks> lifecycleCallbacks) {

    }
}
