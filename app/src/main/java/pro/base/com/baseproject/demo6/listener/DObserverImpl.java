package pro.base.com.baseproject.demo6.listener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.ProgressBar;
import android.widget.TextView;

import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo6.bean.DownloadInfo;
import pro.base.com.baseproject.demo6.constant.DownloadConstant;
import pro.base.com.baseproject.demo6.utils.AppManageUtils;

/**
 * Created by DN on 2018/3/10.
 */

public class DObserverImpl implements DownloadObserver {

    private ProgressBar progressBar;
    private TextView tvDown;
    private Context mContext;

    public DObserverImpl(ProgressBar progressBar, TextView tvDown, Context mContext) {
        this.progressBar = progressBar;
        this.tvDown = tvDown;
    }

    @Override
    public void execute(Context context, DownloadInfo appInfo, final int progress) {
        progressBar.setProgress(progress);
        int state = appInfo.getState();
        switch (state){
            case DownloadConstant.DOWNLOAD_RUNNING:
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvDown.setBackgroundColor(Color.TRANSPARENT);
                        tvDown.setText(progress+"%");
                    }
                });
                break;
            case DownloadConstant.DOWNLOAD_START:
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvDown.setBackgroundColor(Color.TRANSPARENT);
                        tvDown.setText("暂停");
                    }
                });
                break;
            case DownloadConstant.DOWNLOAD_PASE:
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvDown.setBackgroundColor(Color.TRANSPARENT);
                        tvDown.setText("继续");
                    }
                });
                break;
            case DownloadConstant.DOWNLOAD_COMPLETE:
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvDown.setBackground(mContext.getResources().getDrawable(R.drawable.bg_button_selector));
                        tvDown.setText("安装");
                    }
                });
                //清除下载监听器
                //appInfo.removeAppObserver(appInfo.getApp().getAppApkUrl());
                //清除下载信息，
                AppManageUtils.deleteDownInfo(mContext,appInfo.getApp().getAppApkUrl());
                //启动安装流程
                AppManageUtils.installApp(appInfo.getApp().getAppApkUrl(),(Activity) mContext);
                break;


        }

    }


}
