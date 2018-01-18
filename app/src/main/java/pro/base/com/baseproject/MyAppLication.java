package pro.base.com.baseproject;

import android.app.Application;

import fyl.base.f;

/**
 * Created by DN on 2018/1/17.
 */

public class MyAppLication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //关闭错误日志统计,默认开启
        f.init(true);
    }
}
