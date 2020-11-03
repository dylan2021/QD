package com.haocang.waterlink.self.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.config.LibConfig;
import com.haocang.base.utils.ToastUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.constant.bean.HomeConstants;
import com.haocang.waterlink.self.adapter.HomeSetUpAdapter;
import com.haocang.waterlink.self.bean.HomeSetUpEntity;

import java.util.ArrayList;
import java.util.List;

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
 * 创建时间：2018/3/2815:16
 * 修 改 者：
 * 修改时间：
 */
public class HomeSetUpFragment extends Fragment implements BaseAdapter.OnItemClickListener {
    private RecyclerView recyclerview;
    private HomeSetUpAdapter adapter;

    private TextView titleNameTv;
    private SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_home_setup, null);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        sp = getActivity().getSharedPreferences(LibConfig.HOME_SETUP, Context.MODE_PRIVATE);
        recyclerview = view.findViewById(R.id.recyclerview);
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getResources().getString(R.string.home_setup));
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HomeSetUpAdapter();
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        setData();
    }

    private void setData() {
        SharedPreferences.Editor edit = sp.edit();
        HomeSetUpEntity dataEntity = new HomeSetUpEntity();
        dataEntity.setName(getString(R.string.home_data));
        dataEntity.setValue(getString(R.string.home_data_description));
        if (sp.getBoolean(HomeConstants.HomeSetupKey.HOME_DATA, true)) {
            dataEntity.setType(1);
            edit.putBoolean(HomeConstants.HomeSetupKey.HOME_DATA, true);
        } else {
            edit.putBoolean(HomeConstants.HomeSetupKey.HOME_DATA, false);
            dataEntity.setType(0);
        }
        dataEntity.setId(0);
        HomeSetUpEntity entity = new HomeSetUpEntity();
        entity.setName(getString(R.string.task));
        entity.setValue("即将开始的任务滚动显示");
        if (sp.getBoolean(HomeConstants.HomeSetupKey.HOME_TASK, true)) {
            entity.setType(1);
            edit.putBoolean(HomeConstants.HomeSetupKey.HOME_TASK, true);
        } else {
            edit.putBoolean(HomeConstants.HomeSetupKey.HOME_TASK, false);
            entity.setType(0);
        }
        entity.setId(1);

        HomeSetUpEntity entity1 = new HomeSetUpEntity();
        entity1.setName("KPI");
        entity1.setValue("各类管理KPI数据的展示");
        if (sp.getBoolean(HomeConstants.HomeSetupKey.HOME_KPI, true)) {
            entity1.setType(1);
            edit.putBoolean(HomeConstants.HomeSetupKey.HOME_KPI, true);
        } else {
            edit.putBoolean(HomeConstants.HomeSetupKey.HOME_KPI, false);
            entity1.setType(0);
        }

        entity1.setId(2);
        List<HomeSetUpEntity> list = new ArrayList<>();
        list.add(dataEntity);
        list.add(entity);
        list.add(entity1);
        adapter.addAll(list);
        edit.commit();
    }

    @Override
    public void onClick(final View view, final int position, final Object item) {
        HomeSetUpEntity entity = (HomeSetUpEntity) item;
        SharedPreferences.Editor edit = sp.edit();
        if (entity.getId() == 0 && entity.getType() == 1 && sp.getBoolean(HomeConstants.HomeSetupKey.HOME_DATA, false)) {
            edit.putBoolean(HomeConstants.HomeSetupKey.HOME_DATA, false);
            entity.setType(0);
        } else if (entity.getId() == 0 && entity.getType() == 0) {
            edit.putBoolean(HomeConstants.HomeSetupKey.HOME_DATA, true);
            entity.setType(1);
        } else if (entity.getId() == 1 && entity.getType() == 1 && sp.getBoolean(HomeConstants.HomeSetupKey.HOME_TASK, false)) {
            edit.putBoolean(HomeConstants.HomeSetupKey.HOME_TASK, false);
            entity.setType(0);
        } else if (entity.getId() == 1 && entity.getType() == 0) {
            edit.putBoolean(HomeConstants.HomeSetupKey.HOME_TASK, true);
            entity.setType(1);
        } else if (entity.getId() == 2 && entity.getType() == 1 && sp.getBoolean(HomeConstants.HomeSetupKey.HOME_KPI, false)) {
            edit.putBoolean(HomeConstants.HomeSetupKey.HOME_KPI, false);
            entity.setType(0);
        } else if (entity.getId() == 2 && entity.getType() == 0) {
            edit.putBoolean(HomeConstants.HomeSetupKey.HOME_KPI, true);
            entity.setType(1);
        } else {
            ToastUtil.makeText(getActivity(), "至少需要选择一个模块");
        }
        edit.commit();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLongClick(View view, int position, Object item) {

    }
}
