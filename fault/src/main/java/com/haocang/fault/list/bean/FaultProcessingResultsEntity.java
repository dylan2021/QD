package com.haocang.fault.list.bean;

import java.util.List;

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
 * 创建时间：2018/5/1020:59
 * 修 改 者：
 * 修改时间：
 */
public class FaultProcessingResultsEntity {

    /**
     * createDate : 2018-05-10T12:19:03.609Z
     * faultCloseRenson : string
     * faultReason : string
     * id : 0
     * imgUrl : ["string"]
     * processingPersonName : string
     * processingResult : 0
     * processingResultName : string
     * remark : string
     * updateDate : 2018-05-10T12:19:03.609Z
     */

    private String createDate;
    private String faultCloseRenson;
    private String faultReason;
    private int id;
    private String processingPersonName;
    private int processingResult;
    private String processingResultName;
    private String remark;
    private String updateDate;
    private List<String> imgUrl;


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFaultCloseRenson() {
        return faultCloseRenson;
    }

    public void setFaultCloseRenson(String faultCloseRenson) {
        this.faultCloseRenson = faultCloseRenson;
    }

    public String getFaultReason() {
        return faultReason;
    }

    public void setFaultReason(String faultReason) {
        this.faultReason = faultReason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProcessingPersonName() {
        return processingPersonName;
    }

    public void setProcessingPersonName(String processingPersonName) {
        this.processingPersonName = processingPersonName;
    }

    public int getProcessingResult() {
        return processingResult;
    }

    public void setProcessingResult(int processingResult) {
        this.processingResult = processingResult;
    }

    public String getProcessingResultName() {
        return processingResultName;
    }

    public void setProcessingResultName(String processingResultName) {
        this.processingResultName = processingResultName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public List<String> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<String> imgUrl) {
        this.imgUrl = imgUrl;
    }
}
