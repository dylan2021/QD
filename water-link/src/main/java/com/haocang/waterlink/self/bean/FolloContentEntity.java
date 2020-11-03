package com.haocang.waterlink.self.bean;

import android.text.TextUtils;

import com.haocang.base.utils.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

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
 * 创建时间：2018/3/2113:23
 * 修 改 者：
 * 修改时间：
 */
public class FolloContentEntity {


    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * id : 9
     * itemId : 21
     * name : 11
     * no : 2
     * status : 启用
     * maintain : 正常
     * process : 1#泵站
     * imgUrl : http://sh.hc-yun.com:6302/group1/M00/00/00/wKgeIVrUtTaAQRNyAAAWrCv_shw362.png
     * executorName : null
     * severity : null
     * dateTime : null
     * equNo : null
     * repairType : null
     */
    private String siteName;
    private int id;
    private int itemId;
    private String name;
    private String no;
    private String status;
    private String maintain;
    private String process;
    private String imgUrl;
    private String executorName;
    private String severity;
    private String dateTime;
    private String equNo;
    private String repairType;
    private Object type;
    private String inside;
    private String executeUserName;
    private String processingPersonName;
    private String orgName;
    private String value;
    private String unit;

    public String getMpointName() {
        return mpointName;
    }

    public void setMpointName(String mpointName) {
        this.mpointName = mpointName;
    }

    private String mpointName;

    public String getMpointId() {
        return mpointId;
    }

    public void setMpointId(String mpointId) {
        this.mpointId = mpointId;
    }

    private String mpointId;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return !TextUtils.isEmpty(unit) ? unit : "";
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDatadt() {
        return datadt;
    }

    public void setDatadt(String datadt) {
        this.datadt = datadt;
    }

    private String datadt;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }


    public String getProcessingPersonName() {
        return processingPersonName;
    }

    public void setProcessingPersonName(String processingPersonName) {
        this.processingPersonName = processingPersonName;
    }


    public String getExecuteUserName() {
        return executeUserName;
    }

    public void setExecuteUserName(String executeUserName) {
        this.executeUserName = executeUserName;
    }


    public String getInside() {
        return inside;
    }

    public void setInside(String inside) {
        this.inside = inside;
    }


    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public String getNewName() {
        String newName = name;
        if (!TextUtils.isEmpty(name) && name.length() > 8) {
            newName = name.substring(0, 8) + "...";
        }
        return newName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMaintain() {
        return maintain;
    }

    public void setMaintain(String maintain) {
        this.maintain = maintain;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getExecutorName() {
        return executorName != null ? executorName : "";
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getNewDateTime() {
        String time = "";
        if (!TextUtils.isEmpty(dateTime)) {
            try {
                time = converSr(TimeUtil.getTranData(dateTime));
            } catch (Exception e) {
                e.printStackTrace();
                return time;
            }
        }
        return time;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getEquNo() {
        return "null".equals(equNo) ? "" : equNo;
    }

    public void setEquNo(String equNo) {
        this.equNo = equNo;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }


    public static String converSr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        return sdf.format(date);
    }

}
