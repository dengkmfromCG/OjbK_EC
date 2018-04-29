package com.gdut.dkmfromcg.shopcartpage.router;

import android.content.Context;

import com.gdut.dkmfromcg.ojkb.fragments.BaseFragment;
import com.gdut.dkmfromcg.ojkb.router.ShopCartModule;
import com.gdut.dkmfromcg.shopcartpage.ShoppingFragment;

/**
 * Created by dkmFromCG on 2018/4/29.
 * function:
 */

public class ShopCartModuleImpl implements ShopCartModule {
    @Override
    public BaseFragment getShopCartFragment() {
        return new ShoppingFragment();
    }

    @Override
    public void init(Context context) {

    }
}
