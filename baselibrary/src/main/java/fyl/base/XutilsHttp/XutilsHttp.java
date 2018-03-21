package fyl.base.XutilsHttp;

import android.util.ArrayMap;


import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import fyl.base.Utils.GZipUtils;


/**
 * Created by DN on 2017/12/8.
 */

public class XutilsHttp {
    //接口回调
   public interface XUilsCallBack{
        void onResponse(String result);
        void onFail(String result);
    }
    /**
     * 普通的get请求(无参数，无缓存)
     *@param  url  请求地址
     * @param map 参数集合
     * @param  xCallBack 请求结果回调
     */
    public static void xUtilsGet(String url,ArrayMap<String,String> map,final XUilsCallBack xCallBack){
        RequestParams params = new RequestParams(url);
        if (null != map && !map.isEmpty()){
            for (Map.Entry<String,String> entry : map.entrySet()){
                params.addBodyParameter(entry.getKey(),entry.getValue());
            }
        }

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    xCallBack.onResponse(result);
                }
            }
            /**
             * // 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，
             * 因此可以用以下方法区分是网络错误还是其他错误
             // 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
             * @param ex
             * @param isOnCallback
             */
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
               /* if (ex instanceof HttpException) {
                    // 网络错误
                    HttpException httpEx = (HttpException) ex;
                    int responseCode = httpEx.getCode();
                    String responseMsg = httpEx.getMessage();
                    String errorResult = httpEx.getResult();
                    xCallBack.onFail(responseMsg);
                } else {*/
                    // 其他错误
                    xCallBack.onFail(ex.getMessage().toString());
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            // 不管成功或者失败最后都会回调该接口
            }
        });
    }

    /**
     * 普通的get请求(无参数，无缓存)
     * @param params 参数集合
     * @param  xCallBack 请求结果回调
     */
    public static void xUtilsGet(RequestParams params,final XUilsCallBack xCallBack){
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    xCallBack.onResponse(result);
                }
            }
            /**
             * // 注意:如果是自己onSuccess回调方法里写了一些导致程序崩溃的代码，也会回调道该方法，
             * 因此可以用以下方法区分是网络错误还是其他错误
             // 还有一点，网络超时也会也报成其他错误，还需具体打印出错误内容比较容易跟踪查看
             * @param ex
             * @param isOnCallback
             */
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                // 错误
                xCallBack.onFail(ex.getMessage().toString());
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                // 不管成功或者失败最后都会回调该接口
            }
        });
    }
    /**
     *
     * @param url 请求的url
     * @param map 参数集合
     * @param xCallBack  请求回调接口
     */
    public static void xUtilsPost(String url, Map<String,String> map, final XUilsCallBack xCallBack){
        RequestParams params = new RequestParams(url);
        if (null != map && !map.isEmpty()){
            for (Map.Entry<String,String> entry : map.entrySet()){
                params.addBodyParameter(entry.getKey(),entry.getValue());
            }
        }
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    xCallBack.onResponse(result);
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                    // 错误
                    xCallBack.onFail(ex.getMessage().toString());
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });


    }
    /**
     *适配需要添加请求head的参数拼接
     * @param params 参数集合
     * @param xCallBack  请求回调接口
     */
    public static void xUtilsPost(RequestParams params, final XUilsCallBack xCallBack){
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    xCallBack.onResponse(result);
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                    // 错误
                    xCallBack.onFail(ex.getMessage().toString());
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });


    }

    /**
     * 异步请求
     * @param url 请求的url
     * @param map 参数集合
     * @param xCallBack  请求回调接口
     */
    public static void xUtilsRequest(String url, ArrayMap<String,String> map, final XUilsCallBack xCallBack){
        RequestParams params = new RequestParams(url);
        if (null != map && !map.isEmpty()){
            for (Map.Entry<String,String> entry : map.entrySet()){
                params.addBodyParameter(entry.getKey(),entry.getValue());
            }
        }
        x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    xCallBack.onResponse(result);
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                    // 错误
                    xCallBack.onFail(ex.getMessage().toString());
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    /**
     * 异步请求
     * @param params 参数集合
     * @param xCallBack  请求回调接口
     */
    public static void xUtilsRequest( RequestParams params, final XUilsCallBack xCallBack){
        x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    xCallBack.onResponse(result);
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                    // 错误
                    xCallBack.onFail(ex.getMessage().toString());
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }

    /**
     *
     * @param url
     * @param path
     * @param map
     * @param xCallBack
     */
    public static void xUtilsDownloadFile(final String url, String path,ArrayMap<String,String> map, final XUilsCallBack xCallBack){
        RequestParams params = new RequestParams(url);
        if (null != map && !map.isEmpty()){
            for (Map.Entry<String,String> entry : map.entrySet()){
                params.addBodyParameter(entry.getKey(),entry.getValue());
            }
        }
        params.setSaveFilePath(path);//保存文件

        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                File file = result;
                String str = null;
                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    byte[] buffer = new byte[(int)file.length()];
                    inputStream.read(buffer);
                    byte[] decompress;
                    decompress = GZipUtils.decompress(buffer);
                    str = new String(decompress,"GBK");
                    inputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                xCallBack.onResponse(str);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                xCallBack.onFail(ex.getMessage().toString());
            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
            @Override
            public void onWaiting() {
            }
            @Override
            public void onStarted() {
            }
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
            }
        });

    }




}
