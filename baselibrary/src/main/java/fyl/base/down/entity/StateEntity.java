package fyl.base.down.entity;

/**
 * 状态实体
 * Created by DN on 2018/5/30.
 */
public interface StateEntity {
    /**
     * 初始状态
     */
    int STATE_INIT = 0;
    /**
     * //下载中
     */
    int STATE_RUNING = 1;
    /**
     * //暂停
     */
    int STATE_STOP = 2;
    /**
     * //完成
     */
    int STATE_COMPLETE = 3;
    /**
     * //启动
     */
    int STATE_START = 4;
    /**
     * //错误
     */
    int STATE_ERROR = 5;
}
