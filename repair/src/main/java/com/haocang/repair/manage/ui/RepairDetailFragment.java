package com.haocang.repair.manage.ui;

import android.content.Context;
import android.content.Intent;
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

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.inventory.config.ARouteConfig;
import com.haocang.repair.R;
import com.haocang.repair.manage.bean.FaultDetailVo;
import com.haocang.repair.manage.bean.RepairConstans;
import com.haocang.repair.manage.bean.RepairDto;
import com.haocang.repair.manage.bean.RepairRecordVo;
import com.haocang.repair.manage.bean.RepairVo;
import com.haocang.repair.manage.iview.RepairDetailView;
import com.haocang.repair.manage.presenter.RepairDetailPresenter;
import com.haocang.repair.manage.presenter.impl.RepairDetailPresenterImpl;

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
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/2614:34
 * 修 改 者：
 * 修改时间：
 */

@Route(path = ArouterPathConstants.Repair.REPAIR_DETAIL)
public class RepairDetailFragment extends Fragment
        implements View.OnClickListener, RepairDetailView {

    /**
     *
     */
    private RepairDetailPresenter presenter;

    /**
     * 故障设备.
     */
    private TextView repairEquipmentTv;

    /**
     * 设备编号.
     */
    private TextView repairEquipmentCodeTv;

    /**
     * 工艺位置.
     */
    private TextView repairProcessPositionTv;

    /**
     * 故障原因.
     */
    private TextView repairFaultReasonTv;

    /**
     * 紧急程度.
     */
    private TextView repairDegreeTv;

    /**
     * 报障人.
     */
    private TextView repairReportPersonTv;

    /**
     * 报障人所在组织
     */
    private TextView repairReportPersonOrgTv;

    /**
     * 报障时间
     */
    private TextView repairReportDateTv;

    /**
     * 维修状态
     */
    private TextView repairStatusTv;

    /**
     * 当前处理人
     */
    private TextView repairExcutePersonTv;

    /**
     * 维修日期
     */
    private TextView repairExcuteDateTv;

    /**
     * 维修状态
     */
    private ImageView repairStateIv;

    /**
     *
     */
    private String processingProgressArray;

    /**
     * 维修详情
     */
    private RepairDto repairDto;


    /**
     * 如果填过处理结果 显示 查看
     */
    private TextView repairNameTv;

    private TextView spareApplicationTv;//备件申请


    private ImageView assignmentIv;//分派

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
                .inflate(R.layout.repair_detail_fragment, null);
        initView(view);
        return view;
    }

    /**
     * 设置数据.
     *
     * @param view 根View
     */
    private void initView(final View view) {
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getText(R.string.repair_details));
        assignmentIv = view.findViewById(R.id.common_iv);
        assignmentIv.setBackgroundResource(R.mipmap.icon_repair_assign);
        assignmentIv.setOnClickListener(this);
        spareApplicationTv = view.findViewById(R.id.spare_parts_application_tv);
        spareApplicationTv.setOnClickListener(this);

        spareApplicationTv.setVisibility(View.GONE);

        repairEquipmentTv = view.findViewById(R.id.repair_equipment_tv);
        repairEquipmentCodeTv = view.findViewById(R.id.repair_equipment_code_tv);
        repairProcessPositionTv = view.findViewById(R.id.repair_process_position_tv);
        repairFaultReasonTv = view.findViewById(R.id.repair_fault_reason_tv);
        repairDegreeTv = view.findViewById(R.id.repair_degree_tv);
        repairReportPersonTv = view.findViewById(R.id.repair_report_person_tv);
        repairReportPersonOrgTv = view.findViewById(R.id.repair_report_person_org_tv);
        repairReportDateTv = view.findViewById(R.id.repair_report_date_tv);
        repairStatusTv = view.findViewById(R.id.repair_status_tv);
        repairExcutePersonTv = view.findViewById(R.id.repair_excute_person_tv);
        repairExcuteDateTv = view.findViewById(R.id.repair_excute_date_tv);
        repairStateIv = view.findViewById(R.id.repair_state_iv);
        repairNameTv = view.findViewById(R.id.repair_processing_result_tv);
        repairNameTv.setOnClickListener(this);
        view.findViewById(R.id.repair_look_remark_ll).setOnClickListener(this);
        view.findViewById(R.id.repair_look_fault_ll).setOnClickListener(this);
        view.findViewById(R.id.repair_processing_ll).setOnClickListener(this);
        presenter = new RepairDetailPresenterImpl(this);
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        if (id == R.id.repair_look_remark_ll) {
            //现场记录
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", ArouterPathConstants.Repair.REPAIR_LOOK_REMARK);
            if (repairDto != null) {
                RepairVo repairVo = repairDto.getRepairVo();
                if (repairVo != null && !TextUtils.isEmpty(repairVo.getRemark())) {
                    map.put("remark", repairVo.getRemark());
                }
            }
            ARouterUtil.toFragment(map);
        } else if (id == R.id.repair_look_fault_ll) {
            //现场记录
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", ArouterPathConstants.Repair.REPAIR_FAULT_DETAIL);
            if (repairDto != null) {
                FaultDetailVo faultDetailVo = repairDto.getFaultDetailVo();
                if (faultDetailVo != null) {
                    String faultDetailStr = new Gson().toJson(faultDetailVo, FaultDetailVo.class);
                    map.put("faultDetailStr", faultDetailStr);
                }
            }
            ARouterUtil.toFragment(map);
        } else if (id == R.id.repair_processing_ll) {
            toProcessingProgress();
        } else if (id == R.id.repair_processing_result_tv) {
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", ArouterPathConstants.Repair.REPAIR_POST_RESULT);
            RepairRecordVo record = getRecentRecord();
            RepairVo repairVo = repairDto.getRepairVo();
            if (repairVo != null) {
                map.put("repairRecordId", repairVo.getLastRecordId() + "");
                map.put("equName", repairVo.getEquName());
                map.put("process", repairVo.getProcessName());
            }
            ARouterUtil.toFragment(map);
        } else if (id == R.id.spare_parts_application_tv) {
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", ARouteConfig.SPARE_PART_MAIN);
            map.put("orderId", repairDto.getRepairVo().getId());
            map.put("type", 2);
            map.put("applicantPurpose", repairDto.getRepairVo().getRepairNumber());
            if (repairDto.getRepairVo().getState() == 4 || repairDto.getRepairVo().getState() == 3 || AppApplication.getInstance().getUserEntity().getId() != repairDto.getRepairVo().getProcessingPersonId()) {
                //4已完成    3关闭
                map.put("executor", false);//否则只能看 记录
            } else {
                map.put("executor", true);//自己是执行人的话 可以备件申请
            }
            ARouterUtil.toFragment(map);
        } else if (id == R.id.common_iv) {
            Map<String, Object> map = new HashMap<>();
            map.put("selectItemId", repairDto.getRepairVo().getId() + "");
            map.put("fragmentUri",
                    LibConfig.AROUTE_FAULT_POST_PROCESSING);
            if (AppApplication.getInstance().getUserEntity().getId()
                    == repairDto.getRepairVo().getProcessingPersonId()) {
                map.put("isCurrentUserExcute", true);
            }
            ARouterUtil.startActivityForResult(map, getActivity(),
                    LibConstants.Fault.PICKPERSON_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LibConstants.Fault.PICKPERSON_REQUEST_CODE && data != null) {
            //分派任务
            int processingPersonId = Integer.parseInt(data.getStringExtra("id"));
            int selectItemId = Integer.parseInt(data.getStringExtra("selectItemId"));
            presenter.taskAssign(selectItemId, processingPersonId);
        }
    }

    private RepairRecordVo getRecentRecord() {
        RepairRecordVo record = null;
        List<RepairRecordVo> records = repairDto.getRepairRecordVos();
        if (records != null && records.size() > 0) {
            record = records.get(0);
        }
        return record;
    }

    /**
     * 流程处理
     */
    private void toProcessingProgress() {
        Map<String, Object> map = new HashMap<>();
        map.put("fragmentUri", ArouterPathConstants.Repair.REPAIR_PROGRESS);
        map.put("repairId", repairDto.getRepairVo().getId() + "");
        ARouterUtil.toFragment(map);
    }

    @Override
    public String getRepairId() {
        return getActivity().
                getIntent().getStringExtra("repairId");
    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public void setDetailData(final RepairDto entity) {
        repairDto = entity;
        if (entity != null) {
            RepairVo repairVo = entity.getRepairVo();
            if (repairVo != null) {

                spareApplicationTv.setVisibility(View.GONE);

//                if (repairVo.getState() == 5) {
//                    //未分配 隐藏 备件申请
//                    spareApplicationTv.setVisibility(View.GONE);
//                } else {
//                    spareApplicationTv.setVisibility(View.VISIBLE);
//                }
                repairEquipmentTv.setText(repairVo.getEquName());
                repairEquipmentCodeTv.setText(repairVo.getEquCode());
                repairProcessPositionTv.setText(repairVo.getProcessName());
                repairReportPersonTv.setText(repairVo.getCreateUserName());
                repairReportPersonOrgTv.setText(repairVo.getOrgName());
                repairReportDateTv.setText(TimeTransformUtil.getShowLocalTime(repairVo.getCreateDate()));
                repairExcutePersonTv
                        .setText("当前处理人: " + repairVo.getProcessingPersonName());
                repairExcuteDateTv.setText(TimeTransformUtil.getShowLocalTime(repairVo.getUpdateDate()));
                repairStatusTv.setText(repairVo.getStateName());
                setStateIv(repairVo.getState());
                if (repairVo.canRepair()) {
                    repairNameTv.setVisibility(View.VISIBLE);
                } else {
                    repairNameTv.setVisibility(View.GONE);
                }
                repairDegreeTv.setText(repairVo.getSeverityTypeName());
                if (repairVo.getState() == null || repairVo.getState() <= 0) {
                    repairNameTv.setText("维修结果填报");
                } else {
                    repairNameTv.setText("查看维修结果");
                }
                if (repairVo.canExcute()) {
                    assignmentIv.setVisibility(View.VISIBLE);
                } else {
                    assignmentIv.setVisibility(View.GONE);
                }
            }
            repairFaultReasonTv.setText(repairVo.getFaultReasonName());
            List<RepairRecordVo> records = entity.getRepairRecordVos();
            if (records != null && records.size() > 0) {
                RepairRecordVo recenteRecord = records.get(0);
                repairExcutePersonTv
                        .setText(getContext().getString(R.string.repair_processing_person_label)
                                + recenteRecord.getProcessingPersonName());
//                repairExcuteDateTv.setText(TimeUtil.getShowTime_YYYYMMDD(repairVo.getUpdateDate()));
                setStateIv(recenteRecord.getRepairResult());

            }
        }
    }

    private void setStateIv(final Integer state) {
        if (RepairConstans.STATE_UNASSIGNED == state) {
            repairStatusTv.setTextColor(getContext().getResources().getColor(R.color.repair_state_unallote));
            repairStateIv.setBackgroundResource(R.drawable.repair_state_unallocate);
        } else if (RepairConstans.STATE_UNPROCESS == state) {
            repairStatusTv.setTextColor(getContext().getResources().getColor(R.color.repair_state_unprocess));
            repairStateIv.setBackgroundResource(R.drawable.repair_state_wait_processing);
        } else if (RepairConstans.STATE_PROCESSING == state) {
            repairStatusTv.setTextColor(getContext().getResources().getColor(R.color.repair_state_processing));
            repairStateIv.setBackgroundResource(R.drawable.repair_state_processing);
        } else if (RepairConstans.STATE_HANGUP == state) {
            repairStatusTv.setTextColor(getContext().getResources().getColor(R.color.repair_state_hangup));
            repairStateIv.setBackgroundResource(R.drawable.repair_state_hang_up);
        } else if (RepairConstans.STATE_CLOSE == state) {
            repairStatusTv.setTextColor(getContext().getResources().getColor(R.color.repair_state_close));
            repairStateIv.setBackgroundResource(R.drawable.repair_state_close);
        } else if (RepairConstans.STATE_COMPLETE == state) {
            repairStatusTv.setTextColor(getContext().getResources().getColor(R.color.repair_state_complete));
            repairStateIv.setBackgroundResource(R.drawable.repair_state_complete);
        }
    }

    /**
     * @param json 工艺列表数据.
     */
    @Override
    public void setProcessingProgressList(final String json) {
        processingProgressArray = json;
    }

    /**
     *
     */
    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(getRepairId())) {
            presenter.getDetailData();
        }

    }
}
