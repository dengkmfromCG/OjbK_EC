package com.gdut.dkmfromcg.launcherpage;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.gdut.dkmfromcg.launcherpage.banner.LocalImageBannerCreator;
import com.gdut.dkmfromcg.launcherpage.tag.OnLauncherFinishTag;
import com.gdut.dkmfromcg.launcherpage.tag.ScrollLauncherTag;
import com.gdut.dkmfromcg.commonlib.app.sign.AccountManager;
import com.gdut.dkmfromcg.commonlib.app.sign.IUserChecker;
import com.gdut.dkmfromcg.commonlib.fragments.ProxyFragment;
import com.gdut.dkmfromcg.commonlib.util.storage.PreferenceTool;

import java.util.ArrayList;

/**
 * Created by dkmFromCG on 2018/3/13.
 * function:
 */

public class LauncherScrollFragment extends ProxyFragment implements OnItemClickListener {

    private ConvenientBanner<Integer> mConvenientBanner = null;
    private static final ArrayList<Integer> INTEGERS = new ArrayList<>();
    private ILauncherListener mILauncherListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initBanner();
    }

    private void initBanner() {
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        LocalImageBannerCreator.setDefault(mConvenientBanner, INTEGERS, this);
    }

    @Override
    public void onItemClick(int position) {
        //当滑到最后一个页面时
        if (position == INTEGERS.size() - 1) {
            //HAS_FIRST_LAUNCHER_APP 置为 true,表示打开过了
            PreferenceTool.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(), true);

            checkUserAccount();
        }
    }

    //检查是否登录
    private void checkUserAccount() {
        AccountManager.checkAccount(new IUserChecker() {
            @Override
            public void onSingIn() {
                if (mILauncherListener != null) {
                    mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                }
            }

            @Override
            public void onNotSingIn() {
                if (mILauncherListener != null) {
                    mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                }
            }
        });
    }
}
