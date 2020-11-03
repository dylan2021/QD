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
 * 创建时间：2018/1/2310:43
 * 修 改 者：
 * 修改时间：
 */
public class MethodConstantsOld {

    /**
     * 登陆模块
     */
    public static final class Login {
        public static final String LOGIN = "auth/login";

        public static final String PERSONAL = "uaa/api/account"; //个人信息

        public static final String USER_IMGURL = "uaa/api/user/imgurl";

        public static final String VERIFYCATION_CODE = "base/api/verifycation/getVerifycationCode";//获取验证码

        public static final String VERIFY_PHONE_NUMBER = "base/api/verifycation/verifyTelphone";//验证手机号码.

        public static final String UPDATE_PASSWORD = "base/api/verifycation/updatePassword";//修改密码

        public static final String NEWPASSWORD = "uaa/api/user/new-password";//首次登录修改密码
    }

    /**
     * 设备台账
     */
    public static final class Equiment {
        public static final String EQUIMENT_LIST = "equipment/api/equipments";//台账列表

        public static final String EQUIMENT_DETAILS = "equipment/api/equipment/equDetail";//台账详情

        public static final String EQUIPMENT_FILE_UPLOAD = "equipment/api/equipment/imgurl/";//设备台账图片上传

//        public static final String FILE_UPLOAD = "base/api/file/uploadEx";//文件上传
        public static final String FILE_UPLOAD = "zuul/base/api/file/uploadEx";//文件上传

//        public static final String BASE_BATCH_UPLOAD = "base/api/file/batch-upload";//上传多个文件
        public static final String BASE_BATCH_UPLOAD = "zuul/base/api/file/batch-upload";//上传多个文件

    }

    /**
     * 我的
     */
    public static final class Self {

        public static final String NEWPASSWORD = "uaa/api/user/change-password";

    }

    public static final class Uaa {
        /**
         * 获取指定组织下的人，例如获取巡检下面的指派人列表
         */
        public static final String USER_LIST = "uaa/api/user/allocate-patrol";

        public static final String BASE_CONCERN = "base/api/concern";//新增关注

        public static final String BASE_CONCERN_LIST = "base/api/concern/list";//关注列表

        public static final String BASE_TASKS = "base/api/tasks";//获取当前用户任务列表

        public static final String BASE_PROCESSE = "uaa/api/process/list";//工艺位置列表;
        /**
         * 菜单权限
         */
        public static final String MENUS_APP = "uaa/api/app-menus";


    }

    /**
     * 获取消息
     */
    public static final class Messge {

        public static final String MESSAGE_LIST = "message/api/messagelist";//获取消息列表

        public static final String UNREAD_MESSAGE = "message/api/unreadmessage";//获取未读消息

        public static final String MESSAGE_DETAILS = "message/api/messageDetails";//获取消息详情

        public static final String MESSAGE_UNREAD = "message/api/message/unread-flag";//获取到tab是否有未读消息

    }

    public static final class Patrol {
        /**
         * 巡检列表 get
         */
        public static final String PATROL_LIST = "patrol/api/patrol-task-app/list";
        /**
         * get
         * 获取任务详情
         */
        public static final String PATROL_DETAIL = "patrol/api/patrol-task/";
        /**
         * 提交巡检点以及巡检步骤
         */
        public static final String PATROL_TASKPOINT_UPDATEALL = "patrol/api/patrol-task-point-step/update-all";
        /**
         * 获取任务结果
         */
        public static final String PATROL_TASK_RESULT = "patrol/api/patrol-task/result";
        /**
         * 扫码
         */
        public static final String PATROL_TASK_SCAN_QRCODE = "patrol/api/patrol-task-point/scan-qrcode";
        /**
         * put
         * 修改任务，结束巡检和任务分配都调用都这个接口
         */
        public static final String PATROL_TASK_UPDATE = "patrol/api/patrol-task";

        /**
         * get
         * 查询指定巡检任务详情APP
         */
        public static final String PATROL_TASK_DETAIL = "patrol/api/patrol-task-app";

        /**
         * get
         * 查询指定巡检点详情
         */
        public static final String PATROL_TASK_POINT_DETAIL = "patrol/api/patrol-task-point-step";


        /**
         * post
         * 上传轨迹
         */
        public static final String PATROL_TASK_UPLOADTRACE = "patrol/api/patrol-task/actual-path";
//        public static final String PATROL_TASK_UPLOADTRACE = "patrol/api/patrol-task/actual-path";

        public static final String PATROL_FINISH_TASK = "patrol/api/patrol-task/end-task";

    }

    /**
     * 缺陷
     */
    public static final class Fault {

        public static final String FAULT_ADD = "equipment/api/fault";//创建一个缺陷单

        public static final String FAULT_ALL_LIST = "equipment/api/app/faults";//查询缺陷单列表

        public static final String FAULT_DETAILS = "equipment/api/fault/app/faultdetails-info";//缺陷单详情

        public static final String FAULT_REPARI_EQU = "equipment/api/repair/equ-repair";//设备报修

        public static final String FAULT_ASSIGN = "equipment/api/fault/task-assign";//任务分派

        public static final String FAULT_RECORD_ASSIGN = "equipment/api/fault/task-record-assign";//缺陷处理填报

        public static final String FAULT_PROGRESS = "equipment/api/fault/fault-progress";//缺陷流程列表

