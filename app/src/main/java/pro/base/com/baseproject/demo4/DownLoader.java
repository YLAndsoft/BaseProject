package pro.base.com.baseproject.demo4;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DN on 2018/2/9.
 */

public class DownLoader {
    private Context context;
    private LoadInfo dInfo;
    private List<DownObserver> appObservers = new ArrayList<>();
    private int progress = 0;


    public DownLoader(Context context) {
        this.context = context;
    }

    /***
     * 执行下载操作
     * 这里做联网下载操作
     */
    public void down(LoadInfo dInfo, Handler mHandler, Runnable mRunnable) {
        this.appObservers = dInfo.getdObservers();
        this.progress = dInfo.getDownload_pro();
        this.dInfo = dInfo;
        preDownload(mHandler,mRunnable);//启动下载

    }
    /**
     * 进入此方法，只有下载状态
     */
    private void preDownload(Handler mHandler,Runnable mRunnable) {
        if(dInfo.getDownloadState()==Contants.D_PASE){
            dInfo.notifyAppObservers(dInfo,dInfo.getDownload_pro(), null);
            return;
        }
        mHandler.postDelayed(mRunnable,1000);
        if(dInfo.getDownload_pro()<100){
            dInfo.setDownloadState(Contants.D_LODING);
            dInfo.setDownload_pro(dInfo.getDownload_pro()+5);
            dInfo.notifyAppObservers(dInfo,dInfo.getDownload_pro(),null);
        }else{
            dInfo.setDownloadState(Contants.D_COMPLTE);
            dInfo.setDownload_pro(100);
            dInfo.notifyAppObservers(dInfo,dInfo.getDownload_pro(),null);
        }
    }


}
