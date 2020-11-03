package com.haocang.waterlink.self.ui.home;

import android.content.Context;
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
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.datamonitor.monitor.bean.MonitorEntity;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.EquimentKpiEntity;
import com.haocang.waterlink.home.iview.HomeKpiView;
import com.haocang.waterlink.home.presenter.HomeKpiPresenter;
import com.haocang.waterlink.home.presenter.impl.HomeKpiPresenterImpl;
import com.haocang.waterlink.self.adapter.KpiSetUpContentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * 标 题： kpi设置页面.
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/3/2115:47
 * 修 改 者：
 * 修改时间：
 */
public class KpiSetUpFragment extends Fragment implements BaseAdapter.OnItemClickListener, View.OnClickListener, HomeKpiView {
    /**
     * title.
     */
    private TextView titleNameTv;
    /**
     * 内容.
     */
    private RecyclerView contentRecyclerView;
    /**
     * 内容适配器.
     */
    private KpiSetUpContentAdapter contentAdapter;
    private String siteId;
    /**
     * title居右的按钮。大部分界面没有.默认隐藏..
     */
    private TextView commonTv;
    private HomeKpiPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_kpi_set, null);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        commonTv = view.findViewById(R.id.common_tv);
        commonTv.setText(getString(R.string.complete));
        commonTv.setVisibility(View.VISIBLE);
        commonTv.setOnClickListener(this);
        titleNameTv.setText(getResources().getString(R.string.kpi));


        contentRecyclerView = view.findViewById(R.id.content_recyclerview);
        contentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        contentAdapter = new KpiSetUpContentAdapter();
        contentAdapter.setOnItemClickListener(this);
        contentRecyclerView.setAdapter(contentAdapter);
        presenter = new HomeKpiPresenterImpl(this);
        presenter.getKpiListData();
    }


    @Override
    public void onClick(final View view,
                        final int position,
                        final Object item) {
        EquimentKpiEntity entity = (EquimentKpiEntity) item;
        if (entity.isChecked()) {
            entity.setChecked(false);
        } else {
            entity.setChecked(true);
        }
        contentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLongClick(final View view, final int position, final Object item) {

    }

    @Override
    public void onClick(final View view) {
//        if (view.getId() == R.id.common_tv) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("list", transformation());
//            presenter.submit(transformation());
//        }

    }

    private JSONArray transformation() {
        JSONArray array = new JSONArray();
        JSONObject object = null;
        List<EquimentKpiEntity> list = contentAdapter.getEquimentList();
        for (EquimentKpiEntity step : list) {
            object = new JSONObject();
            try {
                object.put("kpiName", step.getKpiName());
                object.put("icon", step.getIcon());
                object.put("title", step.getTitle());
                object.put("description", step.getDescription());
                object.put("checked", step.isChecked());
                object.put("kpiValue", step.getKpiValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(object);
        }
        return array;
    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public String getTime() {
        return TimeUtil.converMMDD(new Date());
    }

    @Override
    public void setKpiList(final List<EquimentKpiEntity> list) {
        contentAdapter.clear();
        if (list != null && list.size() > 0) {
            contentAdapter.addAll(list);
            contentAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void submitSuccess() {
        ToastUtil.makeText(getActivity(), "设置成功");
//        presenter.getKpiListData();
        getActivity().finish();
    }

    @Override
    public void setProcessesList(List<MonitorEntity> list) {

    }

    @Override
    public String getSiteId() {
        return siteId;
    }


}
