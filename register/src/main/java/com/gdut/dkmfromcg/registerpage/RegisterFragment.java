package com.gdut.dkmfromcg.registerpage;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.View;

import com.gdut.dkmfromcg.ojkb.app.sign.ISignListener;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends ProxyFragment {

    TextInputEditText mName = null;
    TextInputEditText mEmail = null;
    TextInputEditText mPhone = null;
    TextInputEditText mPassword = null;
    TextInputEditText mRePassword = null;

    private ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.fragment_register;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mName = rootView.findViewById(R.id.edit_sign_up_name);
        mEmail = rootView.findViewById(R.id.edit_sign_up_email);
        mPhone = rootView.findViewById(R.id.edit_sign_up_phone);
        mPassword = rootView.findViewById(R.id.edit_sign_up_password);
        mRePassword = rootView.findViewById(R.id.edit_sign_up_re_password);
        rootView.findViewById(R.id.btn_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm()) {
                    //注册成功之后的操作,上传用户数据到服务器
            /*RestClient.builder()
                    .url("your url")
                    .params("name", mName.getText().toString())
                    .params("email", mEmail.getText().toString())
                    .params("phone", mPhone.getText().toString())
                    .params("password", mPassword.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            //your action
                            SignHandler.onRegister(response, mISignListener);
                        }
                    })
                    .build()
                    .post();*/
                    mISignListener.onRegisterSuccess();
                }
            }
        });
    }

    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String email = mEmail.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();

        boolean isPass = true;

        if (name.isEmpty()) {
            mName.setError("请输入姓名");
            isPass = false;
        } else {
            mName.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("手机号码错误");
            isPass = false;
        } else {
            mPhone.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        if (rePassword.isEmpty() || rePassword.length() < 6 || !(rePassword.equals(password))) {
            mRePassword.setError("密码验证错误");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }

        return isPass;
    }

}
