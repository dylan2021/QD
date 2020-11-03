package com.haocang.fault.list.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.fault.R;
import com.haocang.fault.list.bean.FaultManagerEntity;
import com.haocang.fault.list.iview.FaultDetailsView;
import com.haocang.fault.list.presenter.FaultDetailsPresenter;
import com.haocang.fault.list.presenter.impl.FaultDetailsPresenterImpl;
import com.haocang.inventory.config.ARouteConfig;

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
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/2614:34
 * 修 改 者：
 * 修改时间：
 */

@Route(path = LibConfig.AROUTE_FAULT_POST_MANAGER_DETAILS)
public class FaultDetailsFragment extends Fragment implements View.OnClickListener, FaultDetailsView {
    @Autowired
    String faultId;//缺陷id

    private TextView titleNameTv;

    private FaultDetailsPresenter presenter;

    private TextView faultTypeTv; //缺陷类型

    private TextView faultNuberTv; //缺陷编号

    private ImageView falutStateIv; //缺陷状态/

    private TextView faultCreateUserTv; //申报人

    private TextView faultSeverityTv;//严重程度

    private TextView processingNameTv;//处理人

    private TextView faultEquTv;//相关设备

    private TextView faultTimeTv;//时间

    private TextView faultProcessingTv;//工艺位置

    private TextView faultNewPeopleTv;//当前节点处理人
    private TextView faultOrgTv;//处理人所在组织
    private TextView faultLowStateTv;//最底下的缺陷状态
    private String remark;//备注
    private String imgUrl;//文件路径

    private TextView processingResultTv;

    private TextView equipmentRepairTv;

    private FaultManagerEntity faultManagerEntity;

    private String lastRecordId;//c处理结果填报 和查看处理结果详情

    private String processingResultsArray;//最新一次填的处理结果

    private int processed;

    private TextView faultPeopleOrgTv;

    private TextView patrolTv;//相关巡检

    private TextView spareApplicationTv;//备件申请

