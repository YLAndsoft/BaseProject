package fyl.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import java.util.List;
import fyl.base.constant.Constant;
import pro.base.com.baselibrary.R;
import fyl.base.widget.SystemBarTintManager;


/**
 * Created by DN on 2017/7/22.
 */

public abstract class BaseFragmentActivity extends FragmentActivity implements View.OnClickListener{
    protected Context mContext;
    private FragmentManager fm;
    private Fragment mFragment;
    /** 是否沉浸状态栏**/
    private boolean isSetStatusBar = true;
    /** 是否允许全屏 **/
    private boolean mAllowFullScreen = true;
    /** 是否禁止旋转屏幕 **/
    private boolean isAllowScreenRoate = false;
    /** 是否设置状态栏颜色*/
    private boolean isSetActionBarColor = true;
    /** 当前Activity渲染的视图View **/
    protected View mContextView = null;
    /** 日志输出标志 **/
    protected final String TAG = this.getClass().getSimpleName();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        this.fm = getSupportFragmentManager();
        Bundle bundle = getIntent().getExtras();
        initParms(bundle);
        View mView = bindView();
        if (null == mView) {
            mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        } else
            mContextView = mView;
        if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (isSetStatusBar) {
            steepStatusBar();
        }
        if (isSetActionBarColor) {
            setActionBarColor();
        } ;
        setContentView(mContextView);
        if (!isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        initView(mContextView);
        setListener();
        doBusiness(this);



    }
    /**
     * 选择Fragment
     * @param flResId
     * @param fragment
     */
    FragmentTransaction ft;
    protected void switchFragment(int flResId, Fragment fragment) {
        try {
            if ((flResId == 0) || (this.fm == null) || (fragment == null)) {
                return;
            }
            if ((fragment != null) && (this.mFragment != fragment)) {
                ft = this.fm.beginTransaction();
                if (this.mFragment == null) {
                    ft.add(flResId, fragment).commit();
                }else if (!fragment.isAdded()) {
                    ft.hide(this.mFragment).add(flResId, fragment).commit();
                }else {
                    ft.hide(this.mFragment).show(fragment).commit();
                }
                this.mFragment = fragment;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 保存Fragment
     * @param flResId
     * @param listFragments
     */
    List<Fragment> listFragments;
    protected void setFragments(int [] flResId, List<Fragment> listFragments) {
        try {
            if ((flResId==null) || (this.fm == null) || (listFragments == null)) {
                return ;
            }
            ft = this.fm.beginTransaction();
            for(int i= 0;i<listFragments.size();i++){
                ft.add(flResId[i], listFragments.get(i));
            }
            ft.commit();
            this.listFragments = listFragments;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 显示Fragment
     * @param flResId
     * @param fragments
     */
    protected void showFragment(int flResId, Fragment fragments) {
        try {
            if ((flResId==0) || (this.fm == null) || (fragments == null)) {
                return ;
            }
            if ((fragments != null) && (this.listFragments != null)) {
                ft = this.fm.beginTransaction();
                for(int i = 0;i<listFragments.size();i++){
                    if(listFragments.get(i)==fragments){
                        ft.show(fragments);
                    }else{
                        ft.hide(listFragments.get(i));
                    }
                }
                ft.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 替换
     * @param flResId
     * @param fragment
     */
    protected void replaceFragment(int flResId, Fragment fragment) {
        if ((flResId == 0) || (this.fm == null) || (fragment == null)) {
            return;
        }
        FragmentTransaction ft = this.fm.beginTransaction();
        ft.replace(flResId, fragment);
        ft.commit();
    }
    /**
     * [初始化参数]
     *
     * @param parms
     */
    public abstract void initParms(Bundle parms);
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
     * @param view
     */
    public abstract void initView(final View view);

    /**
     * [绑定控件]
     *
     * @param resId
     *
     * @return
     */
    protected    <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    /**
     * [设置监听]
     */
    public abstract void setListener();

    /**
     * [业务操作]
     *
     * @param mContext
     */
    public abstract void doBusiness(Context mContext);


    public void toBack(View v)
    {
        finish();
    }
    private void setActionBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.system_bar_color);//通知栏所�?颜色
        }
    }
    /**
     * [沉浸状�?�栏]
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状�?�栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航�?
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    @Override
    public void onClick(View v) {
        widgetClick(v);
    }
    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseFragmentActivity.this,clz));
    }
    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
    /**
     * [吐出Toast]
     * @param msg
     */
    protected void showToast(String msg){
        if(Fyl.isShowToast){
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
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
    /**
     * [是否允许全屏]
     *
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /**
     * [是否设置沉浸状�?�栏]
     *
     * @param isSetStatusBar
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }
    /**
     * [是否允许屏幕旋转]
     *
     * @param isAllowScreenRoate
     */
    public void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

    public void setSetActionBarColor(boolean isSetActionBarColor) {
        this.isSetActionBarColor = isSetActionBarColor;
    }
    /** View点击 **/
    public abstract void widgetClick(View v);

    @Override
    public void onResume() {
        super.onResume();
        //MobclickAgent.onResume(this); //统计时长
    }

    @Override
    public void onPause() {
        super.onPause();
        //MobclickAgent.onPause(this);//统计时长
    }
}
