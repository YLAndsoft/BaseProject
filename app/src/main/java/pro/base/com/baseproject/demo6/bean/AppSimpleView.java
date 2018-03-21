package pro.base.com.baseproject.demo6.bean;

import java.io.Serializable;

/**
 * APP的简单信息
 * */
public class AppSimpleView implements Serializable{
    private int appID;//应用ID
    private String appName;//应用名称
    private String appPackage;//包名
    private String version;//版本
    private int appSize;//应用大小
    private String appBrief;//应用简介
    private String iconUrl;//应用图标
    private int draw;      //有奖下载应用标识
    private String appApkUrl;
    private String appUpdateTime;// 更新时间
    private int appMoney;//付费下载
    private String appTag;//应用标签
    private String appActualSize;//app展示大小

    public String getAppTag() {
        return appTag;
    }
    public void setAppTag(String appTag) {
        this.appTag = appTag;
    }
    public String getAppActualSize() {
        return appActualSize;
    }
    public void setAppActualSize(String appActualSize) {
        this.appActualSize = appActualSize;
    }
    public int getAppMoney() {
        return appMoney;
    }
    public void setAppMoney(int appMoney) {
        this.appMoney = appMoney;
    }

    public int getAppID() {
        return appID;
    }
    public void setAppID(int appID) {
        this.appID = appID;
    }
    public String getAppName() {
        return appName;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }
    public String getAppPackage() {
        return appPackage;
    }
    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public int getAppSize() {
        return appSize;
    }
    public void setAppSize(int appSize) {
        this.appSize = appSize;
    }
    public String getAppBrief() {
        return appBrief;
    }
    public void setAppBrief(String appBrief) {
        this.appBrief = appBrief;
    }
    public String getIconUrl() {
        return iconUrl;
    }
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    public int getDraw() {
        return draw;
    }
    public void setDraw(int draw) {
        this.draw = draw;
    }
    public String getAppApkUrl() {
        return appApkUrl;
    }
    public void setAppApkUrl(String appApkUrl) {
        this.appApkUrl = appApkUrl;
    }
    public String getAppUpdateTime() {
        return appUpdateTime;
    }
    public void setAppUpdateTime(String appUpdateTime) {
        this.appUpdateTime = appUpdateTime;
    }



}
