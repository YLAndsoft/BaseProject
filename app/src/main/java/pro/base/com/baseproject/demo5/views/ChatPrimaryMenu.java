package pro.base.com.baseproject.demo5.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import pro.base.com.baseproject.R;

/**
 * Created by DN on 2018/2/10.
 */

public class ChatPrimaryMenu extends ChatPrimaryMenuBase implements View.OnClickListener {
    private EditText editText;
    private RelativeLayout edittext_layout;
    private View buttonSend;

    public ChatPrimaryMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public ChatPrimaryMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatPrimaryMenu(Context context) {
        super(context);
        init(context, null);
    }


    private void init(final Context context, AttributeSet attrs) {
        Context context1 = context;
        LayoutInflater.from(context).inflate(R.layout.chat_widget_chat_primary_menu1, this);
        editText = findViewById(R.id.et_sendmessage);
        edittext_layout = findViewById(R.id.edittext_layout);
        buttonSend = findViewById(R.id.btn_send);

        buttonSend.setOnClickListener(this);
        editText.setOnClickListener(this);
        editText.requestFocus();

        editText.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edittext_layout.setBackgroundResource(R.drawable.chat_input_bar_bg_active);
                } else {
                    edittext_layout.setBackgroundResource(R.drawable.chat_input_bar_bg_normal);
                }

            }
        });

    }

    /**
     * delete emojicon
     */
    public void onEmojiconDeleteEvent(){
        if (!TextUtils.isEmpty(editText.getText())) {
            KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
            editText.dispatchKeyEvent(event);
        }
    }

    /**
     * on clicked event
     * @param view
     */
    @Override
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btn_send) {
            if(listener != null){
                String s = editText.getText().toString();
                editText.setText("");
                listener.onSendBtnClicked(s);
            }
        } else if (id == R.id.et_sendmessage) {
            edittext_layout.setBackgroundResource(R.drawable.chat_input_bar_bg_active);
            if(listener != null)
                listener.onEditTextClicked();
        }
    }




    /**
     * show keyboard
     */
    protected void setModeKeyboard() {
        edittext_layout.setVisibility(View.VISIBLE);
        editText.requestFocus();
    }


    @Override
    public void onExtendMenuContainerHide() {
    }

}
