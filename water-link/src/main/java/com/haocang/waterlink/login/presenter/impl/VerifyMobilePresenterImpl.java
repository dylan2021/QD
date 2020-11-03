package com.haocang.waterlink.login.presenter.impl;

import android.text.TextUtils;
import android.util.Log;

import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.utils.ToastUtil;
import com.haocang.messge.constans.MessageConstants;
import com.haocang.waterlink.login.config.LoginMethodConstants;
import com.haocang.waterlink.login.iview.VerifyMobilephoneView;
import com.haocang.waterlink.login.presenter.VerifyMobilePresenter;

import java.util.HashMap;
import java.util.Map;

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
 * 创建时间：2018/4/815:25
 * 修 改 者：
 * 修改时间：
 */
public class VerifyMobilePresenterImpl implements VerifyMobilePresenter {
    private VerifyMobilephoneView verifyMobilephoneView;

    /**
     * 构造方法.
     */
    public VerifyMobilePresenterImpl(final VerifyMobilephoneView verifyMobilephoneView) {
        this.verifyMobilephoneView = verifyMobilephoneView;
    }


    /**
     * 判断手机号码是否存在
     */
    private void checkPhone() {
        Map<String, Object> map = new HashMap<>();
        map.put("tel", verifyMobilephoneView.getPhone());
        CommonModel<Integer> model = new CommonModelImpl<>();
        model.setContext(verifyMobilephoneView.getContext())
                .setParamMap(map)
                .setHasDialog(false)
                .setUrl(LoginMethodConstants.VALIDATE_TEL)
                .setEntityListener(new GetEntityListener<String>() {
                    @Override
                    public void success(final String result) {
                        if ("true".equals(result)) {
                            getCode();
                        } else {
                            ToastUtil.makeText(verifyMobilephoneView.getContext(), "手机号码不存在");
                        }
                    }

                    @Override
                    public void fail(final String err) {
                        verifyMobilephoneView.sendError();
                    }
                })
                .getEntityNew();
    }

    /**
     * 发送验证码.
     */
    @Override

    public void sendVerifyMobile() {
        if (!StringUtils.isMobile(verifyMobilephoneView.getPhone())) {
            verifyMobilephoneView.setPhomeWrongful();
        } else if (TextUtils.isEmpty(verifyMobilephoneView.getPhone())) {
            verifyMobilephoneView.phoneEmpty();
        } else {
            checkPhone();
        }
    }

    private void getCode() {
        Map<String, Object> map = new HashMap<>();
        map.put("tel", verifyMobilephoneView.getPhone());
        CommonModel<Integer> model = new CommonModelImpl<>();
        model.setContext(verifyMobilephoneView.getContext())
                .setParamMap(map)
                .setEntityType(Integer.class)
                .setHasDialog(false)
                .setUrl(MessageConstants.MethodConstants.GET_CAPTCHA)
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(final Integer entity) {
                        verifyMobilephoneView.startCountDown();
                        verifyMobilephoneView.sendSuccess();
                    }

                    @Override
                    public void fail(final String err) {
                        verifyMobilephoneView.sendError();
                    }
                })
                .postEntity();
    }

    /**
     * 开始验证验证码和手机号码是否正确.
     */
    @Override
    public void startVerificationPhoneNumber() {
        if (!StringUtils.isMobile(verifyMobilephoneView.getPhone())) {
            verifyMobilephoneView.setPhomeWrongful();
        } else if (TextUtils.isEmpty(verifyMobilephoneView.getPhone())) {
            verifyMobilephoneView.phoneEmpty();
        } else if (TextUtils.isEmpty(verifyMobilephoneView.getVerifyMobileCode())) {
            verifyMobilephoneView.verifyMobileCodeEmpty();
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("tel", verifyMobilephoneView.getPhone());
            map.put("captchaValue", verifyMobilephoneView.getVerifyMobileCode());
//            map.put("markUsed", true);
            CommonModel<String> progressModel = new CommonModelImpl<>();
            progressModel
                    .setContext(verifyMobilephoneView.getContext())
                    .setParamMap(map)
                    .setUrl(MessageConstants.MethodConstants.CAPTCHA_VALIDITY)
                    .setEntityListener(new GetEntityListener<String>() {
                        @Override
                        public void success(final String entity) {
                            verifyMobilephoneView.getVerificationResult(entity + "");
                        }

                        @Override
                        public void fail(final String err) {
                        }
                    }).getEntityNew();
        }
    }

}
