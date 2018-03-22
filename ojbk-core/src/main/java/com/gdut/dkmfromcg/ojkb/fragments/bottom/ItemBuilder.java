package com.gdut.dkmfromcg.ojkb.fragments.bottom;

import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;

import java.util.LinkedHashMap;

/**
 * Created by dkmFromCG on 2018/3/15.
 * function:
 */

public class ItemBuilder {

    private final LinkedHashMap<TabItemBean, ProxyFragment> ITEMS = new LinkedHashMap<>();

    static ItemBuilder builder() {
        return new ItemBuilder();
    }

    public final ItemBuilder addItem(TabItemBean bean, ProxyFragment delegate) {
        ITEMS.put(bean, delegate);
        return this;
    }

    public final ItemBuilder addItems(LinkedHashMap<TabItemBean, ProxyFragment> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<TabItemBean, ProxyFragment> build() {
        return ITEMS;
    }

}
