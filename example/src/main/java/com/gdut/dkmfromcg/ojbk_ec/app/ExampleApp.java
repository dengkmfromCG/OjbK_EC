package com.gdut.dkmfromcg.ojbk_ec.app;

import android.app.Application;


import com.gdut.dkmfromcg.ojkb.app.DKM;
import com.gdut.dkmfromcg.ojbk_ui.icon.FontEcModule;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by dkmFromCG on 2018/3/8.
 * function:
 */

public class ExampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DKM.init(this)
                .putApiHost("https://www.easy-mock.com/")
                .withWeChatAppId("your wechat appId")
                .withWeChatAppSecret("your wechat appSecret")
                .configure();


        Iconify.with(new FontAwesomeModule())
        .with(new FontEcModule());
    }
}
