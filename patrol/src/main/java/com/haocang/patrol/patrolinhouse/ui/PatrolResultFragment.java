package com.haocang.patrol.patrolinhouse.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.ui.CommonActivity;
import com.haocang.offline.home.ui.OffLineHomeFragment;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskResultDTO;
import com.haocang.patrol.patrolinhouse.iview.PatrolResultView;
import com.haocang.patrol.patrolinhouse.presenter.PatrolResultPresenter;
import com.haocang.patrol.patrolinhouse.presenter.impl.PatrolResultPresenterImpl;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题： 缺陷提交成功.
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/4/9下午7:18
 * 修  改  者：
 * 修改时间：
 */
@Route(path = ArouterPathConstants.Patrol.PATROL_RESULT)
public class PatrolResultFragment extends Fragment implements PatrolResultView, View.OnClickListener {
    private Context mContext;
    private TextView actualPathLengthTv;
    private TextView validPathLengthTv;
    private TextView timeCostTv;
    private TextView pointCompleteTv;
    private TextView pointStepCompleteTv;
    private TextView faultCountTv;
    private PatrolResultPresenter patrolResultPresenter;
    /**
     *
     */
    private final double unitK = 1000.0;

    @Nullable
    @Override
    public View onCreateView(@Nullable final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.patrol_result_fragment, null);
        mContext = getActivity();
        initView(view);
        return view;
    }

    private void initView(final View view) {
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getTaskName());
        actualPathLengthTv = view.findViewById(R.id.patrol_actual_pathlength__tv);
        validPathLengthTv = view.findViewById(R.id.patrol_valid_pathlength_tv);
        timeCostTv = view.findViewById(R.id.patrol_timecost_tv);
        pointCompleteTv = view.findViewById(R.id.patrol_point_complete_tv);
        pointStepCompleteTv = view.findViewById(R.id.patrol_pointstep_complete_tv);
        faultCountTv = view.findViewById(R.id.patrol_fault_count_tv);
        patrolResultPresenter = new PatrolResultPresenterImpl(this);
        patrolResultPresenter.getResultData();
        view.findViewById(R.id.patrol_tasklist_tv).setOnClickListener(this);
        view.findViewById(R.id.patrol_backhome_tv).setOnClickListener(this);
        if (!isMapPatrol()) {
            actualPathLengthTv.setVisibility(View.GONE);
            view.findViewById(R.id.patrol_actual_pathlength__tv2).setVisibility(View.GONE);
            view.findViewById(R.id.patrol_valid_pathlength_tv2).setVisibility(View.GONE);
            validPathLengthTv.setVisibility(View.GONE);
        }
    }


    @Override
    public void renderData(final PatrolTaskResultDTO taskResult) {
        Integer validPath = taskResult.getValidPathLength();
        if (validPath > taskResult.getActualPathLength()) {
            validPath = taskResult.getActualPathLength();
        }
        String actualPathLenth = String.format(mContext.getString(R.string.patrol_actual_pathlength),
                taskResult.getActualPathLength() / unitK + "");
        actualPathLengthTv.setText(taskResult.getActualPathLength() / unitK + " km");

        String validPathLength = String.format(mContext.getString(R.string.patrol_valid_pathlength),
                validPath / unitK + "");
        validPathLengthTv.setText(validPath / unitK + " km");

        String timeCost = String.format(mContext.getString(R.string.patrol_timecost), taskResult.getTimeCostShow());

        timeCostTv.setText(taskResult.getTimeCostShow() + " h");
        String pointComplete = String.format(mContext.getString(R.string.patrol_point_complete),
                taskResult.getInspectedCount() + "/" + taskResult.getPointCount());
        pointCompleteTv.setText(taskResult.getInspectedCount() + "/" + taskResult.getPointCount());
        String pointStepComplete = String.format(mContext.getString(R.string.patrol_pointstep_complete), taskResult.getHasResultCount() + "/" + taskResult.getStepCount());
        pointStepCompleteTv.setText(taskResult.getHasResultCount() + "/" + taskResult.getStepCount());
        String faultCount = String.format(mContext.getString(R.string.patrol_fault_count), taskResult.getFaultCount() + "");
        faultCountTv.setText(taskResult.getFaultCount() + "");
    }

    @Override
    public Integer getTaskId() {
        int taskId = getActivity().getIntent().getIntExtra("taskId", -1);
        return taskId;
    }

    public String getTaskName() {
        return getActivity().getIntent().getStringExtra("taskName");
    }


    public boolean isMapPatrol() {
        return getActivity()
                .getIntent()
                .getBooleanExtra("isMapPatrol", false);
    }

    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.patrol_tasklist_tv) {
            getActivity().finish();
        } else if (view.getId() == R.id.patrol_backhome_tv) {
            if (OffLineOutApiUtil.isOffLine()) {
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("fragmentName", OffLineHomeFragment.class.getName());
                startActivity(intent);
                getActivity().finish();
            } else {
                while (AppApplication.getInstance().getActivityStackSize() > 1) {
                    AppApplication.getInstance().finishActivity();
                }
            }

        }
    }
}
