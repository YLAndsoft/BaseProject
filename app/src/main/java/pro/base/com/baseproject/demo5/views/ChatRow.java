package pro.base.com.baseproject.demo5.views;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.util.Date;

import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo5.adapter.ChatMessageAdapter;
import pro.base.com.baseproject.demo5.entity.ChatMessage;
import pro.base.com.baseproject.demo5.utils.DateUtils;

/**
 * Created by DN on 2018/2/10.
 */

public abstract class ChatRow extends LinearLayout {

    protected static final String TAG = ChatRow.class.getSimpleName();

    protected LayoutInflater inflater;
    protected Context context;
    protected BaseAdapter adapter;
    protected ChatMessage message;
    protected int position;
    protected TextView timeStampView;
    protected ImageView userAvatarView;
    protected View bubbleLayout;
    protected Activity activity;

    protected ChatMessageList.MessageListItemClickListener itemClickListener;

    public ChatRow(Context context, ChatMessage message, int position, BaseAdapter adapter) {
        super(context);
        this.context = context;
        this.activity = (Activity) context;
        this.message = message;
        this.position = position;
        this.adapter = adapter;
        inflater = LayoutInflater.from(context);

        initView();
    }

    private void initView() {
        onInitLayout();
        timeStampView = findViewById(R.id.timestamp);
        userAvatarView =  findViewById(R.id.iv_userhead);
        bubbleLayout = findViewById(R.id.bubble);

        onFindViewById();
    }

    /**
     * set property according message and postion
     *
     * @param message
     * @param position
     */
    public void setUpView(ChatMessage message, int position,ChatMessageList.MessageListItemClickListener itemClickListener) {
        this.message = message;
        this.position = position;
        this.itemClickListener = itemClickListener;
        setUpBaseView();
        onSetUpView();
        setClickListener();
    }

    private void setUpBaseView() {
        // set nickname, avatar and background of bubble
        TextView timestamp =  findViewById(R.id.timestamp);
        if (timestamp != null) {
            if (position == 0) {
                timestamp.setText(DateUtils.getTimestampString(new Date(message.getSend_time())));
                timestamp.setVisibility(View.VISIBLE);
            } else {
                // show time stamp if interval with last message is > 30 seconds
                ChatMessage prevMessage = (ChatMessage) adapter.getItem(position - 1);
                if (prevMessage != null && DateUtils.isCloseEnough(message.getSend_time(), prevMessage.getSend_time())) {
                    timestamp.setVisibility(View.GONE);
                } else {
                    timestamp.setText(DateUtils.getTimestampString(new Date(message.getSend_time())));
                    timestamp.setVisibility(View.VISIBLE);
                }
            }
        }
        //设置发送者头像
        try{//
            Glide.with(context).load(message.getSend_userHead()).error(context.getResources().getDrawable(R.drawable.ic_launcher)).into(userAvatarView);
        }catch (Exception ex){
            //加载异常的话，可以设置本地默认图片
            Glide.with(context).load(context.getResources().getDrawable(R.drawable.ic_launcher)).error(context.getResources().getDrawable(R.drawable.ic_launcher)).into(userAvatarView);
        }
        if (adapter instanceof ChatMessageAdapter) {
            if (((ChatMessageAdapter) adapter).isShowAvatar())
                userAvatarView.setVisibility(View.VISIBLE);
            else
                userAvatarView.setVisibility(View.GONE);
            if (((ChatMessageAdapter) adapter).getMyBubbleBg() != null) {
                bubbleLayout.setBackgroundDrawable(((ChatMessageAdapter) adapter).getMyBubbleBg());
            }
        }
    }

    private void setClickListener() {
        if(userAvatarView != null){
            userAvatarView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"点击了头像",Toast.LENGTH_SHORT).show();
                }
            });
            userAvatarView.setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    if(itemClickListener != null){
                        Toast.makeText(context,"长按了头像",Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    protected abstract void onInitLayout();

    /**
     * find view by id
     */
    protected abstract void onFindViewById();

    /**
     * setup view
     *
     */
    protected abstract void onSetUpView();
}
