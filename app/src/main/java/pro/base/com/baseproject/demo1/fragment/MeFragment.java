package pro.base.com.baseproject.demo1.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import fyl.base.base.BaseFragment;
import pro.base.com.baseproject.R;
import pro.base.com.baseproject.demo5.ui.ChatInfoActivity;

/**
 * Created by DN on 2018/1/8.
 */

public class MeFragment extends BaseFragment {

    @ViewInject(value = R.id.check_chatInfo)
    TextView check_chatInfo;
    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.me_activity;
    }

    @Override
    protected void initView() {
        x.view().inject(this,mContextView);
        check_chatInfo.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()){
            case R.id.check_chatInfo:
                Intent intent = new Intent(mContext,ChatInfoActivity.class);
                startActivity(intent);
                break;
        }
    }
}
