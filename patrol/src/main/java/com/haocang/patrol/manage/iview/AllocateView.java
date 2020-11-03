package com.haocang.patrol.manage.iview;

import android.content.Context;

import com.haocang.patrol.manage.bean.UserDTO;

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
 * 创建时间：2018/4/13上午9:30
 * 修  改  者：
 * 修改时间：
 */
public interface AllocateView {
    /**
     * 渲染列表数据.
     *
     * @param list 分配人列表
     */
    void renderList(List<UserDTO> list);

    /**
     * 获取模糊查询关键字.
     *
     * @return 模糊查询关键字
     */
    String getQueryName();

    /**
     * 加载数据时，是否是刷新.
     *
     * @return 是否刷新
     */
    boolean isRefresh();

    /**
     * 获取当前页.
     *
     * @return 当前页
     */
    Integer getPage();

    /**
     * 获取选中执行人.
     *
     * @return 执行人ID
     */
    Integer getExecutorId();

    /**
     * 获取要分配的任务的ID.
     *
     * @return 任务ID
     */
    int getTaskId();

    /**
     * 获取任务所在组织ID.
     *
     * @return 组织ID
     */
    Integer getOrgId();

    /**
     * 获取上下文参数.
     *
     * @return 上下文参数
     */
    Context getContext();

    /**
     * @param entity 分配结果
     */
    void success(String entity);
}
