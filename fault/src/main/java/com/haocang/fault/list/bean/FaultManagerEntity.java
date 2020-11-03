package com.haocang.fault.list.bean;

import android.text.TextUtils;

import com.haocang.base.bean.PictureEntity;
import com.haocang.base.config.AppApplication;
import com.haocang.fault.R;

import org.json.JSONArray;

import java.util.ArrayList;
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
 * 创建时间：2018/4/2613:57
 * 修 改 者：
 * 修改时间：
 */
public class FaultManagerEntity {

    /**
     * id : 19
     * faultTypeName : 工艺缺陷
     * severityTypeName : null
     * processingPersonName : 杜工程师
     * processingDate : null
     * state : 0
     * faultNumber : 2018050310511
     * createUser : 开发管理员
     * createDate : 2018-05-03 10:51:31
     * remark :
     * imgUrl : null
     * concerned : null
     */
    /**
     * 状态-未分派.
     */
    public static final Integer STATE_UNASSIGNED = 5;
    /**
     * 状态-待处理.
     */
    public static final int STATE_UNPROCESS = 0;
    /**
     * 状态-处理中
     */
    public static final Integer STATE_PROCESSING = 1;
    /**
     * 状态-挂起.
     */
    public static final Integer STATE_HANGUP = 2;

    private int id;
    private String faultTypeName;
    private String severityTypeName;
    private int processingPersonId;
    private int severityType;
    private String processingPersonName;
    private String processingDate;
    private int state;
    private String faultNumber;
    private String createUser;
    private String createDate;
    private String remark;
    private String[] imgUrl;
    private String[] imgThumbnailUrl;
    private boolean concerned;//是否关注  1 关注
    private int equId;
    private String equName;
    private int orgId;
    private String orgName;
    private String stateName;
    private String processName;
    private String repairId;
    private String eqCode;

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }

    private String assignDate;

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    private String updateDate;
    private List<PictureEntity> pictureVideos;


    private int processed;

    private int lastRecordId;//缺陷填报必填
    /**
     * 1为已处理
     */
    private int processId;
    private int reported;//1为已报修
    private String patrolName;
    private List<String> pathList;

    public FaultManagerEntity() {
    }

    public List<String> getPathList() {
        if (pathList == null && imgUrl != null && imgUrl.length > 0) {
            pathList = new ArrayList<>();
            for (String url : imgUrl) {
                pathList.add(url);
            }
        }
        return pathList;
    }

    public List<PictureEntity> getPictureVideos() {
        return pictureVideos;
    }

    public void setPictureVideos(List<PictureEntity> pictureVideos) {
        this.pictureVideos = pictureVideos;
    }

    public int getProcessed() {
        return processed;
    }

    public void setProcessed(int processed) {
        this.processed = processed;
    }

    public String getPatrolName() {
        return patrolName;
    }

    public void setPatrolName(String patrolName) {
        this.patrolName = patrolName;
    }

    public String[] getImgThumbnailUrl() {
        return imgThumbnailUrl;
    }

    public void setImgThumbnailUrl(String[] imgThumbnailUrl) {
        this.imgThumbnailUrl = imgThumbnailUrl;
    }

    public int getReported() {
        return this.reported;
    }

    public void setReported(int reported) {
        this.reported = reported;
    }

    public String getProcessName() {
        if (processName != null) {
            processName = processName.replaceAll("null", "");
        }
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }


    public String getEquName() {
        if (!"null".equals(equName) && !TextUtils.isEmpty(equName)) {
            return equName;
        }
        return "";
    }

    public int getLastRecordId() {
        return lastRecordId;
    }

    public void setLastRecordId(int lastRecordId) {
        this.lastRecordId = lastRecordId;
    }

    public int getProcessingPersonId() {
        return processingPersonId;
    }

    public void setProcessingPersonId(int processingPersonId) {
        this.processingPersonId = processingPersonId;
    }

    public int getSeverityType() {
        return severityType;
    }

    public void setSeverityType(int severityType) {
        this.severityType = severityType;
    }

    public void setEquName(String equName) {
        this.equName = equName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFaultTypeName() {
        return faultTypeName;
    }

    public String getFaultTypeColor() {
        String color = "#ff0cabdf";
        if (TextUtils.isEmpty(faultTypeName)) {
            return color;
        } else if ("设备缺陷".equals(faultTypeName)) {
            color = "#ff0cabdf";
        } else if ("管理缺陷".equals(faultTypeName)) {
            color = "#fff5a623";
        } else if ("工艺缺陷".equals(faultTypeName)) {
            color = "#ff3781e8";
        } else if ("其他缺陷".equals(faultTypeName)) {
            color = "#ffed5050";
        }
        return color;
    }

    public void setFaultTypeName(String faultTypeName) {
        this.faultTypeName = faultTypeName;
    }

    public String getSeverityTypeName() {
        return severityTypeName != null ? severityTypeName : "";
    }

    public void setSeverityTypeName(String severityTypeName) {
        this.severityTypeName = severityTypeName;
    }

    public String getProcessingPersonName() {
        if (!TextUtils.isEmpty(processingPersonName)) {
            return processingPersonName;
        }
        return "";
    }

    public void setProcessingPersonName(String processingPersonName) {
        this.processingPersonName = processingPersonName;
    }

    public String getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(String processingDate) {
        this.processingDate = processingDate;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getFaultNumber() {
        return faultNumber;
    }

    public void setFaultNumber(String faultNumber) {
        this.faultNumber = faultNumber;
    }

    public String getCreateUser() {
        return createUser != null ? createUser : "";
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String[] getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String[] imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean getConcerned() {
        return concerned;
    }

    public void setConcerned(boolean concerned) {
        this.concerned = concerned;
    }

    public String getNewState() {
        if (state == 0) {
            return "待处理";
        } else if (state == 1) {
            return "处理中";
        } else if (state == 2) {
            return "挂起";
        } else if (state == 3) {
            return "关闭";
        } else if (state == 4) {
            return "完成";
        } else if (state == 5) {
            return "未分配";
        }
        return "";
    }

    public int getBackgroundResource() {
        if (state == 0) {
            return R.drawable.ic_untreated;
        } else if (state == 1) {
            return R.drawable.ic_fault_in_processing;
        } else if (state == 2) {
            return R.drawable.ic_hangup;
        } else if (state == 3) {
            return R.drawable.ic_close;
        } else if (state == 4) {
            return R.drawable.ic_complete;
        } else {
            return R.drawable.ic_distribution;
        }
    }

    public String getNewStateColor() {
        if (state == 0) {
            return "#f99a6b";
        } else if (state == 1) {
            return "#efb336";
        } else if (state == 2) {
            return "#125fa5";
        } else if (state == 3) {
            return "#e35c5c";
        } else if (state == 4) {
            return "#0cabdf";
        }
        return "#7C4E93";
    }


    public boolean canExcute() {
        /**
         * 如果是已关闭或者已完成的工单，不可执行.
         */
        if (STATE_CLOSE == getState() || STATE_COMPLETE == getState()) {
            return false;
        }
        boolean flag = true;
        /**
         * 当前工单不是自己的返回false
         */
//        if (!getCreateUser().equals(AppApplication.getInstance().getUserEntity().getName())) {
//            flag = false;
//        } else {
//            flag = true;
//        }
        /**
         * 如果执行人不是当前人的，不可执行
         */
        if (AppApplication.getInstance().getUserEntity().getId()
                != getProcessingPersonId()) {
            flag = false;
        }
        if (getProcessingPersonId() == 0 && getCreateUser().equals(AppApplication.getInstance().getUserEntity().getName())) {
            flag = true;
        }
        return flag;
    }

    public boolean canExcute2() {
        /**
         * 如果是已关闭或者已完成的工单，不可执行.
         */
        if (STATE_CLOSE == getState() || STATE_COMPLETE == getState()) {
            return false;
        }
        boolean flag = true;
        /**
         * 当前工单不是自己的返回false
         */
//        if (!getCreateUser().equals(AppApplication.getInstance().getUserEntity().getName())) {
//            flag = false;
//        } else {
//            flag = true;
//        }
        /**
         * 如果执行人不是当前人的，不可执行
         */
        if (AppApplication.getInstance().getUserEntity().getId()
                != getProcessingPersonId()) {
            flag = false;
        }
        return flag;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processeId) {
        this.processId = processeId;
    }

    public String getImgUrlStr() {
        String urlStr = null;
        JSONArray array = new JSONArray();
        if (imgUrl != null) {
            for (String item : imgUrl) {
                array.put(item);
            }
            urlStr = array.toString();
        }
        return urlStr;
    }

    /**
     * 缺陷状态已完成
     */
    public static final int STATE_COMPLETE = 4;
    /**
     * 缺陷状态已关闭
     */
    public static final int STATE_CLOSE = 3;

    /**
     * 已经报修过了
     */
    public static final int REPORTED = 1;

    /**
     * 已经处理过了
     */
    public static final int PROCESSED = 1;

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public int getEquId() {
        return equId;
    }

    public void setEquId(int equId) {
        this.equId = equId;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getEqCode() {
        return eqCode != null && !"null".equals(eqCode) ? eqCode : "";
    }

    public void setEqCode(String eqCode) {
        this.eqCode = eqCode;
    }
}