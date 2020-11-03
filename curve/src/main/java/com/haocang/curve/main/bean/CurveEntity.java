package com.haocang.curve.main.bean;

import java.util.List;

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
 * 创建时间：2018/6/12下午2:26
 * 修  改  者：
 * 修改时间：
 */
public class CurveEntity {
    private String avgData;
    private String maxData;
    private String minData;
    private String unit;
    private List<DataEntity> data;

    public String getAvgData() {
        return avgData;
    }

    public void setAvgData(String avgData) {
        this.avgData = avgData;
    }

    public String getMaxData() {
        return maxData;
    }

    public void setMaxData(String maxData) {
        this.maxData = maxData;
    }

    public String getMinData() {
        return minData;
    }

    public void setMinData(String minData) {
        this.minData = minData;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }
}
