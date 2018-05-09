package pro.base.com.baseproject;

import android.app.Application;

import org.xutils.DbManager;
import org.xutils.x;

import fyl.base.Fyl;
import pro.base.com.baseproject.demo5.db.DManager;
import pro.base.com.baseproject.demo6.utils.AppManageUtils;

/**
 * Created by DN on 2018/1/17.
 */

public class MyAppLication extends Application{
    public static DManager ddbManager;
    private DbManager.DaoConfig daoConfig;
    public static DbManager db;
    @Override
    public void onCreate() {
        super.onCreate();
        //关闭错误日志统计,默认开启
        x.Ext.init(this);
        x.Ext.setDebug(false); // 是否输出debug日志, BuildConfig.DEBUG开启debug会影响性能.
        ddbManager = DManager.init(this);//初始化数据库
        Fyl.openLog(true);
        Fyl.openToast(true);
        //初始化下载异常的任务
        AppManageUtils.initDwonlaod(this);
        initDB();
    }

    private void initDB(){
        daoConfig = new DbManager.DaoConfig()
                .setDbName("_APP.db")//设置数据库名称
                // 不设置dbDir时, 默认存储在app的私有目录.
                //.setDbDir(new File("sdcard/SitSmice/iDEvent/DownLoad/dbPath")) // 数据库存储路径
                .setDbVersion(1)//设置数据库版本
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                });
//                .setDbUpgradeListener()//更新数据库监听

        //db还有其他的一些构造方法，比如含有更新表版本的监听器的
        db = x.getDb(daoConfig);//获取数据库单例
    }
}
