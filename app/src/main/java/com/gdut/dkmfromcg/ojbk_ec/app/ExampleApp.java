package com.gdut.dkmfromcg.ojbk_ec.app;

import com.gdut.dkmfromcg.commonlib.app.BaseApp;
import com.gdut.dkmfromcg.commonlib.router.IDiscoverModule;
import com.gdut.dkmfromcg.commonlib.router.IHomeModule;
import com.gdut.dkmfromcg.commonlib.router.IMineModule;
import com.gdut.dkmfromcg.commonlib.router.IShopCartModule;
import com.gdut.dkmfromcg.commonlib.router.ISortModule;
import com.gdut.dkmfromcg.commonlib.router.RouterPath;
import com.gdut.dkmfromcg.commonlib.util.router.RouterUtil;

/**
 * Created by dkmFromCG on 2018/3/8.
 * function:
 */

public class ExampleApp extends BaseApp {


    private static IHomeModule homeModule = null;
    private static ISortModule sortModule = null;
    private static IDiscoverModule discoverModule = null;
    private static IShopCartModule shopCartModule = null;
    private static IMineModule mineModule = null;

    public static IHomeModule getHomeModule() {
        return homeModule;
    }

    public static ISortModule getSortModule() {
        return sortModule;
    }

    public static IDiscoverModule getDiscoverModule() {
        return discoverModule;
    }

    public static IShopCartModule getShopCartModule() {
        return shopCartModule;
    }

    public static IMineModule getMineModule() {
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

        /*homeModule = (HomeModule) ARouter.getInstance().build(RouterPath.HOME_MODULE).navigation();
        sortModule = (SortModule) ARouter.getInstance().build(RouterPath.SORT_MODULE).navigation();
        discoverModule = (DiscoverModule) ARouter.getInstance().build(RouterPath.DISCOVER_MODULE).navigation();
        shopCartModule = (ShopCartModule) ARouter.getInstance().build(RouterPath.SHOPPING_MODULE).navigation();
        mineModule = (MineModule) ARouter.getInstance().build(RouterPath.MINE_MODULE).navigation();*/
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
