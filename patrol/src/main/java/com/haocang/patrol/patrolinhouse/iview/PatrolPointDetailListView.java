package com.haocang.patrol.patrolinhouse.iview;

import android.content.Context;

import com.haocang.patrol.patrolinhouse.bean.PatrolTaskPointStep;

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
 * 创建时间：2018/4/9下午4:05
 * 修  改  者：
 * 修改时间：
 */
public interface PatrolPointDetailListView {

    /**
     * 渲染列表数据.
     *
     * @param list 列表数据
     */
    void renderList(List<PatrolTaskPointStep> list);

    /**
     * 任务步骤是否全部巡检完.
     *
     * @return
     */
    boolean isTaskComplete();

    /**
     * 获取上下文参数
     *
     * @return
     */
    Context getContexts();

    /**
     * 获取任务ID
     *
     * @return
     */
    Integer getTaskId();

    /**
     * 获取巡检点ID
     *
     * @return
     */
    Integer getPointId();

    /**
     * 获取ID
     *
     * @return
     */
    Integer getPatrolPointId();

    /**
     * 获取有巡检结果的步骤数
     *
     * @return
     */
    Integer getHasResultCount();

    /**
     * 获取异常数量
     *
     * @return
     */
    Integer getFaultCount();

    /**
     * 获取新的异常数
     *
     * @return
     */
    Integer getNewFaultCount();

    /**
     * 获取填写的巡检步骤结果
     *
     * @return
     */
    List<PatrolTaskPointStep> getPatrolPointSteps();

    /**
     * 获取上一次提交时间
     *
     * @return
     */
    String getLastResultUpdateTime();

    /**
     * 当前巡检点是否已全部提交
     *
     * @return
     */
    boolean isPointCompleteAll();

    /**
     * @return
     */
    boolean isMapPatrol();

    /**
     * 获取任务名称
     *
     * @return
     */
    String getTaskName();

    /**
     * 获取搜索框的值
     *
     * @return
     */
    String getQueryName();

    /**
     * @param position 位置.
     */
    void postFault(int position);

    /**
     * 提交成功
     */
    void submitSuccess();

    /**
     * 提交失败
     */
    void submitFail(String error);


    void setClickable(boolean flg);

    void exemptionSubmitSuccess();
}
