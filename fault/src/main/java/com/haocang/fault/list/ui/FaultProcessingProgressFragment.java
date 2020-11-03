package com.haocang.fault.list.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.config.LibConfig;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.fault.R;
import com.haocang.fault.list.adapter.FaultProcessingAdapter;
import com.haocang.fault.list.bean.FaultProcessingProgressEntity;
import com.haocang.fault.list.iview.FaultProcessingProgressView;
import com.haocang.fault.list.presenter.impl.FaultProcessingProgressImpl;

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
 * 标 题： 消缺处理进度.
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/279:54
 * 修 改 者：
 * 修改时间：
 */
@Route(path = LibConfig.AROUTE_FAULT_PROCESSINGPROGRESS)
public class FaultProcessingProgressFragment extends Fragment implements BaseAdapter.OnItemClickListener, FaultProcessingProgressView {
    private TextView titleNameTv;

    private RecyclerView recyclerView;

    private FaultProcessingAdapter mAdapter;

    private FaultProcessingProgressImpl progressImpl;

    private TextView faultStateTv;
    private ImageView faultStateIv;

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.fragment_fault_processing_progress, null);
        initView(view);
        return view;
    }

    /**
     * @param view 根View.
     */
    private void initView(final View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.falut_processing_progress));
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new FaultProcessingAdapter(R.layout.adapter_fault_processing);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mAdapter.setOnItemClickListener(this);
        faultStateTv = view.findViewById(R.id.fault_statu_tv);
        faultStateIv = view.findViewById(R.id.fault_statu_iv);
        progressImpl = new FaultProcessingProgressImpl(this);
        progressImpl.getProcessingProgressList();
    }

    @Override
    public void onClick(View view, int position, Object item) {
        FaultProcessingProgressEntity entity = (FaultProcessingProgressEntity) item;
        Map<String, Object> map = new HashMap<>();
        map.put("fragmentUri", LibConfig.AROUTE_FAULT_PROCESSING_RESULTS);
        if (entity.getProcessResult() == 4 || entity.getProcessResult() == 1 || entity.getProcessResult() == 2 || entity.getProcessResult() == 3) {
            map.put("faultContrailId", entity.getFaultContrailId() + "");
            ARouterUtil.toFragment(map);
        }
    }

    @Override
    public void onLongClick(View view, int position, Object item) {
    }


    private String getFaultIds() {
        return getActivity().getIntent().getStringExtra("faultId");
    }


    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public void setProcessingProgressList(FaultProcessingProgressEntity entity) {
        mAdapter.clear();
        if (entity != null) {
            faultStateTv.setText(entity.getCurrStateName());
            faultStateTv.setTextColor(Color.parseColor(entity.getNewStateColor()));
            faultStateIv.setBackgroundResource(entity.getBackgroundResource());
            mAdapter.addAll(entity.getNewProcessingProgressList());
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public String getFaultId() {
        return getFaultIds();
    }
}
