package com.gdut.dkmfromcg.ojkb.fragments;

/**
 * Created by dkmFromCG on 2018/3/11.
 * function:
 */

public abstract class ProxyFragment extends PermissionCheckerFragment {

    @SuppressWarnings("unchecked")
    public <T extends ProxyFragment> T getParentFrag() {
        return (T) getParentFragment();
    }
}
