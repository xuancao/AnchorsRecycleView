package com.xuancao.anchors.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.xuancao.anchors.edit_header.TagItemView;
import com.xuancao.anchors.view.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class MyToolsGVAdapter extends BaseAdapter implements TagItemView.OnTagDeleteListener {
    private Context context;
    private List<ItemModel> mDatas = new ArrayList<>();

    private boolean wheShowIcon = false;

    public MyToolsGVAdapter(Context context, List<ItemModel> dataList) {
        this.context = context;
        this.mDatas.addAll(dataList);
    }

    public void setData(List<ItemModel> dataList) {
        this.mDatas.clear();
        this.mDatas.addAll(dataList);
        notifyDataSetChanged();
    }

    /** 控制是否展示 添加删除 按钮 */
    public void setWheShowIcon(boolean wheShowIcon){
        this.wheShowIcon = wheShowIcon;
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

        tagItemView.showDeleteIcon(wheShowIcon);

        tagItemView.setData(mDatas.get(position).name,mDatas.get(position).imgUrl,mDatas.get(position).isSelected);
        tagItemView.setOnTagDeleteListener(this);

        return convertView;
    }

    @Override
    public void onDelete(View deleteView) {
        int index = mGridView.indexOfChild(deleteView);
        if (index < 0) return;
        int position = index + mGridView.getFirstVisiblePosition();
        itemClickListener.onGridItemClick(position);
    }

    private GridView mGridView;
    private OnGridItemClickListener itemClickListener;

    /** 设置监听点击RecycleView的item中GridView的位置position */
    public void setOnGridItemClickListener(OnGridItemClickListener listener) {
        itemClickListener = listener;
    }

    public interface OnGridItemClickListener {

        void onGridItemClick(int gvPosition);

    }

}