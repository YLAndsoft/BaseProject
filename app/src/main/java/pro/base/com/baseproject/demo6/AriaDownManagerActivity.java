package pro.base.com.baseproject.demo6;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTarget;
import com.arialyy.aria.core.download.DownloadTask;
import com.arialyy.aria.core.inf.IEntity;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.List;
import fyl.base.base.BaseActivity;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo5.views.LongDeleteDialog;
import pro.base.com.baseproject.demo6.adpter.AriaManagerAdapter;
import pro.base.com.baseproject.demo6.bean.DownloadInfo;
import pro.base.com.baseproject.demo6.constant.DownloadConstant;
import pro.base.com.baseproject.demo6.listener.OnManagerListener;
import pro.base.com.baseproject.demo6.utils.AppManageUtils;
import pro.base.com.baseproject.demo6.utils.DownloadTaskUtils;

/**
 * Created by DN on 2018/3/22.
 */

public class AriaDownManagerActivity extends BaseActivity implements OnManagerListener{
    @ViewInject(value = R.id.stop)
    private Button stop;
    @ViewInject(value = R.id.delete)
    private Button delete;
    @ViewInject(value = R.id.resume)
    private Button resume;
    @ViewInject(value = R.id.obtain)
    private Button obtain;
    @ViewInject(value = R.id.manager_list)
    private ListView manager_list;

    private AriaManagerAdapter adapter;
    @Override
    public void initParms(Intent parms) {
        //此属性设置与状态栏相关
        setAllowFullScreen(true);//是否允许全屏true
        setScreenRoate(false);//是否允许屏幕旋转false
        setSteepStatusBar(false);//是否设置沉浸状态栏true
        setSetActionBarColor(true, R.color.system_bar_color);//设置状态栏主题颜色true
    }
    @Override
    public View bindView() {
        return null;
    }
    @Override
    public int bindLayout() {
        return R.layout.aria_manager_activity;
    }
    @Override
    public void initView(View view) {
        x.view().inject(this);
        Aria.download(mContext).register();
    }
    @Override
    public void initListener() {
        stop.setOnClickListener(this);
        delete.setOnClickListener(this);
        resume.setOnClickListener(this);
        obtain.setOnClickListener(this);
    }
    @Override
    public void doBusiness(Context mContext) {
       // List<DownloadEntity> taskList = DownloadTaskUtils.getTaskList(mContext);
        List<DownloadInfo> downloadInfos = DownloadTaskUtils.getLocationTask(mContext);
        if(downloadInfos==null&&downloadInfos.size()<=0){return;}
        if(adapter==null){
            adapter = new AriaManagerAdapter(mContext,this);
        }
        manager_list.setAdapter(adapter);
        adapter.setData(downloadInfos);
    }
    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.obtain:
                //一键获取
                if(adapter==null){
                    adapter = new AriaManagerAdapter(mContext,this);
                }
                manager_list.setAdapter(adapter);
                List<DownloadInfo> downloadInfos = DownloadTaskUtils.getLocationTask(mContext);
                if(downloadInfos==null&&downloadInfos.size()<=0){return;}
                adapter.setData(downloadInfos);
                break;
            case R.id.stop:
                //一键停止
                Aria.download(mContext).stopAllTask();
                break;
            case R.id.delete:
                final LongDeleteDialog dialog = new LongDeleteDialog(mContext);
                dialog.setMessage("是否删除所有下载任务(包括已完成任务)？");
                dialog.setYesOnclickListener(new LongDeleteDialog.OnYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        //一键删除
                        Aria.download(mContext).removeAllTask(true);
                        //删除本地记录
                        boolean b = AppManageUtils.removeDownInfo(mContext);
                        if(b){
                            showToast("删除成功！");
                            adapter.setData(null);
                            dialog.dismiss();
                        }
                    }
                });
                dialog.setNoOnclickListener(new LongDeleteDialog.OnNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.resume:
                //一键恢复
                Aria.download(mContext).resumeAllTask();
                break;
        }
    }
    /**
     * 任务开始
     * @param task
     */
    @Download.onTaskStart void taskStart(DownloadTask task){
        //修改为下载状态
        AppManageUtils.updateMap(mContext,task.getKey(), DownloadConstant.DOWNLOAD_RUNNING);
        if(adapter!=null){
            adapter.refreshItem(task,manager_list);
        }
    }

    /**
     * 下载中的回调
     * @param task
     */
    @Download.onTaskRunning void taskRunning(DownloadTask task){
        DownloadInfo download = AppManageUtils.getDownload(mContext, task.getKey());
        if(adapter!=null){
            adapter.refreshItem(task,manager_list);
        }
    }
    /**
     * 任务停止
     * @param task
     */
    @Download.onTaskStop void taskStop(DownloadTask task){
        //修改为暂停状态
        AppManageUtils.updateMap(mContext,task.getKey(),DownloadConstant.DOWNLOAD_PASE);
        DownloadInfo download = AppManageUtils.getDownload(mContext, task.getKey());
        if(adapter!=null){
            adapter.refreshItem(task,manager_list);
        }
    }
    /**
     * 任务恢复
     * @param task
     */
    @Download.onTaskResume void taskResume(DownloadTask task) {
        //修改为下载状态
        AppManageUtils.updateMap(mContext, task.getKey(), DownloadConstant.DOWNLOAD_RUNNING);
        DownloadInfo download = AppManageUtils.getDownload(mContext, task.getKey());
        if(adapter!=null){
            adapter.refreshItem(task,manager_list);
        }
    }
    /**
     * 任务完成
     * @param task
     */
    @Download.onTaskComplete void taskComplete (DownloadTask task){
        //修改为完成状态
        AppManageUtils.updateMap(mContext, task.getKey(), DownloadConstant.DOWNLOAD_COMPLETE);
        DownloadInfo download = AppManageUtils.getDownload(mContext, task.getKey());
        if(adapter!=null){
            adapter.refreshItem(task,manager_list);
        }
    }

    @Override
    public void onManagerClick(DownloadInfo download) {
        String key = download.getApkUrl();
        DownloadTarget target = Aria.download(mContext).load(key);
        switch (target.getTaskState()){
            case IEntity.STATE_STOP:
                Aria.download(mContext).load(download.getApkUrl()).resume();
                break;
            case IEntity.STATE_RUNNING:
                Aria.download(mContext).load(download.getApkUrl()).pause();
                break;
            case IEntity.STATE_WAIT:
            case IEntity.STATE_FAIL:
            case IEntity.STATE_OTHER:
                Aria.download(mContext).load(download.getApkUrl()).start();
                break;
            case IEntity.STATE_COMPLETE:
                Toast.makeText(mContext,"点击安装",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
