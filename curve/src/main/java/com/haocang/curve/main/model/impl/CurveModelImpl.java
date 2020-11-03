package com.haocang.curve.main.model.impl;

import android.content.Context;

import com.haocang.base.config.LibConfig;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.ProgressBarDialog;
import com.haocang.curve.constants.CurveMethod;
import com.haocang.curve.main.model.CurveModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.Response;

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
 * 创建时间：2018/6/12下午2:21
 * 修  改  者：
 * 修改时间：
 */
public class CurveModelImpl implements CurveModel {

    private Context mContext;

    public CurveModelImpl(Context context) {
        mContext = context;
    }

    @Override
    public void getCurveData(final Map<String, Object> map,
                             final GetEntityListener<JSONObject> listener) {
        AddParameters addParameters = new AddParameters();
        addParameters.addParam(map);
        new OkHttpClientManager()
                .setRequestMethod(LibConfig.HTTP_GET)
                .setUrl(CurveMethod.GET_CURVE_DATA
                        + addParameters.formGet())
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {

                    @Override
                    public void onNetworkResponse(String result) {
                        try {
                            JSONObject object = new JSONObject(result);
                            listener.success(object);
                            if (object.has("items")) {
                                JSONArray datas = object.getJSONArray("items");
//                                CurveEntity data = new Gson().fromJson(datas, CurveEntity.class);
                                listener.success(object);
                            } else {
                                listener.success(null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(Response response) {

                    }
                })
                .setLoadDialog(new ProgressBarDialog(mContext))
                .builder();
    }
}
