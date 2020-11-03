package com.haocang.waterlink.login.presenter.impl;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.RequiresApi;
import android.text.TextUtils;

import com.haocang.base.config.LibConfig;
import com.haocang.waterlink.login.iview.LoginView;
import com.haocang.waterlink.login.model.LoginModel;
import com.haocang.waterlink.login.model.impl.LoginModelImpl;
import com.haocang.waterlink.login.presenter.LoginPresenter;
import com.haocang.waterlink.utils.PictureSlippingDialog;
import com.luozm.captcha.Captcha;

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
 * 创建时间：2018/1/1017:23
 * 修 改 者：
 * 修改时间：
 */
public class LoginPresenterImpl implements LoginPresenter, LoginPresenter.OnLoginFinishedListener {
    /**
     * 是否显示密码.
     */
    private boolean isDisplayPass = false;
    /**
     * v层获取到数据.
     */
    private LoginView homeIView;
    /**
     * m层访问网络.
     */
    private LoginModel homeModel;
    /**
     * 密码最小长度.
     */
    private static final int MINLENGTH = 6;
    /**
     * 密码最大长度.
     */
    private static final int MAXLENGTH = 20;
    /**
     * 把用户名和密码存储下来.
     */
    private SharedPreferences sp;

    /**
     * 错误次数.
     */
    private int errorCount = 1;

    private PictureSlippingDialog slippingDialog;

    /**
     * 构造方法.
     *
     * @param homeIView .
     */
    public LoginPresenterImpl(final LoginView homeIView) {
        this.homeIView = homeIView;
        this.homeModel = new LoginModelImpl();
    }

    /**
     * 每次登陆成功后 保存用户名和密码.
     */
    public void setConfiguration() {
        try {
            sp = homeIView.getContexts().getSharedPreferences(LibConfig.CHECK, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(LibConfig.USERNAME, homeIView.getUserName());
            edit.putString(LibConfig.PASSWORD, homeIView.getPassWord());
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 登陆.
     */
    @Override
    public void login() {
        if (errorCount > 3) {
            slippingDialog = new PictureSlippingDialog(homeIView.getContexts(), captchaListener);
            slippingDialog.show();
        } else {
            loginSystem();
        }


    }

    private void loginSystem() {
        if (verification()) {
            homeModel.login(homeIView.getContexts(), homeIView.getUserName(), homeIView.getPassWord(), this);
        }
    }

    /**
     * @return 校验输入的值是否合法.
     */
    private boolean verification() {

//            if (!StringUtils.isMobile(homeIView.getUserName())) {
//            verificationFlag = false;
//            homeIView.formattingError();
//        } else
        boolean verificationFlag;
        if (isUsernameEmpty()) {
            homeIView.usernameEmpty();
            verificationFlag = false;
        } else if (isPasswordEmpty()) {
            homeIView.passWordEmpty();
            verificationFlag = false;
        } else if (isPasswordValid(homeIView.getPassWord())) {
            homeIView.setPasswordLength();
            verificationFlag = false;
        } else {
            verificationFlag = true;
        }
        return verificationFlag;
    }

    /**
     * 密码是否合法.
     *
     * @param password 密码
     * @return .
     */
    public boolean isPasswordValid(final String password) {
        return password.length() < MINLENGTH || password.length() > MAXLENGTH;
    }

    /**
     * 设置密码是否明文显示.
     */
    @Override
    public void setDisplayPass() {
        if (!isDisplayPass) {
            isDisplayPass = true;
        } else {
            isDisplayPass = false;
        }
        homeIView.togglePassword(isDisplayPass);
    }

    /**
     * 密码为空.
     */
    @Override
    public boolean isPasswordEmpty() {
        return TextUtils.isEmpty(homeIView.getPassWord());
    }

    /**
     * 用户名为空.
     */
    @Override
    public boolean isUsernameEmpty() {
        return TextUtils.isEmpty(homeIView.getUserName());
    }

    /**
     * 。。。.
     *
     * @param flag .
     */
    @Override
    public void displayPass(final boolean flag) {
        homeIView.togglePassword(flag);
    }

    /**
     * 用户名错误.
     */
    @Override
    public void usernameError() {
        homeIView.usernameError();
    }

    /**
     * 密码错误.
     */
    @Override
    public void passwordError() {
        errorCount++;
        homeIView.passwordError();
    }


    /**
     * 租期是否到期.
     */
    @Override
    public void isLeaseValid() {
        homeIView.isLeaseValid();
    }

    /**
     * 登陆成功.
     */
    @Override
    public void loginSuccess() {
        setConfiguration();
        homeIView.loginSuccess();
    }

    /**
     * 连续输入超过5次.
     */
    @Override
    public void inputError() {
        homeIView.inputError();
    }

    private Captcha.CaptchaListener captchaListener = new Captcha.CaptchaListener() {
        @Override
        public String onAccess(long time) {
            loginSystem();
            slippingDialog.cancel();
            return "验证通过,耗时" + time + "毫秒";
        }

        @Override
        public String onFailed(final int failedCount) {
            return "验证失败,已失败" + failedCount + "次";
        }

        @Override
        public String onMaxFailed() {
//                Toast.makeText(ctx, "验证超过次数，你的帐号被封锁", Toast.LENGTH_SHORT).show();
            return "";
        }
    };

}
