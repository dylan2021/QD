package com.haocang.base.bean;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区龙兰路277号一号楼9楼
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/10/26 11:26
 * 修 改 者：
 * 修改时间：
 */
public class CalendarViewEntity {
    private String dateTime;

    private boolean isAlarm;

    private boolean today;

    private String showTime;

    private String thumbdt;

//    private String alarmCount;

    private String selectDate;

    /**
     * 定时抓拍数量.
     */
    private String timerCount;

    /**
     * 报警抓拍数量.
     */
    private String alarmCount;

    private boolean read;

    public String getSelectDate() {
        return selectDate;
    }

    public void setSelectDate(String selectDate) {
        this.selectDate = selectDate;
    }


    public String getThumbdt() {
        return thumbdt;
    }

    public void setThumbdt(String thumbdt) {
        this.thumbdt = thumbdt;
    }

//    public String getAlarmcount() {
//        return alarmcount;
//    }
//
//    public void setAlarmcount(String alarmcount) {
//        this.alarmcount = alarmcount;
//    }


    public String getDateTime() {
        return dateTime;
    }


    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public boolean isAlarm() {
        return isAlarm;
    }

    public void setAlarm(boolean alarm) {
        isAlarm = alarm;
    }

    public boolean isToday() {
        return today;
    }

    public void setToday(boolean today) {
        this.today = today;
    }


    public boolean getRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getTimerCount() {
        return timerCount;
    }

    public void setTimerCount(String timerCount) {
        this.timerCount = timerCount;
    }

    public String getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(String alarmCount) {
        this.alarmCount = alarmCount;
    }
}
