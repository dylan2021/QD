package com.haocang.maonlib.base.config;

import com.haocang.base.config.LibConstants;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼                                     2001
 * 网址：http://www.haocang.com/
 * 标        题：
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/7/25下午3:08
 * 修  改  者：
 * 修改时间：
 */


public class HCLicConstant {

//    public static final String ADDRESS_IP = "https://mg.hc-yun.com:443/";//正式库
//    public static final String ADDRESS_IP = "https://sh.hc-yun.com:22001/";
//    public static final String ADDRESS_IP = "http://123.235.49.242:18105/";

//    public static final String ADDRESS_IP = "http://122.114.2.195:16901/";//蓝鸟
    //    public static final String ADDRESS_IP = "http://122.114.164.168:6111/";
    public static final String AR_KEY = "ZOLNOWTFOIwZoDi0WMWFq7zGJojQbEpT7yJfiGcdwUdeL7iUCXaQ6k5ENbFADQRfZzqKKs3PferLPtZAdqgcslCKJZnqvmUV0u8tae80eg9girlPqcEOqW8qHZCbeqTJlcSKMyrXyhxdyJ8rp4XWWf11t46UaTeUAVQsmEguQsTZodI02l7b6Dm4g7TaeuJMufeaxKg3";

    public static void setAddressIp() {
        LibConstants.setAddressIp(LibConstants.ADDRESS_IP);
    }

    public static void setAddressIp(String ip) {
        LibConstants.setAddressIp(ip);
    }



    public static String JUMP_STATIC = "1";

}
