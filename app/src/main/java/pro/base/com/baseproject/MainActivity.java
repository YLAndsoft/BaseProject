package pro.base.com.baseproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import fyl.base.BaseActivity;
import pro.base.com.baseproject.demo2.DBTestActivity;
import pro.base.com.baseproject.demo3.SVProgressHUDActivity;
import pro.base.com.baseproject.demo1.fragment.DemoActivity;
import pro.base.com.baseproject.demo4.LayerListActivity;
import pro.base.com.baseproject.demo6.AriaActivity;
import pro.base.com.baseproject.demo7.BaseRecyclerActivity;
import pro.base.com.baseproject.demo7.BaseRecyclerActivityTest;

public class MainActivity extends BaseActivity {

    @ViewInject(value = R.id.textView1)
    private TextView mText1;
    @ViewInject(value = R.id.textView2)
    private TextView mText2;
    @ViewInject(value = R.id.textView3)
    private TextView mText3;
    @ViewInject(value = R.id.textView4)
    private TextView mText4;
    @ViewInject(value = R.id.textView5)
    private TextView mText5;
    @ViewInject(value = R.id.textView6)
    private TextView mText6;
    @ViewInject(value = R.id.textView7)
    private TextView mText7;
    private String[] demoName = Constant.DEMO_NAME;

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
        mText4.setOnClickListener(this);
        mText5.setOnClickListener(this);
        mText6.setOnClickListener(this);
        mText7.setOnClickListener(this);


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
            case R.id.textView4:
                startActivity(new Intent(mContext,LayerListActivity.class));
                break;
            case R.id.textView5:
                startActivity(new Intent(mContext,AriaActivity.class));
                break;
            case R.id.textView6:
                startActivity(new Intent(mContext,BaseRecyclerActivity.class));
                break;
            case R.id.textView7:
                startActivity(new Intent(mContext,BaseRecyclerActivityTest.class));
                break;

        }

    }




}
