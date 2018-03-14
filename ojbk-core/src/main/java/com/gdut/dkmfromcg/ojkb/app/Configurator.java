package com.gdut.dkmfromcg.ojkb.app;

import java.util.ArrayList;
import java.util.WeakHashMap;

import okhttp3.Interceptor;

/**
 * Created by dkmFromCG on 2018/3/8.
 * function:
 */

public class Configurator {

    private static final WeakHashMap<Object,Object> DKM_CONFIGS=new WeakHashMap<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    /*单例模式*/
    private static class Holder{
        private static final Configurator INSTANCE=new Configurator();
    }
    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }
    private Configurator(){
        DKM_CONFIGS.put(ConfigType.CONFIG_READY,false); //初始化时传入false,说明还没准备好配置
    }

    public WeakHashMap<Object,Object> getDKM_CONFIGS() {
        return DKM_CONFIGS;
    }

    public final Configurator putApiHost(String host){
        DKM_CONFIGS.put(ConfigType.API_HOST,host);
        return this;
    }

    public final Configurator withInterceptor( Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        DKM_CONFIGS.put(ConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        DKM_CONFIGS.put(ConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final void configure(){
        DKM_CONFIGS.put(ConfigType.CONFIG_READY,true);
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key){
        checkConfiguration();
        final Object value =DKM_CONFIGS.get(key);
        if (value==null){
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) value;
    }

    /*判断 Configurator 准备好了没*/
    private void checkConfiguration(){
        final boolean configIsReady= (boolean) DKM_CONFIGS.get(ConfigType.CONFIG_READY);
        if (!configIsReady){
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }
}
