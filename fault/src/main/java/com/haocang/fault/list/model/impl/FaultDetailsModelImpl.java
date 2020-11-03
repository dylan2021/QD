package com.haocang.fault.list.model.impl;

import android.content.Context;

import com.google.gson.Gson;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.ProgressBarDialog;
import com.haocang.fault.constants.FaultMethod;
import com.haocang.fault.list.bean.FaultManagerEntity;
import com.haocang.fault.list.model.FaultDetailsModel;

import org.json.JSONException;
import org.json.JSONObject;

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
 * 创建时间：2018/5/317:26
 * 修 改 者：
 * 修改时间：
 */
public class FaultDetailsModelImpl implements FaultDetailsModel {
    private GetEntityListener<FaultManagerEntity> listener;
    private GetEntityListener<String> listListener;

    @Override
    public void getDetailsDataModel(final Context ctx,
                                    final String faultId,
                                    final GetEntityListener<FaultManagerEntity> listener,
                                    final GetEntityListener<String> listListener) {
        this.listener = listener;
        this.listListener = listListener;
        new OkHttpClientManager()
                .setUrl(FaultMethod.FAULT_DETAILS + "/" + faultId)
                .setLoadDialog(new ProgressBarDialog(ctx))
                .setOnNetWorkReponse(onNetworkResponse)
                .builder();
    }

    OkHttpClientManager.OnNetworkResponse onNetworkResponse = new OkHttpClientManager.OnNetworkResponse() {
        @Override
        public void onNetworkResponse(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                String faultDto = jsonObject.optString("faultDto");
                String faultRemoveRecordDtos = jsonObject.optString("faultRemoveRecordDtos");
                FaultManagerEntity fault = new Gson().fromJson(faultDto, FaultManagerEntity.class);
                listener.success(fault);
//                listListener.success(faultRemoveRecordDtos(faultRemoveRecordDtos));
                listListener.success(faultRemoveRecordDtos);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onErrorResponse(final Response response) {
        }
    };


    private FaultManagerEntity faultDto(final String faultDto) {
        FaultManagerEntity entity = new FaultManagerEntity();
        JSONObject faultJSON = null;
        try {
            faultJSON = new JSONObject(faultDto);

            entity.setCreateDate(faultJSON.optString("createDate"));
            entity.setCreateUser(faultJSON.optString("createUser"));
            entity.setId(faultJSON.optInt("id"));
            entity.setFaultTypeName(faultJSON.optString("faultTypeName"));
            entity.setProcessingPersonName(faultJSON.optString("processingPersonName"));
            entity.setProcessingDate(faultJSON.optString("processingDate"));
            entity.setState(faultJSON.optInt("state"));
            entity.setReported(faultJSON.optInt("reported"));
            entity.setProcessId(faultJSON.optInt("processId"));
            entity.setFaultNumber(faultJSON.optString("faultNumber"));
            entity.setCreateUser(faultJSON.optString("createUser"));
            entity.setRemark(faultJSON.optString("remark"));
            entity.setSeverityTypeName(faultJSON.optString("severityTypeName"));
            entity.setEquName(faultJSON.optString("equName"));
            entity.setOrgName(faultJSON.optString("orgName"));
            entity.setProcessName(faultJSON.optString("processName"));
//            entity.setImgUrl(faultJSON.optString("imgUrl"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return entity;
    }


}
