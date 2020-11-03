package com.haocang.curve.main.iview;

import android.content.Context;


import com.haocang.curve.more.bean.PointEntity;

import org.json.JSONArray;

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
 * 创建时间：2018/6/1下午3:17
 * 修  改  者：
 * 修改时间：
 */
public interface CurveView {

    /**
     * 获取上下文参数.
     */
    Context getContext();

    /**
     * 设置选择的时间.
     *
     * @param dayStr 选择的时间
     */
    void setSelectTime(String dayStr);

    /**
     * 设置周期.
     *
     * @param cycle 周期
     */
    void setCycle(String cycle);

    /**
     * 获取选择的测点id列表.
     *
     * @return
     */
    String getSelectPoints();

    /**
     * 跳转到更多
     */
    void toMore();

    /**
     * 跳转到分享
     */
    void toShare();

    /**
     * 设置曲线.
     *
     * @param entity
     * @param datasStr
     */
    void setData(JSONArray entity, String datasStr);

    /**
     * @return
     */
    List<PointEntity> getSelectPointList();

    /**
     * @param list
     */
    void setSelectedPoints(List<PointEntity> list);

    /**
     * 无数据.
     */
    void setNoData();

    void setCombineName(String combineName);

    /**
     * @return
     */
    String getCombineName();


    void startTaggin();

    void refreshCurve();


}
