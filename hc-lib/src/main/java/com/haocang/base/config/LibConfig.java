package com.haocang.base.config;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.haocang.base.R;

import java.util.HashMap;
import java.util.Map;

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
public class LibConfig {

    /**
     * 登陆相关.
     */
    public static final String CHECK = "remember ";         // 创建一个remember的xml文件
    /**
     * 用户名
     */
    public static final String USERNAME = "users";
    /**
     *
     */
    public static final String PASSWORD = "hcpassword";

    public static final String HOME_SETUP = "homesetup";//首页设置是否显示xml
    //    public static final String VOICE
    public static final String HOME_TASK = "task";
    public static final String HOME_KPI = "kpi";

    public static int VOICE_DIALOG_REFRESHTIME = 500;

    public static boolean IsControlMenu = false;//是否有下控权限

    public static String accessToken = "";

    public static String sessionToken = "";

    public static String weather;

    public static String temperature;

    /**
     * 语音参数设置
     */
    /**
     * 主要调节这个参数，调大，大于200之后，比较难触发，小于150会很灵敏
     */
    public static int CONTINUITY_INTERVAL = 300;//  continuityInterval = 200;
    /**
     *
     */
    public static int CONTINUITY_MAX_INTERVAL = 1000;//  continuityInterval = 200;

    public static int SHAKE_INTERVAL = 1500;

    public static int DEFAULT_THRESHHOLD = 16;

    /**
     * 语音灵敏度设置
     */
    public static String VOICER_THRESHHOLD_KEY = "voicer_set";


    public static class SP_KEY {
        public static String VOICER = "voicer";
    }

