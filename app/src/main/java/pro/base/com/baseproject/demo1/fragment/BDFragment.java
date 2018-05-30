package pro.base.com.baseproject.demo1.fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import fyl.base.XutilsHttp.XutilsHttp;
import fyl.base.base.BaseFragment;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo1.ad.adapter.ADListViewAdpter;
import pro.base.com.baseproject.demo1.ad.bean.ADConfig;
import pro.base.com.baseproject.demo1.ad.bean.AdvertisingSimpleness;
import pro.base.com.baseproject.demo1.ad.contants.Contants;
import pro.base.com.baseproject.demo1.ad.utils.AUtils;
import pro.base.com.baseproject.demo1.ad.views.MyListView;
import pro.base.com.baseproject.demo1.ad.views.MyLoadScrollView;
/**
 * Created by DN on 2018/1/8.
 */

public class BDFragment extends BaseFragment implements ADListViewAdpter.OnDownloadClick {

    private MyLoadScrollView mScrollView;
    private MyListView mLv;
    private View mListViewFooter;//加载更多view
    private LinearLayout ll_loading;
    private TextView tv_loading;
    private ProgressBar load_pro;

    private static int num = 1;
    private static int size = 8;
    private boolean hasData = true;//判断是否还有数据
    private boolean isHasFoot = false;//判断是否添加加载更多布局

