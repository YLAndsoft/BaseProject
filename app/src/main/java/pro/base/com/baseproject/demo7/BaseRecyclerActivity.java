package pro.base.com.baseproject.demo7;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import fyl.base.BaseActivity;
import fyl.base.BaseRecyclerAdapter;
import fyl.base.BaseRecyclerHolder;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo1.DataUtils;
import pro.base.com.baseproject.demo1.entity.User;

/**
 * Created by DN on 2018/5/15.
 */

public class BaseRecyclerActivity extends BaseActivity {
    @ViewInject(value = R.id.base_recycler)
    RecyclerView base_recycler;
    @Override
    public void initParms(Bundle parms) {
        //此属性设置与状态栏相关
        setAllowFullScreen(true);//是否允许全屏
        setScreenRoate(false);//是否允许屏幕旋转
        setSteepStatusBar(false);//是否设置沉浸状态栏
        setSetActionBarColor(true, R.color.system_bar_color);//设置状态栏主题颜色
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.base_recycler_activity;
    }

    @Override
    public void initView(View view) {
        x.view().inject(this);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {
        List<User> data = DataUtils.getData(1, mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        base_recycler.setLayoutManager(linearLayoutManager);

        BaseRecyclerAdapter<User> adapter = new BaseRecyclerAdapter<User>(mContext,data,R.layout.user_item_layout) {
            @Override
            public void convert(BaseRecyclerHolder holder, User item, final int position) {
                holder.setImageByUrl(R.id.userHeadUrl,item.getUserHeadUrl());
                holder.setText(R.id.userName,item.getUserName()+"");
                holder.setText(R.id.userSignature,item.getUserSignature()+"");
                //给头像设置点击事件
                holder.setOnViewClick(R.id.userHeadUrl, new BaseRecyclerHolder.OnViewClickListener() {
                    @Override
                    public void onViewClick() {
                        //更新局部控件
                        RecyclerView.ViewHolder viewHolder = base_recycler.findViewHolderForAdapterPosition(position);
                        if (viewHolder instanceof BaseRecyclerHolder) {
                            BaseRecyclerHolder holder1 = (BaseRecyclerHolder) viewHolder;
                            //点击头像更新名称
                            holder1.setText(R.id.userName,"更新名称");
                        }
                    }
                });
            }
        };
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //给recyclerView设置item点击事件
            }
        });
        adapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(View view, int position) {
                //给recyclerview设置item的长按事件
                return false;
            }
        });
        //插入一项
        //adapter.insert(User user,int position);
        //删除一项
        //adapter.delete(int position);
        //更新一项
        //adapter.update(int position);
        //更新所有
        //adapter.updateAll();


        base_recycler.setAdapter(adapter);
    }

    @Override
    public void widgetClick(View v) {

    }
}
