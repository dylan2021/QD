package com.haocang.repair.manage.iview;

import android.app.Activity;
import android.content.Context;

import com.haocang.repair.manage.bean.RepairVo;

import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/5/217:20
 * 修 改 者：
 * 修改时间：
 */
public interface RepairManageView {

    /**
     * @return 获取上下文参数
     */
    Context getContexts();

    /**
     * @return
     */
    String queryName();

    /**
     * @return
     */
    Integer getCurrentPage();

    /**
     * @param list 维修列表.
     */
    void renderList(List<RepairVo> list);

    /**
     * @return 返回所在的Activity.
     */
    Activity getActivity();

    /**
     * @return 状态的ID集合.
     */
    String getStateIds();

//    /**
//     * @return 返回当前时间.
//     */
//    String getCurDateStr();

    /**
     * 获取创建人选项上次结果.
     *
     * @return
     */
    String getCreatePersonIds();

    /**
     * 获取处理人选项上次结果.
     *
     * @return
     */
    String getExcutePersonIds();

//    /**
//     * 获取开始时间.
//     *
//     * @return 开始时间
//     */
//    String getStartTime();
//
//    /**
//     * 获取结束时间.
//     *
//     * @return 结束时间
//     */
//    String getEndTime();

//    /**
//     * 设置开始时间.
//     *
//     * @param startTime 开始时间
//     */
//    void setStartTime(String startTime);
//
//    /**
//     * 设置结束时间.
//     *
//     * @param endTime 结束时间
//     */
//    void setEndTime(String endTime);

    /**
     * @return
     */
    String getSelectDateStr();

    /**
     * 设置选中时间.
     *
     * @param selectDateStr 选中时间
     */
    void setSelectDateStr(String selectDateStr);

    /**
     * @param list
     */
    void renderChangedItem(List<RepairVo> list);

    /**
     *
     */
    void updateChangedItem();

    /**
     * 获取开始时间。
     *
     * @return
     */
    String getStartTime();

    /**
     * 获取结束时间
     *
     * @return
     */
    String getEndTime();

    /**
     * 设置开始时间.
     *
     * @param localTimeStr
     */
    void setStartTime(String localTimeStr);

    /**
     * 设置结束时间.
     *
     * @param localTimeStr
     */
    void setEndTime(String localTimeStr);

    /**
     * @return
     */
    String getProcessingPersonIds();

    /**
     * 获取设备ID.
     *
     * @return
     */
    Integer getEquId();

    /**
     * 获取工艺位置ID
     *
     * @return
     */
    Integer getProcessId();

    /**
     * 设置默认状态.
     *
     * @param s
     */
    void setStates(String s);
}
