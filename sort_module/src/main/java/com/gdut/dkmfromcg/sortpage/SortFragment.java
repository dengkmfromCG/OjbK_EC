package com.gdut.dkmfromcg.sortpage;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.gdut.dkmfromcg.commonlib.fragments.ProxyFragment;
import com.gdut.dkmfromcg.commonlib.net.RestClient;
import com.gdut.dkmfromcg.commonlib.net.callback.RequestCallback;
import com.gdut.dkmfromcg.commonlib.recyclerview.data.MultipleFields;
import com.gdut.dkmfromcg.commonlib.recyclerview.data.MultipleItemEntity;
import com.gdut.dkmfromcg.sortpage.model.SortDataConverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;
import rx.Subscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortFragment extends ProxyFragment {


    VerticalTabLayout tabSortsMenu;
    VerticalViewPager vpSortContent;

    private Context mContext = null;
    private final int LOAD_SUCCESS = 1;
    private List<MultipleItemEntity> mData = null;
    private List<Fragment> fragments = new ArrayList<>();
/*    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_SUCCESS:
                    tabSortsMenu.setTabAdapter(new TabAdapter() {
                        @Override
                        public int getCount() {
                            return mData.size();
                        }

                        @Override
                        public ITabView.TabBadge getBadge(int position) {
                            return null;
                        }

                        @Override
                        public ITabView.TabIcon getIcon(int position) {
                            return null;
                        }

                        @Override
                        public ITabView.TabTitle getTitle(int position) {
                            final String tabTitle=mData.get(position).getField(MultipleFields.TEXT);
                            int selectColor=ContextCompat.getColor(mContext,R.color.app_main);
                            int normalColor=ContextCompat.getColor(mContext,R.color.we_chat_black);
                            return new ITabView.TabTitle.Builder().setContent(tabTitle).setTextColor(selectColor,normalColor).build();
                        }

                        @Override
                        public int getBackground(int position) {
                            return 0;
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };*/

    @Override
    public Object setLayout() {
        return R.layout.fragment_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mContext = getContext();
        tabSortsMenu=rootView.findViewById(R.id.tab_sorts_menu);
        vpSortContent=rootView.findViewById(R.id.vp_sort_content);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

/*        new Thread(new Runnable() {
            @Override
            public void run() {
                mData = new VerticalListDataConverter().setJsonData(getLocalJson()).convert();
                final Message message = new Message();
                message.what = LOAD_SUCCESS;
                handler.sendMessage(message);

            }
        }).start();*/
        RestClient.builder()
                .url(" https://www.easy-mock.com/mock/5aafcf3eaf4e6c5740e46244/ojbkec/sort")
                .build()
                .get(new RequestCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        mData = new SortDataConverter().setJsonData(response).convert();
                        tabSortsMenu.setTabAdapter(new TabAdapter() {
                            @Override
                            public int getCount() {
                                return mData.size();
                            }

                            @Override
                            public ITabView.TabBadge getBadge(int position) {
                                return null;
                            }

                            @Override
                            public ITabView.TabIcon getIcon(int position) {
                                return null;
                            }

                            @Override
                            public ITabView.TabTitle getTitle(int position) {
                                final String tabTitle = mData.get(position).getField(MultipleFields.TEXT);
                                int selectColor = ContextCompat.getColor(mContext, R.color.app_main);
                                int normalColor = ContextCompat.getColor(mContext, R.color.we_chat_black);
                                return new ITabView.TabTitle.Builder().setContent(tabTitle).setTextColor(selectColor, normalColor).build();
                            }

                            @Override
                            public int getBackground(int position) {
                                return 0;
                            }
                        });

                        for (MultipleItemEntity entity : mData) {
                            final JSONArray category_list = entity.getField(MultipleFields.CATEGORY_LIST);
                            final ContentFragment fragment = ContentFragment.getInstance(category_list);
                            fragments.add(fragment);
                        }
                        final MViewPagerAdapter vpAdapter = new MViewPagerAdapter(
                                getActivity().getSupportFragmentManager(), fragments);
                        vpSortContent.setAdapter(vpAdapter);

                        tabSortsMenu.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
                            @Override
                            public void onTabSelected(TabView tab, int position) {
                                vpSortContent.setCurrentItem(position);
                            }

                            @Override
                            public void onTabReselected(TabView tab, int position) {

                            }
                        });
                        vpSortContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                position = position % mData.size();
                                tabSortsMenu.setTabSelected(position);
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void addSubscription(Subscription subscription) {

                    }
                });

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


    private class MViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;

        public MViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mFragments.get(position);
        }

        @Override
        public int getCount() {
            return this.mFragments.size();
        }
    }

}
