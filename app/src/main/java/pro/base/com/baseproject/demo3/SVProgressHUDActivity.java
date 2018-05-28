package pro.base.com.baseproject.demo3;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import fyl.base.base.BaseActivity;
import fyl.base.widget.SVP;
import pro.base.com.baseproject.R;

/**
 * Created by DN on 2018/1/26.
 */

public class SVProgressHUDActivity extends BaseActivity {
    @ViewInject(value = R.id.text1)
    private TextView text1;
    @ViewInject(value = R.id.text2)
    private TextView text2;
    @ViewInject(value = R.id.text3)
    private TextView text3;
    @ViewInject(value = R.id.text4)
    private TextView text4;
    @ViewInject(value = R.id.text5)
    private TextView text5;
    @ViewInject(value = R.id.text6)
    private TextView text6;


    @Override
    public void initParms(Intent parms) {
        setAllowFullScreen(true);
        setScreenRoate(false);
        setSteepStatusBar(false);
        setSetActionBarColor(true, R.color.white);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.svp_layout;
    }

    @Override
    public void initView(View view) {
        x.view().inject(this);
    }

    @Override
    public void initListener() {
        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
        text4.setOnClickListener(this);
        text5.setOnClickListener(this);
        text6.setOnClickListener(this);

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.text1:
                //加载中，无文字版本SVProgressHUDMaskType属性根据需求设置，默认是：Black
                if(!SVP.isShowing(mContext)){
                    SVP.show(mContext);
                }else{
                    SVP.dismiss(mContext);
                }
                break;
            case R.id.text2:
                //加载中，无文字版本SVProgressHUDMaskType属性根据需求设置
                if(!SVP.isShowing(mContext)){
                    SVP.showWithMaskType(mContext, SVP.SVProgressHUDMaskType.GradientCancel);
                }else{
                    SVP.dismiss(mContext);
                }
                break;
            case R.id.text3:
                if(!SVP.isShowing(mContext)){
                    SVP.showWithStatus(mContext,"加载中...", SVP.SVProgressHUDMaskType.GradientCancel);
                }else{
                    SVP.dismiss(mContext);
                }
                break;
            case R.id.text4:
                if(!SVP.isShowing(mContext)){
                    SVP.showInfoWithStatus(mContext,"加载异常！", SVP.SVProgressHUDMaskType.GradientCancel);
                }else{
                    SVP.dismiss(mContext);
                }
                break;
            case R.id.text5:
                if(!SVP.isShowing(mContext)){
                    SVP.showSuccessWithStatus(mContext,"加载成功！", SVP.SVProgressHUDMaskType.GradientCancel);
                }else{
                    SVP.dismiss(mContext);
                }
                break;
            case R.id.text6:
                if(!SVP.isShowing(mContext)){
                    SVP.showErrorWithStatus(mContext,"加载失败！", SVP.SVProgressHUDMaskType.GradientCancel);
                }else{
                    SVP.dismiss(mContext);
                }
                break;



        }
    }
}
