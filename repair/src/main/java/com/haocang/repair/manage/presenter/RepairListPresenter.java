package com.haocang.repair.manage.presenter;

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
 * 创建时间：2018/5/314:26
 * 修 改 者：
 * 修改时间：
 */
public interface RepairListPresenter {
    /**
     * 获取维修列表.
     */
    void getRepairList();

    /**
     * 显示筛选页面.
     */
    void showFilterView();

    /**
     * 设置默认筛选条件.
     */
    void setDefaultFilterCondition();

    /**
     * 分配任务.
     *
     * @param selectItemId       要分配的维修单ID
     * @param processingPersonId 处理人ID
     */
    void taskAssign(int selectItemId, int processingPersonId);

    /**
     * @param changedItemPosition 发生变化的维修
     */
    void getChangedRepairItem(int changedItemPosition);

    /**
     * @param concernFlag 是否关注，true为关注，false为取消关注.
     * @param id          维修单ID
     */
    void concern(boolean concernFlag, Integer id);
}
