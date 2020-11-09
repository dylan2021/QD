package com.haocang.waterlink.pump;

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
        /**
         * id : 33
         * pumpName : 主线泵站
         * processId : 10
         * processName : 主线泵站
         * contractor : 青岛市水利工程建设开发总公司
         * constructors : 中国电建集团核电工程有限公司
         * supervisor : null
         * longitude : null
         * latitude : null
         * userId : null
         * status : false
         * remark : null
         * startTime : null
         * complateTime : null
         * designer : null
         * supervisorUnit : 青岛市工程建设监理有限责任公司
         */

        private int id;
        private String pumpName;
        private int processId;
        private String processName;
        private String contractor;
        private String constructors;
        private Object supervisor;
        private Object longitude;
        private Object latitude;
        private Object userId;
        private boolean status;
        private Object remark;
        private Object startTime;
        private Object complateTime;
        private Object designer;
        private String supervisorUnit;

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

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
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
    }
}
