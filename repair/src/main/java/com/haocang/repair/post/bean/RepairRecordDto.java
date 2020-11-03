package com.haocang.repair.post.bean;

import com.haocang.repair.manage.bean.RepairRecordVo;
import com.haocang.repair.manage.bean.RepairVo;

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
 * 创建时间：2018/5/15下午3:21
 * 修  改  者：
 * 修改时间：
 */
public class RepairRecordDto {
    /**
     *
     */
    private RepairVo repairVo;
    /**
     *
     */
    private RepairRecordVo repairRecordVo;

    public RepairVo getRepairVo() {
        return repairVo;
    }

    public void setRepairVo(RepairVo repairVo) {
        this.repairVo = repairVo;
    }

    public RepairRecordVo getRepairRecordVo() {
        return repairRecordVo;
    }

    /**
     *
     * @param repairRecordVo
     */
    public void setRepairRecordVo(RepairRecordVo repairRecordVo) {
        this.repairRecordVo = repairRecordVo;
    }
}
