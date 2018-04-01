package com.gdut.dkmfromcg.ojbk_ec.fragments.discover;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.gdut.dkmfromcg.ojbk_ec.R;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;
import com.gdut.dkmfromcg.ojkb.web.WebFragmentImpl;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends ProxyFragment {

    @Override
    public Object setLayout() {
        return R.layout.fragment_discover;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        /*final WebFragmentImpl fragment=WebFragmentImpl.create("discover.html");
        fragment.setTopFragment(this);
        getSupportDelegate().loadRootFragment(R.id.web_discovery_container,fragment);*/
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

}
