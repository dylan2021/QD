package com.haocang.base.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题：时间转换工具类
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/5/20下午1:58
 * 修  改  者：
 * 修改时间：
 */
public class TimeTransformUtil {

    /**
     * @return 获取当前所在时间和0时区的差值.
     */
    public static int getTimeOffset() {
        TimeZone oldZone = TimeZone.getDefault();
        TimeZone newZone = TimeZone.getTimeZone("GMT");
        return oldZone.getRawOffset() - newZone.getRawOffset();
    }

    /**
     * @param time 时间.
     * @return
     */
    public static String getShowLocalTime(final String time) {
        String showTime = time;
        TimeZone zone = TimeZone.getDefault();
        if (!TextUtils.isEmpty(time)) {
            showTime = time.replaceAll("T", " ").replaceAll("Z", "");
        } else {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(showTime);
            date = new Date(date.getTime() + getTimeOffset());
            showTime = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return showTime;
    }

    /**
     * @param time 时间.
     * @return
     */
    public static String getShowLocalTime3(final String time) {
        String showTime = time;
        TimeZone zone = TimeZone.getDefault();
        if (!TextUtils.isEmpty(time)) {
            showTime = time.replaceAll("T", " ").replaceAll("Z", "");
        } else {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(showTime);
            date = new Date(date.getTime() + getTimeOffset());
            showTime = sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return showTime;
    }

    /**
     * @param time 时间.
     * @return
     */
    public static String getShowLocalTime2(final String time) {
        String showTime = time;
        TimeZone zone = TimeZone.getDefault();
        if (!TextUtils.isEmpty(time)) {
            showTime = time.replaceAll("T", " ").replaceAll("Z", "");
        } else {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date = sdf.parse(showTime);
            date = new Date(date.getTime() + getTimeOffset());
            showTime = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return showTime;
    }

    public static String getShowLocalDate(final String time) {
        String showTime = time;
        TimeZone zone = TimeZone.getDefault();
        if (!TextUtils.isEmpty(time)) {
            showTime = time.replaceAll("T", " ").replaceAll("Z", "");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(showTime);
            date = new Date(date.getTime() + getTimeOffset());
            showTime = sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return showTime;
    }

    /**
     * @return
     */
    public static String getShowLocalDate(final Date date) {
        Date showDate = new Date(date.getTime() + getTimeOffset());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(showDate);
    }

    /**
     * @return
     */
    public static String getShowLocalTimeHHMM(final Date date) {
        Date showDate = new Date(date.getTime() + getTimeOffset());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(showDate);
    }


    /**
     * @param time 时间
     * @return
     */
    public static String getUploadGMTTime(final String time) {
        String showTime = time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(showTime);
            date = new Date(date.getTime() - getTimeOffset());
            showTime = sdf.format(date);
            showTime = showTime.replaceAll(" ", "T");
            showTime = showTime + "Z";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return showTime;
    }

    public static String getUploadUNGMTTime(final String time) {
        String showTime = time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(showTime);
            date = new Date(date.getTime() - getTimeOffset());
            showTime = sdf.format(date);
            showTime = showTime.replaceAll(" ", "T");
            showTime = showTime + "Z";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return showTime;
    }

}
