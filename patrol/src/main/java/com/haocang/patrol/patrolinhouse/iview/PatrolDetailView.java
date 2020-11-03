package com.haocang.patrol.patrolinhouse.iview;

import android.content.Context;

import com.haocang.patrol.patrolinhouse.bean.PatrolTaskDetailDTO;

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
 * 创建时间：2018/4/13下午6:21
 * 修  改  者：
 * 修改时间：
 */
public interface PatrolDetailView {
    /**
     * 渲染数据
     *
     * @param patrolTaskDetailDTO
     */
    void renderData(PatrolTaskDetailDTO patrolTaskDetailDTO);

    /**
     * @return
     */
    Context getContext();

    /**
     * 获取任务ID
     *
     * @return
     */
    Integer getTaskId();

    /**
     * 获取扫描后的二维码
     *
     * @return
     */
    String getQRCode();

//    /**
//     * 扫码成功
//     *
//     * @param dto 巡检点
//     */
//    void scanSuccess(PatrolTaskPointDTO dto);

    /**
     * 是否是地图巡检
     *
     * @return
     */
    boolean isFromMap();

    /**
     * 当前巡检点外，其他巡检点步骤是否巡检完
     *
     * @param patrolPointId 巡检点ID
     * @return
     */
    boolean isTaskCompleteOthers(Integer patrolPointId);

    /**
     * 离线模式当前巡检点外，其他巡检点步骤是否巡检完，id不一致
     *
     * @param patrolPointId 巡检点ID
     * @return
     */
    boolean offLineIsTaskCompleteOthers(Integer patrolPointId);

    /**
     * 获取巡检任务名称
     *
     * @return
     */
    String getTaskName();

    /**
     * 是否有巡检权限
     *
     * @return
     */
    boolean hasPatrolAuth();

    /**
     * 获取任务结束时间
     *
     * @return
     */
    String getTaskEndTime();

    String getProcessName(int pointId);
}
