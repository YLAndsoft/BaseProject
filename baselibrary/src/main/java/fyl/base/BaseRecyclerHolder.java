package fyl.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

/**
 * BaseRecyclerView的封装
 * Created by DN on 2018/4/12.
 */

public class BaseRecyclerHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    private Context context;
    public BaseRecyclerHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        //初始
        views = new SparseArray<>();
    }

    /**
     * 取得一个RecyclerHolder对象
     * @param context 上下文
     * @param itemView 子项
     * @return 返回一个RecyclerHolder对象
     */
    public static BaseRecyclerHolder getRecyclerHolder(Context context,View itemView){
        return new BaseRecyclerHolder(context,itemView);
    }

    public SparseArray<View> getViews(){
        return this.views;
    }
    /**
     * 通过view的id获取对应的控件，如果没有则加入views中
     * @param viewId 控件的id
     * @return 返回一个控件
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId){
        View view = views.get(viewId);
        if (view == null ){
            view = itemView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }

    /**
     * 设置字符串,图片
     */
    public BaseRecyclerHolder setText(int viewId,String text){
        View view = getView(viewId);
        if(view instanceof TextView){
            TextView textView = (TextView) view;
            textView.setText(text);
        }
        return this;
    }
    /**
     * 设置text颜色
     */
    public BaseRecyclerHolder setTextColor(int viewId,int color){
        View view = getView(viewId);
        if(view instanceof TextView){
            TextView textView = (TextView) view;
            textView.setTextColor(color);
        }
        return this;
    }
    /**
     * 设置textSize
     */
    public BaseRecyclerHolder setTextSize(int viewId,float size){
        View view = getView(viewId);
        if(view instanceof TextView){
            TextView textView = (TextView) view;
            textView.setTextSize(size);
        }
        return this;
    }
    /**
     * 设置单个view的点击事件
     * */
    public BaseRecyclerHolder setOnViewClick(int viewId, final OnViewClickListener l){
        View view = getView(viewId);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l.onViewClick();
            }
        });
        return this;
    }

    public interface OnViewClickListener{
        void onViewClick();
    }

    /**
     * 隐藏控件
     * @param viewId 控件ID
     * @param isisibility 是否隐藏
     * @return
     */
    public BaseRecyclerHolder setVisibility(int viewId,boolean isisibility){
        if(isisibility){
            getView(viewId).setVisibility(View.GONE);
        }else{
            getView(viewId).setVisibility(View.VISIBLE);
        }
        return this;
    }

    /**
     * 设置图片资源
     */
    public BaseRecyclerHolder setImageResource(int viewId,int drawableId){
        ImageView iv = getView(viewId);
        iv.setImageResource(drawableId);
        return this;
    }

    /**
     * 设置图片Bitmap
     */
    public BaseRecyclerHolder setImageBitmap(int viewId, Bitmap bitmap){
        ImageView iv = getView(viewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    /**
     * 设置图片地址，无尺寸
     */
    public BaseRecyclerHolder setImageByUrl(int viewId,String url){
        Glide.with(context).load(url).into((ImageView) getView(viewId));
        return this;
    }
    /**
     * 设置图片地址，有尺寸
     */
    public BaseRecyclerHolder setImageByUrl(int viewId,String url,int x,int y){
        Glide.with(context)
                .load(url)
                .override(x,y)
                .into((ImageView) getView(viewId));
        return this;
    }



}
