package com.xuancao.anchors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import com.xuancao.anchors.adapter.MyToolsGVHeaderAdapter;
import com.xuancao.anchors.adapter.MyToolsRecycleAdapter;
import com.xuancao.anchors.view.ItemModel;
import com.dell.myscrollview.R;
import com.xuancao.anchors.handygridview.HandyGridView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * author：xuancao
 * time：2018/4/4.
 */

public class RecyMainActivity extends Activity {

    private List<RecycleItemModel> mDatas;
    private RecyclerView mRecycleView;
    private String[] navigationTag = {"常用1", "质量2", "背背3", "EAS4", "产品5", "展示6"};
    private MyToolsRecycleAdapter mRecycleApapter;
    private TabLayout mTabLayout;
    private CoordinatorLayout coordinator_layout;
    private LinearLayoutManager mLayoutManager;

    private int lastTagIndex = 0;
    private boolean content2NavigateFlagInnerLock = false;
    private boolean tagFlag = false;
    private AppBarLayout appbar;
    private HandyGridView headView;


    private int newStates;//用于惯性滑动处理

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_home_my_tools);
        initView();
        initData();
        refreshView();
        initLinster();
    }

    private void initView() {
        headView = findViewById(R.id.header_gridview);
        appbar = findViewById(R.id.recy_app_bar);
        coordinator_layout = findViewById(R.id.recy_coordinator_layout);
        mRecycleView = findViewById(R.id.recy_recyclerView);
        mTabLayout = findViewById(R.id.recy_tabLayout);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycleView.setLayoutManager(mLayoutManager);
        mDatas = new ArrayList<>();

        RecycleItemModel itemModel = new RecycleItemModel();
        List<ItemModel> modelList = new ArrayList<>();
        ItemModel modelone = new ItemModel();
        modelone.name ="头条";
        modelList.add(modelone);
        ItemModel modeltwo = new ItemModel();
        modeltwo.name ="视频";
        modelList.add(modeltwo);
        ItemModel model33 = new ItemModel();
        model33.name ="娱乐";
        modelList.add(model33);
        itemModel.modelList = modelList;
        itemModel.titleName = "增员工具";
        mDatas.add(itemModel);

        RecycleItemModel itemModel1 = new RecycleItemModel();
        List<ItemModel> modelList1 = new ArrayList<>();
        ItemModel modelone1 = new ItemModel();
        modelone1.name ="文章";
        modelList1.add(modelone1);
        ItemModel modeltwo1 = new ItemModel();
        modeltwo1.name ="名片";
        modelList1.add(modeltwo1);
        ItemModel model331 = new ItemModel();
        model331.name ="门店";
        modelList1.add(model331);
        itemModel1.modelList = modelList1;
        itemModel1.titleName = "获客工具";
        mDatas.add(itemModel1);

        RecycleItemModel itemModel2 = new RecycleItemModel();
        List<ItemModel> modelList2 = new ArrayList<>();
        ItemModel modelone2 = new ItemModel();
        modelone2.name ="文章";
        modelList2.add(modelone2);
        ItemModel modeltwo2 = new ItemModel();
        modeltwo2.name ="名片";
        modelList2.add(modeltwo2);
        ItemModel model332 = new ItemModel();
        model332.name ="门店";
        modelList2.add(model332);
        itemModel2.modelList = modelList2;
        itemModel2.titleName = "获客工具";
        mDatas.add(itemModel2);

        RecycleItemModel itemModel3 = new RecycleItemModel();
        List<ItemModel> modelList3 = new ArrayList<>();
        ItemModel modelone3 = new ItemModel();
        modelone3.name ="工具";
        modelList3.add(modelone3);
        ItemModel modeltwo3 = new ItemModel();
        modeltwo3.name ="大概";
        modelList3.add(modeltwo3);
        ItemModel model333 = new ItemModel();
        model333.name ="段位";
        modelList3.add(model333);
        itemModel3.modelList = modelList2;
        itemModel3.titleName = "导演工具";
        mDatas.add(itemModel3);

        RecycleItemModel itemModel4 = new RecycleItemModel();
        List<ItemModel> modelList4 = new ArrayList<>();
        ItemModel modelone4 = new ItemModel();
        modelone4.name ="俄文";
        modelList4.add(modelone4);
        ItemModel modeltwo4 = new ItemModel();
        modeltwo4.name ="吃的";
        modelList3.add(modeltwo4);
        ItemModel model334 = new ItemModel();
        model334.name ="限额";
        modelList4.add(model334);
        itemModel4.modelList = modelList2;
        itemModel4.titleName = "侧卧工具";
        mDatas.add(itemModel4);

        mRecycleApapter = new MyToolsRecycleAdapter(this, mDatas);
        mRecycleApapter.setWheShowIcon(false);
        mRecycleView.setAdapter(mRecycleApapter);
        mRecycleApapter.setOnRecycleAdapterListener(new MyToolsRecycleAdapter.OnRecycleAdapterListener() {
            @Override
            public void onRecycleItem(int recyclePosition, int gvPosition) {
            }

            @Override
            public void onRecycleItemClick(int recyclePosition, int gvPosition) {
                startActivity(new Intent(RecyMainActivity.this,RecyEditActivity.class));
            }
        });
    }

    public void refreshView() {
        // 添加页内导航标签
        for (String item : navigationTag) {
            mTabLayout.addTab(mTabLayout.newTab().setText(item));
        }

        onFirstTabClick();
    }

    private void initLinster() {
        mTabLayout.setOnTabSelectedListener(tabSelectedListener);
        mTabLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //表明当前的动作是由 ListView 触发和主导
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    tagFlag = false;
                }
                return false;
            }
        });

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
                newStates = newState;
                //0停止 2惯性滚动
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = 0;
                if (mLayoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) mLayoutManager;
                    firstVisibleItemPosition = linearManager.findFirstVisibleItemPosition();
                }
                //向下滑动且惯性滑动
                if (newStates == 2){
                    if (dy<0){
                        int firstVisiblePosition = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                        if (firstVisiblePosition == 0) {
                            appbar.setExpanded(true, true);
                        }
                    }
                }
                refreshContent2NavigationFlag(firstVisibleItemPosition);
            }
        });

        /**---------------- 用于解决TabLayout点击第一个条目时事件丢失问题  --start----------*/
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab == null) return;
            Class c = tab.getClass();
            try {
                //"mView"是Tab的私有属性名称(可查看TabLayout源码),类型是 TabView,TabLayout私有内部类。
                Field field = c.getDeclaredField("mView");
                field.setAccessible(true);
                final View view = (View) field.get(tab);
                if (view == null) return;
                view.setTag(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) view.getTag();
                        if (position == 0){ //等于0即点击第一个条目，有时会丢失点击事件，此处强行进行滑动指定位置操作
                            int imageheight = headView.getHeight();
                            android.support.design.widget.CoordinatorLayout.Behavior behavior = ((android.support.design.widget.CoordinatorLayout.LayoutParams) appbar.getLayoutParams()).getBehavior();
                            behavior.onNestedPreScroll(coordinator_layout, appbar, mRecycleView, 0, imageheight, new int[]{0, 0});
                            tagFlag = false;
                            mTabLayout.getTabAt(position).select();
                            smoothScrollToPosition(position);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /**---------------- 用于解决TabLayout点击第一个条目时点击事件丢失问题  --end----------*/

    }

    TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            //点击tablayout自动隐藏上方区域
            int imageheight = headView.getHeight();
            android.support.design.widget.CoordinatorLayout.Behavior behavior = ((android.support.design.widget.CoordinatorLayout.LayoutParams) appbar.getLayoutParams()).getBehavior();
            behavior.onNestedPreScroll(coordinator_layout, appbar, mRecycleView, 0, imageheight, new int[]{0, 0});

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

    /**
     *  用于解决TabLayout点击第一个条目时偶尔的事件丢失问题
     */
    private void onFirstTabClick(){
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab == null) return;
            Class c = tab.getClass();
            try {
                //"mView"是Tab的私有属性名称(可查看TabLayout源码),类型是 TabView,TabLayout私有内部类。
                Field field = c.getDeclaredField("mView");
                field.setAccessible(true);
                final View view = (View) field.get(tab);
                if (view == null) return;
                view.setTag(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) view.getTag();
                        if (position == 0){ //等于0即点击第一个条目，有时会丢失点击事件，此处强行进行滑动指定位置操作
                            tagFlag = false;
                            mTabLayout.setScrollPosition(position, 0, true);
                            smoothScrollToPosition(position);
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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
                // 动作是由RecycleView触发主导的情况下，导航标签才可以滚动选中
                if (tagFlag) {
                    mTabLayout.setScrollPosition(currentTagIndex, 0, true);
                }
            }
            lastTagIndex = currentTagIndex;
        }

    }

    private List<ItemModel> modelList;
    private MyToolsGVHeaderAdapter myAdapter;
    private void initData(){
            modelList = new ArrayList<>();

            ItemModel model1 = new ItemModel();
            model1.name ="头条";
            modelList.add(model1);
            ItemModel model2 = new ItemModel();
            model2.name ="视频";
            modelList.add(model2);
            ItemModel model3 = new ItemModel();
            model3.name ="娱乐";
            modelList.add(model3);
            ItemModel model4 = new ItemModel();
            model4.name ="跟帖";
            modelList.add(model4);
            ItemModel model5 = new ItemModel();
            model5.name ="头条";
            modelList.add(model5);
            ItemModel model6 = new ItemModel();
            model6.name ="军事";
            modelList.add(model6);
            ItemModel model7 = new ItemModel();
            model7.name ="热点";
            modelList.add(model7);
            ItemModel model8 = new ItemModel();
            model8.name ="北京";
            modelList.add(model8);
            ItemModel model9= new ItemModel();
            model9.name ="北dgd";
            modelList.add(model9);
            ItemModel model10 = new ItemModel();
            model10.name ="直播";
            modelList.add(model10);
            ItemModel model11 = new ItemModel();
            model11.name ="视频";
            modelList.add(model11);

        myAdapter = new MyToolsGVHeaderAdapter(this, modelList);
        headView.setAdapter(myAdapter);
        headView.setMode(HandyGridView.MODE.NONE);
        myAdapter.setInEditMode(false);
        headView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(RecyMainActivity.this,RecyEditActivity.class));
            }
        });



    }

}
