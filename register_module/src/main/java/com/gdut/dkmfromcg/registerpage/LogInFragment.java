package com.gdut.dkmfromcg.registerpage;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.gdut.dkmfromcg.commonlib.app.sign.ISignListener;
import com.gdut.dkmfromcg.commonlib.fragments.ProxyFragment;
import com.gdut.dkmfromcg.commonlib.util.animator.RxAnimationTool;
/*import com.gdut.dkmfromcg.okjbec.wechat.DkmWeChat;
import com.gdut.dkmfromcg.okjbec.wechat.callbacks.IWeChatSignInCallback;*/

/**
 * A simple {@link Fragment} subclass.
 */
public class LogInFragment extends ProxyFragment {

    AppCompatImageView mLogo = null;
    TextInputEditText etMobile = null;
    AppCompatImageView ivCleanPhone = null;

    TextInputEditText etPassword = null;
    AppCompatImageView cleanPassword = null;
    AppCompatImageView ivShowPwd = null;

    AppCompatButton btnLogin = null;

    AppCompatTextView regist = null;
    AppCompatTextView forgetPassword = null;

    LinearLayoutCompat content = null;
    NestedScrollView scrollView = null;
    AppCompatTextView tryUse = null;

    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度


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
        return R.layout.fragment_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mLogo = rootView.findViewById(R.id.logo);
        etMobile = rootView.findViewById(R.id.et_mobile);
        ivCleanPhone = rootView.findViewById(R.id.iv_clean_phone);
        etPassword = rootView.findViewById(R.id.et_password);
        cleanPassword = rootView.findViewById(R.id.clean_password);
        ivShowPwd = rootView.findViewById(R.id.iv_show_pwd);
        btnLogin = rootView.findViewById(R.id.btn_login);
        regist = rootView.findViewById(R.id.regist);
        forgetPassword = rootView.findViewById(R.id.forget_password);
        content = rootView.findViewById(R.id.content);
        scrollView = rootView.findViewById(R.id.scroll);
        tryUse = rootView.findViewById(R.id.try_use);
        initEvent();
        initView();
    }

    private void initEvent() {
        etMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivCleanPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etMobile.setText("");
            }
        });
        etPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cleanPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPassword.setText("");
            }
        });
        ivShowPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivShowPwd.setImageResource(R.mipmap.pass_visuable);
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivShowPwd.setImageResource(R.mipmap.pass_gone);
                }
                String pwd = etPassword.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    etPassword.setSelection(pwd.length());
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm()) {
                    //请求网络, post用户数据,与服务器的用户数据库比较,如果存在则说明注册过了,
            /*RestClient.builder()
                    .url("the url where you save users' information... ")
                    .params("phone",etMobile.getText().toString())
                    .params("password",etPassword.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            //请求成功后,登录成功
                            SignHandler.onSignIn(response,mISignListener);
                        }
                    })
                    .build()
                    .post();*/
                    mISignListener.onSignInSuccess();
                }
            }
        });
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportDelegate().start(new RegisterFragment());
            }
        });
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        /*游客试用*/
        tryUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private void initView() {
        screenHeight = getContext().getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3


        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && ivCleanPhone.getVisibility() == View.INVISIBLE) {
                    ivCleanPhone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    ivCleanPhone.setVisibility(View.INVISIBLE);
                }
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && cleanPassword.getVisibility() == View.GONE) {
                    cleanPassword.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    cleanPassword.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(getContext(), "请输入数字或字母", Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    etPassword.setSelection(s.length());
                }
            }
        });

        /**
         * 禁止键盘弹起的时候可以滚动
         */
//        scrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
        scrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("wenzhihao", "up------>" + (oldBottom - bottom));
                    int dist = content.getBottom() - bottom;
                    if (dist > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(content, "translationY", 0.0f, -dist);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        RxAnimationTool.zoomIn(mLogo, 0.6f, dist);
                    }
                    tryUse.setVisibility(View.INVISIBLE);

                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("wenzhihao", "down------>" + (bottom - oldBottom));
                    if ((content.getBottom() - oldBottom) > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(content, "translationY", content.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                        RxAnimationTool.zoomOut(mLogo, 0.6f);
                    }
                    tryUse.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private boolean checkForm() {
        final String phone = etMobile.getText().toString();
        final String password = etPassword.getText().toString();

        boolean isPass = true;
        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            etMobile.setError("请输入正确号码");
            isPass = false;
        } else {
            etMobile.setError(null);
        }

        if (password.isEmpty() || password.length() < 0) {
            etPassword.setError("请至少填入6位数密码");
            isPass = false;
        } else {
            etPassword.setError(null);
        }
        return isPass;
    }

    //微信登录时,调用该方法...并且要传入 WeChat的appId和appSecret
    private void weChatSignIn() {
        /*DkmWeChat.getInstance().onSignSuccess(new IWeChatSignInCallback() {
            @Override
            public void onSignInSuccess(String userInfo) {
                //登陆成功的回调
                Logger.d("useInfo: ", userInfo);
            }
        }).signIn();//signIn方法内进行请求*/
    }

}
