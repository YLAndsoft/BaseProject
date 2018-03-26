package pro.base.com.baseproject.demo5.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pro.base.com.baseproject.R;

/**
 * 长按删除对话框
 * Created by DN on 2018/2/28.
 */

public class LongDeleteDialog extends Dialog {
    private Button no;//取消按钮
    private Button yes;//取消按钮
    //private TextView titleTv;//消息标题文本
    private TextView message1;//消息提示文本
    //private String titleStr;//从外界设置的title文本
    private String messageStr1;//从外界设置的消息文本
    //确定文本和取消文本的显示内容

    private OnNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private OnYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(OnNoOnclickListener onNoOnclickListener) {
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener( OnYesOnclickListener onYesOnclickListener) {

        this.yesOnclickListener = onYesOnclickListener;
    }


    public LongDeleteDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.long_delete_layout);
        setCanceledOnTouchOutside(true);
        //setCancelable(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
                }
            }
        });


    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (messageStr1 != null) {
            message1.setText(messageStr1);
        }else{
            message1.setText("是否删除当前聊天信息，并且删除聊天记录？");
        }
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        no =  findViewById(R.id.no);
        yes =  findViewById(R.id.yes);
        message1 =  findViewById(R.id.message1);
    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
  /*  public void setTitle(String title) {
        titleStr = title;
    }
*/
    /**
     * 从外界Activity为Dialog设置dialog的message
     *
     * @param message1
     */
    public void setMessage(String message1) {
        messageStr1 = message1;
    }



    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface OnYesOnclickListener {
        void onYesClick();
    }

    public interface OnNoOnclickListener {
         void onNoClick();
    }
}
