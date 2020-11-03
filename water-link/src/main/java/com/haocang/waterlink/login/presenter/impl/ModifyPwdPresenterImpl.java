package com.haocang.waterlink.login.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.utils.ToastUtil;
import com.haocang.waterlink.login.config.LoginMethodConstants;
import com.haocang.waterlink.login.iview.ModifyPasswordView;
import com.haocang.waterlink.login.model.LoginModel;
import com.haocang.waterlink.login.model.impl.LoginModelImpl;
import com.haocang.waterlink.login.presenter.ModifyPwdPresenter;

import org.json.JSONException;
import org.json.JSONObject;

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
 * 创建时间：2018/4/1015:09
 * 修 改 者：
 * 修改时间：
 */
public class ModifyPwdPresenterImpl implements ModifyPwdPresenter {
    private ModifyPasswordView modifyPasswordView;

    public ModifyPwdPresenterImpl(final ModifyPasswordView modifyPasswordView) {
        this.modifyPasswordView = modifyPasswordView;
    }

    /**
     * 修改密码.
     */
    @Override
    public void modifyPwd() {
        String newPwd = modifyPasswordView.getNewPwd();
        String confirmPwd = modifyPasswordView.getConfirmPwd();
        String tel = modifyPasswordView.getTel();
        String verifycationCodeId = modifyPasswordView.getVerifycationCodeId();
        if (TextUtils.isEmpty(newPwd)) {
            modifyPasswordView.pwdEmpty();
        }
//        else if (newPwd.length() < 6) {
//            modifyPasswordView.minLength();
//        } else
//            if (!StringUtils.isContainAll(newPwd)) {
//            modifyPasswordView.setPwdStrength();
//        } else
//            if (newPwd.equals(AppApplication.getInstance().getUserEntity().getPassword())) {
//            modifyPasswordView.pwdNotSame();
//        }
        else if (TextUtils.isEmpty(confirmPwd)) {
            modifyPasswordView.confirmPwdEmpty();
        } else if (!newPwd.equals(confirmPwd)) {
            modifyPasswordView.pwdInequality();
        } else {
            checkPassWord(confirmPwd, tel, verifycationCodeId);
        }

    }

    private void checkPassWord(final String pwd, final String tel, final String verifycationCodeId) {
        Map<String, Object> map = new HashMap<>();
        map.put("password", pwd);
        CommonModel<Integer> model = new CommonModelImpl<>();
        model.setContext(modifyPasswordView.getContexts())
                .setParamMap(map)
                .setUrl(LoginMethodConstants.PAASWORD_STRENGT)
                .setEntityListener(new GetEntityListener<String>() {
                    @Override
                    public void success(final String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            if (jsonObject.isNull("msg")) {
                                Map<String, Object> map = new HashMap<>();
                                map.put("newPassword", pwd);
                                map.put("tel", tel);
                                map.put("verifyCode", verifycationCodeId);
                                changePassword(map);
                            } else {
                                ToastUtil.makeText(modifyPasswordView.getContexts(), jsonObject.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void fail(final String err) {
                        modifyPasswordView.twicePwdError();
                    }
                })
                .getEntityNew();
    }


    private void changePassword(Map<String, Object> map) {
        CommonModel<Integer> model = new CommonModelImpl<>();
        model.setContext(modifyPasswordView.getContexts())
                .setParamMap(map)
                .setEntityType(Integer.class)
                .setUrl(LoginMethodConstants.PASSWORD_FORGET)
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(final Integer entity) {
                        if (entity != null && entity > 0) {
                            setConfiguration();
                            modifyPasswordView.modifySuccess();
                        } else {
                            modifyPasswordView.twicePwdError();
                        }
                    }

                    @Override
                    public void fail(final String err) {
                        modifyPasswordView.twicePwdError();
                    }
                })
                .putEntity();
    }

    @Override
    public void firstLoginModifyPwd() {
        String newPwd = modifyPasswordView.getNewPwd();
        String confirmPwd = modifyPasswordView.getConfirmPwd();
        String tel = modifyPasswordView.getTel();
        if (TextUtils.isEmpty(newPwd)) {
            modifyPasswordView.pwdEmpty();
        } else if (newPwd.length() < 6) {
            modifyPasswordView.minLength();
        } else if (!StringUtils.isContainAll(newPwd)) {
            modifyPasswordView.setPwdStrength();
        } else if (newPwd.equals(AppApplication.getInstance().getUserEntity().getPassword())) {
            modifyPasswordView.pwdNotSame();
        } else if (TextUtils.isEmpty(confirmPwd)) {
            modifyPasswordView.confirmPwdEmpty();
        } else if (!newPwd.equals(confirmPwd)) {
            modifyPasswordView.pwdInequality();
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("newPassword", newPwd);
            map.put("tel", tel);
            firstLoginChangePassword(map);
        }
    }

    private void firstLoginChangePassword(Map<String, Object> map) {
        CommonModel<Integer> model = new CommonModelImpl<>();
        model.setContext(modifyPasswordView.getContexts())
                .setParamMap(map)
                .setEntityType(Integer.class)
                .setUrl(LoginMethodConstants.PASSWORD_NEW)
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(final Integer entity) {
                        if (entity != null && entity > 0) {
                            setConfiguration();
                            modifyPasswordView.modifySuccess();
                        } else {
                            modifyPasswordView.twicePwdError();
                        }
                    }

                    @Override
                    public void fail(final String err) {
                        modifyPasswordView.twicePwdError();
                    }
                })
                .putEntity();
    }


    /**
     * 每次登陆成功后 保存用户名和密码.
     */
    public void setConfiguration() {
        try {
            SharedPreferences sp = modifyPasswordView.getContexts().getSharedPreferences(LibConfig.CHECK, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(LibConfig.USERNAME, modifyPasswordView.getTel());
            edit.putString(LibConfig.PASSWORD, modifyPasswordView.getNewPwd());
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
