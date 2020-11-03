package com.haocang.repair.manage.iview;

import android.content.Context;

import com.haocang.base.bean.ScreeEntity;

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
 * 创建时间：2018/5/39:48
 * 修 改 者：
 * 修改时间：
 */
public interface RepairFilterView {
    /**
     * 获取上下文参数.
     *
     * @return
     */
    Context getContexts();

    /**
     * 设置时间.
     *
     * @param showTime 显示时间
     */
    void setShowTime(String showTime);

    /**
     * 设置状态.
     *
     * @param list 状态列表.
     */
    void setFilterState(List<ScreeEntity> list);

    /**
     * 设置创建人.
     *
     * @param list 创建人.
     */
    void setFilterCreatePerson(List<ScreeEntity> list);

    /**
     * 设置处理人.
     *
     * @param list 处理人.
     */
    void setFilterExcutePerson(List<ScreeEntity> list);

    /**
     * 获取状态列表.
     *
     * @return
     */
    String getStateIds();

    /**
     * 获取创建人.
     *
     * @return
     */
    String getCreatePersonIds();

    /**
     * 获取处理人.
     *
     * @return
     */
    String getExcutePersonIds();

    /**
     * @param selectDateStr 选中时间
     */
    void setSelectDateStr(String selectDateStr);

    /**
     * 获取选中人的ID.
     *
     * @return
     */
    String getSelectCreatePersonIds();

    /**
     * 获取选中状态ID.
     *
     * @return
     */
    String getSelectStateIds();

    /**
     * 获取处理人.
     *
     * @return
     */
    String getSelectProcessPersonIds();
}
