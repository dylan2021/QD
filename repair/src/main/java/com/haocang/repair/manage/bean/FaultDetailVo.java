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
 * 创建时间：2018/5/7下午6:15
 * 修  改  者：
 * 修改时间：
 */
public class FaultDetailVo {


    /**
     * 缺陷id
     */
    private Integer id;
    /**
     * 缺陷类型编号
     */
    private Integer faultTypeId;
    /**
     * 缺陷类型名称
     */
    private String faultTypeName;
    /**
     * 严重程度（1：严重，2：一般，3：轻微）
     */
    private Integer severityType;
    /**
     * 严重程度（严重，一般，轻微）
     */
    private String severityTypeName;
    /**
     * 组织机构id
     */
    private Integer orgId;
    /**
     * 组织机构名称
     */
    private String orgName;
    /**
     * 处理人id
     */
    private Integer processingPersonId;
    /**
     * 处理人名称
     */
    private String processingPersonName;
    /**
     * 工艺位置id
     */
    private Integer processId;
    /**
     * 工艺位置
     */
    private String processName;
    /**
     * 设备id
     */
    private Integer equId;
    /**
     * 设备名称
     */
    private String equName;
    /**
     * 状态（-1：删除；0：待分派；1：处理中；2：挂起；3:关闭；4：完成）
     */
    private Integer state;
    /**
     * 状态（删除；待分派；处理中；挂起；关闭；完成）
     */
    private String stateName;
    /**
     * 缺陷编号
     */
    private String faultNumber;
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
    private String createUser;
    /**
     * 备注
     */
    private String remark;


    /**
     * 相关巡检
     */
    private String patrolName;
    /**
     * 图片url
     */
    private String[] imgUrl;

    /**
     * 申报时间
     */
    private String createDate;


    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return the faultTypeId
     */
    public Integer getFaultTypeId() {
        return faultTypeId;
    }

    /**
     * @return the faultTypeName
     */
    public String getFaultTypeName() {
        return faultTypeName;
    }

    /**
     * @return the severityType
     */
    public Integer getSeverityType() {
        return severityType;
    }

    /**
     * @return the severityTypeName
     */
    public String getSeverityTypeName() {
        return severityTypeName;
    }

    /**
     * @return the orgId
     */
    public Integer getOrgId() {
        return orgId;
    }

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @return the processingPersonId
     */
    public Integer getProcessingPersonId() {
        return processingPersonId;
    }

    /**
     * @return the processingPersonName
     */
    public String getProcessingPersonName() {
        return processingPersonName;
    }

    /**
     * @return the processId
     */
    public Integer getProcessId() {
        return processId;
    }

    /**
     * @return the processName
     */
    public String getProcessName() {
        return processName;
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
     * @return the faultNumber
     */
    public String getFaultNumber() {
        return faultNumber;
    }

    /**
     * @return the tenantId
     */
    public Integer getTenantId() {
        return tenantId;
    }

    /**
     * @return the createUserId
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * @return the createUser
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * @param faultTypeId the faultTypeId to set
     */
    public void setFaultTypeId(final Integer faultTypeId) {
        this.faultTypeId = faultTypeId;
    }

    /**
     * @param faultTypeName the faultTypeName to set
     */
    public void setFaultTypeName(final String faultTypeName) {
        this.faultTypeName = faultTypeName;
    }

    /**
     * @param severityType the severityType to set
     */
    public void setSeverityType(final Integer severityType) {
        this.severityType = severityType;
    }

    /**
     * @param severityTypeName the severityTypeName to set
     */
    public void setSeverityTypeName(final String severityTypeName) {
        this.severityTypeName = severityTypeName;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Integer orgId) {
        this.orgId = orgId;
    }

    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(final String orgName) {
        this.orgName = orgName;
    }

    /**
     * @param processingPersonId the processingPersonId to set
     */
    public void setProcessingPersonId(final Integer processingPersonId) {
        this.processingPersonId = processingPersonId;
    }

    /**
     * @param processingPersonName the processingPersonName to set
     */
    public void setProcessingPersonName(final String processingPersonName) {
        this.processingPersonName = processingPersonName;
    }

    /**
     * @return the imgUrl
     */
    public String[] getImgUrl() {
        return imgUrl;
    }

    /**
     * @param imgUrl the imgUrl to set
     */
    public void setImgUrl(final String[] imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * @param processId the processId to set
     */
    public void setProcessId(final Integer processId) {
        this.processId = processId;
    }

    /**
     * @param processName the processName to set
     */
    public void setProcessName(final String processName) {
        this.processName = processName;
    }

    /**
     * @param equId the equId to set
     */
    public void setEquId(final Integer equId) {
        this.equId = equId;
    }

    /**
     * @param equName the equName to set
     */
    public void setEquName(final String equName) {
        this.equName = equName;
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
     * @param faultNumber the faultNumber to set
     */
    public void setFaultNumber(String faultNumber) {
        this.faultNumber = faultNumber;
    }

    /**
     * @param tenantId the tenantId to set
     */
    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * @param createUserId the createUserId to set
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * @param createUser the createUser to set
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPatrolName() {
        return patrolName;
    }

    public void setPatrolName(String patrolName) {
        this.patrolName = patrolName;
    }
}
