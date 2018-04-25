package com.gdut.dkmfromcg.ojbk_ec.app;

import android.app.Application;


import com.gdut.dkmfromcg.ojkb.app.BaseApp;
import com.gdut.dkmfromcg.ojkb.util.config.Configs;
import com.gdut.dkmfromcg.ojbk_ui.icon.FontEcModule;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by dkmFromCG on 2018/3/8.
 * function:
 */

public class ExampleApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();

        Iconify.with(new FontAwesomeModule())
        .with(new FontEcModule());

    }
}
