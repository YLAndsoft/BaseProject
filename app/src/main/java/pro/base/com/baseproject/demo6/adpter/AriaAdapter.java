package pro.base.com.baseproject.demo6.adpter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo6.AppDetailsActivtiy;
import pro.base.com.baseproject.demo6.bean.DownloadInfo;
import pro.base.com.baseproject.demo6.constant.DownloadConstant;
import pro.base.com.baseproject.demo6.listener.DObserverImpl;
import pro.base.com.baseproject.demo6.listener.DownloadOnClickListener;
import pro.base.com.baseproject.demo6.bean.AppSimpleView;
import pro.base.com.baseproject.demo6.bean.SecondAppTypeView;
import pro.base.com.baseproject.demo6.utils.AppManageUtils;
import pro.base.com.baseproject.demo6.views.Displayer;

/**
 * Created by DN on 2018/3/9.
 */

public class AriaAdapter extends BaseAdapter {

    private Context mContext;
    private List<SecondAppTypeView> allAppTypes;
    private ArrayList<AppSimpleView> allApps = new ArrayList<>();
    private DownloadOnClickListener listener;
    public AriaAdapter(Context context, DownloadOnClickListener listener){
        this.mContext = context;
        this.listener = listener;
    }
    public void setAllAppTypes(List<SecondAppTypeView> allAppTypes) {
        this.allAppTypes = allAppTypes;
        if(allAppTypes!=null&&allAppTypes.size()>0){
            for(int i = 0;i<allAppTypes.size();i++){
                allApps.addAll((ArrayList<AppSimpleView>) allAppTypes.get(i).getAppSimpleViews());
            }
        }
    }

    @Override
    public int getCount() {
        return allApps.size()>10?10:allApps.size();
    }

    @Override
    public Object getItem(int position) {
        return allApps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
         final ViewHodler holder;
         final AppSimpleView app = allApps.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.aria_item_layout, null);
            holder = new ViewHodler();
            x.view().inject(holder,convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHodler) convertView.getTag();
        }
        Glide.with(mContext).load(app.getIconUrl()).error(R.drawable.chat_default_avatar).into(holder.iv_app_icon);

        DownloadTarget target = Aria.download(mContext).load(app.getAppApkUrl());
        setProgersssBar(holder.progressBar,holder.downBtn,target,app);

        // 名字
        holder.tv_app_name.setText(app.getAppName());
        //标签
        String[] tags = app.getAppTag().split("\\|");
        if(tags.length>0&&tags!=null){
            String tag = tags[0];
            holder.tv_biaoqian.setText(tag+"");
        }
        holder.tv_app_size.setText(app.getAppActualSize()+"MB");
        // 图标
        Glide.with(mContext).load(app.getIconUrl()).error(R.drawable.chat_default_avatar).override(300, 300).into(holder.iv_app_icon);
        // 简介
        holder.tv_appdescdetail.setText(app.getAppBrief());
        holder.rl_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                    //跳转到详情界面去
               /* Displayer displayer = new Displayer(app, mContext,0, app);
                if (displayer != null && !displayer.isShowing()) {
                    displayer.showDetailPopu();
                }*/
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
        return convertView;
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
                    if(mapState==DownloadConstant.DOWNLOAD_RUNNING){
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

    /**
     * 刷新单个item布局
     * @param listView
     * @param downloadInfo
     * @param task
     */
    public void refreshItem(DownloadInfo downloadInfo, ListView listView, DownloadTask task) {

        View childAt = listView.getChildAt(downloadInfo.getPosition());
        int visibleFirstPosi = listView.getFirstVisiblePosition();
        int visibleLastPosi = listView.getLastVisiblePosition();
        //从view中取得holder
        if(childAt== null) {return;}
        ViewHodler holder  = (ViewHodler) childAt.getTag();
        if (downloadInfo.getPosition() >= visibleFirstPosi && downloadInfo.getPosition() <= visibleLastPosi) {
            holder.progressBar =  childAt.findViewById(R.id.zzzzzzzzzpoint_progressBar);
            holder.downBtn =  childAt.findViewById(R.id.zzzzzzzzzpoint_btn_download);
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


    class ViewHodler {
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
    }
}
