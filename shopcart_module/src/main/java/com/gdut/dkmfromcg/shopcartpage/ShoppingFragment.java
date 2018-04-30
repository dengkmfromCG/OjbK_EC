package com.gdut.dkmfromcg.shopcartpage;


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

import com.alibaba.fastjson.JSON;
import com.gdut.dkmfromcg.commonlib.fragments.ProxyFragment;
import com.gdut.dkmfromcg.commonlib.net.RestClient;
import com.gdut.dkmfromcg.commonlib.net.callback.RequestCallback;
import com.gdut.dkmfromcg.commonlib.recyclerview.data.MultipleItemEntity;
import com.gdut.dkmfromcg.okjbec.pay.FastPay;
import com.gdut.dkmfromcg.okjbec.pay.IAlPayResultListener;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import rx.Subscription;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShoppingFragment extends ProxyFragment implements IAlPayResultListener {


    AppCompatTextView tvShopCarEdit;
    RecyclerView rvShopCar;
    ViewStubCompat stubNoItem;
    IconTextView iconShopCartSelectAll;
    AppCompatTextView tvShopCartTotalPrice;
    AppCompatTextView tvShopCartPay;
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
        tvShopCarEdit = rootView.findViewById(R.id.tv_shop_car_edit);
        rvShopCar = rootView.findViewById(R.id.rv_shop_car);
        stubNoItem = rootView.findViewById(R.id.stub_no_item);
        iconShopCartSelectAll = rootView.findViewById(R.id.icon_shop_cart_select_all);
        tvShopCartTotalPrice = rootView.findViewById(R.id.tv_shop_cart_total_price);
        tvShopCartPay = rootView.findViewById(R.id.tv_shop_cart_pay);
        stubShopCartEdit = rootView.findViewById(R.id.stub_shop_cart_edit);
        iconShopCartSelectAll.setTag(ALL_NOT_SELECTED);

        tvShopCarEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        iconShopCartSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        tvShopCartPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();
            }
        });
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

    //创建支付订单，注意创建订单和支付是两件事情
    private void createOrder() {
        final String orderUrl = "你的生成订单的API";
        //生成 订单JSON的参数
        final WeakHashMap<String, Object> orderParams = new WeakHashMap<>();
        //加入你的参数
        RestClient.builder()
                .url(orderUrl)
                .params(orderParams)
                .build()
                .post(new RequestCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        //进行具体的支付
                        final int orderId = JSON.parseObject(response).getInteger("result");
                        FastPay.create(getProxyActivity())
                                .setPayResultListener(ShoppingFragment.this)
                                .setOrderId(orderId)
                                .showPayDialog();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void addSubscription(Subscription subscription) {

                    }
                });

    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }
}
