package pro.base.com.baseproject.demo6.bean;

/**
 * Created by DN on 2018/3/9.
 */

public class AdvertAppView {
    private int advertAppID;// app广告ID
    private int firstAppTypeID;// 一级栏目ID
    private AppSimpleView appSimpleView;// App
    private String appImgURL;// App宣传图路径
    private String appUpdateTime;// 更新时间

    public int getAdvertAppID() {
        return advertAppID;
    }

    public void setAdvertAppID(int advertAppID) {
        this.advertAppID = advertAppID;
    }

    public int getFirstAppTypeID() {
        return firstAppTypeID;
    }

    public void setFirstAppTypeID(int firstAppTypeID) {
        this.firstAppTypeID = firstAppTypeID;
    }

    public String getAppImgURL() {
        return appImgURL;
    }

    public void setAppImgURL(String appImgURL) {
        this.appImgURL = appImgURL;
    }

    public AppSimpleView getAppSimpleView() {
        return appSimpleView;
    }

    public void setAppSimpleView(AppSimpleView appSimpleView) {
        this.appSimpleView = appSimpleView;
    }

    public String getAppUpdateTime() {
        return appUpdateTime;
    }

    public void setAppUpdateTime(String appUpdateTime) {
        this.appUpdateTime = appUpdateTime;
    }
}
