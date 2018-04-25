package com.gdut.dkmfromcg.ojbk_ec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.gdut.dkmfromcg.ojbk_ec.fragments.ExampleFragment;
import com.gdut.dkmfromcg.ojbk_ec.fragments.MainFragment;
import com.gdut.dkmfromcg.ojkb.activities.ProxyActivity;
import com.gdut.dkmfromcg.ojkb.util.config.Configs;
import com.gdut.dkmfromcg.ojkb.util.config.Configurator;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;
import com.gdut.dkmfromcg.ojkb.util.log.Logger;
import com.gdut.dkmfromcg.okjbec.launcher.ILauncherListener;
import com.gdut.dkmfromcg.okjbec.launcher.OnLauncherFinishTag;
import com.gdut.dkmfromcg.okjbec.sign.ISignListener;
import com.gdut.dkmfromcg.okjbec.sign.LogInFragment;

public class LauncherActivity extends ProxyActivity implements ILauncherListener,ISignListener {

    private static final String TAG = "LauncherActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Configurator.getInstance().withActivity(this);

    }

    @Override
    public ProxyFragment setRootFragment() {
        /*//如果是第一次打开App
        if (! PreferenceTool.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())){
            return new LauncherScrollFragment();
        }
        return new LauncherFragment();*/

        return new MainFragment();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                getSupportDelegate().startWithPop(new ExampleFragment());
                break;
            case NOT_SIGNED:
                getSupportDelegate().startWithPop(new LogInFragment());
                break;
            default:
                break;
        }
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        Logger.d(TAG, ": "+getCallingActivity()+"denglu");
        getSupportDelegate().startWithPop(new ExampleFragment());
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        Logger.d(TAG, ": "+getCallingActivity()+"zhuce");
        getSupportDelegate().startWithPop(new ExampleFragment());
    }
}
