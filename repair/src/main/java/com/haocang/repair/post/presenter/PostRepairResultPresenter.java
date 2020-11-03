package com.haocang.repair.post.presenter;

import com.haocang.repair.post.iview.PostRepairResultView;

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
 * 创建时间：2018/5/14下午6:10
 * 修  改  者：
 * 修改时间：
 */
public interface PostRepairResultPresenter {
    /**
     * 设置界面数据交互接口.
     *
     * @param postRepairResultView 界面对外接口
     */
    void setView(PostRepairResultView postRepairResultView);

    /**
     * 显示维修结果选择界面.
     */
    void showPickResultView();

    /**
     * 显示维修措施选择界面.
     */
    void showPickMethodView();

    /**
     * 显示选择完成时间界面.
     */
    void showCompleteTimeView();

    /**
     * 跳转添加备注界面.
     */
    void toRemarkFragment();

    /**
     * 获取维修结果.
     */
    void getRepairResult();

    /**
     * 提交
     */
    void submit();

    /**
     * 设置备注.
     *
     * @param remark 备注
     */
    void setRemark(String remark);
}
