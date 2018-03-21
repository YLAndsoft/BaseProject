package pro.base.com.baseproject.demo5.views;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;

import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo5.entity.ChatMessage;

/**
 * Created by DN on 2018/2/10.
 */

public class ChatRowText extends ChatRow {

    private TextView contentView;

    public ChatRowText(Context context, ChatMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    public void onSetUpView() {
        // 设置内容
        contentView.setText(message.getSend_Message());
    }

    @Override
    protected void onInitLayout() {
        inflater.inflate(R.layout.chat_row_sent_message1, this);
    }

    @Override
    protected void onFindViewById() {
        contentView =findViewById(R.id.tv_chatcontent);
    }


}
