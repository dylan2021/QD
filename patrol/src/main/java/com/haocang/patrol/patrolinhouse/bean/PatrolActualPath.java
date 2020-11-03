/**
 * Copyright &copy; 2017-2027 hcsystem All rights reserved.
 */
package com.haocang.patrol.patrolinhouse.bean;

import java.util.Date;

/***
 * 实际巡检路线Entity
 * @author zhangfang
 * @version 2018-03-22
 */
public class PatrolActualPath {
    private static final long serialVersionUID = 1L;

    //id没有生成,暂时手写
//	@ApiModelProperty(value="ID, 修改时为必填")
    private Integer id;

    //	@NotNull(message="patrolActualPath_patrolTaskIdNotNull")
//	@ApiModelProperty(value = "任务id")
    private Integer patrolTaskId;

    //	@ApiModelProperty(value = "地名")
    private String place;

    //	@NotNull(message="patrolActualPath_longitudeNotNull")
//	@ApiModelProperty(value = "经度")
    private double longitude;

    //	@NotNull(message="patrolActualPath_latitudeNotNull")
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

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}