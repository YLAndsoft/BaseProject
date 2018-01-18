package fyl.base.constant;

import android.os.Environment;

import java.io.File;

/**
 * Created by DN on 2017/12/5.
 */

public class Constant {

    //链接服务器获取数据的开关
    public static final  boolean isInter = true;
    /**
     * 是否关闭吐司
     * true :打开，false:关闭
     */
    public static final boolean isShowToast = true;
    /**
     * 是否关闭打印的日志
     * true :打开，false:关闭
     */
    public static final boolean isShowLog = true;

    //缓存数据的时间为10分钟
    public static final int maxAge = 60*10;
    //网络请求错误码
    public static final int ERROR = 99;
    //网络请求成功码
    public static final int RESULT = 100;

    public static final  int  PERMISSION_CODE = 100;
    public static final  int  REQUEST_CODE_SETTING = 100;



    //保存地址
    public  static  final  String clearImagePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "360wallpaper";


}
