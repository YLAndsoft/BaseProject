package pro.base.com.baseproject.demo1.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.ArrayList;
import java.util.List;
import fyl.base.BaseFragment;
import pro.base.com.baseproject.Constant;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo1.adpter.ClassifyViewPagerAdapter;
import pro.base.com.baseproject.demo1.view.PagerSlidingTabStrip;

/**
 * 首页
 * Created by DN on 2018/1/8.
 */

public class SYFragment extends BaseFragment {

    @ViewInject(value = R.id.pleasure_tabs)
    PagerSlidingTabStrip pleasure_tabs;
    @ViewInject(value = R.id.vp_FindFragment_pager)
    ViewPager mViewPage;
    private List<Fragment> fragments = new ArrayList<>();
    private ClassifyViewPagerAdapter classViewPagerAdapter;
    private SYTabItemFragment sytFragment1;
    private SYTabItemFragment2 sytFragment2;
    private SYTabItemFragment3 sytFragment3;


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.sy_activity;
    }

    @Override
    protected void initView() {
        x.view().inject(this,mContextView);
    }

    @Override
    protected void initData() {
        //此页卡是一次性全部加载，如页卡多的话，不建议使用次方法加载。
        //可继承BaseLazyLoadFragment 来做可见的时候加载数据操作。
        List<String> list = new ArrayList<>();
        sytFragment1 = new SYTabItemFragment(Constant.tabName[0]);
        sytFragment2 = new SYTabItemFragment2(Constant.tabName[1]);
        sytFragment3 = new SYTabItemFragment3(Constant.tabName[2]);

        list.add(Constant.tabName[0]);
        list.add(Constant.tabName[1]);
        list.add(Constant.tabName[2]);
        fragments.add(sytFragment1);
        fragments.add(sytFragment2);
        fragments.add(sytFragment3);
        classViewPagerAdapter = new ClassifyViewPagerAdapter(getFragmentManager(), list, fragments);
        mViewPage.setAdapter(classViewPagerAdapter);
        mViewPage.setOffscreenPageLimit(Constant.tabName.length);//依据传过来的tab页的个数来设置缓存的页数
        //tabs.setFollowTabColor(true);//设置标题是否跟随
        pleasure_tabs.setViewPager(mViewPage);
    }

    @Override
    public void widgetClick(View v) {

    }
}
