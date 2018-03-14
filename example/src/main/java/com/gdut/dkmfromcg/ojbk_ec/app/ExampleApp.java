package com.gdut.dkmfromcg.ojbk_ec.app;

import android.app.Application;

import com.gdut.dkmfromcg.ojbk_ec.R;
import com.gdut.dkmfromcg.ojkb.app.DKM;
import com.gdut.dkmfromcg.ojkb.net.interceptors.DebugInterceptor;

/**
 * Created by dkmFromCG on 2018/3/8.
 * function:
 */

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DKM.init(this)
                .putApiHost("http://192.168.0.189/")
                .withInterceptor(new DebugInterceptor("index", R.raw.text))
                .configure();
    }
}
