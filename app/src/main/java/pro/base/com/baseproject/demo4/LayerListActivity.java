package pro.base.com.baseproject.demo4;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import fyl.base.BaseActivity;
import pro.base.com.baseproject.R;

/**
 * 后期会封装
 * Created by DN on 2018/2/9.
 */

public class LayerListActivity extends BaseActivity {

    @ViewInject(R.id.download_txt)
    private TextView download_txt;
    @ViewInject(R.id.download_txt1)
    private TextView download_txt1;
    @ViewInject(R.id.download_txt2)
    private TextView download_txt2;
    @ViewInject(R.id.download_txt3)
    private TextView download_txt3;
    @ViewInject(R.id.download_txt4)
    private TextView download_txt4;
    @ViewInject(R.id.progressBar)
    private ProgressBar progressBar;
    @ViewInject(R.id.progressBar1)
    private ProgressBar progressBar1;
    @ViewInject(R.id.progressBar2)
    private ProgressBar progressBar2;
    @ViewInject(R.id.progressBar3)
    private ProgressBar progressBar3;
    @ViewInject(R.id.progressBar4)
    private ProgressBar progressBar4;
    public int state = 0;
    //新建4个测试实体类
    LoadInfo d1= new LoadInfo(Contants.D_INIT,0);
    LoadInfo d2= new LoadInfo(Contants.D_INIT,0);
    LoadInfo d3= new LoadInfo(Contants.D_INIT,0);
    LoadInfo d4= new LoadInfo(Contants.D_INIT,0);
    @Override
    public void initParms(Bundle parms) {
        setAllowFullScreen(true);
        setScreenRoate(false);
        setSteepStatusBar(false);
        setSetActionBarColor(true, R.color.white);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.layer_list_activity;
    }

    @Override
    public void initView(View view) {
        x.view().inject(this);
    }

    @Override
    public void setListener() {
        download_txt.setOnClickListener(this);
        download_txt1.setOnClickListener(this);
        download_txt2.setOnClickListener(this);
        download_txt3.setOnClickListener(this);
        download_txt4.setOnClickListener(this);

    }

    @Override
    public void doBusiness(Context mContext) {
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.download_txt:
                singleThreadStart();
                break;
            case R.id.download_txt1:
                manyThreadStart(d1,download_txt1,progressBar1);
                break;
            case R.id.download_txt2:
                manyThreadStart(d2,download_txt2,progressBar2);
                break;
            case R.id.download_txt3:
                manyThreadStart(d3,download_txt3,progressBar3);
                break;
            case R.id.download_txt4:
                manyThreadStart(d4,download_txt4,progressBar4);
                break;
        }
    }

    /**
     * 多线程启动下载
     */
    private void manyThreadStart(LoadInfo dInfo, TextView download_txt, ProgressBar progressBar) {
        DownloadObserverImplements  dObserver = new DownloadObserverImplements(mContext,dInfo,download_txt,progressBar);
        //为此下载实体添加一个监听器
        dInfo.addAppObserver(dObserver);
        DownManager.getInstance().down(mContext, dInfo);
    }

    private void singleThreadStart() {
        switch(state){
            case Contants.D_INIT:
                //初始
                state = Contants.D_LODING;
                progressBar.setProgress(0);
                download_txt.setBackgroundColor(Color.TRANSPARENT);//设置下载控件的背景为透明
                startDownload();
                break;
            case Contants.D_PASE:
                //暂停
                state = Contants.D_LODING;
                startDownload();
                break;
            case Contants.D_COMPLTE:
                //完成
                state = Contants.D_LODING;
                progressBar.setProgress(0);
                startDownload();
                break;
            case Contants.D_LODING:
                state = Contants.D_PASE;
                //下载中
                startDownload();
                break;
        }
    }

    Handler mHandler = new Handler();
    /**
     * 单线程下载模拟启动
     */
    public void startDownload(){
        mHandler.postDelayed(mRunnable,1000);
    }

     Runnable mRunnable = new Runnable() {
         @Override
         public void run() {
             if(state==Contants.D_PASE){
                 showToast("暂停下载");
                 download_txt.setText("暂停");
             }else if(state==Contants.D_COMPLTE){
                 if(progressBar.getProgress()<100){
                     progressBar.setProgress(progressBar.getProgress()+5);
                     download_txt.setText(progressBar.getProgress()+"%");
                     startDownload();
                 }else{
                     download_txt.setText("启动");
                     showToast("下载完成");
                     state = Contants.D_COMPLTE;
                 }
             }else{
                 int progress = progressBar.getProgress();
                 Log.i("LayerListActivity","进度值："+progress);
                 if(progressBar.getProgress()<100){
                     progressBar.setProgress(progressBar.getProgress()+5);
                     download_txt.setText(progressBar.getProgress()+"%");
                     startDownload();
                 }else{
                     download_txt.setText("启动");
                     showToast("下载完成");
                     state = Contants.D_COMPLTE;
                 }
             }


         }
     };





}
