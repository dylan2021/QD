package com.haocang.patrol.manage.bean;

import java.util.Date;

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
 * 创建时间：2018/4/12下午1:21
 * 修  改  者：
 * 修改时间：
 */
public class PatrolConditionEvent {
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
    /**
     * 执行状态
     */
    private String excuteStatus;

    private Date curDate;

    private String curDateStr;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getExcuteStatus() {
        return excuteStatus;
    }

    public void setExcuteStatus(String excuteStatus) {
        this.excuteStatus = excuteStatus;
    }

    public PatrolConditionEvent() {

    }

    public Date getCurDate() {
        return curDate;
    }

    public void setCurDate(Date curDate) {
        this.curDate = curDate;
    }

    public String getCurDateStr() {
        return curDateStr;
    }

    public void setCurDateStr(String curDateStr) {
        this.curDateStr = curDateStr;
    }
}
