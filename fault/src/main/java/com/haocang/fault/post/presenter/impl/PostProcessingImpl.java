package com.haocang.fault.post.presenter.impl;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.bean.LabelEntity;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.utils.GetListListener;
import com.haocang.base.utils.StringUtils;
import com.haocang.fault.constants.FaultMethod;
import com.haocang.fault.post.iview.PostProcessingView;
import com.haocang.fault.post.model.PostProcessingModel;
import com.haocang.fault.post.model.impl.PostProcessingModelImpl;
import com.haocang.fault.post.presenter.PostProcessingPresenter;

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
 * 创建时间：2018/4/2610:04
 * 修 改 者：
 * 修改时间：
 */
public class PostProcessingImpl
        implements PostProcessingPresenter, GetListListener<LabelEntity> {
    private PostProcessingView postProcessingView;
//    private PostProcessingModel postProcessingModel;

    public PostProcessingImpl(final PostProcessingView postProcessingView) {
        this.postProcessingView = postProcessingView;
//        postProcessingModel = new PostProcessingModelImpl();
    }

    @Override
    public void getAllocatorList() {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(postProcessingView.getQueryName())) {
            map.put("queryName", StringUtils.utfCode(postProcessingView.getQueryName()));
        }
        if (postProcessingView.getOrgId() != null) {
            map.put("orgId", postProcessingView.getOrgId());
        }
        CommonModel<LabelEntity> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<LabelEntity>>() {
        }.getType();
        progressModel
                .setContext(postProcessingView.getContext())
                .setParamMap(map)
                .setHasDialog(false)
                .setListType(type)
                .setUrl(MethodConstants.Uaa.USER_LIST)
                .setListListener(new GetListListener<LabelEntity>() {
                    @Override
                    public void success(final List<LabelEntity> list) {
                        if (postProcessingView.isCurrentUserExcute()) {
                            long currentUserId = AppApplication.getInstance().getUserEntity().getId();
                            if (list != null && list.size() > 0) {
                                for (LabelEntity entity : list) {
                                    if (entity.getValue() == currentUserId) {
                                        list.remove(entity);
                                        break;
                                    }
                                }
                            }
                        }
                        postProcessingView.renderList(list);
                    }
                })
                .getList();
//        postProcessingModel.getAllocatorListData(postProcessingView.getContext(), map, this);
    }

    @Override
    public void getOnLinePersonnel() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageSize", "0");
        CommonModel<LabelEntity> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<LabelEntity>>() {
        }.getType();
        progressModel
                .setContext(postProcessingView.getContext())
                .setParamMap(map)
                .setHasDialog(false)
                .setListType(type)
                .setUrl(FaultMethod.ONLINE_PERSON)
                .setListListener(new GetListListener<LabelEntity>() {
                    @Override
                    public void success(final List<LabelEntity> list) {
                        postProcessingView.getOnLinesPersonList(list);
                    }
                })
                .getList();

//        postProcessingModel.getOnLinePersonnelList(postProcessingView.getContext(), "", new GetListListener<LabelEntity>() {
//            @Override
//            public void success(List<LabelEntity> list) {
//            postProcessingView.getOnLinesPersonList(list);
//            }
//        });
    }

    @Override
    public void success(final List<LabelEntity> list) {
        if (postProcessingView.isCurrentUserExcute()) {
            long currentUserId = AppApplication.getInstance().getUserEntity().getId();
            if (list != null && list.size() > 0) {
                for (LabelEntity entity : list) {
                    if (entity.getValue() == currentUserId) {
                        list.remove(entity);
                        break;
                    }
                }
            }
        }
        postProcessingView.renderList(list);
    }
}
