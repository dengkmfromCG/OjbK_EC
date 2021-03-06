package com.gdut.dkmfromcg.sortpage.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gdut.dkmfromcg.commonlib.fragments.BaseFragment;
import com.gdut.dkmfromcg.commonlib.router.RouterPath;
import com.gdut.dkmfromcg.commonlib.router.ISortModule;
import com.gdut.dkmfromcg.sortpage.SortFragment;

/**
 * Created by dkmFromCG on 2018/4/29.
 * function:
 */
@Route(path = RouterPath.SORT_MODULE)
public class SortModuleImpl implements ISortModule {
    @Override
    public void init(Context context) {

    }

    @Override
    public BaseFragment getSortFragment() {
        return new SortFragment();
    }
}
