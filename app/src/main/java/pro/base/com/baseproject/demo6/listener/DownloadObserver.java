package pro.base.com.baseproject.demo6.listener;


import android.content.Context;

import pro.base.com.baseproject.demo6.bean.DownloadInfo;

/**
 * 下载监听器
 * Created by DN on 2018/3/10.
 */

public interface DownloadObserver {

    /****
     * 回调方法
     * @param context
     * @param appInfo
     * @param progress
     */
    public void execute(Context context, DownloadInfo appInfo, int progress);

}
