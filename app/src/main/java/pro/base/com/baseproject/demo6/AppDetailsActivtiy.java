package pro.base.com.baseproject.demo6;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;
import java.util.HashMap;
import java.util.Map;
import fyl.base.BaseActivity;
import fyl.base.Utils.UtilTool;
import fyl.base.Utils.ViewAnimationUtils;
import fyl.base.XutilsHttp.XutilsHttp;
import fyl.base.views.MyListView;
import fyl.base.views.MyScrollView;
import pro.base.com.baseproject.Constant;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo6.bean.App;
import pro.base.com.baseproject.demo6.bean.AppSimpleView;

/**
 * app详情界面
 * Created by DN on 2018/3/19.
 */

public class AppDetailsActivtiy extends BaseActivity implements MyScrollView.OnScrollChangedListener {
    //title
    @ViewInject(value = R.id.detials_rl_head)
    private RelativeLayout detials_rl_head;
    @ViewInject(value = R.id.detials_iv_back)
    private ImageView detials_iv_back;
    @ViewInject(value = R.id.details_topAppName)
    private TextView details_topAppName;
    //top
    @ViewInject(value = R.id.details_top_body)
    private LinearLayout details_top_body;//顶部下载布局
    @ViewInject(value = R.id.details_top_appicon)
    private ImageView details_top_appicon;//顶部应用图标
    @ViewInject(value = R.id.details_top_appdesc)
    private TextView top_appdesc;//顶部应用描述
    @ViewInject(value = R.id.details_top_progressBar)
    private ProgressBar top_progressBar;//顶部下载进度控件
    @ViewInject(value = R.id.details_top_download)
    private TextView details_top_download;//顶部下载
    //content
    @ViewInject(value = R.id.details_ll_mark)
    private LinearLayout details_ll_mark;
    @ViewInject(value = R.id.details_scrollview)
    private MyScrollView details_scrollview;
    @ViewInject(value = R.id.details_iv_appicon)
    private ImageView details_iv_appicon;//应用图标
    @ViewInject(value = R.id.details_tv_appname)
    private TextView details_tv_appname;//应用名称
    @ViewInject(value = R.id.details_tv_appbrief)
    private TextView details_tv_appbrief;//应用说明
    @ViewInject(value = R.id.details_tv_appstatus1)
    private TextView details_tv_appstatus1;//安装人数
    @ViewInject(value = R.id.details_tv_appstatus2)
    private TextView details_tv_appstatus2;//应用大小
    @ViewInject(value = R.id.details_tv_appstatus3)
    private TextView details_tv_appstatus3;//更新日期
    @ViewInject(value = R.id.details_progressBar)
    private ProgressBar details_progressBar;//下载进度控件
    @ViewInject(value = R.id.details_tv_download)
    private TextView details_tv_download;//下载
    @ViewInject(value = R.id.details_tv_apptag1)
    private TextView details_tv_apptag1;//标签
    @ViewInject(value = R.id.details_tv_apptag2)
    private TextView details_tv_apptag2;//标签
    @ViewInject(value = R.id.details_iv_apppic1)
    private ImageView details_iv_apppic1;//应用截图
    @ViewInject(value = R.id.details_iv_apppic2)
    private ImageView details_iv_apppic2;//应用截图
    @ViewInject(value = R.id.details_iv_apppic3)
    private ImageView details_iv_apppic3;//应用截图
    @ViewInject(value = R.id.details_iv_apppic4)
    private ImageView details_iv_apppic4;//应用截图
    @ViewInject(value = R.id.details_iv_apppic5)
    private ImageView details_iv_apppic5;//应用截图
    @ViewInject(value = R.id.details_tv_report)
    private TextView details_tv_report;//举报
    @ViewInject(value = R.id.details_tv_appdescdetail)
    private TextView details_tv_appdescdetail;//描述
    @ViewInject(value = R.id.details_tv_appupdatetime)
    private TextView details_tv_appupdatetime;//更新时间
    @ViewInject(value = R.id.details_tv_applatestversion)
    private TextView details_tv_applatestversion;//最新版本
    @ViewInject(value = R.id.details_tv_appsource)
    private TextView details_tv_appsource;//应用来源
    @ViewInject(value = R.id.details_tv_apppermission)
    private TextView  details_tv_apppermission;//应用权限
    @ViewInject(value = R.id.details_edt_comments)
    private EditText details_edt_comments;//评论edit
    @ViewInject(value = R.id.details_lv_comments)
    private MyListView details_lv_comments;//展示评论的控件
    @ViewInject(value = R.id.details_progress1)
    private ProgressBar details_progress1;

