package com.haocang.repair.manage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
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
import com.haocang.base.utils.AnalysisQRCodeUtil;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.ScanCodeUtils;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.zxing.app.CaptureActivity;
import com.haocang.repair.R;
import com.haocang.repair.manage.adapter.RepairListAdapter;
import com.haocang.repair.manage.bean.RepairVo;
import com.haocang.repair.manage.iview.RepairManageView;
import com.haocang.repair.manage.presenter.RepairListPresenter;
import com.haocang.repair.manage.presenter.impl.RepairListPresenterImpl;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.util.Calendar;
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
 * 标 题：维修管理列表
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：william
 * 创建时间：2018/4/2611:20
 * 修 改 者：
 * 修改时间：
 */
@Route(path = ArouterPathConstants.Repair.REPAIR_LIST)
public class RepairListFragment extends Fragment
        implements View.OnClickListener,
        BaseRefreshListener, RepairManageView, RepairListAdapter.OnConcernListener,
        BaseAdapter.OnItemClickListener, TextView.OnEditorActionListener, PermissionsProcessingUtil.OnPermissionsCallback {
    /**
     *
     */
    private ImageView screeIv;

    /**
     *
     */
    private RecyclerView recyclerView;
    /**
     *
     */
    private PullToRefreshLayout pullToRefreshLayout;
    /**
     * 维修adapter.
     */
    private RepairListAdapter mAdapter;


    /**
     * 维修.
     */
    private RepairListPresenter presenter;
    /**
     * 当前页.
     */
    private int currentPage = 1;
    /**
     * 区分是刷新还是加载更多.
     */
    private boolean isRefresh = true;
    /**
     * 搜索.
     */
    private EditText seachEdt;

    /**
     * 选择的时间.
     */
    private String selectDateStr;

    /**
     * 状态ID.
     */
    private String stateIds;

    /**
     * 执行人ID
     */
    private String excutePersonIds;

    /**
     * 创建人ID
     */
    private String createPersonIds;

    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime = TimeUtil.getDateTimeStr(getDate(new Date()));

    /**
     * 扫码的设备ID
     */
    private Integer equId;

    /**
     * 扫码的工艺位置ID
     */
    private Integer processId;

    private FrameLayout frameLayout;

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
        View view = inflater.from(getActivity())
                .inflate(R.layout.repair_manage_fragment, null);
        initView(view);
        return view;
    }

    /**
     * 初始化View.
     *
     * @param view 根View
     */
    private void initView(final View view) {
        seachEdt = view.findViewById(R.id.query_et);
        seachEdt.setOnEditorActionListener(this);
        view.findViewById(R.id.delete_ibtn).setOnClickListener(this);
        frameLayout = view.findViewById(R.id.no_data_fl);
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.repair_manage));
        screeIv = view.findViewById(R.id.common_iv);
        screeIv.setBackgroundResource(R.mipmap.repair_filter);
        screeIv.setVisibility(View.VISIBLE);
        screeIv.setOnClickListener(this);
        view.findViewById(R.id.search_v).setOnClickListener(this);
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        pullToRefreshLayout.setRefreshListener(this);
        mAdapter = new RepairListAdapter(getActivity(),
                R.layout.repair_list_item);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        presenter = new RepairListPresenterImpl(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnConcernListener(this);
        presenter.setDefaultFilterCondition();
        presenter.getRepairList();
    }

    /**
     * @param v 被点击单view.
     */
    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.common_iv) {
            presenter.showFilterView();
        } else if (v.getId() == R.id.search_v) {
            refresh();
        } else if (v.getId() == R.id.scan_code_iv) {
            PermissionsProcessingUtil.setPermissions(this, LibConfig.CAMERA, this);
        } else if (v.getId() == R.id.delete_ibtn) {
            seachEdt.setText("");
        }
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(final int requestCode,
                                 final int resultCode,
                                 final Intent data) {
        if ((requestCode == RepairFilterActivity.REQUEST
                || resultCode == RepairFilterActivity.REQUEST) && data != null) {
            selectDateStr = data.getStringExtra("selectDateStr");
            stateIds = data.getStringExtra("stateIds");
            excutePersonIds = data.getStringExtra("processingPersonIds");
            startTime = data.getStringExtra("startTime");
            endTime = data.getStringExtra("endTime");
            refresh();
        } else if (requestCode == LibConstants.Fault.PICKPERSON_REQUEST_CODE && data != null) {
            //分派任务
            int processingPersonId = Integer.parseInt(data.getStringExtra("id"));
            int selectItemId = Integer.parseInt(data.getStringExtra("selectItemId"));
            presenter.taskAssign(selectItemId, processingPersonId);
        } else if (requestCode == LibConfig.SCAN_CODE && data != null) {
            String qrCode = data.getStringExtra(CaptureActivity.SCAN_RESULT);
            String type = AnalysisQRCodeUtil.getQRCodeType(qrCode);
            if (AnalysisQRCodeUtil.TYPE_EQUIPMENT.equals(type)) {
                equId = AnalysisQRCodeUtil.getEquipmentId(qrCode);
            } else if (AnalysisQRCodeUtil.TYPE_PROCESS.equals(type)) {
                processId = AnalysisQRCodeUtil.getProcessId(qrCode);
            } else {
                equId = -1;
            }
            refresh();
        }
    }


    /**
     *
     */
    @Override
    public void refresh() {
        currentPage = 1;
        isRefresh = true;
        presenter.getRepairList();
    }

    /**
     *
     */
    @Override
    public void loadMore() {
        currentPage++;
        isRefresh = false;
        presenter.getRepairList();
    }

    /**
     * @return
     */
    @Override
    public Context getContexts() {
        return getActivity();
    }

    /**
     * @return
     */
    @Override
    public String queryName() {
        return seachEdt.getText().toString().trim();
    }

    @Override
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     * 显示维修列表数据
     *
     * @param list 维修列表
     */
    @Override
    public void renderList(final List<RepairVo> list) {
        if (isRefresh) {
            mAdapter.clear();
            pullToRefreshLayout.finishRefresh();
        } else {
            pullToRefreshLayout.finishLoadMore();
        }
        if (list != null && list.size() > 0) {
            mAdapter.addAll(list);
            mAdapter.notifyDataSetChanged();
        } else if (!isRefresh) {
            ToastUtil.makeText(getActivity(), getString(R.string.equiment_all_data));
            backToLastPage();
        }
        if (mAdapter.getItemCount() == 0) {
            frameLayout.setVisibility(View.VISIBLE);
        } else {
            frameLayout.setVisibility(View.GONE);
        }
        mAdapter.notifyDataSetChanged();
        processId = null;
        equId = null;
    }

    /**
     * 如果下一页无数据，则页数变回原来的页数.
     */
    private void backToLastPage() {
        if (currentPage > 1) {
            currentPage--;
        }
    }

    @Override
    public String getStateIds() {
        return stateIds;
    }

    /**
     * @return
     */
    @Override
    public String getCreatePersonIds() {
        return createPersonIds;
    }

    @Override
    public String getExcutePersonIds() {
        return excutePersonIds;
    }

    @Override
    public String getSelectDateStr() {
        return selectDateStr;
    }

    @Override
    public void setSelectDateStr(final String selectDateStr) {
        this.selectDateStr = selectDateStr;
    }

    @Override
    public void renderChangedItem(final List<RepairVo> list) {
        if (list != null && list.size() > 0) {
            mAdapter.setChangedItem(list.get(0));
        }
    }

    /**
     *
     */
    @Override
    public void onResume() {
//        updateChangedItem();
        super.onResume();
    }

    /**
     *
     */
    @Override
    public void updateChangedItem() {
//        int changedItemPosition = mAdapter.getChangedItemPosition();
//        if (changedItemPosition != -1) {
//            presenter.getChangedRepairItem(changedItemPosition + 2);
//            mAdapter.notifyDataSetChanged();
//        }

    }

    @Override
    public String getStartTime() {
        return startTime;
    }

    @Override
    public String getEndTime() {
        return endTime;
    }

    @Override
    public void setStartTime(final String localTimeStr) {
        startTime = localTimeStr;
    }

    @Override
    public void setEndTime(final String localTimeStr) {
        endTime = localTimeStr;
    }

    @Override
    public String getProcessingPersonIds() {
        return excutePersonIds;
    }

    /**
     * @return
     */
    @Override
    public Integer getEquId() {
        return equId;
    }

    /**
     * @return
     */
    @Override
    public Integer getProcessId() {
        return processId;
    }

    /**
     * @param states
     */
    @Override
    public void setStates(final String states) {
        stateIds = states;
    }

    /**
     * 点击事件
     *
     * @param view
     * @param position
     * @param item
     */
    @Override
    public void onClick(final View view, final int position,
                        final Object item) {
        mAdapter.setChangedItemPosition(position);
        RepairVo entity = (RepairVo) item;
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("fragmentUri",
                ArouterPathConstants.Repair.REPAIR_DETAIL);
        paramMap.put("repairId", entity.getId() + "");
        ARouterUtil.toFragment(paramMap);
    }

    @Override
    public void onLongClick(final View view,
                            final int position,
                            final Object item) {

    }

    @Override
    public void callBack(final boolean flag, final String permission) {
        if (flag) {
            ScanCodeUtils.openScanCode(getActivity());
        } else {
            ToastUtil.makeText(getActivity(),
                    getString(R.string.permissions_camera));
        }
    }

    @Override
    public boolean onEditorAction(final TextView textView, final int actionId, final KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            refresh();
            return true;
        }
        return false;
    }

    @Override
    public void concern(final boolean concernFlag, final Integer id) {
        presenter.concern(concernFlag, id);
    }

    private Date getDate(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
    }
}
