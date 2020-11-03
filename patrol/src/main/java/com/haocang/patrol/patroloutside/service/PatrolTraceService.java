package com.haocang.patrol.patroloutside.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.haocang.base.config.LibConstants;
import com.haocang.base.utils.BDSendTraceUtil;
import com.haocang.base.utils.MapAssistUtil;
import com.haocang.patrol.patroloutside.iview.PatrolTraceView;
import com.haocang.patrol.patroloutside.presenter.PatrolTracePresenter;
import com.haocang.patrol.patroloutside.presenter.impl.PatrolTracePresenterImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
 * 创建时间：2018/4/15下午10:02
 * 修  改  者：
 * 修改时间：
 */
public class PatrolTraceService extends Service implements PatrolTraceView {
    /**
     *
     */
    private static final Class[] mStartForegroundSignature = new Class[]{
            int.class, Notification.class};
    private static final Class[] mStopForegroundSignature = new Class[]{boolean.class};
    private NotificationManager mNM;
    private Method mStartForeground;
    private Method mStopForeground;
    private Object[] mStartForegroundArgs = new Object[2];
    private Object[] mStopForegroundArgs = new Object[1];
    private Integer taskId;
    private PatrolTracePresenter patrolTracePresenter;
    private boolean startFlag = true;

    /**
     * 错误的定位次数：主要是针对不符合常理的相邻两个点
     */
    private int errorCount = 0;
    private int startCount = 0;

    /**
     * 记录上一次上传的轨迹点
     */
    private BDLocation lastLocation = null;

    /**
     * 停止上传轨迹
     */
    public static final int PARTOL_FINISH = -2;

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Intent
        if (intent != null) {
            taskId = intent.getIntExtra("taskId", -1);
        }
        startFlag = true;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        patrolTracePresenter = new PatrolTracePresenterImpl();
        patrolTracePresenter.setPatrolTraceView(this);
        mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        try {
            mStartForeground = PatrolTraceService.class.getMethod("startForeground",
                    mStartForegroundSignature);
            mStopForeground = PatrolTraceService.class.getMethod("stopForeground",
                    mStopForegroundSignature);
        } catch (NoSuchMethodException e) {
            mStartForeground = mStopForeground = null;
        }
        // 我们并不需要为 notification.flags 设置 FLAG_ONGOING_EVENT，因为
        // 前台服务的 notification.flags 总是默认包含了那个标志位
        Notification notification = new Notification();
        // 注意使用 startForeground ，id 为 0 将不会显示 notification,1显示
        startForegroundCompat(1, notification);
//        new Thread(r).start();

        //方式二：
        BDSendTraceUtil bdSendTraceUtil = BDSendTraceUtil.getInstance();
        bdSendTraceUtil.setOnLocationListener(new BDSendTraceUtil.OnLocationListener() {
            @Override
            public void receiveLocation(BDLocation location) {
                if (getTaskId() == -1) {
                    stopSelf();
                } else if (null != location
                        && location.getLocType() != BDLocation.TypeServerError
                        && !MapAssistUtil.isZeroPoint(location.getLatitude(), location.getLongitude())) {
                    //patrolTracePresenter.sendTrace();

                    //网络不好的时候，容易出现定位偏差，所以舍弃前几个定位点
                    if (startCount++ <= 5) return;

                    if (lastLocation != null) {
                        LatLng latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                        LatLng lastLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        double distance = DistanceUtil.getDistance(lastLatLng, latLng);
                        if (distance > 100) { //大于100 说明可能出现错误点。如何处理??  汽车120km/h速度，每秒移动33m
                            /*lastLocation = location; */
                            errorCount++;
                            if (errorCount == 5) {//连续出现5次，就把这个点当成正确点
                                errorCount = 0;
                            } else {
                                return;  // 多次出现错误点可能出现问题
                            }
                        }
                        if (errorCount > 0) {
                            errorCount = 0;
                        }
                        if (distance < 10) { //小于10米 ，说明可能原地未动
                            return;
                        }
                    }
                    lastLocation = location;

                    //发送实际的定位结果
                    patrolTracePresenter.sendTrace(location);

                }
            }
        });

    }

    /**
     *
     */
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            while (startFlag) {
                handler.sendEmptyMessage(0);
                try {
                    Thread.sleep(LibConstants.Patrol.UPLOAD_PATROL_TRACE_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     *
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        startFlag = false;
        taskId = -1;
        stopForegroundCompat(1);
    }

    /**
     * @param id id
     * @param n  notification
     */
    // 以兼容性方式开始前台服务
    private void startForegroundCompat(final int id, final Notification n) {
        if (mStartForeground != null) {
            mStartForegroundArgs[0] = id;
            mStartForegroundArgs[1] = n;
            try {
                mStartForeground.invoke(this, mStartForegroundArgs);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return;
        }
        mNM.notify(id, n);
    }

    // 以兼容性方式停止前台服务
    private void stopForegroundCompat(int id) {
        if (mStopForeground != null) {
            mStopForegroundArgs[0] = Boolean.TRUE;
            try {
                mStopForeground.invoke(this, mStopForegroundArgs);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return;
        }
        // 在 setForeground 之前调用 cancel，因为我们有可能在取消前台服务之后
        // 的那一瞬间被kill掉。这个时候 notification 便永远不会从通知一栏移除
        mNM.cancel(id);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            Toast.makeText(TestService.this, "f", 3).show();
            if (getTaskId() == -1) {
                stopSelf();
            } else if (BDSendTraceUtil.getInstance().isLocationValid()) {
                patrolTracePresenter.sendTrace();
            }

        }
    };

//    private boolean isLocationValid() {
//        boolean isValid = true;
//        BDLocation location = BDSendTraceUtil.getInstance().getLocation();
//        if (location != null && TextUtils.isEmpty(location.getAddrStr())) {
//            isValid = false;
//        }
//        return isValid;
//    }


    @Override
    public Integer getTaskId() {
        return taskId == null ? -1 : taskId;
    }

    @Override
    public Context getContext() {
        return null;
    }
}
