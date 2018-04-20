package com.xuancao.anchors;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.xuancao.anchors.adapter.MyToolsGVHeaderAdapter;
import com.dell.myscrollview.R;
import com.xuancao.anchors.adapter.MyToolsRecycleAdapter;
import com.xuancao.anchors.view.ItemModel;
import com.xuancao.anchors.handygridview.HandyGridView;
import com.xuancao.anchors.handygridview.listener.OnItemCapturedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * author：xuancao
 * time：2018/4/4.
 */

public class RecyEditActivity extends Activity implements MyToolsGVHeaderAdapter.OnHeadItemDeleteListener,MyToolsRecycleAdapter.OnRecycleAdapterListener{

    private RecyclerView mRecycleView;
    private MyToolsRecycleAdapter mRecycleApapter;
    private LinearLayoutManager mLayoutManager;
    private List<RecycleItemModel> mDatas;
    private TabLayout mTabLayout;

    private String[] navigationTag = {"常用1", "质量2", "背背3", "EAS4", "产品5", "展示6"};
    private int lastTagIndex = 0;
    private boolean content2NavigateFlagInnerLock = false;
    private boolean tagFlag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_home_my_tools_edit);
        initView();
        initData();
        initHeaderView();
        refreshView();
        initLinster();
    }

    private void initView() {
        mRecycleView = findViewById(R.id.recy_recyclerView);
        mTabLayout = findViewById(R.id.recy_tabLayout);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(mLayoutManager);
        mDatas = new ArrayList<>();
        mRecycleApapter = new MyToolsRecycleAdapter(this, mDatas);
        mRecycleApapter.setWheShowIcon(true);
        mRecycleView.setAdapter(mRecycleApapter);
        mRecycleApapter.setOnRecycleAdapterListener(this);
    }

    public void refreshView() {
        // 添加页内导航标签
        for (String item : navigationTag) {
            mTabLayout.addTab(mTabLayout.newTab().setText(item));
        }
    }

    private void initLinster() {
        mTabLayout.setOnTabSelectedListener(tabSelectedListener);
        mRecycleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //表明当前的动作是由 ListView 触发和主导
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    tagFlag = true;
                }
                return false;
            }
        });
        mRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = 0;
                if (mLayoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) mLayoutManager;
                    firstVisibleItemPosition = linearManager.findFirstVisibleItemPosition();
                }
                refreshContent2NavigationFlag(firstVisibleItemPosition);
            }
        });
    }

    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            //表明当前的动作是由 TabLayout 触发和主导
            tagFlag = false;

            int position = tab.getPosition();
            mTabLayout.getTabAt(position).select();

            smoothScrollToPosition(position);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    };

    private void smoothScrollToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = mLayoutManager.findFirstVisibleItemPosition();
        int lastItem = mLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            mRecycleView.smoothScrollToPosition(n);
        } else if (n <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = mRecycleView.getChildAt(n - firstItem).getTop();
            mRecycleView.smoothScrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            mRecycleView.smoothScrollToPosition(n);
        }
    }

    private void refreshContent2NavigationFlag(int currentTagIndex) {
        if (tagFlag) {
            // 上一个位置与当前位置不一致是，解锁内部锁，是导航可以发生变化
            if (lastTagIndex != currentTagIndex) {
                content2NavigateFlagInnerLock = false;
            }
            if (!content2NavigateFlagInnerLock) {
                // 锁定内部锁
                content2NavigateFlagInnerLock = true;
                //Log.e("yzx", "---------------------tagFlag"+tagFlag);
                // 动作是由ScrollView触发主导的情况下，导航标签才可以滚动选中
                if (tagFlag) {
                    mTabLayout.setScrollPosition(currentTagIndex, 0, true);
                }
            }
            lastTagIndex = currentTagIndex;
        }

    }






    private HandyGridView mHeadGView;
    private List<ItemModel> mHeadDatas;
    private MyToolsGVHeaderAdapter mHeadAdapter;

    private void initHeaderView() {
        mHeadGView = findViewById(R.id.grid_tips);
        mHeadAdapter = new MyToolsGVHeaderAdapter(this, mHeadDatas);
        mHeadGView.setAdapter(mHeadAdapter);
        mHeadGView.setMode(HandyGridView.MODE.TOUCH);
        mHeadGView.setAutoOptimize(false);
        //当gridview可以滚动并且被拖动的item位于gridview的顶部或者底部时，设置gridview滚屏的速度，
        // 每秒移动的像素点个数，默认750，可不设置。
        mHeadGView.setScrollSpeed(750);
        mHeadGView.setOnItemCapturedListener(new OnItemCapturedListener() {
            @Override
            public void onItemCaptured(View v, int position) {
                v.setScaleX(1.2f);
                v.setScaleY(1.2f);
            }

            @Override
            public void onItemReleased(View v, int position) {
                v.setScaleX(1f);
                v.setScaleY(1f);
            }
        });
        mHeadAdapter.setOnHeaderDeleteListener(this);
    }


    public void initData() {
        mHeadDatas = new ArrayList<>();

        ItemModel model1 = new ItemModel();
        model1.name ="头条";
        model1.id = "1";
        mHeadDatas.add(model1);
        ItemModel model2 = new ItemModel();
        model2.name ="视频";
        model2.id = "2";
        mHeadDatas.add(model2);
        ItemModel model3 = new ItemModel();
        model3.name ="娱乐";
        model3.id = "3";
        mHeadDatas.add(model3);
        ItemModel model4 = new ItemModel();
        model4.name ="跟帖";
        model4.id = "4";
        mHeadDatas.add(model4);
        ItemModel model5 = new ItemModel();
        model5.name ="头条";
        model5.id = "5";
        mHeadDatas.add(model5);
        ItemModel model6 = new ItemModel();
        model6.name ="军事";
        model6.id = "6";
        mHeadDatas.add(model6);
        ItemModel model7 = new ItemModel();
        model7.name ="热点";
        model7.id = "7";
        mHeadDatas.add(model7);
        ItemModel model8 = new ItemModel();
        model8.name ="北京";
        model8.id = "8";
        mHeadDatas.add(model8);
        ItemModel model9= new ItemModel();
        model9.name ="北dgd";
        model9.id = "9";
        mHeadDatas.add(model9);
        ItemModel model10 = new ItemModel();
        model10.name ="直播";
        model10.id = "10";
        mHeadDatas.add(model10);
        ItemModel model11 = new ItemModel();
        model11.name ="视频";
        model11.id = "11";
        mHeadDatas.add(model11);

        RecycleItemModel itemModel = new RecycleItemModel();
        List<ItemModel> modelList = new ArrayList<>();
        ItemModel modelone = new ItemModel();
        modelone.name ="头条";
        modelone.id = "1";
        modelone.isSelected = true;
        modelList.add(modelone);
        ItemModel modeltwo = new ItemModel();
        modeltwo.name ="视频";
        modeltwo.id = "2";
        modeltwo.isSelected = true;
        modelList.add(modeltwo);
        ItemModel model33 = new ItemModel();
        model33.name ="娱乐";
        model33.id = "3";
        model33.isSelected = true;
        modelList.add(model33);
        itemModel.modelList = modelList;
        itemModel.titleName = "增员工具";
        mDatas.add(itemModel);

        RecycleItemModel itemModel1 = new RecycleItemModel();
        List<ItemModel> modelList1 = new ArrayList<>();
        ItemModel modelone1 = new ItemModel();
        modelone1.name ="文章";
        modelone1.id = "5";
        modelone1.isSelected = true;
        modelList1.add(modelone1);
        ItemModel modeltwo1 = new ItemModel();
        modeltwo1.name ="名片";
        modeltwo1.id = "6";
        modeltwo1.isSelected = true;
        modelList1.add(modeltwo1);
        ItemModel model331 = new ItemModel();
        model331.name ="门店";
        model331.id = "17";
        model331.isSelected = false;
        modelList1.add(model331);
        itemModel1.modelList = modelList1;
        itemModel1.titleName = "获客工具";
        mDatas.add(itemModel1);

        mRecycleApapter.updateData(mDatas);

    }

    /**
     * header view 中删除一个item调用
     * @param deleteModel
     */
    @Override
    public void onHeaderDelete(int position,ItemModel deleteModel) {
        if (mHeadDatas.size() <= 2){
            Toast.makeText(RecyEditActivity.this,"请至少添加1个工具",Toast.LENGTH_LONG);
            return;
        }
        mHeadDatas.remove(position);
        if (mHeadDatas.size() > 0 && (!mHeadDatas.get(mHeadDatas.size() -1).isPlaceholder)){
            ItemModel model = new ItemModel();
            model.isPlaceholder = true;
            mHeadDatas.add(model);
        }
        mHeadAdapter.updateData(mHeadDatas);

        if (deleteModel!=null){
            for (int i = 0;i<mDatas.size();i++){
                List<ItemModel> modelList = mDatas.get(i).modelList;
                if (modelList!=null && modelList.size()>0){
                    for (int j = 0;j< modelList.size();j++){
                        if (deleteModel.id!=null && deleteModel.id.equals(modelList.get(j).id)){
                            mDatas.get(i).modelList.get(j).isSelected = false;
                            mRecycleApapter.notifyDataSetChanged();
                           return;
                        }
                    }
                }

            }
        }
    }

    /**
     * content view  中点击 具体item调用（添加--加号、删除--减号）
     * @param recyclePosition 一级目录（recycle）中的位置
     * @param gvPosition 二级目录（GridView） 中的位置
     */
    @Override
    public void onRecycleItem(int recyclePosition, int gvPosition) {
        RecycleItemModel data = mDatas.get(recyclePosition);
        if (data!=null && data.modelList!=null){
            ItemModel itemModel = data.modelList.get(gvPosition);
            if (itemModel!=null){
                if (itemModel.isSelected){ //展示情况由减号变成加号，header中删除此id的item
                    mDatas.get(recyclePosition).modelList.get(gvPosition).isSelected = false;

                    int mHeadDatasSize = mHeadDatas.size();
                    for (int i = 0;i< mHeadDatasSize;i++){
                        if (itemModel.id!=null && itemModel.id.equals(mHeadDatas.get(i).id)){
                            mHeadDatas.remove(i);
                            break;
                        }
                    }
                    if (mHeadDatasSize == 11 && (!mHeadDatas.get(mHeadDatas.size() - 1).isPlaceholder)){
                        ItemModel model = new ItemModel();
                        model.isPlaceholder = true;
                        mHeadDatas.add(model);
                    }

                    mHeadAdapter.updateData(mHeadDatas);
                    mRecycleApapter.updateData(mDatas);
                }else { //展示情况由加号变成减号，header中添加此id的item
                    if (mHeadDatas!=null){
                        if (mHeadDatas.size() == 11 && (!mHeadDatas.get(mHeadDatas.size() - 1).isPlaceholder)){
                            Toast.makeText(RecyEditActivity.this,"最多添加11个工具",Toast.LENGTH_LONG);
                        }else {
                            mDatas.get(recyclePosition).modelList.get(gvPosition).isSelected = true;

                            if (mHeadDatas.get(mHeadDatas.size() -1).isPlaceholder){
                                mHeadDatas.remove(mHeadDatas.get(mHeadDatas.size() - 1)); //删除占位符
                            }
                            mHeadDatas.add(itemModel);
                            if (mHeadDatas.size() < 11){ //添加占位符
                                ItemModel model = new ItemModel();
                                model.isPlaceholder = true;
                                mHeadDatas.add(model);
                            }

                            mHeadAdapter.updateData(mHeadDatas);
                            mRecycleApapter.updateData(mDatas);
                        }
                    }
                }
            }
        }

    }

    /**
     *  点击item跳转
     * @param recyclePosition
     * @param gvPosition
     */
    @Override
    public void onRecycleItemClick(int recyclePosition, int gvPosition) {

    }
}
