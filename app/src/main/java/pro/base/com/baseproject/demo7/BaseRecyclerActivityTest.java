package pro.base.com.baseproject.demo7;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;
import fyl.base.base.BaseRecyclerActivity;
import fyl.base.base.BaseRecyclerHolder;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo1.DataUtils;
import pro.base.com.baseproject.demo1.entity.User;

public class BaseRecyclerActivityTest extends BaseRecyclerActivity {
    /**
     *  List<T> data,View view,int recyclerID,int orientation,int color,int itemLayoutId
     *  设置 activity,recyclerview的ID，recycler的布局方向，主题颜色，适配器布局
     */
    public BaseRecyclerActivityTest(){
        super(R.layout.base_recycler_activity,R.id.base_recycler,LinearLayoutManager.VERTICAL,R.color.system_color,R.layout.user_item_layout);
    }


    /**
     * 获取数据
     * @return
     */
    @Override
    public List getData() {
        List<User> data = DataUtils.getData(1, mContext);
        return data;
    }

    /**
     * 其他逻辑业务操作
     * @param view
     */
    @Override
    public void doBusines(View view) {
        //view.findViewById(R.id.base_recycler);
    }


    /**
     * 设置适配器数据
     * @param recyclerView
     * @param holder
     * @param item
     * @param position
     */
    @Override
    public void converts(final RecyclerView recyclerView, BaseRecyclerHolder holder, Object item, final int position) {
        User user = (User) item;
        holder.setImageByUrl(R.id.userHeadUrl,user.getUserHeadUrl());
        holder.setText(R.id.userName,user.getUserName()+"");
        holder.setText(R.id.userSignature,user.getUserSignature()+"");
        //给头像设置点击事件
        holder.setOnViewClick(R.id.userHeadUrl, new BaseRecyclerHolder.OnViewClickListener() {
            @Override
            public void onViewClick() {
                //更新局部控件
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                if (viewHolder instanceof BaseRecyclerHolder) {
                    BaseRecyclerHolder holder1 = (BaseRecyclerHolder) viewHolder;
                    //点击头像更新名称
                    holder1.setText(R.id.userName,"更新名称");
                }
            }
        });
        //其他点击事件和BaseRecyclerAdapter点击事件一样

    }

}
