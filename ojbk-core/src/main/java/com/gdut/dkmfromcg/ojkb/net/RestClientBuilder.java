package com.gdut.dkmfromcg.ojkb.net;

import android.content.Context;

import com.gdut.dkmfromcg.ojbk_ui.progressing.Style;
import com.gdut.dkmfromcg.ojkb.net.callback.IError;
import com.gdut.dkmfromcg.ojkb.net.callback.IFailure;
import com.gdut.dkmfromcg.ojkb.net.callback.IRequest;
import com.gdut.dkmfromcg.ojkb.net.callback.ISuccess;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by dkmFromCG on 2018/3/12.
 * function:
 */

public final class RestClientBuilder {

    private Context mContext=null;
    private  String mUrl=null;
    private  static final WeakHashMap<String,Object> PARAMS=RestCreator.getParams();
    private IRequest mIRequest=null;
    private ISuccess mISuccess=null;
    private IError mIError=null;
    private IFailure mIFailure=null;
    private RequestBody mBody=null;
    private File mFile=null; //upload文件时,需要的参数
    private Style mProgressStyle=null;

    /*download时,需要用到的参数*/
    private String mDownloadDir = null;
    private String mExtension = null;
    private String mName = null;

    //不定义为public,使之不能在外部直接new出来
    RestClientBuilder(){}

    public final RestClientBuilder url(String url){
        this.mUrl=url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String,Object> params){
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key,Object value){
        PARAMS.put(key,value);
        return this;
    }

    public final RestClientBuilder file(File file){
        this.mFile=file;
        return this;
    }

    public final RestClientBuilder file(String file){
        this.mFile=new File(file);
        return this;
    }

    public final RestClientBuilder name(String name) {
        this.mName = name;
        return this;
    }

    public final RestClientBuilder dir(String dir) {
        this.mDownloadDir = dir;
        return this;
    }

    public final RestClientBuilder extension(String extension) {
        this.mExtension = extension;
        return this;
    }

    //传入获取的原始json数据
    public final RestClientBuilder raw(String raw) {
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder onRequest(IRequest iRequest) {
        this.mIRequest = iRequest;
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess) {
        this.mISuccess = iSuccess;
        return this;
    }

    public final RestClientBuilder failure(IFailure iFailure) {
        this.mIFailure = iFailure;
        return this;
    }

    public final RestClientBuilder error(IError iError) {
        this.mIError = iError;
        return this;
    }

    public final RestClientBuilder loader(Context context,Style progressStyle){
        this.mContext=context;
        this.mProgressStyle=progressStyle;
        return  this;
    }

    public final RestClientBuilder loader(Context context){
        this.mContext=context;
        this.mProgressStyle=Style.ROTATING_PLANE;
        return  this;
    }


    public final RestClient build(){
        return new RestClient(mContext,mUrl,PARAMS,mIRequest,mISuccess,mIError,
                mIFailure,mBody,mFile,mProgressStyle,mDownloadDir,mExtension,mName);
    }
}
