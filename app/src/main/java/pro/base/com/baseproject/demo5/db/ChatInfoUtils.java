package pro.base.com.baseproject.demo5.db;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import fyl.base.Utils.SPUtils;
import pro.base.com.baseproject.Constant;
import pro.base.com.baseproject.demo5.entity.ChatMessage;
import pro.base.com.baseproject.demo5.utils.ListUtils;

/**
 * 用户聊友信息
 * 获取，保存，删除，查询聊天信息
 * Created by DN on 2018/2/12.
 */

public class ChatInfoUtils {


    /**
     * 检查聊友记录是否存在
     * @return
     */
    public static boolean isChatInfo(List<ChatMessage> chatInfo,String uId){
        if(chatInfo!=null&&chatInfo.size()>0){
            boolean isExist = false;
            for(ChatMessage cm:chatInfo){
                //如果检查的ID和要检查的ID相等，说明存在
                if(cm.getRecevice_iD().equals(uId)){
                    isExist = true;
                    break;
                }
            }
            return isExist;
        }
        return false;
    }

    /**
     * 保存聊天信息界面
     * @param context
     * @param newChatMessage
     */
    public static List<ChatMessage>  saveChatInfo(Context context, ChatMessage newChatMessage){
        List<ChatMessage> newlist = null;
        try{
            if(newChatMessage!=null){
                newlist = SPUtils.getInstance(context).getList2(Constant.USER_INFO_LIST, ChatMessage.class);
                if(newlist!=null&&newlist.size()>0){
                    //判断要保存的这条消息是否存在，存在就去替换，不存在就直接添加
                    boolean chatInfo = isChatInfo(newlist, newChatMessage.getRecevice_iD());
                    if(chatInfo){
                        List<ChatMessage> list = new ArrayList<>();
                        for(ChatMessage cm:newlist){
                            if(cm.getRecevice_iD().equals(newChatMessage.getRecevice_iD())){
                                list.add(newChatMessage);
                            }else{
                                list.add(cm);
                            }
                        }
                        //排序
                        List<ChatMessage> chatMessages = ListUtils.upsideDownLong(list);
                        SPUtils.getInstance(context).setList2(Constant.USER_INFO_LIST, chatMessages);
                        return chatMessages;
                    }else{
                        newlist.add(newChatMessage);
                    }
                }else{
                    newlist = new ArrayList<>();
                    newlist.add(newChatMessage);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        List<ChatMessage> chatMessages = ListUtils.upsideDownLong(newlist);
        SPUtils.getInstance(context).setList2(Constant.USER_INFO_LIST, chatMessages);
        return chatMessages;
    }


    /**
     * 查询聊天信息
     * @param context
     * @return
     */
    public static List<ChatMessage> queryChatInfo(Context context) {
        List<ChatMessage> list = null;
        try {
            list = SPUtils.getInstance(context).getList2(Constant.USER_INFO_LIST, ChatMessage.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    /**
     * 删除聊天信息
     * @param context
     * @param toChatID
     * @return
     */
    public static List<ChatMessage> deleteChatInfo(Context context, String toChatID ){
        List<ChatMessage> newList = new ArrayList<>();
        List<ChatMessage> chatMe = null;
        try {
            List<ChatMessage> chatMessages = queryChatInfo(context);
            if(chatMessages!=null&&chatMessages.size()>0){
                for(ChatMessage cm:chatMessages){
                    if(!cm.getRecevice_iD().equals(toChatID)){
                        newList.add(cm);
                    }
                }
                //保存
                chatMe = ListUtils.upsideDownLong(newList);
                SPUtils.getInstance(context).setList2(Constant.USER_INFO_LIST, chatMe);
                return chatMe;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return chatMe;
    }

    /**
     * 删除聊天记录
     * @param context
     * @param toChatID
     */
    public static void deleteChatRecord(Context context, String toChatID ){
        List<ChatMessage> newList = new ArrayList<>();
        List<ChatMessage> chatMe = null;
        try{
            List<ChatMessage> chatMessages = queryChatInfo(context);
            if(chatMessages!=null&&chatMessages.size()>0){
                for(ChatMessage cm:chatMessages){
                    if(cm.getRecevice_iD().equals(toChatID)){
                        cm.setSend_Message("");
                        newList.add(cm);
                    }else{
                        newList.add(cm);
                    }
                }
                //保存
                chatMe = ListUtils.upsideDownLong(newList);
                SPUtils.getInstance(context).setList2(Constant.USER_INFO_LIST, chatMe);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }



}
