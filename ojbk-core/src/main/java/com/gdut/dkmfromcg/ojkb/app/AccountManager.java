package com.gdut.dkmfromcg.ojkb.app;

import com.gdut.dkmfromcg.ojkb.util.storage.PreferenceTool;

/**
 * Created by dkmFromCG on 2018/3/14.
 * function:
 */

public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    //保存用户登录状态，登录后调用
    public static void setAccountState(boolean state){
        PreferenceTool.setAppFlag(SignTag.SIGN_TAG.name(),state);
    }

    private static boolean isSignIn() {
        return PreferenceTool.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker){
        if (isSignIn()){
            checker.onSingIn();
        }else {
            checker.onNotSingIn();
        }
    }
}
