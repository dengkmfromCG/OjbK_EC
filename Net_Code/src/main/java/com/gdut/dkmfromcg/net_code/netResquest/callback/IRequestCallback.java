package com.gdut.dkmfromcg.net_code.netResquest.callback;

import rx.Subscription;

/**
 * Created by dkmFromCG on 2018/3/29.
 * function:
 */

public interface IRequestCallback<T> {

    void onSuccess(T response);

    void onError(Throwable e);

    void addSubscription(Subscription subscription);
}
