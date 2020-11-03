package com.haocang.waterlink.self.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.utils.TimeUtil;
import com.haocang.datamonitor.monitor.bean.MonitorEntity;
import com.haocang.datamonitor.monitor.ui.MonitorFilterActivity;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.adapter.EquimentKpiAdapter;
import com.haocang.waterlink.home.bean.EquimentKpiEntity;
import com.haocang.waterlink.home.iview.HomeKpiView;
import com.haocang.waterlink.home.presenter.HomeKpiPresenter;
import com.haocang.waterlink.home.presenter.impl.HomeKpiPresenterImpl;

import java.util.ArrayList;
import java.util.Date;
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
 * 创建时间：2018/3/2211:17
 * 修 改 者：
 * 修改时间：
 */
public class KpiHomeFragment extends Fragment
        implements View.OnClickListener, BaseAdapter.OnItemClickListener, HomeKpiView {
    /**
     * title.
     */
    private TextView titleNameTv;
    /**
     * 导航栏.
     */
    private RecyclerView recyclerView;
    /**
     * 内容.
     */
    private RecyclerView contentRecyclerView;
    /**
     * 内容适配器.
     */
    private EquimentKpiAdapter recycleAdapter;

    /**
     * 在title居右的按钮。大部分界面没有.默认隐藏..
     */
    private ImageView commonIv;
    private HomeKpiPresenter presenter;
    private Date currDate = new Date();
    private TextView timeTv;
    private String listSr;
    private TextView siteNameTv;
    private String siteId;
    //    private SharedPreferences sp;
    private int position = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_kpi_home, null);
        initView(view);
        return view;
    }

    private void initView(final View view) {
//        sp = getActivity().getSharedPreferences(LibConfig.HOME_SETUP, Context.MODE_PRIVATE);
        siteId = getActivity().getIntent().getStringExtra("siteId");
        titleNameTv = view.findViewById(R.id.title_common_tv);
        commonIv = view.findViewById(R.id.common_iv);
        commonIv.setBackgroundResource(R.mipmap.list_filter);
        commonIv.setVisibility(View.VISIBLE);
        commonIv.setOnClickListener(this);
        titleNameTv.setText(getResources().getString(R.string.kpi));
        siteNameTv = view.findViewById(R.id.site_name_tv);
        siteNameTv.setText(getActivity().getIntent().getStringExtra("siteName"));
        recyclerView = view.findViewById(R.id.recyclerview);

        contentRecyclerView = view.findViewById(R.id.content_recyclerview);
        contentRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycleAdapter = new EquimentKpiAdapter();
        recycleAdapter.setOnItemClickListener(this);
        contentRecyclerView.setAdapter(recycleAdapter);
        presenter = new HomeKpiPresenterImpl(this);
        timeTv = view.findViewById(R.id.time_tv);
        timeTv.setText(TimeUtil.converMMDD(currDate));
        view.findViewById(R.id.last_v).setOnClickListener(this);
        view.findViewById(R.id.next_v).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        recycleAdapter.clear();
        presenter.getKpiListData();
        presenter.getProcessesList();
    }

    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.common_iv) {
//            Intent intent = new Intent(getActivity(), CommonActivity.class);
//            intent.putExtra("fragmentName", KpiSetUpFragment.class.getName());
//            startActivity(intent);
            if (!TextUtils.isEmpty(listSr)) {
                Intent intent = new Intent(getActivity(), MonitorFilterActivity.class);
                intent.putExtra("list", listSr);
                intent.putExtra("position", position);
                startActivityForResult(intent, 2323);
            }

        } else if (view.getId() == R.id.next_v) {
            nextMonth();
        } else if (view.getId() == R.id.last_v) {
            lastMonth();
        }
    }

    private void nextMonth() {
        recycleAdapter.clear();
        currDate = TimeUtil.getNextMonth(currDate);
        timeTv.setText(TimeUtil.converMMDD(currDate));
        presenter.getKpiListData();
    }

    private void lastMonth() {
        recycleAdapter.clear();
        currDate = TimeUtil.getLastMonth(currDate);
        timeTv.setText(TimeUtil.converMMDD(currDate));
        presenter.getKpiListData();
    }

    @Override
    public void onClick(final View view,
                        final int position,
                        final Object item) {

    }

    @Override
    public void onLongClick(final View view,
                            final int position,
                            final Object item) {

    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public String getTime() {
        return TimeUtil.converMMDD(currDate);
    }

    @Override
    public void setKpiList(final List<EquimentKpiEntity> list) {
        if (list != null && list.size() > 0) {
            contentRecyclerView.setVisibility(View.VISIBLE);
            recycleAdapter.addAll(list);
            if (recycleAdapter.getItemCount() > 0) {
                contentRecyclerView.setVisibility(View.VISIBLE);
            } else {
                contentRecyclerView.setVisibility(View.GONE);
            }
            recycleAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void submitSuccess() {

    }

    private List<MonitorEntity> listMonitor = new ArrayList<>();

    @Override
    public void setProcessesList(List<MonitorEntity> list) {
        for (int i = 0; i < list.size(); i++) {
            try {
                if (list.get(i).getId() == Integer.parseInt(getSiteId())) {
                    position = i;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        listSr = null;
        listMonitor.clear();
        listSr = new Gson().toJson(list);
        listMonitor.addAll(list);
    }

    @Override
    public String getSiteId() {
        return siteId;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 2323 && data != null) {
            position = data.getIntExtra("position", 0);
            MonitorEntity entity = listMonitor.get(data.getIntExtra("position", 0));
            siteNameTv.setText(entity.getName());
            siteId = entity.getId() + "";
//            SharedPreferences.Editor edit = sp.edit();
//            edit.putString(HomeNewKpiFragment.KPI, entity.getName());
//            edit.putString(HomeNewKpiFragment.KPI_ID, entity.getId() + "");
//            edit.commit();
        }
    }

}
