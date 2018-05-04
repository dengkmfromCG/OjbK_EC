package com.gdut.dkmfromcg.launcherpage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gdut.dkmfromcg.commonlib.activities.ProxyActivity;
import com.gdut.dkmfromcg.commonlib.app.sign.ISignListener;
import com.gdut.dkmfromcg.commonlib.fragments.BaseFragment;
import com.gdut.dkmfromcg.commonlib.fragments.ProxyFragment;
import com.gdut.dkmfromcg.commonlib.router.RouterPath;
import com.gdut.dkmfromcg.commonlib.util.log.Logger;
import com.gdut.dkmfromcg.commonlib.util.storage.PreferenceTool;
import com.gdut.dkmfromcg.launcherpage.app.LauncherApp;
import com.gdut.dkmfromcg.launcherpage.tag.OnLauncherFinishTag;
import com.gdut.dkmfromcg.launcherpage.tag.ScrollLauncherTag;

@Route(path = RouterPath.LAUNCHER)
public class LauncherActivity extends ProxyActivity implements ILauncherListener, ISignListener {

    private static final String TAG = "LauncherActivity";
    private BaseFragment mainFragment = null;
    private BaseFragment logInFragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mainFragment = LauncherApp.getAppModule().getMainFragment();
        logInFragment = LauncherApp.getRegisterModule().getLogInFragment();
    }

    @Override
    public ProxyFragment setRootFragment() {
        //如果是第一次打开App
        if (!PreferenceTool.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())) {
            return new LauncherScrollFragment();
        }
        return new LauncherFragment();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case SIGNED:
                getSupportDelegate().startWithPop(mainFragment);
                break;
            case NOT_SIGNED:
                getSupportDelegate().start(logInFragment);
                break;
            default:
                break;
        }
    }

    @Override
    public void onSignInSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        Logger.d(TAG, ": " + getCallingActivity() + "denglu");
        getSupportDelegate().startWithPop(mainFragment);
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        Logger.d(TAG, ": " + getCallingActivity() + "zhuce");
        getSupportDelegate().startWithPop(mainFragment);
    }

}
