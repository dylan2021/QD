package com.haocang.curve.collection.presenter.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.utils.GetListListener;
import com.haocang.base.utils.StringUtils;
import com.haocang.curve.R;
import com.haocang.curve.collection.bean.PointList;
import com.haocang.curve.collection.bean.SignleCurve;
import com.haocang.curve.collection.iview.SignleCollectionView;
import com.haocang.curve.collection.presenter.SignleCollectionPresenter;
import com.haocang.curve.constants.CurveMethod;
import com.haocang.curve.main.bean.CurveConstans;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

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
 * 创建时间：2018/6/4上午10:07
 * 修  改  者：
 * 修改时间：
 */
public class SignleCollectionPresenterImpl
        implements SignleCollectionPresenter {
    /**
     *
     */
    private SignleCollectionView signleCollectionView;

    /**
     * @param view 和主界面交互接口.
     */
    public SignleCollectionPresenterImpl(final SignleCollectionView view) {
        signleCollectionView = view;
    }

    /**
     *
     */
    @Override
    public void getList() {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(signleCollectionView.getQueryName())) {
            map.put("queryName",
                    StringUtils.utfCode(signleCollectionView.getQueryName()));
        }
        CommonModel<SignleCurve> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<SignleCurve>>() {
        }.getType();
        progressModel
                .setContext(signleCollectionView.getContext())
                .setParamMap(map)
                .setListType(type)
                .setUrl(CurveMethod.SIGNLE_COLLECTION)
                .setListListener(new GetListListener<SignleCurve>() {
                    @Override
                    public void success(final List<SignleCurve> list) {
                        setList(list);
                    }
                })
                .getList();
    }

    /**
     * 查看曲线.
     */
    @Override
    public void toCurveMain() {

    }

    @Override
    public void addSelectedPoints() {
        List<SignleCurve> newList = new ArrayList<>();
        newList.add(getTitle(getContext().getString(R.string.curve_selected)));
        List<SignleCurve> selectedList = signleCollectionView.getSelectedList();
        if (selectedList != null) {
            newList.addAll(selectedList);
        } else {
            newList.add(getNone(null));
        }
        signleCollectionView.renderList(newList);
    }

    @Override
    public void getPoint(Map<String, Object> map) {

//        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(signleCollectionView.getQueryName())) {
            map.put("queryName",
                    StringUtils.utfCode(signleCollectionView.getQueryName()));
        }else {
            map.remove("queryName");
        }
        CommonModel<PointList> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<PointList>>() {
        }.getType();
        progressModel
                .setContext(signleCollectionView.getContext())
                .setParamMap(map)
                .setListType(type)
                .setUrl(CurveMethod.POINT)
                .setListListener(new GetListListener<PointList>() {
                    @Override
                    public void success(final List<PointList> list) {
                        Log.e("PointList",list.toString());
//                        setList(list);
                        setPointList(list);
                    }
                }).setErrorInterface(new CommonModelImpl.ErrorInterface() {
            @Override
            public void error(Response response) {

                Log.e("PointList",response.toString());
            }
        }).getList();
    }

    private void setPointList(List<PointList> list){
        List<PointList> newList = new ArrayList<>();
        List<PointList> selectedList = signleCollectionView.getSelectedPointList();
        if (selectedList != null) {
            newList.add(getPointTitle(getContext().getString(R.string.curve_selected)));
            newList.addAll(selectedList);
        }

        List<String> selectPointIdList = signleCollectionView.getSelectedIdList();
//        newList.add(getPointTitle(getContext().getString(R.string.curve_signle)));
        if (list != null) {
            for (PointList c : list) {
                if (!selectPointIdList.contains(c.getId())) {
                    c.setType(CurveConstans.TYPE_DATA);
                    newList.add(c);
                }
            }
        }
        signleCollectionView.renderPointList(newList);
    }

    /**
     * @param list 列表.
     */
    private void setList(final List<SignleCurve> list) {
        List<SignleCurve> newList = new ArrayList<>();
        List<SignleCurve> selectedList = signleCollectionView.getSelectedList();
        if (selectedList != null) {
            newList.add(getTitle(getContext().getString(R.string.curve_selected)));
            newList.addAll(selectedList);
        }
//        else {
//            newList.add(getNone(null));
//        }
        List<String> selectPointIdList = signleCollectionView.getSelectedIdList();
        newList.add(getTitle(getContext().getString(R.string.curve_signle)));
        if (list != null) {
            for (SignleCurve c : list) {
                if (!selectPointIdList.contains(c.getId())) {
                    c.setType(CurveConstans.TYPE_DATA);
                    newList.add(c);
                }
            }
        }
        signleCollectionView.renderList(newList);
    }

    /**
     * @param signleCurve 参数.
     * @return
     */
    private SignleCurve getNone(final SignleCurve signleCurve) {
        SignleCurve entity = new SignleCurve();
        entity.setType(CurveConstans.TYPE_NONE);
        return entity;
    }

    /**
     * @param title 名称.
     * @return title实体类
     */
    private SignleCurve getTitle(final String title) {
        SignleCurve entity = new SignleCurve();
        entity.setType(CurveConstans.TYPE_TITLE);
        entity.setTitleName(title);
        return entity;
    }


    private PointList getPointTitle(final String title) {
        PointList entity = new PointList();
        entity.setType(CurveConstans.TYPE_TITLE);
        entity.setMpointName(title);
        return entity;
    }

    /**
     * 获取上下文参数.
     *
     * @return
     */
    public Context getContext() {
        return signleCollectionView.getContext();
    }
}
