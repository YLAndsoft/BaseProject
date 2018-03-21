package pro.base.com.baseproject.demo6.bean;

import java.util.List;

/**
 * Created by DN on 2018/3/9.
 */

public class SecondAppTypeView {
    private int firstAppTypeID; // 一级ID
    private String secondAppTypeName; // 二级名称
    private String secondTypeDescribe;// 二级描述
    private int secondDisplay; // 二级显示效果
    private List<AppSimpleView> appSimpleViews;// 二级包含的App

    public String getSecondAppTypeName() {
        return secondAppTypeName;
    }

    public void setSecondAppTypeName(String secondAppTypeName) {
        this.secondAppTypeName = secondAppTypeName;
    }

    public String getSecondTypeDescribe() {
        return secondTypeDescribe;
    }

    public void setSecondTypeDescribe(String secondTypeDescribe) {
        this.secondTypeDescribe = secondTypeDescribe;
    }

    public List<AppSimpleView> getAppSimpleViews() {
        return appSimpleViews;
    }

    public void setAppSimpleViews(List<AppSimpleView> appSimpleViews) {
        this.appSimpleViews = appSimpleViews;
    }

    public int getFirstAppTypeID() {
        return firstAppTypeID;
    }

    public void setFirstAppTypeID(int firstAppTypeID) {
        this.firstAppTypeID = firstAppTypeID;
    }

    public int getSecondDisplay() {
        return secondDisplay;
    }

    public void setSecondDisplay(int secondDisplay) {
        this.secondDisplay = secondDisplay;
    }

    public SecondAppTypeView() {
        super();
    }

    public SecondAppTypeView(int firstAppTypeID, String secondAppTypeName,
                             String secondTypeDescribe, int secondDisplay,
                             List<AppSimpleView> appSimpleViews) {
        super();
        this.firstAppTypeID = firstAppTypeID;
        this.secondAppTypeName = secondAppTypeName;
        this.secondTypeDescribe = secondTypeDescribe;
        this.secondDisplay = secondDisplay;
        this.appSimpleViews = appSimpleViews;
    }

}
