package com.gdut.dkmfromcg.ojbk_ec.fragments.home;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.gdut.dkmfromcg.ojbk_ec.fragments.detail.DetailFragment;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;

/**
 * Created by dkmFromCG on 2018/3/22.
 * function:
 */

public class RvItemClickListener extends SimpleClickListener {

    private final ProxyFragment mFragment;

    private RvItemClickListener( ProxyFragment fragment) {
        this.mFragment = fragment;
    }

    public static SimpleClickListener create( ProxyFragment fragment) {
        return new RvItemClickListener(fragment);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mFragment.getSupportDelegate().start(DetailFragment.create());
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
    }
}
