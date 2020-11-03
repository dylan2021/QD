package com.haocang.repair.progress.bean;

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
 * 创建时间：2018/5/14上午11:23
 * 修  改  者：
 * 修改时间：
 */

import android.text.TextUtils;

import com.haocang.base.utils.TimeUtil;

import java.util.Date;
import java.util.List;

/**
 * Copyright &copy; 2017-2027 hcsystem All rights reserved.
 */

public class ProcessingProgressVo {

    /**
     * id,维修id
     */
    private Integer id;
    /**
     * currState,处理进度id
     */
    private Integer currState;
    /**
     * currStateName,当前状态
     */
    private String currStateName;
    /**
     * 处理进度
     */
    private List<ProcessingProgress> processingProgressList;

    public static class ProcessingProgress {

        /**
         * 维修记录id.
         */
        private Integer repairRecordId;
        /**
         * 时间.
         */
        private String time;
        /**
         * 处理人.
         */
        private String processingPersonName;
        /**
         * 处理进度.
         */
        private String processingResult;

        /**
         * 轨迹ID.
         */
        private Integer repairContrailId;
        /**
         * 下一个处理人
         */
        private String nextProcessingPersonName;
        /**
         * -1:缺陷申报,0：未处理；1：处理中；2：挂起；3:关闭；4：完成
         */
        private Integer processResult;


        /**
         * @return the repairRecordId
         */
        public Integer getRepairRecordId() {
            return repairRecordId;
        }

        /**
         * @param repairRecordId the repairRecordId to set
         */
        public void setRepairRecordId(final Integer repairRecordId) {
            this.repairRecordId = repairRecordId;
        }

        /**
         * @return the time
         */
        public String getTime() {
            return time;
        }

        /**
         * @param time the time to set
         */
        public void setTime(final String time) {
            this.time = time;
        }

        public String getShowTime() {
            String showTime = time;
            if (!TextUtils.isEmpty(time)) {
                Date date = TimeUtil.getDateTimeWithoutSpace(TimeUtil.minusDateTime(time));
                showTime = TimeUtil.converStr_MMDD(date);
            }
            return showTime;
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
        public void setProcessingPersonName(final String processingPersonName) {
            this.processingPersonName = processingPersonName;
        }

        /**
         * @return the processingResult
         */
        public String getProcessingResult() {
            return processingResult;
        }

        /**
         * @param processingResult the processingResult to set
         */
        public void setProcessingResult(final String processingResult) {
            this.processingResult = processingResult;
        }

        /**
         * @return the nextProcessingPersonName
         */
        public String getNextProcessingPersonName() {
            return nextProcessingPersonName;
        }

        /**
         * @param nextProcessingPersonName the nextProcessingPersonName to set
         */
        public void setNextProcessingPersonName(final String nextProcessingPersonName) {
            this.nextProcessingPersonName = nextProcessingPersonName;
        }

        /**
         * @return the processResult
         */
        public Integer getProcessResult() {
            return processResult;
        }

        /**
         * @param processResult the processResult to set
         */
        public void setProcessResult(final Integer processResult) {
            this.processResult = processResult;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "ProcessingProgress [repairRecordId=" + repairRecordId + ", time=" + time + ", processingPersonName="
                    + processingPersonName + ", processingResult=" + processingResult + "]";
        }

        public boolean hasDetail() {
            boolean hasDetail = true;
            if (processResult == -1 || processResult == 0) {
                hasDetail = false;
            }
            return hasDetail;
        }

        public Integer getRepairContrailId() {
            return repairContrailId;
        }

        public void setRepairContrailId(Integer repairContrailId) {
            this.repairContrailId = repairContrailId;
        }
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * @return the currState
     */
    public Integer getCurrState() {
        return currState;
    }

    /**
     * @param currState the currState to set
     */
    public void setCurrState(final Integer currState) {
        this.currState = currState;
    }

    /**
     * @return the currStateName
     */
    public String getCurrStateName() {
        return currStateName;
    }

    /**
     * @param currStateName the currStateName to set
     */
    public void setCurrStateName(String currStateName) {
        this.currStateName = currStateName;
    }

    /**
     * @return the processingProgressList
     */
    public List<ProcessingProgress> getProcessingProgressList() {
        return processingProgressList;
    }

    /**
     * @param processingProgressList the processingProgressList to set
     */
    public void setProcessingProgressList(List<ProcessingProgress> processingProgressList) {
        this.processingProgressList = processingProgressList;
    }

}
