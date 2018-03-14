package com.gdut.dkmfromcg.ojbk_ec.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gdut.dkmfromcg.ojbk_ec.R;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;

import com.gdut.dkmfromcg.ojkb.net.RestClient;
import com.gdut.dkmfromcg.ojkb.net.callback.IError;
import com.gdut.dkmfromcg.ojkb.net.callback.IFailure;
import com.gdut.dkmfromcg.ojkb.net.callback.IRequest;
import com.gdut.dkmfromcg.ojkb.net.callback.ISuccess;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExampleFragment extends ProxyFragment {

    private static final String TAG = "ExampleFragment";
    @Override
    public Object setLayout() {
        return R.layout.fragment_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        text();
    }

    private void text(){
        RestClient.builder()
                .url("http://192.168.0.189/index")
                .loader(getContext())
                .params("","")
                .onRequest(new IRequest() {
                    @Override
                    public void onRequestStart() {

                    }

                    @Override
                    public void onRequestEnd() {

                    }
                })
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String message) {

                    }
                })
                .build()
                .get();
    }


}
