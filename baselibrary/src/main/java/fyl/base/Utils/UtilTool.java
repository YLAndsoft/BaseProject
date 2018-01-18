package fyl.base.Utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by DN on 2017/9/6.
 */

public class UtilTool {
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    // 保留2位小数
    public static double get2Double(Double a) {
        DecimalFormat df = new DecimalFormat("0.00");
        return new Double(df.format(a).toString());
    }

    /**
     * 对内存数进行格式化
     *
     * @param memory
     *            KB
     * @return
     */
    public static String format(long memory) {
        DecimalFormat df = new DecimalFormat("#.##");

        long memory1 = memory / 1024;
        // 1M
        if (memory1 < 1024) {
            return memory1 + "KB";
        }
        // 1G
        else if (memory1 < 1024 * 1024) {
            //大于500，小于900就/10
            if(500<(memory1 / 1024f)&&(memory1 / 1024f)<900){
                return df.format((float) (memory1 / 1024f)/10) + "MB";
            }else if((memory1 / 1024f)<10){
                return df.format((float) (memory1 / 1024f)*15) + "MB";
            }else if(10<=(memory1 / 1024f)&&(memory1 / 1024f)<100){
                return df.format((float) (memory1 / 1024f)) + "MB";
            }else if(100<=(memory1 / 1024f)&&(memory1 / 1024f)<500){
                return df.format((float) (memory1 / 1024f)/3) + "MB";
            }else if(900<=(memory1 / 1024f)){
                return df.format((float) (memory1 / 1024f)/5) + "MB";
            }

        }else{
            //大于一个G
            return df.format((float) (memory1 / 1024f)/100) + "MB";
        }
        //return null;
        return null;
    }


    public static String format1(long memory) {
        DecimalFormat df = new DecimalFormat("#.##");

        long memory1 = memory / 1024;
        // 1M
        if (memory1 < 1024) {
            return memory1+"";
        }
        // 1G
        else if (memory1 < 1024 * 1024) {
            return df.format((float) memory1 / 1024f);
        }
        return null;
    }

    public static String isMobile(String mobile) {
        String str = "";
        if (isStrNull(mobile)) {
            return str;
        } else {
            if (isMobileNO(mobile)) {
                StringBuffer buffer = new StringBuffer();
                buffer.append(mobile.substring(0, 3));
                buffer.append("****");
                buffer.append(mobile.substring(7, mobile.length()));
                return buffer.toString();
            } else {
                return mobile;
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static boolean isStrNull(String str) {
        if (str == null || "".equals(str) || "null".equals(str)) {
            return true;
        }
        return false;
    }

    public static boolean isExtNull(List<?> list) {
        if (list == null || list.isEmpty() || list.size() == 0) {
            return true;
        }
        return false;
    }

    public static long toLong(Object obj) {
        try {
            if (obj != null && !"".equals(obj)) {
                return Long.parseLong(obj.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
		/*
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (isStrNull(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }
}