    private ImageView assignmentIv;//分派

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fault_details, null);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getText(R.string.falut_details));
        assignmentIv = view.findViewById(R.id.common_iv);
        assignmentIv.setBackgroundResource(R.drawable.icon_fault_assign);
        assignmentIv.setOnClickListener(this);
        presenter = new FaultDetailsPresenterImpl(this);
        processingResultTv = view.findViewById(R.id.processing_result_tv);
        equipmentRepairTv = view.findViewById(R.id.equipment_repair_tv);
        faultTypeTv = view.findViewById(R.id.fault_type_tv);
        faultNuberTv = view.findViewById(R.id.fault_nuber_tv);
        falutStateIv = view.findViewById(R.id.falut_state_iv);
        faultCreateUserTv = view.findViewById(R.id.fault_create_user_tv);
        faultSeverityTv = view.findViewById(R.id.fault_severity_tv);
        processingNameTv = view.findViewById(R.id.processing_name_tv);
        faultLowStateTv = view.findViewById(R.id.fault_low_statu_tv);
        faultOrgTv = view.findViewById(R.id.fault_org_tv);
        faultEquTv = view.findViewById(R.id.fault_equ_tv);
        spareApplicationTv = view.findViewById(R.id.spare_application_tv);
        spareApplicationTv.setOnClickListener(this);
        faultNewPeopleTv = view.findViewById(R.id.fault_new_people_tv);
        faultTimeTv = view.findViewById(R.id.fault_time_tv);
        faultProcessingTv = view.findViewById(R.id.fault_processing_tv);
        faultPeopleOrgTv = view.findViewById(R.id.fault_new_people_org_tv);
        patrolTv = view.findViewById(R.id.patrol_tv);
        view.findViewById(R.id.fault_scene_ll).setOnClickListener(this);
        view.findViewById(R.id.fault_processing_ll).setOnClickListener(this);
        view.findViewById(R.id.equipment_repair_tv).setOnClickListener(this);
        view.findViewById(R.id.processing_result_tv).setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        if (id == R.id.fault_scene_ll) {
            //现场记录
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", LibConfig.AROUTE_FAULT_POST_SCENE);
            if (!TextUtils.isEmpty(remark)) {
                map.put("remark", remark);
            }
            if (!TextUtils.isEmpty(imgUrl)) {
                map.put("imgUrl", imgUrl);
            }
            ARouterUtil.toFragment(map);
        } else if (id == R.id.fault_processing_ll) {
            toProcessingProgress();
        } else if (id == R.id.equipment_repair_tv) {
            //设备报修

            if (faultManagerEntity != null && faultManagerEntity.getReported() == FaultManagerEntity.REPORTED) {
                /**
                 * 如果已报修过，只能查看
                 */
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("fragmentUri",
                        ArouterPathConstants.Repair.REPAIR_DETAIL);
                paramMap.put("repairId", faultManagerEntity.getRepairId() + "");
                ARouterUtil.toFragment(paramMap);
            } else {
                /**
                 * 如果未报修过，进行报修
                 */
                Map<String, Object> map = new HashMap<>();
                map.put("fragmentUri", LibConfig.AROUTE_FAULT_EQUIPMENT_REPAIR);
                map.put("faultId", getFaultId());
                map.put("id", faultManagerEntity.getEquId() + "");
                map.put("name", faultManagerEntity.getEquName());
                map.put("code", faultManagerEntity.getEqCode());
                map.put("orgId", faultManagerEntity.getOrgId());
                map.put("orgName", faultManagerEntity.getOrgName());
                map.put("processName", faultManagerEntity.getProcessName());
                map.put("processId", faultManagerEntity.getProcessId() + "");
                ARouterUtil.toFragment(map);
            }
        } else if (id == R.id.processing_result_tv) {
            //处理结果
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", LibConfig.AROUTE_PROCESSING_RESULT);
            map.put("faultId", lastRecordId);
            map.put("processName", faultManagerEntity.getProcessName());
            map.put("equName", faultManagerEntity.getEquName());
            map.put("startTime", faultManagerEntity.getCreateDate());
            if (!TextUtils.isEmpty(processingResultsArray) && processed == 1) {
                map.put("faultArray", processingResultsArray);
            }
            ARouterUtil.toFragment(map);
        } else if (id == R.id.spare_application_tv) {
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", ARouteConfig.SPARE_PART_MAIN);
            map.put("orderId", faultManagerEntity.getId());
            map.put("type", 1);
            map.put("applicantPurpose", faultManagerEntity.getFaultNumber());
            if (faultManagerEntity.getState() == 4 || faultManagerEntity.getState() == 3 || AppApplication.getInstance().getUserEntity().getId() != faultManagerEntity.getProcessingPersonId()) {
                //4已完成    3关闭
                map.put("executor", false);//否则只能看 记录
            } else {
                map.put("executor", true);//自己是执行人的话 可以备件申请
            }
            ARouterUtil.toFragment(map);
        } else if (id == R.id.common_iv) {
            Map<String, Object> map = new HashMap<>();
            map.put("faultId", faultManagerEntity.getId() + "");
            map.put("selectItemId", faultManagerEntity.getId() + "");
            map.put("fragmentUri", LibConfig.AROUTE_FAULT_POST_PROCESSING);
            if (AppApplication.getInstance().getUserEntity().getId()
                    == faultManagerEntity.getProcessingPersonId()) {
                map.put("isCurrentUserExcute", true);
            }
            ARouterUtil.startActivityForResult(map, getActivity(),
                    LibConstants.Fault.PICKPERSON_REQUEST_CODE);
        }
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
        }
    }

    /**
     * 流程处理
     */
    private void toProcessingProgress() {
        Map<String, Object> map = new HashMap<>();
        map.put("fragmentUri", LibConfig.AROUTE_FAULT_PROCESSINGPROGRESS);
        map.put("faultId", getFaultId());
        ARouterUtil.toFragment(map);
    }

    @Override
    public String getFaultId() {
        return getActivity().getIntent().getStringExtra("faultId");
    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public void setDetailsData(final FaultManagerEntity entity) {
        if (entity != null) {
            lastRecordId = entity.getLastRecordId() + "";
            faultManagerEntity = entity;
            remark = entity.getRemark();
            if (entity.getPictureVideos() != null && entity.getPictureVideos().size() > 0) {
                imgUrl = new Gson().toJson(entity.getPictureVideos());
            }
            patrolTv.setText(entity.getPatrolName());
            faultTypeTv.setText(entity.getFaultTypeName());
            faultNuberTv.setText(entity.getFaultNumber());
            falutStateIv.setBackgroundResource(entity.getBackgroundResource());
            faultCreateUserTv.setText(entity.getCreateUser());
            faultSeverityTv.setText(entity.getSeverityTypeName());
            processingNameTv.setText(entity.getProcessingPersonName());
            faultOrgTv.setText(entity.getOrgName());
            faultEquTv.setText(entity.getEquName());
            faultLowStateTv.setText(entity.getNewState());
            faultLowStateTv.setTextColor(Color.parseColor(entity.getNewStateColor()));
            faultNewPeopleTv.setText(getActivity().getString(R.string.fault_current_excute_person) + "："
                    + entity.getProcessingPersonName());
            if (!TextUtils.isEmpty(entity.getProcessingPersonName())) {
                faultPeopleOrgTv.setText(entity.getOrgName());
            }
            faultTimeTv.setText(TimeTransformUtil.getShowLocalTime(entity.getAssignDate()));
            faultProcessingTv.setText(entity.getProcessName());
//                spareApplicationTv.setVisibility(View.GONE);
//            if (entity.getState() == 5) {
//                //未分配的状态 按钮 备件申请按钮不可见
//                spareApplicationTv.setVisibility(View.GONE);
//            } else {
//                spareApplicationTv.setVisibility(View.VISIBLE);
//            }
            if (entity.canExcute2()) {
                processingResultTv.setVisibility(View.VISIBLE);
                equipmentRepairTv.setVisibility(View.VISIBLE);
                if (entity.getReported() == FaultManagerEntity.REPORTED) {
                    equipmentRepairTv.setText(getActivity().getString(R.string.fault_look_report_repair));
                }
                if (entity.getProcessed() == FaultManagerEntity.PROCESSED) {
                    processingResultTv.setText(getActivity().getString(R.string.fault_look_report_result));
                }
                processed = entity.getProcessed();
            } else {
                processingResultTv.setVisibility(View.GONE);
                equipmentRepairTv.setVisibility(View.GONE);
            }
            if (entity.canExcute()) {
                assignmentIv.setVisibility(View.VISIBLE);
            } else {
                assignmentIv.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void setProcessingProgressList(final String json) {
        if (!TextUtils.isEmpty(json)) {
            processingResultsArray = json;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getDetailsData();
    }
}
