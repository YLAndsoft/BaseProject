package fyl.base.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;
import fyl.base.listener.PermissionsListener;


/**
 * 权限工具类
 * Created by DN on 2018/3/5.
 */

public class PerUtils {
    /**
     * 检测单个权限是否开启
     * @param permission
     * @return
     */
    public static boolean isPermission(@NonNull Context context,@NonNull String permission){
        //1.先检测版本是否大于6.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        //2.检测权限是否已经开启
        if(!(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)){
            return false;
        }
        return true;
    }

    /**
     * 检测多个权限是否开启
     * @param permissions 权限组
     * @return
     */
    public static List<String> isPermissionAll(@NonNull Context context,@NonNull String[] permissions){
        List<String> p = null;
        //1.先检测版本是否大于6.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return p;
        //2.检测权限是否已经开启
        for (String permission : permissions) {
            if(!(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)){
                if(p==null){
                    p = new ArrayList<String>();
                }
                p.add(permission);
            }
        }
        return p;
    }

    /**
     * 检测多个权限是否开启
     * @param permissions 权限集合
     * @return 没开启的权限集合
     */
    public static List<String> isPermissionAll(@NonNull Context context, @NonNull List<String> permissions) {
        List<String> p = null;
        //1.先检测版本是否大于6.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return p;
        //2.检测权限是否已经开启
        for (String permission : permissions) {
            if (!(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)) {
                if (p == null) {
                    p = new ArrayList<String>();
                }
                p.add(permission);
            }
        }
        return p;
    }

