package com.gdut.dkmfromcg.minepage.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gdut.dkmfromcg.commonlib.fragments.ProxyFragment;
import com.gdut.dkmfromcg.commonlib.ui.grouplist.GroupListAdapter;
import com.gdut.dkmfromcg.commonlib.ui.grouplist.GroupListType;
import com.gdut.dkmfromcg.commonlib.ui.grouplist.ListBean;
import com.gdut.dkmfromcg.commonlib.ui.grouplist.Section;
import com.gdut.dkmfromcg.commonlib.util.callback.CallbackManager;
import com.gdut.dkmfromcg.commonlib.util.callback.CallbackType;
import com.gdut.dkmfromcg.commonlib.util.callback.IGlobalCallback;
import com.gdut.dkmfromcg.commonlib.widget.CircleImageView;
import com.gdut.dkmfromcg.minepage.R;
import com.gdut.dkmfromcg.minepage.camera.CameraHelper;
import com.gdut.dkmfromcg.minepage.camera.CameraImageBean;
import com.gdut.dkmfromcg.minepage.camera.RequestCodes;
import com.gdut.dkmfromcg.minepage.presenter.MinePresenterImpl;
import com.yalantis.ucrop.UCrop;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends ProxyFragment implements IMineView {

    CircleImageView mIvCircle = null;

    MinePresenterImpl mPresenter;


    @Override
    public Object setLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mIvCircle=rootView.findViewById(R.id.img_user_avatar);
        mPresenter = new MinePresenterImpl();
        mPresenter.attatchView(this);
        initRv(rootView);
        mIvCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallbackManager.getInstance()
                        .addCallback(CallbackType.ON_CROP, new IGlobalCallback() {
                            @Override
                            public void executeCallback(@Nullable Object args) {
                                CameraHelper.start(MineFragment.this);
                            }
                        });
                startCameraWithCheck();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }



    private void initRv(View rootView) {
        Section section = new Section("第一个模块");
        ListBean address = new ListBean.Builder()
                .setItemType(GroupListType.ARROW_ITEM)
                .setTitle("收货地址")
                .build();
        ListBean settings = new ListBean.Builder()
                .setItemType(GroupListType.ARROW_ITEM)
                .setTitle("系统设置")
                .build();
        final List<MultiItemEntity> entityList = new ArrayList<>();
        entityList.add(section);
        entityList.add(address);
        entityList.add(settings);
        final GroupListAdapter adapter = new GroupListAdapter(entityList);
        final RecyclerView recyclerView = rootView.findViewById(R.id.rv_personal_setting);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getPath();
                    UCrop.of(resultUri, resultUri)
                            .withMaxResultSize(400, 400)
                            .start(getContext(), this);
                    break;
                case RequestCodes.PICK_PHOTO:
                    if (data != null) {
                        final Uri pickPath = data.getData();
                        //从相册选择后需要有个路径存放剪裁过的图片
                        final String pickCropPath = CameraHelper.createCropFile().getPath();
                        UCrop.of(pickPath, Uri.parse(pickCropPath))
                                .withMaxResultSize(400, 400)
                                .start(getContext(), this);
                    }
                    break;
                case RequestCodes.CROP_PHOTO:
                    final Uri cropUri = UCrop.getOutput(data);
                    //拿到剪裁后的数据进行处理
                    Glide.with(getContext())
                            .load(cropUri)
                            .into(mIvCircle);
                    break;
                case RequestCodes.CROP_ERROR:
                    Toast.makeText(getContext(), "剪裁出错", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}
