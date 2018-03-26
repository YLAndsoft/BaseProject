package pro.base.com.baseproject.demo6;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arialyy.aria.core.Aria;
import com.arialyy.aria.core.common.RequestEnum;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;

import fyl.base.BaseActivity;
import fyl.base.Utils.StringUtils;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo6.utils.AppManageUtils;

/**
 * Created by DN on 2018/3/22.
 */

public class AriaSettingActivity extends BaseActivity {

    @ViewInject(value = R.id.setting_edit_task)
    private EditText setting_edit_task;
    @ViewInject(value = R.id.setting_task_commit)
    private Button setting_task_commit;
    @ViewInject(value = R.id.setting_edit_speed)
    private EditText setting_edit_speed;
    @ViewInject(value = R.id.setting_speed_commit)
    private Button setting_speed_commit;
    @ViewInject(value = R.id.request_get)
    private CheckBox request_get;
    @ViewInject(value = R.id.request_post)
    private CheckBox request_post;
    @ViewInject(value = R.id.setting_request_commit)
    private Button setting_request_commit;
    @ViewInject(value = R.id.download_path)
    private TextView download_path;
    @ViewInject(value = R.id.setting_toast_commit)
    private Button setting_toast_commit;


    @Override
    public void initParms(Bundle parms) {
        //此属性设置与状态栏相关
        setAllowFullScreen(true);//是否允许全屏true
        setScreenRoate(false);//是否允许屏幕旋转false
        setSteepStatusBar(false);//是否设置沉浸状态栏true
        setSetActionBarColor(true, R.color.system_bar_color);//设置状态栏主题颜色true
    }
    @Override
    public View bindView() {
        return null;
    }
    @Override
    public int bindLayout() {
        return R.layout.aria_setting_activity;
    }
    @Override
    public void initView(View view) {
        x.view().inject(this);
    }
    @Override
    public void setListener() {
        setting_task_commit.setOnClickListener(this);
        setting_speed_commit.setOnClickListener(this);
        setting_request_commit.setOnClickListener(this);
        setting_toast_commit.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        download_path.setText("下载文件保存路径：手机内存/appMarket/");
    }

    @Override
    public void widgetClick(View v) {

        switch (v.getId()){
            case R.id.setting_task_commit:
                String taskNumber = setting_edit_task.getText().toString().trim();
                if(!StringUtils.isEmpty(taskNumber)){
                    try{
                        int parseInt = Integer.parseInt(taskNumber);
                        if(parseInt>0&&parseInt<=8){
                            Aria.get(mContext).getDownloadConfig().setMaxTaskNum(parseInt);
                            showToast("设置成功!最大下载数为+"+parseInt);
                        }else{
                            showToast("请输入2-8之间的数字");
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                        showToast("请输入有效的数字");
                    }
                }else{
                    showToast("输入的内容不能为空！");
                }
                break;
            case R.id.setting_speed_commit:
                String taskNumber1 = setting_edit_speed.getText().toString().trim();
                if(!StringUtils.isEmpty(taskNumber1)){
                    try{
                        int parseInt = Integer.parseInt(taskNumber1);
                        if(parseInt>0){
                            Aria.download(mContext).setMaxSpeed(parseInt);
                            showToast("设置成功!最大下载速度为："+parseInt);
                        }else{
                            showToast("请输入1024的倍数");
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                        showToast("请输入有效的数字");
                    }
                }else{
                    showToast("输入的内容不能为空！");
                }
                break;
            case R.id.setting_toast_commit:
                /*boolean getChecked = request_get.isChecked();
                boolean postChecked = request_post.isChecked();
                if(getChecked){
                    Aria.download(this).load("").setRequestMode(RequestEnum.GET);
                }else if(postChecked){
                    Aria.download(this).load("").setRequestMode(RequestEnum.GET);
                }*/
                View view = LayoutInflater.from(mContext).inflate(R.layout.long_delete_layout,null);
                Toast toast = new Toast(mContext);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(view);
                toast.show();
                break;
        }
    }







}
