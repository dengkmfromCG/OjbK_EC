package com.gdut.dkmfromcg.shopcartpage.router;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gdut.dkmfromcg.commonlib.fragments.BaseFragment;
import com.gdut.dkmfromcg.commonlib.router.RouterPath;
import com.gdut.dkmfromcg.commonlib.router.IShopCartModule;
import com.gdut.dkmfromcg.shopcartpage.ShoppingFragment;

/**
 * Created by dkmFromCG on 2018/4/29.
 * function:
 */
@Route(path = RouterPath.SHOPPING_MODULE)
public class ShopCartModuleImpl implements IShopCartModule {
    @Override
    public BaseFragment getShopCartFragment() {
        return new ShoppingFragment();
    }

    @Override
    public void init(Context context) {

    }
}
