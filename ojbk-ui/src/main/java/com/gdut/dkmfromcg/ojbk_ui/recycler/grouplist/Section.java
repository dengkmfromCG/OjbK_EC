package com.gdut.dkmfromcg.ojbk_ui.recycler.grouplist;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dkmFromCG on 2018/4/1.
 * function:
 */

public class Section implements MultiItemEntity {


    private String mTitle=null;

    public Section(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    @Override
    public int getItemType() {
        return GroupListType.CONSTANT_SECTION_TYPE;
    }

}
