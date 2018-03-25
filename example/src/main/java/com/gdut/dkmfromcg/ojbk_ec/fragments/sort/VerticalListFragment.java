package com.gdut.dkmfromcg.ojbk_ec.fragments.sort;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.gdut.dkmfromcg.ojbk_ec.R;
import com.gdut.dkmfromcg.ojbk_ec.R2;
import com.gdut.dkmfromcg.ojbk_ec.fragments.sort.adapter.VerticalListMenuAdapter;
import com.gdut.dkmfromcg.ojbk_ec.fragments.sort.model.VerticalListDataConverter;
import com.gdut.dkmfromcg.ojbk_ui.recycler.data.MultipleItemEntity;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dkmFromCG on 2018/3/22.
 * function:
 */

public class VerticalListFragment extends ProxyFragment {

    @BindView(R2.id.rv_vertical_menu_list)
    RecyclerView mRecyclerView = null;

    private Context mContext = null;
    private final int LOAD_SUCCESS = 1;
    private List<MultipleItemEntity> mData = null;
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_SUCCESS:
                    final SortFragment sortFragment = (SortFragment) getParentFragment();
                    final VerticalListMenuAdapter adapter = new VerticalListMenuAdapter(mData, sortFragment);
                    mRecyclerView.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public Object setLayout() {
        return R.layout.fragment_vertical_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mContext = getContext();
        initRecyclerView();

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mData = new VerticalListDataConverter().setJsonData(getLocalJson()).convert();
                final Message message = new Message();
                message.what = LOAD_SUCCESS;
                handler.sendMessage(message);

            }
        }).start();

    }


    private void initRecyclerView() {
        final LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(manager);
        //屏蔽动画效果
        mRecyclerView.setItemAnimator(null);
    }

    private String getLocalJson() {
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = mContext.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open("vertical_list_menu.json")));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
