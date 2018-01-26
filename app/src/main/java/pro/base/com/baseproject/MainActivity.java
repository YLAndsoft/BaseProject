package pro.base.com.baseproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import fyl.base.BaseActivity;
import fyl.base.f;
import fyl.base.widget.SVProgressHUD;
import pro.base.com.baseproject.dbDemo.DBTestActivity;
import pro.base.com.baseproject.dbDemo.User;
import pro.base.com.baseproject.dbDemo.UserData;
import pro.base.com.baseproject.demo1.activity.SVProgressHUDActivity;
import pro.base.com.baseproject.demo1.fragment.DemoActivity;

public class MainActivity extends BaseActivity {

    @ViewInject(value = R.id.textView1)
    private TextView mText1;
    @ViewInject(value = R.id.textView2)
    private TextView mText2;
    @ViewInject(value = R.id.textView3)
    private TextView mText3;
    private String[] demoName = Constant.DEMO_NAME;

    private User user;
    @Override
    public void initParms(Bundle parms) {

        //此属性设置与状态栏相关
        setAllowFullScreen(true);//是否允许全屏
        setScreenRoate(false);//是否允许屏幕旋转
        setSteepStatusBar(false);//是否设置沉浸状态栏
        setSetActionBarColor(true,R.color.system_bar_color);//设置状态栏主题颜色
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        //绑定布局文件，如同onCreate()方法中setContentView(R.layout.activity_main);
        return R.layout.activity_main;
    }

    @Override
    public void initView(View view) {
        x.view().inject(this);//注册x工具，不懂的可百度
    }

    @Override
    public void setListener() {
        mText1.setOnClickListener(this);//绑定监听事件
        mText2.setOnClickListener(this);
        mText3.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {


    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.textView1:
                startActivity(new Intent(mContext,DemoActivity.class));
                break;
            case R.id.textView2:
                startActivity(new Intent(mContext,DBTestActivity.class));
                break;
            case R.id.textView3:
                startActivity(new Intent(mContext,SVProgressHUDActivity.class));
                break;
        }

    }




}
