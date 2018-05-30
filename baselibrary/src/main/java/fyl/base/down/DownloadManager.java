package fyl.base.down;


import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fyl.base.down.entity.DownEntity;

/**
 * 二层业务逻辑操作
 * Created by DN on 2018/5/30.
 */

public class DownloadManager {

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
    private static DownloadManager downManage;


    private DownloadManager() {
    }

    public ExecutorService getLoadThreadPool() {
        if (downThreadPool == null) {
            //开启线程，可控制最大并发数3条
            this.downThreadPool = Executors.newFixedThreadPool(3);
        }
        return this.downThreadPool;
    }

    public static DownloadManager getInstance() {
        if (downManage == null) {
            downManage = new DownloadManager();
        }
        return downManage;
    }

    /**
     * 主启动方法
     * @param de
     * @param context
     */
    public void start(DownEntity de, Context context){
        DownloadTask downTask = new DownloadTask(de, context);
        getLoadThreadPool().execute(downTask);
    }

}
