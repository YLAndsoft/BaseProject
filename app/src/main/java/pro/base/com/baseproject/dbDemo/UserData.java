package pro.base.com.baseproject.dbDemo;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by DN on 2018/1/19.
 */

public class UserData {

    public static List<User> getUserData(String jsonName,Context context){
        List<User> user = null;
        try{
            Gson gson = new Gson();
            String json = isPrassString(jsonName,context);
            if (json != null) {
                TypeToken<List<User>> type = new TypeToken<List<User>>() {};
                user = (List<User>) gson.fromJson(json,type.getType());
            }
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        return user;
    }

    public static String isPrassString(String jsonName,Context context){
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
}
