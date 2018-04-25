package com.gdut.dkmfromcg.okjbec.sign;

import com.gdut.dkmfromcg.ojkb.app.sign.AccountManager;

/**
 * Created by dkmFromCG on 2018/3/14.
 * function:
 */

public class SignHandler {

    public static void onSignIn(String response, ISignListener signListener) {
        //数据库操作



        //已经注册并登录成功了
        AccountManager.setAccountState(true);
        signListener.onSignInSuccess();
    }

    public static void onRegister(String response, ISignListener signListener) {
        //数据库操作

        //已经注册并登录成功了
        AccountManager.setAccountState(true);
        signListener.onRegisterSuccess();
    }
}
