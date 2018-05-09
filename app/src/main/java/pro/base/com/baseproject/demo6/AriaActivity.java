package pro.base.com.baseproject.demo6;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import fyl.base.BaseActivity;
import pro.base.com.baseproject.R;

/**
 * Created by DN on 2018/3/22.
 */

public class AriaActivity extends BaseActivity {

    @ViewInject(value = R.id.aria_list)
    private TextView aria_list;
    @ViewInject(value = R.id.aria_manager)
    private TextView aria_manager;
    @ViewInject(value = R.id.aria_setting)
    private TextView aria_setting;

    @Override
    public void initParms(Bundle parms) {
        //此属性设置与状态栏相关
        setAllowFullScreen(true);//是否允许全屏true
        setScreenRoate(false);//是否允许屏幕旋转false
        setSteepStatusBar(false);//是否设置沉浸状态栏true
        setSetActionBarColor(true, R.color.system_bar_color);//设置状态栏主题颜色true
    }
    @Override
    public View bindView() {
        return null;
    }
    @Override
    public int bindLayout() {
        return R.layout.aria_activity;
    }
    @Override
    public void initView(View view) {
        x.view().inject(this);
    }
    @Override
    public void setListener() {
        aria_list.setOnClickListener(this);
        aria_manager.setOnClickListener(this);
        aria_setting.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        //这里操作逻辑等..

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.aria_list:
                //startActivity(new Intent(mContext,AriaListActivity.class));
                startActivity(new Intent(mContext,AriaRecyclerActvity.class));
                break;
            case R.id.aria_manager:
                //下载管理
                startActivity(new Intent(mContext,AriaDownManagerActivity.class));
                break;
            case R.id.aria_setting:
                //下载设置
                startActivity(new Intent(mContext,AriaSettingActivity.class));
                break;
        }
    }


}
