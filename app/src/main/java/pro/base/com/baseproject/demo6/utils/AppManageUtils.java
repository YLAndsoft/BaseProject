package pro.base.com.baseproject.demo6.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fyl.base.Utils.SPUtils;
import pro.base.com.baseproject.demo6.bean.DownloadInfo;
import pro.base.com.baseproject.demo6.constant.DownloadConstant;
import pro.base.com.baseproject.demo6.bean.AppSimpleView;

/**
 * Created by DN on 2018/3/10.
 */

public class AppManageUtils {

    private static String  DEFAULT_FILE_DIR;//默认下载目录

    public static String getDefaultDownloadPath(@NonNull String apkPath,@NonNull String url){
        return getDefaultDirectory(apkPath)+getFileName(url);
    }

    public static String getDefaultDownloadPath(@NonNull String url){
        return getDefaultDirectory(null)+getFileName(url);
    }

    /**
     * 初始化正在下载中的任务为暂停
     */
    public static void initDwonlaod(Context context){
        Map<String, DownloadInfo> map = getMap(context);
        if(map!=null&&map.size()>0){
            for(String key:map.keySet()){
                if(map.get(key).getState()==DownloadConstant.DOWNLOAD_RUNNING){
                    updateMap(context,map.get(key).getApp().getAppApkUrl(),DownloadConstant.DOWNLOAD_PASE);
                }
            }
        }
    }


    /****
     * 判断本地缓存的APK文件是否存在,并且是否完整.
     * 允许存在一些丢包现象,即服务端配置的APK大小为20000字节,可以允许本地SDCARD下载完后只有19900
     *
     * @param apkPath
     * @param apkSize
     * @return
     */
    public static final int APK_FILE_DEVIATION = 100; //APK包大小允许的误差
    public static boolean validateSdcardHasAPK(@NonNull AppSimpleView appInfo) {
        String defaultDownloadPath = getDefaultDownloadPath(appInfo.getAppApkUrl());
        //下载的文件大小是否小于指定文件大小的100
        if (AppManageUtils.exists(defaultDownloadPath)&& AppManageUtils.fileIsFull(defaultDownloadPath, appInfo.getAppSize() - APK_FILE_DEVIATION)) {
            return true;
        } else {
            return false;
        }
    }

