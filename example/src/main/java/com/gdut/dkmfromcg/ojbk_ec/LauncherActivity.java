package com.gdut.dkmfromcg.ojbk_ec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.gdut.dkmfromcg.ojkb.activities.ProxyActivity;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;
import com.gdut.dkmfromcg.ojkb.util.storage.PreferenceTool;
import com.gdut.dkmfromcg.okjbec.launcher.LauncherFragment;
import com.gdut.dkmfromcg.okjbec.launcher.launcherScroll.ILauncherListener;
import com.gdut.dkmfromcg.okjbec.launcher.launcherScroll.LauncherScrollFragment;
import com.gdut.dkmfromcg.okjbec.launcher.launcherScroll.OnLauncherFinishTag;
import com.gdut.dkmfromcg.okjbec.launcher.launcherScroll.ScrollLauncherTag;
import com.gdut.dkmfromcg.okjbec.sign.LogInFragment;

public class LauncherActivity extends ProxyActivity implements ILauncherListener {

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
        /*//如果是第一次打开App
        if (! PreferenceTool.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())){
            return new LauncherScrollFragment();
        }
        return new LauncherFragment();*/
        return new LogInFragment();
    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {

    }

}
