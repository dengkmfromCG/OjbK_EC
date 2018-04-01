package com.gdut.dkmfromcg.ojkb.mvp;



import com.gdut.dkmfromcg.ojkb.mvp.datahandler.RxHandler;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by dkmFromCG on 2018/3/27.
 * function: 当Presenter请求Model完成后,避免了View层已经为null的情况,
 * 采用动态代理的方式,对View判空
 *
 */

public abstract class BasePresenter<V extends IView> {
    /**弱引用, 防止内存泄漏*/
    private WeakReference<V> weakReference;
    private V mProxyView;
    /**
     * 关联V层和P层
     */
    public void attatchView(V v) {
        weakReference = new WeakReference<>(v);
        MvpViewHandler viewHandler = new MvpViewHandler(weakReference.get());
        mProxyView = (V) Proxy.newProxyInstance(v.getClass().getClassLoader(), v.getClass().getInterfaces(), viewHandler);
        RxHandler.getInstance().register(this);
    }

    /**
     * @return P层和V层是否关联.
     */
    public boolean isViewAttached() {
        return weakReference != null && weakReference.get() != null;
    }

    /**
     * 断开V层和P层
     */
    public void detachView() {
        if (isViewAttached()) {
            weakReference.clear();
            weakReference = null;
        }
        RxHandler.getInstance().unRegister(this);
    }

    public V getView() {
        return mProxyView;
    }


    private class MvpViewHandler implements InvocationHandler {
        private IView mvpView;
        MvpViewHandler(IView mvpView) {
            this.mvpView = mvpView;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //如果V层没被销毁, 执行V层的方法.
            if (isViewAttached()) {
                return method.invoke(mvpView, args);
            }
            //P层不需要关注V层的返回值
            return null;
        }
    }
}
