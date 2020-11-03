package com.haocang.curve.collection.bean;

import com.haocang.curve.more.bean.PointEntity;

import java.util.List;

public class AppChartDTO {


    private Long id;


    private String combineName;

    private String mpointNames;

    private String mpointIds;

    private List<PointEntity> list;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCombineName() {
        return combineName;
    }

    public void setCombineName(String combineName) {
        this.combineName = combineName;
    }

    public String getMpointNames() {
        return mpointNames;
    }

    public void setMpointNames(String mpointNames) {
        this.mpointNames = mpointNames;
    }

    public String getMpointIds() {
        return mpointIds;
    }

    public void setMpointIds(String mpointIds) {
        this.mpointIds = mpointIds;
    }

    public List<PointEntity> getList() {
        return list;
    }

    public void setList(List<PointEntity> list) {
        this.list = list;
    }
}
