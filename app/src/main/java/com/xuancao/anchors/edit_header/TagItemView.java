package com.xuancao.anchors.edit_header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dell.myscrollview.R;

public class TagItemView extends RelativeLayout{
    private Context context;
    private TextView textview;
    private ImageView imageView;
    private ImageView icon_delete;
    private RelativeLayout rl_item;

    public TagItemView(Context context) {
        super(context);
        init(context);
    }

    public TagItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TagItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        inflate(context, R.layout.view_home_my_tools, this);

        textview = findViewById(R.id.textview);
        imageView = findViewById(R.id.imageview);
        icon_delete = findViewById(R.id.icon_delete);
        rl_item = findViewById(R.id.rl_item);

        icon_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onDelete(TagItemView.this);
                }
            }
        });
    }


    /** 设置item数据 */
    public void setData(String text,String imgUrl,boolean isSelect){
       textview.setText(text);
//        imageView
        if (isSelect){
            icon_delete.setImageResource(R.mipmap.ic_my_tools_delete);
        }else {
            icon_delete.setImageResource(R.mipmap.ic_add);
        }
    }

    /** 设置占位符item样式 */
    public void showPlaceholder(int resourceId){
        rl_item.setBackgroundResource(resourceId);
    }

    /** 是否展示删除按钮 */
    public void showDeleteIcon(boolean showIcon) {
        if (showIcon){
            icon_delete.setVisibility(View.VISIBLE);
        }else {
            icon_delete.setVisibility(View.GONE);
        }
    }

    private TagItemView.OnTagDeleteListener mListener;

    public void setOnTagDeleteListener(TagItemView.OnTagDeleteListener listener) {
        mListener = listener;
    }

    public interface OnTagDeleteListener {
        /**
         * Delete view.
         *
         * @param deleteView
         */
        void onDelete(View deleteView);
    }

}
