package pro.base.com.baseproject.demo5.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import fyl.base.BaseFragmentActivity;
import fyl.base.Utils.LogUtils;
import fyl.base.Utils.SPUtils;
import pro.base.com.baseproject.Constant;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo5.entity.ChatMessage;

/**
 * Created by DN on 2018/2/10.
 */

public class ChatActivity extends BaseFragmentActivity {

    private ChatFragment chatFragment;
    private String toChatUsername;//接收人的名称
    private String toChatUserid;//接收人的ID
    private String toChatUserhead;//接收人的头像地址
    private int isChatInfo;//是否是聊天信息界面跳转过来的
    private String sendChatUserName;//发送人的名称
    private String sendChatUserhead;//发送人的头像
    private ChatMessage chaMessage;
    private Class<?> clz;
    @Override
    public void initParms(Bundle parms) {
        //此属性设置与状态栏相关
        setAllowFullScreen(true);//是否允许全屏
        setScreenRoate(false);//是否允许屏幕旋转
        setSteepStatusBar(false);//是否设置沉浸状态栏
        //setSetActionBarColor(true,R.color.system_bar_color);//设置状态栏主题颜色
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.chat_activity1;
    }

    @Override
    public void initView(View view) {
        try{
            chaMessage = (ChatMessage) getIntent().getExtras().getSerializable("chatMessage");
            toChatUsername = chaMessage.getRecevice_userName();
            toChatUserid = chaMessage.getRecevice_iD();
            toChatUserhead = chaMessage.getRecevice_userHead();
            sendChatUserName = chaMessage.getSend_userName();
            sendChatUserhead = chaMessage.getSend_userHead();

            isChatInfo = getIntent().getIntExtra("isChatInfo",0);
            SPUtils.getInstance(mContext).setParam(Constant.SEND_USER_NAME, sendChatUserName!=null?sendChatUserName:Constant.userName);
            SPUtils.getInstance(mContext).setParam(Constant.SEND_USER_HEAD, sendChatUserhead!=null?sendChatUserhead:Constant.headUrl);
            clz = (Class<?>) getIntent().getSerializableExtra("clz");
        }catch (NullPointerException ex){
            LogUtils.d("请检查，ChatMessage是否为空?");
            //抛出异常，让用户自己去解决
            ex.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {
        chatFragment = new ChatFragment();
        Bundle extras = getIntent().getExtras();
        extras.putCharSequence("userId",toChatUserid);
        extras.putCharSequence("userName",toChatUsername);
        extras.putCharSequence("userHead",toChatUserhead);
        extras.putCharSequence("send_userName",sendChatUserName);
        extras.putCharSequence("send_userHead",sendChatUserhead);
        extras.putSerializable("clz", clz);
        chatFragment.setArguments(extras);
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
    }

    @Override
    public void widgetClick(View v) {

    }
    @Override
    protected void onNewIntent(Intent intent) {
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isChatInfo>0){
            //如果这个界面销毁，则通知聊友界面更新聊天信息
            Intent intent = new Intent(Constant.CHAT_INFO_REFRESH);
            mContext.sendBroadcast(intent);
        }
    }
}
