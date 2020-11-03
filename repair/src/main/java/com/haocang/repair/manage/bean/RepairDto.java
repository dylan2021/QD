package com.haocang.repair.manage.bean;

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
 * 创建时间：2018/5/7下午4:58
 * 修  改  者：
 * 修改时间：
 */

import java.util.List;

/**
 *
 */

public class RepairDto {
    /**
     * 维修详情头部
     */
    private RepairVo repairVo;
    /**
     * 缺陷详情
     */
    private FaultDetailVo faultDetailVo;
    /**
     * 维修记录
     */
    private List<RepairRecordVo> repairRecordVos;


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RepairDto [repairVo=" + repairVo + ", faultDetailVo=" + faultDetailVo + ", repairRecordVos="
                + repairRecordVos + "]";
    }



    /**
     * @return the repair
     */
    public RepairVo getRepairVo() {
        return repairVo;
    }

    /**
     * @return the faultDetail
     */
    public FaultDetailVo getFaultDetailVo() {
        return faultDetailVo;
    }

    /**
     * @return the repairRecords
     */
    public List<RepairRecordVo> getRepairRecordVos() {
        return repairRecordVos;
    }

    /**
     * @param repairVo 维修详情.
     */
    public void setRepair(final RepairVo repairVo) {
        this.repairVo = repairVo;
    }

    /**
     * @param faultDetailVo
     */
    public void setFaultDetail(final FaultDetailVo faultDetailVo) {
        this.faultDetailVo = faultDetailVo;
    }

    /**
     * @param repairRecordVos
     */
    public void setRepairRecords(final List<RepairRecordVo> repairRecordVos) {
        this.repairRecordVos = repairRecordVos;
    }

}
