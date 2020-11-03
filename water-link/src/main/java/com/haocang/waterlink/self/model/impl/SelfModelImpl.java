package com.haocang.waterlink.self.model.impl;

import android.content.Context;

import com.haocang.base.bean.UserEntity;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.ProgressBarDialog;
import com.haocang.waterlink.self.model.SelfModel;


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
 * 创建时间：2018/1/3010:58
 * 修 改 者：
 * 修改时间：
 */
public class SelfModelImpl implements SelfModel {

    private GetEntityListener<String> entityListener;


    @Override
    public void submitImage(final Context ctx,
                            final String url,
                            final GetEntityListener<String> listener) {
        this.entityListener = listener;
        AddParameters addParameters = new AddParameters();
        addParameters.addParam("imgUrl", url);
        new OkHttpClientManager()
                .setLoadDialog(new ProgressBarDialog(ctx))
                .setOnNetWorkReponse(onNetworkResponse)
                .setRequestBody(addParameters.formBody())
                .setRequestMethod(LibConfig.HTTP_PUT)
                .setUrl(MethodConstants.Uaa.USER_IMGURL + addParameters.formGet())
                .builder();
    }

    private OkHttpClientManager.OnNetworkResponse onNetworkResponse = new OkHttpClientManager.OnNetworkResponse() {
        @Override
        public void onNetworkResponse(final String result) {
            entityListener.success(result);
        }

        @Override
        public void onErrorResponse(final Response response) {

        }
    };


}
