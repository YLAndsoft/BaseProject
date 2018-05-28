package pro.base.com.baseproject.demo6;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.ArrayMap;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTarget;
import com.arialyy.aria.core.download.DownloadTask;
import com.arialyy.aria.core.inf.IEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import fyl.base.base.BaseActivity;
import fyl.base.XutilsHttp.XutilsHttp;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo6.adpter.AriaAdapter;
import pro.base.com.baseproject.demo6.bean.AppSimpleView;
import pro.base.com.baseproject.demo6.bean.DownloadInfo;
import pro.base.com.baseproject.demo6.bean.FirstAppTypeView;
import pro.base.com.baseproject.demo6.bean.SecondAppTypeView;
import pro.base.com.baseproject.demo6.constant.DownloadConstant;
import pro.base.com.baseproject.demo6.listener.DownloadOnClickListener;
import pro.base.com.baseproject.demo6.utils.AppManageUtils;

/**
 * Created by DN on 2018/3/9.
 */

public class AriaListActivity extends BaseActivity implements DownloadOnClickListener {
    @ViewInject(value = R.id.aria_listview)
    private ListView aria_listview;
    @ViewInject(value = R.id.ll_top)
    public static LinearLayout ll_top;

    private AriaAdapter mAdapter;


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
        return R.layout.aria_list_activity;
    }

    @Override
    public void initView(View view) {
        x.view().inject(this);
        Aria.download(this).register();
        ll_top = mContextView.findViewById(R.id.ll_top);
    }

    @Override
    public void initListener() {
    }

    private List<SecondAppTypeView> newGameAppTypeViews = new ArrayList<SecondAppTypeView>();
    @Override
    public void doBusiness(final Context mContext) {
        //获取数据
        //?packageKey=" + packageKey+"&firstAppTypeID="+firstAppTypeID+"&userID="+userID
        String url = "http://120.25.155.50/AppMarket/web/appServlet/getAppListAll";
        ArrayMap<String,String> params = new ArrayMap<>();
        params.put("packageKey","com.modernstark.hotelisland");
        params.put("firstAppTypeID",9+"");
        params.put("userID","defaultname");
        String path = Environment.getExternalStorageDirectory().getPath()+ File.separator+"appMarket/a.txt";
        XutilsHttp.xUtilsDownloadFile(url, path,params, new XutilsHttp.XUilsCallBack() {
            @Override
            public void onResponse(String result) {
                    if(result!=null){
                        Gson gson = new Gson();
                        TypeToken<List<FirstAppTypeView>> tToken = new TypeToken<List<FirstAppTypeView>>() {};
                        List<FirstAppTypeView> result1 = gson.fromJson(result, tToken.getType());
                        FirstAppTypeView newGame = result1.get(0);
                        newGameAppTypeViews = newGame.getSecondAppTypeViews();
                        if(newGameAppTypeViews!=null&&newGameAppTypeViews.size()>0){
                            mAdapter = new AriaAdapter(mContext,AriaListActivity.this);
                            mAdapter.setAllAppTypes(newGameAppTypeViews);
                            aria_listview.setAdapter(mAdapter);
                        }
                    }
            }
            @Override
            public void onFail(String result) {
                showToast(result+"");
            }
        });
    }

    @Override
    public void widgetClick(View v) {
    }


    /**
     * 点击adapter的回调
     * @param position
     * @param app
     * @param target
     */
    @Override
    public void downClick(int position, AppSimpleView app, DownloadTarget target) {
        try{
            String path = AppManageUtils.getDefaultDownloadPath(app.getAppApkUrl());
            int taskState = target.getTaskState();//获取任务状态
            showToast("下载的状态为："+taskState);
            switch (taskState){
                case IEntity.STATE_STOP: //停止状态
                    //修改为下载中状态
                    AppManageUtils.updateMap(mContext,app.getAppApkUrl(), DownloadConstant.DOWNLOAD_RUNNING);
                    //恢复下载
                    target.resume();
                    break;
                case IEntity.STATE_RUNNING: //下载中状态
                    /**
                     * //判断是否在下载中.
                     // 注意：如果在下载过程中，程序突然被杀死，此时的下载状态还是下载状态
                     //这里在做一次判断,判断保存在本地的状态是否也是下载状态
                     //如果本地的也是下载状态那么就去下载，否则就去暂停
                     */
                    int mapState = AppManageUtils.getDownInfoState(mContext, app.getAppApkUrl());
                    if(mapState==DownloadConstant.DOWNLOAD_RUNNING){
                        //暂停下载
                        target.pause();
                    }else{
                        //修改为下载状态
                        AppManageUtils.updateMap(mContext,app.getAppApkUrl(),DownloadConstant.DOWNLOAD_RUNNING);
                        //恢复下载
                        target.resume();
                    }
                    break;
                case IEntity.STATE_COMPLETE://完成状态
                    if(AppManageUtils.isInstalled(app.getAppPackage(),mContext)){ //是否安装
                        //启动应用
                        AppManageUtils.launchApp(app.getAppPackage(),mContext);
                    }
                    if(AppManageUtils.validateSdcardHasAPK(app)){  //文件是否下载完成，并且完整
                        //安装
                        String s = AppManageUtils.installApp(app.getAppApkUrl(), AriaListActivity.this);
                        if(s!=null&&!s.equals("")){
                            showToast(s);
                        }
                    }else{
                        //文件不存在，或者不完整，重新去下载
                        target.setDownloadPath(path).start();
                    }
                    break;
                case IEntity.STATE_WAIT://等待状态
                case IEntity.STATE_OTHER://等待状态
                case IEntity.STATE_FAIL://失败状态
                    //保存下载信息至本地
                    DownloadInfo downloadInfo = new DownloadInfo(app,app.getAppApkUrl(),DownloadConstant.DOWNLOAD_RUNNING,position);

                    boolean b = AppManageUtils.saveMap(mContext, app.getAppApkUrl(), downloadInfo);
                    if(!b){
                        showToast("保存下载信息失败！");
                    }
                    //开启下载
                    target.setDownloadPath(path).start();
                    if(taskState==IEntity.STATE_FAIL){
                        showToast("下载失败，请检查下载地址是否正确或者网络连接是否正常！");
                    }
                    break;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * 任务开始
     * @param task
     */
    @Download.onTaskStart void taskStart(DownloadTask task){
        //修改为下载状态
        AppManageUtils.updateMap(mContext,task.getKey(),DownloadConstant.DOWNLOAD_RUNNING);
        DownloadInfo download = AppManageUtils.getDownload(mContext, task.getKey());
        if(download!=null){
            if(mAdapter!=null){
                mAdapter.refreshItem(download,aria_listview,task);
            }
        }
    }
    /**
     * 下载中的回调
     * @param task
     */
    @Download.onTaskRunning void taskRunning(DownloadTask task){
        DownloadInfo download = AppManageUtils.getDownload(mContext, task.getKey());
        if(download!=null){
            if(mAdapter!=null){
                mAdapter.refreshItem(download,aria_listview,task);
            }
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
        if(download!=null){
            if(mAdapter!=null){
                mAdapter.refreshItem(download,aria_listview,task);
            }
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
        if(download!=null){
            if(mAdapter!=null){
                mAdapter.refreshItem(download,aria_listview,task);
            }
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
        if(download!=null){
            if(mAdapter!=null){
                mAdapter.refreshItem(download,aria_listview,task);
            }
        }
    }



}
