package com.haocang.patrol.patrolinhouse.bean;

public class PatrolPlanPath {

    private Integer id;

    //    @ApiModelProperty(value = "巡检计划ID，必填")
//	@NotNull(message = "PlanIdIsNull", groups = { UpdGroup.class })
    private Integer planId;

    private double longitude;

    private double latitude;

    private int taskId;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
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
}