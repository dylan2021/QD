package com.haocang.patrol.manage.presenter;

import com.haocang.patrol.manage.iview.AllocateView;

/**
 * Created by william on 2018/4/3.
 */

public interface PatrolAllocatePresenter {
    /**
     * @param allocateView
     */
    void setAllocateView(AllocateView allocateView);

    /**
     * 获取执行人列表
     */
    void getAllocatorList();

    /**
     * 分配任务
     */
    void allocatePatrolTask();
}
