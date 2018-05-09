package pro.base.com.baseproject.demo2;

import android.support.annotation.NonNull;
import org.xutils.ex.DbException;
import java.util.List;

import fyl.base.Utils.LogUtils;
import pro.base.com.baseproject.MyAppLication;

/**
 * 数据库使用辅助类
 * Created by DN on 2018/4/23.
 */

public class DBManageHelper {

    private DBManageHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 增加单个
     * @param  data 添加的数据 User
     * @param  clazz 增加的类型 User.class/String.class
     * @return 返回所有对象
     */
    public static <T>List<T> insert(@NonNull T data,@NonNull Class<T> clazz){
        List<T> lists = null;//查询所有
        try {
            //db.save(app);//保存成功之后【不会】对user的主键进行赋值绑定
            //db.saveOrUpdate(user);//保存成功之后【会】对user的主键进行赋值绑定
            //db.saveBindingId(user);//保存成功之后【会】对user的主键进行赋值绑定,并返回保存是否成功
            //MyAppLication.db.save(data);//对user的主键进行赋值绑定,并返回保存是否成功
            MyAppLication.db.saveBindingId(data);//对user的主键进行赋值绑定,并返回保存是否成功
            lists = queryAll(clazz);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("增加单个insert异常:"+ex.getMessage().toString());
            return null;
        }
        return lists;
    }
    /**
     * 增加集合
     * @param list 增加的集合 List<User>/List<String>
     * @param  clazz 增加的数据类型 User.class/String.class
     * @return 返回所有对象
     */
    public static <T>List<T> insert(@NonNull List<T> list,@NonNull Class<T> clazz){
        List<T> lists = null;//查询所有
        try {
            MyAppLication.db.save(list);
            lists = queryAll(clazz);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("增加集合insert异常:"+ex.getMessage().toString());
            return null;
        }
        return lists;
    }

    /**
     * 查询所有
     * @param clazz 查询的类型
     * @return 返回所有对象
     */
    public static <T>List<T> queryAll(@NonNull Class<T> clazz){
        List<T> mList = null;//查询所有
        try {
            mList = MyAppLication.db.findAll(clazz);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("查询所有queryAll异常:"+ex.getMessage().toString());
            return null;
        }
        return mList;
    }

    /**
     * 单个查询
     * @param clazz 查询的类型
     * @param position 查询第几条数据
     * @return 对象
     */
    public static <T> T queryClazz(@NonNull Class<T> clazz, @NonNull int position){
        T byId = null;
        try {
            byId = MyAppLication.db.findById(clazz, position);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("单个查询queryClazz异常:"+ex.getMessage().toString());
            return null;
        }
        return byId;
    }


    /**
     * 删除表中所有的clazz对象
     * @TODO 【慎用】
     * @param clazz 删除的数据类型
     * @return 返回所有对象
     */
    public static <T>List<T> deleteAll(@NonNull Class<T> clazz){
        List<T> tList =null;
        try {
            //db.delete(users.get(0)); //删除第一个对象
            //db.delete(User.class);//删除表中所有的User对象【慎用】
            //db.delete(users); //删除users对象集合
            MyAppLication.db.delete(clazz);
            tList = queryAll(clazz);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("deleteAll异常:"+ex.getMessage().toString());
            return null;
        }
        return tList;
    }

    /**
     * //删除第position个对象
     * @param clazz 删除的类型，
     * @param position 第几个对象
     * @return 返回所有对象
     */
    public static <T>List<T> deleteById(@NonNull Class<T> clazz,@NonNull int position){
        List<T> tList=null;
        try {
            List<T> list = queryAll(clazz);
            if(null!=list&&list.size()>0){
                MyAppLication.db.delete(list.get(position));
                tList = queryAll(clazz);
            }
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("第position个对象,deleteById()异常:"+ex.getMessage().toString());
            return null;
        }
        return tList;
    }

    /**
     * //删除对象集合
     * @param clazz 删除的类型，
     * @param list 对象集合
     * @return 返回所有对象
     */
    public static <T>List<T> deleteById(@NonNull List<T> list,@NonNull Class<T> clazz){
        List<T> tList=null;
        try {
            MyAppLication.db.delete(list);
            tList = queryAll(clazz);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("对象集合deleteById()异常:"+ex.getMessage().toString());
            return null;
        }
        return tList;
    }

    /**
     * 修改单个对象
     * @param data 修改的数据
     * @param clazz 修改数据的类型
     * @return 返回所有数据column
     */
    public static <T>List<T> update(@NonNull T data,@NonNull Class<T> clazz){
        List<T> ts= null;
        try {
            //db.replace(user);
            //db.update(user);
            //db.update(user,"email");//指定只对email列进行更新
            MyAppLication.db.update(data);
            ts = queryAll(clazz);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("修改单个对象update()异常:"+ex.getMessage().toString());
            return null;
        }
        return ts;
    }


    /**
     * 修改单个对象的指定列
     */
    public static <T>List<T> updateColumn(@NonNull T data,@NonNull String column,@NonNull Class<T> clazz){
        List<T> ts= null;
        try {
            MyAppLication.db.update(data,column);
            ts = queryAll(clazz);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("修改单个对象update()异常:"+ex.getMessage().toString());
            return null;
        }
        return ts;
    }


}
