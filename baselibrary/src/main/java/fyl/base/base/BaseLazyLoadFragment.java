package fyl.base.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import fyl.base.Fyl;
import fyl.base.constant.Constant;

/**
 * Created by DN on 2018/1/10.
 */

public abstract class BaseLazyLoadFragment extends Fragment implements View.OnClickListener{
    protected View mContextView = null;
    protected Context mContext;
    protected boolean isVisible;//界面是否可见
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
        if(getUserVisibleHint()){
            isVisible = true;
            initData_();
        }else{
            isVisible = false;
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
        if(isVisible){
            initData_();
        }
        return mContextView;
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
        if(Fyl.isShowToast){
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
        if(Fyl.isShowLog){
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
