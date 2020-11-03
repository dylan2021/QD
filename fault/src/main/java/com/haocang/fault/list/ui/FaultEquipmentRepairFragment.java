package com.haocang.fault.list.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.AnalysisQRCodeUtil;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.ScanCodeUtils;
import com.haocang.base.utils.SpeechService;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.zxing.app.CaptureActivity;
import com.haocang.fault.R;
import com.haocang.fault.list.bean.FaultConstants;
import com.haocang.fault.list.iview.FaultRepairView;
import com.haocang.fault.list.presenter.FaultRepairPresenter;
import com.haocang.fault.list.presenter.impl.FaultRepairPresenterImpl;
import com.haocang.fault.post.ui.utils.PostEquipmentFragment;

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
 * 标 题： 设备报修
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/2713:04
 * 修 改 者：
 * 修改时间：
 */
@Route(path = LibConfig.AROUTE_FAULT_EQUIPMENT_REPAIR)
public class FaultEquipmentRepairFragment extends Fragment implements View.OnClickListener, PermissionsProcessingUtil.OnPermissionsCallback, SpeechService.OnSpeechResult, TextWatcher, FaultRepairView {

    private TextView titleNameTv;

    private TextView faultEquipmentNameTv;//设备名称
    private TextView faultEquipmentCodeTv;//设备编号
    private TextView faultOrgNameTv;//所属组织
    private TextView faultProcessTv;//工艺位置
    private TextView faultRepairTypeTv;//维修类型
    private TextView faultSeverityTv;//紧急程度
    private TextView faultReasonTv;//故障原因
    private TextView processingPersonTv;//处理人
    private TextView charLengthTv;//字符最大数值
    private EditText faultEt;

