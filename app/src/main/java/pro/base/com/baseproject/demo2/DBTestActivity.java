package pro.base.com.baseproject.demo2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import fyl.base.BaseActivity;
import pro.base.com.baseproject.R;

/**
 * Created by DN on 2018/1/18.
 */

public class DBTestActivity extends BaseActivity {
    @ViewInject(value = R.id.recycler_view)
    RecyclerView recycler_view;
    @ViewInject(value = R.id.btn_select)
    Button btn_select;
    @ViewInject(value = R.id.btn_add)
    Button btn_add;
    @ViewInject(value = R.id.btn_delete)
    Button btn_delete;
    @ViewInject(value = R.id.btn_update)
    Button btn_update;
    BaseRecyclerAdapter<App> adapter;

    @Override
    public void initParms(Bundle parms) {
        //此属性设置与状态栏相关
        setAllowFullScreen(true);//是否允许全屏
        setScreenRoate(false);//是否允许屏幕旋转
        setSteepStatusBar(false);//是否设置沉浸状态栏
        setSetActionBarColor(true, R.color.system_bar_color);//设置状态栏主题颜色
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.db_test_activity;
    }

    @Override
    public void initView(View view) {
        x.view().inject(this);
    }

    @Override
    public void setListener() {
        btn_select.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_update.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        List<App> apps = new ArrayList<>();
        apps.add(new App(1, "http://pp.myapp.com/ma_icon/0/icon_11352411_1520242062/256", "反恐枪战3D","1","1","1"));
        apps.add(new App(2, "http://pp.myapp.com/ma_icon/0/icon_10451659_1521083580/256", "天天炫斗","1","1","1"));
        apps.add(new App(3, "http://pp.myapp.com/ma_icon/0/icon_11679110_1497337052/256", "战—领地争","1","1","1"));
        apps.add(new App(4, "http://pp.myapp.com/ma_icon/0/icon_10475565_1460134419/256", "神之刃","1","1","1"));
        List<App> appList = DBManageHelper.queryAll(App.class);//查询所有
        if (appList == null) {
            DBManageHelper.insert(apps, App.class);
        }

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select:
                List<App> apps = DBManageHelper.queryAll(App.class);
                if(null==apps||apps.size()<=0){
                    showToast("查询失败！");
                }
                adapter = new BaseRecyclerAdapter<App>(mContext, apps, R.layout.recycler_item_layout) {
                    @Override
                    public void convert(BaseRecyclerHolder holder, App item, int position) {
                        holder.setText(R.id.app_name, item.getAppName());
                        holder.setImageByUrl(R.id.app_icon, item.getAppIcon());
                    }
                };
                LinearLayoutManager llManager = new LinearLayoutManager(mContext);
                //设置布局管理器
                llManager.setOrientation(OrientationHelper.VERTICAL);
                recycler_view.setLayoutManager(llManager);
                recycler_view.setAdapter(adapter);
                break;
            case R.id.btn_add:
                List<App> appss = DBManageHelper.queryAll(App.class);//查询所有
                App app = new App(appss.size() + 1, "http://pp.myapp.com/ma_icon/0/icon_42256123_1521193388/256", "增加APP","1","1","1");
                List<App> apps2 = DBManageHelper.insert(app, App.class);
                if (null == apps2|| apps2.size() <= 0) {
                    showToast("添加失败！");
                    return;
                }
                adapter.notifyDataChanged(apps2, true);
                break;
            case R.id.btn_delete:
                List<App> appst = DBManageHelper.queryAll(App.class);//查询所有
                if (appst == null || appst.size() <= 0) {
                    showToast("没有可删除的数据！");
                    return;
                }
                DBManageHelper.deleteById(App.class,(appst.size() - 1));
                List<App> appList = DBManageHelper.queryAll(App.class);//查询所有
                adapter.notifyDataChanged(appList, true);
                break;
            case R.id.btn_update:
                List<App> appsss = DBManageHelper.queryAll(App.class);
                if (null == appsss || appsss.size() <= 0) {
                    showToast("没有要修改的数据");
                    return;
                }
                App app3 = appsss.get(appsss.size() - 1);
                app3.setAppName("修改APP");
                List<App> update = DBManageHelper.updateColumn(app3, "APPNAME", App.class);
                if (null == update || update.size() <= 0) {
                    showToast("修改失败！");
                    return;
                }
                adapter.notifyDataChanged(update, true);
                break;

        }
    }
}
