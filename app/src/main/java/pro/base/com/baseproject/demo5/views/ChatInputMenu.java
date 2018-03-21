package pro.base.com.baseproject.demo5.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import pro.base.com.baseproject.R;

/**
 * Created by DN on 2018/2/10.
 */

public class ChatInputMenu extends LinearLayout {
    FrameLayout primaryMenuContainer;
    protected ChatPrimaryMenuBase chatPrimaryMenu;
    protected LayoutInflater layoutInflater;
    private ChatInputMenuListener listener;

    private Context context;

    public ChatInputMenu(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public ChatInputMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ChatInputMenu(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.chat_widget_chat_input_menu1, this);
        primaryMenuContainer = findViewById(R.id.primary_menu_container);
    }

    /**
     * init view
     *
     * This method should be called after registerExtendMenuItem(), setCustomEmojiconMenu() and setCustomPrimaryMenu().
     */
    @SuppressLint("InflateParams")
    public void init() {
        if(chatPrimaryMenu == null){
            chatPrimaryMenu = (ChatPrimaryMenu) layoutInflater.inflate(R.layout.chat_layout_chat_primary_menu1, null);
        }
        primaryMenuContainer.addView(chatPrimaryMenu);
        processChatMenu();

    }

    /**
     * hide extend menu
     */
    public void hideExtendMenuContainer() {
        chatPrimaryMenu.onExtendMenuContainerHide();
    }

    public void setChatInputMenuListener(ChatInputMenuListener listener) {
        this.listener = listener;
    }

    public interface ChatInputMenuListener {
        /**
         * when send message button pressed
         *
         * @param content
         *            message content
         */
        void onSendMessage(String content);

    }

    protected void processChatMenu() {
        chatPrimaryMenu.setChatPrimaryMenuListener(new ChatPrimaryMenuBase.ChatPrimaryMenuListener() {

            @Override
            public void onSendBtnClicked(String content) {
                if (listener != null)
                    listener.onSendMessage(content);
            }
            @Override
            public void onEditTextClicked() {
                hideExtendMenuContainer();
            }

        });



    }

}
