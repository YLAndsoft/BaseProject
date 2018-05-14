package fyl.base.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * UI相关工具类
 * Created by DN on 2017/10/16.
 */

public class UiUtils {
    public static Toast mToast;

    public static void showToast(String msg, Context context) {
        if (mToast == null) {
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }



    /**
     * 获取到字符数组
     * @param tabNames  字符数组的id
     */
    public static String[] getStringArray(int tabNames) {
        return getStringArray(tabNames);
    }

    public static Resources getResource(Context context) {
        return context.getResources();
    }

    /** dip转换px */
    public static int dip2px(int dip,Context context) {
        final float scale = getResource(context).getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /** px转换dip */

    public static int px2dip(int px,Context context) {
        final float scale = getResource(context).getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }



    public static Drawable getDrawalbe(int id,Context context) {
        return getResource(context).getDrawable(id);
    }

    public static int getDimens(int homePictureHeight,Context context) {
        return (int) getResource(context).getDimension(homePictureHeight);
    }
    /**
     * 延迟执行 任务
     * @param run   任务
     * @param time  延迟的时间
     */
    public static void postDelayed(Runnable run, int time) {
        postDelayed(run, time); // 调用Runable里面的run方法
    }
    /**
     * 取消任务
     * @param auToRunTask
     */
    public static void cancel(Runnable auToRunTask) {
        //BaseApplication.getHandler().removeCallbacks(auToRunTask);
    }
    /**
     * 可以打开activity
     * @param intent
     */
    public static void startActivity(Intent intent,Context context) {
        // 如果不在activity里去打开activity  需要指定任务栈  需要设置标签
        if(context==null){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }else{
            context.startActivity(intent);
        }
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            LogUtils.w("listAdapter.getCount="+listAdapter.getCount());
            View listItem = listAdapter.getView(i, null, listView);
            LogUtils.w("listItem="+i);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
