package pro.base.com.baseproject.demo4;

/**
 * APP下载进度监听器,可以同步更新Android UI界面上的ProgressBar
 * Created by DN on 2018/2/9.
 */

public interface DownObserver {
    /****
     * 回调方法, 当下载有进度时候调用此方法同步更新UI进度条,
     * 当有异常发生时,将会把异常信息封装成message对象,正常时message为NULL
     * @param progress
     * @param message
     */
    //public void execute(DownloadInfo appInfo, int progress, String message);

    /****
     * 回调方法，更新ui
     */
    public void execute(LoadInfo appInfo, int progress);
}
