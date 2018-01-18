package pro.base.com.baseproject.demo1.fragment;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import fyl.base.BaseFragment;
import pro.base.com.baseproject.R;

/**
 * Created by DN on 2018/1/10.
 */

public class SYTabItemFragment2 extends BaseFragment {

    @ViewInject(value = R.id.tv_tab_Name)
    private TextView tv_tab_Name;
    @ViewInject(value = R.id.pro)
    private ProgressBar pro;
    private String tabName;
    public SYTabItemFragment2(){
    }
    public SYTabItemFragment2(String tabName){
        this.tabName = tabName;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.tabitem_layout;
    }

    @Override
    protected void initView() {
        x.view().inject(this,mContextView);
    }

    @Override
    protected void initData() {
        pro.setVisibility(View.GONE);
        tv_tab_Name.setVisibility(View.VISIBLE);
        tv_tab_Name.setText("可见内容"+tabName+"");
    }

    @Override
    public void widgetClick(View v) {

    }
}
