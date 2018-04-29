package com.gdut.dkmfromcg.ojbk_ec.fragments;


import android.graphics.Color;
import android.support.v4.app.Fragment;

import com.gdut.dkmfromcg.ojbk_ec.app.ExampleApp;
import com.gdut.dkmfromcg.ojbk_ui.bottom.BaseBottomTabFragment;
import com.gdut.dkmfromcg.ojbk_ui.bottom.ItemBuilder;
import com.gdut.dkmfromcg.ojbk_ui.bottom.TabItemBean;
import com.gdut.dkmfromcg.ojkb.fragments.BaseFragment;

import java.util.LinkedHashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseBottomTabFragment {

    @Override
    public LinkedHashMap<TabItemBean, BaseFragment> setItems(ItemBuilder builder) {
        final LinkedHashMap<TabItemBean, BaseFragment> items = new LinkedHashMap<>();
        items.put(new TabItemBean("{fa-home}", "主页"), ExampleApp.getHomeModule().getHomeFragment());
        items.put(new TabItemBean("{fa-sort}", "分类"), ExampleApp.getSortModule().getSortFragment());
        items.put(new TabItemBean("{fa-compass}", "发现"), ExampleApp.getDiscoverModule().getDisCoverFragment());
        items.put(new TabItemBean("{fa-shopping-cart}", "购物车"), ExampleApp.getShopCartModule().getShopCartFragment());
        items.put(new TabItemBean("{fa-user}", "我的"), ExampleApp.getMineModule().getMineFragment());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexFragment() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }

}
