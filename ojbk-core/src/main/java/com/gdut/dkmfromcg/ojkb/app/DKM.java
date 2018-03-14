package com.gdut.dkmfromcg.ojkb.app;

import android.content.Context;

import java.util.WeakHashMap;

/**
 * Created by dkmFromCG on 2018/3/8.
 * function:
 */

public class DKM {

    public static Configurator init(Context context){
        Configurator.getInstance()
                .getDKM_CONFIGS()
                .put(ConfigType.APPLICATION_CONTEXT,context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static <T> T getConfiguration(Object key) {
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplication(){
        return getConfiguration(ConfigType.APPLICATION_CONTEXT);
    }

    private static Configurator getConfigurator() {
        return Configurator.getInstance();
    }
}