    private ADConfig config;//配置文件信息
    private ADListViewAdpter  ava;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.bd_activity;
    }

    @Override
    protected void initView() {
        mScrollView = mContextView.findViewById(R.id.scrollview_hot);
        mLv = mContextView.findViewById(R.id.lv_hotspots);
        initLvFootView();//初始加载更多布局
    }

    /**
     * 初始化加载更多布局
     */
    private void initLvFootView() {
        mListViewFooter = LayoutInflater.from(mContext).inflate(R.layout.lv_footer, null, false);
        ll_loading = (LinearLayout) mListViewFooter.findViewById(R.id.ll_lvfoot_loading);
        tv_loading = (TextView) mListViewFooter.findViewById(R.id.tv_lvfoot_loading);
        load_pro = (ProgressBar) mListViewFooter.findViewById(R.id.progress_lv_load);
        tv_loading.setText("正在加载...");

    }

    @Override
    protected void initData() {
        try {
            config = AUtils.getConfig(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getData(); //获取数据
    }

    @Override
    public void widgetClick(View v) {

    }

    /**
     * 获取数据
     */
    private void getData() {
        Map<String, String> am = new HashMap<String, String>();
        am.put(Contants.appName, Contants.APP_NAME);
        am.put(Contants.pageNum, num+"");
        am.put(Contants.pageSize, size+"");
        XutilsHttp.xUtilsPost(Contants.ADURL, am, new XutilsHttp.XUilsCallBack() {
            @Override
            public void onResponse(String result) {
                if(result==null){
                    return;
                }
                Message msg = new Message();
                msg.what = Contants.RESULT;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onFail(String result) {
                Message msg = new Message();
                msg.what = Contants.ERROR;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        });
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            List<AdvertisingSimpleness> insertData = null;
            switch (msg.what){
                case Contants.RESULT:
                    try{
                        String str = (String)msg.obj;
                        if(null!=str&&!str.equals("")){
                            //插入配资文件数据
                            num++;
                            Gson gson = new Gson();
                            TypeToken<List<AdvertisingSimpleness>> type = new TypeToken<List<AdvertisingSimpleness>>() {};
                            List<AdvertisingSimpleness> mData = gson.fromJson(str, type.getType());
                            if(mData!=null&&mData.size()>0){
                                if(mData.size()>=size){
                                    hasData=true;
                                }else{
                                    hasData=false;
                                }
                                insertData = AUtils.insertData(mData,config);
                            }else{
                                insertData = AUtils.insertData(mData,config);
                            }
                        }else{
                            insertData = AUtils.insertData(null,config);
                            showToast("错误信息："+str);
                        }
                        if(insertData==null&&insertData.size()<1){
                            return;
                        }
                        mLv.setVerticalScrollBarEnabled(false);
                        mLv.addFooterView(mListViewFooter);
                        isHasFoot = true;
                        if(ava==null){
                            ava = new ADListViewAdpter(mContext,insertData);
                        }
                        // ava.setDownloadClock(downloadClock);
                        mLv.setAdapter(ava);
                        mScrollView.setOnBorderListener(new mOnBorderListener());
                        if(insertData.size()<2){
                            mLv.removeFooterView(mListViewFooter);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                        if (isHasFoot) {
                            mLv.removeFooterView(mListViewFooter);
                            isHasFoot = false;
                        }
                        showToast("错误信息："+e.getMessage().toString());
                    }

                    break;
                case Contants.ERROR:
                    if (isHasFoot) {
                        mLv.removeFooterView(mListViewFooter);
                        isHasFoot = false;
                    }
                    showToast("错误信息："+(String)msg.obj.toString());
                    break;
            }

        }
    };

    /**
     * 适配器点击下载回调
     * @param position
     * @param downloadState
     * @param as
     */
    @Override
    public void onDownClick(int position, int downloadState, AdvertisingSimpleness as) {
        //添加统计
        AUtils.addCount(as.getInfoTitle());
        //启动下载//后期处理放入apk里面去
        startDownload(position,downloadState,as);
    }

    private void startDownload(int position, int downloadState, AdvertisingSimpleness as) {
        switch (downloadState){
            case Contants.COMPLETE:
                //去安装

                break;
            case Contants.RUNING:
                //暂停

                break;
            case Contants.ERO:
            case Contants.INIT:
            case Contants.PASE:
                //去下载


                break;
        }



    }


    class mOnBorderListener implements MyLoadScrollView.OnBorderListener {

        @Override
        public void onBottom() {
            if (!isHasFoot) {
                mLv.addFooterView(mListViewFooter);
                isHasFoot = true;
            }
            if (hasData) {
                ll_loading.setVisibility(View.VISIBLE);
                tv_loading.setText("正在加载中...");
                load_pro.setVisibility(View.VISIBLE);
                if (AUtils.isNetworkAvailable(mContext)) {
                    //请求更多数据getData();
                    Map<String, String> am = new HashMap<String, String>();
                    am.put(Contants.appName, Contants.APP_NAME);
                    am.put(Contants.pageNum, num+"");
                    am.put(Contants.pageSize, size+"");
                    XutilsHttp.xUtilsPost(Contants.ADURL, am, new XutilsHttp.XUilsCallBack() {
                        @Override
                        public void onResponse(String result) {
                            if(result==null){
                                hasData = false;
                                mScrollView.isLoading = false;
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_loading.setText("数据已全部加载");
                                        load_pro.setVisibility(View.GONE);
                                    }
                                });
                                return;
                            }
                            Gson gson = new Gson();
                            TypeToken<List<AdvertisingSimpleness>> type = new TypeToken<List<AdvertisingSimpleness>>() {};
                            List<AdvertisingSimpleness> mData1 = gson.fromJson(result, type.getType());
                            if (mData1!=null&&mData1.size() > 0) {
                                num++;
                                hasData = true;
                                if(ava!=null){
                                    List<AdvertisingSimpleness> insertData = AUtils.insertData(mData1,config);
                                    ava.addData(insertData);
                                }
                            }else{
                                hasData = false;
                            }
                            if (hasData) {
                                mLv.postDelayed(new Runnable() {
                                    public void run() {
                                        mScrollView.isLoading = false;
                                        ll_loading.setVisibility(View.GONE);
                                    }
                                }, 200);

                            } else {
                                mScrollView.isLoading = false;
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        tv_loading.setText("数据已全部加载");
                                        load_pro.setVisibility(View.GONE);
                                    }
                                });
                            }
                        }
                        @Override
                        public void onFail(String result) {
                            Message msg = new Message();
                            msg.what = Contants.ERROR;
                            msg.obj = result;
                            mHandler.sendMessage(msg);
                        }
                    });
                }else{
                    if (isHasFoot) {
                        mLv.removeFooterView(mListViewFooter);
                        isHasFoot = false;
                    }
                    Toast.makeText(mContext, "请检查网络连接", Toast.LENGTH_SHORT).show();
                }
            } else {
                tv_loading.setText("数据已全部加载");
                load_pro.setVisibility(View.GONE);
            }

        }

        @Override
        public void onTop() {
        }

    }


    @Override
    public void onDestroy() {
        //
        super.onDestroy();
        //清理下载缓存状态
        num=1;
    }

}
