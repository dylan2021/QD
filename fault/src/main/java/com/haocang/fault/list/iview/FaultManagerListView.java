package com.haocang.fault.list.iview;

import android.content.Context;

import com.haocang.fault.list.bean.FaultManagerEntity;

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
public interface FaultManagerListView {
    Context getContexts();

    String getStates();

    String queryName();

    String getStartDate();

    String getEndDate();

    String getFaultTypes();

    String getCreateUserIds();//创始人

    String getProcessingPersonIds();//处理人



    Integer getCurrentPage();

    void setListAllData(List<FaultManagerEntity> list);

    void dstributionSuccess();//任务分配成功


}
