package com.gdut.dkmfromcg.ojbk_ec.fragments.sort.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gdut.dkmfromcg.ojbk_ui.recycler.data.DataConverter;
import com.gdut.dkmfromcg.ojbk_ui.recycler.data.ItemType;
import com.gdut.dkmfromcg.ojbk_ui.recycler.data.MultipleFields;
import com.gdut.dkmfromcg.ojbk_ui.recycler.data.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by dkmFromCG on 2018/3/22.
 * function:
 */

public final class VerticalListDataConverter extends DataConverter {

    @Override
    public ArrayList<MultipleItemEntity> convert() {

        final JSONArray dataArray = JSON
                .parseObject(getJsonData())
                .getJSONObject("data")
                .getJSONArray("list");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.TEXT, name)
                    .setField(MultipleFields.TAG, false)
                    .build();

            ENTITIES.add(entity);
        }
        //设置第一个被选中
        ENTITIES.get(0).setField(MultipleFields.TAG, true);

        return ENTITIES;
    }
}
