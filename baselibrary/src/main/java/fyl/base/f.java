package fyl.base;

import android.app.Application;

/**
 * Created by DN on 2018/1/17.
 */

public class f extends Application {

    public static boolean isBaiDuState=true; //是否开启百度统计 默认开启
    @Override
    public void onCreate() {
        super.onCreate();
    }
    //嵌入百度统计是为了监听错误，便于更新维护
    public static void init(boolean isState){
        isBaiDuState = isState;
    }

}
