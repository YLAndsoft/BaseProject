package pro.base.com.baseproject.demo5.views;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo5.entity.ChatMessage;

/**
 * Created by DN on 2018/2/10.
 */

public class ChatRowImage extends ChatRow {
    protected ImageView imageView;

    public ChatRowImage(Context context, ChatMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInitLayout() {
        inflater.inflate(R.layout.chat_row_sent_picture1, this);
    }

    @Override
    protected void onFindViewById() {
        imageView = findViewById(R.id.image);
    }


    @Override
    protected void onSetUpView() {
        // received messages
        if (message.getType() == ChatMessage.Type.IMAGE) {
            String filePath = message.getSend_Message();
            if(filePath!=null){
                Glide.with(context).load(filePath).error(R.drawable.chat_default_avatar).into(imageView);
            }
        }
    }



}