    private Map<String, Object> map = new HashMap<>();
    private FaultRepairPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_fault_equipment_repair, null);
        initView(view);
        return view;
    }

    /**
     * @param view 根View.
     */
    private void initView(final View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.falut_processing_equipment_repair));
        view.findViewById(R.id.repair_ll).setOnClickListener(this);
        view.findViewById(R.id.fault_equipment_ll).setOnClickListener(this);
        view.findViewById(R.id.fault_severity_ll).setOnClickListener(this);
        view.findViewById(R.id.fault_reason_ll).setOnClickListener(this);
        view.findViewById(R.id.audio_ll).setOnClickListener(this);
        view.findViewById(R.id.scan_code_iv).setOnClickListener(this);
        view.findViewById(R.id.common_complete_tv).setOnClickListener(this);
        view.findViewById(R.id.processing_person_ll).setOnClickListener(this);
        faultEquipmentNameTv = view.findViewById(R.id.fault_equipment_name_tv);
        faultEquipmentCodeTv = view.findViewById(R.id.fault_equipment_code_tv);
        faultOrgNameTv = view.findViewById(R.id.fault_org_tv);
        faultProcessTv = view.findViewById(R.id.process_tv);
        faultRepairTypeTv = view.findViewById(R.id.fault_repair_tv);
        faultSeverityTv = view.findViewById(R.id.fault_severity_tv);
        faultReasonTv = view.findViewById(R.id.fault_reason_tv);
        charLengthTv = view.findViewById(R.id.char_lenth_tv);
        processingPersonTv = view.findViewById(R.id.processing_person_tv);
        faultEt = view.findViewById(R.id.fault_scene_et);
        faultEt.addTextChangedListener(this);
        getEquipment(getActivity().getIntent());
        presenter = new FaultRepairPresenterImpl(this);
        setFaultRepairType(FaultConstants.REPAIR_TYPE_INTERNAL, getString(R.string.repair_type_internal));
    }

    /**
     * @param v
     */
    @Override
    public void onClick(final View v) {
        int id = v.getId();
        if (id == R.id.common_tv) {
            ARouterUtil.toFragment(LibConfig.AROUTE_FAULT_SUBMIT_SUCCESS);
        } else if (id == R.id.repair_ll) {
//            repairShowDialog();
        } else if (id == R.id.fault_equipment_ll) {
            onClickEquipment();
        } else if (id == R.id.fault_severity_ll) {
            onClickSeverity();
        } else if (id == R.id.fault_reason_ll) {
            onClickReason();
        } else if (id == R.id.audio_ll) {
            PermissionsProcessingUtil.setPermissions(this, LibConfig.AUDIO, this);
        } else if (id == R.id.common_complete_tv) {
            map.put("remark", faultEt.getText().toString());
            map.put("faultId", getFaultId());
            presenter.submitRepair();
        } else if (id == R.id.scan_code_iv) {
            PermissionsProcessingUtil.setPermissions(this, LibConfig.CAMERA, this);
        } else if (id == R.id.processing_person_ll) {
            Map<String, Object> map = new HashMap<>();
            map.put("authorized", false);
            map.put("fragmentUri", LibConfig.AROUTE_FAULT_POST_PROCESSING);
            ARouterUtil.startActivityForResult(map, getActivity(),
                    LibConstants.Fault.PICKPERSON_REQUEST_CODE);
        }
    }

    private void repairShowDialog() {
        final String[] stateLabel = getActivity().getResources().getStringArray(R.array.fault_repair_type);
        final String[] stateLabelId = getActivity().getResources().getStringArray(R.array.fault_repair_type_ids);
        new AlertView(null, null, "取消", null,
                stateLabel, getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(final Object o, final int position) {
                //如果是-1说明点击的取消,点击取消时，不做任何事
                if (position == -1) {
                    return;
                }
                setFaultRepairType(position + 1, stateLabel[position]);

            }
        }).show();
    }

    private void setFaultRepairType(final int repairType, final String repairTypeName) {
        faultRepairTypeTv.setText(repairTypeName);
        map.put("repairType", repairType);
    }


    /**
     * 点击相关设备
     */
    private void onClickEquipment() {
        Map<String, Object> map = new HashMap<>();
        map.put("fragmentUri", LibConfig.AROUTE_FAULT_EQUIPMENT);
        ARouterUtil.startActivityForResult(map, getActivity(), PostEquipmentFragment.REQUESTCODE);
    }

    /**
     * 点击紧急程度
     */
    private void onClickSeverity() {
        final String[] stateLabel = getActivity().getResources().getStringArray(R.array.fault_repair_urgency);
        final String[] stateLabelId = getActivity().getResources().getStringArray(R.array.fault_repair_urgency_id);
        new AlertView(null, null, "取消", null,
                stateLabel, getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(final Object o, final int position) {
                //如果是-1说明点击的取消,点击取消时，不做任何事
                if (position == -1) {
                    return;
                }
                faultSeverityTv.setText(stateLabel[position]);
                map.put("severityType", position + 1);
            }
        }).show();
    }


    /**
     * 点击故障原因
     */
    private void onClickReason() {
        final String[] stateLabel = getActivity().getResources().getStringArray(R.array.fault_repair_reason);
        final String[] stateLabelId = getActivity().getResources().getStringArray(R.array.fault_repair_reason_id);
        new AlertView(null, null, "取消", null,
                stateLabel, getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(final Object o, final int position) {
                //如果是-1说明点击的取消,点击取消时，不做任何事
                if (position == -1) {
                    return;
                }
                faultReasonTv.setText(stateLabel[position]);
                map.put("faultReason", position + 1);
            }
        }).show();
    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (PostEquipmentFragment.REQUESTCODE == requestCode && data != null) {
            //相关设备
            getEquipment(data);
        } else if (requestCode == LibConfig.SCAN_CODE && data != null) {
            String result = data.getStringExtra(CaptureActivity.SCAN_RESULT);
            int id = AnalysisQRCodeUtil.getEquipmentId(result);
            if (id > 0) {
                presenter.getEquipmentData(id + "");
            } else {
                ToastUtil.makeText(getActivity(), "请扫描正确的二维码");
            }
        } else if (requestCode == LibConstants.Fault.PICKPERSON_REQUEST_CODE && data != null) {
            //分派任务
            map.put("processingPersonId", Integer.parseInt(data.getStringExtra("id")));
            String name = data.getStringExtra("name");
            processingPersonTv.setText(name);
        }
    }

    /**
     * 获取相关设备
     */
    private void getEquipment(final Intent data) {
        faultEquipmentNameTv.setText(getName(data));
        faultEquipmentCodeTv.setText(data.getStringExtra("code"));
        faultOrgNameTv.setText(data.getStringExtra("orgName"));
        faultProcessTv.setText(data.getStringExtra("processName"));
        String id = data.getStringExtra("id");
        String processId = data.getStringExtra("processId");
        if (!TextUtils.isEmpty(id)) {
            map.put("equId", Integer.parseInt(id));
        }
        if (!TextUtils.isEmpty(processId)) {
            map.put("processId", Integer.parseInt(processId));
        }
    }

    /**
     * 获取名称
     *
     * @param data
     * @return
     */
    private String getName(Intent data) {
        if (!TextUtils.isEmpty(data.getStringExtra("name"))) {
            return data.getStringExtra("name");
        } else {
            return "";
        }
    }

    @Override
    public void callBack(boolean flag, String permission) {
        if (flag && LibConfig.AUDIO.equals(permission)) {
            SpeechService.btnVoice(getActivity(), this);
        } else if (!flag && LibConfig.AUDIO.equals(permission)) {
            ToastUtil.makeText(getActivity(), getResources().getString(R.string.permissions_audio));
        } else if (flag && LibConfig.CAMERA.equals(permission)) {
            ScanCodeUtils.openScanCode(getActivity());
        } else if (!flag && LibConfig.CAMERA.equals(permission)) {
            ToastUtil.makeText(getActivity(), getString(R.string.permissions_photo));
        }
    }

    @Override
    public void onSpeechResult(String result) {
        String oldResult = faultEt.getText().toString();
        faultEt.setText(oldResult + result);
        faultEt.setSelection(faultEt.getText().length());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        charLengthTv.setText(faultEt.getText().length() + "/100");
    }

    private String getFaultId() {
        return getActivity().getIntent().getStringExtra("faultId");
    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public Map<String, Object> getMap() {
        return map;
    }

    @Override
    public void isEmpty(String error) {
        ToastUtil.makeText(getActivity(), error);
    }

    @Override
    public void submitSuccess() {
        ARouterUtil.toFragment(LibConfig.AROUTE_FAULT_SUBMIT_SUCCESS);
        getActivity().finish();
    }

    @Override
    public void setEquipmentName(String name) {
        faultEquipmentNameTv.setText(name);
    }

    @Override
    public void setEquipmentId(int id) {
        map.put("equId", id);
    }

    @Override
    public void setEquipmentCode(String code) {
        faultEquipmentCodeTv.setText(code);
    }

    @Override
    public void setOrgName(String orgName) {
        faultOrgNameTv.setText(orgName);
    }

    @Override
    public void setOrgId(int id) {

    }

    @Override
    public void setProcessName(String name) {
        faultProcessTv.setText(name);
    }

    @Override
    public void setProcessId(int id) {
        map.put("processId", id);
    }


}
