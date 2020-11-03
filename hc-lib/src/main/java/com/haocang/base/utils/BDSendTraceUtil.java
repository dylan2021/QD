package com.haocang.base.utils;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2015-2020 <br/>
 * 公司名称：上海昊沧系统控制技术有限责任公司<br/>
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001<br/>
 * 网址：http://www.haocang.com/<br/>
 * <p>
 * 标 题：
 * </p>
 * <p>
 * 文 件 名：com.haocang.dax.util.BDLocationUtil.java
 * </p>
 * <p>
 * 部 门：产品研发部
 * <p>
 * 版 本： 1.0
 * </p>
 * <p>
 * 创 建 者：william
 * </p>
 * <p>
 * 创建时间：2016-11-11上午8:49:51
 * </p>
 * <p>
 * 修 改 者：
 * </p>
 * <p>
 * 修改时间：
 * </p>
 */


import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.haocang.base.config.AppApplication;

public class BDSendTraceUtil {
    private BDLocationListener myListener = new MyLocationListener();
    private LocationClient mLocationClient = null;

    private int refleshRate = 1000;                  /* 刷新时间10s */

    private boolean isStartLocation = false;

    private OnLocationListener mOnLocationListener;

    private static BDSendTraceUtil mBDSendTraceUtil;

    private BDLocation mLocation;

    public static BDSendTraceUtil getInstance() {
        if (mBDSendTraceUtil == null) {
            mBDSendTraceUtil = new BDSendTraceUtil();
        }
        return mBDSendTraceUtil;
    }

    public void setOnLocationListener(OnLocationListener onLocationListener) {
        mOnLocationListener = onLocationListener;
    }


    public BDSendTraceUtil() {

    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系bd09ll
        option.setScanSpan(refleshRate);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setAddrType("all");
//        option.setEnableSimulateGps();
        mLocationClient.setLocOption(option);
    }

    public boolean isLocationValid(final BDLocation location) {
        boolean isValid = false;
        if (location != null && location.getLongitude() != 0
                && location.getLatitude() != 0
                && !TextUtils.isEmpty(location.getAddrStr())) {
            isValid = true;
        }
        return isValid;
    }

    public boolean isLocationValid() {
        return isLocationValid(mLocation);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //保证每次都调用到
            if (mOnLocationListener != null) {
                mOnLocationListener.receiveLocation(location);
            }
            if (isLocationValid(location)) {
                mLocation = location;
            }
//            ToastUtil.makeText(AppApplication.getContext(), "FPS质量" + location. + "");
        }
    }

    public void stop() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    public BDLocation getLocation() {
        return mLocation;
    }

    public void setLocation(BDLocation location) {
        this.mLocation = location;
    }

    public interface OnTraceLocateListener {
        public void onLocate(double longitude, double latitude, String address);
    }

    public interface OnLocationListener {
        void receiveLocation(BDLocation location);
    }

    public void start() {
        if (!isStartLocation) {
            mLocationClient = new LocationClient(AppApplication.getInstance().getApplicationContext());
            initLocation();
            mLocationClient.registerLocationListener(myListener); // 注册监听函数
            mLocationClient.start();
            isStartLocation = true;
        }
    }
}
