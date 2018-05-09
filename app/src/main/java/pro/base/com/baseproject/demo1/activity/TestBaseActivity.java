package pro.base.com.baseproject.demo1.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import fyl.base.BaseActivity;
import pro.base.com.baseproject.R;

/**
 * Created by DN on 2018/1/17.
 */

public class TestBaseActivity extends BaseActivity {
    private TextView test1,test2,test3;
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
        return R.layout.test_activity;
    }

    @Override
    public void initView(View view) {
        test1 = findViewById(R.id.test1);
        test2 = findViewById(R.id.test2);
        test3 = findViewById(R.id.test3);
    }

    @Override
    public void setListener() {
        test1.setOnClickListener(this);
        test2.setOnClickListener(this);
        test3.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        test1.setText("使用说明");
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.test1:
                showToast("使用说明>>>");
                break;
            case R.id.test2:
                showToast("22222222222");
                break;
            case R.id.test3:
                showToast("33333333333");
                break;
        }
    }
}
