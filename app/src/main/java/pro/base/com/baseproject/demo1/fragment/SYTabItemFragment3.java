package pro.base.com.baseproject.demo1.fragment;

import android.view.View;
import org.xutils.x;
import fyl.base.base.BaseLazyLoadFragment;
import pro.base.com.baseproject.R;

/**此fragment已使用可见加载
 * Created by DN on 2018/1/10.
 */

public class SYTabItemFragment3 extends BaseLazyLoadFragment {


    /** 标志位，标志已经初始化完成 */
    private boolean isPrepared;
    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    private boolean mHasLoadedOnce;

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
