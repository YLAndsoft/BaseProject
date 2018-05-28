package fyl.base.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by DN on 2018/4/12.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder>  {
    private Context context;//上下文
    private List<T> list;//数据源
    private LayoutInflater inflater;//布局器
    private int itemLayoutId;//布局id
    private OnItemClickListener listener;//点击事件监听器
    private OnItemLongClickListener longClickListener;//长按监听器

    /**
     * 定义一个点击事件接口回调
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public interface OnItemLongClickListener {
        boolean onItemLongClick(View view, int position);
    }
    public interface OnClickListener {
        void onClick(View view, int position);
    }

        public BaseRecyclerAdapter(Context context, List<T> list, int itemLayoutId) {
        this.context = context;
        this.list = list;
        this.itemLayoutId = itemLayoutId;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(itemLayoutId, viewGroup,false);
        return BaseRecyclerHolder.getRecyclerHolder(context, view);
    }

    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null && view != null ) {
                    listener.onItemClick(view, position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (longClickListener != null && view != null ) {
                    longClickListener.onItemLongClick(view,position);
                    return true;
                }
                return false;
            }
        });
        if(null!=list&&list.size()>0){
            convert(holder, list.get(position), position);
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
    /**
     * 填充RecyclerView适配器的方法，子类需要重写
     *
     * @param holder      ViewHolder
     * @param item        子项
     * @param position    位置
     */
    public abstract void convert(BaseRecyclerHolder holder, T item, int position);

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    /**
     * 插入一项
     *
     * @param item
     * @param position
     */
    public void insert(@NonNull T item, int position) {
        if(null==item)return;
        list.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * 删除一项
     *
     * @param position 删除位置
     */
    public void delete(int position) {
        try{
            if(null!=list&&list.size()>0){
                list.remove(position);
                //notifyDataSetChanged();
            }
        }catch (Exception ex){
        }
    }
    /**
     * 更新一项
     *此方法【慎用】，掉用此更新，在点击删除会出现数组越界异常，因为没有更新position
     * @param position 更新的位置
     */
    public void update(int position) {
        //ssss 优化更新item图片闪烁问题
        notifyItemChanged(position,"ssss");
    }
    /**
     * 更新所有
     */
    public void updateAll() {
        notifyDataSetChanged();
    }



}
