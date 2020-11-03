package com.haocang.repair.manage.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.repair.R;
import com.haocang.repair.manage.bean.FaultDetailVo;

import java.util.HashMap;
import java.util.Map;

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
 * 创建时间：2018/5/8下午3:16
 * 修  改  者：
 * 修改时间：
 */
@Route(path = ArouterPathConstants.Repair.REPAIR_FAULT_DETAIL)
public class FaultDetailFragment extends Fragment implements View.OnClickListener {

    private TextView repairFaultTypeTv;
    private TextView repairFaultCodeTv;
    private TextView repairFaultDegreeTv;
    private TextView repairEquipementTv;
    private TextView repairFaultPatrolTv;
    private TextView repairReportPersonOrgTv;
    private TextView repairReportPersonTv;
    private TextView repairReportDateTv;

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
                .inflate(R.layout.repair_fault_detail_fragment, null);
        initView(view);
        return view;
    }


    /**
     * 初始化界面.
     *
     * @param view 根View
     */
    private void initView(final View view) {
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.repair_fault_detail));
        repairFaultTypeTv = view.findViewById(R.id.repair_fault_type_tv);
        repairFaultCodeTv = view.findViewById(R.id.repair_fault_code_tv);
        repairFaultDegreeTv = view.findViewById(R.id.repair_fault_degree_tv);
        repairEquipementTv = view.findViewById(R.id.repair_equipement_tv);
        repairFaultPatrolTv = view.findViewById(R.id.repair_fault_patrol_tv);
        repairReportPersonOrgTv = view.findViewById(R.id.repair_report_person_org_tv);
        repairReportPersonTv = view.findViewById(R.id.repair_report_person_tv);
        repairReportDateTv = view.findViewById(R.id.repair_report_date_tv);
        view.findViewById(R.id.repair_look_scene_record_ll).setOnClickListener(this);
        initData();
    }

    /**
     *
     */
    private void initData() {
        FaultDetailVo vo = getFaultDetail();
        if (vo != null) {
            repairFaultTypeTv.setText(vo.getFaultTypeName());
            repairFaultCodeTv.setText(vo.getFaultNumber());
            repairFaultDegreeTv.setText(vo.getSeverityTypeName());
            repairEquipementTv.setText(vo.getEquName());
            repairReportPersonOrgTv.setText(vo.getOrgName());
            repairReportPersonTv.setText(vo.getCreateUser());
            repairReportDateTv.setText(TimeTransformUtil.getShowLocalTime(vo.getCreateDate()));
            repairFaultPatrolTv.setText(vo.getPatrolName());
        }
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        if (id == R.id.repair_look_scene_record_ll) {
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", ArouterPathConstants.Repair.REPAIR_FAULT_SENCES);
            FaultDetailVo vo = getFaultDetail();
            if (vo != null) {
                if (vo.getRemark() != null) {
                    map.put("remark", vo.getRemark());
                }
                if (vo.getImgUrl() != null) {
                    map.put("imgUrl", vo.getImgUrl());
                }
            }
            ARouterUtil.toFragment(map);
        }
    }

    public FaultDetailVo getFaultDetail() {
        String faultDetailStr = getActivity().getIntent().getStringExtra("faultDetailStr");
        FaultDetailVo vo = null;
        if (!TextUtils.isEmpty(faultDetailStr)) {
            vo = new Gson().fromJson(faultDetailStr, FaultDetailVo.class);
        }
        return vo;
    }
}
