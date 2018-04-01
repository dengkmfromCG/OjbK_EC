package com.gdut.dkmfromcg.net_code.mvptext;

/**
 * Created by dkmFromCG on 2018/3/27.
 * function:
 */


import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.gdut.dkmfromcg.net_code.mvp.BasePresenter;
import com.gdut.dkmfromcg.net_code.mvp.datahandler.HandleMessage;
import com.gdut.dkmfromcg.net_code.mvp.datahandler.RxHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.functions.Func1;

/**
 * 模拟 View
 */

public class MvpView implements IMvpView{
    private static final String TAG = "MvpView";
    MvpPresenterImpl presenter;

    //判断用户是否登录
    boolean accountIsSignIn=false;

    @Before
    public void onCreate() throws Exception{
        final IMvpModel model=new MvpModelImpl();
        presenter=new MvpPresenterImpl(model);
        presenter.attatchView(this);
        Log.d(TAG, "onCreate: 用户默认是否登录"+accountIsSignIn);
    }

    /***
     * 模拟 View交互
     */
    @Test
    public void askIsSignIn(){
        presenter.askIsSignIn();
    }

    @After
    public void onDestroy() throws Exception{
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        presenter.detachView();
    }

    @Override
    public void changAccountSignInState(boolean isSignIn) {
        accountIsSignIn=isSignIn;
        Log.d(TAG, "changAccountSignInState: 用户是否登录"+accountIsSignIn);
    }
}

/**
 * 模拟 Presenter
 */
class MvpPresenterImpl extends BasePresenter<IMvpView> implements IMvpPresenter{

    //MvpPresenter 继承自BasePresenter,并传入 IMvpView泛型,已存在 IMvpView成员变量
    IMvpModel mModel;

    public MvpPresenterImpl(IMvpModel model){
        this.mModel=model;
    }


    @Override
    public void askIsSignIn() {
        mModel.searchCache();
    }

    @HandleMessage
    public void afterAskAccountIsSignIn(boolean isSign){
        getView().changAccountSignInState(isSign);
    }
}

/**
 * 模拟 Model
 */
class MvpModelImpl implements IMvpModel{

    @Override
    public void searchCache() {

        //Func1() 是在IO线程操作的
        RxHandler.getInstance().chainProcess(new Func1() {
            @Override
            public Object call(Object o) {
                boolean isSignIn=false;
                /**
                 * 网络请求或者查询数据库等耗时操作,查询完成后,重新赋值
                 */
                isSignIn=true;
                return isSignIn;
            }
        });
    }
}

