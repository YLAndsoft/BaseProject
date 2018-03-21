package fyl.base.constant;

/**
 * 此类主要存放Dangerous敏感权限
 * Created by DN on 2018/3/5.
 */

public class PermissionsConstant {

    /**
     * Dangerous敏感权限
     */
    public static String [] PermissionsD = {
            "android.permission.READ_CALENDAR",//日历
            "android.permission.WRITE_CALENDAR",//日历
            "android.permission.CAMERA",//相机
            "android.permission.READ_CONTACTS",//联系人
            "android.permission.WRITE_CONTACTS",//联系人
            "android.permission.GET_ACCOUNTS",//联系人
            "android.permission.ACCESS_FINE_LOCATION",//定位
            "android.permission.ACCESS_COARSE_LOCATION",//定位
            "android.permission.RECORD_AUDIO",//麦克风
            "android.permission.READ_PHONE_STATE",//PHONE
            "android.permission.CALL_PHONE",//PHONE
            "android.permission.READ_CALL_LOG",//PHONE
            "android.permission.WRITE_CALL_LOG",//PHONE
            "android.permission.USE_SIP",//PHONE
            "android.permission.PROCESS_OUTGOING_CALLS",//PHONE
            "android.permission.BODY_SENSORS",//传感器
            "android.permission.SEND_SMS",//SMS
            "android.permission.RECEIVE_SMS",//SMS
            "android.permission.READ_SMS",//SMS
            "android.permission.RECEIVE_WAP_PUSH",//SMS
            "android.permission.RECEIVE_MMS",//SMS
            "android.permission.READ_EXTERNAL_STORAGE",//存储
            "android.permission.WRITE_EXTERNAL_STORAGE",//存储
    };

    /**
     * Normal权限
     */
    public static String [] PermissionsN = {
            "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS",
            "android.permission.ACCESS_NETWORK_STATE",
            "android.permission.ACCESS_NOTIFICATION_POLICY",
            "android.permission.ACCESS_WIFI_STATE",
            "android.permission.BLUETOOTH",
            "android.permission.BLUETOOTH_ADMIN",
            "android.permission.BROADCAST_STICKY",
            "android.permission.CHANGE_NETWORK_STATE",
            "android.permission.CHANGE_WIFI_MULTICAST_STATE",
            "android.permission.CHANGE_WIFI_STATE",
            "android.permission.DISABLE_KEYGUARD",
            "android.permission.EXPAND_STATUS_BAR",
            "android.permission.GET_PACKAGE_SIZE",
            "android.permission.INTERNET",
            "android.permission.KILL_BACKGROUND_PROCESSES",
            "android.permission.MODIFY_AUDIO_SETTINGS",
            "android.permission.NFC",
            "android.permission.READ_SYNC_SETTINGS",
            "android.permission.READ_SYNC_STATS",
            "android.permission.RECEIVE_BOOT_COMPLETED",
            "android.permission.REORDER_TASKS",
            "android.permission.REQUEST_INSTALL_PACKAGES",
            "android.permission.SET_TIME_ZONE",
            "android.permission.SET_WALLPAPER",
            "android.permission.SET_WALLPAPER_HINTS",
            "android.permission.TRANSMIT_IR",
            "android.permission.USE_FINGERPRINT",
            "android.permission.VIBRATE",
            "android.permission.WAKE_LOCK",
            "android.permission.WRITE_SYNC_SETTINGS",
    };


}
