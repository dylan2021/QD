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
 * 创建时间：2018/8/3下午5:47
 * 修  改  者：
 * 修改时间：
 */
public class WeatherEntity {
    private String city;
    private String temprature;


    private String isDay;
    /**
     * 天气，包括晴，阴，雨，雪
     */
    private String type;
    private String cityPinyin;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemprature() {
        return temprature;
    }

    public void setTemprature(String temprature) {
        this.temprature = temprature;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCityPinyin() {
        return cityPinyin;
    }

    public void setCityPinyin(String cityPinyin) {
        this.cityPinyin = cityPinyin;
    }

    public String getIsDay() {
        return isDay;
    }

    public void setIsDay(String isDay) {
        this.isDay = isDay;
    }
}
