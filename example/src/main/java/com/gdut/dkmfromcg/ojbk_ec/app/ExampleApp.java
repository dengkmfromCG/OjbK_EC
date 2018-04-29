package com.gdut.dkmfromcg.ojbk_ec.app;

import com.gdut.dkmfromcg.ojkb.app.BaseApp;
import com.gdut.dkmfromcg.ojkb.router.DiscoverModule;
import com.gdut.dkmfromcg.ojkb.router.HomeModule;
import com.gdut.dkmfromcg.ojkb.router.MineModule;
import com.gdut.dkmfromcg.ojkb.router.RouterPath;
import com.gdut.dkmfromcg.ojkb.router.ShopCartModule;
import com.gdut.dkmfromcg.ojkb.router.SortModule;
import com.gdut.dkmfromcg.ojkb.util.router.RouterUtil;

/**
 * Created by dkmFromCG on 2018/3/8.
 * function:
 */

public class ExampleApp extends BaseApp {

    private static HomeModule homeModule = null;
    private static SortModule sortModule = null;
    private static DiscoverModule discoverModule = null;
    private static ShopCartModule shopCartModule = null;
    private static MineModule mineModule = null;

    public static HomeModule getHomeModule() {
        return homeModule;
    }

    public static SortModule getSortModule() {
        return sortModule;
    }

    public static DiscoverModule getDiscoverModule() {
        return discoverModule;
    }

    public static ShopCartModule getShopCartModule() {
        return shopCartModule;
    }

    public static MineModule getMineModule() {
        return mineModule;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        homeModule = RouterUtil.navigation(RouterPath.HOME_MODULE);
        sortModule = RouterUtil.navigation(RouterPath.SORT_MODULE);
        discoverModule = RouterUtil.navigation(RouterPath.DISCOVER_MODULE);
        shopCartModule = RouterUtil.navigation(RouterPath.SHOPPING_MODULE);
        mineModule = RouterUtil.navigation(RouterPath.MINE_MODULE);
    }

    @Override
    public void onTerminate() {
        homeModule = null;
        sortModule = null;
        discoverModule = null;
        shopCartModule = null;
        mineModule = null;
        super.onTerminate();
    }
}
