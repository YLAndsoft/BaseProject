package pro.base.com.baseproject.demo6.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 监听应用安装
 * Created by DN on 2018/3/10.
 */

public class InstallReceiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)){ //安装
            String packageName = intent.getDataString();
            Toast.makeText(context, "安装成功" + packageName, Toast.LENGTH_LONG).show();
        } else if(intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)){//卸载
            String packageName = intent.getDataString();
            Toast.makeText(context, "卸载成功" + packageName, Toast.LENGTH_LONG).show();
        }else if(intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)){//替换
            String packageName = intent.getDataString();
            Toast.makeText(context, "替换成功" + packageName, Toast.LENGTH_LONG).show();
        }

    }


}
