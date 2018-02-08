package pro.base.com.baseproject.demo1.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import fyl.base.BaseLazyLoadFragment;
import fyl.base.views.MyListView;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo1.DataUtils;
import pro.base.com.baseproject.demo1.GlideImageLoader;
import pro.base.com.baseproject.demo1.adpter.User1Adapter;
import pro.base.com.baseproject.demo1.entity.User;

/**
 * 此fragment已使用可见加载
 * Created by DN on 2018/1/10.
 */

public class SYTabItemFragment extends BaseLazyLoadFragment implements XRefreshView.XRefreshViewListener,OnBannerListener{

    @ViewInject(value = R.id.main_pull_refresh_view)
    private XRefreshView xRefreshView;
    @ViewInject(value = R.id.myList)
    private MyListView myList;
    @ViewInject(value = R.id.banner)
    private Banner banner;

    /** 标志位，标志已经初始化完成 */
    private boolean isPrepared;
    /** 是否已被加载过一次，第二次就不再去请求数据了 */
    private boolean mHasLoadedOnce;
    public static long lastRefreshTime;//刷新的时间
    public static int size = 1;
    public static boolean isData = false;

    private List<User> mData = new ArrayList<>();
    private User1Adapter u1Adapter;
    private List<String> images;
    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.tabitem1_layout;
    }

    @Override
    protected void initView() {
        x.view().inject(this,mContextView);
        initRefreshView();
        initBanner();
        isPrepared = true;//标识已经初始化完成
    }

    private void initRefreshView() {
        // 设置是否可以下拉刷新
        xRefreshView.setPullRefreshEnable(true);
        // 设置是否可以上拉加载
        xRefreshView.setPullLoadEnable(true);
        // 设置上次刷新的时间
        xRefreshView.restoreLastRefreshTime(lastRefreshTime);
        //当下拉刷新被禁用时，调用这个方法并传入false可以不让头部被下拉
        xRefreshView.setMoveHeadWhenDisablePullRefresh(false);
        // 设置时候可以自动刷新true 为自动刷新
        xRefreshView.setAutoRefresh(false);
        // 设置时候可以自动加载  true为自动加载
        xRefreshView.setAutoLoadMore(false);
        //刷新时不想让里面的列表滑动
        xRefreshView.setPinnedContent(true);
        xRefreshView.setXRefreshViewListener(this);
    }

    private void initBanner() {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        images = DataUtils.getImages();
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(DataUtils.getImageTitle());
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置点击事件
        banner .setOnBannerListener(this);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }
    @Override
    protected void initData() {
        if(!isVisible || !isPrepared || mHasLoadedOnce){
            return;
        }
         mHasLoadedOnce = true;//标识已经加载过
         //这里做加载数据的操作
        List<User> data = DataUtils.getData(size, mContext);
        isData= data!=null&&data.size()>0;
        if(data!=null&&data.size()>0){mData.addAll(data);}
        if(isData){size = size+1;}
        u1Adapter = new User1Adapter(mContext);
        myList.setAdapter(u1Adapter);
        u1Adapter.refresh(mData);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User user = mData.get(i);
                showToast(user.getUserName()+"");
            }
        });



    }

    @Override
    public void widgetClick(View v) {
    }


    @Override
    public void onRefresh() {
    }

    /**
     * 刷新
     * @param isPullDown
     */
    @Override
    public void onRefresh(boolean isPullDown) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<User> userList = DataUtils.getData(1, mContext);
                u1Adapter.refresh(userList);
                xRefreshView.stopRefresh();
                showToast("刷新完成");
            }
        },2000);

    }

    Handler mHandler = new Handler();

    /**
     * 加载更多
     * @param isSilence
     */
    @Override
    public void onLoadMore(boolean isSilence) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isData){
                    List<User> userList = DataUtils.getData(size+1, mContext);
                    isData = userList!=null&&userList.size()>0;
                    if(userList!=null&&userList.size()>0){
                        mData.addAll(userList);
                    }
                    if(isData){size = size+1;}
                    if(u1Adapter==null){
                        u1Adapter = new User1Adapter(mContext);
                    }
                    u1Adapter.loadMore(userList);
                    if(!xRefreshView.isStopLoadMore()){
                        xRefreshView.stopLoadMore();
                        showToast("加载完成");
                    }
                }else{
                    if(!xRefreshView.isStopLoadMore()){
                        xRefreshView.stopLoadMore();
                        showToast("没有更多数据了");
                    }

                }

            }
        },2000);

    }

    @Override
    public void onRelease(float direction) {
    }

    @Override
    public void onHeaderMove(double headerMovePercent, int offsetY) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        size=0;
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        //结束轮播
        banner.stopAutoPlay();
    }

    @Override
    public void OnBannerClick(int position) {
        showToast("点了第"+position+"张轮播图"+"\n"+"图片地址："+images.get(position));

    }
}
