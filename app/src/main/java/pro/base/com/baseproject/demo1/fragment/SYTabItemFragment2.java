package pro.base.com.baseproject.demo1.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import com.shizhefei.mvc.MVCCoolHelper;
import com.shizhefei.mvc.MVCHelper;
import com.shizhefei.view.coolrefreshview.CoolRefreshView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import fyl.base.BaseLazyLoadFragment;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo1.DataUtils;
import pro.base.com.baseproject.demo1.GlideImageLoader;
import pro.base.com.baseproject.demo1.UserDataSource;
import pro.base.com.baseproject.demo1.adpter.UserAdapter;
import pro.base.com.baseproject.demo1.entity.User;

/**
 * 此页面嵌套刷新，加载更多，适合那种没有轮播等业务的情况，
 * Created by DN on 2018/1/10.
 */

public class SYTabItemFragment2 extends BaseLazyLoadFragment  {

    @ViewInject(value = R.id.sy_refresh_view)
    private CoolRefreshView coolRefreshView;
    private MVCHelper<List<User>> mvcHelper;
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
        return R.layout.tabitem2_layout;
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
        initRefresh();

    }
    private void initRefresh() {
        mvcHelper  = new MVCCoolHelper<>(coolRefreshView);
        // 设置数据源
        mvcHelper.setDataSource(new UserDataSource(mContext));
        // 设置适配器
        mvcHelper.setAdapter(new UserAdapter(mContext));
        // 加载数据
        mvcHelper.refresh();
    }

    @Override
    public void widgetClick(View v) {

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        // 释放资源
        mvcHelper.destory();
    }
}
