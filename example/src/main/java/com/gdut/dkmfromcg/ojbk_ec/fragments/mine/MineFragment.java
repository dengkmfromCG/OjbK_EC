package com.gdut.dkmfromcg.ojbk_ec.fragments.mine;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gdut.dkmfromcg.ojbk_ec.R;
import com.gdut.dkmfromcg.ojbk_ui.recycler.grouplist.GroupListAdapter;
import com.gdut.dkmfromcg.ojbk_ui.recycler.grouplist.GroupListType;
import com.gdut.dkmfromcg.ojbk_ui.recycler.grouplist.ListBean;
import com.gdut.dkmfromcg.ojbk_ui.recycler.grouplist.Section;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends ProxyFragment {


    @Override
    public Object setLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initRv(rootView);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void initRv(View rootView){
        Section section=new Section("第一个模块");
        ListBean address=new ListBean.Builder()
                .setItemType(GroupListType.ARROW_ITEM)
                .setTitle("收货地址")
                .build();
        ListBean settings=new ListBean.Builder()
                .setItemType(GroupListType.ARROW_ITEM)
                .setTitle("系统设置")
                .build();
        final List<MultiItemEntity> entityList=new ArrayList<>();
        entityList.add(section);
        entityList.add(address);
        entityList.add(settings);
        final GroupListAdapter adapter=new GroupListAdapter(entityList);
        final RecyclerView recyclerView=rootView.findViewById(R.id.rv_personal_setting);
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

}
