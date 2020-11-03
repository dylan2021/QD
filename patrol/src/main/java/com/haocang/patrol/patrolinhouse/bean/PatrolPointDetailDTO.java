package com.haocang.patrol.patrolinhouse.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by william on 2018/4/4.
 */

public class PatrolPointDetailDTO {
    private Integer id;

    private String patrolPoint;

    //所在组织名称
    private String orgName;

    //组织id
    private Integer orgId;

    private String description;

    private Integer stepCount;

    private Integer recordCount;

    private Integer faultCount;

    //工艺位置ids
    private String relatedProcess;

    //工艺位置名称
    private String relatedProcessNames;

    private double longitude;

    private double latitude;

    private String no;

    public int getPatrolPointId() {
        return patrolPointId;
    }

    public void setPatrolPointId(int patrolPointId) {
        this.patrolPointId = patrolPointId;
    }

    private int patrolPointId;

    private List<PatrolStep> patrolSteps;

    private List<PatrolTaskPointStep> patrolTaskPointSteps;

    private Date resultUpdateTime;

    public Date getResultUpdateTime() {
        return resultUpdateTime;
    }

    public void setResultUpdateTime(final Date resultUpdateTime) {
        this.resultUpdateTime = resultUpdateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatrolPoint() {
        return patrolPoint;
    }

    public void setPatrolPoint(String patrolPoint) {
        this.patrolPoint = patrolPoint;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStepCount() {
        return stepCount == null ? 0 : stepCount;
    }

    public void setStepCount(Integer stepCount) {
        this.stepCount = stepCount;
    }

    public Integer getRecordCount() {
        return recordCount == null ? 0 : recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }

    public Integer getFaultCount() {
        return faultCount == null ? 0 : faultCount;
    }

    public void setFaultCount(Integer faultCount) {
        this.faultCount = faultCount;
    }

    public String getRelatedProcess() {
        return relatedProcess;
    }

    public void setRelatedProcess(String relatedProcess) {
        this.relatedProcess = relatedProcess;
    }

    public String getRelatedProcessNames() {
        return relatedProcessNames;
    }

    public void setRelatedProcessNames(String relatedProcessNames) {
        this.relatedProcessNames = relatedProcessNames;
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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public List<PatrolStep> getPatrolSteps() {
        return patrolSteps;
    }

    public void setPatrolSteps(List<PatrolStep> patrolSteps) {
        this.patrolSteps = patrolSteps;
    }

    public List<PatrolTaskPointStep> getPatrolTaskPointSteps() {
        return patrolTaskPointSteps;
    }

    public void setPatrolTaskPointSteps(List<PatrolTaskPointStep> patrolTaskPointSteps) {
        this.patrolTaskPointSteps = patrolTaskPointSteps;
    }

    /**
     * 是否已巡检过
     * 如果有更新时间，代表已经巡检过。
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
