package com.haocang.curve.more.presenter.impl;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.bean.ScreeEntity;
import com.haocang.base.utils.GetListListener;
import com.haocang.curve.constants.CurveMethod;
import com.haocang.curve.more.iview.PointScreePopupView;
import com.haocang.curve.more.presenter.PointScreePopupPresenter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
 * 创建时间：2018/6/4 11:23
 * 修 改 者：
 * 修改时间：
 */
public class PointScreePopupPresenterImpl implements PointScreePopupPresenter {
    private PointScreePopupView pointScreePopupView;
//    private PointScreePopupModel pointScreePopupModel;

    public PointScreePopupPresenterImpl(PointScreePopupView pointScreePopupView) {
        this.pointScreePopupView = pointScreePopupView;
//        pointScreePopupModel = new PointScreePopupModelImpl();
        initData();
    }

    private void initData() {

        String processList = pointScreePopupView.getProcessList();
        String siteId = pointScreePopupView.getSiteId();

    }

    @Override
    public void getDataTypeList() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("siteId", pointView.getSiteId());
//        map.put("currentPage", pointView.getCurrentPage());
//        if (!TextUtils.isEmpty(pointView.getQueryName())) {
//            map.put("queryName", StringUtils.utfCode(pointView.getQueryName()));
//        }
        CommonModel<ScreeEntity> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<ScreeEntity>>() {
        }.getType();
        progressModel
                .setContext(pointScreePopupView.getContexts())
//                .setParamMap(map)
                .setHasDialog(false)
                .setListType(type)
                .setUrl(CurveMethod.CURVE_POINT_DATACATEGORYS)
                .setListListener(new GetListListener<ScreeEntity>() {
                    @Override
                    public void success(final List<ScreeEntity> list) {
                        List<String> mList = new ArrayList<>();
                        if (!TextUtils.isEmpty(pointScreePopupView.getCategoryId())) {
                            String[] categoryId = pointScreePopupView.getCategoryId().split(",");
                            for (int i = 0; i < categoryId.length; i++) {
                                mList.add(categoryId[i]);
                            }
                            for (ScreeEntity entity : list) {
                                if (mList.contains(entity.getId() + "")) {
                                    entity.setSelector(true);
                                } else {
                                    entity.setSelector(false);
                                }
                            }
                        }
                        pointScreePopupView.setDataType(list);
                    }
                })
                .getList();

//        pointScreePopupModel.getDataTypeModel(pointScreePopupView.getContexts(), listener);

    }

    GetListListener<ScreeEntity> listener = new GetListListener<ScreeEntity>() {
        @Override
        public void success(List<ScreeEntity> list) {
            List<String> mList = new ArrayList<>();
            if (!TextUtils.isEmpty(pointScreePopupView.getCategoryId())) {
                String[] categoryId = pointScreePopupView.getCategoryId().split(",");
                for (int i = 0; i < categoryId.length; i++) {
                    mList.add(categoryId[i]);
                }
                for (ScreeEntity entity : list) {
                    if (mList.contains(entity.getId() + "")) {
                        entity.setSelector(true);
                    } else {
                        entity.setSelector(false);
                    }
                }
            }
            pointScreePopupView.setDataType(list);
        }
    };
}
