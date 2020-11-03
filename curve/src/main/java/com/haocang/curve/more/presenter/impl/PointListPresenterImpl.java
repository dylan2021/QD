package com.haocang.curve.more.presenter.impl;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.utils.GetListListener;
import com.haocang.curve.constants.CurveMethod;
import com.haocang.curve.more.bean.PointEntity;
import com.haocang.curve.more.iview.PointListView;
import com.haocang.curve.more.presenter.PointListPresenter;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
 * 创建时间：2018/5/31 14:30
 * 修 改 者：
 * 修改时间：
 */
public class PointListPresenterImpl implements PointListPresenter {

    private PointListView pointListView;
    private List<PointEntity> selectList;
    private List<PointEntity> mPointList;
    private String historyPointId = "";

    public PointListPresenterImpl(PointListView pointListView) {
        this.pointListView = pointListView;
        selectList = pointListView.getSelectList();
    }

    @Override
    public void getPointListData() {
        String currentPage = pointListView.getCurrentPage();
        String pageSize = pointListView.getPageSize();
        String queryName = pointListView.getQueryName();
        Context ctx = pointListView.getContexts();
        Map<String, Object> map = new HashMap<>();
        map.put("pageSize", pageSize);
        map.put("currentPage", currentPage);
        map.put("siteId", pointListView.getSiteId());
        map.put("categoryId", pointListView.getCategoryId());
        if (!TextUtils.isEmpty(queryName)) {
            map.put("queryName", queryName);
        }
//        Map<String, Object> map = getParamMap();
        CommonModel<PointEntity> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<PointEntity>>() {
        }.getType();
        progressModel
                .setContext(pointListView.getContexts())
                .setParamMap(map)
                .setHasDialog(false)
                .setListType(type)
                .setUrl(CurveMethod.CURVE_POINT_LIST)
                .setListListener(new GetListListener<PointEntity>() {
                    @Override
                    public void success(final List<PointEntity> list) {
                        for (PointEntity e : list) {
                            e.setMpointId(e.getId() + "");
                        }
                        mPointList = new ArrayList<>();
                        addHistoryItem();
                        addPointList(list);
                        pointListView.setPointList(mPointList);
                    }
                })
                .getList();
//        pointListModel.getPointListData(ctx, map, new GetListListener<PointEntity>() {
//            @Override
//            public void success(List<PointEntity> list) {
//                mPointList = new ArrayList<>();
//                addHistoryItem();
//                addPointList(list);
//                pointListView.setPointList(mPointList);
//            }
//        });
    }

    /**
     * 历史曲线.
     */
    private void addHistoryItem() {
        List<PointEntity> selectList = pointListView.getSelectList();
        if (selectList != null && selectList.size() > 0 && "1".equals(pointListView.getCurrentPage())) {
            PointEntity entity = new PointEntity();
            entity.setTitleName("已选曲线");
            entity.setType(1);
            mPointList.add(0, entity);
            for (PointEntity pEntity : selectList) {
                pEntity.setSelect(true);
                mPointList.add(pEntity);
                historyPointId = historyPointId + pEntity.getId() + ",";
            }
        }
    }

    /**
     * 曲线数据列表，再已选里面相同的id删除.
     *
     * @param list 后台获取列表
     */
    private void addPointList(final List<PointEntity> list) {
        List<PointEntity> mList = new ArrayList<>();
//        if (list != null && list.size() > 0
//                && "1".equals(pointListView.getCurrentPage())) {
//            PointEntity entity = new PointEntity();
//            entity.setTitleName("单数据曲线");
//            entity.setType(1);
//            if (entity.getMpointId() == null) {
//                entity.setMpointId(entity.getId() + "");
//            }
//            mList.add(entity);
//        }
        if (historyPointId != null) {
            for (PointEntity pointEntity : list) {
                if (!historyPointId.contains(pointEntity.getId() + "")) {
                    mList.add(pointEntity);
                }
            }
        }
        mPointList.addAll(mList);
    }
}
