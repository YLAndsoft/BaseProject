package pro.base.com.baseproject.demo5.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 聊天信息实体类
 * Created by DN on 2018/2/10.
 */

public class ChatMessage implements Serializable{
    private String recevice_iD;//接收人的id
    private String send_userName;//发送人的名称
    private String send_userHead;//发送人的头像
    private String recevice_userName;//接收人的名称
    private String recevice_userHead;//接收人的头像
    private String send_Message;//发送的消息
    private long send_time;//发送时间
    private int type ;//消息类型

    /**
     * 创建一条消息(注意：这里只是创建，并未保存到数据库中)
     * @param toChatUserid 接收消息的用户ID
     * @param toChatUsername 接收消息的用户名称
     * @param toChatUserhead    接收消息的用户头像
     * @param send_userName 发送消息的用户名称
     * @param send_userHead 发送消息的用户头像
     * @param content
     * @return
     */
    public static ChatMessage createTxtSendMessage(String toChatUserid, String toChatUsername,
                                                   String toChatUserhead,String send_userName,
                                                   String send_userHead,String content) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSend_userName(send_userName);
        chatMessage.setSend_userHead(send_userHead);
        if(toChatUserid==null){
            return null;
        }
        chatMessage.setRecevice_iD(toChatUserid);
        if(toChatUsername!=null){
            chatMessage.setRecevice_userName(toChatUsername);
        }else{
            chatMessage.setRecevice_userName(toChatUserid);
        }
        if(toChatUserhead!=null){
            chatMessage.setRecevice_userHead(toChatUserhead);
        }else{
            chatMessage.setRecevice_userHead("");
        }
        chatMessage.setSend_Message(content);
        chatMessage.setSend_time(new Date().getTime());

        return chatMessage;
    }


    public class Type{
         public static final int TXT = 0;//文字消息
         public static final int IMAGE = 1;//图片消息

    }

    public ChatMessage(){
    }
    public ChatMessage(String recevice_iD,String recevice_userName,String recevice_userHead ,String send_Message){
        this.recevice_iD = recevice_iD;
        this.recevice_userName = recevice_userName;
        this.recevice_userHead = recevice_userHead;
        this.send_Message = send_Message;
    }

    public long getSend_time() {
        return send_time;
    }

    public void setSend_time(long send_time) {
        this.send_time = send_time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRecevice_iD() {
        return recevice_iD;
    }
    public void setRecevice_iD(String recevice_iD) {
        this.recevice_iD = recevice_iD;
    }

    public String getSend_userName() {
        return send_userName;
    }

    public void setSend_userName(String send_userName) {
        this.send_userName = send_userName;
    }

    public String getSend_userHead() {
        return send_userHead;
    }

    public void setSend_userHead(String send_userHead) {
        this.send_userHead = send_userHead;
    }

    public String getRecevice_userName() {
        return recevice_userName;
    }

    public void setRecevice_userName(String recevice_userName) {
        this.recevice_userName = recevice_userName;
    }

    public String getRecevice_userHead() {
        return recevice_userHead;
    }

    public void setRecevice_userHead(String recevice_userHead) {
        this.recevice_userHead = recevice_userHead;
    }

    public String getSend_Message() {
        return send_Message;
    }

    public void setSend_Message(String send_Message) {
        this.send_Message = send_Message;
    }
}
