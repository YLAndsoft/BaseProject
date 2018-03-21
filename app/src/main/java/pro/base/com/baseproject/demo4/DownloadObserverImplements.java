package pro.base.com.baseproject.demo4;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by DN on 2018/2/9.
 */

public class DownloadObserverImplements implements DownObserver {

    private TextView download_txt;
    private ProgressBar progressBar;
    private Context mContext;
    public DownloadObserverImplements(Context mContext, LoadInfo dInfo, TextView download_txt, ProgressBar progressBar){
        this.download_txt = download_txt;
        this.progressBar = progressBar;
        this.mContext = mContext;
        init(dInfo);
    }

    private void init(LoadInfo dInfo) {
        switch (dInfo.getDownloadState()){
            case Contants.D_INIT:
                download_txt.setBackgroundColor(Color.TRANSPARENT);
                download_txt.setText("0%");
                break;
        }
    }

    @Override
    public void execute(LoadInfo appInfo, final int progress) {
        if(progress<100){
            switch(appInfo.getDownloadState()){
                case  Contants.D_PASE:
                    //暂停操作
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            download_txt.setText("暂停");
                            progressBar.setProgress(progress);
                        }
                    });
                    break;
                case Contants.D_LODING:
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            download_txt.setText(progress+"%");
                            progressBar.setProgress(progress);
                        }
                    });
                    break;
            }

        }else{
            ((Activity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    download_txt.setText("安装");
                    progressBar.setProgress(progress);
                }
            });

        }

    }


}
