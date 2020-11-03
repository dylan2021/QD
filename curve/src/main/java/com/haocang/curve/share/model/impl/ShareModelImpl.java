package com.haocang.curve.share.model.impl;

import android.content.Context;

import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.ProgressBarDialog;
import com.haocang.curve.constants.CurveMethod;
import com.haocang.curve.share.model.ShareModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

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
 * 创建时间：2018/6/25 10:54
 * 修 改 者：
 * 修改时间：
 */
public class ShareModelImpl implements ShareModel {
    private GetEntityListener<String> listener;

    @Override
    public void shareCurve(Context ctx, Map<String, Object> map, GetEntityListener<String> listener) {
        this.listener = listener;
        AddParameters addParameters = new AddParameters();
        addParameters.addParam(map);
        new OkHttpClientManager()
                .setOnNetWorkReponse(onNetworkResponse)
                .setLoadDialog(new ProgressBarDialog(ctx))
                .setUrl(CurveMethod.CURVE_SHARE)
                .setRequestBody(addParameters.formBody())
                .setRequestMethod(LibConfig.HTTP_POST)
                .builder();
    }

    OkHttpClientManager.OnNetworkResponse onNetworkResponse = new OkHttpClientManager.OnNetworkResponse() {
        @Override
        public void onNetworkResponse(String result) {
            try {
                JSONObject object = new JSONObject(result);
                String url = LibConstants.SHARE_IP + object.optString("url");
                listener.success(url);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onErrorResponse(Response response) {

        }
    };
}
