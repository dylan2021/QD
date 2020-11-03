package com.haocang.patrol.patroloutside.iview;

import android.content.Context;

import com.haocang.patrol.patrolinhouse.bean.PatrolPointDetailDTO;

import java.util.List;

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
 * 创建时间：2018/4/19上午11:09
 * 修  改  者：
 * 修改时间：
 */
public interface PatrolOutdiseView {


    /**
     * 获取巡检点列表
     *
     * @return
     */
    List<PatrolPointDetailDTO> getPatrolPointList();

    /**
     * 获取上下文参数
     *
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
     * 获取任务名称
     *
     * @return
     */
    String getTaskName();

    /**
     * 达到巡检点
     *
     * @param arrivedPoint
     */
    void arrive(PatrolPointDetailDTO arrivedPoint);

    /**
     * 离线模式达到巡检点
     *
     * @param arrivedPoint
     */
    void offLineArrive(PatrolPointDetailDTO arrivedPoint);

    /**
     * 刷新巡检详情
     */
    void getPatrolDetailData();

    /**
     * 获取巡检结束时间
     *
     * @return
     */
    String getPatroEndTime();


    /**
     * 到达巡检点附近震动
     */
    void showVibrator();
}
