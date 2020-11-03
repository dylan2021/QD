package com.haocang.repair.post.iview;

import android.content.Context;

import com.haocang.repair.post.bean.RepairRecordDto;

import java.util.Date;
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
 * 创建时间：2018/5/14下午6:26
 * 修  改  者：
 * 修改时间：
 */
public interface PostRepairResultView {
    /**
     * 获取上下文参数.
     *
     * @return 上下文参数
     */
    Context getContext();

    /**
     * 设置维修结果.
     *
     * @param resultKey
     * @param resultName 结果名称.
     */
    void setRepairResult(int resultKey, String resultName);

    /**
     * @param resultKey  维修措施.
     * @param resultName 维修措施名称.
     */
    void setRepairMethod(int resultKey, String resultName);

    /**
     * 设置完成时间.
     *
     * @param date 完成时间
     */
    void setCompleteTime(String date);

//    /**
//     * 获取备注.
//     *
//     * @return 备注
//     */
//    String getRemarks();

    /**
     * @return 维修记录ID
     */
    String getRepairRecordId();

    /**
     * @param entity 维修记录
     */
    void setDetailData(RepairRecordDto entity);

    void setNoFutureTime();

    /**
     * @return
     */
    List<String> getfileList();

    /**
     * @return
     */
    String getNetWorkList();

    String getNetWordThumbnailUrl();

    Date getCreateData();
}
