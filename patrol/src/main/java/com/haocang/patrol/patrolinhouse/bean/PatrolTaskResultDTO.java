package com.haocang.patrol.patrolinhouse.bean;

import java.text.DecimalFormat;
import java.util.Date;

public class PatrolTaskResultDTO {

    private Integer id;

    /**
     * 巡检开始时间
     */
    private Date startTime;

    /**
     * 巡检结束时间
     */
    private Date endTime;

    /**
     * 有效路径
     */
    private Integer validPathLength;

    /**
     * 实际路径
     */
    private Integer actualPathLength;

    /**
     * 记录路径长度
     */
    private Integer planPathLength;

    private float timeCost;

    private final double nh = 1000 * 60 * 60;

    private Integer inspectedCount;

    private Integer pointCount;

    private Integer faultCount;

    private Integer stepCount;

    private Integer hasResultCount;

    private Date actualStartTime;

    private Date actualEndTime;

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(final Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getValidPathLength() {
        return validPathLength <= actualPathLength ? validPathLength : actualPathLength;
    }

    public void setValidPathLength(Integer validPathLength) {
        this.validPathLength = validPathLength;
    }

    public Integer getActualPathLength() {
        return actualPathLength;
    }

    public void setActualPathLength(Integer actualPathLength) {
        this.actualPathLength = actualPathLength;
    }

    public Integer getPlanPathLength() {
        return planPathLength;
    }

    public void setPlanPathLength(Integer planPathLength) {
        this.planPathLength = planPathLength;
    }

    public float getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(float timeCost) {
        this.timeCost = timeCost;
    }

    public Integer getInspectedCount() {
        return inspectedCount == null ? 0 : inspectedCount;
    }

    public void setInspectedCount(Integer inspectedCount) {
        this.inspectedCount = inspectedCount;
    }

    public Integer getPointCount() {
        return pointCount == null ? 0 : pointCount;
    }

    public void setPointCount(Integer pointCount) {
        this.pointCount = pointCount;
    }

    public Integer getFaultCount() {
        return faultCount == null ? 0 : faultCount;
    }

    public void setFaultCount(Integer faultCount) {
        this.faultCount = faultCount;
    }

    public Integer getStepCount() {
        return stepCount;
    }

    public void setStepCount(Integer stepCount) {
        this.stepCount = stepCount;
    }

    public Integer getHasResultCount() {
        return hasResultCount;
    }

    public void setHasResultCount(final Integer hasResultCount) {
        this.hasResultCount = hasResultCount;
    }

    public String getTimeCostShow() {
        if (actualEndTime == null || actualStartTime == null) {
            return "";
        }
        long actualDiff = actualEndTime.getTime() - actualStartTime.getTime();
//        long planDiff = endTime.getTime() - startTime.getTime();
        DecimalFormat df = new DecimalFormat("#.0");
        String result = df.format(actualDiff / nh);
        if (result.startsWith(".")) {
            result = "0" + result;
        }
        return result;
    }
}
