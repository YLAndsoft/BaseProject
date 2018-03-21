package pro.base.com.baseproject.demo6.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo6.AriaActivity;
import pro.base.com.baseproject.demo6.bean.AppSimpleView;
import pro.base.com.baseproject.demo6.bean.DownloadInfo;
import pro.base.com.baseproject.demo6.constant.DownloadConstant;
import pro.base.com.baseproject.demo6.listener.OnAlertDismissListener;
import pro.base.com.baseproject.demo6.listener.OnItClickListener;
import pro.base.com.baseproject.demo6.utils.AppManageUtils;

import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arialyy.annotations.Download;
import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadTarget;
import com.arialyy.aria.core.download.DownloadTask;
import com.arialyy.aria.core.inf.IEntity;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Random;

/**
 * Created by DN on 2018/3/12.
 */

public class Displayer extends  PopupWindow implements MyScrollView.OnScrollChangedListener, OnDismissListener,
        View.OnClickListener, OnItClickListener, OnAlertDismissListener {

    private AppSimpleView app;
    private Context mContext;
    private int position;
    private PopupWindow topPopu;
    private View view1, view2;
    private int statusBarHeight;
    private RelativeLayout rl_app_content;//app所有信息的父类布局对象
    private LinearLayout ll_mark;// 标记 当其不可见时，弹出第二个popu
    private MyScrollView mScrollview;
    private ImageView iv_close;// 关闭
    private ImageView iv_appicon;// appicon
    private TextView tv_appname;// appname
    private TextView tv_appbrief;// app简介
    private TextView tv_appstatus1;// app状态详细信息
    private TextView tv_appstatus2;// app状态详细信息
    private TextView tv_appstatus3;// app状态详细信息
    private TextView btn_download;// 安装
    private ProgressBar progressBar;
    private TextView tv_apptag1;// apptag1
    private TextView tv_apptag2;// apptag2
    private TextView mReport;
    private TextView tv_details_brief;//简介
    private int[] tagBgs = new int[] { R.drawable.zzzzzzzzzpoint_tagbg1,
            R.drawable.zzzzzzzzzpoint_tagbg2, R.drawable.zzzzzzzzzpoint_tagbg3,
            R.drawable.zzzzzzzzzpoint_tagbg4, R.drawable.zzzzzzzzzpoint_tagbg5,
            R.drawable.zzzzzzzzzpoint_tagbg6 };
    // 详细图片展示
    private TextView tv_appdescdetail;// 应用描述
    private ImageView iv_deschideandshow;// 展开、关闭
    private TextView tv_appupdatetime;// 更新时间
    private TextView tv_applatestversion;// 最新版本
    private TextView tv_versionhistory;// 历史版本
    private TextView tv_appsource;// 应用来源
    private TextView tv_appdeveloper;// 开发者
    private TextView tv_apppermission;// 使用权限
    private RelativeLayout rl_showallinfo;// 查看全部
    private EditText edt_comments;// 评论
    private RelativeLayout rl_showallcomments;// 查看全部评论
    private ProgressBar pro_loading;// 加载进度条

    private ImageView iv_back;
    private TextView tv_appname1;
    private ImageView iv_share1;// 分享
    private TextView btn_download1;// 安装
    private ProgressBar progressBar1;
    private ImageView iv_appicon1;
    private TextView tv_appdesc;
    private int topHeight;

    /**
     * 普通构造方法
     * @param app
     * @param mContext
     * @param position
     * @param appInfo
     */
    public Displayer(AppSimpleView app, Context mContext, int position, AppSimpleView appInfo) {
        this.app = app;
        this.mContext = mContext;
        this.position = position;
        // 初始化权限字符串
        initView();
        initData();
        initDownloadPro();
        Aria.download(this).register();
    }

    private void initDownloadPro() {
        DownloadTarget target = Aria.download(mContext).load(app.getAppApkUrl());
        if(target!=null){
            if(target.getPercent()>0){
                progressBar.setProgress(target.getPercent());
                progressBar1.setProgress(target.getPercent());
                if(target.getTaskState()== IEntity.STATE_STOP){ //暂停
                    btn_download.setBackgroundColor(Color.TRANSPARENT);
                    btn_download.setText("继续");
                    btn_download1.setBackgroundColor(Color.TRANSPARENT);
                    btn_download1.setText("继续");
                }else if(target.getTaskState()== IEntity.STATE_RUNNING){ //下载中
                    //暂停下载
                    btn_download.setBackgroundColor(Color.TRANSPARENT);
                    btn_download.setText(target.getPercent()+"%");
                    btn_download1.setBackgroundColor(Color.TRANSPARENT);
                    btn_download1.setText(target.getPercent()+"%");
                }else if(target.getTaskState()== IEntity.STATE_COMPLETE){ //完成
                    boolean installed = AppManageUtils.isInstalled(app.getAppPackage(), mContext);
                    if(installed){
                        btn_download.setBackground(mContext.getResources().getDrawable(R.drawable.bg_button_selector));
                        btn_download.setText("启动");
                        btn_download1.setBackground(mContext.getResources().getDrawable(R.drawable.bg_button_selector));
                        btn_download1.setText("启动");
                    }else{
                        btn_download.setBackground(mContext.getResources().getDrawable(R.drawable.bg_button_selector));
                        btn_download.setText("安装");
                        btn_download1.setBackground(mContext.getResources().getDrawable(R.drawable.bg_button_selector));
                        btn_download1.setText("安装");
                    }
                }else if(target.getPercent()>=100){
                    btn_download.setBackground(mContext.getResources().getDrawable(R.drawable.bg_button_selector));
                    btn_download.setText("安装");
                    btn_download1.setBackground(mContext.getResources().getDrawable(R.drawable.bg_button_selector));
                    btn_download1.setText("安装");
                }
                return;
            }
        }
        progressBar.setProgress(0);
        progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.download_bg));
        progressBar1.setProgress(0);
        progressBar1.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.download_bg));
        btn_download.setText("下载");
        btn_download1.setText("下载");

    }

    private void initData() {
        // 设置app的各项属性
        if (app != null) {
            Glide.with(mContext).load(app.getIconUrl() + "").error(R.drawable.chat_default_avatar).into(iv_appicon);
            tv_appname.setText(app.getAppName());
            tv_appstatus2.setText(app.getAppActualSize() + "MB");
            String[] tags = app.getAppTag().split("\\|");
            if (tags.length > 0) {
                tv_appbrief.setText(app.getAppBrief());
                // 随机产生一个背景
                Random random = new Random();
                int index = random.nextInt(6);
                tv_apptag1.setText(tags[0]);
                tv_apptag1.setBackgroundResource(tagBgs[index]);
                if (tags.length >= 2) {
                    tv_apptag2.setText(tags[1]);
                    tv_apptag2.setBackgroundResource(tagBgs[random.nextInt(5)]);
                } else {
                    tv_apptag2.setVisibility(View.GONE);
                }
            } else {
                tv_apptag1.setVisibility(View.GONE);
                tv_apptag2.setVisibility(View.GONE);
            }
            // 应用描述
            tv_appdescdetail.setText("这里是应用描述");
            // 初始
            iv_deschideandshow.setVisibility(View.VISIBLE);
            iv_deschideandshow.setSelected(false);// 初始向下
            // 更新时间
            tv_appupdatetime.setText(app.getAppUpdateTime());
            // 版本
            tv_applatestversion.setText(app.getVersion());
            // 来源
            tv_appsource.setText("这里是应用来源");
            // 开发者
            tv_appdeveloper.setText("这里是开发者");
            // 权限
            tv_apppermission.setText("这里是权限");
            edt_comments.setFocusable(false);
            edt_comments.setClickable(true);
        }
    }

    private void initView() {
        view2 = LayoutInflater.from(mContext).inflate(R.layout.appdetail_popu, null);
        this.setContentView(view2);
        initDetailPopuWindow();
        initTopPopuWindow();
    }

    private void initDetailPopuWindow() {
        /*PopupWindow detailPopu = new PopupWindow(view2, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        detailPopu.setFocusable(true);
        detailPopu.setBackgroundDrawable(new BitmapDrawable());
        detailPopu.setAnimationStyle(R.style.PopupAnimation_detail);
        detailPopu.setOutsideTouchable(false);
        detailPopu.update();*/
        this.setWidth(LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(LayoutParams.MATCH_PARENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setAnimationStyle(R.style.PopupAnimation_detail);// 设置动画
        this.setBackgroundDrawable(new BitmapDrawable());// 设置背景透明


        rl_app_content = (RelativeLayout) view2.findViewById(R.id.zzzzzzzzzpoint_rl_app_content);
        ll_mark = (LinearLayout) view2.findViewById(R.id.zzzzzzzzzpoint_ll_mark);
        mScrollview = (MyScrollView) view2.findViewById(R.id.zzzzzzzzzpoint_scrollview);
        iv_close = (ImageView) view2.findViewById(R.id.zzzzzzzzzpoint_iv_close);
        tv_apptag1 =  (TextView) view2.findViewById(R.id.zzzzzzzzzpoint_tv_apptag1);
        tv_apptag2 =  (TextView) view2.findViewById(R.id.zzzzzzzzzpoint_tv_apptag2);
        btn_download = (TextView) view2.findViewById(R.id.zzzzzzzzzpoint_tv_download);
        iv_appicon = (ImageView) view2.findViewById(R.id.zzzzzzzzzpoint_iv_appicon);
        tv_appname = (TextView) view2.findViewById(R.id.zzzzzzzzzpoint_tv_appname);
        tv_appbrief = (TextView) view2.findViewById(R.id.zzzzzzzzzpoint_tv_appbrief);
        tv_appstatus1 = (TextView) view2.findViewById(R.id.zzzzzzzzzpoint_tv_appstatus1);
        tv_appstatus2 = (TextView) view2.findViewById(R.id.zzzzzzzzzpoint_tv_appstatus2);
        tv_appstatus3 = (TextView) view2.findViewById(R.id.zzzzzzzzzpoint_tv_appstatus3);
        progressBar = (ProgressBar) view2.findViewById(R.id.zzzzzzzzzpoint_progressBar);
        tv_appdescdetail = (TextView) view2.findViewById(R.id.zzzzzzzzzpoint_tv_appdescdetail);
        iv_deschideandshow = (ImageView) view2.findViewById(R.id.zzzzzzzzzpoint_iv_deschideandshow);
        tv_appupdatetime = (TextView) view2.findViewById(R.id.zzzzzzzzzpoint_tv_appupdatetime);
        tv_applatestversion = (TextView) view2.findViewById(R.id.zzzzzzzzzpoint_tv_applatestversion);
        tv_versionhistory = (TextView) view2.findViewById(R.id.zzzzzzzzzpoint_tv_versionhistory);
        tv_appsource = (TextView) view2.findViewById(R.id.zzzzzzzzzpoint_tv_appsource);
        tv_appdeveloper = (TextView) view2.findViewById(R.id.zzzzzzzzzpoint_tv_appdeveloper);
        tv_apppermission = (TextView) view2.findViewById(R.id.zzzzzzzzzpoint_tv_apppermission);
        rl_showallinfo = (RelativeLayout) view2.findViewById(R.id.zzzzzzzzzpoint_rl_showallinfo);
        edt_comments = (EditText) view2.findViewById(R.id.zzzzzzzzzpoint_edt_comments);
        rl_showallcomments = (RelativeLayout) view2.findViewById(R.id.zzzzzzzzzpoint_rl_showallcomments);
        pro_loading = (ProgressBar) view2.findViewById(R.id.zzzzzzzzzpoint_progress1);
        //举报
        mReport = (TextView) view2.findViewById(R.id.zzzzzzzzzpoint_tv_report);
        tv_details_brief = (TextView) view2.findViewById(R.id.tv_details_brief);

        mScrollview.setOnScrollChangedListener(this);
        //detailPopu.setOnDismissListener(this);
        iv_close.setOnClickListener(this);
        mReport.setOnClickListener(this);
        btn_download.setOnClickListener(this);
        tv_versionhistory.setOnClickListener(this);
        rl_showallinfo.setOnClickListener(this);
        rl_showallcomments.setOnClickListener(this);
        edt_comments.setOnClickListener(this);
    }

    private void initTopPopuWindow() {
        view1 = ((Activity) mContext).getLayoutInflater().inflate(R.layout.appdetailtop_popu, null);
        view1.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        topHeight = view1.getMeasuredHeight();
        topPopu = new PopupWindow(view1, ViewGroup.LayoutParams.MATCH_PARENT, topHeight, false);
        topPopu.setBackgroundDrawable(new BitmapDrawable());
        topPopu.setAnimationStyle(R.style.PopupAnimation_top);
        topPopu.setOutsideTouchable(false);
        topPopu.update();

        tv_appname1 = (TextView) view1.findViewById(R.id.zzzzzzzzzpoint_tv_appname1);
        iv_back = (ImageView) view1.findViewById(R.id.zzzzzzzzzpoint_iv_back);
        iv_share1 = (ImageView) view1.findViewById(R.id.zzzzzzzzzpoint_iv_share1);
        btn_download1 = (TextView) view1.findViewById(R.id.zzzzzzzzzpoint_tv_download);
        progressBar1 = (ProgressBar) view1.findViewById(R.id.zzzzzzzzzpoint_progressBar);
        iv_appicon1 = (ImageView) view1 .findViewById(R.id.zzzzzzzzzpoint_iv_appicon);
        tv_appdesc = (TextView) view1.findViewById(R.id.zzzzzzzzzpoint_tv_appdesc);
        // 状态栏的高度
        Rect frame = new Rect();
        ((Activity) mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (t < -100) {
            closeDetailPopu();
        }
        if (t > 0) {
            Rect scrollBounds = new Rect();
            mScrollview.getHitRect(scrollBounds);
            if (ll_mark.getLocalVisibleRect(scrollBounds)) {
                // 可见
                if (topPopu != null && topPopu.isShowing()) {
                    // 关闭toppopu
                    closeTopPopu();
                    initTopPopuWindow();
                }
            } else {
                // 完全不可见
                if (topPopu != null && !topPopu.isShowing()) {
                    // 显示toppopu
                    iv_back.setOnClickListener(this);
                    showTopPopu();
                }
            }
        }
    }

    public void showTopPopu() {
        if (app != null) {
            // appname
            tv_appname1.setText(app.getAppName());

        }
        topPopu.showAtLocation(AriaActivity.ll_top, Gravity.TOP, 0, statusBarHeight);
    }

    private void closeTopPopu() {
        topPopu.dismiss();
        topPopu = null;
    }

    private void closeDetailPopu() {
        this.dismiss();
    }

    public void showDetailPopu() {
        // 显示在主界面
        if (!this.isShowing()) {
            changAlpha(1f);
            this.showAtLocation(AriaActivity.ll_top, Gravity.CENTER, 0, 0);
        }
    }

    private void changAlpha(float f) {
        WindowManager.LayoutParams params = ((Activity) mContext).getWindow().getAttributes();
        params.alpha = f;
        ((Activity) mContext).getWindow().setAttributes(params);
    }
    @Override
    public void onClick(View view) {
        if (view == iv_back) {
            // 退出
            if (topPopu != null && topPopu.isShowing()) {
                // 关闭toppopu
                closeTopPopu();
                initTopPopuWindow();
            }
            if (!isShowing()) {
                closeDetailPopu();
            }
            changAlpha(1.0f);
        } else if (view == iv_close) {
            // 关闭
            closeDetailPopu();
        } else if (view == btn_download) {
            DownloadTarget target = Aria.download(mContext).load(app.getAppApkUrl());
            setClickPro(target);
        }
    }

    private void setClickPro(DownloadTarget target) {
        if(target.getTaskState()== IEntity.STATE_RUNNING){
            //设置状态为暂停
            AppManageUtils.updateMap(mContext,app.getAppApkUrl(),DownloadConstant.DOWNLOAD_START);
            target.stop();
        }else if(target.getTaskState()== IEntity.STATE_COMPLETE){
            if(AppManageUtils.isInstalled(app.getAppPackage(),mContext)){
                //启动应用
                AppManageUtils.launchApp(app.getAppPackage(),mContext);
                //设置状态为启动
                AppManageUtils.updateMap(mContext,app.getAppApkUrl(),DownloadConstant.DOWNLOAD_START);
            }else{
                //检查文件是否完整
                if(AppManageUtils.validateSdcardHasAPK(app)){
                    //开启安装
                    AppManageUtils.installApp(target.getDownloadEntity().getDownloadPath(),(Activity)mContext);
                }else{
                    Toast.makeText(mContext,"文件有损坏，请重新下载！",Toast.LENGTH_SHORT).show();
                    //不完整，删除不完整文件，提示用户重新去下载
                    File file = new File(target.getDownloadEntity().getDownloadPath());
                    try{
                        if(file.exists())file.delete();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                    //修改保存的下载状态
                    AppManageUtils.updateMap(mContext,app.getAppApkUrl(),DownloadConstant.DOWNLOAD_INIT);
                }
            }
        }else if(target.getTaskState()== IEntity.STATE_STOP){
            //设置状态为下载
            AppManageUtils.updateMap(mContext,app.getAppApkUrl(),DownloadConstant.DOWNLOAD_RUNNING);
            target.resume();
        }else if(target.getTaskState()== IEntity.STATE_WAIT||
                target.getTaskState()== IEntity.STATE_FAIL||
                target.getTaskState()== IEntity.STATE_OTHER){
            btn_download.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
            btn_download1.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
            btn_download.setText(target.getPercent()+"%");
            btn_download1.setText(target.getPercent()+"%");
            progressBar.setProgress(target.getPercent());
            progressBar1.setProgress(target.getPercent());
            //设置状态为下载
            DownloadInfo  downloadInfo = new DownloadInfo(app,app.getAppApkUrl(),DownloadConstant.DOWNLOAD_RUNNING,position);
            AppManageUtils.saveMap(mContext,app.getAppApkUrl(),downloadInfo);
            Aria.download(mContext)
                    .load(app.getAppApkUrl())
                    .setDownloadPath(AppManageUtils.getDefaultDownloadPath(app.getAppApkUrl()))
                    .start();
        }

    }


    @Override
    public void onDismiss() {

    }
    @Override
    public void onItemClick(String o, int position) {
    }
    @Override
    public void onItemSelectClick(Object o, int position) {
    }
    @Override
    public void onDismiss(Object o) {
    }

    public void upProgress(DownloadInfo download,DownloadTask task){
        if(task.getKey().equals(app.getAppApkUrl())){ //加上地址判断，避免进度出现错乱现象
            progressBar.setProgress(task.getPercent());
            progressBar1.setProgress(task.getPercent());
            if(task.getPercent()>=100||task.getState()==IEntity.STATE_COMPLETE){
                btn_download.setText("安装");
                btn_download1.setText("安装");
                //开启安装
                AppManageUtils.installApp(task.getDownloadEntity().getDownloadPath(),(Activity)mContext);
                AppManageUtils.updateMap(mContext,task.getKey(),DownloadConstant.DOWNLOAD_COMPLETE);
            }else{
                if(task.getState()==IEntity.STATE_RUNNING){
                    btn_download.setText(task.getPercent()+"%");
                    btn_download1.setText(task.getPercent()+"%");
                }else if(task.getState()==IEntity.STATE_STOP){
                    btn_download.setText("继续");
                    btn_download1.setText("继续");
                }
            }
        }
    }

    /**
     * 任务开始
     * @param task
     */
    @Download.onTaskStart void taskStart(DownloadTask task){
        //修改为下载状态
        AppManageUtils.updateMap(mContext,task.getKey(), DownloadConstant.DOWNLOAD_RUNNING);
        DownloadInfo download = AppManageUtils.getDownload(mContext, task.getKey());
        upProgress(download,task);
    }
    /**
     * 下载中的回调
     * @param task
     */
    @Download.onTaskRunning void taskRunning(DownloadTask task){
        DownloadInfo download = AppManageUtils.getDownload(mContext, task.getKey());
        upProgress(download,task);
    }
    /**
     * 任务停止
     * @param task
     */
    @Download.onTaskStop void taskStop(DownloadTask task){
        //修改为暂停状态
        AppManageUtils.updateMap(mContext,task.getKey(),DownloadConstant.DOWNLOAD_PASE);
        DownloadInfo download = AppManageUtils.getDownload(mContext, task.getKey());
        upProgress(download,task);
    }
    /**
     * 任务恢复
     * @param task
     */
    @Download.onTaskResume void taskResume(DownloadTask task) {
        //修改为下载状态
        AppManageUtils.updateMap(mContext, task.getKey(), DownloadConstant.DOWNLOAD_RUNNING);
        DownloadInfo download = AppManageUtils.getDownload(mContext, task.getKey());
        upProgress(download,task);
    }
    /**
     * 任务完成
     * @param task
     */
    @Download.onTaskComplete void taskComplete (DownloadTask task){
        //修改为完成状态
        AppManageUtils.updateMap(mContext, task.getKey(), DownloadConstant.DOWNLOAD_COMPLETE);
        DownloadInfo download = AppManageUtils.getDownload(mContext, task.getKey());
        upProgress(download,task);
    }



}
