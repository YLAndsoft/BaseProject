package pro.base.com.baseproject.demo1.fragment;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import fyl.base.BaseLazyLoadFragment;
import pro.base.com.baseproject.R;

/**
 * 此fragment已使用可见加载
 * Created by DN on 2018/1/10.
 */

public class SYTabItemFragment extends BaseLazyLoadFragment {

    @ViewInject(value = R.id.tv_tab_Name)
    private TextView tv_tab_Name;
    @ViewInject(value = R.id.pro)
    private ProgressBar pro;

    private String tabName;
    /** 标志位，标志已经初始化完成 */
    private boolean isPrepared;
    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    private boolean mHasLoadedOnce;

    public SYTabItemFragment(){
    }
    public SYTabItemFragment(String tabName){
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
        isPrepared = true;//标识已经初始化完成
    }

    @Override
    protected void initData() {
        if(!isVisible || !isPrepared || mHasLoadedOnce){
            return;
        }
         mHasLoadedOnce = true;//标识已经加载过
         //这里做加载数据的操作
         pro.setVisibility(View.GONE);
         tv_tab_Name.setVisibility(View.VISIBLE);
         tv_tab_Name.setText("可见内容"+tabName+"");
        //绑定监听，否则点击无效
         tv_tab_Name.setOnClickListener(this);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.tv_tab_Name:
                showToast("点击了"+tv_tab_Name.getText().toString());
                //此Activity继承BaseActivity

                break;
        }
    }
}
