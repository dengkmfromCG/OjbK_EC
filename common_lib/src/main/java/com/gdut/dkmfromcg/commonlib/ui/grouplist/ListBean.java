package com.gdut.dkmfromcg.commonlib.ui.grouplist;

import android.widget.CompoundButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by dkmFromCG on 2018/4/1.
 * function:
 */

public class ListBean implements MultiItemEntity {

    private int mItemType = 0;
    private String mTitle = null;
    private String mDescription = null;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = null;

    private ListBean(int itemType, String title, String description, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.mItemType = itemType;
        this.mTitle = title;
        this.mDescription = description;
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public static final class Builder {
        private int itemType = 0;
        private String title = "";
        private String description = "";
        private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = null;

        public Builder setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            this.onCheckedChangeListener = onCheckedChangeListener;
            return this;
        }

        public ListBean build(){
            return new ListBean(itemType, title,description,onCheckedChangeListener);
        }
    }
}
