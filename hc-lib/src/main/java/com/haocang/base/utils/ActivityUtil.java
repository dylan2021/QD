package com.haocang.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.haocang.base.ui.CommonActivity;

import java.util.Map;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题：跳转fragment工具类
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/7/19下午4:47
 * 修  改  者：
 * 修改时间：
 */
public class ActivityUtil {

    public static void toFragment(Context ctx, Map<String, Object> param) {
        Intent intent = new Intent(ctx, CommonActivity.class);
        if (param != null && param.size() > 0) {
            for (String key : param.keySet()) {
                Object value = param.get(key);
                if (value instanceof Integer) {
                    intent.putExtra(key, (int) param.get(key));
                } else if (value instanceof Long) {
                    intent.putExtra(key, (long) param.get(key));
                } else {
                    intent.putExtra(key, (String) param.get(key));
                }
            }
        }
        ctx.startActivity(intent);
    }

    public static void toFragmentForResult(Activity activity, int requestCode, Map<String, Object> param) {
        Intent intent = new Intent(activity, CommonActivity.class);
        if (param != null && param.size() > 0) {
            for (String key : param.keySet()) {
                Object value = param.get(key);
                if (value instanceof Integer) {
                    intent.putExtra(key, (int) param.get(key));
                } else if (value instanceof Long) {
                    intent.putExtra(key, (long) param.get(key));
                } else {
                    intent.putExtra(key, (String) param.get(key));
                }
            }
        }
        activity.startActivityForResult(intent, requestCode);
    }
}
