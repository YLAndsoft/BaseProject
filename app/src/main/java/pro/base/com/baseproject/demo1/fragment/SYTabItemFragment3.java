package pro.base.com.baseproject.demo1.fragment;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import base.BaseFragment;
import pro.base.com.baseproject.R;

/**
 * Created by DN on 2018/1/10.
 */

public class SYTabItemFragment3 extends LazyLoadBaseFragment {

    @ViewInject(value = R.id.tv_tab_Name)
    TextView tv_tab_Name;
    @ViewInject(value = R.id.pro)
    ProgressBar pro;

    String tabName;
    public SYTabItemFragment3(){
    }
    public SYTabItemFragment3(String tabName){
        this.tabName = tabName;
    }


    @Override
    public void initLazy() {
        setIsLazyload(true);//是否开启懒加载
        setIsRefresh(false); //是否开启缓存
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        pro.setVisibility(View.GONE);
        tv_tab_Name.setVisibility(View.VISIBLE);
        tv_tab_Name.setText("可见内容"+tabName+"");

    }

    @Override
    public void widgetClick(View v) {

    }
}
