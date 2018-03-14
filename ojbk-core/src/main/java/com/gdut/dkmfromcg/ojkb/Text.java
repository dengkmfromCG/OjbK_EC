package com.gdut.dkmfromcg.ojkb;

import com.gdut.dkmfromcg.ojkb.net.RestClient;
import com.gdut.dkmfromcg.ojkb.net.callback.IError;
import com.gdut.dkmfromcg.ojkb.net.callback.IFailure;
import com.gdut.dkmfromcg.ojkb.net.callback.IRequest;
import com.gdut.dkmfromcg.ojkb.net.callback.ISuccess;

/**
 * Created by dkmFromCG on 2018/3/12.
 * function:
 */

public class Text {

    public void text(){
        RestClient.builder()
                .url("https://www.baidu.com/")
                .params("","")
                .onRequest(new IRequest() {
                    @Override
                    public void onRequestStart() {
                        System.out.println("onRequestStart---------------------");
                    }

                    @Override
                    public void onRequestEnd() {
                        System.out.println("onRequestEnd---------------------");
                    }
                })
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        System.out.println(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        System.out.println("onFailure");
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String message) {
                        System.out.println("onError");
                    }
                })
                .build()
                .get();
    }
}
