package com.haocang.base.version.model.impl;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.haocang.base.bean.AppVersion;
import com.haocang.base.bean.PatchEntity;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.version.model.VersionUpdateModel;

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
 * 创建时间：2018/8/13 13:05
 * 修 改 者：Rick.Lau
 * 修改时间：2018/10/13
 */
public class VersionUpdateModelImpl implements VersionUpdateModel {
    @Override
    public void getVersionUpdateModel(String tag, Context ctx, GetEntityListener<AppVersion> listener) {
        AddParameters addParameters = new AddParameters();
        addParameters.addParam("type", tag);
        addParameters.addParam("iosAndroid", "android");
        new OkHttpClientManager()
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            AppVersion appVersion = new Gson().fromJson(result, AppVersion.class);
                            listener.success(appVersion);
                        }
                    }

                    @Override
                    public void onErrorResponse(Response response) {

                    }
                })
                .setUrl(MethodConstants.Base.VERSION_UPDATE + addParameters.formGet())
                .builder();
    }

    @Override
    public void getPatchContent(String versionNo, Context ctx, GetEntityListener<PatchEntity> listener) {
        new OkHttpClientManager()
                .setUrl(MethodConstants.Base.GET_PATCH + "?versionNo=" + versionNo)
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            //发现有差分包可以升级
                            PatchEntity entity = new Gson().fromJson(result, PatchEntity.class);
//                            String sr = FileUtils.savePatchPath(LibConfig.PATCH_FILE_PATH);
//                            downPatch(entity.getPatchFullPath(), sr, entity.getVersionNo());
                            listener.success(entity);
                        } else {
                            //未生成差分包
                            listener.success(null);

                        }
                    }

                    @Override
                    public void onErrorResponse(Response response) {

                    }
                }).builder();
    }

    @Override
    public void getPatchContentOld(String versionNo, Context ctx, GetEntityListener<PatchEntity> listener) {
        new OkHttpClientManager()
                .setUrl(MethodConstants.Base.GET_PATCH_OLD + "?versionNo=" + versionNo)
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            //发现有差分包可以升级
                            PatchEntity entity = new Gson().fromJson(result, PatchEntity.class);
//                            String sr = FileUtils.savePatchPath(LibConfig.PATCH_FILE_PATH);
//                            downPatch(entity.getPatchFullPath(), sr, entity.getVersionNo());
                            listener.success(entity);
                        } else {
                            //未生成差分包
                            listener.success(null);

                        }
                    }

                    @Override
                    public void onErrorResponse(Response response) {

                    }
                }).builder();
    }
}
