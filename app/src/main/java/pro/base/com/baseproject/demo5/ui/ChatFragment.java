package pro.base.com.baseproject.demo5.ui;

import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import fyl.base.Utils.LogUtils;
import pro.base.com.baseproject.Constant;
import pro.base.com.baseproject.MyAppLication;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo5.db.ChatInfoUtils;
import pro.base.com.baseproject.demo5.entity.ChatMessage;
import pro.base.com.baseproject.demo5.views.ChatInputMenu;
import pro.base.com.baseproject.demo5.views.ChatMessageList;

/**
 * Created by DN on 2018/2/10.
 */

public class ChatFragment extends BaseChatFragment {

        //return R.layout.fragment_chat_activity;
    /**
     * params to fragment
     */
    protected Bundle fragmentArgs;
    protected ChatMessageList messageList;
    protected int chatType;
    protected ListView listView;
    protected ChatInputMenu inputMenu;
    protected InputMethodManager inputManager;
    protected ClipboardManager clipboard;
    protected String toChatUsername;//接收人的名称
    protected String toChatUserid;//接收人的ID
    protected String toChatUserhead;//接收人的头像地址
    private boolean isMessageListInited;
    protected String sendChatUserName;//发送人的名称
    protected String sendChatUserhead;//发送人的头像
    private View view;
    private Class<?> clz;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_chat_activity, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        fragmentArgs = getArguments();
        toChatUserid = fragmentArgs.getString(Constant.EXTRA_USER_ID);
        toChatUsername = fragmentArgs.getString(Constant.EXTRA_USER_NAME);
        toChatUserhead = fragmentArgs.getString(Constant.EXTRA_USER_HEAD);
        sendChatUserName = fragmentArgs.getString(Constant.SEND_USER_NAME);
        sendChatUserhead = fragmentArgs.getString(Constant.SEND_USER_HEAD);
        clz = (Class<?>) fragmentArgs.getSerializable("clz");
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * init view
     */
    protected void initView() {
        // message list layout
        messageList =  getView().findViewById(R.id.message_list);
        messageList.setShowUserNick(true);
        messageList.setUID(toChatUserid);
        listView = messageList.getListView();

        inputMenu = getView().findViewById(R.id.input_menu);
        inputMenu.init();
        onMessageListInit();
        inputMenu.setChatInputMenuListener(new ChatInputMenu.ChatInputMenuListener() {
            @Override
            public void onSendMessage(String content) {
                if(null!=content&&!content.equals("")){
                    sendTextMessage(content);
                }else{
                    Toast.makeText(getContext(),"发送的消息不能为空！",Toast.LENGTH_SHORT).show();
                }
            }
        });
        inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    }

    @Override
    protected void setUpView() {
        titleBar.setTitle(toChatUsername);
        titleBar.setRightImageResource(R.drawable.chat_mm_title_remove);
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"删除聊天记录",Toast.LENGTH_SHORT).show();
                MyAppLication.ddbManager.delete(toChatUserid);
                ChatInfoUtils.deleteChatRecord(getContext(),toChatUserid);
                if(isMessageListInited) {
                    messageList.refreshSelectLast();
                }

            }
        });

    }

    //send message
    protected void sendTextMessage(String content) {
            //创建一个消息实体
            ChatMessage message = ChatMessage.createTxtSendMessage(toChatUserid,toChatUsername,toChatUserhead,Constant.userName,Constant.headUrl,content);
            sendMessage(message);
    }

    /**
     * 发送消息
     * @param message
     */
    private void sendMessage(ChatMessage message) {
        //判断聊友是否存在
        //boolean isExist = ChatInfoUtils.isChatInfo(getContext(), message.getRecevice_iD());
        //if(!isExist){
            //保存聊友至SP中
            ChatInfoUtils.saveChatInfo(getContext(),message);
        //}
        //聊天记录保存至数据库中
        boolean b = MyAppLication.ddbManager.insert(message);
        LogUtils.d("保存聊b天记录信息："+b);
      /* List<ChatMessage> chat = SPUtils.getInstance(getContext()).getDataList("chat", ChatMessage.class);
        if(chat!=null&&chat.size()>0){
            chat.add(message);
        }else{
             chat = new ArrayList<>();
             chat.add(message);
        }
        SPUtils.getInstance(getContext()).setDataList("chat",chat);*/
        //refresh ui
        if(isMessageListInited) {
            messageList.refreshSelectLast();
        }
        //Toast.makeText(getContext(),"发送的内容为："+message.getSend_Message()+"\n"+"接收人："+message.getRecevice_userName(),Toast.LENGTH_SHORT).show();
    }

    protected void onMessageListInit(){
        messageList.init(toChatUsername, chatType);
        setListItemClickListener();
        messageList.getListView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                inputMenu.hideExtendMenuContainer();
                return false;
            }
        });
        isMessageListInited = true;
    }

    protected ChatFragmentHelper chatFragmentHelper;
    protected void setListItemClickListener() {
        messageList.setItemClickListener(new ChatMessageList.MessageListItemClickListener() {

            @Override
            public void onUserAvatarClick(String username) {
                if(chatFragmentHelper != null){
                    chatFragmentHelper.onAvatarClick(username);
                }
            }
            @Override
            public void onUserAvatarLongClick(String username) {
                if(chatFragmentHelper != null){
                    chatFragmentHelper.onAvatarLongClick(username);
                }
            }
        });
    }
    /**
     * hide
     */
    protected void hideKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void onBackPressed() {
        getActivity().finish();
    }

    public interface ChatFragmentHelper{
        /**
         * on avatar clicked
         * @param username
         */
        void onAvatarClick(String username);

        /**
         * on avatar long pressed
         * @param username
         */
        void onAvatarLongClick(String username);

    }

}
