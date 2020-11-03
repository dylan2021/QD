package com.haocang.curve.collection.bean;

import java.util.List;

public class PointList {

    @Override
    public String toString() {
        return "PointList{" +
                "id=" + id +
                ", mpointType='" + mpointType + '\'' +
                ", siteId=" + siteId +
                ", siteName='" + siteName + '\'' +
                ", mpointId='" + mpointId + '\'' +
                ", mpointName='" + mpointName + '\'' +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", datasource='" + datasource + '\'' +
                ", datype='" + datype + '\'' +
                ", unit='" + unit + '\'' +
                ", value='" + value + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", numtail=" + numtail +
                ", coefficient='" + coefficient + '\'' +
                ", virtual=" + virtual +
                ", datadt='" + datadt + '\'' +
                ", ftype=" + ftype +
                ", enumvalue='" + enumvalue + '\'' +
                ", point='" + point + '\'' +
                ", tenantId=" + tenantId +
                ", tenantName='" + tenantName + '\'' +
                ", isConcerned=" + isConcerned +
                ", equipment=" + equipment +
                ", equipmentName=" + equipmentName +
                ", fixedState=" + fixedState +
                ", upperRange=" + upperRange +
                ", lowerRange=" + lowerRange +
                '}';
    }


    /**
         * id : 120
         * mpointType : 1
         * siteId : 6
         * siteName : 主线
         * mpointId : 000096
         * mpointName : 泵站COD
         * categoryId : 1
         * categoryName : 水质
         * datasource : AUTO
         * datype : Digtal
         * unit : mg/L
         * value : 31.00
         * deleteFlag : 0
         * numtail : 2
         * coefficient : 0
         * virtual : 0
         * datadt : 2020-06-30T09:24:00Z
         * ftype : null
         * enumvalue :
         * point : 1000000000-Modbus_01_TEMP_0
         * tenantId : 2
         * tenantName : 昊沧芒果
         * isConcerned : null
         * equipment : null
         * equipmentName : null
         * fixedState : false
         * upperRange : null
         * lowerRange : null
         */

        private boolean isSelect;
        private String checked;
        private Integer type;

        private int id;
        private String mpointType;
        private int siteId;
        private String siteName;
        private String mpointId;
        private String mpointName;
        private int categoryId;
        private String categoryName;
        private String datasource;
        private String datype;
        private String unit;
        private String value;
        private int deleteFlag;
        private int numtail;
        private String coefficient;
        private int virtual;
        private String datadt;
        private Object ftype;
        private String enumvalue;
        private String point;
        private int tenantId;
        private String tenantName;
        private Object isConcerned;
        private Object equipment;
        private Object equipmentName;
        private boolean fixedState;
        private Object upperRange;
        private Object lowerRange;

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getChecked() {
            return checked;
        }

        public void setChecked(String checked) {
            this.checked = checked;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMpointType() {
            return mpointType;
        }

        public void setMpointType(String mpointType) {
            this.mpointType = mpointType;
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

        public String getMpointId() {
            return mpointId;
        }

        public void setMpointId(String mpointId) {
            this.mpointId = mpointId;
        }

        public String getMpointName() {
            return mpointName;
        }

        public void setMpointName(String mpointName) {
            this.mpointName = mpointName;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getDatasource() {
            return datasource;
        }

        public void setDatasource(String datasource) {
            this.datasource = datasource;
        }

        public String getDatype() {
            return datype;
        }

        public void setDatype(String datype) {
            this.datype = datype;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getDeleteFlag() {
            return deleteFlag;
        }

        public void setDeleteFlag(int deleteFlag) {
            this.deleteFlag = deleteFlag;
        }

        public int getNumtail() {
            return numtail;
        }

        public void setNumtail(int numtail) {
            this.numtail = numtail;
        }

        public String getCoefficient() {
            return coefficient;
        }

        public void setCoefficient(String coefficient) {
            this.coefficient = coefficient;
        }

        public int getVirtual() {
            return virtual;
        }

        public void setVirtual(int virtual) {
            this.virtual = virtual;
        }

        public String getDatadt() {
            return datadt;
        }

        public void setDatadt(String datadt) {
            this.datadt = datadt;
        }

        public Object getFtype() {
            return ftype;
        }

        public void setFtype(Object ftype) {
            this.ftype = ftype;
        }

        public String getEnumvalue() {
            return enumvalue;
        }

        public void setEnumvalue(String enumvalue) {
            this.enumvalue = enumvalue;
        }

        public String getPoint() {
            return point;
        }

        public void setPoint(String point) {
            this.point = point;
        }

        public int getTenantId() {
            return tenantId;
        }

        public void setTenantId(int tenantId) {
            this.tenantId = tenantId;
        }

        public String getTenantName() {
            return tenantName;
        }

        public void setTenantName(String tenantName) {
            this.tenantName = tenantName;
        }

        public Object getIsConcerned() {
            return isConcerned;
        }

        public void setIsConcerned(Object isConcerned) {
            this.isConcerned = isConcerned;
        }

        public Object getEquipment() {
            return equipment;
        }

        public void setEquipment(Object equipment) {
            this.equipment = equipment;
        }

        public Object getEquipmentName() {
            return equipmentName;
        }

        public void setEquipmentName(Object equipmentName) {
            this.equipmentName = equipmentName;
        }

        public boolean isFixedState() {
            return fixedState;
        }

        public void setFixedState(boolean fixedState) {
            this.fixedState = fixedState;
        }

        public Object getUpperRange() {
            return upperRange;
        }

        public void setUpperRange(Object upperRange) {
            this.upperRange = upperRange;
        }

        public Object getLowerRange() {
            return lowerRange;
        }

        public void setLowerRange(Object lowerRange) {
            this.lowerRange = lowerRange;
        }

}
