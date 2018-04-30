package com.gdut.dkmfromcg.commonlib.app;

import android.app.Application;
import android.content.Context;

import com.gdut.dkmfromcg.commonlib.util.manifest.ManifestParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dkmFromCG on 2018/4/25.
 * function:
 */

public class ApplicationDelegate implements IAppLife {

    private final List<IAppLife> appLives;
    private final List<Application.ActivityLifecycleCallbacks> lifecycleCallbacks;

    ApplicationDelegate() {
        appLives = new ArrayList<>();
        lifecycleCallbacks = new ArrayList<>();
    }

    @Override
    public void attachBaseContext(Context base) {
        ManifestParser manifestParser = new ManifestParser(base);
        final List<IModuleConfig> list = manifestParser.parse();
        if (list != null && list.size() > 0) {
            for (IModuleConfig configModule :
                    list) {
                configModule.injectAppLifecycle(base, appLives);
                configModule.injectActivityLifecycle(base, lifecycleCallbacks);
            }
        }
        if (appLives.size() > 0) {
            for (IAppLife life :
                    appLives) {
                life.attachBaseContext(base);
            }
        }
    }

    @Override
    public void onCreate(Application application) {
        if (appLives.size() > 0) {
            for (IAppLife life :
                    appLives) {
                life.onCreate(application);
            }
        }
        if (lifecycleCallbacks.size() > 0) {
            for (Application.ActivityLifecycleCallbacks life :
                    lifecycleCallbacks) {
                application.registerActivityLifecycleCallbacks(life);
            }
        }
    }

    @Override
    public void onTerminate(Application application) {
        if (appLives.size() > 0) {
            for (IAppLife life :
                    appLives) {
                life.onTerminate(application);
            }
        }
        if (lifecycleCallbacks.size() > 0) {
            for (Application.ActivityLifecycleCallbacks life :
                    lifecycleCallbacks) {
                application.unregisterActivityLifecycleCallbacks(life);
            }
        }
    }

}
