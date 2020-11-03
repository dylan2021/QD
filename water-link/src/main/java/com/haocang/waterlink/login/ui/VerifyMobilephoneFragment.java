package com.haocang.waterlink.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.config.LibConfig;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.ToastUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.login.iview.VerifyMobilephoneView;
import com.haocang.waterlink.login.presenter.VerifyMobilePresenter;
import com.haocang.waterlink.login.presenter.impl.VerifyMobilePresenterImpl;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：  忘记密码 、 找回密码
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/1/1113:44
 * 修 改 者：
 * 修改时间：
 */


@Route(path = LibConfig.AROUTE_VERIFYMOBILE)
public class VerifyMobilephoneFragment extends Fragment implements View.OnClickListener, VerifyMobilephoneView {
    /**
     * 电话输入框.
     */
    private EditText phoneEt;
    /**
     * 验证码输入框.
     */
    private EditText verificationCodeTv;
    /**
     * 验证码按钮
     */
    private TextView codeTv;

    private VerifyMobilePresenter presenter;

    /**
     * 初始化.
     *
     * @param inflater           .
     * @param container          .
     * @param savedInstanceState .
     * @return
     */
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_retrieve_pwd, null);
        initView(view);
        return view;
    }


    /**
     * 初始化.
     *
     * @param view .
     */
    private void initView(final View view) {
        presenter = new VerifyMobilePresenterImpl(this);
        codeTv = view.findViewById(R.id.send_code_tv);
        codeTv.setOnClickListener(this);
        phoneEt = view.findViewById(R.id.userame_et);
        view.findViewById(R.id.next_step_btn).setOnClickListener(this);
        verificationCodeTv = view.findViewById(R.id.password_et);
        setPhoneData();
    }

    /**
     * 验证码倒计时类.
     */
    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(final long millisUntilFinished) {
            codeTv.setEnabled(false);
            codeTv.setText((millisUntilFinished / 1000) + getActivity().getResources().getString(R.string.second));

        }

        @Override
        public void onFinish() {
            codeTv.setEnabled(true);
            codeTv.setText(getActivity().getResources().getString(R.string.get_verifying_code));
        }
    };

    /**
     * @param view
     */
    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.send_code_tv) {
            presenter.sendVerifyMobile();
        } else if (view.getId() == R.id.next_step_btn) {
            presenter.startVerificationPhoneNumber();
        }
    }

    /**
     * @return
     */
    @Override
    public String getPhone() {
        return phoneEt.getText().toString().trim();
    }

    @Override
    public String getVerifyMobileCode() {
        return verificationCodeTv.getText().toString().trim();
    }

    @Override
    public void sendSuccess() {
        ToastUtil.makeText(getActivity(), getString(R.string.send_verification_code));
    }

    @Override
    public void sendError() {
        ToastUtil.makeText(getActivity(), getString(R.string.send_verification_error));
    }

    @Override
    public void setPhomeWrongful() {
        ToastUtil.makeText(getActivity(), getString(R.string.formatting_error));
    }

    @Override
    public void phoneEmpty() {
        ToastUtil.makeText(getActivity(), getString(R.string.phone_is_empty));
    }

    @Override
    public void verifyMobileCodeEmpty() {
        ToastUtil.makeText(getActivity(), getString(R.string.verifymobile_empty));
    }

    @Override
    public void startCountDown() {
        timer.start();
    }


    @Override
    public void getVerificationResult(final String isSuccess) {
        if (isSuccess != null) {
            ToastUtil.makeText(getActivity(), getString(R.string.verifymobile_sucess));
            Intent intent = new Intent(getActivity(), CommonActivity.class);
            intent.putExtra("fragmentName", ModifyPwdFragment.class.getName());
            intent.putExtra("tel", phoneEt.getText().toString().trim());
            intent.putExtra("FULL_SCREEN", true);
//            intent.putExtra("`", isSuccess);
            intent.putExtra("verifycationCodeId", verificationCodeTv.getText().toString());
            startActivity(intent);
        } else {
            ToastUtil.makeText(getActivity(), getString(R.string.verifymobile_error));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    /**
     * 把最后一次登录的用户名和密码显示出来.
     */
    public void setPhoneData() {
        phoneEt.setText(getUserName());
    }

    private String getUserName() {
        return getActivity().getIntent().getStringExtra("username");
    }
}
