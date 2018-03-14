package com.gdut.dkmfromcg.ojkb.net.callback;

import android.os.Handler;

import com.gdut.dkmfromcg.ojbk_ui.progressing.Style;
import com.gdut.dkmfromcg.ojkb.ui.loader.ProgressingLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dkmFromCG on 2018/3/12.
 * function:
 */

public final class RequestCallbacks implements Callback<String> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final Style PROGRESS_STYLE;
    private static final Handler HANDLER =new Handler();//Handler声明成 static 类型可以避免内存泄漏

    public RequestCallbacks(IRequest request, ISuccess success, IFailure failure, IError error,Style progressStyle) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.PROGRESS_STYLE=progressStyle;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            if (call.isExecuted()) {
                if (SUCCESS != null) {
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if (ERROR != null) {
                ERROR.onError(response.code(), response.message());
            }
        }
        onRequestFinish();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null) {
            FAILURE.onFailure();
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }

        onRequestFinish();
    }

    private void onRequestFinish() {
        final long delayed =1000;
        if (PROGRESS_STYLE != null) {
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ProgressingLoader.stopLoading();
                }
            },delayed);
        }
    }
}
