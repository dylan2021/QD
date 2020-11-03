package com.haocang.patrol.patroloutside.nfcutil;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.haocang.base.config.AppApplication;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.AnalysisQRCodeUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.patrol.constants.PatrolMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

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
 * 创 建 者：hy
 * 创建时间：${DATA} 11:24
 * 修 改 者：
 * 修改时间：
 */
public class PatrolNfcPresenter {
    public static void getTasksList(final String content) {
        int pointId = AnalysisQRCodeUtil.getPatrolPointId(content);
        new OkHttpClientManager()
                .setUrl(PatrolMethod.PATROL_TASKS_NFC + "?pointId=" + pointId)
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            try {
                                JSONArray jsonArray = new JSONArray(result);
                                if (jsonArray != null && jsonArray.length() > 0) {
                                    JSONObject obj = jsonArray.optJSONObject(0);
                                    int taskId = obj.optInt("id");
                                    String type = obj.optString("type");
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("nfc", true);
                                    map.put("taskId", taskId);
                                    map.put("qrCode", content);
                                    map.put("taskName", obj.optString("name"));
                                    if ("Outside".equals(type)) {
                                        map.put("fragmentUri", ArouterPathConstants.Patrol.PATROL_OUTSIDE);
                                    } else {
                                        map.put("fragmentUri", "/patrol/inhouse");
                                    }
                                    ARouterUtil.toFragment(map);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtil.makeText(AppApplication.getInstance().getTopActivity(), "未找到对应的巡检任务");
                        }
                    }

                    @Override
                    public void onErrorResponse(Response response) {

                    }
                }).builder();
    }
}
