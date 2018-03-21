package pro.base.com.baseproject.demo5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Date;
import java.util.List;

import pro.base.com.baseproject.MyAppLication;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo5.entity.ChatMessage;
import pro.base.com.baseproject.demo5.utils.DateUtils;

/**
 * Created by DN on 2018/2/26.
 */

public class ChatInfoAdapter extends BaseAdapter {
    private Context context;
    private List<ChatMessage> chatInfo;
    private LayoutInflater inflater;
    public ChatInfoAdapter(Context context,List<ChatMessage> chatInfo){
        this.context = context;
        this.chatInfo = chatInfo;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 刷新单个item布局
     */
    public void refeshItem(ListView home_list, int position,String receviceID){
        View childAt = home_list.getChildAt(position);
        ChatInfoHolder chatHolder = (ChatInfoHolder) childAt.getTag();
        //根据ID查询出所有聊天记录，并排序找出最后一条记录
        List<ChatMessage> chatMessages = MyAppLication.ddbManager.queryChatMessage(receviceID);
        chatHolder.recevice_message.setText(chatMessages.get(chatMessages.size() - 1).getSend_Message());
        chatHolder.recevice_time.setText(DateUtils.getTimestampString(new Date(chatMessages.get(chatMessages.size() - 1).getSend_time())));
    }

    /**
     *  移动单个item布局
     */
    public void refesh( List<ChatMessage> chatMessages){
            this.chatInfo =null;
            this.chatInfo = chatMessages;
            notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return chatInfo.size();
    }

    @Override
    public Object getItem(int i) {
        return chatInfo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ChatInfoHolder chatInfoHolder;
        if(view==null){
            chatInfoHolder = new ChatInfoHolder();
            view = inflater.inflate(R.layout.chat_info_item_layout1,null);
            x.view().inject(chatInfoHolder,view);
            view.setTag(chatInfoHolder);
        }else{
            chatInfoHolder = (ChatInfoHolder) view.getTag();
        }
        if(chatInfo!=null&&chatInfo.size()>0){
            ChatMessage chatMessage = chatInfo.get(i);
            if(chatMessage!=null){
                chatInfoHolder.recevice_name.setText(chatMessage.getRecevice_userName()+"");
                try{
                    Glide.with(context).load(chatMessage.getRecevice_userHead()).error(R.drawable.chat_default_avatar).into(chatInfoHolder.recevice_headUrl);
                }catch (Exception ex){
                    chatInfoHolder.recevice_headUrl.setImageResource(R.drawable.chat_default_avatar);
                }
                chatInfoHolder.recevice_message.setText(chatInfo.get(i).getSend_Message()+"");
                chatInfoHolder.recevice_time.setText(DateUtils.getTimestampString(new Date(chatMessage.getSend_time())));
            }
        }
        return view;
    }

    class ChatInfoHolder{
        @ViewInject(value = R.id.recevice_headUrl)
        ImageView recevice_headUrl;
        @ViewInject(value = R.id.recevice_name)
        TextView recevice_name;
        @ViewInject(value = R.id.recevice_message)
        TextView recevice_message;
        @ViewInject(value = R.id.recevice_time)
        TextView recevice_time;
    }


}
