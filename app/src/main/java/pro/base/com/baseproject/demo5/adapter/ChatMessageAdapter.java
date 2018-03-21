package pro.base.com.baseproject.demo5.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

import pro.base.com.baseproject.MyAppLication;
import pro.base.com.baseproject.demo5.entity.ChatMessage;
import pro.base.com.baseproject.demo5.views.ChatMessageList;
import pro.base.com.baseproject.demo5.views.ChatRow;
import pro.base.com.baseproject.demo5.views.ChatRowImage;
import pro.base.com.baseproject.demo5.views.ChatRowText;

/**
 * Created by DN on 2018/2/10.
 */

public class ChatMessageAdapter extends BaseAdapter {
    private final static String TAG = "msg";
    private Context context;
    private static final int HANDLER_MESSAGE_REFRESH_LIST = 0;
    private static final int HANDLER_MESSAGE_SELECT_LAST = 1;
    private static final int HANDLER_MESSAGE_SEEK_TO = 2;

    private static final int MESSAGE_TYPE_SENT_TXT = 1;
    private static final int MESSAGE_TYPE_SENT_IMAGE = 2;



    public int itemTypeCount;

    // reference to conversation object in chatsdk
    //private EMConversation conversation;
    ChatMessage[] messages = null;

    private String toChatUsername;

    private ChatMessageList.MessageListItemClickListener itemClickListener;

    private boolean showUserNick;
    private boolean showAvatar;
    private Drawable myBubbleBg;
    private Drawable otherBuddleBg;
    private String uID;
    private ListView listView;

    public ChatMessageAdapter(Context context,String uID,String username, int chatType, ListView listView) {
        this.context = context;
        this.listView = listView;
        this.uID = uID;
        toChatUsername = username;
    }

    Handler handler = new Handler() {
        private void refreshList() {
            // you should not call getAllMessages() in UI thread
            // otherwise there is problem when refreshing UI and there is new message arrive
            //Toast.makeText(context,"刷新了",Toast.LENGTH_SHORT).show();
            //java.util.List<ChatMessage> var = EMConversation.getAllMessages();//获取所有的消息
            if(uID!=null&&!uID.equals("")){
                List<ChatMessage> var = MyAppLication.ddbManager.queryChatMessage(uID);
                if(null!=var&&var.size()>0){
                    messages = var.toArray(new ChatMessage[var.size()]);
                }
                notifyDataSetChanged();
            }

        }

        @Override
        public void handleMessage(android.os.Message message) {
            switch (message.what) {
                case HANDLER_MESSAGE_REFRESH_LIST:
                    refreshList();
                    break;
                case HANDLER_MESSAGE_SELECT_LAST:
                    if(messages!=null){
                        if (messages.length > 0) {
                            listView.setSelection(messages.length - 1);
                        }
                    }
                    break;
                case HANDLER_MESSAGE_SEEK_TO:
                    int position = message.arg1;
                    listView.setSelection(position);
                    break;
                default:
                    break;
            }
        }
    };

    public void refresh() {
        if (handler.hasMessages(HANDLER_MESSAGE_REFRESH_LIST)) {
            return;
        }
        android.os.Message msg = handler.obtainMessage(HANDLER_MESSAGE_REFRESH_LIST);
        handler.sendMessage(msg);
    }

    /**
     * refresh and select the last
     */
    public void refreshSelectLast() {
        final int TIME_DELAY_REFRESH_SELECT_LAST = 100;
        handler.removeMessages(HANDLER_MESSAGE_REFRESH_LIST);
        handler.removeMessages(HANDLER_MESSAGE_SELECT_LAST);
        handler.sendEmptyMessageDelayed(HANDLER_MESSAGE_REFRESH_LIST, TIME_DELAY_REFRESH_SELECT_LAST);
        handler.sendEmptyMessageDelayed(HANDLER_MESSAGE_SELECT_LAST, TIME_DELAY_REFRESH_SELECT_LAST);
    }

    /**
     * refresh and seek to the position
     */
    public void refreshSeekTo(int position) {
        handler.sendMessage(handler.obtainMessage(HANDLER_MESSAGE_REFRESH_LIST));
        android.os.Message msg = handler.obtainMessage(HANDLER_MESSAGE_SEEK_TO);
        msg.arg1 = position;
        handler.sendMessage(msg);
    }


    public ChatMessage getItem(int position) {
        if (messages != null && position < messages.length) {
            return messages[position];
        }
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * get count of messages
     */
    public int getCount() {
        return messages == null ? 0 : messages.length;
    }



    /**
     * get type of item
     */
    public int getItemViewType(int position) {
        ChatMessage message = getItem(position);
        if (message == null) {
            return -1;
        }

        if (message.getType() == ChatMessage.Type.TXT) {

            return ChatMessage.Type.TXT;
        }
        if (message.getType() == ChatMessage.Type.IMAGE) {
            return ChatMessage.Type.IMAGE;
        }
        return -1;// invalid
    }

    protected ChatRow createChatRow(Context context, ChatMessage message, int position) {
        ChatRow chatRow = null;
        switch (message.getType()) {
            case ChatMessage.Type.TXT:
                chatRow = new ChatRowText(context, message, position, this);
                break;
            case ChatMessage.Type.IMAGE:
                chatRow = new ChatRowImage(context, message, position, this);
                break;
            default:
                break;
        }

        return chatRow;
    }


    @SuppressLint("NewApi")
    public View getView(final int position, View convertView, ViewGroup parent) {
        ChatMessage message = getItem(position);
        if(convertView == null){
            convertView = createChatRow(context, message, position);
        }

        //refresh ui with messages
        ((ChatRow)convertView).setUpView(message, position, itemClickListener);

        return convertView;
    }


    public String getToChatUsername(){
        return toChatUsername;
    }



    public void setShowUserNick(boolean showUserNick) {
        this.showUserNick = showUserNick;
    }


    public void setShowAvatar(boolean showAvatar) {
        this.showAvatar = showAvatar;
    }


    public void setMyBubbleBg(Drawable myBubbleBg) {
        this.myBubbleBg = myBubbleBg;
    }


    public void setOtherBuddleBg(Drawable otherBuddleBg) {
        this.otherBuddleBg = otherBuddleBg;
    }


    public void setItemClickListener(ChatMessageList.MessageListItemClickListener listener){
        itemClickListener = listener;
    }




    public boolean isShowUserNick() {
        return showUserNick;
    }


    public boolean isShowAvatar() {
        return showAvatar;
    }


    public Drawable getMyBubbleBg() {
        return myBubbleBg;
    }


    public Drawable getOtherBuddleBg() {
        return otherBuddleBg;
    }
}
