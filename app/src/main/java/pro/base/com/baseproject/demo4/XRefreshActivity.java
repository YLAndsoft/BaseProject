package pro.base.com.baseproject.demo4;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import org.xutils.x;
import fyl.base.BaseActivity;
import pro.base.com.baseproject.R;

/**
 * Created by DN on 2018/1/26.
 */

public class XRefreshActivity extends BaseActivity {


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
        return R.layout.xrefreshview_activity;
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
