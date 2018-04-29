package com.gdut.dkmfromcg.homepage;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;
import com.gdut.dkmfromcg.ojkb.recyclerview.data.DataConverter;
import com.gdut.dkmfromcg.ojkb.recyclerview.divider.BaseDecoration;
import com.joanzapata.iconify.widget.IconTextView;


/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends ProxyFragment implements
        IContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {


    RecyclerView mRecyclerView = null;
    SwipeRefreshLayout mRefreshLayout = null;
    IconTextView mIconScan = null;
    AppCompatEditText mEtSearchView = null;
    IconTextView mIconMessage = null;

    private HomeFragmentPresenter mPresenter;
    private MultipleRecyclerAdapter mAdapter = null;


    @Override
    public Object setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mPresenter = new HomeFragmentPresenter(this);
        mRecyclerView = rootView.findViewById(R.id.rv_home);
        mRefreshLayout = rootView.findViewById(R.id.srl_home);
        mIconScan = rootView.findViewById(R.id.icon_home_scan);
        mEtSearchView = rootView.findViewById(R.id.et_search_view);
        mIconMessage = rootView.findViewById(R.id.icon_home_message);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mPresenter.firstPage();
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }
        super.onDestroy();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
        mRefreshLayout.setOnRefreshListener(this);
    }

    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration
                (BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.home_module_background), 5));
        mRecyclerView.addOnItemTouchListener(RvItemClickListener.create(this));
    }

    @Override
    public void initAdapter(DataConverter converter) {
        mAdapter = MultipleRecyclerAdapter.create(converter);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public int getAdapterDataSize() {
        return mAdapter.getData().size();
    }

    @Override
    public void refreshAdapter(DataConverter converter) {
        mAdapter.addData(converter.convert());
        mAdapter.loadMoreComplete();
    }

    @Override
    public void adapterLoadMoreEnd() {
        mAdapter.loadMoreEnd(true);
    }

    @Override
    public void stopRefresh() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMore();
    }


    @Override
    public void onRefresh() {
        mRefreshLayout.setRefreshing(true);
        mPresenter.refreshData();
    }

}
