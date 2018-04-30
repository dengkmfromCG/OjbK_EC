package com.gdut.dkmfromcg.okjbec.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gdut.dkmfromcg.commonlib.net.RestClient;
import com.gdut.dkmfromcg.commonlib.net.callback.RequestCallback;
import com.gdut.dkmfromcg.commonlib.util.log.Logger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;

/**
 * Created by dkmFromCG on 2018/3/14.
 * function:
 */

public abstract class BaseWXEntryActivity extends BaseWXActivity {

    private final List<Subscription> SUBSCRIPTIONS=new ArrayList<>();
    //用户登录成功后回调
    protected abstract void onSignInSuccess(String userInfo);

    //BaseWXActivity实现IWXAPIEventHandler接口,微信发送的请求将回调到onReq方法
    @Override
    public void onReq(BaseReq baseReq) {
    }

    //BaseWXActivity实现IWXAPIEventHandler接口,发送到微信请求的响应结果将回调到onResp方法
    @Override
    public void onResp(BaseResp baseResp) {

        final String code = ((SendAuth.Resp) baseResp).code;
        final StringBuilder authUrl = new StringBuilder();
        authUrl
                .append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(DkmWeChat.APP_ID)
                .append("&secret=")
                .append(DkmWeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");

        Logger.d("authUrl", authUrl.toString());
        getAuth(authUrl.toString());
    }

    private void getAuth(String authUrl) {

        RestClient.builder()
                .url(authUrl)
                .build()
                .get(new RequestCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject authObj = JSON.parseObject(response);
                        final String accessToken = authObj.getString("access_token");
                        final String openId = authObj.getString("openid");

                        final StringBuilder userInfoUrl = new StringBuilder();
                        userInfoUrl
                                .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessToken)
                                .append("&openid=")
                                .append(openId)
                                .append("&lang=")
                                .append("zh_CN");//把用户信息以中文的形式进行返回

                        Logger.d("userInfoUrl", userInfoUrl.toString());
                        getUserInfo(userInfoUrl.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void addSubscription(Subscription subscription) {
                        SUBSCRIPTIONS.add(subscription);
                    }
                });
    }

    private void getUserInfo(String userInfoUrl) {
        RestClient.builder()
                .url(userInfoUrl)
                .build()
                .get(new RequestCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        //这个 response 就是用户信息
                        onSignInSuccess(response);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void addSubscription(Subscription subscription) {
                        SUBSCRIPTIONS.add(subscription);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Subscription s:SUBSCRIPTIONS){
            if (!s.isUnsubscribed()){
                s.unsubscribe();
            }
        }
        SUBSCRIPTIONS.clear();
    }
}
