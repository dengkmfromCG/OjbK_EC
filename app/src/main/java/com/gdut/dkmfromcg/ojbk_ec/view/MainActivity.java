package com.gdut.dkmfromcg.ojbk_ec.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.gdut.dkmfromcg.commonlib.activities.ProxyActivity;
import com.gdut.dkmfromcg.commonlib.fragments.ProxyFragment;
import com.gdut.dkmfromcg.commonlib.router.RouterPath;
import com.gdut.dkmfromcg.commonlib.util.log.Logger;
import com.gdut.dkmfromcg.commonlib.util.router.RouterUtil;
import com.gdut.dkmfromcg.ojbk_ec.R;
import com.gdut.dkmfromcg.ojbk_ec.app.ExampleApp;

/**
 * Created by dkmFromCG on 2018/4/27.
 * function:
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RouterUtil.navigation(RouterPath.LAUNCHER);
        finish();
    }
}
