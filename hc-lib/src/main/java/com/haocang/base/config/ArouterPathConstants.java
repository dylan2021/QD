package com.haocang.base.config;

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
 * 创建时间：2018/5/7下午6:25
 * 修  改  者：
 * 修改时间：
 */
public class ArouterPathConstants {

    /**
     * 设备台账
     */
    public static final class Common {
        /**
         * common
         */
        public static final String COMMON_ACTIVY = "/common/activity";
        public static final String BUSSINESS_ACTIVY = "/common/bussiness/";
        public static final String PICTURE_PREVIEW = "/picture/pic";
    }

    /**
     * 设备台账
     */
    public static final class Equipment {
        /**
         * 设备列表
         */
        public static final String EQUIPMENT_LIST = "/equiment/list";
    }

    /**
     * 缺陷.
     */
    public static final class Fault {
        /**
         * 缺陷列表.
         */
        public static final String FAULT_LIST = "/fault/list";
        /**
         * 设备报修查看
         //         */
//        public static final String FAULT_LOOK_EQUIPMENT_REPAIR = "fault/look/equipment/repair";
//
//        /**
//         * 选择处理人界面
//         */
//        public static final String AROUTE_FAULT_POST_PROCESSING = "/fault/postprocessingpersons";
    }

    /**
     * 维修.
     */
    public static final class Repair {
        /**
         * 维修列表.
         */
        public static final String REPAIR_LIST = "/repair/list";

        /**
         * 维修详情.
         */
        public static final String REPAIR_DETAIL = "/repair/detail";

        /**
         * 查看备注.
         */
        public static final String REPAIR_LOOK_REMARK = "/repair/lookremark";

        /**
         * 缺陷记录详情.
         */
        public static final String REPAIR_FAULT_DETAIL = "/repair/repairfault/detail";

        /**
         * 缺陷现场记录
         */
        public static final String REPAIR_FAULT_SENCES = "/repair/repairfault/sence";

        /**
         * 维修处理进度
         */
        public static final String REPAIR_PROGRESS = "/repair/progress";

        /**
         * 维修处理进度详情(维修详情)
         */
        public static final String REPAIR_PROGRESS_DETAIL = "/repair/progress/detail";

        /**
         * 填写维修结果
         */
        public static final String REPAIR_POST_RESULT = "/repair/post/result";
        /**
         * 维修结果填写备注
         */
        public static final String POST_REMARK = "/repair/post/remark";

    }

    /**
     * 巡检.
     */
    public static final class Patrol {
        /**
         * 巡检列表.
         */
        public static final String PATROL_LIST = "/patrol/list";
        /**
         * 巡检列表.
         */
        public static final String PATROL_RESULT = "/patrol/result";

        /**
         * 厂外巡检
         */
        public static final String PATROL_OUTSIDE = "/patrol/outside";
    }

    /**
     * 曲线.
     */
    public static final class Curve {
        /**
         * 收藏曲线.
         */
        public static final String CURVE_COLLECTION = "/curve/collection";

        /**
         * 曲线列表.
         */
        public static final String CURVE_POINT_LIST = "/curve/list";

        /**
         * 数据分享
         */
        public static final String CURVE_SHARE = "/curve/share";

        /**
         * 曲线主界面.
         */
        public static final String CURVE_MAIN = "/curve/main";
        /**
         * 添加收藏
         */
        public static final String CURVE_ADD_COLLECTION = "/curve/add/collection";

        public static final String CURVE_SHAPRE_SETUP = "/curve/share/setup";
        /**
         * 选择站点
         */
        public static final String CURVE_SITE = "/curve/selection";
    }

    /**
     * 数据监视.
     */
    public class Monitor {
        /**
         * 监视画面主界面.
         */
        public static final String MONITORMAIN = "/monitor/main";
        /**
         * 选择监视画面.
         */
        public static final String MONITOR_SELECT_SCREEN = "/monitor/select/screen";
        public static final String MONITOR_MAIN_PICTURE = "/monitor/main/picture";
    }

    /**
     * 报警.
     */
    public class Alarm {
        public static final String ALARMMAIN = "/alarm/main";
        /**
         * 解除报警
         */
        public static final String ALARMRELEASE = "/alarm/release";
        /**
         * 解除组合报警
         */
        public static final String ALARM_GROUP_RELEASE = "/alarm/group/release";
        public static final String ALARMDETAIL = "/alarm/detail";
    }

    /**
     * 消息.
     */
    public class Message {
        /**
         * 消息Service
         */
        public static final String MESSAGE_SERVICE = "/message/service";
    }

    /**
     * 养护模块
     */
    public static final class Maintain {
        public static final String MAINTAIN_WORKER_LIST = "/maintain/list";
    }
}
