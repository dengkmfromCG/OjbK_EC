package com.gdut.dkmfromcg.ojbk_ui.bottom;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;


import com.gdut.dkmfromcg.ojbk_ui.R;
import com.gdut.dkmfromcg.ojbk_ui.R2;
import com.gdut.dkmfromcg.ojkb.fragments.BaseFragment;
import com.gdut.dkmfromcg.ojbk_ui.widget.NotSlideViewPager;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseBottomTabFragment extends BaseFragment {

    private static final String TAG = "BaseBottomTabFragment";
    @BindView(R2.id.view_pager)
    NotSlideViewPager viewPager;
    @BindView(R2.id.bottom_tab)
    TabLayout tabLayout;

    private final LinkedHashMap<TabItemBean, BaseFragment> ITEMS = new LinkedHashMap<>();

    public abstract LinkedHashMap<TabItemBean, BaseFragment> setItems(ItemBuilder builder);

    private final List<TabItemBean> TAB_BEANS = new ArrayList<>();
    private final List<BaseFragment> ITEM_Fragments = new ArrayList<>();


    private int mIndexFragment = 0; //初识化页面时,选择的页面,默认是 0,即第一个页面
    private int mClickedColor = Color.RED; //点击Tab时,text和Icon的颜色

    public abstract int setIndexFragment();

    @ColorInt
    public abstract int setClickedColor();

    private View.OnClickListener mTabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos = (int) view.getTag();
            final int size=TAB_BEANS.size();
            for (int i=0;i<size;i++){
                TabLayout.Tab tab=tabLayout.getTabAt(i);
                if (tab!=null){
                    View tabCustomView= tab.getCustomView();
                    if (tabCustomView!=null){
                        IconTextView iconTextView=tabCustomView.findViewById(R.id.icon_bottom_item);
                        AppCompatTextView textView=tabCustomView.findViewById(R.id.tv_bottom_item);
                        if (i==pos){
                            //如果是选中的,着色
                            iconTextView.setTextColor(mClickedColor);
                            textView.setTextColor(mClickedColor);
                        }else {
                            iconTextView.setTextColor(Color.GRAY);
                            textView.setTextColor(Color.GRAY);
                        }
                    }
                }
            }
        }
    };

    @Override
    public Object setLayout() {
        return R.layout.fragment_base_bottom_tab;
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
        for (Map.Entry<TabItemBean, BaseFragment> item : ITEMS.entrySet()) {
            final TabItemBean tabItemBean = item.getKey();
            final BaseFragment proxyFragment = item.getValue();
            TAB_BEANS.add(tabItemBean);
            ITEM_Fragments.add(proxyFragment);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final int size = ITEMS.size();
        if (setIndexFragment() >= 0 && setIndexFragment() < size) {
            mIndexFragment = setIndexFragment();
        }

        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager(), ITEM_Fragments);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        final int tabCount=tabLayout.getTabCount();
        for (int i=0;i<tabCount;i++){
            TabLayout.Tab tab=tabLayout.getTabAt(i);
            if (tab!=null){
                tab.setCustomView(pagerAdapter.getTabView(i));//对应tab定制view
                final View tabCustomView=tab.getCustomView();
                if ( tabCustomView!= null){
                    if (i == mIndexFragment){
                        IconTextView iconTextView=tabCustomView.findViewById(R.id.icon_bottom_item);
                        AppCompatTextView textView=tabCustomView.findViewById(R.id.tv_bottom_item);
                        iconTextView.setTextColor(mClickedColor);
                        textView.setTextColor(mClickedColor);
                    }
                    View tabView= (View) tabCustomView.getParent(); //获得tab对应父item
                    tabView.setTag(i); // 父item放入标记值
                    tabView.setOnClickListener(mTabOnClickListener);
                }
            }
        }
        viewPager.setCurrentItem(mIndexFragment);
    }


    private final class PagerAdapter extends FragmentPagerAdapter {

        List<BaseFragment> mFragmentList;

        public PagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
            super(fm);
            this.mFragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }

        public View getTabView(int position){
            View view= LayoutInflater.from(getContext()).inflate(R.layout.tab_item_icon_text_layout,null);
            IconTextView iconTextView=view.findViewById(R.id.icon_bottom_item);
            AppCompatTextView textView=view.findViewById(R.id.tv_bottom_item);
            final TabItemBean bean=TAB_BEANS.get(position);
            iconTextView.setText(bean.getIcon());
            textView.setText(bean.getTitle());
            return view;
        }
    }


}