    private App app;
    private String appID;
    private AppSimpleView appSimpleView;


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
        return R.layout.app_details_activity;
    }
    @Override
    public void initView(View view) {
        x.view().inject(this);
    }
    @Override
    public void setListener() {
        details_top_download.setOnClickListener(this);
        details_tv_download.setOnClickListener(this);
        details_iv_apppic1.setOnClickListener(this);
        details_iv_apppic2.setOnClickListener(this);
        details_iv_apppic3.setOnClickListener(this);
        details_iv_apppic4.setOnClickListener(this);
        details_iv_apppic5.setOnClickListener(this);
        details_tv_report.setOnClickListener(this);
        details_edt_comments.setOnClickListener(this);

    }
    @Override
    public void doBusiness(Context mContext) {
        getTag();//获取指定页面传过来的页面标识和应用详情信息
        getHttpAppInfo(appID); //从网络上获取app详情数据
    }
    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.details_top_download:
                break;
            case R.id.details_tv_download:
                break;
            case R.id.details_iv_apppic1:
                break;
            case R.id.details_iv_apppic2:
                break;
            case R.id.details_iv_apppic3:
                break;
            case R.id.details_iv_apppic4:
                break;
            case R.id.details_iv_apppic5:
                break;
            case R.id.details_tv_report:
                break;
            case R.id.details_edt_comments:
                break;
        }
    }
    /**
     * 获取传过来的信息，标识等
     */
    public void getTag() {
        appID = getIntent().getStringExtra("appID");
        appSimpleView = (AppSimpleView) getIntent().getSerializableExtra("app");
        if(appID==null||appID.equals("")){
            appID = appSimpleView.getAppID()+"";
        }
    }

    /**
     * 设置数据
     */
    private void setData(App app) {
        if(app!=null){
            details_progress1.setVisibility(View.GONE);
            details_topAppName.setText(app.getAppName()+"");
            details_tv_appname.setText(app.getAppName()+"");//应用名称
            //top
            try{
                //顶部应用图标
                Glide.with(mContext).load(app.getIconUrl()).error(R.drawable.default_icon).into(details_top_appicon);
                //应用图标
                Glide.with(mContext).load(app.getIconUrl()).error(R.drawable.default_icon).into(details_iv_appicon);
            }catch (Exception ex){}

            int num = app.getAppInstallCount();
            if (num < 10000) {
                String str = num + "人安装";
                details_tv_appstatus1.setText(str);// 安装人次
            } else {
                int i = num / 10000;
                String str = i + "万人安装";
                details_tv_appstatus1.setText(str);// 安装人次
            }
            // 大小
            if(Constant.isAppsize){//应用大小
                int appSize = app.getAppSize();
                String format = UtilTool.format1(appSize);
                details_tv_appstatus2.setText(format+"");
            }else{
                details_tv_appstatus2.setText(app.getAppActualSize()+"MB");
            }
            //更新日期
            String appupdate = app.getAppUpdateTime();
            String[] strs = appupdate.split("\\.");
            String str = "";
            for (int i = 0; i < strs.length; i++) {
                str = strs[1] + "月" + strs[2] + "日更新";
            }
            details_tv_appstatus3.setText(str);
            //标签
            String[] tags = app.getAppTag().split("\\|");
            if(tags.length>0){
                switch (tags.length){
                    case 1:
                        details_tv_apptag1.setText(tags[0]);
                        details_tv_apptag1.setBackground(mContext.getResources().getDrawable(R.drawable.appdetails_tag_shpae));
                        break;
                    case 2:
                        details_tv_apptag2.setText(tags[1]);
                        /*Random random = new Random();
                        int index = random.nextInt(6);
                        tv_apptag2.setBackgroundResource(tagBgs[index]);*/
                        details_tv_apptag2.setBackground(mContext.getResources().getDrawable(R.drawable.appdetails_tag_shpae2));
                        break;
                    default:
                        details_tv_apptag1.setText(tags[0]);
                        details_tv_apptag1.setBackground(mContext.getResources().getDrawable(R.drawable.appdetails_tag_shpae));
                        details_tv_apptag2.setText(tags[1]);
                        details_tv_apptag2.setBackground(mContext.getResources().getDrawable(R.drawable.appdetails_tag_shpae2));
                        break;
                }
            }else{
                details_tv_apptag1.setVisibility(View.GONE);
                details_tv_apptag2.setVisibility(View.GONE);
            }
            details_tv_appbrief.setText(app.getAppBrief()+"");//应用说明
            top_appdesc.setText(app.getAppBrief()+"");//顶部应用简介

            if (app.getAppImgOne() != null&&!app.getAppImgOne().equals("")) {
                Glide.with(mContext).load(app.getAppImgOne()+"").error(R.drawable.default_icon).into(details_iv_apppic1);
            }else{
                details_iv_apppic1.setVisibility(View.GONE);
            }
            if (app.getAppImgTwo() != null&&!app.getAppImgTwo().equals("")) {
                Glide.with(mContext).load(app.getAppImgTwo()+"").error(R.drawable.default_icon).into(details_iv_apppic2);
            }else{
                details_iv_apppic2.setVisibility(View.GONE);
            }
            if (app.getAppImgThree() != null&&!app.getAppImgThree().equals("")) {
                Glide.with(mContext).load(app.getAppImgThree()+"").error(R.drawable.default_icon).into(details_iv_apppic3);
            }else{
                details_iv_apppic3.setVisibility(View.GONE);
            }
            if (app.getAppImgFour() != null&&!app.getAppImgFour().equals("")) {
                Glide.with(mContext).load(app.getAppImgFour()+"").error(R.drawable.default_icon).into(details_iv_apppic4);
            }else{
                details_iv_apppic4.setVisibility(View.GONE);
            }
            if (app.getAppImgFive() != null&&!app.getAppImgFive().equals("")) {
                Glide.with(mContext).load(app.getAppImgFive()+"").error(R.drawable.default_icon).into(details_iv_apppic5);
            }else{
                details_iv_apppic5.setVisibility(View.GONE);
            }

            // 应用描述
            details_tv_appdescdetail.setText(app.getAppDescribe());
            // 更新时间
            details_tv_appupdatetime.setText(app.getAppUpdateTime());
            // 版本
            details_tv_applatestversion.setText(app.getVersion());
            // 来源
            details_tv_appsource.setText(app.getAppSource());
            // 开发者
            //tv_appdeveloper.setText(app.getAppDeveloper());
            // 权限
            details_tv_apppermission.setText(app.getAppPermission());
        }else{
            details_progress1.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 根据ID获取详情数据
     * @param appID
     */
    public void getHttpAppInfo(String appID) {
        String url = "http://120.25.155.50/AppMarket/web/appServlet/getAppByID";
        Map<String,String> map = new HashMap<>();
        map.put("appID",appID+"");
        XutilsHttp.xUtilsPost(url, map, new XutilsHttp.XUilsCallBack() {
            @Override
            public void onResponse(String result) {
                try{
                    Gson gson = new Gson();
                    App app1 = gson.fromJson(result, new TypeToken<App>(){}.getType());
                    Log.i("app结果===",app1.getAppName()+"");
                    if(app1!=null){
                        app = app1;
                        setData(app); //设置数据
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            @Override
            public void onFail(String result) {
                details_progress1.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onScrollChanged(int x, int y, int oldl, int oldt) {
        if (y> 0) {
            Rect scrollBounds = new Rect();
            details_scrollview.getHitRect(scrollBounds);
            if (details_ll_mark.getLocalVisibleRect(scrollBounds)) {
                // 可见
                if (details_top_body != null && details_top_body.getVisibility()==View.VISIBLE) {
                    // 关闭
                    ViewAnimationUtils.goneViewByAlpha(details_top_body,1000);

                }
            } else {
                if (details_top_body != null && details_top_body.getVisibility()==View.GONE) {
                    // 显示
                    ViewAnimationUtils.visibleViewByAlpha(details_top_body,700);
                }
            }
        }
    }

    @Override
    public void onWindowFocusChanged(final boolean hasFocus) {
        if (hasFocus){
            details_scrollview.setOnScrollChangedListener(this);
        }
        details_scrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(hasFocus){
                    return false;
                }else{
                    return true;
                }
            }
        });
    }


}
