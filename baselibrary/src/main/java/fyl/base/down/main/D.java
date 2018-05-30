package fyl.base.down.main;
import android.content.Context;
import fyl.base.down.DownloadManager;
import fyl.base.down.entity.DownEntity;
import fyl.base.down.entity.StateEntity;
import fyl.base.down.helper.DBHelper;

/**
 * 主下载入口
 * Created by DN on 2018/5/30.
 */

public class D {


    /**
     * 启动下载
     */
    public static void dStart(String downUrl,Context context){
        DownEntity downEntity = DBHelper.queryClazzApkUrl(DownEntity.class, downUrl);
        if(null==downEntity){
            downEntity = new DownEntity(downUrl,0,0,StateEntity.STATE_INIT);
        }
        if(downEntity.getDownloadState()== StateEntity.STATE_RUNING){
            downEntity.setDownloadState(StateEntity.STATE_STOP);//设置为暂停状态
        }else if(downEntity.getDownloadState()== StateEntity.STATE_STOP){
            downEntity.setDownloadState(StateEntity.STATE_RUNING);//设置为下载中的状态
        }else if(downEntity.getDownloadState()== StateEntity.STATE_ERROR){
            downEntity.setDownloadState(StateEntity.STATE_INIT);//设置为初始状态
            downEntity.setDownloadCurrentLen(0);//设置已经下载的进度为0
        }
        DownloadManager.getInstance().start(downEntity,context);
    }

    /**
     * 暂停下载
     */
    public static void dStop(){

    }

    /**
     * 完成下载
     */
    public static void dComplete(){

    }

    /**
     * 启动应用
     */
    /*public static void starts(){

    }*/


}
