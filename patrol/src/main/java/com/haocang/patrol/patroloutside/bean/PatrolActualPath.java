package com.haocang.patrol.patroloutside.bean;

import java.util.Date;

/**
 * 实际巡检路线Entity
 *
 * @author zhangfang
 * @version 2018-03-22
 */
public class PatrolActualPath {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;

    //	@ApiModelProperty(value = "任务id")
    private Integer patrolTaskId;

    //	@ApiModelProperty(value = "地名")
    private String place;

    //	@ApiModelProperty(value = "经度")
    private double longitude;

    //	@ApiModelProperty(value = "纬度")
    private double latitude;

    //	@ApiModelProperty(value = "上传时间")
    private Date updateTime;

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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}