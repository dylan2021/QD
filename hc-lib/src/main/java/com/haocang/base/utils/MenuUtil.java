package com.haocang.base.utils;

import android.content.Context;

import com.haocang.base.R;
import com.haocang.base.bean.LeftNavigationEntity;
import com.haocang.base.config.AppApplication;

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
 * 标 题：菜单权限控制
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/6/5 15:57
 * 修 改 者：
 * 修改时间：
 */
public class MenuUtil {
    /**
     * 巡检人员手机端 和任务 左边菜单控制
     *
     * @return
     */
    public static List<LeftNavigationEntity> getTaskBoardData(Context ctx) {
        String[] menuName = ctx.getResources().getStringArray(R.array.pending_task);
        int[] menuId = ctx.getResources().getIntArray(R.array.pending_task_id);
        List<LeftNavigationEntity> list = new ArrayList<>();
        for (int i = 0; i < menuName.length && i < menuId.length; i++) {
            LeftNavigationEntity entity = new LeftNavigationEntity();
            entity.setName(menuName[i]);
            entity.setType(menuId[i]);
            if (i == 0 && AppApplication.getInstance().getUserEntity().isPatrol()) {
                list.add(entity);
            } else if (i == 1 && AppApplication.getInstance().getUserEntity().isFault()) {
                list.add(entity);
            } else if (i == 2 && AppApplication.getInstance().getUserEntity().isRepair()) {
                list.add(entity);
            } else if (i == 3 && AppApplication.getInstance().getUserEntity().isMaintain()) {
                list.add(entity);
            }
        }
        return list;
    }

    /**
     * 关注列表 左边菜单
     *
     * @return
     */
    public static List<LeftNavigationEntity> getFollowData(Context ctx) {
        String[] menuName = ctx.getResources().getStringArray(R.array.follow_menu);
        String[] menuId = ctx.getResources().getStringArray(R.array.follow_menu_id);
        List<LeftNavigationEntity> list = new ArrayList<>();
        for (int i = 0; i < menuName.length && i < menuId.length; i++) {
            LeftNavigationEntity entity = new LeftNavigationEntity();
            entity.setName(menuName[i]);
            entity.setType(Integer.parseInt(menuId[i]));
            if (i == 0 && AppApplication.getInstance().getUserEntity().isEqu()) {
                list.add(entity);
            } else if (i == 1 && AppApplication.getInstance().getUserEntity().isPatrol()) {
                list.add(entity);
            } else if (i == 2 && AppApplication.getInstance().getUserEntity().isFault()) {
                list.add(entity);
            } else if (i == 3 && AppApplication.getInstance().getUserEntity().isRepair()) {
                list.add(entity);
            }

        }
        return list;
    }
}
