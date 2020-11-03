/**
 * Copyright &copy; 2017-2027 hcsystem All rights reserved.
 */
package com.haocang.patrol.patrolinhouse.bean;

import java.util.Date;
import java.util.List;

/**
 * .
 * 巡检任务和巡检点关联Entity
 *
 * @author zhangfang
 * @version 2018-03-22
 */
public class PatrolTaskPointDTO {
    private static final long serialVersionUID = 1L;

    private Integer id;

    //	@ApiModelProperty(value = "巡检任务id")
    private Integer patrolTaskId;

    //	@ApiModelProperty(value = "巡检点id")
    private Integer patrolPointId;

    //	@ApiModelProperty(value = "巡检点名称")
    // @NotBlank(message = "patrolTaskPoint_patrolPointNameNotBlank")
    private String patrolPointName;

    //	@ApiModelProperty(value = "巡检点描述")
    // @NotBlank(message = "patrolTaskPoint_patrolPointDescriptionNotBlank")
    private String patrolPointDescription;

    //	@ApiModelProperty(value = "相关工艺位置ids")
    // @NotBlank(message = "patrolTaskPoint_patrolTaskIdNotBlank")
    private String relatedProcessIds;

    //	@ApiModelProperty(value = "经度")
    // @NotNull(message = "patrolTaskPoint_longitudeNotNull")
    private double longitude;

    //	@ApiModelProperty(value = "纬度")
    // @NotNull(message = "patrolTaskPoint_latitudeNotNull")
    private double latitude;

    //	@ApiModelProperty(value = "巡检步骤数")
    // @NotNull(message="patrolTaskPoint_stepCountNotNull")
    private Integer patrolStepCount;

    //	@ApiModelProperty(value = "已有结果的巡检步骤数")
//	@NotNull(message = "patrolTaskPoint_faultCountNotNull")
    private Integer faultCount;

    //	@ApiModelProperty(value = "需要记录结果的巡检步骤数")
    private Integer patrolHasResultCount;

    //	@ApiModelProperty(value = "巡检点提交/更新时间")
    private Date resultUpdateTime;

    List<PatrolTaskPointStep> patrolTaskPointSteps;

    public List<PatrolTaskPointStep> getPatrolTaskPointSteps() {
        return patrolTaskPointSteps;
    }

    public void setPatrolTaskPointSteps(List<PatrolTaskPointStep> patrolTaskPointSteps) {
        this.patrolTaskPointSteps = patrolTaskPointSteps;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatrolTaskId() {
        return patrolTaskId;
    }

    public void setPatrolTaskId(Integer patrolTaskId) {
        this.patrolTaskId = patrolTaskId;
    }

    public Integer getPatrolPointId() {
        return patrolPointId;
    }

    public void setPatrolPointId(Integer patrolPointId) {
        this.patrolPointId = patrolPointId;
    }

    public String getPatrolPointName() {
        return patrolPointName;
    }

    public void setPatrolPointName(String patrolPointName) {
        this.patrolPointName = patrolPointName;
    }

    public String getPatrolPointDescription() {
        return patrolPointDescription;
    }

    public void setPatrolPointDescription(String patrolPointDescription) {
        this.patrolPointDescription = patrolPointDescription;
    }

    public String getRelatedProcessIds() {
        return relatedProcessIds;
    }

    public void setRelatedProcessIds(String relatedProcessIds) {
        this.relatedProcessIds = relatedProcessIds;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Integer getPatrolStepCount() {
        return patrolStepCount;
    }

    public void setPatrolStepCount(Integer patrolStepCount) {
        this.patrolStepCount = patrolStepCount;
    }

    public Integer getFaultCount() {
        return faultCount;
    }

    public void setFaultCount(Integer faultCount) {
        this.faultCount = faultCount;
    }

    public Integer getPatrolHasResultCount() {
        return patrolHasResultCount;
    }

    public void setPatrolHasResultCount(Integer patrolHasResultCount) {
        this.patrolHasResultCount = patrolHasResultCount;
    }

    public Date getResultUpdateTime() {
        return resultUpdateTime;
    }

    public void setResultUpdateTime(Date resultUpdateTime) {
        this.resultUpdateTime = resultUpdateTime;
    }

    /**
     * 是否已巡检
     *
     * @return
     */
    public boolean hasPatroled() {
        boolean hasPatroled = false;
        if (getResultUpdateTime() != null) {
            hasPatroled = true;
        }
        return hasPatroled;
    }
}