    /****
     * 检查APP是否安装成功
     *
     * @param packageName
     * @param context
     * @return
     */
    public  static boolean isInstalled(@NonNull String packageName, Context context) {
        if(packageName==null){return false;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }
    /*****
     * 启动APP
     *
     * @param packageName
     * @param context
     */
    public static void launchApp(@NonNull String packageName, Context context) {
        boolean installFlag = isInstalled(packageName, context);
        if (installFlag) {
            Intent mainIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            context.startActivity(mainIntent);
        }
    }

    /***
     * 安装APP
     *
     * @param apkPath
     * @param act
     */
    public static String installApp( @NonNull String apkPath,Activity act) {
        String downloadPath = getDefaultDownloadPath(apkPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (exists(downloadPath)) {
            // 开始安装
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){   //判断版本是否大于7.0
                Uri apkUri = FileProvider.getUriForFile(act,act.getApplicationContext().getPackageName() + ".fileprovider",new File(downloadPath));
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            }else{
                //7.0以下的安装方式
                intent.setDataAndType(Uri.fromFile(new File(downloadPath)),"application/vnd.android.package-archive");
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            act.getApplicationContext().startActivity(intent);
            return null;
        }else{
            return "安装文件不存在！";
        }
    }


    /**
     * 保存下载信息
     * @param context
     * @param apkUrl
     * @param downLoadInfo
     * @return
     */
    public static boolean saveMap(Context context,@NonNull String apkUrl,@NonNull DownloadInfo downLoadInfo){
        try{
            Map<String, DownloadInfo> mapData = SPUtils.getInstance(context).getHashMapData(DownloadConstant.downloadKey, DownloadInfo.class);
            if(mapData!=null&&mapData.size()>0){
                if(!mapData.containsKey(apkUrl)){
                    mapData.put(apkUrl,downLoadInfo);
                }
            }else{
                mapData = new HashMap<>();
                mapData.put(apkUrl,downLoadInfo);
            }
            SPUtils.getInstance(context).putHashMapData(DownloadConstant.downloadKey,mapData);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 获取保存的下载信息
     * @param context
     * @return
     */
    public static Map<String,DownloadInfo> getMap(Context context){
        return SPUtils.getInstance(context).getHashMapData(DownloadConstant.downloadKey, DownloadInfo.class);
    }

    /**
     * 根据下载地址获取保存在本地的下载状态
     * @param context
     * @param apkUrl
     * @return
     */
    public static int getDownInfoState(Context context,@NonNull String apkUrl){
        Map<String, DownloadInfo> map = getMap(context);
        if(map!=null&&map.size()>0){
            for(String key:map.keySet()){
                if(key.equals(apkUrl)){
                    DownloadInfo downloadInfo = map.get(key);
                    return downloadInfo.getState();
                }
            }
        }
        return 0;
    }

    /**
     * 根据下载地址获取保存在本地的下载信息
     * @param context
     * @param apkUrl
     * @return
     */
    public static DownloadInfo getDownload(Context context, @NonNull String apkUrl){
        DownloadInfo dInfo = null;
        try{
            Map<String, DownloadInfo> map = getMap(context);
            for(String key:map.keySet()){
                if(key.equals(apkUrl)){
                    dInfo = map.get(key);
                    return dInfo;
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return dInfo;
    }

    /**
     * 修改下载信息的状态
     * @param context
     * @param apkUrl
     * @return
     */
    public static void updateMap(Context context,@NonNull String apkUrl,int state){
        Map<String, DownloadInfo> mapData = SPUtils.getInstance(context).getHashMapData(DownloadConstant.downloadKey, DownloadInfo.class);
        Map<String, DownloadInfo> newData = new HashMap<>();
        try{
            if(mapData!=null){
                for(String key : mapData.keySet()){
                    if(key.equals(apkUrl)){
                        DownloadInfo downloadInfo = mapData.get(key);
                        downloadInfo.setState(state);
                        newData.put(key,downloadInfo);
                    }else{
                        newData.put(key,mapData.get(key));
                    }
                }
                SPUtils.getInstance(context).putHashMapData(DownloadConstant.downloadKey, newData);
            }else{
                mapData = new HashMap<>();
                SPUtils.getInstance(context).putHashMapData(DownloadConstant.downloadKey, mapData);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public static boolean deleteDownInfo(Context context,@NonNull String apkUrl){
        Map<String, DownloadInfo> mapData = SPUtils.getInstance(context).getHashMapData(DownloadConstant.downloadKey, DownloadInfo.class);
        Map<String, DownloadInfo> newData = new HashMap<>();
        try{
            if(mapData!=null){
                for(String key : mapData.keySet()){
                    if(!key.equals(apkUrl)){
                        newData.put(key,mapData.get(key));
                    }
                }
            }
            return SPUtils.getInstance(context).putHashMapData(DownloadConstant.downloadKey, newData);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * 一键移除所有下载任务
     * @return
     */
    public static boolean removeDownInfo(Context context){
        return SPUtils.getInstance(context).remove(DownloadConstant.downloadKey);
    }

    //3. 判断SDCard的文件大小不小于指定的
    public static boolean fileIsFull(@NonNull String path, double size) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
            int available = inputStream.available();
            if (available >= size) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 默认下载目录
     * @return
     */
    public static String getDefaultDirectory(@NonNull String apkPath) {
        if (TextUtils.isEmpty(apkPath)) {
            DEFAULT_FILE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "appMarket" + File.separator;
        }else{
            return apkPath;
        }
        return DEFAULT_FILE_DIR;

    }

    /**
     * 默认下载文件的名称
     * @return
     */
    public static String getFileName(@NonNull String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }




    public static boolean exists(String fileName) {
        if (fileName == null) {
            return false;
        }
        File file = new File(fileName);
        return file.exists();
    }



}
