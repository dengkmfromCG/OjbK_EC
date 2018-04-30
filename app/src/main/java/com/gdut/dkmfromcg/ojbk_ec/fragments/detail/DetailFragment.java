package com.gdut.dkmfromcg.ojbk_ec.fragments.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.gdut.dkmfromcg.ojbk_ec.R;

import com.gdut.dkmfromcg.commonlib.fragments.ProxyFragment;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by dkmFromCG on 2018/3/22.
 * function:
 */

public class DetailFragment extends ProxyFragment {

    public static DetailFragment create(){
        return new DetailFragment();
    }
    @Override
    public Object setLayout() {
        return R.layout.fragment_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }


}
