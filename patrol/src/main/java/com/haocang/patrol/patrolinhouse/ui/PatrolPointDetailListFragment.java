package com.haocang.patrol.patrolinhouse.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.cj.videoeditor.Constants;
import com.example.cj.videoeditor.WaterMark;
import com.example.cj.videoeditor.activity.RecordedActivity;
import com.example.cj.videoeditor.picture.ImageUtil;
import com.haocang.base.bean.PictureEntity;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.ui.CameraFragment;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.BDSendTraceUtil;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.constants.OfflineConstants;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.patrolinhouse.adapter.PatrolPointDetailAdapter;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskPointStep;
import com.haocang.patrol.patrolinhouse.iview.PatrolPointDetailListView;
import com.haocang.patrol.patrolinhouse.presenter.impl.PatrolPointDetailListPresenterImpl;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.utils.FileUtils;

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
 * 创建时间：2018/4/8下午6:41
 * 修  改  者：
 * 修改时间：
 * 巡检步骤提交界面
 */
public class PatrolPointDetailListFragment extends Fragment
        implements PatrolPointDetailListView,
        BaseRefreshListener, View.OnClickListener,
        TextView.OnEditorActionListener {
    private PatrolPointDetailAdapter mAdapter;
    private PatrolPointDetailListPresenterImpl mPresenter;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private PullToRefreshLayout pullToRefreshLayout;
    private String stepSr;
    /**
     * 查询输入框
     */
    private EditText queryEt;
    private TextView exemptionTv;

    private Button compleBtn;//完成

    private int position;
//    /**
//     * 巡检点上次提交时间
//     */
//    private Date lastUpdateTime;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.patrol_pointdetail_list_fragment, null);
        mContext = getActivity();
        initView(view);
        return view;
    }

    private void initView(final View view) {
        queryEt = view.findViewById(R.id.query_et);
        queryEt.setHint("请输入巡检步骤");
        view.findViewById(R.id.delete_ibtn).setOnClickListener(this);
        queryEt.setHintTextColor(Color.parseColor("#ff9b9b9b"));
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getPointName());
        exemptionTv = view.findViewById(R.id.common_tv);
        exemptionTv.setVisibility(View.VISIBLE);
        exemptionTv.setText("免检");
        if (OffLineOutApiUtil.isOffLine()) {//离线模式需要禁止免检
            exemptionTv.setVisibility(View.INVISIBLE);
        }
        exemptionTv.setOnClickListener(this);
        compleBtn = view.findViewById(R.id.comple_btn);
        compleBtn.setOnClickListener(this);
        mPresenter = new PatrolPointDetailListPresenterImpl(this);
        mRecyclerView = view.findViewById(R.id.patrol_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PatrolPointDetailAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        pullToRefreshLayout.setRefreshListener(this);
        queryEt.setOnEditorActionListener(this);
        mAdapter.setTaskId(getTaskId());
        mAdapter.setTaskName(getTaskName());
        mAdapter.setPatrolPointDetailListView(this);
        mAdapter.setOnOpenCamera(new PatrolPointDetailAdapter.OnOpenCamera() {
            @Override
            public void onOpnCamera(int ps, final String step) {
                stepSr = step;
                position = ps;
                showMulti(step);
            }
        });
        mPresenter.getPatrolPointDetailList();
    }


    private void showMulti(final String step) {
        final String[] stateLabel = getActivity().getResources().getStringArray(R.array.patrol_multi_media);
        new AlertView(null, null, "取消", null,
                stateLabel, getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                //如果是-1说明点击的取消,点击取消时，不做任何事
                if (position == -1) {
                    return;
                } else if (position == 0) {
                    PermissionsProcessingUtil.setPermissions(PatrolPointDetailListFragment.this, LibConfig.CAMERA, cameraCallBack);
                } else if (position == 1) {
                    openVideo(step);
                }
            }
        }).show();
    }

    private void openVideo(String step) {
        Intent intent = new Intent(getActivity(), RecordedActivity.class);
        intent.putExtra("person", AppApplication.getInstance().getUserEntity().getName());
        if (BDSendTraceUtil.getInstance().getLocation() != null) {
            intent.putExtra("address", BDSendTraceUtil.getInstance().getLocation().getAddrStr());
        } else {
            intent.putExtra("address", "");
        }
        if (!TextUtils.isEmpty(LibConfig.weather)) {
            intent.putExtra("weather", LibConfig.weather + " " + LibConfig.temperature);
        } else {
            intent.putExtra("weather", "");
        }
        intent.putExtra("processName", getProcessName() + "");
        intent.putExtra("equName", step);
        startActivityForResult(intent, 1039);
    }

    private PermissionsProcessingUtil.OnPermissionsCallback cameraCallBack = new PermissionsProcessingUtil.OnPermissionsCallback() {
        @Override
        public void callBack(boolean flag, String permission) {
            if (flag) {
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("fragmentName", CameraFragment.class.getName());
                getActivity().startActivityForResult(intent, 1122);
            } else {
                ToastUtil.makeText(getActivity(), getString(R.string.permissions_camera));
            }
        }
    };

    @Override
    public String getQueryName() {
        return queryEt.getText().toString();
    }


    /**
     *
     */
    private static final int POSTFAULT_REQUEST_CODE = 9001;
    private static final int FROM_PATROL_RESULT_CODE = 9002;

    @Override
    public void postFault(final int position) {
        Map<String, Object> map = new HashMap<>();
        map.put("fragmentUri", OfflineConstants.POST_FAULT);
        map.put("patrolId", getTaskId() + "");
        map.put("taskName", getTaskName());
        ARouterUtil.startActivityForResult(map, getActivity(), POSTFAULT_REQUEST_CODE);
    }

    @Override
    public void submitSuccess() {
        ToastUtil.makeText(getActivity(),
                getActivity().getString(R.string.patrol_success));
    }

    @Override
    public void submitFail(String error) {
        compleBtn.setClickable(true);
        ToastUtil.makeText(getActivity(),
                error);
    }

    @Override
    public void setClickable(boolean flag) {
        compleBtn.setClickable(flag);
    }

    @Override
    public void exemptionSubmitSuccess() {
        getActivity().getIntent().putExtra("faultCount", getFaultCount());
        Intent intent = new Intent(getActivity(), CommonActivity.class);
        intent.putExtra("fragmentName", PatrolExemptionDetailListFragment.class.getName());
        intent.putExtra("taskId", getTaskId());
        intent.putExtra("pointId", getPointId());
        intent.putExtra("id", getPatrolPointId());
        intent.putExtra("faultCount", getFaultCount());
        intent.putExtra("pointName", getPointName());
        intent.putExtra("isMapPatrol", isMapPatrol());
        intent.putExtra("resultUpdateTime", getLastResultUpdateTime());
        intent.putExtra("taskName", getTaskName());
        startActivityForResult(intent, 2019);
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == POSTFAULT_REQUEST_CODE) {
            if (data != null && !TextUtils.isEmpty(data.getStringExtra("faultId"))) {
                mAdapter.setAbnormalResult();
            } else {
                mAdapter.setNullResult();
            }
        } else if (resultCode == 2019) {
            mPresenter.getPatrolPointDetailList();
        } else if (resultCode == 1039 && data != null) {
            String video = data.getStringExtra("videoUrl");
            List<PictureEntity> list = new ArrayList<>();
            PictureEntity fileEntity = new PictureEntity();
            fileEntity.setType(1);
            fileEntity.setVideoPath(video);
            list.add(fileEntity);
            if (mAdapter.getPointSteps().get(position).getFileList() != null && mAdapter.getPointSteps().get(position).getFileList().size() > 0) {
                mAdapter.getPointSteps().get(position).getFileList().addAll(list);
            } else {
                mAdapter.getPointSteps().get(position).setFileList(list);
            }
            mAdapter.notifyDataSetChanged();
        } else if (requestCode == 1122 && data != null) {
            String pic = data.getStringExtra("picturePath");
            File fi = new File(pic);
            Bitmap bitmap = CompressHelper.getDefault(getActivity()).compressToBitmap(fi);//压缩图片
            if (bitmap != null) {
                Intent intent = new Intent();
                intent.putExtra("person", AppApplication.getInstance().getUserEntity().getName());
                if (BDSendTraceUtil.getInstance().getLocation() != null) {
                    intent.putExtra("address", BDSendTraceUtil.getInstance().getLocation().getAddrStr());
                } else {
                    intent.putExtra("address", "");
                }
                intent.putExtra("processName", getProcessName() + "");
                intent.putExtra("equName", stepSr);
                if (!TextUtils.isEmpty(LibConfig.weather)) {
                    intent.putExtra("weather", LibConfig.weather + " " + LibConfig.temperature);
                } else {
                    intent.putExtra("weather", "");
                }
                Bitmap map = ImageUtil.createWaterMaskLeftBottom(getActivity(), bitmap, Constants.loadBitmapFromView(new WaterMark(getActivity(), intent, R.layout.patrol_mark_view).getWaterMarkView()), 0, 0);
                File file = FileUtils.saveBitmapFile(map);
                List<PictureEntity> list = new ArrayList<>();
                PictureEntity fileEntity = new PictureEntity();
                fileEntity.setType(0);
                fileEntity.setLocalImgPath(file.getPath());
                list.add(fileEntity);
                if (mAdapter.getPointSteps().get(position).getFileList() != null && mAdapter.getPointSteps().get(position).getFileList().size() > 0) {
                    mAdapter.getPointSteps().get(position).getFileList().addAll(list);
                } else {
                    mAdapter.getPointSteps().get(position).setFileList(list);
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.comple_btn) {
            compleBtn.setClickable(false);
            mPresenter.complete();
        } else if (view.getId() == R.id.common_tv) {
            mPresenter.completeExemption();
        } else if (view.getId() == R.id.delete_ibtn) {
            queryEt.setText("");
        }
    }

    /**
     * 上拉刷新
     */
    @Override
    public void refresh() {
        pullToRefreshLayout.finishRefresh();
    }

    /**
     * 加载更多，不做动作，直接去掉动作
     */
    @Override
    public void loadMore() {
        pullToRefreshLayout.finishLoadMore();
    }

    /**
     * @param list 列表数据
     */
    @Override
    public void renderList(final List<PatrolTaskPointStep> list) {
        mAdapter.clean();
        mAdapter.notifyDataSetChanged();
        for (PatrolTaskPointStep item : list) {
            if ("/".equals(item.getStepResult())) {
                item.setStepResultType(3);
            }
        }
        mAdapter.setList(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isTaskComplete() {
        boolean taskCompleteFlag = getActivity().getIntent().getBooleanExtra("taskCompleteFlag", false);
        if (taskCompleteFlag) {
            taskCompleteFlag = mAdapter.isAllComplete();
        }
        return taskCompleteFlag;
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

    /**
     * 巡检点id
     *
     * @return
     */
    @Override
    public Integer getPointId() {
        int pointId = getActivity().getIntent().getIntExtra("pointId", -1);
        return pointId;
    }

    /**
     * 巡检工单下巡检点id
     *
     * @return
     */
    @Override
    public Integer getPatrolPointId() {
        int id = getActivity().getIntent().getIntExtra("id", -1);
        return id;
    }

    @Override
    public Integer getHasResultCount() {
        return mAdapter.getHasResultCount();
    }

    @Override
    public Integer getFaultCount() {
        return mAdapter.getFaultCount();
    }

    @Override
    public Integer getNewFaultCount() {
        int originalFaultCount = getActivity().getIntent().getIntExtra("faultCount", 0);
        return mAdapter.getFaultCount() - originalFaultCount;
    }

    @Override
    public List<PatrolTaskPointStep> getPatrolPointSteps() {
        return mAdapter.getPointSteps();
    }

    @Override
    public String getLastResultUpdateTime() {
        return getActivity().getIntent().getStringExtra("resultUpdateTime");
    }

    /**
     * 当前巡检点结果是否全部填写了
     *
     * @return
     */
    @Override
    public boolean isPointCompleteAll() {
        return mAdapter.isAllComplete();
    }

    /**
     * @param textView
     * @param actionId
     * @param event
     * @return
     */
    @Override
    public boolean onEditorAction(final TextView textView, final int actionId,
                                  final KeyEvent event) {
//        if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
//            mPresenter.getPatrolPointDetailList();
//            return true;
//        }
        if (textView.getId() == R.id.query_et) {
            mPresenter.getPatrolPointDetailList();
        }
        return false;
    }

    @Override
    public boolean isMapPatrol() {
        return getActivity()
                .getIntent()
                .getBooleanExtra("isMapPatrol", false);
    }

    /**
     * 获取任务名称
     *
     * @return
     */
    @Override
    public String getTaskName() {
        return getActivity().getIntent().getStringExtra("taskName");
    }

    /**
     * 获取巡检点名称
     *
     * @return
     */
    public String getPointName() {
        return getActivity().getIntent().getStringExtra("pointName");
    }


    public String getProcessName() {
        return getActivity().getIntent().getStringExtra("processName");
    }


}

