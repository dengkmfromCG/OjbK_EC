package com.gdut.dkmfromcg.ojbk_ui.bottom;


import com.gdut.dkmfromcg.ojkb.fragments.BaseFragment;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;

import java.util.LinkedHashMap;

/**
 * Created by dkmFromCG on 2018/3/15.
 * function:
 */

public class ItemBuilder {

    private final LinkedHashMap<TabItemBean, BaseFragment> ITEMS = new LinkedHashMap<>();

    static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(TabItemBean bean, BaseFragment delegate) {
        ITEMS.put(bean, delegate);
        return this;
    }

    public final ItemBuilder addItems(LinkedHashMap<TabItemBean, BaseFragment> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<TabItemBean, BaseFragment> build() {
        return ITEMS;
    }

}
