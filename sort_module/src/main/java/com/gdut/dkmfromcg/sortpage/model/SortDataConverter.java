package com.gdut.dkmfromcg.sortpage.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gdut.dkmfromcg.ojkb.recyclerview.data.DataConverter;
import com.gdut.dkmfromcg.ojkb.recyclerview.data.MultipleFields;
import com.gdut.dkmfromcg.ojkb.recyclerview.data.MultipleItemEntity;


import java.util.ArrayList;

/**
 * Created by dkmFromCG on 2018/3/24.
 * function:
 */

public class SortDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray= JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size=dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String category_name=data.getString("category_name");
            final int category_id=data.getIntValue("category_id");
            final JSONArray category_list=data.getJSONArray("category_list");

            final MultipleItemEntity entity=MultipleItemEntity.builder()
                    .setField(MultipleFields.ID,category_id)
                    .setField(MultipleFields.TEXT,category_name)
                    .setField(MultipleFields.CATEGORY_LIST,category_list)
                    .build();

            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
