package com.haocang.waterlink.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.haocang.base.bean.UserEntity;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.iview.UserInfoView;
import com.haocang.base.presenter.UserInfoPresenter;
import com.haocang.base.presenter.impl.UserInfoPresenterImpl;
import com.haocang.base.utils.ProgressBarDialog;
import com.haocang.offline.bean.user.OffLineUserEntity;
import com.haocang.offline.dao.UserDaoManager;
import com.haocang.waterlink.login.bean.LoginEntity;

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
 * 创 建 者：hy
 * 创建时间：${DATA} 13:34
 * 修 改 者：
 * 修改时间：
 */
public class AutomaticLogonUtils implements UserInfoView {

    private Activity mActivity;

    private OffLineUserEntity userEntity;

    private AutomaticLogonInterface logonInterface;

    private boolean recordUser = false;
    private UserInfoPresenter userInfoPresenter;

    public AutomaticLogonUtils(Activity mActivity) {
        this.mActivity = mActivity;
        userInfoPresenter = new UserInfoPresenterImpl(this);

    }

    public AutomaticLogonUtils setAutomaticLogon(AutomaticLogonInterface logonInterface) {
        this.logonInterface = logonInterface;
        return this;
    }

    public AutomaticLogonUtils setUserEntity(OffLineUserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }

    public void login() {
        AddParameters addParameters = new AddParameters();
        addParameters.addParam("username", userEntity.getTel());
        addParameters.addParam("password", userEntity.getPassword());
        new OkHttpClientManager()
                .setRequestBody(addParameters.formBody())
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            LoginEntity entity = new Gson().fromJson(result.toString(), LoginEntity.class);
                            LibConfig.cookie = "access_token=" + entity.getAccess_token() + ";refresh_token=" + entity.getRefresh_token();
                            LibConfig.setCookie(LibConfig.cookie);
                            getUserInformation();
                        }
                    }

                    @Override
                    public void onErrorResponse(Response response) {
                        Headers responseHeaders = response.headers();
                        List<String> list = new ArrayList<>();
                        for (String headerName : responseHeaders.names()) {
                            list.addAll(responseHeaders.values(headerName));
                        }
                        for (String value : list) {
                            if ("uaa_auth_notfound".equals(value)) {
                                if (logonInterface != null)
                                    logonInterface.userNameError();
                                new UserDaoManager().deleteUser(userEntity);
                            } else if ("uaa_auth_passwordError".equals(value)) {
                                if (logonInterface != null)
                                    logonInterface.passWordError();
                                new UserDaoManager().deleteUser(userEntity);
                            } else if ("uaa_auth_expire".equals(value)) {
//                                listener.isLeaseValid();//
                                if (logonInterface != null)
                                    logonInterface.userNameError();
                            } else if ("uaa_auth_countOverLimit".equals(value)) {
//                                listener.inputError();//超過五次
                                if (logonInterface != null)
                                    logonInterface.userNameError();
                            } else {
                                logonInterface.passWordError();
                            }
                        }
                    }
                })
                .setLoadDialog(new ProgressBarDialog(mActivity))
                .setUrl(MethodConstants.Login.LOGIN)
                .setRequestMethod(LibConfig.HTTP_POST)
                .builder();
    }

    //@todo 获取用户信息失败的回调未做
    private void getUserInformation() {
        userInfoPresenter.getUserInfo();
    }

    @Override
    public Context getContext() {
        return mActivity;
    }

    @Override
    public void setUserInfo(UserEntity entity) {
        entity.setPassword(userEntity.getPassword());
        AppApplication.getInstance().setUserEntity(entity);
        try {
            UserDaoManager user = new UserDaoManager();
            user.insertUser(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (logonInterface != null)
            logonInterface.loginSuccess();
    }


    public interface AutomaticLogonInterface {
        void loginSuccess();

        void passWordError();

        void userNameError();
    }

}
