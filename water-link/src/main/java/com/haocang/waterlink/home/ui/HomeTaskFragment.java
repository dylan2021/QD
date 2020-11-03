package com.haocang.waterlink.home.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haocang.base.ui.CommonActivity;
import com.haocang.fault.list.ui.FaultDetailsFragment;
import com.haocang.patrol.patrolinhouse.ui.PatrolInHouseFragment;
import com.haocang.patrol.patroloutside.ui.PatrolOutsideFragment;
import com.haocang.repair.manage.ui.RepairDetailFragment;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.HomeTaskEntity;
import com.haocang.waterlink.home.iview.HomeTaskView;
import com.haocang.waterlink.home.iview.TodoTaskView;
import com.haocang.waterlink.home.presenter.HomeTaskPresenter;
import com.haocang.waterlink.home.presenter.impl.HomeTaskPresenterImpl;
import com.haocang.waterlink.home.widgets.RollingView;
import com.haocang.waterlink.taskboard.ui.TaskNewBoardFragment;
import com.haotan.maintain.worker.ui.MaintainDetailListFragment;

import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题： 首页待办 任务
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/1/1614:23
 * 修 改 者：
 * 修改时间：
 */
public class HomeTaskFragment extends Fragment
        implements RollingView.onItemClickListener, TodoTaskView, View.OnClickListener, HomeTaskView {
    private int PATROL = 1;//巡检任务
    private int FAULT = 2;//缺陷任務
    private int REPAIR = 3;//维修任务
    private int MAINTAIN = 4;//养护任务
    /**
     * 翻页控件.
     */
    private RollingView taskRv;
    /**
     * 设置任务每页的数量.
     */
    private static final int PAGESIZE = 3;
    /**
     * 翻页时每页停留的时间.
     */
    private static final int DELAYEDTIME = 4500;

    private HomeTaskPresenter presenter;

    private TextView nodataTv;

    /**
     * 初始化.
     *
     * @param inflater           .  .
     * @param container          .
     * @param savedInstanceState .
     * @return .
     */
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_home_task, null);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        nodataTv = view.findViewById(R.id.nodata_tv);
        taskRv = view.findViewById(R.id.task_rv);
        view.findViewById(R.id.jump_ll).setOnClickListener(this);
        presenter = new HomeTaskPresenterImpl(this);
        taskRv.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getTaskList();
    }


    /**
     * 每个item的点击事件.
     *
     * @param taskEntity 获取到点击item的数据源.
     */
    @Override
    public void onItemClick(final HomeTaskEntity taskEntity) {
        toFragment(taskEntity);
    }

    /**
     * 设置任务总数.
     *
     * @param taskCount 任务总数.
     */
    @Override
    public void setTaskCount(final int taskCount) {

    }

    @Override
    public void setTaskList(final List<HomeTaskEntity> list) {

    }

    @Override
    public void getClickTask() {

    }

    /**
     * 点击事件.
     *
     * @param view .
     */
    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.jump_ll) {
            Intent intent = new Intent(getActivity(), CommonActivity.class);
            intent.putExtra("fragmentName", TaskNewBoardFragment.class.getName());
            intent.putExtra("type", true);
            startActivity(intent);
        }
    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public void setTaskData(final List<HomeTaskEntity> list) {
        if (list != null && list.size() > 0) {
            taskRv.setPageSize(PAGESIZE);
            taskRv.setDelayedDuration(DELAYEDTIME);
            taskRv.setRollingData(list);
            taskRv.setVisibility(View.VISIBLE);
            nodataTv.setVisibility(View.GONE);
        } else {
            nodataTv.setVisibility(View.VISIBLE);
            taskRv.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        taskRv.pause();
    }

    private void toFragment(final HomeTaskEntity taskEntity) {
        Intent intent = new Intent(getActivity(), CommonActivity.class);
        if (taskEntity.getType() == PATROL) {
            isOutside(intent, taskEntity);
        } else if (taskEntity.getType() == FAULT) {
            intent.putExtra("faultId", taskEntity.getId() + "");
            intent.putExtra("fragmentName", FaultDetailsFragment.class.getName());

        } else if (taskEntity.getType() == REPAIR) {
            intent.putExtra("fragmentName", RepairDetailFragment.class.getName());
            intent.putExtra("repairId", taskEntity.getId() + "");

        } else if (taskEntity.getType() == MAINTAIN) {
            intent.putExtra("fragmentName", MaintainDetailListFragment.class.getName());
            intent.putExtra("taskId", taskEntity.getId());
            intent.putExtra("taskName", taskEntity.getName());
        }
        startActivity(intent);
    }

    private void isOutside(final Intent intent, final HomeTaskEntity taskEntity) {
        intent.putExtra("taskId", taskEntity.getId());
        intent.putExtra("taskName", taskEntity.getName());
        if ("Outside".equals(taskEntity.getPatrolType())) {
            intent.putExtra("fragmentName", PatrolOutsideFragment.class.getName());
            intent.putExtra("mapFlag", true);
        } else {
            intent.putExtra("fragmentName", PatrolInHouseFragment.class.getName());
        }
    }
}
