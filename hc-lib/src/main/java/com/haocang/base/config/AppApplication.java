package com.haocang.base.config;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.SDKInitializer;
import com.haocang.base.bean.AppVersion;
import com.haocang.base.bean.UserEntity;
import com.haocang.base.utils.CrashHandler;
import com.haocang.base.utils.MetaDataUtil;
import com.haocang.base.utils.NetBroadcastReceiver;
import com.haocang.base.utils.NfcUtil;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Stack;

import ren.solid.skinloader.load.SkinManager;


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
 * 创建时间：2018/1/1015:45
 * 修 改 者：
 * 修改时间：
 */
public class AppApplication extends MultiDexApplication {

    public static boolean isNetWord = true;//当前网络是否可用

    public UserEntity userEntity;
    /**
     * 整个应用的 sinleton.
     */
    private static AppApplication singleton;

    /**
     * 版本信息.
     */
    private AppVersion version;
    /**
     * 所有activity.
     */
    private static Stack<Activity> activityStack;

    /**
     * @return 获取Application.
     */
    public static AppApplication getInstance() {
        return singleton;
    }

    /**
     * ..
     *
     * @param version ..
     */
    public void setVersion(AppVersion version) {
        this.version = version;
    }

    private static Context context;


    public static Context getContext() {
        return context;
    }

    public static int messgeCount = 0;

    private TextView spotTv;

    private OnReceiveVoiceListener onReceiveVoiceListener;
    private NfcUtil.OnReadNFCTagListener onReadNFCTagListener;

    /**
     * 连续语音模式.
     */
    private boolean flowMode;

    private String voiceName;
//    public static boolean nativeTest = false;

    public Activity getLastActivity() {
        return lastActivity;
    }


    /**
     * 加速度阀值,小于13比较灵敏，大于15比较难触发.
     */
    public int threshhold = 16;

    private Activity lastActivity;


    public int getThreshhold() {
        return threshhold;
    }

    public void initThreshhold(SharedPreferences sp) {
        if (sp.getInt(LibConfig.VOICER_THRESHHOLD_KEY, 0) >= 0) {
            AppApplication.getInstance().setThreshhold(sp.getInt(LibConfig.VOICER_THRESHHOLD_KEY, 16) + 5);
        }
    }

    public void setThreshhold(int threshhold) {
        this.threshhold = threshhold;
    }

    public boolean isUpgradeTips() {
        return isUpgradeTips;
    }

    public void setUpgradeTips(boolean upgradeTips) {
        isUpgradeTips = upgradeTips;
    }

    private boolean isUpgradeTips = true;

    private String speechKey = "5ab8b9ec";


    /**
     * 初始化.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        singleton = this;
        context = getApplicationContext();
//        if (BuildConfig.DEBUG) {  // 这两行必须写在init之前，否则这些配置在init过程中将无效
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
//        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
        speechKey = new MetaDataUtil().getMetaData(this, "speechkey", speechKey);
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=" + speechKey);//语音转换

        SDKInitializer.initialize(this);
        initSkinLoader();//todo  皮肤管理初始化
        closeAndroidPDialog();
        /**
         *  如何在首页获取个人信息接口报错的话。调用userEntity会报错，所以先实例化避免空指针
         */
        userEntity = new UserEntity();
        startReceiver();


        //处理程序奔溃问题,直接关闭程序
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());
    }

    public String getMeteData(String metaDataName) {
        String channel = "";
        try {
            channel = this.getPackageManager().getApplicationInfo(getPackageName(),
                    PackageManager.GET_META_DATA).metaData.getString(metaDataName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channel;
    }


    public String getVoiceName() {
        return voiceName;
    }

    public void setVoiceName(String voiceName) {
        this.voiceName = voiceName;
    }

    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * todo 皮肤管理
     */
    private void initSkinLoader() {
        SkinManager.getInstance().init(this);
        SkinManager.getInstance().load();
    }

    /**
     * @param activity 退出时关闭所有的activity.
     */
    public void finishActivity(final Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }


    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    public void setSpotTv(TextView spotTv) {
        this.spotTv = spotTv;
    }

    public void setSport() {
        if (spotTv == null) {
            return;
        }
        if (messgeCount > 0) {
            spotTv.setVisibility(View.VISIBLE);
        } else {
            spotTv.setVisibility(View.GONE);
        }
    }

    public void finisBussActivity(Activity a) {
        for (Activity activity : activityStack) {
            if (a != activity) {
                activity.finish();
            }
        }
    }

    public int getActivityStackSize() {
        return activityStack.size();
    }


    /**
     * @param activity 把运行了的activity添加进 activityStack.
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }


    public Activity getTopActivity() {
        return activityStack.lastElement();
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public void backHome(Context mContext) {
        String homeName = "com.haocang.waterlink.home.ui.NavigationActivity";
        if(mContext!=null){
            Log.i("tttttttt",mContext.getClass().getName());
        }

//        if (mContext.getClass().getName().equals(homeName)) {
//            //如果已经是在首页 就拦截
//            return;
//        }
        Intent intent = null;
        try {
            intent = new Intent(mContext, Class.forName(homeName));
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("FIRST_FLAG", true);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        mContext.startActivity(intent);
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }

    /**
     * 开启网络监听广播.
     */
    private void startReceiver() {
        NetBroadcastReceiver netWorkChangReceiver = new NetBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkChangReceiver, filter);
    }

    public OnReceiveVoiceListener getOnReceiveVoiceListener() {
        return onReceiveVoiceListener;
    }

    public void setOnReceiveVoiceListener(OnReceiveVoiceListener onReceiveVoiceListener) {
        this.onReceiveVoiceListener = onReceiveVoiceListener;
    }

    public boolean isFlowMode() {
        return flowMode;
    }

    public void setFlowMode(boolean flowMode) {
        this.flowMode = flowMode;
        this.flowMode = false;
    }

    public NfcUtil.OnReadNFCTagListener getOnReadNFCTagListener() {
        return onReadNFCTagListener;
    }

    public void setOnReadNFCTagListener(NfcUtil.OnReadNFCTagListener onReadNFCTagListener) {
        this.onReadNFCTagListener = onReadNFCTagListener;
    }

    public String getSpeechKey() {
        return speechKey;
    }

    public void setSpeechKey(String speechKey) {
        this.speechKey = speechKey;
    }
}
