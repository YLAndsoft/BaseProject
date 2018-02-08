package pro.base.com.baseproject.demo1.fragment;

import android.os.Handler;
import android.view.View;


import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import fyl.base.BaseLazyLoadFragment;
import fyl.base.views.MyListView;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo1.DataUtils;
import pro.base.com.baseproject.demo1.adpter.UserAdapter;
import pro.base.com.baseproject.demo1.entity.User;

/**此fragment已使用可见加载
 * Created by DN on 2018/1/10.
 */

public class SYTabItemFragment3 extends BaseLazyLoadFragment {


    /** 标志位，标志已经初始化完成 */
    private boolean isPrepared;
    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    private boolean mHasLoadedOnce;

    private List<User> userData;
    public static long lastRefreshTime;//刷新的时间
    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.tabitem3_layout;
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

    }
    @Override
    public void widgetClick(View v) {

    }

}
