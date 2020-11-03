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
 * 创建时间：2018/2/210:03
 * 修 改 者：
 * 修改时间：
 */
public class AppVersion {


    /**
     * id : 30
     * name : 测试888
     * versionNo : android-2.0.0003B
     * remark : 1.ui更新
     * iosAndroid : android
     * apkPath : http://dev3.haocang.com:11011/group1/M00/00/30/wKgCfltw_JiAaHDUAZN8X4Sysiw537.apk
     * qrImgPath : http://dev3.haocang.com:11011/group1/M00/00/30/wKgCfltw_JmAHq1tAAA5z4QycD0189.jpg
     * type : android
     * createTime : 2018-08-12T16:00:00Z
     * updateTime : 2018-08-12T16:00:00Z
     * createUserId : 2
     * updateUserId : 2
     * state : 1
     * version : 0
     * forcedUpgrade : 0
     * compatibleVersion : null
     */

    private int id;
    private String name;
    private String versionNo;
    private String remark;
    private String iosAndroid;
    private String apkPath;
    private String qrImgPath;
    private String type;
    private String createTime;
    private String updateTime;
    private int createUserId;
    private int updateUserId;
    private int state;
    private int version;
    private int forcedUpgrade;
    private String compatibleVersion;
    private String size;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIosAndroid() {
        return iosAndroid;
    }

    public void setIosAndroid(String iosAndroid) {
        this.iosAndroid = iosAndroid;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public String getQrImgPath() {
        return qrImgPath;
    }

    public void setQrImgPath(String qrImgPath) {
        this.qrImgPath = qrImgPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public int getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(int updateUserId) {
        this.updateUserId = updateUserId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getForcedUpgrade() {
        return forcedUpgrade;
    }

    public void setForcedUpgrade(int forcedUpgrade) {
        this.forcedUpgrade = forcedUpgrade;
    }

    public String getCompatibleVersion() {
        return compatibleVersion;
    }

    public void setCompatibleVersion(String compatibleVersion) {
        this.compatibleVersion = compatibleVersion;
    }
}
