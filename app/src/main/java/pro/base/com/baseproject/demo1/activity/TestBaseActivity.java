package pro.base.com.baseproject.demo1.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import fyl.base.BaseActivity;
import pro.base.com.baseproject.R;

/**
 * Created by DN on 2018/1/17.
 */

public class TestBaseActivity extends BaseActivity {
    @Override
    public void initParms(Bundle parms) {
        setAllowFullScreen(true);
        setScreenRoate(false);
        setSteepStatusBar(false);
        setSetActionBarColor(true, R.color.white);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.test_base_activity;
    }

    @Override
    public void initView(View view) {

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
