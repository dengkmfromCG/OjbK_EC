package com.gdut.dkmfromcg.ojbk_ec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.widget.Toast;

import com.gdut.dkmfromcg.ojbk_ec.fragments.ExampleFragment;
import com.gdut.dkmfromcg.ojkb.activities.ProxyActivity;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;
import com.gdut.dkmfromcg.ojkb.util.storage.PreferenceTool;
import com.gdut.dkmfromcg.okjbec.launcher.ILauncherListener;
import com.gdut.dkmfromcg.okjbec.launcher.LauncherFragment;
import com.gdut.dkmfromcg.okjbec.launcher.LauncherScrollFragment;
import com.gdut.dkmfromcg.okjbec.launcher.OnLauncherFinishTag;
import com.gdut.dkmfromcg.okjbec.launcher.launcherScroll.ScrollLauncherTag;
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
    }

    @Override
    public ProxyFragment setRootFragment() {
        //如果是第一次打开App
        if (! PreferenceTool.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())){
            return new LauncherScrollFragment();
        }
        return new LauncherFragment();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                startWithPop(new ExampleFragment());
                break;
            case NOT_SIGNED:
                startWithPop(new LogInFragment());
                break;
            default:
                break;
        }
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        Log.d(TAG, ": "+getCallingActivity()+"denglu");
        startWithPop(new ExampleFragment());
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        Log.d(TAG, ": "+getCallingActivity()+"zhuce");
        startWithPop(new ExampleFragment());
    }
}
