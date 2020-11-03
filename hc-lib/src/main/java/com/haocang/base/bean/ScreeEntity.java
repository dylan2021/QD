package com.haocang.base.bean;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/1515:27
 * 修 改 者：
 * 修改时间：
 */
public class ScreeEntity {
    private boolean isSelector;

    private String name;

    private int id;
    /**
     * categoryName : 水质
     * unit : mg/L;NTU;个/L;mL/g
     * createTime : 2018-02-05 19:04:54
     * createUserid : 1
     * updateTime : null
     * updateUserid : null
     */

    private String categoryName;
    private String unit;
    private String createTime;
    private int createUserid;
    private String updateTime;
    private int updateUserid;
    private String ids;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }


    public boolean isSelector() {
        return isSelector;
    }

    public void setSelector(boolean selector) {
        isSelector = selector;
    }

    public String getName() {
        return name != null ? name : categoryName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(int createUserid) {
        this.createUserid = createUserid;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getUpdateUserid() {
        return updateUserid;
    }

    public void setUpdateUserid(int updateUserid) {
        this.updateUserid = updateUserid;
    }
}
