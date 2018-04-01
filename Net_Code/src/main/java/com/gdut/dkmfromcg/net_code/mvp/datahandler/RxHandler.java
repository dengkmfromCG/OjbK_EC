package com.gdut.dkmfromcg.net_code.mvp.datahandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by dkmFromCG on 2018/3/27.
 * function:
 */

public class RxHandler {
    private static final String TAG = "RxHandler";
    // 订阅者集合
    private Set<Object> subscribers;

    /**
     *  注册 DataBusSubscriber
     * @param subscriber 观察者,传入Presenter...当Model层返回数据时,Presenter作为观察者执行操作
     */
    public synchronized void register(Object subscriber) {
        subscribers.add(subscriber);
    }


    /**
     *  注销 DataBusSubscriber
     * @param subscriber  传入Presenter
     */
    public synchronized void unRegister(Object subscriber) {
        subscribers.remove(subscriber);
    }

    /**
     * 单例模式
     */
    private static volatile RxHandler instance;
    private RxHandler() {
        subscribers = new CopyOnWriteArraySet<>();
    }
    public static synchronized RxHandler getInstance() {

        if (instance == null) {
            synchronized (RxHandler.class) {
                if (instance == null) {
                    instance = new RxHandler();
                }

            }
        }
        return instance;
    }

    /**
     * 包装处理过程
     * @param func
     */
    public void chainProcess(Func1 func) {
        Observable.just("")
                .subscribeOn(Schedulers.io()) // 指定处理过程在 IO 线程
                .map(func)   // 包装处理过程
                .observeOn(AndroidSchedulers.mainThread())  // 指定事件消费在 Main 线程
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object data) {
                        if (data == null) {
                            return;
                        }
                        send(data);
                    }
                });
    }

    /**
     * 发送数据
     * @param data
     */
    public void send(Object data) {
        for (Object subscriber : subscribers) {
            // 扫描注解，将数据发送到注册的对象的标记方法
            callMethodByAnnotiation(subscriber, data);
        }
    }


    /**
     * 反射获取对象方法列表，判断：
     * 1 是否被注解修饰
     * 2 参数类型是否和 data 类型一致
     * @param target
     * @param data
     */

    private void callMethodByAnnotiation(Object target, Object data) {
        Method[] methodArray = target.getClass().getDeclaredMethods();
        for (int i = 0; i < methodArray.length; i++) {
            try {
                if (methodArray[i].isAnnotationPresent(HandleMessage.class)) {
                    // 被 @RegisterBus 修饰的方法
                    Class paramType = methodArray[i].getParameterTypes()[0];
                    if (data.getClass().getName().equals(paramType.getName())) {
                        // 参数类型和 data 一样，调用此方法
                        methodArray[i].invoke(target, new Object[]{data});
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
