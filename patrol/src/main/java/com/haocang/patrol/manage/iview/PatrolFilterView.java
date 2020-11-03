package com.haocang.patrol.manage.iview;

import android.content.Context;

import com.haocang.base.picker.PickerView;
import com.haocang.patrol.manage.bean.filter.FilterEntity;

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
 * 创建时间：2018/4/8下午3:56
 * 修  改  者：
 * 修改时间：
 */
public interface PatrolFilterView {
    void renderList(List<FilterEntity> list);

    void renderTaskList(List<FilterEntity> list);

    String getSelectStatusKeys();

    void setShowTime(String showTime);

    String getPersonId();

    /**
     * 获取上下文参数
     *
     * @return
     */
    Context getContext();

    PickerView getPickerview();
}
