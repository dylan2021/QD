package com.haocang.waterlink.constant.bean;

import com.haocang.base.config.AppApplication;

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
 * 创建时间：2018/7/3下午6:23
 * 修  改  者：
 * 修改时间：
 */
public class HomeConstants {

    //    public static String MENU_EQU = "equ";
//    public static String MENU_PATROL = "patrol";
//    public static String MENU_FAULT = "fault";
//    public static String MENU_REPAIR = "repair";
//    public static String MENU_
    /**
     * 获取天气时，看是否取到位置的时间间隔
     */
    public static final int CHECK_LOCATION_INTERVAL = 3000;
    public static final String WHEATHER = "weather";
    public static final String IS_DAY = "isday";

    public static class ArouterPath {
        public static final String FOLLOW_MAIN = "/follow/main";
        public static final String MY_INFO = "/my/info";
        public static final String HOME_TASK = "/task/list";
    }

    public static class MethodPath {
        public static final String HOME_DATA = "portal/api/module/mpoints/default";

        public static final String HOME_SUBSCRIPTION = "loong/api/mpoint-datas/subscription";//获取订阅路径
    }

    public static class HomeSetupKey {
        public static String HOME_DATA;
        public static String HOME_TASK;
        public static String HOME_KPI;
    }


}
