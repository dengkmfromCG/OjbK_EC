package com.gdut.dkmfromcg.ojkb.net;

import android.content.Context;

import com.gdut.dkmfromcg.ojbk_ui.progressing.Style;
import com.gdut.dkmfromcg.ojkb.net.callback.IError;
import com.gdut.dkmfromcg.ojkb.net.callback.IFailure;
import com.gdut.dkmfromcg.ojkb.net.callback.IRequest;
import com.gdut.dkmfromcg.ojkb.net.callback.ISuccess;
import com.gdut.dkmfromcg.ojkb.net.callback.RequestCallbacks;
import com.gdut.dkmfromcg.ojkb.net.download.DownloadHandler;
import com.gdut.dkmfromcg.ojkb.ui.loader.ProgressingLoader;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by dkmFromCG on 2018/3/12.
 * function:
 */

public final class RestClient {

    private final Context CONTEXT;
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final RequestBody BODY; //传递原始数据时,BODY 不为null
    private final File FILE;  //upload文件时,需要的参数
    private final Style PROGRESS_STYLE; //progress的样式,需要用到Context
    /*download时,需要用到的参数*/
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;



    RestClient(Context context,
               String url,
               WeakHashMap<String, Object> params,
               IRequest request,
               ISuccess success,
               IError error,
               IFailure failure,
               RequestBody body,
               File file,
               Style progressStyle,
               String downloadDir,
               String extension,
               String name
               ) {
        this.CONTEXT=context;
        this.URL = url;
        PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.ERROR = error;
        this.FAILURE = failure;
        this.BODY = body;
        this.FILE=file;
        this.PROGRESS_STYLE=progressStyle;
        this.DOWNLOAD_DIR=downloadDir;
        this.EXTENSION=extension;
        this.NAME=name;
    }


    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        if (BODY == null) {
            request(HttpMethod.POST);
        } else {
            if (!PARAMS.isEmpty()) { // post 原始数据时,params一定要为空
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAW);
        }
    }

    public final void put() {
        if (BODY == null) {
            request(HttpMethod.PUT);
        } else {
            if (!PARAMS.isEmpty()) { // put 原始数据时,params一定要为空
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload() {
        request(HttpMethod.UPLOAD);
    }

    public final void download() {
        new DownloadHandler(URL, REQUEST, DOWNLOAD_DIR, EXTENSION, NAME,
                SUCCESS, FAILURE, ERROR)
                .handleDownload();
    }

    private void request(HttpMethod method) {
        final RestService service = RestCreator.getRestService();
        Call<String> call = null;

        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }
        if (PROGRESS_STYLE!=null){
            ProgressingLoader.showLoading(CONTEXT,PROGRESS_STYLE);
        }


        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL,BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL,BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                call = service.upload(URL, body);
                break;
            default:
                break;
        }

        if (call!=null){
            //call.execute(); 是在主线程执行的
            call.enqueue(getRequestCallbacks()); //异步处理
        }

    }

    private Callback<String> getRequestCallbacks() {
        return new RequestCallbacks(REQUEST,SUCCESS,FAILURE,ERROR,PROGRESS_STYLE);
    }
}
