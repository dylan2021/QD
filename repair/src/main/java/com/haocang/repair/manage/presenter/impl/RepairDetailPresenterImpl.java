package com.haocang.repair.manage.presenter.impl;


import android.content.Context;

import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.ToastUtil;
import com.haocang.repair.R;
import com.haocang.repair.constants.RepairMethod;
import com.haocang.repair.manage.bean.RepairDto;
import com.haocang.repair.manage.iview.RepairDetailView;
import com.haocang.repair.manage.presenter.RepairDetailPresenter;
import com.haocang.repair.progress.bean.ProcessingProgressVo;

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
 * 创建时间：2018/5/317:24
 * 修 改 者：
 * 修改时间：
 */
public class RepairDetailPresenterImpl implements RepairDetailPresenter {
    private RepairDetailView faultDetailView;

    public RepairDetailPresenterImpl(final RepairDetailView view) {
        this.faultDetailView = view;
    }

    @Override
    public void getDetailData() {
        CommonModel<RepairDto> progressModel = new CommonModelImpl<>();
        progressModel
                .setContext(faultDetailView.getContexts())
//                .setParamMap(map)
                .setEntityType(RepairDto.class)
                .setUrl(RepairMethod.REPAIR_DETAIL + "/" + faultDetailView.getRepairId())
                .setEntityListener(new GetEntityListener<RepairDto>() {
                    @Override
                    public void success(final RepairDto entity) {
                        faultDetailView.setDetailData(entity);
                    }

                    @Override
                    public void fail(final String err) {

                    }
                }).getEntityNew();
    }


    /**
     * @param selectItemId       要分配的维修单ID
     * @param processingPersonId 处理人ID
     */
    @Override
    public void taskAssign(final int selectItemId,
                           final int processingPersonId) {
        Map<String, Object> map = new HashMap<>();
        map.put("repairId", selectItemId);
        map.put("processingPersonId", processingPersonId);
        CommonModel<Integer> commonModel = new CommonModelImpl<>();
        commonModel
                .setContext(faultDetailView.getContexts())
                .setUrl(RepairMethod.ASSIGN_TASK)
                .setParamMap(map)
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(final Integer entity) {
                        ToastUtil.makeText(faultDetailView.getContexts(),
                                faultDetailView.getContexts().getString(R.string.repair_assign_succuss));
                        getDetailData();
//                        repairListView.updateChangedItem();
                    }

                    @Override
                    public void fail(final String err) {
                        ToastUtil.makeText(faultDetailView.getContexts(),
                                faultDetailView.getContexts().getString(R.string.repair_assign_fail));
                    }
                }).putEntity();
    }

}
