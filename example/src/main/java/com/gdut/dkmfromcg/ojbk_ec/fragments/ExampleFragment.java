package com.gdut.dkmfromcg.ojbk_ec.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.gdut.dkmfromcg.ojbk_ec.R;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExampleFragment extends ProxyFragment {

    private static final String TAG = "ExampleFragment";

    @Override
    public Object setLayout() {
        return R.layout.fragment_example;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        text();
    }

    private void text() {

    }


}
