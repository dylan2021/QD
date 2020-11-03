package com.haocang.waterlink.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.haocang.base.bean.UserEntity;
import com.haocang.base.config.AppApplication;
import com.haocang.base.iview.UserInfoView;
import com.haocang.base.presenter.UserInfoPresenter;
import com.haocang.base.presenter.impl.UserInfoPresenterImpl;
import com.haocang.base.utils.ToastUtil;
import com.haocang.messge.service.MessgeService;
import com.haocang.offline.dao.UserDaoManager;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.ui.HomeFragment;
import com.haocang.waterlink.home.ui.NavigationActivity;
import com.haocang.waterlink.login.iview.ModifyPasswordView;
import com.haocang.waterlink.login.model.LoginModel;
import com.haocang.waterlink.login.model.impl.LoginModelImpl;
import com.haocang.waterlink.login.presenter.LoginPresenter;
import com.haocang.waterlink.login.presenter.ModifyPwdPresenter;
import com.haocang.waterlink.login.presenter.impl.ModifyPwdPresenterImpl;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：修改密码
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/1/1216:09
 * 修 改 者：
 * 修改时间：
 */
public class ModifyPwdFragment extends Fragment implements ModifyPasswordView, UserInfoView, View.OnClickListener {

    private EditText newPwdEt;
    private EditText confirmPwdEt;
    private ModifyPwdPresenter presenter;
    private UserInfoPresenter userInfoPresenter;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_modify_pwd, null);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        userInfoPresenter = new UserInfoPresenterImpl(this);
        newPwdEt = view.findViewById(R.id.userame_et);
        confirmPwdEt = view.findViewById(R.id.password_et);
        view.findViewById(R.id.next_step_btn).setOnClickListener(this);
        presenter = new ModifyPwdPresenterImpl(this);
    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public void modifySuccess() {
//        Intent intent = new Intent(getActivity(), CommonActivity.class);
//        intent.putExtra("fragmentName", LoginFragment.class.getName());
//        intent.putExtra("statu", true);
//        startActivity(intent);
        LoginModel loginModel = new LoginModelImpl();
        loginModel.login(getActivity(), getTel(), getNewPwd(), new LoginPresenter.OnLoginFinishedListener() {
            @Override
            public void usernameError() {

            }

            @Override
            public void passwordError() {

            }

            @Override
            public void isLeaseValid() {

            }

            @Override
            public void loginSuccess() {
                getUserInformation();
            }

            @Override
            public void inputError() {

            }
        });
    }

    private void getUserInformation() {
        userInfoPresenter.getUserInfo();
    }

    /**
     * 把当前用户信息存在本地
     */
    private void addUserToLocal(UserEntity entity) {
        UserDaoManager user = new UserDaoManager();
        user.insertUser(entity);
    }

    @Override
    public String getNewPwd() {
        return newPwdEt.getText().toString().trim();
    }

    @Override
    public String getVerifycationCodeId() {
        return getActivity().getIntent().getStringExtra("verifycationCodeId");
    }

    @Override
    public String getConfirmPwd() {
        return confirmPwdEt.getText().toString().trim();
    }

    @Override
    public void setPwdStrength() {
        ToastUtil.makeText(getActivity(), getString(R.string.pwdstrength));
    }

    @Override
    public void twicePwdError() {
        ToastUtil.makeText(getActivity(), getString(R.string.modifypwd_error));
    }

    @Override
    public void pwdEmpty() {
        ToastUtil.makeText(getActivity(), getString(R.string.newpwd_empty));
    }

    @Override
    public void confirmPwdEmpty() {
        ToastUtil.makeText(getActivity(), getString(R.string.confirm_pwd_empty));
    }

    @Override
    public void pwdInequality() {
        ToastUtil.makeText(getActivity(), getString(R.string.pwdinequality));
    }

    @Override
    public void minLength() {
        ToastUtil.makeText(getActivity(), getString(R.string.pwdlength));
    }

    @Override
    public void pwdNotSame() {
        ToastUtil.makeText(getActivity(), getString(R.string.pwdNotSame));
    }

    @Override
    public String getTel() {
        return getActivity().getIntent().getStringExtra("tel");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.next_step_btn) {
            isFirstLogin();
        }
    }

    /**
     * isFirstLogin true 首次登录，false 忘记密码
     */
    private void isFirstLogin() {
        boolean isFirstLogin = getActivity().getIntent().getBooleanExtra("isFirstLogin", false);
        if (isFirstLogin) {
            presenter.firstLoginModifyPwd();
        } else {
            presenter.modifyPwd();
        }
    }

    /**
     * 开始服务轮循消息.
     */
    private void startMessgeService() {
//        LibConfig.startMessageService(getContexts());
        Intent intent = new Intent(getActivity(), MessgeService.class);
        getActivity().startService(intent);
//
//        ARouterUtil.toActivity(null, ArouterPathConstants.Message.MESSAGE_SERVICE);
    }

    @Override
    public void setUserInfo(UserEntity entity) {
        entity.setPassword(newPwdEt.getText().toString());
        AppApplication.getInstance().setUserEntity(entity);
        addUserToLocal(entity);
        startMessgeService();
        Intent intent = new Intent(getActivity(), NavigationActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
