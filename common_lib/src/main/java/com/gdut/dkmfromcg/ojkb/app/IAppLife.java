package com.gdut.dkmfromcg.ojkb.app;

import android.app.Application;
import android.content.Context;


public interface IAppLife {

    void attachBaseContext(Context tx);

    void onCreate(Application application);

    void onTerminate(Application application);

}
