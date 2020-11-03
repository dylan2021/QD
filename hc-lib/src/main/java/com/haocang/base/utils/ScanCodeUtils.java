package com.haocang.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.haocang.base.config.LibConfig;
import com.haocang.base.zxing.app.CaptureActivity;

import java.util.HashMap;
import java.util.Map;

//import com.google.zxing.integration.android.IntentIntegrator;


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
 * 创建时间：2018/3/2916:26
 * 修 改 者：
 * 修改时间：
 */
public class ScanCodeUtils {

    public static void openScanCode(final Activity activity) {
        Intent inten = new Intent(activity, CaptureActivity.class);
        activity.startActivityForResult(inten, LibConfig.SCAN_CODE);
    }

    public static void qrCodeType(String content, Context ctx) {
        String type = AnalysisQRCodeUtil.getQRCodeType(content);
        int id = -1;
//        Intent intent = new Intent(getActivity(), CommonActivity.class);
        if (AnalysisQRCodeUtil.TYPE_NONE.equals(type)) {
            ToastUtil.makeText(ctx, "请扫描正确的二维码");
        } else if (AnalysisQRCodeUtil.TYPE_EQUIPMENT.equals(type)) {
            id = AnalysisQRCodeUtil.getEquipmentId(content);
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", LibConfig.AROUTE_EQUIPMENT_DETAILS);
            map.put("flag", "flag");//如果是从扫码进入的设备详情 ，接口报错的话直接退出当前页面
            map.put("id", id + "");
//            intent.putExtra("fragmentName", EquipmentDetailsFragment.class.getName());
//            intent.putExtra("id", id + "");
//            startActivity(intent);
            ARouterUtil.toFragment(map);
        } else if (AnalysisQRCodeUtil.TYPE_PATROL.equals(type)) {
            id = AnalysisQRCodeUtil.getPatrolPointId(content);
//            intent.putExtra("fragmentName", HomeEquipmentListFragment.class.getName());
//            intent.putExtra("id", id + "");
//            intent.putExtra("type", type);
//            startActivity(intent);
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", LibConfig.AROUTE_EQUIPMENT_LIST_NEW);
            map.put("id", id + "");
            map.put("type", type);
            ARouterUtil.toFragment(map);
        } else if (AnalysisQRCodeUtil.TYPE_PROCESS.equals(type)) {
            id = AnalysisQRCodeUtil.getProcessId(content);
//            intent.putExtra("fragmentName", HomeEquipmentListFragment.class.getName());
//            intent.putExtra("id", id + "");
//            intent.putExtra("type", type);
//            startActivity(intent);
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", LibConfig.AROUTE_EQUIPMENT_LIST_NEW);
            map.put("id", id + "");
            map.put("type", type);
//            intent.putExtra("type", type);
            ARouterUtil.toFragment(map);
        }
    }

}
