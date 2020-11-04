package com.haocang.waterlink.experiment.bean;

import java.util.List;

public class ExperimentDetailBean {

    private int formId;
    private String recordDate;
    private String thumbnailUrl;
    private String url;
    private List<MpointsBean> mpoints;

    @Override
    public String toString() {
        return "ExperimentDetailBean{" +
                "formId=" + formId +
                ", recordDate='" + recordDate + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", url='" + url + '\'' +
                ", mpoints=" + mpoints +
                '}';
    }


    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MpointsBean> getMpoints() {
        return mpoints;
    }

    public void setMpoints(List<MpointsBean> mpoints) {
        this.mpoints = mpoints;
    }

    public static class MpointsBean {
        /**
         * mpointId : 263
         * time : 2020-07-09T16:00:00Z
         * value : 2
         * status : 2
         */

        private int mpointId;
        //请求时间
        private String time;
        //显示时间 需要+8
        private String falseTime;
        private String value;
        private String status;
        public String mpointName;


        @Override
        public String toString() {
            return "MpointsBean{" +
                    "mpointId=" + mpointId +
                    ", time='" + time + '\'' +
                    ", falseTime='" + falseTime + '\'' +
                    ", value='" + value + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }

        public String getFalseTime() {
            return falseTime;
        }

        public void setFalseTime(String falseTime) {
            this.falseTime = falseTime;
        }

        public int getMpointId() {
            return mpointId;
        }

        public void setMpointId(int mpointId) {
            this.mpointId = mpointId;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
