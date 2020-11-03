package com.haocang.repair.progress.bean;

import com.haocang.base.bean.PictureEntity;

import java.util.List;

/**
 * app
 * 内部维修工单详情
 *
 * @author leijun
 * @version 2018年5月22日 下午8:48:21
 */
public class AppRepairRecordDetailVo {
    /**
     * 设备id
     */
    private Integer equId;
    /**
     * 设备编号
     */
    private String equCode;
    /**
     * 设备名称"
     */
    private String equName;
    /**
     * 维修措施,1.跟换,2.维修,3.其他
     */
    private Integer repairAdopt;
    /**
     * 维修措施,1.跟换,2.维修,3.其他
     */
    private String repairAdoptname;
    /**
     * 处理结果（-1：删除；0：未处理；1：执行中；2：挂起；3:关闭；4:完成）
     */
    private Integer repairResult;
    /**
     * "处理结果（-1：删除；0：未处理；1：执行中；2：挂起；3:关闭；4:完成）
     */
    private String repairResultName;
    /**
     * 处理人id"
     */
    private Integer processingPersonId;
    /**
     * 处理人名称"
     */
    private String processingPersonName;
    /**
     * 完成时间
     */
    private String finishDate;
    /**
     * 创建人ID"
     */
    private Integer createUserId;
    /**
     * 创建人  报障人员
     */
    private String createUserName;
    /**
     * 故障分析
     */
    private String faultAnalysis;
    /**
     * 图片url
     */
    private String[] imgUrl;
    /**
     * 是否已经关注了
     */
    private Boolean concerned;

    private List<PictureEntity> pictureVideos;

    /**
     * @return the equId
     */
    public Integer getEquId() {
        return equId;
    }

    /**
     * @param equId the equId to set
     */
    public void setEquId(Integer equId) {
        this.equId = equId;
    }

    /**
     * @return the equCode
     */
    public String getEquCode() {
        return equCode;
    }

    /**
     * @param equCode the equCode to set
     */
    public void setEquCode(String equCode) {
        this.equCode = equCode;
    }

    /**
     * @return the equName
     */
    public String getEquName() {
        return equName;
    }

    /**
     * @param equName the equName to set
     */
    public void setEquName(String equName) {
        this.equName = equName;
    }

    /**
     * @return the processingPersonId
     */
    public Integer getProcessingPersonId() {
        return processingPersonId;
    }

    /**
     * @param processingPersonId the processingPersonId to set
     */
    public void setProcessingPersonId(Integer processingPersonId) {
        this.processingPersonId = processingPersonId;
    }

    /**
     * @return the processingPersonName
     */
    public String getProcessingPersonName() {
        return processingPersonName;
    }

    /**
     * @param processingPersonName the processingPersonName to set
     */
    public void setProcessingPersonName(String processingPersonName) {
        this.processingPersonName = processingPersonName;
    }

    /**
     * @return the finishDate
     */
    public String getFinishDate() {
        return finishDate;
    }

    /**
     * @param finishDate the finishDate to set
     */
    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    /**
     * @return the createUserId
     */
    public Integer getCreateUserId() {
        return createUserId;
    }

    /**
     * @param createUserId the createUserId to set
     */
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * @return the createUserName
     */
    public String getCreateUserName() {
        return createUserName;
    }

    /**
     * @param createUserName the createUserName to set
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    /**
     * @return the faultAnalysis
     */
    public String getFaultAnalysis() {
        return faultAnalysis;
    }

    /**
     * @param faultAnalysis the faultAnalysis to set
     */
    public void setFaultAnalysis(String faultAnalysis) {
        this.faultAnalysis = faultAnalysis;
    }

    /**
     * @return the imgUrl
     */
    public String[] getImgUrl() {
        return imgUrl;
    }

    /**
     * @param imgUrl the imgUrl to set
     */
    public void setImgUrl(String[] imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * @return the repairAdopt
     */
    public Integer getRepairAdopt() {
        return repairAdopt;
    }

    /**
     * @param repairAdopt the repairAdopt to set
     */
    public void setRepairAdopt(Integer repairAdopt) {
        this.repairAdopt = repairAdopt;
    }

    /**
     * @return the repairAdoptname
     */
    public String getRepairAdoptname() {
        return repairAdoptname;
    }

    /**
     * @param repairAdoptname the repairAdoptname to set
     */
    public void setRepairAdoptname(String repairAdoptname) {
        this.repairAdoptname = repairAdoptname;
    }

    /**
     * @return the repairResult
     */
    public Integer getRepairResult() {
        return repairResult;
    }

    /**
     * @param repairResult the repairResult to set
     */
    public void setRepairResult(Integer repairResult) {
        this.repairResult = repairResult;
    }

    /**
     * @return the repairResultName
     */
    public String getRepairResultName() {
        return repairResultName;
    }

    /**
     * @param repairResultName the repairResultName to set
     */
    public void setRepairResultName(String repairResultName) {
        this.repairResultName = repairResultName;
    }

    /**
     * @return the concerned
     */
    public Boolean getConcerned() {
        return concerned;
    }

    /**
     * @param concerned the concerned to set
     */
    public void setConcerned(Boolean concerned) {
        this.concerned = concerned;
    }

    public List<PictureEntity> getPictureVideos() {
        return pictureVideos;
    }

    public void setPictureVideos(List<PictureEntity> pictureVideos) {
        this.pictureVideos = pictureVideos;
    }

}
