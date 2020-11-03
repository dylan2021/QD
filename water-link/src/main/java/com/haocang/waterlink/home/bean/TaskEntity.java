package com.haocang.waterlink.home.bean;

import android.text.TextUtils;

import com.haocang.base.utils.TimeTransformUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
 * 创建时间：2018/1/1715:27
 * 修 改 者：
 * 修改时间：
 */
public class TaskEntity {
    private String name;
    private String showTime;
    private String status;
    private String statusName;
    private String equName;
    private int id;
    private String startTime;
    private String endTime;
    private String patrolType;
    private String no;
    private int type;//任务类别（ 1：巡检；2：缺陷；3：维修；4：养护）

    public String getEquName() {
        return equName;
    }

    public void setEquName(String equName) {
        this.equName = equName;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPatrolType() {
        return patrolType;
    }

    public void setPatrolType(String patrolType) {
        this.patrolType = patrolType;
    }


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


    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShowTime() {
        return showTime != null ? TimeTransformUtil.getShowLocalTime2(showTime) : showTime;
    }

    public String getShowTime2() {
        return showTime != null ? TimeTransformUtil.getShowLocalTime3(showTime) : showTime;
    }


    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
