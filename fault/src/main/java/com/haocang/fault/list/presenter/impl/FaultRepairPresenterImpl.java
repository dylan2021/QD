package com.haocang.fault.list.presenter.impl;

import android.content.Context;

import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.bean.EquimentEntity;
import com.haocang.base.utils.EquipmentModelUtil;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.ToastUtil;
import com.haocang.fault.R;
import com.haocang.fault.constants.FaultMethod;
import com.haocang.fault.list.iview.FaultRepairView;
import com.haocang.fault.list.presenter.FaultRepairPresenter;

import java.util.Map;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：@todo
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/5/89:47
 * 修 改 者：
 * 修改时间：
 */
public class FaultRepairPresenterImpl implements FaultRepairPresenter {
    private FaultRepairView faultRepairView;

    public FaultRepairPresenterImpl(final FaultRepairView faultRepairView) {
        this.faultRepairView = faultRepairView;
    }

    @Override
    public void submitRepair() {
        if (isEmpty()) {
            CommonModel<Integer> progressModel
                    = new CommonModelImpl<>();
            progressModel
                    .setContext(faultRepairView.getContexts())
                    .setParamMap(faultRepairView.getMap())
                    .setUrl(FaultMethod.REPAIR_ADD)
                    .setEntityListener(new GetEntityListener<Integer>() {
                        @Override
                        public void success(Integer id) {
                            faultRepairView.submitSuccess();
                        }

                        @Override
                        public void fail(String err) {
                            ToastUtil.makeText(faultRepairView.getContexts(), "提交失败，请联系管理员");
                        }
                    }).postEntity();
        }
    }

    @Override
    public void getEquipmentData(final String id) {
        Context ctx = faultRepairView.getContexts();
        EquipmentModelUtil.getDetailsData(ctx, id, equipmentListener);
    }

    /**
     *
     */
    private GetEntityListener<EquimentEntity> equipmentListener = new GetEntityListener<EquimentEntity>() {
        @Override
        public void success(final EquimentEntity entity) {
            faultRepairView.setEquipmentCode(entity.getCode());
            faultRepairView.setEquipmentId(entity.getId());
            faultRepairView.setEquipmentName(entity.getName());
            faultRepairView.setOrgId(entity.getOrgId());
            faultRepairView.setOrgName(entity.getOrgName());
            faultRepairView.setProcessId(entity.getProcessId());
            faultRepairView.setProcessName(entity.getProcessName());
        }

        @Override
        public void fail(final String err) {
            ToastUtil.makeText(faultRepairView.getContexts(), err);
        }
    };

    private boolean isEmpty() {
        Context ctx = faultRepairView.getContexts();
        Map<String, Object> map = faultRepairView.getMap();
        int equId = map.get("equId") != null ? (int) map.get("equId") : -1;
        int repairType = map.get("repairType") != null ? (int) map.get("repairType") : -1;//维修类型
        int faultReason = map.get("faultReason") != null ? (int) map.get("faultReason") : -1;
        int severityType = map.get("severityType") != null ? (int) map.get("severityType") : -1;//紧急程度
        if (equId <= 0) {
            faultRepairView.isEmpty(ctx.getResources().getString(R.string.fault_seletor_equ));
            return false;
        } else if (repairType <= 0) {
            faultRepairView.isEmpty(ctx.getResources().getString(R.string.fault_seletor_repair));
            return false;
        } else if (faultReason <= 0) {
            faultRepairView.isEmpty(ctx.getResources().getString(R.string.fault_seletor_reason));
            return false;
        } else if (severityType <= 0) {
            faultRepairView.isEmpty(ctx.getResources().getString(R.string.fault_seletor_servity));
            return false;
        } else {
            return true;
        }
    }
}
