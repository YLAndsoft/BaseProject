package pro.base.com.baseproject.demo5.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pro.base.com.baseproject.demo5.entity.ChatMessage;

/**
 * Created by DN on 2018/2/10.
 */

public class DManager {
    private DbOpenHelper DBhelper;
    private static DManager cDao=null;

    public DManager(Context context){
        this.DBhelper = DbOpenHelper.getInstance(context);
    }

    public static DManager init(Context context){
        if(cDao==null){
            cDao = new DManager(context);
        }
        return cDao;
    }

    /*public static final String TB_QA_ID = "_id";
    public static final String TB_QA_UID = "chat_uID";//用户ID
    public static final String TB_QA_USERNAME = "_userName";//用户名称
    public static final String TB_QA_HEADIMAGE = "_image";//用户头像
    public static final String TB_QA_MESSGAE = "_message";//发送的消息
    public static final String TB_QA_CHAT_NAME = "chat_name";//接收消息的用户名称
    public static final String TB_QA_CHAT_IMAGE = "chat_image";//接收消息的头像*/

    /**
     * 增加一条聊天记录
     * @param chatMessage 消息的实体
     * @return
     */
    public boolean insert(ChatMessage chatMessage){
        long result = 0L;
        SQLiteDatabase db = this.DBhelper.getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(DBConstant.TB_RE_UID, chatMessage.getRecevice_iD());
            values.put(DBConstant.TB_SE_USERNAME, chatMessage.getSend_userName());
            values.put(DBConstant.TB_SE_HEADIMAGE, chatMessage.getSend_userHead());
            values.put(DBConstant.TB_SE_MESSGAE, chatMessage.getSend_Message());
            values.put(DBConstant.TB_RE_NAME, chatMessage.getRecevice_userName());
            values.put(DBConstant.TB_RE_IMAGE, chatMessage.getRecevice_userHead());
            values.put(DBConstant.TB_TIME, chatMessage.getSend_time());
            values.put(DBConstant.TB_TYPE, chatMessage.getType());
            result = db.insert(DBConstant.TB_CHAT, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return result > 0;
    }

    //查询
    public List<ChatMessage> queryAll() {
        List<ChatMessage> lists = new ArrayList<>();
        SQLiteDatabase db = this.DBhelper.getWritableDatabase();
        Cursor cursor = null;
        try {
            db.beginTransaction();
            cursor = db.query(DBConstant.TB_CHAT, null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setRecevice_iD(cursor.getString(DBConstant.CHAT_INDEX_RE_UID));
                chatMessage.setSend_userName(cursor.getString(DBConstant.CHAT_INDEX_SE_USERNAME));
                chatMessage.setSend_userHead(cursor.getString(DBConstant.CHAT_INDEX_SE_HEADIMAGE));
                chatMessage.setSend_Message(cursor.getString(DBConstant.CHAT_INDEX_SE_MESSGAE));
                chatMessage.setRecevice_userName(cursor.getString(DBConstant.CHAT_INDEX_RE_NAME));
                chatMessage.setRecevice_userHead(cursor.getString(DBConstant.CHAT_INDEX_RE_IMAGE));
                try{
                    chatMessage.setSend_time(Long.parseLong(cursor.getString(DBConstant.CHAT_INDEX_TIME)));
                }catch (Exception ex){
                    ex.printStackTrace();
                    chatMessage.setSend_time(new Date().getTime());
                }
                chatMessage.setType(cursor.getInt(DBConstant.CHAT_INDEX_TYPE));
                lists.add(chatMessage);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.endTransaction();
        }
        return lists;
    }

    /**
     * 根据uID查询聊天记录
     * @param uID
     * @return
     */
    public List<ChatMessage> queryChatMessage(String uID) {
        List<ChatMessage> lists = new ArrayList<>();
        ChatMessage chatMessage = null;
        SQLiteDatabase db = this.DBhelper.getWritableDatabase();
        Cursor cursor = null;
        try {
            db.beginTransaction();
            cursor = db.query(DBConstant.TB_CHAT, null, DBConstant.TB_RE_UID + "=?", new String[] { uID }, null, null, null);
            while (cursor.moveToNext()) {
                chatMessage = new ChatMessage();
                chatMessage.setRecevice_iD(cursor.getString(DBConstant.CHAT_INDEX_RE_UID));
                chatMessage.setSend_userName(cursor.getString(DBConstant.CHAT_INDEX_SE_USERNAME));
                chatMessage.setSend_userHead(cursor.getString(DBConstant.CHAT_INDEX_SE_HEADIMAGE));
                chatMessage.setSend_Message(cursor.getString(DBConstant.CHAT_INDEX_SE_MESSGAE));
                chatMessage.setRecevice_userName(cursor.getString(DBConstant.CHAT_INDEX_RE_NAME));
                chatMessage.setRecevice_userHead(cursor.getString(DBConstant.CHAT_INDEX_RE_IMAGE));
                try{
                    chatMessage.setSend_time(Long.parseLong(cursor.getString(DBConstant.CHAT_INDEX_TIME)));
                }catch (Exception ex){
                    ex.printStackTrace();
                    chatMessage.setSend_time(new Date().getTime());
                }
                chatMessage.setType(cursor.getInt(DBConstant.CHAT_INDEX_TYPE));
                lists.add(chatMessage);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.endTransaction();
        }
        return lists;
    }

    //修改


    /**
     * 删除聊天记录
     * @param userId 发送人的id
     * @return 返回结果
     */
    public boolean delete(String userId){
        long result = 0L;
        SQLiteDatabase db = this.DBhelper.getWritableDatabase();
        try {
            db.beginTransaction();
            String[] whereArgs = { userId };
            result = db.delete(DBConstant.TB_CHAT, DBConstant.TB_RE_UID + "=?", whereArgs);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return result > 0;
    }





}
