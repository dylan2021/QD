package com.haocang.waterlink.self.presenter.impl;

import android.content.Context;

import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.waterlink.self.bean.FollowEntity;
import com.haocang.waterlink.self.iview.FollowView;
import com.haocang.waterlink.self.presenter.FollowPresenter;

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
 * 创建时间：2018/5/516:32
 * 修 改 者：
 * 修改时间：
 */
public class FollowPresenterImpl implements FollowPresenter {

    private FollowView followView;


    public FollowPresenterImpl(final FollowView followView) {
        this.followView = followView;
    }

    @Override
    public void getFollowList() {
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", followView.getCurrentPage());
        map.put("type", followView.getType());
        CommonModel<FollowEntity> progressModel = new CommonModelImpl<>();
        progressModel
                .setContext(followView.getContexts())
                .setParamMap(map)
                .setEntityType(FollowEntity.class)
                .setUrl(MethodConstants.Portal.BASE_CONCERN_LIST)
                .setEntityListener(new GetEntityListener<FollowEntity>() {
                    @Override
                    public void success(final FollowEntity entity) {
                        if (entity != null) {
                            followView.setFollowListData(entity.getItems());
                            if (entity.getDeleteItemCount() > 0) {
                                followView.showDeleteItemDialog(entity.getDeleteItemCount());
                            }

                        }
                    }

                    @Override
                    public void fail(final String err) {

                    }
                }).getEntityNew();

    }

//    private GetListListener<FolloContentEntity> listener = new GetListListener<FolloContentEntity>() {
//        @Override
//        public void success(List<FolloContentEntity> list) {
//            followView.setFoloowListData(list);
//        }
//    };
//
//    private GetEntityListener<Integer> entityListener = new GetEntityListener<Integer>() {
//        @Override
//        public void success(Integer integer) {
//            if (integer > 0) {
//                followView.showDeleteItemDialog(integer);
//            }
//        }
//
//        @Override
//        public void fail(String s) {
//
//        }
//    };
}
