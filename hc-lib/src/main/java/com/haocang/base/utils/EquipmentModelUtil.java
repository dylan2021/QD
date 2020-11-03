package com.haocang.base.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.haocang.base.bean.EquimentEntity;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Response;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区龙兰路277号一号楼9楼
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/5/16 15:29
 * 修 改 者：
 * 修改时间：
 */
public class EquipmentModelUtil {

    private static GetEntityListener<EquimentEntity> listeners;

    private GetEntityListener<Boolean> uploadListener;


    public static void getDetailsData(Context ctx, String id, GetEntityListener<EquimentEntity> listener) {
        listeners = listener;
        AddParameters addParameters = new AddParameters();
        addParameters.addParam("id", id);
        new OkHttpClientManager()
                .setOnNetWorkReponse(detailsResponse)
                .setLoadDialog(new ProgressBarDialog(ctx))
                .setUrl(MethodConstants.Equiment.EQUIMENT_DETAILS + "/" + id)
                .setRequestMethod(LibConfig.HTTP_GET)
                .builder();
    }


    static OkHttpClientManager.OnNetworkResponse detailsResponse = new OkHttpClientManager.OnNetworkResponse() {
        @Override
        public void onNetworkResponse(String result) {
            try {
                JSONObject object = new JSONObject(result);
                EquimentEntity entity = new Gson().fromJson(object.optString("equipment"), EquimentEntity.class);
                listeners.success(entity);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onErrorResponse(Response response) {
            listeners.fail("无该台账设备权限，请联系管理员");
        }
    };
}
