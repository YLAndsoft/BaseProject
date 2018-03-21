package pro.base.com.baseproject.demo5.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Created by DN on 2018/2/10.
 */

public abstract class ChatPrimaryMenuBase extends RelativeLayout {
    protected ChatPrimaryMenuListener listener;
    protected Activity activity;
    protected InputMethodManager inputManager;

    public ChatPrimaryMenuBase(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public ChatPrimaryMenuBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChatPrimaryMenuBase(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        this.activity = (Activity) context;
        inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    /**
     * set primary menu listener
     * @param listener
     */
    public void setChatPrimaryMenuListener(ChatPrimaryMenuListener listener){
        this.listener = listener;
    }
    /**
     * hide extend menu
     */
    public abstract void onExtendMenuContainerHide();

    /**
     * hide keyboard
     */
    public void hideKeyboard() {
        if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public interface ChatPrimaryMenuListener{
        /**
         * when send button clicked
         * @param content
         */
        void onSendBtnClicked(String content);
        /**
         * on text input is clicked
         */
        void onEditTextClicked();

    }
}
