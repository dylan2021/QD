package com.haocang.waterlink.home.presenter.impl;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.utils.GetListListener;
import com.haocang.base.utils.StringUtils;
import com.haocang.datamonitor.monitor.bean.MonitorEntity;
import com.haocang.waterlink.home.bean.EquimentKpiEntity;
import com.haocang.waterlink.home.iview.HomeKpiView;
import com.haocang.waterlink.home.presenter.HomeKpiPresenter;


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
 * 公司地址：中国上海市徐汇区龙兰路277号一号楼9楼
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/5/22 11:07
 * 修 改 者：
 * 修改时间：
 */
public class HomeKpiPresenterImpl implements HomeKpiPresenter {
    /**
     *
     */
    private HomeKpiView homeKpiView;

    public HomeKpiPresenterImpl(final HomeKpiView homeKpiView) {
        this.homeKpiView = homeKpiView;
    }

    //@todo
    @Override
    public void getKpiListData() {
//        Context ctx = homeKpiView.getContexts();
        String date = homeKpiView.getTime();
        Map<String, Object> map = new HashMap<>();
//        map.put("siteId", AppApplication.getInstance().getUserEntity().getOrgId());
        map.put("date", homeKpiView.getTime());
        map.put("siteId", homeKpiView.getSiteId());
        CommonModel<EquimentKpiEntity> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<EquimentKpiEntity>>() {
        }.getType();
        progressModel
                .setContext(homeKpiView.getContexts())
                .setParamMap(map)
                .setHasDialog(false)
                .setListType(type)
                .setUrl(MethodConstants.Base.BASE_KPI)
                .setListListener(new GetListListener<EquimentKpiEntity>() {
                    @Override
                    public void success(final List<EquimentKpiEntity> list) {
                        homeKpiView.setKpiList(list);
                    }
                })
                .getList();
    }

//    @Override
//    public void submit(final Map<String, Object> map) {
//        Context ctx = homeKpiView.getContexts();
//
//        homeKpiModel.submit(ctx, map, entityListener);
//    }

//    @Override
//    public void submit(final JSONArray array) {
//        Context ctx = homeKpiView.getContexts();
//        homeKpiModel.submit(ctx, array, entityListener);
//    }

    @Override
    public void getProcessesList() {
        Map<String, Object> map = new HashMap<>();
        map.put("exType", 2);
        CommonModel<MonitorEntity> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<MonitorEntity>>() {
        }.getType();
        progressModel
                .setContext(homeKpiView.getContexts())
                .setParamMap(map)
                .setListType(type)
                .setHasDialog(true)
                .setUrl(MethodConstants.Uaa.BASE_PROCESSE)
                .setListListener(new GetListListener<MonitorEntity>() {
                    @Override
                    public void success(final List<MonitorEntity> list) {
                        if (list != null && list.size() > 0) {
                            homeKpiView.setProcessesList(list);
                        }
                    }
                })
                .getList();
    }


//    private GetListListener<EquimentKpiEntity> listener = new GetListListener<EquimentKpiEntity>() {
//        @Override
//        public void success(final List<EquimentKpiEntity> list) {
//            homeKpiView.setKpiList(list);
//        }
//    };
//    /**
//     *
//     */
//    private GetEntityListener entityListener = new GetEntityListener() {
//        @Override
//        public void success(final Object entity) {
//            homeKpiView.submitSuccess();
//        }
//
//        @Override
//        public void fail(final String err) {
//
//        }
//    };
}