    public static void startMessageService(Context ctx) {
        try {
            Class<?> classType = Class.forName(ctx.getString(R.string.service_name));
            Intent intent = new Intent(ctx, classType.getClass());
            ctx.startService(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static String cookie = "";

    public static Map<String, Object> map = new HashMap<>();//todo 报表登录的请求头存储

    public static void addReportHeads(Map<String, Object> headsMap) {
        map.clear();
        map.putAll(headsMap);

    }

    /**
     * 版本更新.
     */
    public static boolean checkUpdateFlag = false;

    /**
     * 文件下载路径.
     */
    public static final String FILE_PATH = "/Mango/";                 // 文件夹路径
    public static final String APP_NAME = "Mango.apk";//下载后apk名称
    public static final String NEWAPK_FILE_PATH = "/Mango2/newapk/";
    public static final String MR_FILE_PATH = "/Mango/MR/";                 // 文件夹路径

    /**
     * 图片选择然后绑定适配器.
     */
    public static final int MAXIMAGE = 4;
    public static final String FILE_DIR_NAME = "com.kuyue.wechatpublishimagesdrag";//应用缓存地址
    public static final String FILE_IMG_NAME = "images";//放置图片缓存
    public static final int SCAN_CODE = 10001;

    /**
     * 权限.
     */
    public static final String STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;//存储权限
    public static final String CAMERA = Manifest.permission.CAMERA;//相机权限
    public static final String AUDIO = Manifest.permission.RECORD_AUDIO;//录音权限
    public static final String LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;//位置权限
    public static final String SETTINGS = Manifest.permission.WRITE_SETTINGS;
    public static final String CHANGECONFIGURATION = Manifest.permission.CHANGE_CONFIGURATION;

    /**
     * 请求方式.
     */
    public static final int HTTP_POST = 0; //post
    public static final int HTTP_GET = 1; //get
    public static final int HTTP_PUT = 2; //put
    public static final int HTTP_DELETE = 3; //delete

    /**
     * 巡检扫码限制距离，在此距离外，提示用户不能进行巡检
     */
    public static final int POTROL_LIMIT_DISTANCE = 100;

    /**
     * 通过路由实现来跳转
     */
    public static final String AROUTE_LOGIN = "/login/loginhome";//登录首页

    public static final String AROUTE_VERIFYMOBILE = "/login/verifymobile";

    public static final String AROUTE_PATROL = "/patrol/allocate";//巡检人界面

    public static final String AROUTE_FAULT_POST_PROCESSING = "/fault/postprocessingpersons";//选择处理人界面

    public static final String AROUTE_FAULT_POST_REMARKS = "/fault/postremarks";//缺陷申报添加备注

    public static final String AROUTE_FAULT_POST_MANAGER_DETAILS = "/fault/manager/details";//缺陷详情

    public static final String AROUTE_FAULT_POST_SCENE = "/fault/manager/scenes";//缺陷现场记录

    public static final String AROUTE_FAULT_PROCESSINGPROGRESS = "/fault/processing/progress";//缺陷处理进度

    public static final String AROUTE_FAULT_PROCESSING_RESULTS = "/fault/processing/results";//处理结果详情

    public static final String AROUTE_PROCESSING_RESULT = "/fault/processing/result";//处理结果填报

    public static final String AROUTE_FAULT_EQUIPMENT_REPAIR = "/fault/equipment/repair";//设备报修

    public static final String AROUTE_FAULT_EQUIPMENT = "/fault/equipment";//相关设备

    public static final String AROUTE_FAULT_SUBMIT_SUCCESS = "/fault/submit/success";//提交缺陷成功

    public static final String AROUTE_FAULT_TYPE = "/process/type";//缺陷类型

    public static final String AROUTE_FAULT_SEVERITY = "/process/severity";//严重程度

    public static final String AROUTE_FAULT_RESULT = "/process/result";//缺陷提交成功

    public static final String AROUTE_PROCESS_POSITION = "/process/position";//工艺位置列表

    public static final String AROUTE_HOME = "/home/homefragment";

    public static final String AROUTE_FAULT_POST = "/fault/post";//缺陷申报

    public static final String AROUTE_EQUIPMENT_DETAILS = "/maintence/details/";//设备详情

    public static final String AROUTE_EQUIPMENT_LIST_NEW = "/equipment/list/new";//扫码的设备列表界面

    public static final String AROUTE_MODEL_MSG = "/model/modelmsg";//扫码的设备列表界面

    public static void setCookie(String cookies) {
        cookie = cookies;
    }

    public static String getCookie() {
        return cookie;
    }

    /**
     * 用来 标记功能来源
     */

    private static String EQIPMENT = "equipment/api/equipments";//接口地址

    private static String EQUIPMENT_ROTER = "/equiment/list";//路由地址

    private static String FAULT = "equipment/api/faults/app";//接口地址

    private static String FAULT_ROUTE = "/fault/list";//路由地址

    private static String AR = "equipment/api/equipments/app";

    private static String AR_ROUTE = "/ar/main";

    private static String CURVE = "loong/api/curves";

    private static String CURVE_ROUTE = "/curve/main";

    private static String MONITOR = "uaa/api/processes/list";

    private static String MONITOR_ROUTE = "/monitor/home/";

    private static String MR = "equipment/api/equ-mrs/app";

    private static String MR_ROUTE = "/mr/main";

    private static String MESSAGE = "message/api/messages";

    private static String MESSAGE_ROUTE = "/message/main";

    private static String REPORT = "loong/api/smart-reports";

    private static String REPORT_ROUTE = "/data/reportform";

    private static String PATROL = "patrol/api/tasks/app";

    private static String PATROL_ROUTE = "/patrol/list";

    private static String REPAIRS = "equipment/api/repairs/app";

    private static String REPAIRS_ROUTE = "/repair/list";

    private static String MAIMTAIN = "equipment/api/maintains/tasks";

    private static String MAINTAIN_ROUTE = "/maintain/list";

    private static String TASK = "portal/api/tasks";

    private static String TASK_ROUTE = "/task/list";

    private static String FAULT_POST = "equipment/api/faults";

    private static String FAULT_POST_ROUTE = "/fault/post";

    private static String CONTROL = "loong/api/mpoints/remote-control";

    private static String CONTROL_ROUTE = "/monitor/control";

    public static String addRoute(String url) {
        String path = "";
        if (url.equals(EQIPMENT)) {
            path = EQUIPMENT_ROTER;
        } else if (url.equals(PATROL)) {
            path = PATROL_ROUTE;
        } else if (url.equals(FAULT)) {
            path = FAULT_ROUTE;
        } else if (url.equals(REPAIRS)) {
            path = REPAIRS_ROUTE;
        } else if (url.equals(MAIMTAIN)) {
            path = MAINTAIN_ROUTE;
        } else if (url.equals(CURVE)) {
            path = CURVE_ROUTE;
        } else if (url.equals(MONITOR)) {
            path = MAINTAIN_ROUTE;
        } else if (url.equals(TASK)) {
            path = TASK_ROUTE;
        } else if (url.equals(FAULT_POST)) {
            path = FAULT_POST_ROUTE;
        } else if (url.equals(REPORT)) {
            path = REPORT_ROUTE;
        } else if (url.equals(AR)) {
            path = AR_ROUTE;
        } else if (url.equals(CONTROL)) {
            path = CONTROL_ROUTE;
        } else if (url.equals(MR)) {
            path = MR_ROUTE;
        }
        return path;
    }


    /**
     * @param activity
     * @param color    任务栏需要改变的颜色
     */
    public static void setBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            activity.getWindow().setStatusBarColor(color);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public static void setBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            Window window = activity.getWindow();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //根据上面设置是否对状态栏单独设置颜色
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