    /**
     * 请求权限，回调到系统的onRequestPermissionsResult的方法
     * @param context
     * @param per         权限，可以是单个
     * @param requestCode 权限申请回调码
     */
    public static void requestPermission(@NonNull Object context, @NonNull String per, @NonNull int requestCode) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            boolean permission = isPermission(activity, (String) per);
            if (!permission) {
                //去请求权限
                ActivityCompat.requestPermissions(activity, new String[]{(String) per}, requestCode);
            }
        } else if (context instanceof android.support.v4.app.Fragment) {
            android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment) context;
            boolean permission = isPermission(fragment.getActivity(), (String) per);
            if (!permission) {
                //去请求权限
                fragment.requestPermissions(new String[]{(String) per}, requestCode);
            }
        } else {
            throw new RuntimeException("请确认参数context是Activity或者是Fragment");
        }

    }

    /**
     * 请求权限，回调到系统的onRequestPermissionsResult的方法
     * @param context
     * @param per         权限，组
     * @param requestCode 权限申请回调码
     */
    public static void requestPermission(@NonNull Object context, @NonNull String[] per, @NonNull int requestCode) {
        if (per instanceof String[]) {
            if (context instanceof Activity) {
                Activity activity = (Activity) context;
                List<String> permissionAll = isPermissionAll(activity, per);
                if (permissionAll != null && permissionAll.size() > 0) {
                    //去请求权限
                    ActivityCompat.requestPermissions(activity, per, requestCode);
                }
            } else if (context instanceof android.support.v4.app.Fragment) {
                android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment) context;
                List<String> permissionAll = isPermissionAll(fragment.getContext(), per);
                if (permissionAll != null && permissionAll.size() > 0) {
                    //去请求权限
                    fragment.requestPermissions(per, requestCode);
                }
            } else {
                throw new RuntimeException("请确认参数context是Activity或者是Fragment");
            }
        }
    }

    /**
     * 请求权限，回调到系统的onRequestPermissionsResult的方法
     * @param context
     * @param per         权限，集合
     * @param requestCode 权限申请回调码
     */
    public static void requestPermission(@NonNull Object context, @NonNull List<String> per, @NonNull int requestCode) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            List<String> permissionAll = isPermissionAll(activity, per);
            if (permissionAll != null && permissionAll.size() > 0) {
                String[] p = new String[permissionAll.size()];
                for (int i = 0; i < permissionAll.size(); i++) {
                    p[i] = permissionAll.get(i);
                }
                //去请求权限
                ActivityCompat.requestPermissions(activity, p, requestCode);
            }
        } else if (context instanceof android.support.v4.app.Fragment) {
            android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment) context;
            List<String> permissionAll = isPermissionAll(fragment.getContext(), per);
            if (permissionAll != null && permissionAll.size() > 0) {
                String[] p = new String[permissionAll.size()];
                for (int i = 0; i < permissionAll.size(); i++) {
                    p[i] = permissionAll.get(i);
                }
                //去请求权限
                fragment.requestPermissions(p, requestCode);
            }
        } else if(context instanceof android.app.Fragment){
            android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment) context;
            List<String> permissionAll = isPermissionAll(fragment.getContext(), per);
            if (permissionAll != null && permissionAll.size() > 0) {
                String[] p = new String[permissionAll.size()];
                for (int i = 0; i < permissionAll.size(); i++) {
                    p[i] = permissionAll.get(i);
                }
                //去请求权限
                fragment.requestPermissions(p, requestCode);
            }
        }else {
            throw new RuntimeException("请确认参数context是Activity或者是Fragment");
        }
    }


    /**
     * 请求返回结果
     * @param requestCode  request code.
     * @param permissions  one or more permissions.
     * @param grantResults results.
     * @param listener     {@link PermissionsListener}.
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[]
            grantResults, @NonNull PermissionsListener listener) {
        List<String> grantedList = new ArrayList<>();
        List<String> deniedList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                grantedList.add(permissions[i]);//同意
            else
                deniedList.add(permissions[i]);//拒绝
        }
        if (deniedList.isEmpty())
            listener.onSucceed(requestCode, grantedList);
        else
            listener.onFailed(requestCode, deniedList);
    }


    /**
     * 某些永久禁用的特权，可能需要在执行中设置。
     * @param context
     * @param deniedPermissions one or more permissions.
     * @return true, false.
     */
    public static boolean hasAlwaysDeniedPermission(@NonNull Object context, @NonNull List<String>deniedPermissions) {
        for (String deniedPermission : deniedPermissions) {
            if (!shouldShowRationalePermissions(context, deniedPermission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测权限是否是被拒绝，不在提醒
     * @param o
     * @param permissions
     * @return
     */
    static boolean shouldShowRationalePermissions(Object o, String... permissions) {
        if (Build.VERSION.SDK_INT < 23) return false;
        boolean rationale = false;
        for (String permission : permissions) {
            if (o instanceof Activity) {
                rationale = ActivityCompat.shouldShowRequestPermissionRationale((Activity) o, permission);
            } else if (o instanceof android.support.v4.app.Fragment) {
                rationale = ((android.support.v4.app.Fragment) o).shouldShowRequestPermissionRationale(permission);
            } else if (o instanceof android.app.Fragment) {
                rationale = ((android.app.Fragment) o).shouldShowRequestPermissionRationale(permission);
            }
            if (rationale) return true;
        }
        return false;
    }


    public void execute(Object object,int requestCode) {
        Context context = getContext(object);
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        startForResult(object, intent, requestCode);
    }

    /**
     * 获取context
     * @param o
     * @return
     */
    static Context getContext(Object o) {
        if (o instanceof Activity)
            return (Activity) o;
        else if (o instanceof Fragment)
            return ((Fragment) o).getActivity();
        else if (o instanceof android.app.Fragment)
            return ((android.app.Fragment) o).getActivity();
        throw new IllegalArgumentException("The " + o.getClass().getName() + " is not support.");
    }

    /**
     * 跳转至应用权限界面去开启权限
     * @param object
     * @param intent
     * @param requestCode
     */
    public static void startForResult(Object object, Intent intent, int requestCode) {
        if (object instanceof Activity) {
            ((Activity) object).startActivityForResult(intent, requestCode);
        } else if (object instanceof android.support.v4.app.Fragment) {
            ((android.support.v4.app.Fragment) object).startActivityForResult(intent, requestCode);
        } else if (object instanceof android.app.Fragment) {
            ((android.app.Fragment) object).startActivityForResult(intent, requestCode);
        }
    }



}

