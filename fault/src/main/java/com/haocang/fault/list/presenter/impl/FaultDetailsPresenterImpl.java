package com.haocang.fault.list.presenter.impl;


import android.content.Context;

import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.ToastUtil;
import com.haocang.fault.R;
import com.haocang.fault.constants.FaultMethod;
import com.haocang.fault.list.bean.FaultDetail;
import com.haocang.fault.list.bean.FaultManagerEntity;
import com.haocang.fault.list.iview.FaultDetailsView;
import com.haocang.fault.list.model.FaultDetailsModel;
import com.haocang.fault.list.model.impl.FaultDetailsModelImpl;
import com.haocang.fault.list.presenter.FaultDetailsPresenter;

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
public class FaultDetailsPresenterImpl implements FaultDetailsPresenter {
    private FaultDetailsView faultDetailsView;
    private FaultDetailsModel faultDetailsModel;

    public FaultDetailsPresenterImpl(FaultDetailsView faultDetailsView) {
        this.faultDetailsView = faultDetailsView;
        faultDetailsModel = new FaultDetailsModelImpl();
    }

    @Override
    public void taskAssign(int faultId, int processingPersonId) {
        Map<String, Object> map = new HashMap<>();
        map.put("faultId", faultId);
        map.put("processingPersonId", processingPersonId);
        CommonModel<Integer> progressModel
                = new CommonModelImpl<>();
        progressModel
                .setContext(faultDetailsView.getContexts())
                .setParamMap(map)
                .setUrl(FaultMethod.FAULT_ASSIGN)
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(Integer entity) {
                        ToastUtil.makeText(faultDetailsView.getContexts(),"分派成功");
                        getDetailsData();
                    }

                    @Override
                    public void fail(String err) {
                        Context ctx = faultDetailsView.getContexts();
                        ToastUtil.makeText(ctx, ctx.getString(R.string.task_assign_failed));
                    }
                }).putEntity();

    }

//    @Override
//    public void getDetailsData() {
//        CommonModel<FaultDetail> progressModel
//                = new CommonModelImpl<>();
//        progressModel
//                .setContext(faultDetailsView.getContexts())
//                .setEntityType(FaultDetail.class)
//                .setUrl(FaultMethod.FAULT_DETAILS + "/" + faultDetailsView.getFaultId())
//                .setEntityListener(new GetEntityListener<FaultDetail>() {
//                    @Override
//                    public void success(FaultDetail faultDetail) {
//                        if(faultDetail != null){
//                            faultDetailsView.setDetailsData(faultDetail.getFaultDto());
//                            faultDetailsView.setProcessingProgressList(faultDetail.getFaultRemoveRecordDtos());
//                        }
//                    }
//
//                    @Override
//                    public void fail(String err) {
//
//                    }
//
//                })
//                .getEntityNew();
//
//    }

    @Override
    public void getDetailsData() {
        String faultId = faultDetailsView.getFaultId();
        Context ctx = faultDetailsView.getContexts();
        faultDetailsModel.getDetailsDataModel(ctx, faultId, listener, listListener);
    }

    GetEntityListener<FaultManagerEntity> listener = new GetEntityListener<FaultManagerEntity>() {
        @Override
        public void success(FaultManagerEntity entity) {
            faultDetailsView.setDetailsData(entity);
        }

        @Override
        public void fail(String err) {

        }
    };

    GetEntityListener<String> listListener = new GetEntityListener<String>() {
        @Override
        public void success(String json) {
            faultDetailsView.setProcessingProgressList(json);
        }

        @Override
        public void fail(String err) {

        }
    };

}
