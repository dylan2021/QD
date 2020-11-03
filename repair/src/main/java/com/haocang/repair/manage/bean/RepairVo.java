package com.haocang.repair.manage.bean;


import com.haocang.base.config.AppApplication;

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
 * 创建时间：2018/4/2613:57
 * 修 改 者：
 * 修改时间：
 */
public class RepairVo {

    /**
     * 状态已完成
     */
    public static final int STATE_COMPLETE = 4;
    /**
     * 状态已关闭
     */
    public static final int STATE_CLOSE = 3;
    // id没有生成,暂时手写
    /**
     * ID, 修改时为必填
     */
    private Integer id;
    /**
     * 缺陷id
     */
    private Long faultId;
    /**
     * 设备id
     */
    private Integer equId;
    /**
     * 设备名称
     */
    private String equName;
    /**
     *
     */
    private Integer lastRecordId;

    public String getFaultReasonName() {
        return faultReasonName;
    }

    public void setFaultReasonName(String faultReasonName) {
        this.faultReasonName = faultReasonName;
    }

    private String faultReasonName;
    /**
     * 处理人id
     */
    private Integer processingPersonId;
    /**
     * 处理人名称
     */
    private String processingPersonName;
    /**
     * 最后处理时间.
     */
    private String processingDate;

    /**
     * 设备编号.
     */
    private String equCode;
    /**
     * 状态（-1：删除；0：未处理；1：执行中；2：挂起；3:关闭；4：完成）
     */
    private Integer state;
    /**
     * 状态（删除；未处理；执行中；挂起；关闭；完成）
     */
    private String stateName;
    /**
     * 维修编号
     */
    private String repairNumber;
    /**
     * 所属组织
     */
    private Integer orgId;
    /**
     * 租户ID
     */
    private Integer tenantId;
    /**
     * 创建人ID
     */
    private Integer createUserId;
    /**
     * 创建人
     */
    private String createUserName;
    /**
     * 创建时间
     */
    private String createDate;

    private String remark;

    private String updateDate;

    private Boolean concerned;

    /**
     * 维修类型
     */
    private String repairTypeName;

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }

    private String assignDate;

    public String getProcessName() {
        return processName != null ? processName : "";
    }

    public void setProcessName(final String processName) {
        this.processName = processName;
    }

    /**
     * 工艺名称
     */
    private String processName;

    public String getSeverityTypeName() {
        return severityTypeName;
    }

    public void setSeverityTypeName(String severityTypeName) {
        this.severityTypeName = severityTypeName;
    }

    private String severityTypeName;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(final String orgName) {
        this.orgName = orgName;
    }

    /**
     * 创建人所在组织
     */

    private String orgName;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the faultId
     */
    public Long getFaultId() {
        return faultId;
    }

    /**
     * @return the equId
     */
    public Integer getEquId() {
        return equId;
    }

    /**
     * @return the equName
     */
    public String getEquName() {
        return equName;
    }

    /**
     * @return the processingPersonId
     */
    public Integer getProcessingPersonId() {
        return processingPersonId == null ? -1 : processingPersonId;
    }

    public String getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(final String processingDate) {
        this.processingDate = processingDate;
    }

    /**
     * @return the processingPersonName
     */
    public String getProcessingPersonName() {
        return processingPersonName == null ? "" : processingPersonName;
    }

    /**
     * @return the state
     */
    public Integer getState() {
        return state;
    }

    /**
     * @return the stateName
     */
    public String getStateName() {
        return stateName;
    }

    /**
     * @return the orgId
     */
    public Integer getOrgId() {
        return orgId;
    }

    /**
     * @return the tenantId
     */
    public Integer getTenantId() {
        return tenantId;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @param faultId the faultId to set
     */
    public void setFaultId(Long faultId) {
        this.faultId = faultId;
    }

    /**
     * @param equId the equId to set
     */
    public void setEquId(Integer equId) {
        this.equId = equId;
    }

    /**
     * @param equName the equName to set
     */
    public void setEquName(String equName) {
        this.equName = equName;
    }

    /**
     * @param processingPersonId the processingPersonId to set
     */
    public void setProcessingPersonId(Integer processingPersonId) {
        this.processingPersonId = processingPersonId;
    }

    /**
     * @param processingPersonName the processingPersonName to set
     */
    public void setProcessingPersonName(String processingPersonName) {
        this.processingPersonName = processingPersonName;
    }

    /**
     * @param state the state to set
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * @param stateName the stateName to set
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     * @return the repairNumber
     */
    public String getRepairNumber() {
        return repairNumber;
    }

    /**
     * @param repairNumber the repairNumber to set
     */
    public void setRepairNumber(String repairNumber) {
        this.repairNumber = repairNumber;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    /**
     * @param tenantId the tenantId to set
     */
    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * @return the createUserId
     */
    public Integer getCreateUserId() {
        return createUserId == null ? -1 : createUserId;
    }

    /**
     * @return the createUserName
     */
    public String getCreateUserName() {
        return createUserName;
    }

    /**
     * @param createUserId the createUserId to set
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * @param createUserName the createUserName to set
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RepairVo [id=" + id + ", faultId=" + faultId + ", equId=" + equId + ", equName=" + equName
                + ", processingPersonId=" + processingPersonId + ", processingPersonName=" + processingPersonName
                + ", processingDate=" + processingDate + ", state=" + state + ", stateName=" + stateName
                + ", repairNumber=" + repairNumber + ", orgId=" + orgId + ", tenantId=" + tenantId + ", createUserId="
                + createUserId + ", createUserName=" + createUserName + ", createDate=" + createDate + "]";
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 是否可维修
     *
     * @return
     */
    public boolean canRepair() {
        boolean canRepair = false;
        /**
         * 只有状态是待处理，处理中，挂起，并且处理人是当前人，才可以进行维修
         */
        if ((RepairConstans.STATE_UNPROCESS == state
                || RepairConstans.STATE_HANGUP == state
                || RepairConstans.STATE_PROCESSING == state)
                && (processingPersonId != null
                && processingPersonId == AppApplication.getInstance().getUserEntity().getId())) {
            canRepair = true;
        }
        return canRepair;
    }

    public boolean canExcute() {
        /**
         * 如果是已关闭或者已完成的工单，不可执行.
         */
        if (STATE_CLOSE == getState() || STATE_COMPLETE == getState()) {
            return false;
        }
        boolean flag = true;
        /**
         * 如果执行人不是当前人的，不可执行
         */
        if (AppApplication.getInstance().getUserEntity().getId()
                != getProcessingPersonId()) {
            flag = false;
        }
        /**
         * 执行人为空，创建人等于当前人 可以显示
         */
        if (getProcessingPersonId() <= 0 && AppApplication.getInstance().getUserEntity().getId() != getCreateUserId()) {
            flag = true;
        }
        return flag;
    }

    public String getEquCode() {
        return equCode == null ? "" : equCode;
    }

    public void setEquCode(String equCode) {
        this.equCode = equCode;
    }

    public Integer getLastRecordId() {
        return lastRecordId;
    }

    public void setLastRecordId(Integer lastRecordId) {
        this.lastRecordId = lastRecordId;
    }

    public String getRepairTypeName() {
        return repairTypeName;
    }

    public void setRepairTypeName(String repairTypeName) {
        this.repairTypeName = repairTypeName;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Boolean getConcerned() {
        return concerned == null ? false : concerned;
    }

    public void setConcerned(final Boolean concerned) {
        this.concerned = concerned;
    }
}
