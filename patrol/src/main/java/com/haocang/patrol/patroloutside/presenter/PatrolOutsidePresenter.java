package com.haocang.patrol.patroloutside.presenter;

import com.haocang.patrol.patrolinhouse.bean.PatrolPointDetailDTO;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskPointDTO;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题：
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/4/19上午11:03
 * 修  改  者：
 * 修改时间：
 */
public interface PatrolOutsidePresenter {

    /**
     * 获取达到附近,并且未巡检的巡检点
     *
     * @return
     */
    PatrolPointDetailDTO getArrivePatrolPoint();

    /**
     * 现在是否在巡检点附近
     *
     * @param patrolPointDetailDTO 巡检点
     */
    boolean isNearPoint(PatrolTaskPointDTO patrolPointDetailDTO);

    /**
     * 此巡检点外的其他巡检点的巡检步骤是否都已完成
     *
     * @param patrolPointId 巡检点ID
     * @return
     */
    boolean isTaskCompleteOthers(int patrolPointId);

    /**
     * 离线模式此巡检点外的其他巡检点的巡检步骤是否都已完成
     * id不一致
     *
     * @param patrolPointId 巡检点ID
     * @return
     */
    boolean offLineIsTaskCompleteOthers(int patrolPointId);

    /**
     * 轨迹Service是否正常运行
     *
     * @return
     */
    boolean isServiceRunning();

    /**
     * 根据巡检任务状态获取显示名称
     *
     * @param excuteStatus 执行状态
     * @return
     */
    String getShowNameByStatus(String excuteStatus);

    /**
     * 地图标记点击事件
     *
     * @param title 点击的标记的title，设置为巡检点的ID
     */
    PatrolPointDetailDTO onMarkerClick(String title);

    /**
     * 开启检测是否到巡检点附近
     */
    void startCheckArrivedPoint();

    /**
     * 停止检查是否在巡检点附近
     */
    void stopCheckArrivedPoint();
}
