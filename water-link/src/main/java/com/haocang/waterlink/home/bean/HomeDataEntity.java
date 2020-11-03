package com.haocang.waterlink.home.bean;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题：
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/8/3上午9:10
 * 修  改  者：
 * 修改时间：
 */
public class HomeDataEntity {
    private Long id;
    private Long mpointId;
    private String mpointName;
    private Long siteId;
    private String siteName;
    private String unit;
    private String numtail;
    private String point;
    private String value;

    public Long getMpointId() {
        return mpointId;
    }

    public void setMpointId(Long mpointId) {
        this.mpointId = mpointId;
    }

    public String getMpointName() {
        return mpointName;
    }

    public void setMpointName(String mpointName) {
        this.mpointName = mpointName;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getNumtail() {
        return numtail;
    }

    public void setNumtail(String numtail) {
        this.numtail = numtail;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
