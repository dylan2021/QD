package com.haocang.patrol.patrolinhouse.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haocang.base.utils.SpaceItemDecoration;
import com.haocang.patrol.R;
import com.haocang.patrol.patrolinhouse.adapter.PatrolPointDetailScanAdapter;
import com.haocang.patrol.patrolinhouse.bean.PatrolPointDetailDTO;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskPointStep;
import com.haocang.patrol.patrolinhouse.iview.PatrolPointDetailListView;
import com.haocang.patrol.patrolinhouse.presenter.impl.PatrolPointDetailListPresenterImpl;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题：
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/4/9下午6:29
 * 修  改  者：
 * 修改时间：
 * 巡检步骤查看列表
 */

public class PatrolPointDetailScanListFragment extends Fragment
        implements PatrolPointDetailListView,
        BaseRefreshListener, View.OnClickListener, TextView.OnEditorActionListener {
    private PatrolPointDetailScanAdapter mAdapter;
    private PatrolPointDetailListPresenterImpl mPresenter;
    private Context ctx;
    private RecyclerView mRecyclerView;
    private PullToRefreshLayout pullToRefreshLayout;
    private TextView titleNameTv;
    /**
     * 查询输入框
     */
    private EditText queryEt;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.patrol_point_detail_scan_list_fragment, null);
        ctx = getActivity();
        initView(view);
        return view;
    }

    private void initView(final View view) {
        view.findViewById(R.id.comple_ll).setVisibility(View.GONE);
        queryEt = view.findViewById(R.id.query_et);
        queryEt.setHint("请输入巡检步骤");
        queryEt.setHintTextColor(Color.parseColor("#ff9b9b9b"));
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getPointName());
        mPresenter = new PatrolPointDetailListPresenterImpl(this);
        mRecyclerView = view.findViewById(R.id.patrol_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PatrolPointDetailScanAdapter(R.layout.patrol_pointstep_scan_item, ctx);
        mRecyclerView.setAdapter(mAdapter);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.patrol_list_space);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        mPresenter.getPatrolPointDetailList();
        pullToRefreshLayout.setRefreshListener(this);
        queryEt.setOnEditorActionListener(this);
        view.findViewById(R.id.delete_ibtn).setOnClickListener(this);
//        setData(getPatrolPointDetailDTO());
    }

    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.delete_ibtn) {
            queryEt.setText("");
        }
    }

    @Override
    public void refresh() {
        mPresenter.getPatrolPointDetailList();
        pullToRefreshLayout.finishRefresh();
    }

    @Override
    public void loadMore() {
        pullToRefreshLayout.finishLoadMore();
    }

    @Override
    public void renderList(final List<PatrolTaskPointStep> list) {
        if (list != null) {
            mAdapter.replaceAll(list);
            mAdapter.notifyListDataSetChanged();
        }
    }

    @Override
    public boolean isTaskComplete() {
        return false;
    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public Integer getTaskId() {
        int taskId = getActivity().getIntent().getIntExtra("taskId", -1);
        return taskId;
    }

    @Override
    public Integer getPointId() {
        int pointId = getActivity().getIntent().getIntExtra("pointId", -1);
        return pointId;
    }

    @Override
    public Integer getPatrolPointId() {
        return getActivity().getIntent().getIntExtra("patrolPointId", -1);
    }

    @Override
    public Integer getHasResultCount() {
        return null;
    }

    @Override
    public Integer getFaultCount() {
        return null;
    }

    @Override
    public Integer getNewFaultCount() {
        return null;
    }

    @Override
    public List<PatrolTaskPointStep> getPatrolPointSteps() {
        return null;
    }

    @Override
    public String getLastResultUpdateTime() {
        return null;
    }

    @Override
    public boolean isPointCompleteAll() {
        return false;
    }

    @Override
    public boolean isMapPatrol() {
        return false;
    }

    @Override
    public String getTaskName() {
        return "";
    }

    @Override
    public String getQueryName() {
        return queryEt.getText().toString();
    }

    @Override
    public void postFault(int position) {

    }

    @Override
    public void submitSuccess() {

    }

    @Override
    public void submitFail(String error) {

    }

    @Override
    public void setClickable(boolean flg) {

    }

    @Override
    public void exemptionSubmitSuccess() {

    }

    /**
     * 获取巡检点名称
     *
     * @return
     */
    public String getPointName() {
        return getActivity().getIntent().getStringExtra("pointName");
    }

    /**
     * @return
     */
    public PatrolPointDetailDTO getPatrolPointDetailDTO() {
        PatrolPointDetailDTO dto = null;
        String patrolPointDetailDTOStr = getActivity().getIntent().getStringExtra("patrolPointDetailDTOStr");
        if (patrolPointDetailDTOStr != null) {
            dto = new Gson().fromJson(patrolPointDetailDTOStr, PatrolPointDetailDTO.class);
        }
        return dto;
    }

    /**
     * @param textView
     * @param actionId
     * @param event
     * @return
     */
    @Override
    public boolean onEditorAction(final TextView textView, final int actionId, final KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            mPresenter.getPatrolPointDetailList();
            return true;
        }
        return false;
    }


}
