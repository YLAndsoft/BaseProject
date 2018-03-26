package pro.base.com.baseproject.demo6.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.download.DownloadEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pro.base.com.baseproject.demo6.bean.DownloadInfo;

/**
 * 下载任务管理
 * Created by DN on 2018/3/23.
 */

public class DownloadTaskUtils {

    public DownloadTaskUtils(){ throw new Error("Do not need instantiate!");}

    /**
     * 获取所有任务，包括已完成和未完成任务
     * @param mContext
     * @return
     */
    public static List<DownloadEntity> getTaskList(@NonNull Context mContext){
        List<DownloadEntity> taskList = new ArrayList<>();
        //获取所有未完成的普通下载任务
        List<DownloadEntity> allNotCompletTask = Aria.download(mContext).getAllNotCompletTask();
        //获取所有已经完成的普通任务
        List<DownloadEntity> allCompleteTask = Aria.download(mContext).getAllCompleteTask();
        if(allNotCompletTask!=null&&allNotCompletTask.size()>0){
            taskList.addAll(allNotCompletTask);
        }
        if(allCompleteTask!=null&&allCompleteTask.size()>0){
            taskList.addAll(allCompleteTask);
        }
        return taskList;

    }

    /**
     * 获取本地任务集合
     * @param mContext
     * @return
     */
    public static List<DownloadInfo> getLocationTask(@NonNull Context mContext){
        List<DownloadInfo> downloadInfos = new ArrayList<>();
        Map<String, DownloadInfo> infoMap = AppManageUtils.getMap(mContext);
        if(infoMap!=null&&infoMap.size()>0){
            for(String key:infoMap.keySet()){
                downloadInfos.add(infoMap.get(key));
            }
        }
        return downloadInfos;
    }





}
