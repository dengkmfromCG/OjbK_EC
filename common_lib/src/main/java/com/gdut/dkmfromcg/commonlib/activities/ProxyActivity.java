package com.gdut.dkmfromcg.commonlib.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;

import com.gdut.dkmfromcg.commonlib.R;
import com.gdut.dkmfromcg.commonlib.fragments.ProxyFragment;

import me.yokeyword.fragmentation.ExtraTransaction;
import me.yokeyword.fragmentation.ISupportActivity;
import me.yokeyword.fragmentation.SupportActivityDelegate;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by dkmFromCG on 2018/3/11.
 * function:
 */

public abstract class ProxyActivity extends AppCompatActivity implements ISupportActivity {

    //代理Activity
    private final SupportActivityDelegate ACTIVITY_DELEGATE = new SupportActivityDelegate(this);

    public abstract ProxyFragment setRootFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //要先调用 ACTIVITY_DELEGATE.onCreate(...) 才可以调用ACTIVITY_DELEGATE.loadRootFragment(...)
        ACTIVITY_DELEGATE.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
        StatusBarCompat.translucentStatusBar(this, true);
    }

    private void initContainer(@Nullable Bundle savedInstanceState) {
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.fragment_container);
        setContentView(container);
        if (savedInstanceState == null) {
            ACTIVITY_DELEGATE.loadRootFragment(R.id.fragment_container, setRootFragment());
        }

    }

    @Override
    protected void onDestroy() {
        ACTIVITY_DELEGATE.onDestroy();
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }

    @Override
    public SupportActivityDelegate getSupportDelegate() {
        return ACTIVITY_DELEGATE;
    }

    @Override
    public ExtraTransaction extraTransaction() {
        return ACTIVITY_DELEGATE.extraTransaction();
    }

    @Override
    public FragmentAnimator getFragmentAnimator() {
        return ACTIVITY_DELEGATE.getFragmentAnimator();
    }

    @Override
    public void setFragmentAnimator(FragmentAnimator fragmentAnimator) {
        ACTIVITY_DELEGATE.setFragmentAnimator(fragmentAnimator);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return ACTIVITY_DELEGATE.onCreateFragmentAnimator();
    }

    @Override
    public void onBackPressedSupport() {
        ACTIVITY_DELEGATE.onBackPressedSupport();
    }

    @Override
    public void onBackPressed() {
        ACTIVITY_DELEGATE.onBackPressed();
    }


}
