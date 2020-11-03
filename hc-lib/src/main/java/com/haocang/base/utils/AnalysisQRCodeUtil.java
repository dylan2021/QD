package com.haocang.base.utils;

import android.text.TextUtils;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区龙兰路277号一号楼9楼
 * 网址：http://www.haocang.com/
 * 标 题：  二维码处理返回  id
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/5/16 15:39
 * 修 改 者：
 * 修改时间：
 */
public class AnalysisQRCodeUtil {
    private static final String EUIPMENT = "EQ";//设备
    private static final String PATROLPOINT = "PP";//巡检点
    private static final String PROCESS = "PR";//工艺位置

    /**
     * 二维码类型为设备.
     */
    public static final String TYPE_EQUIPMENT = "equipment";
    /**
     * 二维码类型为巡检.
     */
    public static final String TYPE_PATROL = "patrol";
    /**
     * 二维码类型为工艺.
     */
    public static final String TYPE_PROCESS = "process";
    public static final String TYPE_NONE = "none";

    public static String getQRCodeType(final String qrCode) {
        String type = TYPE_NONE;
        if (qrCode != null && qrCode.length() > 2) {
            if (qrCode.startsWith(EUIPMENT)) {
                type = TYPE_EQUIPMENT;
            } else if (qrCode.startsWith(PATROLPOINT)) {
                type = TYPE_PATROL;
            } else if (qrCode.startsWith(PATROLPOINT)) {
                type = TYPE_PATROL;
            } else if (qrCode.startsWith(PROCESS)) {
                type = TYPE_PROCESS;
            }
        }
        return type;
    }

    /**
     * 解析设备台账二维码
     *
     * @param id
     * @return
     */
    public static int getEquipmentId(final String id) {

        if (!TextUtils.isEmpty(id) && id.indexOf(EUIPMENT) == 0) {
            return Integer.parseInt(id.replace(EUIPMENT, ""));
        } else {
            return -1;
        }
    }

    /**
     * 解析巡检点二维码
     *
     * @param id
     * @return
     */
    public static int getPatrolPointId(String id) {
        if (!TextUtils.isEmpty(id) && id.indexOf(PATROLPOINT) == 0) {
            return Integer.parseInt(id.replace(PATROLPOINT, ""));
        } else {
            return -1;
        }
    }

    /**
     * 解析工艺位置二维码
     *
     * @param id
     * @return
     */
    public static int getProcessId(String id) {
        if (!TextUtils.isEmpty(id) && id.indexOf(PROCESS) == 0) {
            return Integer.parseInt(id.replace(PROCESS, ""));
        } else {
            return -1;
        }
    }
}
