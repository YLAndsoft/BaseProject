package pro.base.com.baseproject.demo6.bean;

import java.io.Serializable;

/**
 * Created by DN on 2018/3/10.
 */

public class DownloadInfo implements Serializable{
    /**
     * 下载状态
     */
    private int state;
    /**
     * 下载实体
     */
    private AppSimpleView app;
    /**
     * 点击的position
     */
    private int position;

    /**
     * 下载地址
     */
    private String apkUrl;

    public DownloadInfo(){}

    public DownloadInfo(AppSimpleView app, String apkUrl, int state, int position){
        this.state = state;
        this.apkUrl = apkUrl;
        this.app = app;
        this.position = position;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public AppSimpleView getApp() {
        return app;
    }

    public void setApp(AppSimpleView app) {
        this.app = app;
    }
}
