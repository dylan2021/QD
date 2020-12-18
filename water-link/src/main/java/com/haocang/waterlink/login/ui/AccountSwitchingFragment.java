package com.haocang.waterlink.login.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.ui.CommonActivity;
import com.haocang.offline.bean.user.OffLineUserEntity;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.ui.HomeFragment;
import com.haocang.waterlink.home.ui.NavigationActivity;
import com.haocang.waterlink.login.adapter.AccountSwitchAdapter;
import com.haocang.waterlink.login.config.LoginMethodConstants;
import com.haocang.waterlink.login.iview.AccountSwitchView;
import com.haocang.waterlink.login.presenter.AccountSwitchPresenter;
import com.haocang.waterlink.login.presenter.impl.AccountSwitchPresenterImpl;
import com.haocang.waterlink.utils.AutomaticLogonUtils;

import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：切换账号
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：${DATA} 9:37
 * 修 改 者：
 * 修改时间：
 */
@Route(path = LoginMethodConstants.ACCOUNT_SWITCHING)
public class AccountSwitchingFragment extends Fragment implements View.OnClickListener, AccountSwitchView {
    private RecyclerView recyclerview;
    private AccountSwitchAdapter adapter;
    private AccountSwitchPresenter presenter;
    /**
     * 把用户名和密码存储下来.
     */
    private SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setBarColor();
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_account_swich, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.cancler_tv).setOnClickListener(this);
        recyclerview = view.findViewById(R.id.recyclerview);
        adapter = new AccountSwitchAdapter(R.layout.adapter_account_switch, getActivity());
        presenter = new AccountSwitchPresenterImpl(this);
        presenter.getUserList();
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i, Object o) {
                OffLineUserEntity entity = (OffLineUserEntity) o;
                Log.d("切换账号", entity.getTel()+",数据:"+entity.getPassword());
             /*   if (!entity.getTel().equals(AppApplication.getInstance().getUserEntity().getTel())) {
                    LibConfig.setCookie("");
                    accountSwitch(entity);
                }*/
                //LibConfig.setCookie("");
                accountSwitch(entity);
            }

            @Override
            public void onLongClick(View view, int i, Object o) {

            }
        });
    }

    //登录成功
    private void accountSwitch(final OffLineUserEntity entity) {
        new AutomaticLogonUtils(getActivity()).setAutomaticLogon(
                new AutomaticLogonUtils.AutomaticLogonInterface() {
            @Override
            public void loginSuccess() {
                AppApplication.getInstance().setOnReceiveVoiceListener(null);
                setConfiguration(entity.getTel(), entity.getPassword());
          /*      Intent intent = new Intent(getActivity(), NavigationActivity.class);
                getActivity().startActivity(intent);
                AppApplication.getInstance().finishAllActivity();*/

                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void passWordError() {
                Log.d("切换账号", "passWordError");
                toFragment();
            }

            @Override
            public void userNameError() {
                Log.d("切换账号", "userNameError");
                toFragment();
            }
        }).setUserEntity(entity).login();
    }


    private void toFragment() {
        Intent intent = new Intent(getActivity(), CommonActivity.class);
        intent.putExtra("fragmentName", LoginFragment.class.getName());
        getActivity().startActivity(intent);
        getActivity().finish();
        AppApplication.getInstance().finisBussActivity(getActivity());
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancler_tv) {
            getActivity().finish();
        }
    }

    @Override
    public void setRecyclerCount(int count) {
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), count));
        recyclerview.setAdapter(adapter);
    }

    @Override
    public void setUserList(List<OffLineUserEntity> list) {
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LibConfig.setBarColor(getActivity());
    }

    private void setBarColor() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getActivity().getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            getActivity().getWindow().setStatusBarColor(Color.parseColor("#f9f9f9"));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    /**
     * 每次登陆成功后 保存用户名和密码.
     */
    private void setConfiguration(String userName, String passWord) {
        try {
            sp = getActivity().getSharedPreferences(LibConfig.CHECK, Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(LibConfig.USERNAME, userName);
            edit.putString(LibConfig.PASSWORD, passWord);
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
