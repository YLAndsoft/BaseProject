package pro.base.com.baseproject.demo4;

import android.content.Context;
import android.os.Handler;

/**
 * 下载执行
 * Created by DN on 2018/2/9.
 */

class DownTask implements Runnable{

    private LoadInfo dInfo;
    private Context context;

    public DownTask(LoadInfo dInfo, Context context) {
        this.dInfo = dInfo;
        this.context = context;
    }

    Handler mHandler = new Handler();
    @Override
    public void run() {
        DownLoader downLoader = new DownLoader(context);
        downLoader.down(dInfo,mHandler,this);
    }


}
