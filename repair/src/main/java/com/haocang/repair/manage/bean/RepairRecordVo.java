package com.haocang.repair.manage.bean;

import com.haocang.base.bean.PictureInfo;

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
 * 创建时间：2018/5/7下午6:16
 * 修  改  者：
 * 修改时间：
 */
public class RepairRecordVo {
    /**
     * 修改时为必填"
     */
    private Integer id;
    /**
     * 维修工单id"
     */
    private Integer repairId;
    /**
     * 处理人id"
     */
    private Integer processingPersonId;
    /**
     * 处理人名称
     */
    private String processingPersonName;
    /**
     * 处理结果（-1：删除；0：待分派；1：处理中；2：挂起；3:关闭；4：完成）
     */
    private Integer repairResult;
    /**
     * 处理结果（删除；待分派；处理中;挂起；3:关闭；4：完成）。
     */
    private String repairResultName;

    /**
     * 挂起原因
     */
    private Integer repairHangupReason;

    /**
     * 关闭原因
     */
    private Integer repairCloseReason;
    /**
     * 维修措施(更换)"
     */
    private Integer repairAdopt;

    /**
     * resultName
     * 维修措施名称
     */
    private String repairAdoptName;


    private List<PictureInfo> pictureVideos;

    public Integer getRepairAdopt() {
        return repairAdopt;
    }

    public void setRepairAdopt(Integer repairAdopt) {
        this.repairAdopt = repairAdopt;
    }

    public String getRepairAdoptName() {
        return repairAdoptName;
    }

    public void setRepairAdoptName(String repairAdoptName) {
        this.repairAdoptName = repairAdoptName;
    }

    /**
     * 故障分析"
     */
    private String repairReason;
    /**
     * 故障分析
     */
    private String faultAnalysis;
    /**
     * 挂起原因
     */
    private String causeHanging;
    /**
     * 现场记录图片视频url
     */
    private String[] imgUrl;
    /**
     * 备注"
     */
    private String remark;
    /**
     * 创建人ID"
     */
    private Integer createUserId;
    /**
     * 创建人
     */
    private String createUserName;
    /**
     * 维修时间
     */
    private String createDate;

    /**
     * 结束时间
     */
    private String finishDate;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }


    /**
     * @return the repairId
     */
    public Integer getRepairId() {
        return repairId;
    }


    /**
     * @return the processingPersonId
     */
    public Integer getProcessingPersonId() {
        return processingPersonId;
    }


    /**
     * @return the processingPersonName
     */
    public String getProcessingPersonName() {
        return processingPersonName;
    }


    /**
     * @return the repairResult
     */
    public Integer getRepairResult() {
        return repairResult;
    }


    /**
     * @return the repairResultName
     */
    public String getRepairResultName() {
        return repairResultName;
    }


    /**
     * @return the repairReason
     */
    public String getRepairReason() {
        return repairReason;
    }


    /**
     * @return the faultAnalysis
     */
    public String getFaultAnalysis() {
        return faultAnalysis;
    }


    /**
     * @return the causeHanging
     */
    public String getCauseHanging() {
        return causeHanging;
    }


    /**
     * @return the imgUrl
     */
    public String[] getImgUrl() {
        return imgUrl;
    }


    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }


    /**
     * @return the createUserId
     */
    public Integer getCreateUserId() {
        return createUserId;
    }


    /**
     * @return the createUserName
     */
    public String getCreateUserName() {
        return createUserName;
    }


    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * @param repairId the repairId to set
     */
    public void setRepairId(Integer repairId) {
        this.repairId = repairId;
    }


    /**
     * @param processingPersonId the processingPersonId to set
     */
    public void setProcessingPersonId(Integer processingPersonId) {
        this.processingPersonId = processingPersonId;
    }


    /**
     * @param processingPersonName the processingPersonName to set
     */
    public void setProcessingPersonName(String processingPersonName) {
        this.processingPersonName = processingPersonName;
    }


    /**
     * @param repairResult the repairResult to set
     */
    public void setRepairResult(Integer repairResult) {
        this.repairResult = repairResult;
    }


    /**
     * @param repairResultName the repairResultName to set
     */
    public void setRepairResultName(String repairResultName) {
        this.repairResultName = repairResultName;
    }


    /**
     * @param repairReason the repairReason to set
     */
    public void setRepairReason(String repairReason) {
        this.repairReason = repairReason;
    }


    /**
     * @param faultAnalysis the faultAnalysis to set
     */
    public void setFaultAnalysis(String faultAnalysis) {
        this.faultAnalysis = faultAnalysis;
    }


    /**
     * @param causeHanging the causeHanging to set
     */
    public void setCauseHanging(String causeHanging) {
        this.causeHanging = causeHanging;
    }


    /**
     * @param imgUrl the imgUrl to set
     */
    public void setImgUrl(String[] imgUrl) {
        this.imgUrl = imgUrl;
    }


    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }


    /**
     * @param createUserId the createUserId to set
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }


    /**
     * @param createUserName the createUserName to set
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }


    /**
     * @return the createDate
     */
    public String getCreateDate() {
        return createDate;
    }


    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RepairRecord [id=" + id + ", repairId=" + repairId + ", processingPersonId=" + processingPersonId
                + ", processingPersonName=" + processingPersonName + ", repairResult=" + repairResult
                + ", repairResultName=" + repairResultName + ", repairAdopt=" + repairAdopt + ", repairReason="
                + repairReason + ", faultAnalysis=" + faultAnalysis + ", causeHanging=" + causeHanging + ", imgUrl="
                + imgUrl + ", remark=" + remark + ", createUserId=" + createUserId + ", createUserName="
                + createUserName + "]";
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getRepairHangupReason() {
        return repairHangupReason;
    }

    public void setRepairHangupReason(Integer repairHangupReason) {
        this.repairHangupReason = repairHangupReason;
    }

    public Integer getRepairCloseReason() {
        return repairCloseReason;
    }

    public void setRepairCloseReason(Integer repairCloseReason) {
        this.repairCloseReason = repairCloseReason;
    }

    public List<PictureInfo> getPictureVideos() {
        return pictureVideos;
    }

    public void setPictureVideos(List<PictureInfo> pictureVideos) {
        this.pictureVideos = pictureVideos;
    }
}
