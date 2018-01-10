package pro.base.com.baseproject.demo1.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import constant.Constant;

/**
 * Created by DN on 2018/1/10.
 */

public abstract class LazyLoadBaseFragment extends Fragment implements View.OnClickListener{
    protected View mContextView = null;
    protected Context mContext;
    private boolean isLazyload ; //是否使用懒加载
    private boolean isRefresh;//是否开启刷新(使用缓存)
    private boolean isLoadData;//是否已经加载过界面
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    /**
     * 此方法是在onCreateView之前调用
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        initLazy();
        //false:不使用懒加载 true:使用懒加载
        if(isLazyload&&isVisibleToUser){ //开启的情况 &&可见
            if(!isRefresh){ //不使用缓存
                initLazyData();
            }else{
                if(!isLoadData){
                    initLazyData();
                }
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = bindView();//绑定布局
        if (null == mView) {
            mContextView = inflater.inflate(bindLayout(), null, false);
        } else{
            mContextView = mView;
        }
        initView();//初始化控件
        if(!isLazyload){
            initData_();
        }
        return mContextView;
    }

    /**
     * 使用懒加载
     * 在这里面做加载数据操作
     */
    public abstract void initLazy();

    /**
     * 是否使用懒加载 注意：此方法必须在initLazy()里面调用
     * @param isLazyload
     */
    public void setIsLazyload(boolean isLazyload){
        this.isLazyload = isLazyload;
    }
    /**
     * 是否使用缓存
     * @param isRefresh
     */
    public void setIsRefresh(boolean isRefresh){
        this.isRefresh = isRefresh;
    }
    /**
     * 数据是否加载过
     * @param isLoadData
     */
    public void setIsLoadData(boolean isLoadData){
        this.isLoadData = isLoadData;
    }

    /**
     * [绑定视图]
     *
     * @return
     */
    public abstract View bindView();

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * [初始化控件]
     *
     */

    private void initData_() {
        initData();
    }
    private void initLazyData(){
        initData();
    }
    /**
     * 控件的初始化
     */
    protected abstract void initView();

    /**
     * 数据显示
     */
    protected abstract void initData();

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    /** View点击 **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    /**
     * [吐出Toast]
     * @param msg
     */
    protected void showToast(String msg){
        if(Constant.isShowToast){
            if(null!=getActivity()){
                Toast.makeText(getActivity(),msg, Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
     * [打印日志]
     * @param msg
     */
    public static final String TAG1 = "BaseActivity";
    protected void showLog(String msg){
        if(Constant.isShowLog){
            Log.i(TAG1,msg);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
       /* if(isRefresh){
            initData_();//加载数据
        }*/

    }

    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
