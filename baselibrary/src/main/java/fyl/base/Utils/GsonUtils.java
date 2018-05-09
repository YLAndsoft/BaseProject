package fyl.base.Utils;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * Gson解析工具类
 * Created by DN on 2018/5/4.
 */

public class GsonUtils {

    private GsonUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    /**
     * 将object对象转成json字符串
     *
     * @param object
     * @return
     */
    public static String gsonString(@NonNull Object object) {
        if(null==object){return null;}
        String gsonString = null;
        try{
            gsonString = new Gson().toJson(object);
        }catch (Exception ex){
            ex.printStackTrace();
            LogUtils.e("gsonString异常："+ex.getMessage().toString());
        }
        return gsonString;
    }

    /**
     * 将gsonString转成泛型bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T gsonToBean(@NonNull String gsonString, Class<T> cls) {
        if(null==gsonString||!gsonString.equals("")){return null;}
        T t = null;
        try{
            t = new Gson().fromJson(gsonString, cls);
        }catch (Exception ex){
            ex.printStackTrace();
            LogUtils.e("gsonToBean异常："+ex.getMessage().toString());
        }
        return t;
    }

    /**
     * 解析返回List类型
     * 泛型在编译期类型被擦除导致报错
     * @param results
     * @return
     */
    public static <T>List<T> getListGosn(@NonNull String results){
        if(null==results||!results.equals("")){return null;}
        List<T> result = null;
        try{
            TypeToken<List<T>> tToken = new TypeToken<List<T>>() {};
            result = new Gson().fromJson(results, tToken.getType());
        }catch (Exception ex){
            ex.printStackTrace();
            LogUtils.e("getListGosn异常信息："+ex.getMessage().toString());
        }
        return result;
    }
    /**
     * 转成list
     * 解决泛型问题
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> gsonToList(@NonNull String json, Class<T> cls) {
        if(null==json||!json.equals("")){return null;}
        Gson gson = new Gson();
        List<T> list = null;
        try{
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for(final JsonElement elem : array){
                list.add(gson.fromJson(elem, cls));
            }
        }catch (Exception ex){
            ex.printStackTrace();
            LogUtils.e("jsonToList异常信息："+ex.getMessage().toString());
        }
        return list;
    }

    /**
     * 转成list中有map的
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> gsonToListMaps(@NonNull String gsonString) {
        if(null==gsonString||!gsonString.equals("")){return null;}
        List<Map<String, T>> list = null;
        try{
            list = new Gson().fromJson(gsonString,new TypeToken<List<Map<String, T>>>() {}.getType());
        }catch (Exception ex){
            ex.printStackTrace();
            LogUtils.e("gsonToListMaps异常信息："+ex.getMessage().toString());
        }
        return list;
    }

    /**
     * 转成map的
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> gsonToMaps(@NonNull String gsonString) {
        if(null==gsonString||!gsonString.equals("")){return null;}
        Map<String, T> map = null;
        Gson gson = new Gson();
        try{
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {}.getType());
        }catch (Exception ex){
            ex.printStackTrace();
            LogUtils.e("gsonToMaps异常信息："+ex.getMessage().toString());
        }
        return map;
    }

}
