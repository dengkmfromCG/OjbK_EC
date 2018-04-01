package com.gdut.dkmfromcg.okjbec.pay;

/**
 * Created by dkmFromCG on 2018/4/1.
 * function: 支付结果回调
 */

public interface IAlPayResultListener {

    void onPaySuccess();

    void onPaying();

    void onPayFail();

    void onPayCancel();

    void onPayConnectError();
}
