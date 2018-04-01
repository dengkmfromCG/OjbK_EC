package com.gdut.dkmfromcg.ojbk_ec.fragments.shopping;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.Toast;

import com.gdut.dkmfromcg.ojbk_ec.R;
import com.gdut.dkmfromcg.ojbk_ui.recycler.data.MultipleItemEntity;
import com.gdut.dkmfromcg.ojkb.fragments.ProxyFragment;
import com.gdut.dkmfromcg.ojkb.net.RestClient;
import com.gdut.dkmfromcg.ojkb.net.callback.RequestCallback;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingFragment extends ProxyFragment {


    @BindView(R.id.tv_shop_car_edit)
    AppCompatTextView tvShopCarEdit;
    @BindView(R.id.rv_shop_car)
    RecyclerView rvShopCar;
    @BindView(R.id.stub_no_item)
    ViewStubCompat stubNoItem;
    @BindView(R.id.icon_shop_cart_select_all)
    IconTextView iconShopCartSelectAll;
    @BindView(R.id.tv_shop_cart_total_price)
    AppCompatTextView tvShopCartTotalPrice;
    @BindView(R.id.tv_shop_cart_pay)
    AppCompatTextView tvShopCartPay;
    @BindView(R.id.stub_shop_cart_edit)
    ViewStubCompat stubShopCartEdit;

    private final int ALL_SELECTED = 1;
    private final int ALL_NOT_SELECTED = 0;

    private ShopCartAdapter mAdapter = null;
    private int mCurrentCount = 0;
    private int mTotalCount = 0;
    private double mTotalPrice = 0.00;

    @Override
    public Object setLayout() {
        return R.layout.fragment_shopping;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        iconShopCartSelectAll.setTag(ALL_NOT_SELECTED);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url(" https://www.easy-mock.com/mock/5aafcf3eaf4e6c5740e46244/ojbkec/shopcart")
                .build()
                .get(new RequestCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        final ArrayList<MultipleItemEntity> data =
                                new ShopCartDataConverter()
                                        .setJsonData(response)
                                        .convert();
                        mAdapter = new ShopCartAdapter(data, getContext());
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        rvShopCar.setLayoutManager(manager);
                        rvShopCar.setAdapter(mAdapter);
                        mTotalPrice = mAdapter.getTotalPrice();
                        tvShopCartTotalPrice.setText(String.valueOf(mTotalPrice));
                        checkItemCount();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void addSubscription(Subscription subscription) {

                    }
                });
    }


    @OnClick(R.id.tv_shop_car_edit)
    public void onTvShopCarEditClicked() {
        @SuppressLint("RestrictedApi") final View stubView = stubShopCartEdit.inflate();
        final AppCompatTextView tvDelete = stubView.findViewById(R.id.tv_delete);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<MultipleItemEntity> data = mAdapter.getData();
                //要删除的数据
                final List<MultipleItemEntity> deleteEntities = new ArrayList<>();
                for (MultipleItemEntity entity : data) {
                    final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
                    if (isSelected) {
                        deleteEntities.add(entity);
                    }
                }
                for (MultipleItemEntity entity : deleteEntities) {
                    int removePosition;
                    final int entityPosition = entity.getField(ShopCartItemFields.POSITION);
                    if (entityPosition > mCurrentCount - 1) {
                        removePosition = entityPosition - (mTotalCount - mCurrentCount);
                    } else {
                        removePosition = entityPosition;
                    }
                    if (removePosition <= mAdapter.getItemCount()) {
                        mAdapter.remove(removePosition);
                        mCurrentCount = mAdapter.getItemCount();
                        //更新数据
                        mAdapter.notifyItemRangeChanged(removePosition, mAdapter.getItemCount());
                    }
                }
                checkItemCount();

            }
        });
    }

    @OnClick(R.id.icon_shop_cart_select_all)
    public void onIconShopCartSelectAllClicked() {
        final int tag = (int) iconShopCartSelectAll.getTag();
        final int itemCount = mAdapter.getItemCount();
        if (tag == ALL_NOT_SELECTED) {
            iconShopCartSelectAll.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
            iconShopCartSelectAll.setTag(ALL_SELECTED);
            mAdapter.setIsSelectedAll(true);
            //更新RecyclerView显示数据
            mAdapter.notifyItemRangeChanged(0, itemCount);
        } else {
            iconShopCartSelectAll.setTextColor(Color.GRAY);
            iconShopCartSelectAll.setTag(ALL_NOT_SELECTED);
            mAdapter.setIsSelectedAll(false);
            //更新RecyclerView显示数据
            mAdapter.notifyItemRangeChanged(0, itemCount);
        }

    }

    @OnClick(R.id.tv_shop_cart_pay)
    public void onTvShopCartPayClicked() {

    }

    @SuppressWarnings("RestrictedApi")
    private void checkItemCount() {
        final int count = mAdapter.getItemCount();
        if (count == 0) {
            final View stubView = stubNoItem.inflate();
            final AppCompatTextView tvToBuy =
                    (AppCompatTextView) stubView.findViewById(R.id.tv_stub_to_buy);
            tvToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "你该购物啦！", Toast.LENGTH_SHORT).show();
                }
            });
            rvShopCar.setVisibility(View.GONE);
        } else {
            rvShopCar.setVisibility(View.VISIBLE);
        }
    }
}
