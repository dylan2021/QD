package com.haocang.patrol.patrolinhouse.presenter;

import com.haocang.patrol.patrolinhouse.bean.PatrolPointDetailDTO;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskPointDTO;
import com.haocang.patrol.patrolinhouse.iview.PatrolDetailView;

/**
 * Created by william on 2018/4/4.
 */

public interface PatrolPointListPresenter {
    /**
     * 获取巡检详情数据
     */
    void getPatrolDetailData();

    /**
     * 设置巡检View
     *
     * @param patrolDetailView 巡检View接口
     */
    void setPatrolDetailView(PatrolDetailView patrolDetailView);

    /**
     * 扫码
     */
    void getScanCode();

    /**
     * 是否靠近巡检点
     *
     * @param patrolPointDetailDTO 巡检点
     * @return
     */
    boolean isNearPoint(PatrolTaskPointDTO patrolPointDetailDTO);

    /**
     * 巡检点点击事件
     *
     * @param dto 巡检点
     */
    void onItemClick(PatrolPointDetailDTO dto);

    /**
     * 上传巡检轨迹
     */
    void sendTrace();

}
