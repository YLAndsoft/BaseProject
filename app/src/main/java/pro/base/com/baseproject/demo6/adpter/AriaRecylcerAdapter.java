package pro.base.com.baseproject.demo6.adpter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTarget;
import com.arialyy.aria.core.download.DownloadTask;
import com.arialyy.aria.core.inf.IEntity;
import com.bumptech.glide.Glide;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import fyl.base.Utils.LogUtils;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo6.AppDetailsActivtiy;
import pro.base.com.baseproject.demo6.bean.AppSimpleView;
import pro.base.com.baseproject.demo6.bean.DownloadInfo;
import pro.base.com.baseproject.demo6.bean.SecondAppTypeView;
import pro.base.com.baseproject.demo6.constant.DownloadConstant;
import pro.base.com.baseproject.demo6.listener.DownloadOnClickListener;
import pro.base.com.baseproject.demo6.utils.AppManageUtils;

/**
 * Created by DN on 2018/3/24.
 */

public class AriaRecylcerAdapter extends RecyclerView.Adapter<AriaRecylcerAdapter.ViewHodler> {

    private Context mContext;
    DownloadOnClickListener listener;
    private LayoutInflater inflater;
    private ArrayList<AppSimpleView> allApps = new ArrayList<>();
    private int type = 0;
    public AriaRecylcerAdapter(Context mContext,int type,DownloadOnClickListener listener){
        this.mContext = mContext;
        this.listener = listener;
        this.type = type;
        inflater = LayoutInflater.from(mContext);
    }
    public void setAllAppTypes(List<SecondAppTypeView> allAppTypes) {
        if(allAppTypes!=null&&allAppTypes.size()>0){
            for(int i = 0;i<allAppTypes.size();i++){
                allApps.addAll((ArrayList<AppSimpleView>) allAppTypes.get(i).getAppSimpleViews());
            }
        }
    }
    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(type==0){
             view = inflater.inflate(R.layout.aria_item_layout, null);
        }else if(type==1){
             view = inflater.inflate(R.layout.aria_grid_item_layout, null);
        }
        ViewHodler mHodler = new ViewHodler(view);
        x.view().inject(mHodler,view);
        return mHodler;
    }
    @Override
    public void onBindViewHolder(final ViewHodler holder, final int position) {
        final AppSimpleView app = allApps.get(position);
        Glide.with(mContext).load(app.getIconUrl()).error(R.drawable.chat_default_avatar).into(holder.iv_app_icon);
        // 名字
        holder.tv_app_name.setText(app.getAppName());
        holder.tv_app_size.setText(app.getAppActualSize()+"MB");
        DownloadTarget target = Aria.download(mContext).load(app.getAppApkUrl());
        setProgersssBar(holder.progressBar,holder.downBtn,target,app);
        if(type==0){
            //标签
            String[] tags = app.getAppTag().split("\\|");
            if(tags.length>0&&tags!=null){
                String tag = tags[0];
                holder.tv_biaoqian.setText(tag+"");
            }
            // 简介
            holder.tv_appdescdetail.setText(app.getAppBrief());
        }

        holder.rl_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //跳转到详情界面去
                Intent intent = new Intent(mContext, AppDetailsActivtiy.class);
                intent.putExtra("app",app);
                mContext.startActivity(intent);
            }
        });

        holder.downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(app!=null){
                    DownloadTarget target = Aria.download(mContext).load(app.getAppApkUrl());
                    if(target!=null){
                        if(target.getTaskState()==IEntity.STATE_WAIT||//等待状态
                                target.getTaskState()==IEntity.STATE_OTHER||                        //等待状态
                                target.getTaskState()==IEntity.STATE_OTHER){
                            holder.downBtn.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
                            holder.downBtn.setText(target.getPercent()+"%");
                            holder.progressBar.setProgress(target.getPercent());
                        }
                        listener.downClick(position,app,target);
                    }
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return allApps.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder {
        @ViewInject(value = R.id.zzzzzzzzzpoint_iv_app_icon)
        ImageView iv_app_icon;
        @ViewInject(value = R.id.zzzzzzzzzpoint_tv_app_name)
        TextView tv_app_name;
        @ViewInject(value = R.id.zzzzzzzzzpoint_tv_app_size)
        TextView tv_app_size;
        @ViewInject(value = R.id.zzzzzzzzzpoint_tv_appdescdetail)
        TextView tv_appdescdetail;
        @ViewInject(value = R.id.zzzzzzzzzpoint_progressBar)
        ProgressBar progressBar;
        @ViewInject(value = R.id.zzzzzzzzzpoint_btn_download)
        TextView downBtn;
        @ViewInject(value = R.id.tv_biaoqian)
        TextView  tv_biaoqian;
        @ViewInject(value = R.id.zzzzzzzzzpoint_rl_info)
        RelativeLayout rl_info;

        public ViewHodler(View view) {
            super(view);
        }
    }

    /**
     * 设置pro的进度
     * @param progressBar
     * @param target
     * @param downBtn
     */
    private void setProgersssBar(ProgressBar progressBar, TextView downBtn,DownloadTarget target,AppSimpleView app) {
        if(target!=null){
            if(target.getPercent()>0){
                progressBar.setProgress(target.getPercent());
                if(target.getTaskState()== IEntity.STATE_STOP){ //暂停
                    downBtn.setBackgroundColor(Color.TRANSPARENT);
                    downBtn.setText("继续");
                }else if(target.getTaskState()== IEntity.STATE_RUNNING){ //下载中
                    downBtn.setBackgroundColor(Color.TRANSPARENT);
                    int mapState = AppManageUtils.getDownInfoState(mContext, app.getAppApkUrl());
                    if(mapState== DownloadConstant.DOWNLOAD_RUNNING){
                        downBtn.setText(target.getPercent()+"%");
                    }else{
                        downBtn.setText("继续");
                    }
                }else if(target.getTaskState()== IEntity.STATE_COMPLETE){ //完成
                    boolean installed = AppManageUtils.isInstalled(app.getAppPackage(), mContext);
                    if(installed){
                        downBtn.setBackground(mContext.getResources().getDrawable(R.drawable.bg_button_selector));
                        downBtn.setText("启动");
                    }else{
                        downBtn.setBackground(mContext.getResources().getDrawable(R.drawable.bg_button_selector));
                        downBtn.setText("安装");
                    }
                }else{
                    downBtn.setBackground(mContext.getResources().getDrawable(R.drawable.bg_button_selector));
                    downBtn.setText("安装");
                }
                return;
            }
        }
        progressBar.setProgress(0);
        progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.download_bg));
        downBtn.setText("下载");
    }

    public void refreshItem(DownloadInfo downloadInfo, DownloadTask task, LinearLayoutManager layoutManager, RecyclerView mRecyclerView) {
        if(layoutManager.findViewByPosition(layoutManager.findFirstVisibleItemPosition()).getTop()==0 &&
                layoutManager.findFirstVisibleItemPosition()==0){
            LogUtils.i("这是LinearManager=====");
        }else{
            LogUtils.i("这是GridLinearManager=====");
        }

        int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
        if (downloadInfo.getPosition() - firstItemPosition >= 0) {
            //得到要更新的item的view
            View view = mRecyclerView.getChildAt(downloadInfo.getPosition());
            if (null != mRecyclerView.getChildViewHolder(view)) {
                ViewHodler holder = (ViewHodler) mRecyclerView.getChildViewHolder(view);
                holder.progressBar.setProgress(task.getPercent());
                if(task.getPercent()>=100){
                    boolean installed = AppManageUtils.isInstalled(downloadInfo.getApp().getAppPackage(), mContext);
                    if(installed){
                        holder.downBtn.setBackground(mContext.getResources().getDrawable(R.drawable.bg_button_selector));
                        holder.downBtn.setText("启动");
                    }else{
                        holder.downBtn.setBackground(mContext.getResources().getDrawable(R.drawable.bg_button_selector));
                        holder.downBtn.setText("安装");
                        AppManageUtils.installApp(task.getDownloadPath(),(Activity)mContext);
                    }
                }else{
                    if(downloadInfo.getState()== DownloadConstant.DOWNLOAD_RUNNING){ //下载
                        holder.downBtn.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
                        holder.downBtn.setText(task.getPercent()+"%");
                    }else if(downloadInfo.getState()== DownloadConstant.DOWNLOAD_PASE){     //暂停
                        holder.downBtn.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
                        holder.downBtn.setText("继续");
                    }else if(downloadInfo.getState()== DownloadConstant.DOWNLOAD_COMPLETE){ //完成
                        holder.downBtn.setBackground(mContext.getResources().getDrawable(R.drawable.bg_button_selector));
                        holder.downBtn.setText("安装");
                        AppManageUtils.installApp(task.getDownloadPath(),(Activity)mContext);
                    }
                }

            }

        }
    }


}
