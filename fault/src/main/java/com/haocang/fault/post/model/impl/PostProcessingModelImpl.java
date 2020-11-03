package com.haocang.fault.post.model.impl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.bean.LabelEntity;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.GetListListener;
import com.haocang.base.utils.ProgressBarDialog;
import com.haocang.fault.post.model.PostProcessingModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * 创 建 者：he
 * 创建时间：2018/4/2610:18
 * 修 改 者：
 * 修改时间：
 */
public class PostProcessingModelImpl implements PostProcessingModel {
    private GetListListener<LabelEntity> listener;

    @Override
    public void getAllocatorListData(Context ctx, Map<String, Object> map, GetListListener<LabelEntity> listener) {
        this.listener = listener;
        AddParameters addParameters = new AddParameters();
        addParameters.addParam(map);
        new OkHttpClientManager()
                .setLoadDialog(new ProgressBarDialog(ctx))
                .setUrl(MethodConstants.Uaa.USER_LIST + addParameters.formGet())
                .setRequestMethod(LibConfig.HTTP_GET)
                .setOnNetWorkReponse(response)
                .builder();
    }

    @Override
    public void getOnLinePersonnelList(Context mCtx, String pageSize, final GetListListener<LabelEntity> listener) {
        new OkHttpClientManager()
                .setLoadDialog(new ProgressBarDialog(mCtx))
                .setUrl("base/api/dispatch-center/online-user?pageSize=0")
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(String s) {
                        try {
                            List<LabelEntity> list = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.optJSONArray("items");
                            for (int i = 0; i < array.length(); i++) {
                                LabelEntity labelEntity = new LabelEntity();
                                JSONObject object = array.optJSONObject(i);
                                labelEntity.setValue(object.optLong("userId"));
                                list.add(labelEntity);
                            }
                            listener.success(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onErrorResponse(Response response) {

                    }
                })
                .builder();
    }

    OkHttpClientManager.OnNetworkResponse response = new OkHttpClientManager.OnNetworkResponse() {
        @Override
        public void onNetworkResponse(String result) {
            try {
                Map<String, String> map = new HashMap<>();
                Type type = new TypeToken<List<LabelEntity>>() {
                }.getType();
                List<LabelEntity> list = new Gson().fromJson(result, type);
                listener.success(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onErrorResponse(Response response) {

        }
    };
}
