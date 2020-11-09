package com.haocang.waterlink.pump;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BZ_FMJ_ListBean {

    private int total;
    private List<ItemsBean> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        private int id;
        private String pumpName;
        private int processId;
        private String processName;
        private String contractor;
        private String constructors;
        private Object supervisor;
        private Object userId;
        private boolean status;
        private Object remark;
        private Object startTime;
        private Object complateTime;
        private Object designer;
        private String supervisorUnit;
        private String wellName;
        private Object lineId;
        private String lineName;
        private int wellType;
        private String groundHeight;
        private String designTube;
        private String topHeight;
        private String bottemHeight;
        private String buriedDepth;
        private double longitude;
        private double latitude;
        private String stake;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPumpName() {
            return pumpName;
        }

        public void setPumpName(String pumpName) {
            this.pumpName = pumpName;
        }

        public int getProcessId() {
            return processId;
        }

        public void setProcessId(int processId) {
            this.processId = processId;
        }

        public String getProcessName() {
            return processName;
        }

        public void setProcessName(String processName) {
            this.processName = processName;
        }

        public String getContractor() {
            return contractor;
        }

        public void setContractor(String contractor) {
            this.contractor = contractor;
        }

        public String getConstructors() {
            return constructors;
        }

        public void setConstructors(String constructors) {
            this.constructors = constructors;
        }

        public Object getSupervisor() {
            return supervisor;
        }

        public void setSupervisor(Object supervisor) {
            this.supervisor = supervisor;
        }


        public Object getUserId() {
            return userId;
        }

        public void setUserId(Object userId) {
            this.userId = userId;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Object getStartTime() {
            return startTime;
        }

        public void setStartTime(Object startTime) {
            this.startTime = startTime;
        }

        public Object getComplateTime() {
            return complateTime;
        }

        public void setComplateTime(Object complateTime) {
            this.complateTime = complateTime;
        }

        public Object getDesigner() {
            return designer;
        }

        public void setDesigner(Object designer) {
            this.designer = designer;
        }

        public String getSupervisorUnit() {
            return supervisorUnit;
        }

        public void setSupervisorUnit(String supervisorUnit) {
            this.supervisorUnit = supervisorUnit;
        }

        public String getWellName() {
            return wellName;
        }

        public void setWellName(String wellName) {
            this.wellName = wellName;
        }

        public Object getLineId() {
            return lineId;
        }

        public void setLineId(Object lineId) {
            this.lineId = lineId;
        }

        public String getLineName() {
            return lineName;
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }

        public int getWellType() {
            return wellType;
        }

        public void setWellType(int wellType) {
            this.wellType = wellType;
        }

        public String getGroundHeight() {
            return groundHeight;
        }

        public void setGroundHeight(String groundHeight) {
            this.groundHeight = groundHeight;
        }

        public String getDesignTube() {
            return designTube;
        }

        public void setDesignTube(String designTube) {
            this.designTube = designTube;
        }

        public String getTopHeight() {
            return topHeight;
        }

        public void setTopHeight(String topHeight) {
            this.topHeight = topHeight;
        }

        public String getBottemHeight() {
            return bottemHeight;
        }

        public void setBottemHeight(String bottemHeight) {
            this.bottemHeight = bottemHeight;
        }

        public String getBuriedDepth() {
            return buriedDepth;
        }

        public void setBuriedDepth(String buriedDepth) {
            this.buriedDepth = buriedDepth;
        }

        public String getStake() {
            return stake;
        }

        public void setStake(String stake) {
            this.stake = stake;
        }


    }
}
