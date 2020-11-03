package com.haocang.waterlink.home.presenter.impl;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.GetListListener;
import com.haocang.waterlink.constant.bean.HomeConstants;
import com.haocang.waterlink.home.bean.HomeDataEntity;
import com.haocang.waterlink.home.iview.HomeDataView;
import com.haocang.waterlink.home.presenter.HomeDataPresenter;

import org.json.JSONException;
import org.json.JSONObject;

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
 * 标        题：
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/8/3上午9:23
 * 修  改  者：
 * 修改时间：
 */
public class HomeDataPresenterImpl implements HomeDataPresenter {

    private HomeDataView mHomeDataView;

    public HomeDataPresenterImpl(HomeDataView homeDataView) {
        mHomeDataView = homeDataView;
    }

    @Override
    public void getData() {
        Map<String, Object> map = new HashMap<>();
        CommonModel<HomeDataEntity> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<HomeDataEntity>>() {
        }.getType();
        progressModel
                .setContext(mHomeDataView.getContext())
                .setParamMap(map)
                .setHasDialog(false)
                .setListType(type)
                .setUrl(HomeConstants.MethodPath.HOME_DATA)
                .setListListener(new GetListListener<HomeDataEntity>() {
                    @Override
                    public void success(final List<HomeDataEntity> list) {
                        mHomeDataView.renderData(list);
                    }
                })
                .getList();
    }

    @Override
    public void getSubscription() {
        CommonModel<HomeDataEntity> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<HomeDataEntity>>() {
        }.getType();
        progressModel
                .setContext(mHomeDataView.getContext())
                .setHasDialog(false)
                .setListType(type)
                .setUrl(HomeConstants.MethodPath.HOME_SUBSCRIPTION + "?ids=" + mHomeDataView.getIds())
                .setEntityListener(new GetEntityListener<String>() {
                    @Override
                    public void success(String result) {
                        if (!TextUtils.isEmpty(result)) {
                            try {
                                JSONObject object = new JSONObject(result);
                                String topic = object.optString("topic");
                                mHomeDataView.setTopic(topic);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void fail(String s) {

                    }
                })
                .getEntityNew();
    }

}
