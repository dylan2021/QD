/**
 * Copyright &copy; 2017-2027 hcsystem All rights reserved.
 */
package com.haocang.patrol.patrolinhouse.bean;

import com.haocang.base.bean.PictureInfo;

import java.util.List;

/**
 * .
 * 巡检任务-点-步骤关联Entity
 *
 * @author zhangfang
 * @version 2018-03-22
 */
public class PatrolTaskPointStep {
    public List<PictureInfo> getFileList() {
        return fileList;
    }

    private List<PatrolPictureEntity> stepImgList;

    public List<PatrolPictureEntity> getStepImgList() {
        return stepImgList;
    }

    public void setStepImgList(List<PatrolPictureEntity> stepImgList) {
        this.stepImgList = stepImgList;
    }

    public void setFileList(List<PictureInfo> fileList) {
        this.fileList = fileList;
    }

    private List<PictureInfo> fileList;
    private String imgUrl;

    private String imgThumbnaiUrl;

    private static final long serialVersionUID = 1L;

    //id没有生成,暂时手写
//	@ApiModelProperty(value="ID, 修改时为必填")
    private Integer id;

    //	@ApiModelProperty(value = "巡检任务id")
//	@NotNull(message="patrolTaskPointStep_patrolTaskIdNotNull")
    private Integer patrolTaskId;

    //	@ApiModelProperty(value = "关联的巡检点id")
//	@NotNull(message="patrolTaskPointStep_patrolPointIdNotNull")
    private Integer patrolPointId;

    //	@ApiModelProperty(value = "关联的巡检步骤")
//	@NotNull(message="patrolTaskPointStep_patrolStepIdNotNull")
    private Integer patrolStepId;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    private boolean isSelect;

    //	@ApiModelProperty(value = "巡检步骤名称")
//	@NotBlank(message="patrolTaskPointStep_stepNameNotBlank")
    private String stepName;

    //	@ApiModelProperty(value = "巡检步骤结果类型：1为状态，2为数据",3为免检)
//	@NotNull(message="patrolTaskPointStep_stepResultTypeNotNull")
    private Integer stepResultType;
    /**
     * 1为状态
     */
    public static final int TYPE_STATE = 1;
    /**
     * 2为数据
     */
    public static final int TYPE_DATA = 2;

    /**
     * 免检
     */
    public static final int TYPE_EXEMPTION = 3;


    public static final String NORMAL = "normal";

    public static final String ABNORMAL = "abnormal";

    private boolean isShowRemark;

    public boolean isShowRemark() {
        return isShowRemark;
    }

    public void setShowRemark(boolean showRemark) {
        isShowRemark = showRemark;
    }


    //	@ApiModelProperty(value = "巡检步骤结果：type为1和2时为必填。其中type为1时，该字段normal为正常，abnormal为异常")
    private String stepResult;

    private String lastStepResult;

    public String getLastStepResult() {
        return "/".equals(lastStepResult) ? "" : lastStepResult;
    }

    public void setLastStepResult(String lastStepResult) {
        this.lastStepResult = lastStepResult;
    }


    //	@ApiModelProperty(value = "巡检步骤备注")
    private String stepComment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatrolTaskId() {
        return patrolTaskId;
    }

    public void setPatrolTaskId(Integer patrolTaskId) {
        this.patrolTaskId = patrolTaskId;
    }

    public Integer getPatrolPointId() {
        return patrolPointId;
    }

    public void setPatrolPointId(Integer patrolPointId) {
        this.patrolPointId = patrolPointId;
    }

    public Integer getPatrolStepId() {
        return patrolStepId;
    }

    public void setPatrolStepId(Integer patrolStepId) {
        this.patrolStepId = patrolStepId;
    }

    public String getStepName() {
        return stepName;
    }

    public String getNewStepName() {
//        if (stepName != null && stepName.length() >= 20) {
//            stepName = stepName.substring(0, 19) + "...";
//        }
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public Integer getStepResultType() {
        return stepResultType;
    }

    public void setStepResultType(Integer stepResultType) {
        this.stepResultType = stepResultType;
    }

    public String getStepResult() {
        return stepResult;
    }

    public void setStepResult(String stepResult) {
        this.stepResult = stepResult;
    }

    public String getStepComment() {
        return stepComment;
    }

    public void setStepComment(String stepComment) {
        this.stepComment = stepComment;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgThumbnaiUrl() {
        return imgThumbnaiUrl;
    }

    public void setImgThumbnaiUrl(String imgThumbnaiUrl) {
        this.imgThumbnaiUrl = imgThumbnaiUrl;
    }

}