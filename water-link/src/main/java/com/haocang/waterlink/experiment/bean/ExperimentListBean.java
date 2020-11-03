package com.haocang.waterlink.experiment.bean;

import java.util.List;

public class ExperimentListBean {


    /**
     * total : 9
     * items : [{"id":21,"siteId":8,"siteName":"南线泵站","formName":"南线泵站水泵4","cycleId":"2H","cycleName":"2小时","formLatestdate":"2020-07-06T16:00:00Z","updateTime":"2020-07-07T10:16:45Z"},{"id":20,"siteId":8,"siteName":"南线泵站","formName":"南线泵站水泵3","cycleId":"2H","cycleName":"2小时","formLatestdate":"2020-07-06T16:00:00Z","updateTime":"2020-07-07T10:16:16Z"},{"id":19,"siteId":8,"siteName":"南线泵站","formName":"南线泵站水泵2","cycleId":"2H","cycleName":"2小时","formLatestdate":"2020-07-06T16:00:00Z","updateTime":"2020-07-07T10:15:44Z"},{"id":18,"siteId":8,"siteName":"南线泵站","formName":"南线泵站水泵1","cycleId":"2H","cycleName":"2小时","formLatestdate":"2020-07-06T16:00:00Z","updateTime":"2020-07-07T10:15:08Z"},{"id":17,"siteId":1,"siteName":"黄水东调","formName":"人工采集测试","cycleId":"24H","cycleName":"24小时","formLatestdate":"2020-07-05T16:00:00Z","updateTime":"2020-07-06T02:05:08Z"},{"id":16,"siteId":9,"siteName":"主线","formName":"主线泵站数据","cycleId":"1H","cycleName":"1小时","formLatestdate":"2020-06-30T16:00:00Z","updateTime":"2020-07-01T08:52:36Z"},{"id":15,"siteId":8,"siteName":"南线泵站","formName":"南线泵站水质","cycleId":"1H","cycleName":"1小时","formLatestdate":"2020-06-30T16:00:00Z","updateTime":"2020-07-01T08:54:14Z"},{"id":13,"siteId":10,"siteName":"主线泵站","formName":"tep","cycleId":"1H","cycleName":"1小时","formLatestdate":"2020-06-30T16:00:00Z","updateTime":"2020-07-01T06:17:07Z"},{"id":12,"siteId":10,"siteName":"主线泵站","formName":"主线泵站SS","cycleId":"1H","cycleName":"1小时","formLatestdate":"2020-06-30T16:00:00Z","updateTime":"2020-07-01T06:17:32Z"}]
     */

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
         * id : 21
         * siteId : 8
         * siteName : 南线泵站
         * formName : 南线泵站水泵4
         * cycleId : 2H
         * cycleName : 2小时
         * formLatestdate : 2020-07-06T16:00:00Z
         * updateTime : 2020-07-07T10:16:45Z
         */

        private int id;
        private int siteId;
        private String siteName;
        private String formName;
        private String cycleId;
        private String cycleName;
        private String formLatestdate;
        private String updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSiteId() {
            return siteId;
        }

        public void setSiteId(int siteId) {
            this.siteId = siteId;
        }

        public String getSiteName() {
            return siteName;
        }

        public void setSiteName(String siteName) {
            this.siteName = siteName;
        }

        public String getFormName() {
            return formName;
        }

        public void setFormName(String formName) {
            this.formName = formName;
        }

        public String getCycleId() {
            return cycleId;
        }

        public void setCycleId(String cycleId) {
            this.cycleId = cycleId;
        }

        public String getCycleName() {
            return cycleName;
        }

        public void setCycleName(String cycleName) {
            this.cycleName = cycleName;
        }

        public String getFormLatestdate() {
            return formLatestdate;
        }

        public void setFormLatestdate(String formLatestdate) {
            this.formLatestdate = formLatestdate;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
