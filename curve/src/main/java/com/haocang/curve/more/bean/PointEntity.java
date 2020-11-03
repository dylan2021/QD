package com.haocang.curve.more.bean;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区龙兰路277号一号楼9楼
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/5/31 9:56
 * 修 改 者：
 * 修改时间：
 */
public class PointEntity {


    /**
     * id : 2204
     * siteId : 1
     * siteName : A区
     * mpointId : 001819
     * mpointName : test1111234
     * categoryId : 1
     * categoryName : 水质
     * datasource : CALC
     * datype : State
     * unit :
     * value : null
     * deleteFlag : 0
     * numtail : null
     * virtual : 0
     * datadt : null
     * ftype : Simple
     * point : null
     */
    private String titleName;
    private int id;
    private int siteId;
    private String siteName;
    private String mpointId;
    private String combineId;
    private String mpointName;
    private int categoryId;
    private String categoryName;
    private String datasource;
    private String datype;
    private String unit;
    private String value;
    private int deleteFlag;
    private String numtail;
    private int virtual;
    private String datadt;
    private String ftype;
    private String point;
    private boolean isSelect;//是否选中

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }


    /**
     * 1已选曲线，2单数据曲线
     */
    private int type = 2;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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

    public String getNumtail() {
        return numtail;
    }

    public void setNumtail(String numtail) {
        this.numtail = numtail;
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

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getCombineId() {
        return combineId;
    }

    public void setCombineId(String combineId) {
        this.combineId = combineId;
    }
}