        public static final String FAULT_RECORD_DETAIL = "equipment/api/fault/fault-record-detail";//流程节点详情

    }

    /**
     * 维修
     */
    public static final class Repair {
        /**
         * 维修列表.
         */
        public static final String REPAIR_LIST = "equipment/api/App/repairs";

        /**
         * 维修详情.
         */
        public static final String REPAIR_DETAIL = "equipment/api/App/repair";

        /**
         * 维修进度.
         */
        public static final String REPAIR_PROGRESS = "equipment/api/repair/repair-progress";

        /**
         * 维修进度.
         */
        public static final String REPAIR_PROGRESS_DETAIL = "equipment/api/App/repair-contrail-detail";

        /**
         * 维修进度.
         */
        public static final String REPAIR_REPORT_DETAIL = "equipment/api/App/repair-recod-detail";

        /**
         * 维修分派.
         */
        public static final String ASSIGN_TASK = "equipment/api/repair/task-assign";
        /**
         * 提交结果.
         */
        public static final String REPAIR_REPORT_RESULT = "equipment/api/repair/report-fill";
    }

    /**
     * 基礎信息
     */
    public static final class Base {
        /**
         * 获取任务列表.
         */
        public static final String BASE_TASK_LIST = "base/api/tasks";

        /**
         * 获取首页待办任务.
         */
        public static final String BASE_TASKS_TODO = "base/api/tasks/todo";

        /**
         * 意见反馈.
         */
        public static final String BASE_FEEDBACK = "base/api/feedback";

        /**
         * 首页kpi数据.
         */
        public static final String BASE_KPI = "base/api/kpi";

        /**
         * 获取版本地址
         */
        public static final String VERSION_UPDATE = "base/api/appHistorys/latest-version";

        public static final String GET_PATCH = "base/api/appHistorys/patch";
    }

    public static final class Curve {
        /**
         * 测点列表.
         */
        public static final String CURVE_POINT_LIST = "loong/api/chart/mpoints";
        /**
         * 数据类型.
         */
        public static final String CURVE_POINT_DATACATEGORYS = "loong/api/dataCategory/dataCategorys";
        /**
         * 获取收藏的数据组合.
         */
        public static final String GET_COMBINES = "loong/api/chart/app/combines";
        /**
         * 获取收藏的单组合.
         */
        public static final String GET_SIGNLE_COLLECTION = "loong/api/chart/chart";
        /**
         * post 保存单数据曲线.
         */
        public static final String COLLECT_SINGLE_CURVE = "loong/api/chart/charts";
        /**
         * post 保存组合曲线.
         */
        public static final String COLLECT_MULTI_CURVE = "loong/api/chart/combine";
        /**
         * 获取曲线数据
         */
        public static final String GET_CURVE_DATA = "loong/api/chart/app/data";
        /**
         * 曲线分享.
         */
        public static final String CURVE_SHARE = "loong/api/chart/share";
    }

    public class Monitor {
        /**
         * 监视画面，获取数据列表.
         */
        public static final String GET_MONITOR_DATA = "loong/api/screen/screen/mpoints";
        /**
         * 监视画面，获取报警列表.
         */
        public static final String GET_MONITOR_ALARM = "loong/api/screen/alarm";

        /**
         * 获取监视画面列表.
         */
        public static final String GET_MONITOR_SCREENS = "loong/api/screen/screens";
        /**
         * 获取监视画面详情.
         */
        public static final String GET_MONITOR_DETAIL = "loong/api/screen/screen";
    }

    public class Alarm {
        /**
         * 报警列表
         */
        public static final String GET_ALARM_RECORD = "loong/api/alarm-record/records";

        /**
         * 获取报警条件.
         */
        public static final String GET_ALARM_CONDITION = "loong/api/alarm-record/detail";
        /**
         * 报警详情
         */
        public static final String GET_ALARM_DETAIL = "loong/api/alarm-record/detail";

        /**
         * 解除报警
         */
        public static final String GET_RELEASE = "loong/api/alarm-record/release";
    }

    /**
     * 保养
     */
    public static final class Maintain {
        /**
         * 任务-查询所有保养任务
         */
        public static final String MAINTAIN_LIST = "equipment/api/maintains/tasks";
        /**
         * 任务-查询指定保养任务_明细信息
         */
        public static final String MAINTAIN_DETAIL_LIST = "equipment/api/maintain/task/detail";

        /**
         * 任务-app端执行一条或多条任务明细
         */
        public static final String MAINTAIN_PUT_CONFIRM = "equipment/api/maintains/task/app-execute";

        /**
         * 任务-任务转派确认接收
         */
        public static final String MAINTAIN_SENT_RECEIVE = "equipment/api/maintains/task/transfer-confirm";

        /**
         * 查询所有用户
         */
        public static final String MAINTAIN_ALL_PEOPLE = "uaa/api/users";

        /**
         * 任务-任务转派
         */
        public static final String MAINTAIN_TASK_TRANSFER = "equipment/api/maintains/task/transfer";

        /**
         * 查询权限内所有工艺位置（填充了父节点）uaa/api/processes
         */
        public static final String MAINTAIN_ALL_AREA = "uaa/api/processes";

        /**
         * 任务-获取保养任务设备类型
         * /api/maintain/task/{id}/equipTypes
         */
        public static final String MAINTAIN_DEVICE_TYPE = "equipment/api/maintain/task/{id}/equipTypes";
    }

}

