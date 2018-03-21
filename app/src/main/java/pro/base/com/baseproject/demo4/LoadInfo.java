package pro.base.com.baseproject.demo4;

import java.util.ArrayList;
import java.util.List;

/**
 * 下载信息实体类
 * Created by DN on 2018/2/9.
 */

public class LoadInfo {

    //此处只做测试，实体类需根据需求来定义
    private int downloadState;//下载状态
    private String download_url;//下载地址
    private String download_name;//下载文件的名称
    private int download_pro;//下载进度

    public LoadInfo(int downloadState, int download_pro){
        this.downloadState = downloadState;
        this.download_pro = download_pro;
    }


    List<DownObserver>  dObservers = new ArrayList<>();

    //添加一个监听器
    public synchronized void addAppObserver(DownObserver appObserver) {
        dObservers.add(appObserver);
    }
    //移除一个监听器
    public synchronized void removeAppObserver(DownObserver appObserver) {
        dObservers.remove(appObserver);
    }
    //移除所有监听器
    public synchronized void removeAppObservers() {
        dObservers.removeAll(null);
    }

    //进度通知Callback
    public synchronized void notifyAppObservers(LoadInfo dInfo, int progress, String message) {
        for (int i = 0; i < dObservers.size(); i++) {
            dObservers.get(i).execute(dInfo,progress);
            //dObservers.get(i).execute(this,progress,message);
        }
    }

    public List<DownObserver> getdObservers() {
        return dObservers;
    }

    public void setdObservers(List<DownObserver> dObservers) {
        this.dObservers = dObservers;
    }

    public int getDownload_pro() {
        return download_pro;
    }

    public void setDownload_pro(int download_pro) {
        this.download_pro = download_pro;
    }

    public int getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(int downloadState) {
        this.downloadState = downloadState;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getDownload_name() {
        return download_name;
    }

    public void setDownload_name(String download_name) {
        this.download_name = download_name;
    }
}
