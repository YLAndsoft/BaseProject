package pro.base.com.baseproject.demo5.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.List;
import fyl.base.base.BaseActivity;
import pro.base.com.baseproject.Constant;
import pro.base.com.baseproject.MyAppLication;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo5.adapter.ChatInfoAdapter;
import pro.base.com.baseproject.demo5.db.ChatInfoUtils;
import pro.base.com.baseproject.demo5.entity.ChatMessage;
import pro.base.com.baseproject.demo5.views.LongDeleteDialog;

/**
 * Created by DN on 2018/2/26.
 */

public class ChatInfoActivity extends BaseActivity {

    @ViewInject(value = R.id.chat_info_list)
    private ListView chat_info_list;
    @ViewInject(value = R.id.info_null)
    private TextView info_null;
    private ChatInfoAdapter chatInfoAdapter;
    private List<ChatMessage> chatInfo;
    private ChatRefeshReceiver refeshReceiver;
    @Override
    public void initParms(Intent parms) {
        setAllowFullScreen(true);
        setScreenRoate(false);
        setSteepStatusBar(false);
        setSetActionBarColor(true, R.color.white);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.chat_info_activity1;
    }

    @Override
    public void initView(View view) {
        x.view().inject(this);
        //注册刷新广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.CHAT_INFO_REFRESH);
        refeshReceiver = new ChatRefeshReceiver();
        registerReceiver(refeshReceiver,intentFilter);
    }

    @Override
    public void initListener() {
        chat_info_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(chatInfo!=null&&chatInfo.size()>0){
                    Intent chat = new Intent(mContext, ChatActivity.class);
                    chat.putExtra("chatMessage",chatInfo.get(i));
                    chat.putExtra("isChatInfo",1);
                    startActivity(chat);
                }
            }
        });
        chat_info_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                //showToast("长按了"+chatInfo.get(i).getRecevice_userName());
                final LongDeleteDialog longDeleteDialog = new LongDeleteDialog(mContext);
                longDeleteDialog.setNoOnclickListener(new LongDeleteDialog.OnNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        //否，不去删除聊天记录
                        deleteInfo(false,i);
                        longDeleteDialog.dismiss();
                    }
                });
                longDeleteDialog.setYesOnclickListener(new LongDeleteDialog.OnYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        //是，删除聊天信息，并且删除聊天记录
                        deleteInfo(true,i);
                        longDeleteDialog.dismiss();
                    }
                });
                longDeleteDialog.show();
                //如果返回false那么click仍然会被调用。而且是先调用Long click，然后调用click。
                //如果返回true那么click就会被吃掉，click就不会再被调用了
                return true;
            }
        });
    }

    /**
     * 删除聊天信息及记录
     * @param b
     */
    private void deleteInfo(boolean b,int position) {
        //删除聊天信息
        List<ChatMessage> chatMessages = ChatInfoUtils.deleteChatInfo(mContext,chatInfo.get(position).getRecevice_iD());
        if(b){
            //删除聊天记录
            MyAppLication.ddbManager.delete(chatInfo.get(position).getRecevice_iD());
        }
        if(chatMessages==null){
            chat_info_list.setVisibility(View.GONE);
            info_null.setVisibility(View.VISIBLE);
            return;
        }
        if(chatInfoAdapter!=null){
            chatInfo =null;
            chatInfo = chatMessages;
            chatInfoAdapter.refesh(chatInfo);
        }
    }

    @Override
    public void doBusiness(final Context mContext) {
        //查询所有聊友
        chatInfo = ChatInfoUtils.queryChatInfo(mContext);
        if(chatInfo!=null&&chatInfo.size()>0){
            chat_info_list.setVisibility(View.VISIBLE);
            info_null.setVisibility(View.GONE);
            if(chatInfoAdapter==null){
                if(chatInfo!=null){
                    chatInfoAdapter = new ChatInfoAdapter(mContext,chatInfo);
                }
            }
            chat_info_list.setAdapter(chatInfoAdapter);
        }else{
                chat_info_list.setVisibility(View.GONE);
                info_null.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void widgetClick(View v) {

    }


   class ChatRefeshReceiver extends BroadcastReceiver{
       @Override
       public void onReceive(Context context, Intent intent) {
           //刷新单个item
           if(chatInfoAdapter!=null){
               chatInfo =null;
               chatInfo = ChatInfoUtils.queryChatInfo(mContext);
               if(chatInfo!=null&&chatInfo.size()>0){
                   chat_info_list.setVisibility(View.VISIBLE);
                   info_null.setVisibility(View.GONE);
                   chatInfoAdapter.refesh(chatInfo);
               }else{
                   chat_info_list.setVisibility(View.GONE);
                   info_null.setVisibility(View.VISIBLE);
               }
           }else{
               chat_info_list.setVisibility(View.GONE);
               info_null.setVisibility(View.VISIBLE);
           }
       }
   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁广播
        if(refeshReceiver!=null){
            mContext.unregisterReceiver(refeshReceiver);
        }
    }
}
