package com.gdut.dkmfromcg.minepage.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gdut.dkmfromcg.commonlib.fragments.BaseFragment;
import com.gdut.dkmfromcg.commonlib.router.MineModule;
import com.gdut.dkmfromcg.commonlib.router.RouterPath;
import com.gdut.dkmfromcg.minepage.view.MineFragment;

/**
 * Created by dkmFromCG on 2018/4/30.
 * function:
 */
@Route(path = RouterPath.MINE_MODULE)
public class MineModuleImpl implements MineModule {
    @Override
    public BaseFragment getMineFragment() {
        return new MineFragment();
    }

    @Override
    public void init(Context context) {

    }
}
