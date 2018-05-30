package fyl.base.down;

import android.content.Context;
import android.os.Handler;

import fyl.base.down.entity.DownEntity;
import fyl.base.down.utils.DownLoader;

/**
 * Created by DN on 2018/5/30.
 */

public class DownloadTask implements Runnable{
    private DownEntity dInfo;
    private Context context;

    public DownloadTask(DownEntity dInfo, Context context) {
        this.dInfo = dInfo;
        this.context = context;
    }

    Handler mHandler = new Handler();
    @Override
    public void run() {
        DownLoader downLoader = new DownLoader();
        downLoader.down(dInfo,mHandler,this);
    }
}
