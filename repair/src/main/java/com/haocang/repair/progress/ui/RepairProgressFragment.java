package com.haocang.repair.progress.ui;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.repair.R;
import com.haocang.repair.manage.bean.RepairConstans;
import com.haocang.repair.progress.adapter.RepairProgressAdapter;
import com.haocang.repair.progress.bean.ProcessingProgressVo;
import com.haocang.repair.progress.iview.RepairProgressView;
import com.haocang.repair.progress.presenter.RepairProgressPresenter;
import com.haocang.repair.progress.presenter.impl.RepairProgressPresenterImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题： 维修进度.
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/279:54
 * 修 改 者：
 * 修改时间：
 */
@Route(path = ArouterPathConstants.Repair.REPAIR_PROGRESS)
public class RepairProgressFragment extends Fragment
        implements BaseAdapter.OnItemClickListener, RepairProgressView {

    /**
     * 列表视图.
     */
    private RecyclerView recyclerView;

    /**
     *
     */
    private RepairProgressAdapter mAdapter;

    /**
     * 界面处理接口
     */
    private RepairProgressPresenter mPresenter;

    /**
     *
     */
    private TextView stateTv;

    private ImageView stateIv;

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
                .inflate(R.layout.repair_processing_progress_fragment, null);
        initView(view);
        return view;
    }

    /**
     * @param view 根View.
     */
    private void initView(final View view) {
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.repair_processing_progress));
        stateTv = view.findViewById(R.id.fault_statu_tv);
        stateIv = view.findViewById(R.id.fault_statu_iv);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter =
                new RepairProgressAdapter(R.layout.repair_processing_adapter);
        recyclerView.setAdapter(mAdapter);
        mPresenter = new RepairProgressPresenterImpl();
        mPresenter.setView(this);
        mPresenter.getProgressList();
    }


    /**
     * @param view
     * @param position
     * @param item
     */
    @Override
    public void onClick(final View view, final int position, final Object item) {
        ProcessingProgressVo.ProcessingProgress vo = (ProcessingProgressVo.ProcessingProgress) item;
        if (vo.hasDetail()) {
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri",
                    ArouterPathConstants.Repair.REPAIR_PROGRESS_DETAIL);
            map.put("repairRecordId", vo.getRepairContrailId() + "");
            ARouterUtil.toFragment(map);
        }


    }

    @Override
    public void onLongClick(final View view, final int position, final Object item) {

    }


    /**
     * 获取上下文参数.
     *
     * @return
     */
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void renderData(final ProcessingProgressVo processingProgressVo) {
        if (processingProgressVo != null) {
            stateTv.setText(processingProgressVo.getCurrStateName());
//            setStateTvColor(processingProgressVo.getCurrState());
            setState(processingProgressVo.getCurrState());
            List<ProcessingProgressVo.ProcessingProgress> list = processingProgressVo.getProcessingProgressList();
            for (int i = list.size() - 1; i >= 0; i--) {
                mAdapter.add(list.get(i));
            }
            mAdapter.notifyDataSetChanged();
            mAdapter.setOnItemClickListener(this);
        }
    }

    private void setState(final Integer state) {
        if (RepairConstans.STATE_UNASSIGNED == state) {
            stateTv.setTextColor(getContext().getResources().getColor(R.color.repair_state_unallote));
            stateIv.setBackgroundResource(R.drawable.repair_state_unallocate);
        } else if (RepairConstans.STATE_UNPROCESS == state) {
            stateTv.setTextColor(getContext().getResources().getColor(R.color.repair_state_unprocess));
            stateIv.setBackgroundResource(R.drawable.repair_state_wait_processing);
        } else if (RepairConstans.STATE_PROCESSING == state) {
            stateTv.setTextColor(getContext().getResources().getColor(R.color.repair_state_processing));
            stateIv.setBackgroundResource(R.drawable.repair_state_processing);
        } else if (RepairConstans.STATE_HANGUP == state) {
            stateTv.setTextColor(getContext().getResources().getColor(R.color.repair_state_hangup));
            stateIv.setBackgroundResource(R.drawable.repair_state_hang_up);
        } else if (RepairConstans.STATE_CLOSE == state) {
            stateTv.setTextColor(getContext().getResources().getColor(R.color.repair_state_close));
            stateIv.setBackgroundResource(R.drawable.repair_state_close);
        } else if (RepairConstans.STATE_COMPLETE == state) {
            stateTv.setTextColor(getContext().getResources().getColor(R.color.repair_state_complete));
            stateIv.setBackgroundResource(R.drawable.repair_state_complete);
        }
    }

    @Override
    public String getRepairId() {
        return getActivity().getIntent().getStringExtra("repairId");
    }

}
