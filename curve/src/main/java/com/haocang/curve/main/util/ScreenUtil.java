package com.haocang.curve.main.util;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.Surface;

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
 * 创建时间：2018/6/22上午10:18
 * 修  改  者：
 * 修改时间：
 */
public class ScreenUtil {

    private static class Orientation {
        private static final int LANDSCAPE = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        private static final int PORTRAIT = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        private static final int REVERSE_LANDSCAPE = 8; // ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
        private static final int REVERSE_PORTRAIT = 9; // ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT
        private static final int UNSPECIFIED = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }

    /**
     * This class may not be instantiated
     */
    private ScreenUtil() {

    }

    /**
     * Locks the screen's orientation to the current setting
     *
     * @param activity an `Activity` reference
     */
    @SuppressLint({"NewApi", "WrongConstant"})
    @SuppressWarnings("deprecation")
    public static void lockOrientation(final Activity activity) {
        final Display display = activity.getWindowManager().getDefaultDisplay();
        final int rotation = display.getRotation();

        final int width, height;
        if (Build.VERSION.SDK_INT >= 13) {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;
        } else {
            width = display.getWidth();
            height = display.getHeight();
        }

        switch (rotation) {
            case Surface.ROTATION_90:
                if (width > height) {
                    activity.setRequestedOrientation(Orientation.LANDSCAPE);
                } else {
                    activity.setRequestedOrientation(Orientation.REVERSE_PORTRAIT);
                }
                break;
            case Surface.ROTATION_180:
                if (height > width) {
                    activity.setRequestedOrientation(Orientation.REVERSE_PORTRAIT);
                } else {
                    activity.setRequestedOrientation(Orientation.REVERSE_LANDSCAPE);
                }
                break;
            case Surface.ROTATION_270:
                if (width > height) {
                    activity.setRequestedOrientation(Orientation.REVERSE_LANDSCAPE);
                } else {
                    activity.setRequestedOrientation(Orientation.PORTRAIT);
                }
                break;
            default:
                if (height > width) {
                    activity.setRequestedOrientation(Orientation.PORTRAIT);
                } else {
                    activity.setRequestedOrientation(Orientation.LANDSCAPE);
                }
        }
    }

    /**
     * Unlocks the screen's orientation in case it has been locked before
     *
     * @param activity an `Activity` reference
     */
    public static void unlockOrientation(final Activity activity) {
        activity.setRequestedOrientation(Orientation.UNSPECIFIED);
    }

}