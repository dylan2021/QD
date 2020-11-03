package com.haocang.base.config;

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
 * 创建时间：2018/1/1010:38
 * 修 改 者：
 * 修改时间：
 */
public class LibConstants {

    public static String ADDRESS_IP = "http://www.qdhsdd.com:18105/";
    public static String WS_ADDRESS_IP = ADDRESS_IP + "websocket/websocket";
//    public static String ADDRESS_IP = "http://kaifa.hc-yun.com:31013/"; //访问api的端口
//    public static String WS_ADDRESS_IP = "wss://ws.mg.hc-yun.com:443/websocket/websocket";// websocket

//    public static String ADDRESS_IP = "http://sh.hc-yun.com:22001/"; //访问api的端口
//    public static final String ADDRESS_IP = "http://mg.hc-yun.com:80/"; //访问api的端口
//    public static final String ADDRESS_IMAGE_IP = "http://dev3.haocang.com:11011/"; //访问图片的端口
//    public static String ADDRESS_IP = "http://117.50.70.211:31015/";

    //    public static final String HC_API_TEST_IP = "http://dev3.haocang.com:11006/"; //昊沧开发环境访问api的端口
//    public static final String HC_API_TEST_IP_TWO = "http://sh.hc-yun.com:6229/"; //昊沧测试环境访问api的端口
//    public static final String HT_API_TEST_IP = "http://sh.hc-yun.com:6332/"; //昊昙开发环境访问api的端口
    //    public static final String ADDRESS_IP = HC_API_TEST_IP_TWO;

    public static String SHARE_IP = ADDRESS_IP;
//    public static String SHARE_IP = "http://sh.hc-yun.com:22001/";

    public static void setAddressIp(String addressIp) {
        ADDRESS_IP = addressIp;
        SHARE_IP = addressIp;
    }

    public static void setWsAddressIp(String wsAddressIp) {
        WS_ADDRESS_IP = wsAddressIp;
    }

    public static void setShareIp(String shareIp) {
        SHARE_IP = shareIp;
    }

    public static final class Patrol {
        /**
         * 上传巡检轨迹间隔事件.
         */
        public static final int UPLOAD_PATROL_TRACE_INTERVAL = 30 * 1000;

        /**
         * 检查是否接近巡检点的间隔时间.
         */
        public static final int CHECK_ARRIVE_POINT_INTERVAL = 30 * 1000;
    }

    /**
     * 缺陷
     */
    public static final class Fault {

        /**
         * 选择处理人请求码
         */
        public static final int PICKPERSON_REQUEST_CODE = 2001;

        /**
         * 选择处理人返回码
         */
        public static final int PICKPERSON_RESULT_CODE = 2002;

    }

    /**
     * 基础模块常量定义
     */
    public static final class Base {
        /**
         * 退出程序时弹出"再按一次退出程序"提示间隔时间.
         */
        public static final int EXIT_PROGRAM_TIP_INTERVAL = 2000;

        /**
         * 图片列表列数.
         */
        public static final int PICTURE_ADAPTER_COLUMN_COUNT = 3;

        /**
         * 备注长度.
         */
        public static final int REMARK_MAX_LENGTH = 200;
    }

    /**
     * 维修常量定义
     */
    public static final class Repair {
        /**
         * 拍照.
         */
        public static final int TASK_PICTURE_REQUEST_CODE = 12301;

        /**
         * 最多选择照片数量.
         */
        public static final int MAX_PIC_NUM = 6;
    }

    public static final class SharedPreferenceKey {
        public static final String COOKIE = "http-cookies";
    }

    public static final class Maintain {
        public static final int MAINTAIN_ALL_PEOPLE_REQUEST = 4004;

        public static final int MAINTAIN_ALL_PEOPLE_RESULT_OK = 4005;

        public static final int MAINTAIN_DETAIL_FILTER_REQUEST = 5004;
        public static final int MAINTAIN_DETAIL_FILTER_RESULT_OK = 5005;

    }

}
