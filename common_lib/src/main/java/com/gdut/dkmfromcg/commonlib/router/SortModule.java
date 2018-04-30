package com.gdut.dkmfromcg.commonlib.router;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.gdut.dkmfromcg.commonlib.fragments.BaseFragment;

/**
 * Created by dkmFromCG on 2018/4/29.
 * function:
 */

public interface SortModule extends IProvider{

    BaseFragment getSortFragment();
}
