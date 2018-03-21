package pro.base.com.baseproject.demo6.listener;

import com.arialyy.aria.core.download.DownloadTarget;

import pro.base.com.baseproject.demo6.bean.AppSimpleView;

/**
 * Created by DN on 2018/3/10.
 */

public interface DownloadOnClickListener {
    void downClick(int position,AppSimpleView app,DownloadTarget target);
}
