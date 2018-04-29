package com.gdut.dkmfromcg.ojbk_ec;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gdut.dkmfromcg.ojkb.router.RouterPath;
import com.gdut.dkmfromcg.ojkb.util.router.RouterUtil;

/**
 * Created by dkmFromCG on 2018/4/27.
 * function:
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RouterUtil.navigation(RouterPath.LAUNCHER);
    }
}
