package com.gdut.dkmfromcg.sortpage.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gdut.dkmfromcg.commonlib.recyclerview.data.ItemType;
import com.gdut.dkmfromcg.commonlib.recyclerview.data.MultipleFields;
import com.gdut.dkmfromcg.commonlib.recyclerview.data.MultipleItemEntity;


import java.util.ArrayList;

/**
 * Created by dkmFromCG on 2018/3/25.
 * function:
 */

public class SortItemDataConverter  {


    private final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();

    private final JSONArray dataArray;
    private int type=-1;
    private int spanSize=3;

    private SortItemDataConverter(JSONArray dataArray) {
        this.dataArray = dataArray;
    }

    public static SortItemDataConverter create(JSONArray dataArray){
        return new SortItemDataConverter(dataArray);
    }

    public ArrayList<MultipleItemEntity> convert() {
        final int size=dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String view_type=data.getString("view_type");
            if (view_type!=null ) {
                if("cells_auto_fill".equals(view_type)) {
                    type= ItemType.IMAGE;
                    spanSize=3;
                }else if ("category_title".equals(view_type)){
                    type=ItemType.TEXT;
                    spanSize=3;
                } else if("category_group".equals(view_type)){
                    type=ItemType.TEXT_IMAGE;
                    spanSize=1;
                }
            }


            final JSONObject body=data.getJSONObject("body");
            final JSONArray itemArray= body.getJSONArray("items");
            final String category_name=body.getString("category_name");
            if (itemArray == null) {
                MultipleItemEntity entity=MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE,type)
                        .setField(MultipleFields.TEXT,category_name)
                        .setField(MultipleFields.SPAN_SIZE,spanSize)
                        .build();
                ENTITIES.add(entity);
                continue;
            }

            String img_url;
            String action_url;
            String product_name;
            final int itemSize=itemArray.size();
            for (int j=0;j<itemSize;j++){
                final JSONObject itemData=itemArray.getJSONObject(j);
                img_url=itemData.getString("img_url");
                product_name=itemData.getString("product_name");
                final JSONObject action=itemData.getJSONObject("action");
                action_url=action.getString("extra");

                MultipleItemEntity entity=MultipleItemEntity.builder()
                        .setField(MultipleFields.ITEM_TYPE,type)
                        .setField(MultipleFields.IMAGE_URL,img_url)
                        .setField(MultipleFields.TEXT,product_name)
                        .setField(MultipleFields.SPAN_SIZE,spanSize)
                        .build();

                ENTITIES.add(entity);
            }

        }
        return ENTITIES;
    }
}
