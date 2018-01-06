package pro.base.com.baselibrary;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by DN on 2017/12/29.
 */

public class Test {
    public static void showToast(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
