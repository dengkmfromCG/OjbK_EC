package com.gdut.dkmfromcg.ojkb.util.config;

import android.content.Context;
import android.os.Handler;

/**
 * Created by dkmFromCG on 2018/3/8.
 * function:
 */

public class Configs {

    public static Configurator init(Context context){
        Configurator.getInstance()
                .getAPP_CONFIGS()
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


    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }
}
