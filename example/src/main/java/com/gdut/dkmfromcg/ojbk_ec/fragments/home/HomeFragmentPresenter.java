package com.gdut.dkmfromcg.ojbk_ec.fragments.home;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gdut.dkmfromcg.ojbk_ec.fragments.home.model.HomeDataConverter;
import com.gdut.dkmfromcg.ojbk_ec.fragments.home.model.PagingBean;
import com.gdut.dkmfromcg.ojbk_ui.recycler.data.DataConverter;
import com.gdut.dkmfromcg.ojkb.util.config.Configs;
import com.gdut.dkmfromcg.ojkb.net.RestClient;
import com.gdut.dkmfromcg.ojkb.net.callback.RequestCallback;

import rx.Subscription;

/**
 * Created by dkmFromCG on 2018/3/20.
 * function:
 */

public class HomeFragmentPresenter implements IContract.Presenter {

    private IContract.View mView;

    private final PagingBean BEAN;
    private final DataConverter CONVERTER;

    HomeFragmentPresenter(IContract.View view) {
        mView = view;
        BEAN = new PagingBean();
        CONVERTER = new HomeDataConverter();
    }

    @Override
    public void start() {

    }

    @Override
    public void destroy() {
        mView = null;
    }

    @Override
    public void firstPage() {
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url("https://www.easy-mock.com/mock/5aafcf3eaf4e6c5740e46244/ojbkec/homeAd")
                .build()
                .get(new RequestCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("total"))
                                .setPageSize(object.getInteger("page_size"));

                        mView.initAdapter(CONVERTER.setJsonData(response));

                        BEAN.addIndex();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void addSubscription(Subscription subscription) {

                    }
                });
    }

    @Override
    public void loadMore() {
        final int pageSize = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();
        final int index = BEAN.getPageIndex();//index 暂时还没用上
        if (mView.getAdapterDataSize() < pageSize || currentCount >= total) {
            mView.adapterLoadMoreEnd();
        } else {
            Configs.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestClient.builder()
                            .url("https://www.easy-mock.com/mock/5aafcf3eaf4e6c5740e46244/ojbkec/homeAd")
                            .build()
                            .get(new RequestCallback<String>() {
                                @Override
                                public void onSuccess(String response) {
                                    CONVERTER.clearData();
                                    mView.refreshAdapter(CONVERTER.setJsonData(response));
                                    //数量累加
                                    BEAN.setCurrentCount(mView.getAdapterDataSize());
                                    BEAN.addIndex();
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void addSubscription(Subscription subscription) {

                                }
                            });
                }
            }, BEAN.getDelayed());
        }
    }

    @Override
    public void refreshData() {
        Configs.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //刷新数据

                mView.stopRefresh();
            }
        }, BEAN.getDelayed());
    }


}
