package pro.base.com.baseproject;

import android.app.Application;

import org.xutils.x;

import pro.base.com.baseproject.demo5.db.DBManager;
import pro.base.com.baseproject.demo6.utils.AppManageUtils;

/**
 * Created by DN on 2018/1/17.
 */

public class MyAppLication extends Application{
    public static DBManager ddbManager;
    @Override
    public void onCreate() {
        super.onCreate();
        //关闭错误日志统计,默认开启
        x.Ext.init(this);
        x.Ext.setDebug(false); // 是否输出debug日志, BuildConfig.DEBUG开启debug会影响性能.
        ddbManager = DBManager.init(this);//初始化数据库

        //初始化下载异常的任务
        AppManageUtils.initDwonlaod(this);

    }
}
