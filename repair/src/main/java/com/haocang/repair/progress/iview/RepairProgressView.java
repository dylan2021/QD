package com.haocang.repair.progress.iview;

import android.content.Context;

import com.haocang.repair.progress.bean.ProcessingProgressVo;

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
 * 创建时间：2018/5/14上午9:17
 * 修  改  者：
 * 修改时间：
 */
public interface RepairProgressView {
    /**
     * 获取上下文参数.
     *
     * @return 上下文参数
     */
    Context getContext();

    /**
     * 渲染界面数据.
     *
     * @param processingProgressVo 列表数据
     */
    void renderData(ProcessingProgressVo processingProgressVo);

    /**
     * 获取维修单ID.
     *
     * @return 维修单ID
     */
    String getRepairId();
}
