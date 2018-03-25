package com.gdut.dkmfromcg.ojkb.fragments.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gdut.dkmfromcg.ojkb.fragments.web.initImpl.IWebViewInitializer;
import com.gdut.dkmfromcg.ojkb.fragments.web.initImpl.WebChromeClientInitImpl;
import com.gdut.dkmfromcg.ojkb.fragments.web.initImpl.WebViewClientInitImpl;
import com.gdut.dkmfromcg.ojkb.fragments.web.initImpl.WebViewInitImpl;
import com.gdut.dkmfromcg.ojkb.fragments.web.route.RouteKeys;
import com.gdut.dkmfromcg.ojkb.fragments.web.route.Router;

/**
 * Created by dkmFromCG on 2018/3/25.
 * function: WebFragment的具体实现
 */

public class WebFragmentImpl extends WebFragment implements IWebViewInitializer {

    private String mUrl = null;
    private IPageLoadListener mIPageLoadListener = null;


    public static WebFragmentImpl create(String url){
        final Bundle args = new Bundle();
        args.putString(RouteKeys.URL.name(), url);
        final WebFragmentImpl webFragment=new WebFragmentImpl();
        webFragment.setArguments(args);
        return webFragment;
    }

    public void setPageLoadListener(IPageLoadListener listener) {
        this.mIPageLoadListener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mUrl = args.getString(RouteKeys.URL.name());
    }




    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        //加载 Url
        if (getUrl()!=null){
            Router.getInstance().loadPage(this,getUrl());
        }
    }

    public String getUrl() {
        if (mUrl == null) {
            throw new NullPointerException("Url IS NULL!");
        }
        return mUrl;
    }

    @Override
    protected IWebViewInitializer setIWebViewInitializer() {
        return this;
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitImpl().createWebView(webView);
    }

    @Override
    public WebViewClient initWebViewClient() {
        final WebViewClientInitImpl client = new WebViewClientInitImpl(this);
        client.setPageLoadListener(mIPageLoadListener);
        return client;
    }

    @Override
    public WebChromeClient initWebChromeClient() {
        return new WebChromeClientInitImpl();
    }
}
