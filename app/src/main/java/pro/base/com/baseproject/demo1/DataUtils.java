package pro.base.com.baseproject.demo1;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import pro.base.com.baseproject.demo1.entity.User;

/**
 * Created by DN on 2018/2/7.
 */

public class DataUtils {

    public static List<User> getData(int size,Context context){
        String assetsName = "User"+size+".json";
        return getUserData(assetsName,context);
    }
    public static List<User> getUserData(String jsonName,Context context){
        /*try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        List<User> fromJson = null;
        try{
            String json = isPrassString(jsonName, context);
            Gson gson = new Gson();
            if (json != null) {
                TypeToken<List<User>> type = new TypeToken<List<User>>() {};
                fromJson = (List<User>) gson.fromJson(json,type.getType());
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return fromJson;
    }

    private static String isPrassString(String jsonName,Context context){
        InputStream is;
        try {
            is = context.getAssets().open(jsonName);
            if (is != null) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String readLine = br.readLine();
                br.close();
                isr.close();
                is.close();
                return readLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<String> getImages(){
        List<String> images = new ArrayList<>();
        images.add("http://pic48.nipic.com/file/20140912/7487939_223908808000_2.jpg");
        images.add("http://pic48.nipic.com/file/20140912/7487939_223925236000_2.jpg");
        images.add("http://pic48.nipic.com/file/20140912/7487939_224239519000_2.jpg");
        images.add("http://pic48.nipic.com/file/20140912/7487939_224002930000_2.jpg");
        images.add("http://pic48.nipic.com/file/20140912/7487939_224356073000_2.jpg");
        return images;
    }
    public static List<String> getImageTitle(){
        List<String> images = new ArrayList<>();
        images.add("睡美人");
        images.add("睡衣的诱惑");
        images.add("清纯美眉");
        images.add("沙发美眉的诱惑");
        images.add("白衣妹子");

        return images;
    }

}
