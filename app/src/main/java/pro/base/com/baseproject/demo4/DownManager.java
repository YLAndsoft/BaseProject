package pro.base.com.baseproject.demo4;

import android.content.Context;
import android.widget.Toast;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 下载管理器
 * Created by DN on 2018/2/9.
 */

public class DownManager {
    /*
    Executors提供四种线程池，分别为：
    newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
    线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
    newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
    newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。
    newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
    */
    // 1. 加一个线程队列型线程池管理下载线程
    private ExecutorService downThreadPool;
    // 2. 单例模式
    private static DownManager downManage;


    private DownManager() {
    }

    public ExecutorService getLoadThreadPool() {
        if (downThreadPool == null) {
            //开启线程，可控制最大并发数3条
            this.downThreadPool = Executors.newFixedThreadPool(3);
        }
        return this.downThreadPool;
    }

    public static DownManager getInstance() {
        if (downManage == null) {
            downManage = new DownManager();
        }
        return downManage;
    }

    public void down(final Context context, final LoadInfo dInfo) {
        if (dInfo == null) {
            Toast.makeText(context, "下载实体为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        //获得下载状态
        switch (dInfo.getDownloadState()) {
            case Contants.D_INIT:
                //初始状态,去下载
                dInfo.setDownloadState(Contants.D_LODING);//设置为下载中状态
                start(dInfo, context);
                break;
            case Contants.D_LODING:
                //下载中状态，暂停,通知监听器更新UI
                dInfo.setDownloadState(Contants.D_PASE);//设置为暂停状态
                start(dInfo, context);
                break;
            case Contants.D_PASE:
                //暂停状态，去下载
                dInfo.setDownloadState(Contants.D_LODING);//设置为下载状态
                start(dInfo, context);
                break;
            case Contants.D_COMPLTE:
                //完成状态，提示完成
                dInfo.setDownloadState(Contants.D_COMPLTE);//设置为完成状态
                Toast.makeText(context, "文件已下载完成，可执行下载完成操作", Toast.LENGTH_SHORT).show();
                break;
        }
    }

        public void start(LoadInfo dInfo, Context context){
            DownTask downTask = new DownTask(dInfo, context);
            getLoadThreadPool().execute(downTask);
        }









}
