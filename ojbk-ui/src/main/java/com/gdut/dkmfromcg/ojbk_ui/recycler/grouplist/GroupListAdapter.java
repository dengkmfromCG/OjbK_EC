package com.gdut.dkmfromcg.ojbk_ui.recycler.grouplist;

import android.support.v7.widget.SwitchCompat;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.gdut.dkmfromcg.ojbk_ui.R;


import java.util.List;

/**
 * Created by dkmFromCG on 2018/4/1.
 * function:
 */

public class GroupListAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {


    public GroupListAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(GroupListType.CONSTANT_SECTION_TYPE, R.layout.item_grouplist_section);
        addItemType(GroupListType.NORMAL_ITEM, R.layout.item_grouplist_normal);
        addItemType(GroupListType.RIGHT_DESCRIPTION_ITEM, R.layout.item_grouplist_right_desp);
        addItemType(GroupListType.BOTTOM_DESCRIPTION_ITEM, R.layout.item_grouplist_bottom_desp);
        addItemType(GroupListType.ARROW_ITEM, R.layout.item_grouplist_arrow);
        addItemType(GroupListType.SWITCH_BUTTON_ITEM, R.layout.item_grouplist_swith_btn);
        addItemType(GroupListType.CUSTOM_VIEW_ITEM, R.layout.item_grouplist_custom_view);
    }


    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        final int type = helper.getItemViewType();
        if (type == GroupListType.CONSTANT_SECTION_TYPE){
            final Section section= (Section) item;
            helper.setText(R.id.tv_section, section.getTitle());
        }else {
            final ListBean listBean= (ListBean) item;
            switch (type) {
                case GroupListType.NORMAL_ITEM:
                    helper.setText(R.id.tv_gl_title_normal, listBean.getTitle());
                    break;
                case GroupListType.RIGHT_DESCRIPTION_ITEM:
                    helper.setText(R.id.tv_gl_title_right_desp, listBean.getTitle());
                    helper.setText(R.id.tv_right_description, listBean.getDescription());
                    break;
                case GroupListType.BOTTOM_DESCRIPTION_ITEM:
                    helper.setText(R.id.tv_gl_title_bottom_desp, listBean.getTitle());
                    helper.setText(R.id.tv_bottom_description, listBean.getDescription());
                    break;
                case GroupListType.ARROW_ITEM:
                    helper.setText(R.id.tv_gl_title_arrow, listBean.getTitle());
                    break;
                case GroupListType.SWITCH_BUTTON_ITEM:
                    helper.setText(R.id.tv_gl_title_switch, listBean.getTitle());
                    final SwitchCompat switchCompat = helper.getView(R.id.switch_btn);
                    switchCompat.setChecked(true);
                    switchCompat.setOnCheckedChangeListener(listBean.getOnCheckedChangeListener());
                    break;
                case GroupListType.CUSTOM_VIEW_ITEM:

                    break;
                default:
                    break;
            }
        }

    }

}
