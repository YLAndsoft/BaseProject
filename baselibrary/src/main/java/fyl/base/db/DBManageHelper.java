package fyl.base.db;

import android.support.annotation.NonNull;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;
import java.io.File;
import java.util.List;
import fyl.base.Utils.LogUtils;
import fyl.base.Utils.StringUtils;

/**
 * 数据库使用辅助类
 * Created by DN on 2018/4/23.
 */

public class DBManageHelper {

    private static DbManager db;
    private static String DBNAME = "test.db";//默认数据库名称
    private static String DBPATH = "sdcard/SitSmice/iDEvent/DownLoad/dbPath";// 默认数据库存储路径

    private DBManageHelper() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 创建数据库
     * @param dbName 数据库名称 xxx.db 命名
     * @param  dbPath 数据库存储路径
     * @param  listener 更新数据库监听
     */
    public static void initDB(@NonNull String dbName, @NonNull String dbPath, DbManager.DbUpgradeListener listener){
        if(!StringUtils.isEmpty(dbName))DBNAME  = dbName;
        if(!StringUtils.isEmpty(dbName))DBPATH = dbPath;
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName(DBNAME)//设置数据库名称
                // 不设置dbDir时, 默认存储在app的私有目录.
                .setDbDir(new File(DBPATH)) // 数据库存储路径
                .setDbVersion(1)//设置数据库版本
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                });
        if(listener!=null){
            daoConfig.setDbUpgradeListener(listener);//更新数据库监听
        }
        //db还有其他的一些构造方法，比如含有更新表版本的监听器的
        db = x.getDb(daoConfig);//获取数据库单例
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
            boolean b = db.saveBindingId(data);//对user的主键进行赋值绑定,并返回保存是否成功
            if(b){
                lists = queryAll(clazz);
            }
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("增加单个insert异常:"+ex);
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
        List<T> lists ;//查询所有
        try {
            db.saveBindingId(list);
            lists = queryAll(clazz);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("增加集合insert异常:"+ex);
            return null;
        }
        return lists;
    }

    /**
     * 查询所有
     * @param clazz 查询的类型
     * @return 返回所有对象
     */
    public static <T>List<T> queryAll(Class<T> clazz){
        List<T> mList ;//查询所有
        try {
            mList = db.findAll(clazz);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("查询所有queryAll异常:"+ex);
            return null;
        }
        return mList;
    }

    /**
     * 单个查询
     * @param clazz 查询的类型
     * @param index 查询第几条数据
     * @return 对象
     */
    public static <T> T queryClazz(Class<T> clazz, int index){
        T byId ;
        try {
            byId = db.findById(clazz, index);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("单个查询queryClazz异常:"+ex);
            return null;
        }
        return byId;
    }

    /**
     * 根据ID查询
     * @param clazz 查询的实体类类型
     * @param appId appID
     * @return
     */
    public static <T>T queryClazzID(Class<T> clazz,int appId){
        try {
            //MyApplication.db.selector(clazz).where("id","=",appId).and("id","<",4).findAll();
            List<T> appid = db.selector(clazz).where("APPID", "=", appId).findAll();
            if(null==appid||appid.size()<=0){
                return null;
            }
            return appid.get(0);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("根据ID查询queryClazzID异常:"+ex);
            return null;
        }
    }

    /**
     * 根据下载地址查询
     * @param clazz 查询的实体类类型
     * @param appUrl appUrl
     * @return
     */
    public static <T>T queryClazzApkUrl(Class<T> clazz,String appUrl){
        try {
            //MyApplication.db.selector(clazz).where("id","=",appId).and("id","<",4).findAll();
            List<T> appid = db.selector(clazz).where("APPAPKURL", "=", appUrl).findAll();
            if(null==appid||appid.size()<=0){
                return null;
            }
            return appid.get(0);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("根据ID查询queryClazzID异常:"+ex);
            return null;
        }
    }
    /**
     * 根据key，values查询
     * @param clazz 查询的实体类类型
     * @param key appUrl
     * @return
     */
    public static <T>List<T> queryClazzKeyValue(Class<T> clazz,String key,String values){
        try {
            //MyApplication.db.selector(clazz).where("id","=",appId).and("id","<",4).findAll();
            List<T> apps= db.selector(clazz).where(key, "=", values).findAll();
            if(null==apps||apps.size()<=0){
                return null;
            }
            return apps;
        } catch (DbException e) {
            e.printStackTrace();
        }catch (Exception ex){
            LogUtils.e("根据ID查询queryClazzID异常:"+ex);

        }
        return null;
    }


    /**
     * 删除表中所有的clazz对象
     * @TODO 【慎用】
     * @param clazz 删除的数据类型
     * @return 返回所有对象
     */
    public static <T>List<T> deleteAll(Class<T> clazz){
        List<T> tList;
        try {
            //db.delete(users.get(0)); //删除第一个对象
            //db.delete(User.class);//删除表中所有的User对象【慎用】
            //db.delete(users); //删除users对象集合
            db.delete(clazz);
            tList = queryAll(clazz);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("deleteAll异常:"+ex);
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
    public static <T>List<T> deleteById(Class<T> clazz,int position){
        List<T> tList=null;
        try {
            List<T> list = queryAll(clazz);
            if(null!=list&&list.size()>0){
                db.delete(list.get(position));
                tList = queryAll(clazz);
            }
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("第position个对象,deleteById()异常:"+ex);
            return null;
        }
        return tList;
    }
    /**
     * //删除指定对象
     * @param clazz 删除的类型，
     * @param  t 指定的对象
     * @return 返回所有对象
     */
    public static <T>List<T> deleteEntity(Class<T> clazz,T t){
        List<T> tList=null;
        try {
            List<T> list = queryAll(clazz);
            if(null!=list&&list.size()>0){
                db.delete(t);
                tList = queryAll(clazz);
            }
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("第position个对象,deleteById()异常:"+ex);
            return null;
        }
        return tList;
    }

    /**
     * 删除对象集合
     * @param clazz 删除的类型，
     * @param list 对象集合
     * @return 返回所有对象
     */
    public static <T>List<T> deleteById(List<T> list,Class<T> clazz){
        List<T> tList;
        try {
            db.delete(list);
            tList = queryAll(clazz);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("对象集合deleteById()异常:"+ex);
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
    public static <T>List<T> update(T data,Class<T> clazz){
        List<T> ts;
        try {
            //db.replace(user);
            //db.update(user);
            //db.update(user,"email");//指定只对email列进行更新
            db.update(data);
            ts = queryAll(clazz);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("修改单个对象update()异常:"+ex);
            return null;
        }
        return ts;
    }


    /**
     * 修改单个对象的指定列
     * @param data
     * @param column
     * @param clazz
     * @return
     */
    public static <T>List<T> updateColumn(T data,String column,Class<T> clazz){
        List<T> ts;
        try {
            db.update(data,column);
            ts = queryAll(clazz);
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }catch (Exception ex){
            LogUtils.e("修改单个对象update()异常:"+ex);
            return null;
        }
        return ts;
    }




}
