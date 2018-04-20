package com.xuancao.anchors.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.xuancao.anchors.edit_header.TagItemView;
import com.dell.myscrollview.R;
import com.xuancao.anchors.view.ItemModel;
import com.xuancao.anchors.handygridview.scrollrunner.OnItemMovedListener;

import java.util.ArrayList;
import java.util.List;

public class MyToolsGVHeaderAdapter extends BaseAdapter implements OnItemMovedListener, TagItemView.OnTagDeleteListener {
    private Context context;
    private List<ItemModel> mDatas = new ArrayList<>();

    public MyToolsGVHeaderAdapter(Context context, List<ItemModel> dataList) {
        this.context = context;
        this.mDatas.addAll(dataList);
    }

    private GridView mGridView;
    private boolean inEditMode = true;

    public void updateData(List<ItemModel> dataList) {
        this.mDatas.clear();
        this.mDatas.addAll(dataList);
        notifyDataSetChanged();
    }

    /** 设置为是否为编辑模式（是否展示删除按钮）  */
    public void setInEditMode(boolean inEditMode) {
        this.inEditMode = inEditMode;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public ItemModel getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mGridView == null) {
            mGridView = (GridView) parent;
        }
        TagItemView tagItemView;
        if (convertView == null) {
            tagItemView = new TagItemView(context);
            convertView = tagItemView;
        } else {
            tagItemView = (TagItemView) convertView;
        }
        if (!isFixed(position)) {
            tagItemView.showDeleteIcon(inEditMode);
        } else {
            tagItemView.showDeleteIcon(false);
        }
        tagItemView.setData(mDatas.get(position).name,mDatas.get(position).imgUrl,true);
        tagItemView.setOnTagDeleteListener(this);
        if (mDatas.get(position).isPlaceholder){ //展示占位符
            tagItemView.showPlaceholder(R.drawable.icon_0);
        }else {
            tagItemView.showPlaceholder(R.drawable.s_grid_item);
        }
        return convertView;
    }

    @Override
    public void onItemMoved(int from, int to) {
        ItemModel s = mDatas.remove(from);
        mDatas.add(to, s);
    }

    @Override
    public boolean isFixed(int position) {
        //When postion==0,the item can not be dragged.
        if (mDatas.get(position)!=null && mDatas.get(position).isPlaceholder) {
            return true;
        }
        return false;
    }

    @Override
    public void onDelete(View deleteView) {
        int index = mGridView.indexOfChild(deleteView);
        if (index < 0) return;
        int position = index + mGridView.getFirstVisiblePosition();
        mDeleteListener.onHeaderDelete(position,mDatas.get(position));
    }


    private OnHeadItemDeleteListener mDeleteListener;

    public void setOnHeaderDeleteListener(OnHeadItemDeleteListener listener) {
        mDeleteListener = listener;
    }

    public interface OnHeadItemDeleteListener {
        /**
         * Delete header view.
         *
         * @param deleteModel
         */
        void onHeaderDelete(int postion,ItemModel deleteModel);
    }

}