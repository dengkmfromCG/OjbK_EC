package com.gdut.dkmfromcg.okjbec.wechat.templates;

import com.gdut.dkmfromcg.okjbec.wechat.BaseWXEntryActivity;
import com.gdut.dkmfromcg.okjbec.wechat.DkmWeChat;

/**
 * Created by dkmFromCG on 2018/3/14.
 * function:
 */

public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        DkmWeChat.getInstance().getSignInCallback().onSignInSuccess(userInfo);
    }
}
