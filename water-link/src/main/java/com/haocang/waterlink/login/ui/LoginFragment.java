package com.haocang.waterlink.login.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.alertview.AlertView;
import com.haocang.base.bean.UserEntity;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.iview.UserInfoView;
import com.haocang.base.presenter.UserInfoPresenter;
import com.haocang.base.presenter.impl.UserInfoPresenterImpl;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.constants.OfflineConstants;
import com.haocang.messge.service.MessgeService;
import com.haocang.offline.dao.UserDaoManager;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.ui.HomeFragment;
import com.haocang.waterlink.home.ui.NavigationActivity;
import com.haocang.waterlink.login.iview.LoginView;
import com.haocang.waterlink.login.presenter.LoginPresenter;
import com.haocang.waterlink.login.presenter.impl.LoginPresenterImpl;
import com.haocang.waterlink.utils.HomeJumpUtil;

import okhttp3.Response;


/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 产品规格书 Version 0.01
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：  登录页面
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/1/1015:58
 * 修 改 者：
 * 修改时间：
 */

@Route(path = LibConfig.AROUTE_LOGIN)
public class LoginFragment extends Fragment implements LoginView, View.OnClickListener,
        UserInfoView, PermissionsProcessingUtil.OnPermissionsCallback {

    /**
     * 控制层.
     */
    private LoginPresenter presenter;
    /**
     * 用户名输入框.
     */
    private EditText usernameEt; //账号
    /**
     * 密码输入框.
     */
    private EditText passwordEt; //密码

    private SharedPreferences sp;

//    private boolean isLoginBaiduSuccess;

    private UserInfoPresenter userInfoPresenter;

    // 两次点击按钮之间的点击间隔
    private int MIN_CLICK_DELAY_TIME = 500;
    private long lastClickTime;

    /**
     * 初始化.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        userInfoPresenter = new UserInfoPresenterImpl(this);
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_login, null);
        sp = getActivity().getSharedPreferences(LibConfig.HOME_SETUP, Context.MODE_PRIVATE);
        initView(view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        PermissionsProcessingUtil.setPermissions(this, LibConfig.AUDIO, this);
        OffLineOutApiUtil.deleteNotToDayTask();//删除所有非今日任务巡检点，巡检步骤
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    /**
     * 初始化控件.
     *
     * @param view .
     */
    private void initView(final View view) {
        usernameEt = view.findViewById(R.id.userame_et);
        passwordEt = view.findViewById(R.id.password_et);
        presenter = new LoginPresenterImpl(this);
        setData();
        if (!TextUtils.isEmpty(getType())) {
            AppApplication.getInstance().finisBussActivity(getActivity());
        }
        accessBaidu(new AccessBaiduListener() {
            @Override
            public void success() {
            }

            @Override
            public void failed() {
                tipOffLine();
            }
        });
        view.findViewById(R.id.sign_in_btn).setOnClickListener(this);
        view.findViewById(R.id.forgetPwd_tv).setOnClickListener(this);
        view.findViewById(R.id.ip_btn).setOnClickListener(this);

        //todo 自动登录(调试),后面删掉
        //presenter.login();
    }


    private void tipOffLine() {
        new AlertView(getResources().getString(R.string.offline_tip),
                getResources().getString(R.string.offLine_no_signal),
                getResources().getString(R.string.offLine_cancle),
                new String[]{getResources().getString(R.string.offLine_confirm)}, null, getActivity(), AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position >= 0) {
                    ARouterUtil.toFragment(OfflineConstants.OFFLINE_LOGIN);
                }
            }
        }).show();
    }


    /**
     * 把最后一次登录的用户名和密码显示出来.
     */
    public void setData() {
        try {
            SharedPreferences sp = getActivity().getSharedPreferences(LibConfig.CHECK, Context.MODE_PRIVATE);
            usernameEt.setText(sp.getString(LibConfig.USERNAME, ""));
            passwordEt.setText(sp.getString(LibConfig.PASSWORD, ""));
            boolean isModifyStatue = getActivity().getIntent().
                    getBooleanExtra("statu", false);
            if (isModifyStatue) {
                presenter.login();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return 获取输入框中的用户名.
     */
    @Override
    public String getUserName() {
        return usernameEt.getText().toString().trim();
    }

    /**
     * @return 获取输入框中的密码.
     */
    @Override
    public String getPassWord() {
        return passwordEt.getText().toString().trim();
    }

    /**
     * @return 获取上下文参数.43
     */
    @Override
    public Context getContexts() {
        return getActivity();
    }

    /**
     * 密码为空.
     */
    @Override
    public void passWordEmpty() {
        ToastUtil.makeText(getActivity(), "密码不能为空");
    }

    /**
     * 用户名不存在.
     */
    @Override
    public void usernameError() {
        ToastUtil.makeText(getActivity(), "用户不存在");
    }


    /**
     * 密码错误.
     */
    @Override
    public void passwordError() {
        ToastUtil.makeText(getActivity(), "密码错误");
    }

    /**
     * 账号为空.
     */
    @Override
    public void usernameEmpty() {
        ToastUtil.makeText(getActivity(), "账号不能为空");
    }


    /**
     * 服务已到期.
     */
    @Override
    public void isLeaseValid() {
        ToastUtil.makeText(getActivity(), "服务已到期");
    }

    /**
     * 登陆成功.
     */
    @Override
    public void loginSuccess() {
        OffLineOutApiUtil.isOffLine = false;//
        getUserInformation();
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

    /**
     * 切换明文 密码.
     *
     * @param flag .
     */
    @Override
    public void togglePassword(final boolean flag) {
        if (flag) {
            //选择状态 显示明文--设置为可见的密码
            passwordEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
            passwordEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    /**
     * 输入的密码长度 不正确.
     */
    @Override
    public void setPasswordLength() {
        ToastUtil.makeText(getActivity(), getString(R.string.password_length));
    }

    /**
     * 格式错误.
     */
    @Override
    public void formattingError() {
        ToastUtil.makeText(getActivity(), getString(R.string.formatting_error));
    }

    /**
     * 连续输错三次.
     */
    @Override
    public void inputError() {
        ToastUtil.makeText(getActivity(), getString(R.string.input_error));
    }

    /**
     * 点击事件.
     *
     * @param view .
     */
    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.sign_in_btn) {
            if (AppApplication.isNetWord) {
                presenter.login();
            } else {
                accessBaidu(new AccessBaiduListener() {
                    @Override
                    public void success() {
                        presenter.login();
                    }

                    @Override
                    public void failed() {
                        tipOffLine();
                    }
                });
            }
        } else if (view.getId() == R.id.forgetPwd_tv) {
            Intent intent = new Intent(getActivity(), CommonActivity.class);
            intent.putExtra("fragmentName", VerifyMobilephoneFragment.class.getName());
            intent.putExtra("username", usernameEt.getText().toString());
            intent.putExtra("FULL_SCREEN", true);
            startActivity(intent);
        } else if (view.getId() == R.id.ip_btn) {
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) <= MIN_CLICK_DELAY_TIME) {
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("fragmentName", SetUpIPAddress.class.getName());
                startActivity(intent);
            } else {
                lastClickTime = curClickTime;
            }
        }
    }


    /**
     * 开始服务轮循消息.
     */
    private void startMessgeService() {
        Intent intent = new Intent(getActivity(), MessgeService.class);
        getActivity().startService(intent);
    }


    @Override
    public void callBack(final boolean flag, final String permission) {
    }

    /**
     * 从我的模块 修改密码带过来。。。
     *
     * @return
     */
    private String getType() {
        return getActivity().getIntent().getStringExtra("type");
    }

    private int ACCESS_BAIDU_TIMEOUT = 5;

    private void accessBaidu(final AccessBaiduListener listener) {
        new OkHttpClientManager(ACCESS_BAIDU_TIMEOUT, ACCESS_BAIDU_TIMEOUT)
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(String s) {
                        listener.success();
//                        tipOffLine();
//                        isLoginBaiduSuccess = true;
                    }

                    @Override
                    public void onErrorResponse(Response response) {
                        listener.failed();
                    }
                }).sendGet("https://www.baidu.com/");
    }

    @Override
    public void setUserInfo(UserEntity entity) {
        entity.setPassword(passwordEt.getText().toString());
        AppApplication.getInstance().setUserEntity(entity);
        addUserToLocal(entity);
        if (entity.isFirstLogin()) {
            Intent intent = new Intent(getActivity(), CommonActivity.class);
            intent.putExtra("fragmentName", ModifyPwdFragment.class.getName());
            intent.putExtra("isFirstLogin", true);
            intent.putExtra("tel", usernameEt.getText().toString().trim());
            startActivity(intent);
            getActivity().finish();
        } else {
            startMessgeService();
            Intent intent = new Intent(getActivity(), NavigationActivity.class);
            intent.putExtra("FIRST_FLAG", true);
            startActivity(intent);
            AppApplication.getInstance().finishAllActivity();
//            AppApplication.getInstance().backHome(getActivity());
        }
    }

    public interface AccessBaiduListener {
        void success();

        void failed();
    }
}
