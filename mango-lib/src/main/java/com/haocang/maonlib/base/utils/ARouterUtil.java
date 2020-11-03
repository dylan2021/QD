package com.haocang.maonlib.base.utils;

import android.app.Activity;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.haocang.base.config.AppApplication;

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
 * 创建时间：2018/4/2314:47
 * 修 改 者：
 * 修改时间：
 */
public class ARouterUtil {

    public static void toFragment(final Map<String, Object> map) {
        Postcard postcard = ARouter.getInstance().build("/base/common");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Boolean) {
                boolean b = ((Boolean) value).booleanValue();
                postcard.withBoolean(key, b);
            } else if (value instanceof Integer) {
                int c = (int) value;
                postcard.withInt(key, c);
            } else if (value instanceof String[]) {
                postcard.getExtras().putStringArray(entry.getKey(), (String[]) entry.getValue());
            } else {
                postcard.withString(entry.getKey(), entry.getValue() + "");
            }

        }
        postcard.navigation();
    }

    public static void toFragment(final String fragmentUri) {
        ARouter.getInstance()
                .build("/base/common")
                .withString("fragmentUri", fragmentUri)
                .navigation();
    }

    public static void startActivityForResult(final Map<String, Object> map,
                                              final Activity activity,
                                              final int requestCode) {
        Postcard postcard = ARouter.getInstance().build("/base/common");
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Boolean) {
                boolean b = ((Boolean) value).booleanValue();
                postcard.withBoolean(key, b);
            } else if (value instanceof Integer) {
                int c = (int) value;
                postcard.withInt(key, c);
            } else if (entry.getValue() != null) {
                postcard.withString(entry.getKey(), entry.getValue().toString());
            }

        }
        postcard.navigation(activity, requestCode);
    }

    /**
     * @param map  参数.
     * @param path 路径
     */
    public static void toActivity(final Map<String, Object> map, final String path) {
        Postcard postcard = ARouter.getInstance().build(path);
        if (map != null && map.size() > 0) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value instanceof Boolean) {
                    boolean b = ((Boolean) value).booleanValue();
                    postcard.withBoolean(key, b);
                } else if (value instanceof Integer) {
                    int c = (int) value;
                    postcard.withInt(key, c);
                } else if (entry.getValue() != null) {
                    postcard.withString(entry.getKey(), entry.getValue().toString());
                }
            }
        }
        postcard.navigation();
    }
}
