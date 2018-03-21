package pro.base.com.baseproject.demo6.bean;

/**
 * Created by DN on 2018/3/9.
 */

public class TaskAppExperienceConfigView {
    private int appID;
    private int taskTime;//体验时长
    private int taskFulfil;//完成与否 1完成 0未完成
    private int appTaskPrice;//奖励金币
    public int getAppID() {
        return appID;
    }
    public void setAppID(int appID) {
        this.appID = appID;
    }
    public int getTaskTime() {
        return taskTime;
    }
    public void setTaskTime(int taskTime) {
        this.taskTime = taskTime;
    }
    public int getTaskFulfil() {
        return taskFulfil;
    }
    public void setTaskFulfil(int taskFulfil) {
        this.taskFulfil = taskFulfil;
    }
    public int getAppTaskPrice() {
        return appTaskPrice;
    }
    public void setAppTaskPrice(int appTaskPrice) {
        this.appTaskPrice = appTaskPrice;
    }
}
