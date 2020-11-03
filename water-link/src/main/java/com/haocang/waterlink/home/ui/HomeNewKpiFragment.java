package com.haocang.waterlink.home.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.widgets.MyGridLayoutManager;
import com.haocang.datamonitor.monitor.bean.MonitorEntity;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.adapter.EquimentKpiAdapter;
import com.haocang.waterlink.home.bean.EquimentKpiEntity;
import com.haocang.waterlink.home.iview.HomeKpiView;
import com.haocang.waterlink.home.presenter.HomeKpiPresenter;
import com.haocang.waterlink.home.presenter.impl.HomeKpiPresenterImpl;
import com.haocang.waterlink.self.ui.home.KpiHomeFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区龙兰路277号一号楼9楼
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/5/22 10:52
 * 修 改 者：
 * 修改时间：
 */
public class HomeNewKpiFragment extends Fragment implements HomeKpiView, View.OnClickListener {
    private RecyclerView recyclerView;
    private EquimentKpiAdapter recycleAdapter;
    private Date currDate = new Date();
    private HomeKpiPresenter presenter;
    private TextView nodataTv;
    private TextView timeTv;
    private View view;
    private TextView siteNameTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_home_new_kpi, null);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        this.view = view;
        siteNameTv = view.findViewById(R.id.site_name_tv);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new MyGridLayoutManager(getActivity(), 3));
        recycleAdapter = new EquimentKpiAdapter();
        recyclerView.setAdapter(recycleAdapter);
        presenter = new HomeKpiPresenterImpl(this);
        view.findViewById(R.id.home_kpi_more_tv).setOnClickListener(this);
        view.findViewById(R.id.last_v).setOnClickListener(this);
        view.findViewById(R.id.next_v).setOnClickListener(this);
        nodataTv = view.findViewById(R.id.nodata_tv);
        timeTv = view.findViewById(R.id.time_tv);
        timeTv.setText(TimeUtil.converMMDD(currDate));
        presenter.getKpiListData();
    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public String getTime() {
        return converMMDD(currDate);
    }

    private String converMMDD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }

    @Override
    public void setKpiList(final List<EquimentKpiEntity> list) {
        recycleAdapter.clear();
        if (list != null && list.size() > 0) {
            String siteName = list.get(0).getSiteName();
            siteNameTv.setText(siteName);
            recycleAdapter.addAll(list);
            recyclerView.setVisibility(View.VISIBLE);
            recycleAdapter.notifyDataSetChanged();
            if (recycleAdapter.getItemCount() > 0) {
                nodataTv.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                nodataTv.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }
        recycleAdapter.notifyDataSetChanged();
    }


    @Override
    public void submitSuccess() {

    }

    @Override
    public void setProcessesList(List<MonitorEntity> list) {

    }

    @Override
    public String getSiteId() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.jump_ll || view.getId() == R.id.home_kpi_more_tv) {
            Intent intent = new Intent(getActivity(), CommonActivity.class);
            intent.putExtra("fragmentName", KpiHomeFragment.class.getName());
            intent.putExtra("siteId", getSiteId());
            intent.putExtra("siteName", siteNameTv.getText().toString());
            startActivity(intent);
        } else if (view.getId() == R.id.next_v) {
            nextMonth();
        } else if (view.getId() == R.id.last_v) {
            lastMonth();
        }
    }

    private void nextMonth() {
        if (TimeUtil.getNextMonth(currDate).after(new Date())) {
            ToastUtil.makeText(getContexts(), getString(R.string.home_time_outoffrange));
        } else {
            currDate = TimeUtil.getNextMonth(currDate);
            recycleAdapter.clear();
            timeTv.setText(TimeUtil.converMMDD(currDate));
            presenter.getKpiListData();
        }
    }

    private void lastMonth() {
        recycleAdapter.clear();
        view.findViewById(R.id.next_v).setVisibility(View.VISIBLE);
        currDate = TimeUtil.getLastMonth(currDate);
        timeTv.setText(TimeUtil.converMMDD(currDate));
        presenter.getKpiListData();
    }
}
