package com.gdut.dkmfromcg.commonlib.net.callback;

/**
 * Created by dkmFromCG on 2018/3/29.
 * function:
 */

public abstract class RequestCallback<T> implements IRequestCallback<T> {

    //IRequest
    public void onRequestStart(){}
    public void onRequestEnd(){}

    //IProgress
    public void onProgress(float progress, long transfered, long total) {}
    public void onProgress(float progress, long speed, long transfered, long total) {}
}
