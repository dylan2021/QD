package com.haocang.repair.manage.bean;

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
 * 创建时间：2018/5/318:34
 * 修 改 者：
 * 修改时间：
 */
public class FaultProcessingProgressEntity {

    /**
     * id : 1
     * processingPersonName : 普通用户
     * processingResult : 1
     * processingResultName : 处理中
     * faultReason : 缺陷原因1
     * faultCloseRenson : 关闭原因1
     * imgUrl : ["https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_3834907999094674363%22%7D&n_type=0&p_from=1","https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_3834907999094674363%22%7D&n_type=0&p_from=1"]
     * remark : null
     * createDate : 2018-04-25 17:54:02
     */

    private int id;
    private String processingPersonName;
    private int processingResult;
    private String processingResultName;
    private String faultReason;
    private String faultCloseRenson;
    private String remark;
    private String createDate;
    private String imgUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProcessingPersonName() {
        return processingPersonName != null ? processingPersonName : "";
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
        return processingResultName != null ? processingResultName : "";
    }

    public void setProcessingResultName(String processingResultName) {
        this.processingResultName = processingResultName;
    }

    public String getFaultReason() {
        return faultReason;
    }

    public void setFaultReason(String faultReason) {
        this.faultReason = faultReason;
    }

    public String getFaultCloseRenson() {
        return faultCloseRenson;
    }

    public void setFaultCloseRenson(String faultCloseRenson) {
        this.faultCloseRenson = faultCloseRenson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
