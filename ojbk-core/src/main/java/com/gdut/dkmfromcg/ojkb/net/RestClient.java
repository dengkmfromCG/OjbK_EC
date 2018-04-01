package com.gdut.dkmfromcg.ojkb.net;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;


import com.gdut.dkmfromcg.ojkb.net.callback.RequestCallback;
import com.gdut.dkmfromcg.ojkb.net.download.SaveFileTask;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by dkmFromCG on 2018/3/12.
 * function:
 */

public final class RestClient {

    private final Context CONTEXT;
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestClientBuilderHelper.getInstance().getParams();
    private final RequestBody BODY; //传递原始数据时,BODY 不为 null
    private final File FILE;  //upload文件时,需要的参数
    /*download时,需要用到的参数*/
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;

    private final OkHttpClient OKHTTP_CLIENT;

    RestClient(Context context,
               String url,
               WeakHashMap<String, Object> params,
               RequestBody body,
               File file,
               String downloadDir,
               String extension,
               String name,
               OkHttpClient okHttpClient
    ) {
        this.CONTEXT = context;
        this.URL = url;
        PARAMS.putAll(params);
        this.BODY = body;
        this.FILE = file;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
        this.OKHTTP_CLIENT = okHttpClient;
    }


    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    public final Subscription get(RequestCallback<String> requestCallback) {
        return request(HttpMethod.GET,requestCallback);
    }

    public final Subscription post(RequestCallback<String> requestCallback) {
        Subscription subscription;
        if (BODY == null) {
            subscription = request(HttpMethod.POST,requestCallback);
        } else {
            if (!PARAMS.isEmpty()) { // post 原始数据时,params一定要为空
                throw new RuntimeException("params must be null!");
            }
            subscription = request(HttpMethod.POST_RAW,requestCallback);
        }
        return subscription;
    }

    public final Subscription put(RequestCallback<String> requestCallback) {
        Subscription subscription;
        if (BODY == null) {
            subscription = request(HttpMethod.PUT,requestCallback);
        } else {
            if (!PARAMS.isEmpty()) { // put 原始数据时,params一定要为空
                throw new RuntimeException("params must be null!");
            }
            subscription = request(HttpMethod.PUT_RAW,requestCallback);
        }
        return subscription;
    }

    public final Subscription delete(RequestCallback<String> requestCallback) {
        return request(HttpMethod.DELETE,requestCallback);
    }

    public final Subscription upload(RequestCallback<String> requestCallback) {
        return request(HttpMethod.UPLOAD,requestCallback);
    }

    public final Subscription download(final RequestCallback<String> requestCallback) {
        final Subscription subscription=
                RestServiceBuilder.getInstance(OKHTTP_CLIENT).getRestService()
                .download(URL,PARAMS)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (requestCallback!=null){
                            requestCallback.onRequestStart();
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        if (requestCallback != null) {
                            requestCallback.onRequestEnd();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (requestCallback != null) {
                            requestCallback.onError(e);
                        }
                        if (requestCallback != null) {
                            requestCallback.onRequestEnd();
                        }
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        final SaveFileTask task=new SaveFileTask(CONTEXT,requestCallback);
                        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                                DOWNLOAD_DIR,EXTENSION,responseBody,NAME);

                        if (task.isCancelled()){
                            if (requestCallback!=null){
                                requestCallback.onRequestEnd();
                            }
                        }
                        if (requestCallback != null) {
                            requestCallback.onRequestEnd();
                        }
                    }
                });

        if (requestCallback!=null){
            requestCallback.addSubscription(subscription);
        }
        return subscription;
    }

    private Subscription request(HttpMethod method, final RequestCallback<String> requestCallback) {
        final RestService service = RestServiceBuilder.getInstance(OKHTTP_CLIENT).getRestService();
        Observable<String> observable = null;

        switch (method) {
            case GET:
                observable = service.get(URL, PARAMS);
                break;
            case POST:
                observable = service.post(URL, PARAMS);
                break;
            case POST_RAW:
                observable = service.postRaw(URL, BODY);
                break;
            case PUT:
                observable = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                observable = service.putRaw(URL, BODY);
                break;
            case DELETE:
                observable = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                final RequestBody requestBody =
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), FILE);
                final MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", FILE.getName(), requestBody);
                observable = service.upload(URL, body);
                break;
            default:
                break;
        }
        Subscription subscription = null;
        if (observable != null) {
            subscription = observable
                    .subscribeOn(Schedulers.io())
                    //请求开始之前,在 UI线程 做界面操作
                    //doOnSubscribe() 执行在 subscribe() 发生的线程；
                    // 而如果在 doOnSubscribe() 之后有 subscribeOn() 的话，它将执行在离它最近的 subscribeOn() 所指定的线程。
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            if (requestCallback!=null){
                                requestCallback.onRequestStart();
                            }
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    //在 UI 线程 更新 UI
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {

                        @Override
                        public void onCompleted() {
                            if (requestCallback != null) {
                                requestCallback.onRequestEnd();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            if (requestCallback != null) {
                                requestCallback.onError(e);
                            }
                            if (requestCallback != null) {
                                requestCallback.onRequestEnd();
                            }
                        }


                        @Override
                        public void onNext(String s) {
                            if (requestCallback != null) {
                                requestCallback.onSuccess(s);
                            }
                            if (requestCallback != null) {
                                requestCallback.onRequestEnd();
                            }
                        }
                    });
        }
        if (requestCallback!=null){
            requestCallback.addSubscription(subscription);
        }
        return subscription;
    }


    //自定义 APIService
    public final <T> T createCustomService(final Class<T> customService,@NonNull final String baseUrl){
        final T service=CustomServiceBuilder.getInstance(OKHTTP_CLIENT).getRestService(customService,baseUrl);
        return service;
    }

    public final <T> Subscription call(Observable<T> observable, final RequestCallback<T> requestCallback){
        final Subscription subscription=
                observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        if (requestCallback!=null){
                            requestCallback.onRequestStart();
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onCompleted() {
                        if (requestCallback != null) {
                            requestCallback.onRequestEnd();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (requestCallback != null) {
                            requestCallback.onError(e);
                        }
                        if (requestCallback != null) {
                            requestCallback.onRequestEnd();
                        }
                    }

                    @Override
                    public void onNext(T t) {
                        if (requestCallback != null) {
                            requestCallback.onSuccess(t);
                        }
                        if (requestCallback != null) {
                            requestCallback.onRequestEnd();
                        }
                    }
                });
        if (requestCallback!=null){
            requestCallback.addSubscription(subscription);
        }
        return subscription;
    }
}
