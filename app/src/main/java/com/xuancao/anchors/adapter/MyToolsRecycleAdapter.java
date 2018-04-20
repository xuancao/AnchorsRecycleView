package com.xuancao.anchors.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.xuancao.anchors.CustomGridView;
import com.dell.myscrollview.R;
import com.xuancao.anchors.RecycleItemModel;

import java.util.List;


public class MyToolsRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    private Context mContext;
    private List<RecycleItemModel> mData;
    private static final int TYPE_HEADER = 0; //正常item
    private static final int TYPE_FOTTER = 1; //footer item(最后一个item)

    private boolean wheShowIcon;

    public MyToolsRecycleAdapter(Context mContext, List<RecycleItemModel> datas) {
        this.mContext = mContext;
        this.mData = datas;
    }

    public void updateData(List<RecycleItemModel> datas) {
        if (datas != null) {
           mData = datas;
        }
        notifyDataSetChanged();
    }

    /** 控制是否展示 添加删除 按钮 */
    public void setWheShowIcon(boolean wheShowIcon){
        this.wheShowIcon = wheShowIcon;
    }


    @Override
    public int getItemViewType(int position) {
        int s = mData.size()-1;
        if (position == s){
            return TYPE_FOTTER;
        }else{
            return TYPE_HEADER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate((viewType == TYPE_HEADER ? R.layout.item_my_tools_recycleview : R.layout.item_my_tools_foot_recycleview),parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecycleItemModel model = mData.get(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        if (model != null){
            if (model.titleName!=null){
                itemViewHolder.item_title.setText(model.titleName);
            }
            if (model.modelList!=null && model.modelList.size()>0){
                MyToolsGVAdapter gridViewAdapter = new MyToolsGVAdapter(mContext,model.modelList);
                gridViewAdapter.setWheShowIcon(wheShowIcon);
                itemViewHolder.content_gridview.setAdapter(gridViewAdapter);
                gridViewAdapter.setOnGridItemClickListener(new MyToolsGVAdapter.OnGridItemClickListener() {
                    @Override
                    public void onGridItemClick(int gvPosition) {
                        recycleAdapterListener.onRecycleItem(position,gvPosition);
                    }
                });
                itemViewHolder.content_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int gvPosition, long id) {
                        recycleAdapterListener.onRecycleItemClick(position,gvPosition);
                    }
                });
            }
        }
        return;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView item_title;
        private CustomGridView content_gridview;

        public ItemViewHolder(View view){
            super(view);
            item_title = view.findViewById(R.id.item_title);
            content_gridview = view.findViewById(R.id.content_gridview);
        }
    }

    private OnRecycleAdapterListener recycleAdapterListener;

    /** 设置监听点击RecycleView的item中GridView的位置position */
    public void setOnRecycleAdapterListener(OnRecycleAdapterListener listener) {
        recycleAdapterListener = listener;
    }

    public interface OnRecycleAdapterListener {

        /** 点击gridview中添加删除的icon按钮 */
        void onRecycleItem(int recyclePosition,int gvPosition);

        /** 点击item跳转 */
        void onRecycleItemClick(int recyclePosition,int gvPosition);

    }
}
