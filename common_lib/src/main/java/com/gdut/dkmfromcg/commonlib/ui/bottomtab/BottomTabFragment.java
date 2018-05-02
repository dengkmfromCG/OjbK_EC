package com.gdut.dkmfromcg.commonlib.ui.bottomtab;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdut.dkmfromcg.commonlib.R;
import com.gdut.dkmfromcg.commonlib.fragments.BaseFragment;
import com.gdut.dkmfromcg.commonlib.fragments.ProxyFragment;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by dkmFromCG on 2018/4/30.
 * function:
 */

public abstract class BottomTabFragment extends ProxyFragment implements View.OnClickListener {

    private static final String TAG = "BottomTabFragment";

    private LinearLayout tabsBar = null;
    private int mIndexFragment = 0; //初识化页面时,选择的页面,默认是 0,即第一个页面
    private int mCurrentDelegate = 0;
    private int mClickedColor = Color.BLUE; //点击Tab时,text和Icon的颜色

    private final List<TabItemBean> TAB_BEANS = new ArrayList<>();
    private final List<BaseFragment> ITEM_Fragments = new ArrayList<>();
    private final LinkedHashMap<TabItemBean, BaseFragment> ITEMS = new LinkedHashMap<>();

    public abstract LinkedHashMap<TabItemBean, BaseFragment> setItems(ItemBuilder builder);

    public abstract int setIndexFragment();

    @ColorInt
    public abstract int setClickedColor();


    @Override
    public Object setLayout() {
        return R.layout.fragment_bottom_tab;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor();
        }
        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<TabItemBean, BaseFragment> items = setItems(builder);
        ITEMS.putAll(items);
        if (setIndexFragment() >= 0 && setIndexFragment() < ITEMS.size()) {
            mIndexFragment = setIndexFragment();
        }
        for (Map.Entry<TabItemBean, BaseFragment> item : ITEMS.entrySet()) {
            final TabItemBean tabItemBean = item.getKey();
            final BaseFragment baseFragment = item.getValue();
            TAB_BEANS.add(tabItemBean);
            ITEM_Fragments.add(baseFragment);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        tabsBar = rootView.findViewById(R.id.tabs_bar);
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.item_bottom_tab,tabsBar);
            final RelativeLayout item= (RelativeLayout) tabsBar.getChildAt(i);
            //设置每个item的点击事件
            item.setTag(i);
            item.setOnClickListener(this);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final TextView itemTitle= (TextView) item.getChildAt(1);
            //初始化数据
            final TabItemBean itemBean=TAB_BEANS.get(i);
            itemIcon.setText(itemBean.getIcon());
            itemTitle.setText(itemBean.getTitle());
            if (i==mIndexFragment){
                itemIcon.setTextColor(mClickedColor);
                itemTitle.setTextColor(mClickedColor);
            }
        }
        final ISupportFragment[] fragmentArray=ITEM_Fragments.toArray(new ISupportFragment[size]);
        getSupportDelegate().loadMultipleRootFragment(R.id.bottom_tab_fragments_container,mIndexFragment,fragmentArray);
    }

    @Override
    public void onClick(View v) {
        final int tag= (int) v.getTag();
        resetColor();
        final RelativeLayout item = (RelativeLayout) v;
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        final TextView itemTitle= (TextView) item.getChildAt(1);
        itemIcon.setTextColor(mClickedColor);
        itemTitle.setTextColor(mClickedColor);
        getSupportDelegate().showHideFragment(ITEM_Fragments.get(tag),ITEM_Fragments.get(mCurrentDelegate));
        //注意先后顺序
        mCurrentDelegate = tag;
    }

    private void resetColor() {
        final int count = tabsBar.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) tabsBar.getChildAt(i);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final TextView itemTitle= (TextView) item.getChildAt(1);
            itemIcon.setTextColor(Color.GRAY);
            itemTitle.setTextColor(Color.GRAY);
        }
    }
}
