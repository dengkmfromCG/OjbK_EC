package com.gdut.dkmfromcg.ojkb.util.config;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.WeakHashMap;

import okhttp3.Interceptor;

/**
 * Created by dkmFromCG on 2018/3/8.
 * function:
 */

public class Configurator {

    private static final WeakHashMap<Object,Object> APP_CONFIGS =new WeakHashMap<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    private static final Handler HANDLER = new Handler();
    /*单例模式*/
    private static class Holder{
        private static final Configurator INSTANCE=new Configurator();
    }
    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }
    private Configurator(){
        APP_CONFIGS.put(ConfigType.CONFIG_READY,false); //初始化时传入false,说明还没准备好配置
        APP_CONFIGS.put(ConfigType.HANDLER,HANDLER);
    }

    public WeakHashMap<Object,Object> getAPP_CONFIGS() {
        return APP_CONFIGS;
    }

    public final Configurator putApiHost(String host){
        APP_CONFIGS.put(ConfigType.API_HOST,host);
        return this;
    }

    public final Configurator withInterceptor( Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        APP_CONFIGS.put(ConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        APP_CONFIGS.put(ConfigType.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withWeChatAppId(String appId) {
        APP_CONFIGS.put(ConfigType.WE_CHAT_APP_ID, appId);
        return this;
    }

    public final Configurator withWeChatAppSecret(String appSecret) {
        APP_CONFIGS.put(ConfigType.WE_CHAT_APP_SECRET, appSecret);
        return this;
    }

    public final Configurator withActivity(AppCompatActivity activity) {
        APP_CONFIGS.put(ConfigType.ACTIVITY, activity);
        return this;
    }

    public final void configure(){
        Logger.addLogAdapter(new AndroidLogAdapter());
        APP_CONFIGS.put(ConfigType.CONFIG_READY,true);
    }

    @SuppressWarnings("unchecked")
    final public <T> T getConfiguration(Object key){
        checkConfiguration();
        final Object value = APP_CONFIGS.get(key);
        if (value==null){
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) value;
    }

    /*判断 Configurator 准备好了没*/
    private void checkConfiguration(){
        final boolean configIsReady= (boolean) APP_CONFIGS.get(ConfigType.CONFIG_READY);
        if (!configIsReady){
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }
}
