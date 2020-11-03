package com.haocang.waterlink.self.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.haocang.base.config.LibConfig;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.ToastUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.login.ui.LoginFragment;
import com.haocang.waterlink.self.iview.SelfModifyPassWordView;
import com.haocang.waterlink.self.presenter.SelfModifyPassWordPresenter;
import com.haocang.waterlink.self.presenter.impl.SelfModifyPassWordPresenterImpl;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题： 修改密码.
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/3/2613:34
 * 修 改 者：
 * 修改时间：
 */
public class SelfModifyPassWordFragment extends Fragment implements View.OnClickListener, SelfModifyPassWordView, View.OnFocusChangeListener {

    /**
     * 标题.
     */
    private TextView titleNameTv;


    /**
     * 旧密码.
     */
    private EditText oldPwdEt;
    /**
     * 新密码.
     */
    private EditText newPwdEt;

    /**
     * 确认密码.
     */
    private EditText confirmPwdEt;

    private SharedPreferences sp;

    private SelfModifyPassWordPresenter presenter;

    private View oldView;

    private View newPwdView;

    private View confirmView;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self_modify_pwd, null);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        presenter = new SelfModifyPassWordPresenterImpl(this);
        sp = getActivity().getSharedPreferences(LibConfig.CHECK, Context.MODE_PRIVATE);
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getResources().getString(R.string.modify_password));
        oldPwdEt = view.findViewById(R.id.old_pwd_et);
        newPwdEt = view.findViewById(R.id.new_pwd_et);
        confirmPwdEt = view.findViewById(R.id.confirm_pwd);
        view.findViewById(R.id.submit_tv).setOnClickListener(this);
        oldPwdEt.setOnFocusChangeListener(this);
        newPwdEt.setOnFocusChangeListener(this);
        confirmPwdEt.setOnFocusChangeListener(this);
        oldView = view.findViewById(R.id.old_view_view);
        newPwdView = view.findViewById(R.id.new_pwd_view);
        confirmView = view.findViewById(R.id.confirm_view);
        TextView phoneTv = view.findViewById(R.id.self_phone_tv);
        phoneTv.setText(getAccount());

    }

    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.submit_tv) {
            presenter.doneUpdataPwd();
        }
    }


    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public String getAccount() {
        return sp.getString(LibConfig.USERNAME, "");
    }

    @Override
    public String getOldPwd() {
        return oldPwdEt.getText().toString().trim();
    }

    @Override
    public String getNewPwd() {
        return newPwdEt.getText().toString().trim();
    }

    @Override
    public String getConfirmPwd() {
        return confirmPwdEt.getText().toString().trim();
    }

    @Override
    public String getLocalPwd() {
        return sp.getString(LibConfig.PASSWORD, "");
    }


    @Override
    public void oldPwdEmpty() {
        ToastUtil.makeText(getActivity(), getString(R.string.oldpwd_empty));
    }

    @Override
    public void newPwdEmtpy() {
        ToastUtil.makeText(getActivity(), getString(R.string.newpwd_empty));
    }

    @Override
    public void confirmPwdEmpty() {
        ToastUtil.makeText(getActivity(), getString(R.string.confirm_pwd_empty));
    }

    @Override
    public void updataSucess() {
        setConfiguration();
        ToastUtil.makeText(getActivity(), "密码修改成功，请重新登录");
        Intent intent = new Intent(getActivity(), CommonActivity.class);
        intent.putExtra("fragmentName", LoginFragment.class.getName());
        intent.putExtra("type", "type");
        startActivity(intent);
    }

    @Override
    public void updataError() {
        ToastUtil.makeText(getActivity(), getString(R.string.updata_error));
    }

    @Override
    public void oldPwdIncorrect() {
        ToastUtil.makeText(getActivity(), getString(R.string.old_incorrect));
    }

    @Override
    public void newPwdLength() {
        ToastUtil.makeText(getActivity(), getString(R.string.new_pwdlength));
    }

    @Override
    public void pwdInequality() {
        ToastUtil.makeText(getActivity(), getString(R.string.pwdinequality));
    }

    @Override
    public String getUserId() {
        return getActivity().getIntent().getStringExtra("userId");
    }

    @Override
    public void pwDidentical() {
        ToastUtil.makeText(getActivity(), getString(R.string.pwdidentical));
    }

    @Override
    public void setPwdStrength() {
        ToastUtil.makeText(getActivity(), getString(R.string.pwdstrength));
    }


    /**
     * 每次登陆成功后 保存用户名和密码.
     */
    public void setConfiguration() {
        try {
            sp = getActivity().getSharedPreferences(LibConfig.CHECK, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(LibConfig.PASSWORD, "");
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        if (id == R.id.old_pwd_et) {
            setHintColor(oldView, hasFocus);
        } else if (id == R.id.new_pwd_et) {
            setHintColor(newPwdView, hasFocus);
        } else if (id == R.id.confirm_pwd) {
            setHintColor(confirmView, hasFocus);
        }
    }

    private void setHintColor(View view, boolean hasFocus) {
        if (hasFocus) {
            view.setBackgroundColor(Color.parseColor("#0cabdf"));
        } else {
            view.setBackgroundColor(Color.parseColor("#f9f9f9"));
        }
    }
}
