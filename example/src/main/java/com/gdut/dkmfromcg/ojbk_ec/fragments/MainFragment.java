package com.gdut.dkmfromcg.ojbk_ec.fragments;


import android.graphics.Color;
import android.support.v4.app.Fragment;

import com.gdut.dkmfromcg.ojbk_ec.fragments.discover.DiscoverFragment;
import com.gdut.dkmfromcg.ojbk_ec.fragments.home.HomeFragment;
import com.gdut.dkmfromcg.ojbk_ec.fragments.mine.MineFragment;
import com.gdut.dkmfromcg.ojbk_ec.fragments.shopping.ShoppingFragment;
import com.gdut.dkmfromcg.ojbk_ec.fragments.sort.SortFragment;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;
import com.gdut.dkmfromcg.ojbk_ui.bottom.BaseBottomTabFragment;
import com.gdut.dkmfromcg.ojbk_ui.bottom.ItemBuilder;
import com.gdut.dkmfromcg.ojbk_ui.bottom.TabItemBean;

import java.util.LinkedHashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseBottomTabFragment {

    @Override
    public LinkedHashMap<TabItemBean, ProxyFragment> setItems(ItemBuilder builder) {
        final LinkedHashMap<TabItemBean, ProxyFragment> items = new LinkedHashMap<>();
        items.put(new TabItemBean("{fa-home}", "主页"),new HomeFragment());
        items.put(new TabItemBean("{fa-sort}", "分类"), new SortFragment());
        items.put(new TabItemBean("{fa-compass}", "发现"), new DiscoverFragment());
        items.put(new TabItemBean("{fa-shopping-cart}", "购物车"), new ShoppingFragment());
        items.put(new TabItemBean("{fa-user}", "我的"), new MineFragment());
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
