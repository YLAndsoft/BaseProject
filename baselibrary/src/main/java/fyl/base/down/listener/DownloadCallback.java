package fyl.base.down.listener;

import fyl.base.down.entity.DownEntity;

/**
 * Created by DN on 2018/5/30.
 */

public interface  DownloadCallback {
    void downloading(int state,DownEntity de);//下载中回调
    void complete(DownEntity de);//下载完成回调
    void downloadFail(DownEntity de);//下载错误的回调
}
