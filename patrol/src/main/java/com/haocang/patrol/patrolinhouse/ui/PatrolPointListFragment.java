package com.haocang.patrol.patrolinhouse.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haocang.base.config.LibConfig;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.ScanCodeUtils;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.zxing.app.CaptureActivity;
import com.haocang.patrol.R;
import com.haocang.patrol.patrolinhouse.adapter.PatrolPointAdapter;
import com.haocang.patrol.patrolinhouse.bean.PatrolPointDetailDTO;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskDetailDTO;
import com.haocang.patrol.patrolinhouse.iview.PatrolDetailView;
import com.haocang.patrol.patrolinhouse.presenter.impl.PatrolPointListPresenterImpl;
import com.haocang.patrol.patroloutside.nfcutil.PatrolNfcActivity;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

/**
 * Created by william on 2018/4/4.
 */

public class PatrolPointListFragment
        extends Fragment
        implements PatrolDetailView, BaseRefreshListener,
        View.OnClickListener, PatrolPointAdapter.PatrolOnItemClickListent {
    private PatrolPointAdapter mAdapter;
    private PatrolPointListPresenterImpl mPresenter;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private View mPatrolScanLl;
    private View mPatrolNfcLl;
    private PatrolTaskDetailDTO patrolTaskDetailDTO;
    private PatrolPointListDataInterface dataInterface;
    /**
     *
     */
    private PullToRefreshLayout pullToRefreshLayout;

    /**
     * 扫码后回调后设置
     */
    private String qrCode;

    private View toolLl;


    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater
                .from(getActivity())
                .inflate(R.layout.patrol_point_fragment, null);
        mContext = getActivity();
        initView(view);
        return view;
    }

    private void initView(final View view) {
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        mRecyclerView = view.findViewById(R.id.patrol_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PatrolPointAdapter(R.layout.patrol_point_item, mContext);
        mAdapter.setTaskId(getTaskId());
        mRecyclerView.setAdapter(mAdapter);
        pullToRefreshLayout.setRefreshListener(this);
        mPatrolScanLl = view.findViewById(R.id.patrol_scan_ll);
        mPatrolNfcLl = view.findViewById(R.id.patrol_nfc_iv);
        mPatrolNfcLl.setOnClickListener(this);
        toolLl = view.findViewById(R.id.tool_ll);
        view.findViewById(R.id.patrol_scan_ll).setOnClickListener(this);
        view.findViewById(R.id.patrol_scan_bt).setOnClickListener(this);
        mPresenter = new PatrolPointListPresenterImpl();
        mPresenter.setPatrolDetailView(this);
        mAdapter.setOnItemClickListent(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getPatrolDetailData();
    }


    @Override
    public void onActivityResult(final int requestCode,
                                 final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LibConfig.SCAN_CODE && data != null) {
            qrCode = data.getStringExtra(CaptureActivity.SCAN_RESULT);
            mPresenter.getScanCode();
        } else if (requestCode == PatrolNfcActivity.PATROL_NFC && data != null) {
            qrCode = data.getStringExtra("NFCInfo");
            mPresenter.getScanCode();
        }
    }

    /**
     * 单位千
     */
    private final double unitK = 1000.0;

    /**
     * 显示任务详情数据
     *
     * @param patrolTaskDetailDTO 巡检任务
     */
    @SuppressLint("StringFormatMatches")
    @Override
    public void renderData(final PatrolTaskDetailDTO patrolTaskDetailDTO) {
        if (patrolTaskDetailDTO == null) {
            return;
        }
        String timeCountDown = "";

        Integer validPath = patrolTaskDetailDTO.getValidPathLength();
        if (validPath != null && validPath > patrolTaskDetailDTO.getActualPathLength()) {
            validPath = patrolTaskDetailDTO.getActualPathLength();
        }
        if (validPath == null) {
            validPath = 0;
        }
        String validPlanPath = String.format(mContext.getString(R.string.patrol_valid_pathlength),
                validPath / unitK);
        String completePoints = String.format(mContext.getString(R.string.patrol_points_complete),
                patrolTaskDetailDTO.getInspectedCount() + "/" + patrolTaskDetailDTO.getPointCount());
        mAdapter.replaceAll(patrolTaskDetailDTO.getPatrolPointDetailDTOs());
        mAdapter.setStatu(patrolTaskDetailDTO.getExecuteStatus());
        pullToRefreshLayout.finishRefresh();
        setScanLlVisibility(patrolTaskDetailDTO);
        this.patrolTaskDetailDTO = patrolTaskDetailDTO;

        if (dataInterface != null) {
            dataInterface.setCompleCount(patrolTaskDetailDTO.getInspectedCount() + "/" + patrolTaskDetailDTO.getPointCount());
            if (patrolTaskDetailDTO.isStart()) {
                dataInterface.setDownTime(patrolTaskDetailDTO.getTimeCountdown());
            } else {
                dataInterface.setState(patrolTaskDetailDTO.getShowStatus(), patrolTaskDetailDTO.getExecuteStatus());
            }
            dataInterface.setMileage(validPath / unitK + "KM");
        }
        if (isNfc()) {
            qrCode = getActivity().getIntent().getStringExtra("qrCode");
            mPresenter.getScanCode();
            getActivity().getIntent().putExtra("nfc", false);
        }
    }

    /**
     * 是否是nfc 进入
     *
     * @return
     */
    private boolean isNfc() {
        return getActivity().getIntent().getBooleanExtra("nfc", false);
    }

    /**
     * 设置扫码布局的可见性.
     *
     * @param patrolTaskDetailDTO 巡检任务详情
     */
    private void setScanLlVisibility(final PatrolTaskDetailDTO patrolTaskDetailDTO) {
        if (patrolTaskDetailDTO.canStart()) {
            mPatrolScanLl.setVisibility(View.VISIBLE);
            mPatrolNfcLl.setVisibility(View.GONE);
            toolLl.setVisibility(View.VISIBLE);
        } else {
            mPatrolScanLl.setVisibility(View.GONE);
            mPatrolNfcLl.setVisibility(View.GONE);
            toolLl.setVisibility(View.GONE);
        }
    }

    /**
     * 刷新
     */
    @Override
    public void refresh() {
        mPresenter.getPatrolDetailData();
    }

    /**
     * 加载更多
     */
    @Override
    public void loadMore() {
        pullToRefreshLayout.finishLoadMore();
    }

    /**
     * 获取上下文参数
     *
     * @return
     */
    @Override
    public Context getContext() {
        return mContext;
    }

    /**
     * 获取巡检任务ID
     *
     * @return
     */
    @Override
    public Integer getTaskId() {
        int taskId = getActivity().getIntent().getIntExtra("taskId", -1);
        return taskId;
    }

    /**
     * 获取巡检二维码内容
     *
     * @return
     */
    @Override
    public String getQRCode() {
        return qrCode;
    }

    /**
     * 是否是地图的列表
     *
     * @return
     */
    public boolean isFromMap() {
        return getActivity().getIntent().getBooleanExtra("mapFlag", false);
    }

    /**
     * @param patrolPointId 巡检点ID
     * @return
     */
    @Override
    public boolean isTaskCompleteOthers(final Integer patrolPointId) {
        return mAdapter.isTaskCompleteOthers(patrolPointId);
    }

    @Override
    public boolean offLineIsTaskCompleteOthers(Integer patrolPointId) {
        return mAdapter.offLineisTaskCompleteOthers(patrolPointId);
    }


    /**
     * @param view 被点击的View
     */
    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.patrol_scan_bt) {
            PermissionsProcessingUtil
                    .setPermissions(this, LibConfig.CAMERA, new PermissionsProcessingUtil.OnPermissionsCallback() {
                        @Override
                        public void callBack(final boolean flag, final String permission) {
                            if (flag) {
                                ScanCodeUtils.openScanCode(getActivity());
                            } else {
                                ToastUtil.makeText(getActivity(), getString(R.string.permissions_camera));
                            }
                        }
                    });
        } else if (view.getId() == R.id.patrol_nfc_iv) {
            Intent intent = new Intent(getActivity(), PatrolNfcActivity.class);
            getActivity().startActivityForResult(intent, PatrolNfcActivity.PATROL_NFC);
        }

    }

    /**
     * 获取任务名称
     *
     * @return
     */
    public String getTaskName() {
        String taskName = getActivity().getIntent().getStringExtra("taskName");
        return taskName;
    }

    /**
     * 是否有巡检权限
     *
     * @return
     */
    @Override
    public boolean hasPatrolAuth() {
        return patrolTaskDetailDTO.canStart();
    }

    /**
     * @return
     */
    @Override
    public String getTaskEndTime() {
        return null;
    }

    @Override
    public String getProcessName(int pointId) {
        if (patrolTaskDetailDTO != null && patrolTaskDetailDTO.getPatrolPointDetailDTOs() != null && patrolTaskDetailDTO.getPatrolPointDetailDTOs().size() > 0) {
            String processName = "";
            for (int i = 0; i < patrolTaskDetailDTO.getPatrolPointDetailDTOs().size(); i++) {
                PatrolPointDetailDTO entity = patrolTaskDetailDTO.getPatrolPointDetailDTOs().get(i);
                if (entity.getId() == pointId) {
                    processName = entity.getRelatedProcessNames();
                }
            }
            return processName;
        } else {
            return "";
        }
    }

    /**
     * @param dto 被点击的巡检点
     */
    @Override
    public void onItemClickListent(final PatrolPointDetailDTO dto) {
        mPresenter.onItemClick(dto);
    }


    public interface PatrolPointListDataInterface {
        void setCompleCount(String count);

        void setState(String showState, String state);

        void setDownTime(String time);

        void setMileage(String mileage);
    }


    public void setPatrolPointListData(PatrolPointListDataInterface dataInterface) {
        this.dataInterface = dataInterface;
    }

}