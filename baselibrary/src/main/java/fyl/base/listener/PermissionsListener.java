package fyl.base.listener;


/**
 * Created by DN on 2018/3/5.
 */

public interface PermissionsListener {
     void onSucceed(int requestCode, Object grantPermissions);//申请成功回调
     void onFailed(int requestCode, Object deniedPermissions); //申请权限回调失败
}
