package com.gdut.dkmfromcg.homepage;


import com.gdut.dkmfromcg.commonlib.recyclerview.data.DataConverter;

/**
 * Created by dkmFromCG on 2018/3/20.
 * function:
 */

public interface IContract {

    interface View{
        void initAdapter(DataConverter converter);
        int getAdapterDataSize();
        void refreshAdapter(DataConverter converter);
        void adapterLoadMoreEnd();
        void stopRefresh();
    }

    interface Presenter{
        /**
         * 调用该方法表示presenter被激活了
         */
        void start();

        /**
         * 调用此方法表示presenter要结束了
         * 其目的是为了接触相互持有导致的内存泄露
         */
        void destroy();

        void firstPage();

        void loadMore();

        void refreshData();
    }

}
