package com.haocang.repair.progress.model.impl;

import android.content.Context;

import com.haocang.base.utils.GetListListener;
import com.haocang.repair.manage.bean.RepairRecordVo;
import com.haocang.repair.progress.bean.AppRepairRecordDetailVo;
import com.haocang.repair.progress.model.RepairProgressModel;

import java.util.Map;

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
 * 创建时间：2018/5/14上午9:14
 * 修  改  者：
 * 修改时间：
 */
public class RepairProgressModelImpl implements RepairProgressModel {
    /**
     * 上下文参数
     */
    private Context mContext;
//    private

    /**
     * @param context 上下文参数.
     * @return
     */
    @Override
    public RepairProgressModel setContext(final Context context) {
        mContext = context;
        return this;
    }

    @Override
    public RepairProgressModel setMap(final Map<String, Object> map) {
        return null;
    }

    @Override
    public RepairProgressModel setGetListener(final GetListListener<AppRepairRecordDetailVo> listener) {
        return null;
    }

    @Override
    public RepairProgressModel getProgressList() {
        return null;
    }
}
