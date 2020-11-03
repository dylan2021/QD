package com.haocang.waterlink.self.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.AppApplication;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.utils.ToastUtil;
import com.haocang.waterlink.login.config.LoginMethodConstants;
import com.haocang.waterlink.self.iview.SelfModifyPassWordView;
import com.haocang.waterlink.self.presenter.SelfModifyPassWordPresenter;

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
 * 创建时间：2018/4/1117:09
 * 修 改 者：
 * 修改时间：
 */
public class SelfModifyPassWordPresenterImpl implements SelfModifyPassWordPresenter {
    private SelfModifyPassWordView selfModifyPassWordView;

    public SelfModifyPassWordPresenterImpl(final SelfModifyPassWordView selfModifyPassWordView) {
        this.selfModifyPassWordView = selfModifyPassWordView;
    }

    @Override
    public void doneUpdataPwd() {
        if (verification()) {
            checkPassWord(selfModifyPassWordView.getNewPwd());
        }
    }

    private void updatePwd() {
        Map<String, Object> map = new HashMap<>();
        String userId = selfModifyPassWordView.getUserId();
        map.put("newPassword", selfModifyPassWordView.getNewPwd());
        map.put("oldPassword", selfModifyPassWordView.getOldPwd());
        map.put("userId", Integer.parseInt(userId));
        CommonModel<Integer> model = new CommonModelImpl<>();
        model.setContext(selfModifyPassWordView.getContexts())
                .setParamMap(map)
                .setEntityType(Integer.class)
                .setUrl(LoginMethodConstants.PASSWORD_CHANGE)
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(final Integer entity) {
                        selfModifyPassWordView.updataSucess();
                    }

                    @Override
                    public void fail(final String err) {
                        selfModifyPassWordView.updataError();
                    }
                })
                .putEntity();
    }

    private void checkPassWord(final String pwd) {
        Map<String, Object> map = new HashMap<>();
        map.put("password", pwd);
        CommonModel<Integer> model = new CommonModelImpl<>();
        model.setContext(selfModifyPassWordView.getContexts())
                .setParamMap(map)
                .setUrl(LoginMethodConstants.PAASWORD_STRENGT)
                .setEntityListener(new GetEntityListener<String>() {
                    @Override
                    public void success(final String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            if (jsonObject.isNull("msg")) {
                                updatePwd();
                            } else {
                                ToastUtil.makeText(selfModifyPassWordView.getContexts(), jsonObject.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void fail(final String err) {
                    }
                })
                .getEntityNew();
    }

    /**
     * @return true通过，false不通过
     */
    private boolean verification() {
        String localPwd = selfModifyPassWordView.getLocalPwd();//存储在本地的密码
        String oldPwd = selfModifyPassWordView.getOldPwd();//输入的旧密码
        String newPwd = selfModifyPassWordView.getNewPwd();//输入的新密码
        String confirmPwd = selfModifyPassWordView.getConfirmPwd();//二次输入的新密码
        if (TextUtils.isEmpty(oldPwd)) {
            selfModifyPassWordView.oldPwdEmpty();
            return false;
        } else if (!localPwd.equals(oldPwd)) {
            selfModifyPassWordView.oldPwdIncorrect();
            return false;
        } else if (newPwd.length() < 6) {
            selfModifyPassWordView.newPwdLength();
            return false;
        } else
//            if (!StringUtils.isContainAll(newPwd)) {
//            selfModifyPassWordView.setPwdStrength();
//            return false;
//        } else

            if (TextUtils.isEmpty(newPwd)) {
                selfModifyPassWordView.newPwdEmtpy();
                return false;
            } else if (TextUtils.isEmpty(confirmPwd)) {
                selfModifyPassWordView.confirmPwdEmpty();
                return false;
            } else if (!newPwd.equals(confirmPwd)) {
                selfModifyPassWordView.pwdInequality();
                return false;
            } else if (oldPwd.equals(newPwd)) {
                selfModifyPassWordView.pwDidentical();
                return false;
            } else {
                return true;
            }
    }

}
