package com.haocang.patrol.manage.presenter;

/**
 * Created by william on 2018/4/2.
 */

public interface PatrolListPresenter {
    /**
     * 获取巡检列表.
     */
    void getPatrolList();

    /**
     * 获取本地列表
     */
    void getOffLinePatrolList();

    /**
     * 设置默认条件.
     */
    void setDefalutCondition();

    /**
     * 打开筛选页面.
     */
    void showFilterView();

    /**
     * @param changePosition 改变的位置.
     */
    void getItem(int changePosition);
}
