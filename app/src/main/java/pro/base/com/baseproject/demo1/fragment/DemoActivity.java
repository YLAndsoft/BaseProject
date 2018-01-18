package pro.base.com.baseproject.demo1.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fyl.base.MenuFragmentActivity;
import pro.base.com.baseproject.R;

/**
 * Created by DN on 2018/1/8.
 */

public class DemoActivity extends MenuFragmentActivity {

    private LinearLayout ll_tab_table;
    private ImageView[] imgBtn;
    private TextView[] txtBtn;
    private int[] tabResIds = { R.id.rl_menu_0, R.id.rl_menu_1,R.id.rl_menu_2, R.id.rl_menu_3 };

    private int flResId = R.id.fl_menu_container;
    private int[] imageNormals = {R.mipmap.ic_home_actionbar0,
            R.mipmap.ic_home_actionbar1,
            R.mipmap.ic_home_actionbar2,
            R.mipmap.ic_home_actionbar3};
    private int[] imgsHovers = {
            R.mipmap.ic_home_select0,
            R.mipmap.ic_home_select1,
            R.mipmap.ic_home_select2,
            R.mipmap.ic_home_select3};
    private SYFragment syFragment;
    private XJFragment xjFragment;
    private BDFragment bdFragment;
    private MeFragment meFragment;

    @Override
    public void initParms(Bundle parms) {
        setAllowFullScreen(true);
        setScreenRoate(false);
        setSteepStatusBar(false);
        setSetActionBarColor(true);
    }

    @Override
    public View bindView() {
        //View view = LayoutInflater.from(mContext).inflate(R.layout.demo_activity, null);
        //return view;
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.demo_activity;
    }

    @Override
    public void initView(View view) {
        ll_tab_table = $(R.id.main_menu_table);
        int[] imgIds = { R.id.iv_menu_0, R.id.iv_menu_1,R.id.iv_menu_2, R.id.iv_menu_3};
        int[] txtIds = { R.id.tv_menu_0, R.id.tv_menu_1,R.id.tv_menu_2, R.id.tv_menu_3};
        imgBtn = new ImageView[imgIds.length];
        txtBtn = new TextView[txtIds.length];
        for (int i = 0; i < imgIds.length; i++) {
            imgBtn[i] = findViewById(imgIds[i]);
            txtBtn[i] =findViewById(txtIds[i]);
        }
        super.initTab(tabResIds);
        //首次展现加载的fragment
        if (syFragment == null) {
            syFragment = new SYFragment();
        }
        switchFragment(flResId, syFragment);
    }
    @Override
    public void setListener() {
    }
    @Override
    public void doBusiness(Context mContext) {
    }
    @Override
    public void widgetClick(View v) {
    }
    @Override
    protected boolean onTabClick(int tabId) {
        //切换页面前，初始化控件颜色
        for (int i = 0; i < txtBtn.length; i++) {
            imgBtn[i].setImageResource(imageNormals[i]);
            txtBtn[i].setTextColor(Color.parseColor("#666666"));
        }
        super.onTabClick(tabId);
        switch (tabId) {
            case R.id.rl_menu_0:
                imgBtn[0].setImageResource(imgsHovers[0]);
                txtBtn[0].setTextColor(getResources().getColor(R.color.maintab_topbar_bg_color));
                ll_tab_table.setBackgroundResource(R.color.white);
                if (syFragment == null) {
                    syFragment = new SYFragment();
                }
                switchFragment(flResId, syFragment);
                break;

            case R.id.rl_menu_1:
                imgBtn[1].setImageResource(imgsHovers[1]);
                txtBtn[1].setTextColor(getResources().getColor(R.color.maintab_topbar_bg_color));
                ll_tab_table.setBackgroundResource(R.color.white);
                if (xjFragment == null) {
                    xjFragment = new XJFragment();
                }
                switchFragment(flResId, xjFragment);
                break;
            case R.id.rl_menu_2:
                imgBtn[2].setImageResource(imgsHovers[2]);
                txtBtn[2].setTextColor(getResources().getColor(R.color.maintab_topbar_bg_color));
                ll_tab_table.setBackgroundResource(R.color.white);
                if (bdFragment == null) {
                    bdFragment = new BDFragment();
                }
                switchFragment(flResId, bdFragment);
                break;

            case R.id.rl_menu_3:
                imgBtn[3].setImageResource(imgsHovers[3]);
                txtBtn[3].setTextColor(getResources().getColor(R.color.maintab_topbar_bg_color));
                ll_tab_table.setBackgroundResource(R.color.white);
                if (meFragment == null) {
                    meFragment = new MeFragment();
                }
                switchFragment(flResId, meFragment);
                break;
        }
        return true;

    }

    private long mkeyTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                mkeyTime = System.currentTimeMillis();
                showToast("再按一次退出程序");
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
