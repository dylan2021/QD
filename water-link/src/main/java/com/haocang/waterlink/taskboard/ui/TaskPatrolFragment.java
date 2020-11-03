package com.haocang.waterlink.taskboard.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.ToastUtil;
import com.haocang.patrol.patrolinhouse.ui.PatrolInHouseFragment;
import com.haocang.patrol.patroloutside.ui.PatrolOutsideFragment;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.TaskEntity;
import com.haocang.waterlink.self.iview.TaskBooardView;
import com.haocang.waterlink.self.presenter.TaskBoardPresenter;
import com.haocang.waterlink.self.presenter.impl.TaskBoardPresenterImpl;
import com.haocang.waterlink.taskboard.adapter.TaskPatrolAdapter;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

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
 * 创建时间：${DATA} 11:05
 * 修 改 者：
 * 修改时间：
 */
public class TaskPatrolFragment extends Fragment implements BaseRefreshListener, TaskBooardView {
    private int state = 1;//任务状态  1待执行，  2执行中  ，3已完成

    public void setTaskState(int state) {
        this.state = state;
        if (getUserVisibleHint()) {
            refresh();
        }
    }

    private RecyclerView recyclerView;
    private PullToRefreshLayout pullToRefreshLayout;
    private int page = 1;
    private boolean isRefresh = true;//区分是刷新还是加载更多
    private FrameLayout frameLayout;
    private TaskPatrolAdapter adapter;
    private TaskBoardPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_task_board_all, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        frameLayout = view.findViewById(R.id.no_data_fl);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TaskPatrolAdapter(R.layout.adapter_task_patrol);
        recyclerView.setAdapter(adapter);
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        pullToRefreshLayout.setRefreshListener(this);
        presenter = new TaskBoardPresenterImpl(this);
        refresh();
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i, Object o) {
                TaskEntity taskEntity = (TaskEntity) o;
                isOutside(taskEntity);
            }

            @Override
            public void onLongClick(View view, int i, Object o) {

            }
        });
    }

    @Override
    public void refresh() {
        page = 1;
        isRefresh = true;
        presenter.getTaskListData();
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        page++;
        presenter.getTaskListData();
    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public int getStatus() {
        return state;
    }

    @Override
    public int getCurrentPage() {
        return page;
    }

    @Override
    public void setTaskList(List<TaskEntity> taskList) {
        if (isRefresh) {
            adapter.clear();
            pullToRefreshLayout.finishRefresh();
        } else {
            pullToRefreshLayout.finishLoadMore();
        }
        if (taskList != null && taskList.size() > 0) {
            adapter.addAll(taskList);
        } else if (!isRefresh) {
            ToastUtil.makeText(getActivity(), getString(R.string.equiment_all_data));
        }
        adapter.notifyDataSetChanged();
        if (adapter.getItemCount() == 0) {
            recyclerView.setVisibility(View.GONE);
            frameLayout.setVisibility(View.VISIBLE);
        } else {
            frameLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void isOutside(final TaskEntity taskEntity) {
        Intent intent = new Intent(getActivity(), CommonActivity.class);
        intent.putExtra("taskId", taskEntity.getId());
        intent.putExtra("taskName", taskEntity.getName());
        if ("Outside".equals(taskEntity.getPatrolType())) {
            intent.putExtra("fragmentName", PatrolOutsideFragment.class.getName());
            intent.putExtra("mapFlag", true);
        } else {
            intent.putExtra("fragmentName", PatrolInHouseFragment.class.getName());
        }
        startActivity(intent);
    }
}
