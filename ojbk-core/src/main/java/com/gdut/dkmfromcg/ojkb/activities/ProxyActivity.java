package com.gdut.dkmfromcg.ojkb.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.gdut.dkmfromcg.ojkb.R;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;

import me.yokeyword.fragmentation.SupportActivity;
import qiu.niorgai.StatusBarCompat;

/**
 * Created by dkmFromCG on 2018/3/11.
 * function:
 */

public abstract class ProxyActivity extends SupportActivity {

    public abstract ProxyFragment setRootFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContainer(savedInstanceState);
        StatusBarCompat.translucentStatusBar(this,true);
    }

    private void initContainer(@Nullable Bundle savedInstanceState){
        final ContentFrameLayout container=new ContentFrameLayout(this);
        container.setId(R.id.fragment_container);
        setContentView(container);
        if (savedInstanceState == null){
            loadRootFragment(R.id.fragment_container,setRootFragment());
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
        
    }


}
