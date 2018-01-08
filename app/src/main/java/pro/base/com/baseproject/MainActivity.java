package pro.base.com.baseproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.BaseActivity;
import pro.base.com.baseproject.demo1.DemoActivity;

public class MainActivity extends BaseActivity {
    @ViewInject(value = R.id.textView)
    private TextView mText;
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
        mText.setOnClickListener(this);//绑定监听事件
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.textView:
                startActivity(new Intent(mContext,DemoActivity.class));
                break;
        }

    }




}
