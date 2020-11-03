package com.haocang.curve.collection.presenter.impl;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.utils.GetListListener;
import com.haocang.base.utils.StringUtils;
import com.haocang.curve.collection.bean.AppChartDTO;
import com.haocang.curve.collection.bean.MultiCurve;
import com.haocang.curve.collection.iview.MultiCollectionView;
import com.haocang.curve.collection.presenter.MultiCollectionPresenter;
import com.haocang.curve.constants.CurveMethod;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题：组合曲线P层
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/6/4上午10:09
 * 修  改  者：
 * 修改时间：
 */
public class MultiCollectionPresenterImpl implements MultiCollectionPresenter {

    /**
     *
     */
    private MultiCollectionView multiCollectionView;

    /**
     * @param view view
     */
    public MultiCollectionPresenterImpl(final MultiCollectionView view) {
        multiCollectionView = view;
    }

    /**
     *
     */
    @Override
    public void getList() {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(multiCollectionView.getQueryName())) {
            map.put("queryName",
                    StringUtils.utfCode(multiCollectionView.getQueryName()));
        }
        CommonModel<AppChartDTO> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<AppChartDTO>>() {
        }.getType();
        progressModel
                .setContext(multiCollectionView.getContext())
                .setParamMap(map)
                .setListType(type)
                .setUrl(CurveMethod.COMBINES_COLLECTION)
                .setListListener(new GetListListener<AppChartDTO>() {
                    @Override
                    public void success(final List<AppChartDTO> list) {
                        multiCollectionView.renderList(list);
                    }
                })
                .getList();
    }
}
