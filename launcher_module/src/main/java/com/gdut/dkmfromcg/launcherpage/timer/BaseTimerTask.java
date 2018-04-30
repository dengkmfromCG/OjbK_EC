package com.gdut.dkmfromcg.launcherpage.timer;

import java.util.TimerTask;

/**
 * Created by dkmFromCG on 2018/3/12.
 * function:
 */

public class BaseTimerTask extends TimerTask {

    private ITimerListener mITimerListener = null;

    public BaseTimerTask(ITimerListener timerListener) {
        this.mITimerListener = timerListener;
    }

    @Override
    public void run() {
        if (mITimerListener != null) {
            mITimerListener.onTimer();
        }
    }
}
