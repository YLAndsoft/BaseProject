package pro.base.com.baseproject.demo6.bean;

import java.util.List;

/**
 * Created by DN on 2018/3/9.
 */

public class FirstAppTypeView {
    private int firstAppTypeID;
    private List<SecondAppTypeView> secondAppTypeViews;
    private AdvertAppView advertAppView;
    private int totalPage;
    private List<TaskAppExperienceConfigView> taskAppExperienceConfig;
    public int getFirstAppTypeID() {
        return firstAppTypeID;
    }
    public void setFirstAppTypeID(int firstAppTypeID) {
        this.firstAppTypeID = firstAppTypeID;
    }
    public List<SecondAppTypeView> getSecondAppTypeViews() {
        return secondAppTypeViews;
    }
    public void setSecondAppTypeViews(List<SecondAppTypeView> secondAppTypeViews) {
        this.secondAppTypeViews = secondAppTypeViews;
    }
    public AdvertAppView getAdvertAppView() {
        return advertAppView;
    }
    public void setAdvertAppView(AdvertAppView advertAppView) {
        this.advertAppView = advertAppView;
    }
    public int getTotalPage() {
        return totalPage;
    }
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public List<TaskAppExperienceConfigView> getTaskAppExperienceConfig() {
        return taskAppExperienceConfig;
    }
    public void setTaskAppExperienceConfig(
            List<TaskAppExperienceConfigView> taskAppExperienceConfig) {
        this.taskAppExperienceConfig = taskAppExperienceConfig;
    }
}
