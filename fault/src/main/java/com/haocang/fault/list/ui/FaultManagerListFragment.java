package com.haocang.fault.list.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.ConcernUtil;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.fault.R;
import com.haocang.fault.list.adapter.FaultManagerListAdapter;
import com.haocang.fault.list.bean.FaultManagerEntity;
import com.haocang.fault.list.iview.FaultManagerListView;
import com.haocang.fault.list.presenter.FaultManagerListPresenter;
import com.haocang.fault.list.presenter.impl.FaultMangerListPresenterImpl;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.Date;
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
 * 标 题：消缺管理列表
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/2611:20
 * 修 改 者：
 * 修改时间：
 */
@Route(path = ArouterPathConstants.Fault.FAULT_LIST)
public class FaultManagerListFragment extends Fragment
        implements OnClickListener, BaseRefreshListener,
        FaultManagerListView, BaseAdapter.OnItemClickListener, TextView.OnEditorActionListener, ConcernUtil.AddConcernInterface {

    private TextView titilNameTv;
    private ImageView screeIv;
    //    private TabLayout tab;
//    private TabAdapter adapter;
//    private ViewPager viewpager;
//    private String[] tabArray;
//    private List<Fragment> fragments = new ArrayList<>();

    private RecyclerView recyclerView;
    private FrameLayout noDataFl;//没有数据的时候显示暂无数据图片.
    private PullToRefreshLayout pullToRefreshLayout;
    private FaultManagerListAdapter mAdapter;


    private FaultManagerListPresenter presenter;

    private int currentPage = 1;
    private boolean isRefresh = true;//区分是刷新还是加载更多
    private EditText seachEdt;


    /**
     * 筛选条件
     */
//    private String startTime = TimeUtil.getDateTimeStr(TimeUtil.getWeekFirstDay(new Date()));//开始时间
//    private String endTime = TimeUtil.getDateTimeStr(TimeUtil.getWeekLastDay(new Date()));//结束时间
    private String startTime;//开始时间
    private String endTime = TimeUtil.getDayStr(new Date()) + " 23:59:59";//结束时间
    private String statu = "5,0,1";//状态
    private String faultTypes;//缺陷类型
    private String processingPersonIds;//处理人
    private String createUserIds;//创建人

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_fault_manager_list, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        seachEdt = view.findViewById(R.id.query_et);
        seachEdt.setOnEditorActionListener(this);
        noDataFl = view.findViewById(R.id.no_data_fl);
        titilNameTv = view.findViewById(R.id.title_common_tv);
        titilNameTv.setText(getString(R.string.fault_manager));
        screeIv = view.findViewById(R.id.common_iv);
        screeIv.setBackgroundResource(R.drawable.screen_icon);
        screeIv.setVisibility(View.VISIBLE);
        screeIv.setOnClickListener(this);
        view.findViewById(R.id.search_v).setOnClickListener(this);

        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        pullToRefreshLayout.setRefreshListener(this);
        mAdapter = new FaultManagerListAdapter(getActivity(), R.layout.adapter_fault_manager_list);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        presenter = new FaultMangerListPresenterImpl(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setAddConcernInterface(this);
        view.findViewById(R.id.delete_ibtn).setOnClickListener(this);

    }


    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.common_iv) {
            toFragment();
        } else if (v.getId() == R.id.search_v) {
            currentPage = 1;
            isRefresh = true;
            presenter.getAllListData();
        } else if (v.getId() == R.id.delete_ibtn) {
            seachEdt.setText("");
        }

    }

    private void toFragment() {
        Intent intent = new Intent(getActivity(), FaultFilterActivity.class);
        if (!TextUtils.isEmpty(statu)) {
            intent.putExtra("states", statu);
        }
        if (!TextUtils.isEmpty(faultTypes)) {
            intent.putExtra("faultTypes", faultTypes);
        }
        if (!TextUtils.isEmpty(createUserIds)) {
            intent.putExtra("createUserIds", createUserIds);
        }
        if (!TextUtils.isEmpty(processingPersonIds)) {
            intent.putExtra("processingPersonIds", processingPersonIds);
        }
        if (!TextUtils.isEmpty(startTime)) {
            intent.putExtra("startDate", startTime);
        }
        if (!TextUtils.isEmpty(endTime)) {
            intent.putExtra("endDate", endTime);
        }
        getActivity().startActivityForResult(intent, FaultFilterActivity.REQUEST);
    }

    @Override
    public void refresh() {
        currentPage = 1;
        isRefresh = true;
        presenter.getAllListData();
    }

    @Override
    public void loadMore() {
        currentPage++;
        isRefresh = false;
        presenter.getAllListData();

    }

    @Override
    public void onActivityResult(final int requestCode,
                                 final int resultCode,
                                 final Intent data) {
        if (requestCode == LibConstants.Fault.PICKPERSON_REQUEST_CODE && data != null) {
            //分派任务
            int processingPersonId = Integer.parseInt(data.getStringExtra("id"));
            int faultId = Integer.parseInt(data.getStringExtra("selectItemId"));
            presenter.taskAssign(faultId, processingPersonId);
        } else if (resultCode == FaultFilterActivity.REQUEST && data != null) {
            getActivityResult(data);
        }
    }

    private void getActivityResult(final Intent data) {
        if (data.getStringExtra("startTime") != null) {
            startTime = data.getStringExtra("startTime");
        }
        if (data.getStringExtra("endTime") != null) {
            endTime = data.getStringExtra("endTime");
        }
        statu = data.getStringExtra("statu");
        faultTypes = data.getStringExtra("faultTypes");
        processingPersonIds = data.getStringExtra("processingPersonIds");
        createUserIds = data.getStringExtra("createUserIds");
        presenter.getAllListData();
    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public String getStates() {
        return statu;
    }

    @Override
    public String queryName() {
        return seachEdt.getText().toString().trim();
    }

    @Override
    public String getStartDate() {
        return startTime;
    }

    @Override
    public String getEndDate() {
        return endTime;
    }

    @Override
    public String getFaultTypes() {
        return faultTypes;
    }

    @Override
    public String getCreateUserIds() {
        return createUserIds;
    }

    @Override
    public String getProcessingPersonIds() {
        return processingPersonIds;
    }

    @Override
    public Integer getCurrentPage() {
        return currentPage;
    }

    @Override
    public void setListAllData(List<FaultManagerEntity> list) {
        if (isRefresh) {
            mAdapter.clear();
            mAdapter.notifyDataSetChanged();
            pullToRefreshLayout.finishRefresh();
        } else {
            pullToRefreshLayout.finishLoadMore();
        }
        if (list != null && list.size() > 0) {
            mAdapter.addAll(list);
            mAdapter.notifyDataSetChanged();
        } else if (!isRefresh) {
            ToastUtil.makeText(getActivity(), getString(R.string.equiment_all_data));
        }
        if (mAdapter.getItemCount() == 0) {
            noDataFl.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noDataFl.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void dstributionSuccess() {
        ToastUtil.makeText(getActivity(), getString(R.string.fault_distribution));
//        presenter.getAllListData();
        refresh();
    }


    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onClick(View view, int position, Object item) {
        FaultManagerEntity entity = (FaultManagerEntity) item;
        Map<String, Object> map = new HashMap<>();
        map.put("fragmentUri", LibConfig.AROUTE_FAULT_POST_MANAGER_DETAILS);
        map.put("faultId", entity.getId() + "");
        ARouterUtil.toFragment(map);
    }

    @Override
    public void onLongClick(View view, int position, Object item) {

    }

    @Override
    public void concernSucess() {
        refresh();
    }

    @Override
    public void concernError() {

    }

    @Override
    public void abolishConcern() {
        refresh();
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
            refresh();
            return true;
        }
        return false;
    }

}


