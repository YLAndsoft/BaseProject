package fyl.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.List;

import pro.base.com.baselibrary.R;


public abstract class BaseRecyclerActivity<T> extends BaseActivity {
    private List<T> data;
    private int layout;
    private int orientation;//默认垂直
    private int color =R.color.white;
    private int recyclerID;
    private int itemLayoutId;
    private RecyclerView recyclerView;

    /**
     * @param layout 布局
     * @param orientation recyler数据方向
     * @param  color 状态栏主题颜色
     * @param recyclerID recyclerview的id
     * @param color 系统主题颜色
     * @param itemLayoutId item布局
     */
    public BaseRecyclerActivity(int layout,int recyclerID,int orientation,int color,int itemLayoutId){
        this.layout = layout;
        this.recyclerID = recyclerID;
        this.orientation = orientation;
        this.color = color;
        this.itemLayoutId = itemLayoutId;
    }

    /**
     * @param layout 布局
     * @param orientation recyler数据方向
     * @param recyclerID recyclerview的id
     * @param itemLayoutId item布局
     */
    public BaseRecyclerActivity(int layout,int recyclerID,int orientation,int itemLayoutId){
        this.layout = layout;
        this.recyclerID = recyclerID;
        this.orientation = orientation;
        this.itemLayoutId = itemLayoutId;
    }


    @Override
    public void initParms(Bundle parms) {
        //此属性设置与状态栏相关
        setAllowFullScreen(true);//是否允许全屏
        setScreenRoate(false);//是否允许屏幕旋转
        setSteepStatusBar(false);//是否设置沉浸状态栏
        setSetActionBarColor(true, color);//设置状态栏主题颜色
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return layout==0? R.layout.base_recycler_error_activity:layout;
    }

    @Override
    public void initView(View v) {
        recyclerView  = mContextView.findViewById(recyclerID);
    }

    @Override
    public void setListener() {
    }

    public abstract List<T> getData();
    @Override
    public void doBusiness(Context mContext) {
        this.data = getData();
        if(null==data||data.size()<=0) return;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(orientation);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        BaseRecyclerAdapter<T> adapter = new BaseRecyclerAdapter<T>(mContext,data,itemLayoutId) {
            @Override
            public void convert(BaseRecyclerHolder holder, T item, int position) {
                converts(recyclerView,holder,item,position);
            }
        };
        //设置布局管理器
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void widgetClick(View v) {

    }

    public abstract void converts(RecyclerView recyclerView,BaseRecyclerHolder holder, T item, int position);



}
