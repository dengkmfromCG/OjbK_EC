package com.gdut.dkmfromcg.ojkb.app;

import android.content.Context;
import android.os.Handler;

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

    public static Context getApplicationContext(){
        return getConfiguration(ConfigType.APPLICATION_CONTEXT);
    }

    public static Handler getHandler(){
        return getConfiguration(ConfigType.HANDLER);
    }


    private static Configurator getConfigurator() {
        return Configurator.getInstance();
    }
}
