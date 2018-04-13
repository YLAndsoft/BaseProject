package pro.base.com.baseproject.demo7;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import org.xutils.x;
import fyl.base.BaseActivity;
import pro.base.com.baseproject.R;

/**
 * Created by DN on 2018/4/9.
 */

public class VideoActivity extends BaseActivity {
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
        return R.layout.video_activity;
    }

    @Override
    public void initView(View view) {
        x.view().inject(this);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void doBusiness(Context mContext) {

    }


    @Override
    public void widgetClick(View v) {

    }

}
