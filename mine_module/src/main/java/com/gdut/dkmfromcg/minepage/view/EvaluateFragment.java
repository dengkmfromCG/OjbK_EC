package com.gdut.dkmfromcg.minepage.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdut.dkmfromcg.commonlib.fragments.ProxyFragment;
import com.gdut.dkmfromcg.commonlib.ui.autophoto.ActivityResultDelegate;
import com.gdut.dkmfromcg.commonlib.ui.autophoto.AutoPhotoView;
import com.gdut.dkmfromcg.minepage.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EvaluateFragment extends ProxyFragment {

    private AutoPhotoView autoPhotoView;
    private ActivityResultDelegate resultDelegate;

    @Override
    public Object setLayout() {
        return R.layout.fragment_evaluate;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        autoPhotoView=rootView.findViewById(R.id.auto_photo_view);
        resultDelegate=new ActivityResultDelegate(this,autoPhotoView);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        resultDelegate.onActivityResult(requestCode, resultCode, data);
    }
}
