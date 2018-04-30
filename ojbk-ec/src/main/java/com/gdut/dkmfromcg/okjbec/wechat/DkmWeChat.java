package com.gdut.dkmfromcg.okjbec.wechat;

import android.app.Activity;

import com.gdut.dkmfromcg.commonlib.util.config.ConfigType;
import com.gdut.dkmfromcg.commonlib.util.config.Configs;
import com.gdut.dkmfromcg.okjbec.wechat.callbacks.IWeChatSignInCallback;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by dkmFromCG on 2018/3/14.
 * function:
 */

public class DkmWeChat {
    public static final String APP_ID = Configs.getConfiguration(ConfigType.WE_CHAT_APP_ID);
    public static final String APP_SECRET = Configs.getConfiguration(ConfigType.WE_CHAT_APP_SECRET);

    private final IWXAPI WXAPI;
    private IWeChatSignInCallback mSignInCallback = null;

    /*单例模式*/
    private static final class Holder {
        private static final DkmWeChat INSTANCE = new DkmWeChat();
    }
    public static DkmWeChat getInstance() {
        return Holder.INSTANCE;
    }
    private DkmWeChat(){
        final Activity activity = Configs.getConfiguration(ConfigType.ACTIVITY);
        WXAPI = WXAPIFactory.createWXAPI(activity, APP_ID, true);
        WXAPI.registerApp(APP_ID);
    }

    public final IWXAPI getWXAPI() {
        return WXAPI;
    }

    public DkmWeChat onSignSuccess(IWeChatSignInCallback callback) {
        this.mSignInCallback = callback;
        return this;
    }

    public IWeChatSignInCallback getSignInCallback() {
        return mSignInCallback;
    }

    public final void signIn() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        WXAPI.sendReq(req);
    }
}
