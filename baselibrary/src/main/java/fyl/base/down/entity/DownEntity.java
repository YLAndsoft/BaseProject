package fyl.base.down.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.util.ArrayList;
import java.util.List;

import fyl.base.down.listener.DownloadCallback;

/**
 * Created by DN on 2018/5/30.
 */
@Table(name = "TABLE_DOWN")//表名
public class DownEntity {

    private int _id; //id，主键
    @Column(name = "downloadUrl",property = "NOT NULL")
    private String downloadUrl;//下载地址
    @Column(name = "downloadCurrentLen")
    private long downloadCurrentLen;//当前下载进度
    @Column(name = "downloadTotalSize",property = "NOT NULL")
    private long downloadTotalSize;//文件总大小
    @Column(name = "downloadState")
    private int downloadState;//下载状态


    public DownEntity(String downloadUrl, long downloadCurrentLen, long downloadTotalSize, int downloadState){
        this.downloadUrl = downloadUrl;
        this.downloadCurrentLen = downloadCurrentLen;
        this.downloadTotalSize = downloadTotalSize;
        this.downloadState = downloadState;
    }

    public DownEntity(){}

    private List<DownloadCallback> dObservers = new ArrayList<>();

    //添加一个监听器
    public synchronized void addAppObserver(DownloadCallback appObserver) {
        dObservers.add(appObserver);
    }

    public List<DownloadCallback> getdObservers() {
        return dObservers;
    }

    public void setdObservers(List<DownloadCallback> dObservers) {
        this.dObservers = dObservers;
    }
    //进度通知Callback
    public synchronized void notifyAppObservers(int state, DownEntity de) {
        for (int i = 0; i < dObservers.size(); i++) {
            if(state==StateEntity.STATE_ERROR){
                dObservers.get(i).downloadFail(de); //走错误回调
            }else if(state==StateEntity.STATE_COMPLETE){
                dObservers.get(i).complete(de);//完成回调
            }else{
                dObservers.get(i).downloading(state, de);//正常回调
            }

        }
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public long getDownloadCurrentLen() {
        return downloadCurrentLen;
    }

    public void setDownloadCurrentLen(long downloadCurrentLen) {
        this.downloadCurrentLen = downloadCurrentLen;
    }

    public long getDownloadTotalSize() {
        return downloadTotalSize;
    }

    public void setDownloadTotalSize(long downloadTotalSize) {
        this.downloadTotalSize = downloadTotalSize;
    }

    public int getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(int downloadState) {
        this.downloadState = downloadState;
    }
}
