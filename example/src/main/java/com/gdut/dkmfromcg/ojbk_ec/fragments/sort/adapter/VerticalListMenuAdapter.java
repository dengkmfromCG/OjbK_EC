package com.gdut.dkmfromcg.ojbk_ec.fragments.sort.adapter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.gdut.dkmfromcg.ojbk_ec.R;
import com.gdut.dkmfromcg.ojbk_ec.fragments.sort.SortFragment;
import com.gdut.dkmfromcg.ojbk_ui.recycler.adapter.MultipleRecyclerAdapter;
import com.gdut.dkmfromcg.ojbk_ui.recycler.adapter.MultipleViewHolder;
import com.gdut.dkmfromcg.ojbk_ui.recycler.data.ItemType;
import com.gdut.dkmfromcg.ojbk_ui.recycler.data.MultipleFields;
import com.gdut.dkmfromcg.ojbk_ui.recycler.data.MultipleItemEntity;

import java.util.List;

/**
 * Created by dkmFromCG on 2018/3/22.
 * function:
 */

public class VerticalListMenuAdapter extends MultipleRecyclerAdapter {

    private final SortFragment mSortFragment;
    private int mPrePosition = 0;

    public VerticalListMenuAdapter(List<MultipleItemEntity> data, SortFragment sortFragment) {
        super(data);
        this.mSortFragment = sortFragment;
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list);
    }

    @Override
    protected void convert(final MultipleViewHolder holder, final MultipleItemEntity entity) {
        super.convert(holder, entity);
        switch (holder.getItemViewType()) {
            case ItemType.VERTICAL_MENU_LIST:
                final String text = entity.getField(MultipleFields.TEXT);
                final boolean isClicked = entity.getField(MultipleFields.TAG);
                final AppCompatTextView name = holder.getView(R.id.tv_vertical_item_name);
                final View line = holder.getView(R.id.view_line);
                final View itemView = holder.itemView;
                holder.setText(R.id.tv_vertical_item_name, text);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int currentPosition = holder.getAdapterPosition();
                        if (mPrePosition != currentPosition) {
                            //还原上一个
                            getData().get(mPrePosition).setField(MultipleFields.TAG, false);
                            notifyItemChanged(mPrePosition);

                            //更新选中的item
                            entity.setField(MultipleFields.TAG, true);
                            notifyItemChanged(currentPosition);
                            mPrePosition = currentPosition;

                            final int contentId = getData().get(currentPosition).getField(MultipleFields.ID);
                            showContent(contentId);
                        }
                    }
                });

                if (!isClicked) {
                    line.setVisibility(View.INVISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
                } else {
                    line.setVisibility(View.VISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext, R.color.app_main));
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.app_main));
                    itemView.setBackgroundColor(Color.WHITE);
                }
                break;
            default:
                break;
        }
    }

    private void showContent(int contentId) {

    }
}
