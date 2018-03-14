package com.gdut.dkmfromcg.okjbec.launcher;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.gdut.dkmfromcg.ojkb.app.AccountManager;
import com.gdut.dkmfromcg.ojkb.app.IUserChecker;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;
import com.gdut.dkmfromcg.okjbec.R;
import com.gdut.dkmfromcg.okjbec.R2;
import com.gdut.dkmfromcg.okjbec.launcher.timer.BaseTimerTask;
import com.gdut.dkmfromcg.okjbec.launcher.timer.ITimerListener;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LauncherFragment extends ProxyFragment implements ITimerListener {


    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer = null;

    private Timer mTimer = null;
    private int mCount = 5;

    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            checkUserAccount();
        }
    }

    private ILauncherListener mILauncherListener =null;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener){
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mTvTimer!=null){
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s",mCount));
                    mCount--;
                    if (mCount<0){
                        if (mTimer!=null){
                            mTimer.cancel();
                            mTimer=null;
                            //your action
                            checkUserAccount();
                        }
                    }
                }
            }
        });
    }

    //检查是否登录
    private void checkUserAccount() {
        AccountManager.checkAccount(new IUserChecker() {
            @Override
            public void onSingIn() {
                if (mILauncherListener !=null){
                    mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                }
            }

            @Override
            public void onNotSingIn() {
                if (mILauncherListener !=null){
                    mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                }
            }
        });
    }

    private void initTimer() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask(this);
        /** schedule(...)
         * @param task   task to be scheduled.
         * @param delay  delay in milliseconds before task is to be executed.
         * @param period time in milliseconds between successive task executions.
         */
        mTimer.schedule(task, 0, 1000);
    }
}
