package pro.base.com.baseproject.demo2;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fyl.base.BaseActivity;
import pro.base.com.baseproject.R;

/**
 * Created by DN on 2018/1/18.
 */

public class DBTestActivity extends BaseActivity {
    public TextView tv_show;
    public Button btn_get;
    private User student;
    @Override
    public void initParms(Bundle parms) {
        //此属性设置与状态栏相关
        setAllowFullScreen(true);//是否允许全屏
        setScreenRoate(false);//是否允许屏幕旋转
        setSteepStatusBar(false);//是否设置沉浸状态栏
        setSetActionBarColor(true, R.color.system_bar_color);//设置状态栏主题颜色
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.db_test_activity;
    }

    @Override
    public void initView(View view) {
        tv_show = (TextView) findViewById(R.id.tv_show);
        btn_get = (Button) findViewById(R.id.btn_get);
    }
    String user;
    @Override
    public void setListener() {
        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        user  = student.getDate("user");
                        showLog(user+"");
                    }
                });
            }
        });
        tv_show.setText(user+"");
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {

    }
}
