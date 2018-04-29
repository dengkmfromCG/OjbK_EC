package com.gdut.dkmfromcg.sortpage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;
import com.gdut.dkmfromcg.ojkb.recyclerview.data.MultipleItemEntity;
import com.gdut.dkmfromcg.ojkb.recyclerview.divider.BaseDecoration;
import com.gdut.dkmfromcg.sortpage.model.SortItemDataConverter;




import java.util.List;



/**
 * Created by dkmFromCG on 2018/3/22.
 * function:
 */

public class ContentFragment extends ProxyFragment {

    private static final String BUNDLE_CONTENT_JSON = "BUNDLE_CONTENT_JSON";
    private JSONArray mCategoryList = null;
    private List<MultipleItemEntity> entityList = null;
    private MultipleRecyclerAdapter mAdapter = null;

    RecyclerView mRecyclerView;

    public static ContentFragment getInstance(JSONArray categoryList) {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_CONTENT_JSON, categoryList);
        final ContentFragment fragment = new ContentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle bundle = getArguments();
        if (bundle != null) {
            mCategoryList = (JSONArray) bundle.getSerializable(BUNDLE_CONTENT_JSON);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_list_content;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initRv(rootView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                entityList = SortItemDataConverter.create(mCategoryList).convert();
                mAdapter = MultipleRecyclerAdapter.create(entityList);
                Message message = new Message();
                message.what = -1;
                handler.sendMessage(message);
            }
        }).run();

    }

    private void initRv(View rootView) {
        mRecyclerView=rootView.findViewById(R.id.rv_list_content);
        final GridLayoutManager manager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration
                (BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background), 5));
    }

    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case -1:
                    mRecyclerView.setAdapter(mAdapter);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCategoryList=null;
        entityList=null;
        mAdapter=null;
    }
}
