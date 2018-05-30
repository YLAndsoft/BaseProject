package fyl.base.down.utils;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import fyl.base.down.entity.DownEntity;

/**
 * Created by DN on 2018/5/30.
 */

public class DownLoader {

    private DownEntity dInfo;
    private int progress = 0;
    /***
     * 执行下载操作
     * 这里做联网下载操作
     */
    public void down(DownEntity dInfo, Handler mHandler, Runnable mRunnable) {
        this.progress = (int)dInfo.getDownloadCurrentLen();
        this.dInfo = dInfo;
        //preDownload(mHandler,mRunnable);//启动下载
    }

    /**
     * 下载
     * @param url
     * @param mHandler
     */
    public static void download(String url, String savePath,DownEntity de, Handler mHandler) {
        HttpURLConnection connection = null;
        FileOutputStream output = null;
        InputStream input = null;
        try {
            // 判断sdCard是否存在
            if (isSupportSdCard()) {
                URL urlObj = new URL(url);
                connection = (HttpURLConnection) urlObj.openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();
                String responseMsg = connection.getResponseMessage();
                output = new FileOutputStream(getSdcardCacheDir("DownloadManager") + getADFileName(url));
                byte[] buffer = new byte[1024];
                int len = 0;
                if ("OK".equalsIgnoreCase(responseMsg)) {
                    input = connection.getInputStream();
                    while ((len = input.read(buffer)) > 0) {
                        output.write(buffer, 0, len);
                        de.setDownloadCurrentLen(de.getDownloadCurrentLen() + len);
                        mHandler.sendEmptyMessage(99);
                    }
                    input.close();
                    output.flush();
                    output.close();
                    mHandler.sendEmptyMessage(100);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message msg = new Message();
            msg.what = 101;
            msg.obj = de;
            mHandler.sendMessage(msg);

        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


    /**
     * 根据地址获得数据的字节流并转换成大小
     * @param strUrl 网络连接地址
     * @return
     */
    public static String getFileSizeByUrl(String strUrl){
        InputStream inStream=null;
        ByteArrayOutputStream outStream=null;
        String size="";
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            inStream = conn.getInputStream();
            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while( (len=inStream.read(buffer)) != -1 ){
                outStream.write(buffer, 0, len);
            }
            byte[] bt =  outStream.toByteArray();

            if(null != bt && bt.length > 0){
                DecimalFormat df = new DecimalFormat("#.00");
                if (bt.length < 1024) {
                    size = df.format((double) bt.length) + "BT";
                } else if (bt.length < 1048576) {
                    size = df.format((double) bt.length / 1024) + "KB";
                } else if (bt.length < 1073741824) {
                    size = df.format((double) bt.length / 1048576) + "MB";
                } else {
                    size = df.format((double) bt.length / 1073741824) +"GB";
                }
                //System.out.println("文件大小=：" + size);
            }else{
                //System.out.println("没有从该连接获得内容");
            }
            inStream.close();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                if(inStream !=null){
                    inStream.close();
                }
                if(outStream !=null){
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return size;
    }







    /**
     * 判断sdcard是否存在
     * @return
     */
    public static boolean isSupportSdCard() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } catch (Exception ex) {
            return false;
        }
    }

    /****
     * 获得存储文件路径
     *@param  mkdirName 保存文件的夹名称
     * @return
     */
    public static String getSdcardCacheDir(String mkdirName) {
        try {
            if (isSupportSdCard()) {
                String path = Environment.getExternalStorageDirectory()+ File.separator + mkdirName + File.separator;
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                return path;
            }
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * 默认下载文件的名称得到保存文件的名
     * @return
     */
    public static String getADFileName(String url) {
        try {
            return url.substring(url.lastIndexOf("/") + 1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
