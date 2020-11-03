package com.haocang.waterlink.home.presenter.impl;


import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.utils.GetListListener;
import com.haocang.waterlink.home.bean.HomeTaskEntity;
import com.haocang.waterlink.home.iview.HomeTaskView;
import com.haocang.waterlink.home.presenter.HomeTaskPresenter;

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
 * 创建时间：2018/5/514:22
 * 修 改 者：
 * 修改时间：
 */
public class HomeTaskPresenterImpl implements HomeTaskPresenter {
    private HomeTaskView homeTaskView;

    public HomeTaskPresenterImpl(final HomeTaskView homeTaskView) {
        this.homeTaskView = homeTaskView;
    }

    @Override
    public void getTaskList() {
        Map<String, Object> map = new HashMap<>();
        map.put("size", "10");
        CommonModel<HomeTaskEntity> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<HomeTaskEntity>>() {
        }.getType();
        progressModel
                .setContext(homeTaskView.getContexts())
                .setParamMap(map)
                .setListType(type)
                .setHasDialog(true)
                .setUrl(MethodConstants.Portal.BASE_TASKS_TODO)
                .setListListener(new GetListListener<HomeTaskEntity>() {
                    @Override
                    public void success(final List<HomeTaskEntity> list) {
                        if (list != null && list.size() > 0) {
                            homeTaskView.setTaskData(list);
                        }
                    }
                })
                .getList();
    }

}
