package com.haocang.fault.list.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.GetListListener;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.fault.R;
import com.haocang.fault.constants.FaultMethod;
import com.haocang.fault.list.bean.FaultManagerEntity;
import com.haocang.fault.list.iview.FaultManagerListView;
import com.haocang.fault.list.presenter.FaultManagerListPresenter;


import java.lang.reflect.Type;
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
 * 创建时间：2018/5/314:27
 * 修 改 者：
 * 修改时间：
 */
public class FaultMangerListPresenterImpl implements FaultManagerListPresenter {
    private FaultManagerListView faultManagerListView;

    public FaultMangerListPresenterImpl(FaultManagerListView faultManagerListView) {
        this.faultManagerListView = faultManagerListView;
    }

    @Override
    public void getAllListData() {
        Map<String, Object> map = getListParamMap();
        CommonModel<FaultManagerEntity> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<FaultManagerEntity>>() {
        }.getType();
        progressModel
                .setContext(faultManagerListView.getContexts())
                .setParamMap(map)
                .setHasDialog(false)
                .setListType(type)
                .setUrl(FaultMethod.FAULT_ALL_LIST)
                .setListListener(new GetListListener<FaultManagerEntity>() {
                    @Override
                    public void success(final List<FaultManagerEntity> list) {
                        faultManagerListView.setListAllData(list);
                    }
                })
                .getList();

    }

    @Override
    public void taskAssign(int faultId, int processingPersonId) {
        Map<String, Object> map = new HashMap<>();
        map.put("faultId", faultId);
        map.put("processingPersonId", processingPersonId);
        CommonModel<Integer> progressModel
                = new CommonModelImpl<>();
        progressModel
                .setContext(faultManagerListView.getContexts())
                .setParamMap(map)
                .setUrl(FaultMethod.FAULT_ASSIGN)
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(Integer entity) {
                        faultManagerListView.dstributionSuccess();
                    }

                    @Override
                    public void fail(String err) {
                        Context ctx = faultManagerListView.getContexts();
                        ToastUtil.makeText(ctx, ctx.getString(R.string.task_assign_failed));
                    }
                }).putEntity();

    }

    private Map<String, Object> getListParamMap() {
        int page = faultManagerListView.getCurrentPage();
        String quertName = faultManagerListView.queryName();
        String states = faultManagerListView.getStates();
        String faultTypes = faultManagerListView.getFaultTypes();
        String createUserIds = faultManagerListView.getCreateUserIds();
        String processingPersonIds = faultManagerListView.getProcessingPersonIds();
        String startDate = faultManagerListView.getStartDate();
        String endDate = faultManagerListView.getEndDate();

        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", page);
        map.put("pageSize", 10);
        if (!TextUtils.isEmpty(states)) {
            map.put("states", states);
        }
        if (!TextUtils.isEmpty(faultTypes)) {
            map.put("faultTypes", faultTypes);
        }
        if (!TextUtils.isEmpty(createUserIds) && createUserIds.equals("0")) {
            map.put("createUserIds", createUserIds);
        }
        if (!TextUtils.isEmpty(quertName)) {
            map.put("queryName", StringUtils.utfCode(quertName));
        }
        if (!TextUtils.isEmpty(processingPersonIds) && processingPersonIds.equals("0")) {
            map.put("processingPersonIds", processingPersonIds);
        }
        if (!TextUtils.isEmpty(startDate)) {
            map.put("startDate", TimeTransformUtil.getUploadGMTTime(startDate).trim());
        }
        if (!TextUtils.isEmpty(endDate)) {
            map.put("endDate", TimeTransformUtil.getUploadGMTTime(endDate).trim());
        }
        return map;
    }

}
