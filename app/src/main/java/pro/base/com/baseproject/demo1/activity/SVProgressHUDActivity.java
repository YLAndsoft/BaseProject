package pro.base.com.baseproject.demo1.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import fyl.base.BaseActivity;
import fyl.base.widget.SVProgressHUD;
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
    public void initParms(Bundle parms) {
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
    public void setListener() {
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
                if(!SVProgressHUD.isShowing(mContext)){
                    SVProgressHUD.show(mContext);
                }else{
                    SVProgressHUD.dismiss(mContext);
                }
                break;
            case R.id.text2:
                //加载中，无文字版本SVProgressHUDMaskType属性根据需求设置
                if(!SVProgressHUD.isShowing(mContext)){
                    SVProgressHUD.showWithMaskType(mContext, SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
                }else{
                    SVProgressHUD.dismiss(mContext);
                }
                break;
            case R.id.text3:
                if(!SVProgressHUD.isShowing(mContext)){
                    SVProgressHUD.showWithStatus(mContext,"加载中...",SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
                }else{
                    SVProgressHUD.dismiss(mContext);
                }
                break;
            case R.id.text4:
                if(!SVProgressHUD.isShowing(mContext)){
                    SVProgressHUD.showInfoWithStatus(mContext,"加载异常！",SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
                }else{
                    SVProgressHUD.dismiss(mContext);
                }
                break;
            case R.id.text5:
                if(!SVProgressHUD.isShowing(mContext)){
                    SVProgressHUD.showSuccessWithStatus(mContext,"加载成功！",SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
                }else{
                    SVProgressHUD.dismiss(mContext);
                }
                break;
            case R.id.text6:
                if(!SVProgressHUD.isShowing(mContext)){
                    SVProgressHUD.showErrorWithStatus(mContext,"加载失败！",SVProgressHUD.SVProgressHUDMaskType.GradientCancel);
                }else{
                    SVProgressHUD.dismiss(mContext);
                }
                break;



        }
    }
}
