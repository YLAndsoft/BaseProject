package pro.base.com.baseproject.demo6.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.download.DownloadTask;
import com.arialyy.aria.core.inf.IEntity;
import com.arialyy.aria.util.CommonUtil;
import com.bumptech.glide.Glide;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo6.bean.DownloadInfo;
import pro.base.com.baseproject.demo6.listener.OnManagerListener;
import pro.base.com.baseproject.demo6.utils.AppManageUtils;
import pro.base.com.baseproject.demo6.utils.DownloadTaskUtils;
import pro.base.com.baseproject.demo6.views.HorizontalProgressBarWithNumber;

/**
 * Created by DN on 2018/3/22.
 */

public class AriaManagerAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<DownloadInfo> infoList;
    private OnManagerListener listener;
    public AriaManagerAdapter(Context context,OnManagerListener listener){
        this.context= context;
        this.listener= listener;
        inflater = LayoutInflater.from(context);
    }
    public void setData(List<DownloadInfo> infoList){
        this.infoList = infoList;
        notifyDataSetChanged();
    }

    public void refreshItem(DownloadTask task,ListView manager_list){
        if(null==infoList&&infoList.size()<=0){return ;}
        int position = 0;
        for(int i=0;i<infoList.size();i++){
            if(infoList.get(i).getApkUrl().equals(task.getKey())){
                position = i;
                break;
            }
        }
        View childAt  = manager_list.getChildAt(position);
        int visibleFirstPosi = manager_list.getFirstVisiblePosition();
        int visibleLastPosi = manager_list.getLastVisiblePosition();
        //从view中取得holder
        if(childAt== null) {return;}
        ManagerHolder holder  = (ManagerHolder) childAt.getTag();
        if (position >= visibleFirstPosi && position <= visibleLastPosi) {
            holder.manager_downSize =  childAt.findViewById(R.id.manager_downSize);
            holder.manager_convertSpeed =  childAt.findViewById(R.id.manager_convertSpeed);
            holder.manager_progressBar = childAt.findViewById(R.id.manager_progressBar);
            holder.btn_stop = childAt.findViewById(R.id.btn_stop);
            holder.manager_convertSpeed.setText(task.getConvertSpeed()+"");//下载速度
            holder.manager_downSize.setText(task.getConvertCurrentProgress()+"/"+task.getConvertFileSize()+"");//下载大小
            long currentProgress = task.getCurrentProgress();
            long l = task.getFileSize();
            if(task.getState()== IEntity.STATE_STOP){
                holder.manager_progressBar.setProgress((int) ((currentProgress*100)/l));
                holder.btn_stop.setText("继续");
            }else if(task.getState()== IEntity.STATE_COMPLETE){
                holder.btn_stop.setText("安装");
                holder.manager_progressBar.setProgress(100);
                holder.manager_convertSpeed.setVisibility(View.GONE);
            }else{
                holder.btn_stop.setText("暂停");
                holder.manager_progressBar.setProgress(task.getPercent());
            }
        }
    }


    @Override
    public int getCount() {
        return infoList!=null&& infoList.size()>0?infoList.size():0;
    }

    @Override
    public Object getItem(int i) {
        return infoList!=null&& infoList.size()>0?infoList.get(i):null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ManagerHolder managerHolder = null;
        if(view==null){
            view = inflater.inflate(R.layout.aria_manager_item_layout,null);
            managerHolder = new ManagerHolder();
            x.view().inject(managerHolder,view);
            view.setTag(managerHolder);
        }else{
            managerHolder = (ManagerHolder) view.getTag();
        }
        if(infoList!=null&&infoList.size()>0){
        DownloadInfo downloadInfo = infoList.get(i);
        managerHolder.manager_appName.setText(downloadInfo.getApp().getAppName()+"");
        Glide.with(context).load(downloadInfo.getApp().getIconUrl()).error(R.drawable.default_icon).into(managerHolder.manager_appIcon);
            final DownloadEntity downloadEntity = Aria.download(context).load(infoList.get(i).getApkUrl()).getDownloadEntity();
            if(downloadEntity!=null){
                try{
                    long currentProgress = downloadEntity.getCurrentProgress();
                    long l = downloadEntity.getFileSize();
                   // int pro = (cl*100);
                    managerHolder.manager_progressBar.setProgress((int) ((currentProgress*100)/l));
                    managerHolder.manager_convertSpeed.setText(downloadEntity.getConvertSpeed()+"");
                    String downSize = CommonUtil.formatFileSize((double) (downloadEntity.getCurrentProgress()));
                    String fileSize = downloadEntity.getConvertFileSize();
                    managerHolder.manager_downSize.setText(downSize+"/"+fileSize);
                    if(downloadEntity.getState()==IEntity.STATE_STOP){
                        managerHolder.btn_stop.setText("继续");
                    }else if(downloadEntity.getState()==IEntity.STATE_RUNNING){
                        managerHolder.btn_stop.setText("暂停");
                    }else if(downloadEntity.getState()==IEntity.STATE_COMPLETE){
                        managerHolder.btn_stop.setText("安装");
                    }else if(downloadEntity.getState()==IEntity.STATE_WAIT||downloadEntity.getState()==IEntity.STATE_OTHER){
                        managerHolder.btn_stop.setText("等待");
                    }else if(downloadEntity.getState()==IEntity.STATE_FAIL){
                        managerHolder.btn_stop.setText("重试");
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            final ManagerHolder finalManagerHolder = managerHolder;
            managerHolder.btn_stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(downloadEntity!=null){
                        switch (downloadEntity.getState()){
                            case IEntity.STATE_STOP:
                                finalManagerHolder.btn_stop.setText("继续");
                                break;
                            case IEntity.STATE_RUNNING:
                                finalManagerHolder.btn_stop.setText("暂停");
                                break;
                            case IEntity.STATE_WAIT:
                                finalManagerHolder.btn_stop.setText("暂停");
                                break;
                            case IEntity.STATE_COMPLETE:
                                Toast.makeText(context,"点击安装",Toast.LENGTH_SHORT).show();
                                return;
                            case IEntity.STATE_FAIL:
                                finalManagerHolder.btn_stop.setText("暂停");
                                return;
                        }
                    }
                    listener.onManagerClick(infoList.get(i));
                }
            });
            managerHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //删除单个下载任务
                    try{
                        Aria.download(context).load(infoList.get(i).getApkUrl()).removeRecord();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    try{
                        boolean b = AppManageUtils.deleteDownInfo(context, infoList.get(i).getApkUrl());
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    //List<DownloadEntity> taskList = DownloadTaskUtils.getTaskList(context);
                    List<DownloadInfo> downloadInfos = DownloadTaskUtils.getLocationTask(context);
                    if(infoList!=null&&infoList.size()>0){
                        infoList.clear();
                        infoList.addAll(downloadInfos);
                    }
                    notifyDataSetChanged();
                }
            });
        }
        return view;
    }


    class ManagerHolder {
        @ViewInject(value = R.id.manager_rl_Info)
        RelativeLayout manager_rl_Info;
        @ViewInject(value = R.id.manager_appIcon)
        ImageView manager_appIcon;
        @ViewInject(value = R.id.manager_appName)
        TextView manager_appName;
        @ViewInject(value = R.id.manager_progressBar)
        HorizontalProgressBarWithNumber manager_progressBar;
        @ViewInject(value = R.id.manager_convertSpeed)
        TextView manager_convertSpeed;
        @ViewInject(value = R.id.manager_downSize)
        TextView manager_downSize;
        @ViewInject(value = R.id.btn_stop)
        TextView btn_stop;
        @ViewInject(value = R.id.btn_delete)
        TextView btn_delete;
    }


}
