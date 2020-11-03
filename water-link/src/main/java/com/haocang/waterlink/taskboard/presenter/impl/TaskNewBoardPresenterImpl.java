package com.haocang.waterlink.taskboard.presenter.impl;

import android.content.Context;

import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.MenuEntity;
import com.haocang.waterlink.taskboard.presenter.TaskNewBoardPresenter;
import com.haocang.waterlink.taskboard.view.TaskNewBoardView;

import java.util.ArrayList;
import java.util.List;

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
 * 创 建 者：hy
 * 创建时间：${DATA} 14:05
 * 修 改 者：
 * 修改时间：
 */
public class TaskNewBoardPresenterImpl implements TaskNewBoardPresenter {
    private TaskNewBoardView boardView;

    public TaskNewBoardPresenterImpl(TaskNewBoardView boardView) {
        this.boardView = boardView;
    }


    @Override
    public void getTabList() {
        Context context = boardView.getContexts();
        List<MenuEntity> titleList = new ArrayList<>();
        if (context != null) {
            String[] tabArray = context.getResources().getStringArray(R.array.task_tab_list);
            for (int i = 0; i < tabArray.length; i++) {
                MenuEntity entity = new MenuEntity();
                entity.setName(tabArray[i]);
                if (i == 0) {
                    entity.setReson(i + 1);
                }
                titleList.add(entity);
            }
        }
        boardView.setTabList(titleList);

    }
}
