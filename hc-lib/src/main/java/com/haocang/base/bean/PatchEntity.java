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
 * 创 建 者：hy
 * 创建时间：${DATA} 14:47
 * 修 改 者：
 * 修改时间：
 */
public class PatchEntity {

    public String getNewVersionNo() {
        return newVersionNo;
    }

    public void setNewVersionNo(String newVersionNo) {
        this.newVersionNo = newVersionNo;
    }

    /**
     * id : 43
     * appId : 72
     * patchAppId : 71
     * patchPath : group1/M00/0E/29/wKgCflzRJzmAKcdJAAMpKjIzXH45.patch
     * patchFullPath : http://kaifa.hc-yun.com:31018/group1/M00/0E/29/wKgCflzRJzmAKcdJAAMpKjIzXH45.patch
     * versionNo : HaoAn-2.0.0.0003A
     * size : 207146
     * createTime : 2019-05-07T06:35:58Z
     * createUserId : 2
     */
    private String newVersionNo;
    private int id;
    private int appId;
    private int patchAppId;
    private String patchPath;
    private String patchFullPath;
    private String versionNo;
    private String size;
    private String createTime;
    private int createUserId;
    private String remark;

    public int getForcedUpgrade() {
        return forcedUpgrade;
    }

    public void setForcedUpgrade(int forcedUpgrade) {
        this.forcedUpgrade = forcedUpgrade;
    }

    private int forcedUpgrade;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getPatchAppId() {
        return patchAppId;
    }

    public void setPatchAppId(int patchAppId) {
        this.patchAppId = patchAppId;
    }

    public String getPatchPath() {
        return patchPath;
    }

    public void setPatchPath(String patchPath) {
        this.patchPath = patchPath;
    }

    public String getPatchFullPath() {
        return patchFullPath;
    }

    public void setPatchFullPath(String patchFullPath) {
        this.patchFullPath = patchFullPath;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }
}
