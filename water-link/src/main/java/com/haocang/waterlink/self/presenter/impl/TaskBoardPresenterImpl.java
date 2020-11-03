package com.haocang.waterlink.self.presenter.impl;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.utils.GetListListener;
import com.haocang.waterlink.home.bean.TaskEntity;
import com.haocang.waterlink.self.iview.TaskBooardView;
import com.haocang.waterlink.self.presenter.TaskBoardPresenter;

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
 * 创建时间：2018/5/14 16:20
 * 修 改 者：
 * 修改时间：
 */
public class TaskBoardPresenterImpl implements TaskBoardPresenter {
    private TaskBooardView taskBooardView;

    public TaskBoardPresenterImpl(final TaskBooardView taskBooardView) {
        this.taskBooardView = taskBooardView;
    }

    @Override
    public void getTaskListData() {
        Map<String, Object> map = new HashMap<>();
        map.put("type", taskBooardView.getType());
        map.put("status", taskBooardView.getStatus());
        map.put("currentPage", taskBooardView.getCurrentPage());
        CommonModel<TaskEntity> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<TaskEntity>>() {
        }.getType();
        progressModel
                .setContext(taskBooardView.getContexts())
                .setParamMap(map)
                .setHasDialog(false)
                .setListType(type)
                .setUrl(MethodConstants.Portal.BASE_TASK_LIST)
                .setListListener(new GetListListener<TaskEntity>() {
                    @Override
                    public void success(final List<TaskEntity> list) {
                        taskBooardView.setTaskList(list);
                    }
                })
                .getList();

    }

}
