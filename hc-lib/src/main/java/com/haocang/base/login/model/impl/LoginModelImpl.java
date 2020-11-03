package com.haocang.base.login.model.impl;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.login.bean.LoginEntity;
import com.haocang.base.login.model.LoginModel;
import com.haocang.base.login.presenter.LoginPresenter;
import com.haocang.base.utils.ProgressBarDialog;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
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
 * 创建时间：2018/1/1017:22
 * 修 改 者：
 * 修改时间：
 */
public class LoginModelImpl implements LoginModel, OkHttpClientManager.OnNetworkResponse {

    /**
     * 结果回掉.
     */
    private LoginPresenter.OnLoginFinishedListener listener;

    private Context ctx;

    /**
     * 登陆.
     *
     * @param ctx      上下文参数.
     * @param username 用户名.
     * @param password
     * @param listener 回掉.
     */
    @Override
    public void login(final Context ctx, final String username, final String password, final LoginPresenter.OnLoginFinishedListener listener) {
        this.listener = listener;
        this.ctx = ctx;
        AddParameters addParameters = new AddParameters();
        addParameters.addParam("username", username);
        addParameters.addParam("password", password);
        new OkHttpClientManager()
                .setRequestBody(addParameters.formBody())
                .setOnNetWorkReponse(this)
                .setLoadDialog(new ProgressBarDialog(ctx))
                .setUrl(MethodConstants.Login.LOGIN)
                .setRequestMethod(LibConfig.HTTP_POST)
                .builder();
    }

    @Override
    public void loginNoLoad(Context ctx, String username, String passWord, LoginPresenter.OnLoginFinishedListener listener) {
        this.listener = listener;
        this.ctx = ctx;
        AddParameters addParameters = new AddParameters();
        addParameters.addParam("username", username);
        addParameters.addParam("password", passWord);
        new OkHttpClientManager()
                .setRequestBody(addParameters.formBody())
                .setOnNetWorkReponse(this)
                .setUrl(MethodConstants.Login.LOGIN)
                .setRequestMethod(LibConfig.HTTP_POST)
                .builder();
    }

    /**
     * 获取到数据源.
     *
     * @param result 结果.
     */
    @Override
    public void onNetworkResponse(final String result) {
        if (!TextUtils.isEmpty(result)) {
            LoginEntity entity = new Gson().fromJson(result.toString(), LoginEntity.class);
            LibConfig.accessToken = "access_token=" + entity.getAccess_token();
            LibConfig.sessionToken = ";session_token=" + entity.getRefresh_token();
            LibConfig.cookie = LibConfig.accessToken + LibConfig.sessionToken;//todo 每次获取接口的时候需要刷新refreshToken所以就不存全局
            LibConfig.setCookie(LibConfig.cookie);
        }
        listener.loginSuccess();
    }

    /**
     * 异常代码.
     */
    @Override
    public void onErrorResponse(final Response response) {
        Headers responseHeaders = response.headers();
        List<String> list = new ArrayList<>();
        for (String headerName : responseHeaders.names()) {
            list.addAll(responseHeaders.values(headerName));
        }
        for (String value : list) {
            if ("uaa_auth_notfound".equals(value)) {
                listener.usernameError();//用戶不存在
                break;
            } else if ("uaa_auth_passwordError".equals(value)) {
                listener.passwordError();//密碼錯誤
                break;
            } else if ("uaa_auth_expire".equals(value)) {
                listener.isLeaseValid();//
                break;
            } else if ("uaa_auth_countOverLimit".equals(value)) {
                listener.inputError();//超過五次
                break;
            }
        }
    }
}
