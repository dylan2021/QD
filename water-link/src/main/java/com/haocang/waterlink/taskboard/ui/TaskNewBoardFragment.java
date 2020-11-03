package com.haocang.waterlink.taskboard.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.utils.DensityUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.constant.bean.HomeConstants;
import com.haocang.waterlink.follow.adapter.FollowTabAdapter;
import com.haocang.waterlink.home.bean.MenuEntity;
import com.haocang.waterlink.taskboard.adapter.TaskBoardViewPageAdapter;
import com.haocang.waterlink.taskboard.presenter.TaskNewBoardPresenter;
import com.haocang.waterlink.taskboard.presenter.impl.TaskNewBoardPresenterImpl;
import com.haocang.waterlink.taskboard.view.TaskNewBoardView;

import java.util.ArrayList;
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
 * 创 建 者：hy
 * 创建时间：${DATA} 17:17
 * 修 改 者：
 * 修改时间：
 */
@Route(path = HomeConstants.ArouterPath.HOME_TASK)
public class TaskNewBoardFragment extends Fragment implements View.OnClickListener, TaskNewBoardView {
    private ViewPager viewPager;
    private TaskBoardViewPageAdapter pagerAdapter;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    private RecyclerView recyclerview;
    private FollowTabAdapter adapter;


    private TaskFaultFragment faultFragment;
    private TaskPatrolFragment patrolFragment;
    private TaskRepairFragment repairFragment;
    private TaskMaintainFragment maintainFragment;
    private TextView executedTv;//待执行
    private TextView executionTv;//执行中
    private TextView completedTv;//已完成
    private TaskNewBoardPresenter presenter;
    private List<TextView> stateTvList = new ArrayList<>();

    private int index = 0;

    private Map<String, Object> map = new HashMap<>();

    private LinearLayout hideLl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_new_task_board, null);
        initView(view);
        return view;
    }


    private void initView(View view) {
        presenter = new TaskNewBoardPresenterImpl(this);
        executedTv = view.findViewById(R.id.executed_tv);
        executionTv = view.findViewById(R.id.execution_tv);
        completedTv = view.findViewById(R.id.completed_tv);
        executedTv.setOnClickListener(this);
        executionTv.setOnClickListener(this);
        completedTv.setOnClickListener(this);
        stateTvList.add(executedTv);
        stateTvList.add(executionTv);
        stateTvList.add(completedTv);
        hideLl = view.findViewById(R.id.state_ll);
        if (getActivity().getIntent().getBooleanExtra("type", false)) {
            hideLl.setVisibility(View.GONE);
            FrameLayout frameLayout = view.findViewById(R.id.board_fl);
            frameLayout.setBackgroundResource(R.drawable.ic_follow_rectangle_blue_big);
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) frameLayout.getLayoutParams(); //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
            linearParams.height = DensityUtil.dip2px(getActivity(), 75);// 控件的宽强制设成30
            frameLayout.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
        }
        addFragment();
        setStateColor(0);
        recyclerview = view.findViewById(R.id.recyclerview);
        adapter = new FollowTabAdapter(R.layout.adapter_follow_tab, getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(adapter);
        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);
        pagerAdapter = new TaskBoardViewPageAdapter(getActivity().getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        presenter.getTabList();
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setStateName(position);
                index = position;
                if (map.get(index + "") != null) {
                    setStateColor((Integer) map.get(index + ""));
                } else {
                    setStateColor(0);
                }
                adapter.setSelect(position);
                recyclerview.scrollToPosition(position);
                if (position > 2) {
                    recyclerview.scrollToPosition(adapter.getItemCount() - 1);
                } else {
                    recyclerview.scrollToPosition(0);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i, Object o) {
                setStateName(i);
                viewPager.setCurrentItem(i);
                adapter.setSelect(i);
//                if (i > 2) {
//                    recyclerview.scrollToPosition(adapter.getItemCount() - 1);
//                } else {
//                    recyclerview.scrollToPosition(0);
//                }
            }

            @Override
            public void onLongClick(View view, int i, Object o) {

            }
        });
        view.findViewById(R.id.back_iv).setOnClickListener(this);
    }

    private void addFragment() {
        faultFragment = new TaskFaultFragment();

        patrolFragment = new TaskPatrolFragment();

        repairFragment = new TaskRepairFragment();

        maintainFragment = new TaskMaintainFragment();

        fragmentList.add(patrolFragment);
        fragmentList.add(faultFragment);
        fragmentList.add(repairFragment);
//        fragmentList.add(maintainFragment);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back_iv) {
            getActivity().finish();
        } else if (v.getId() == R.id.executed_tv) {
            //待执行
            setStateColor(0);
            setState(1);
        } else if (v.getId() == R.id.execution_tv) {
            //执行中
            setStateColor(1);
            setState(2);
        } else if (v.getId() == R.id.completed_tv) {
            //已完成
            setStateColor(2);
            setState(3);
        }
    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public void setTabList(List<MenuEntity> titleList) {
        adapter.addAll(titleList);
        adapter.notifyDataSetChanged();
    }


    private void setStateColor(int state) {
        for (int i = 0; i < stateTvList.size(); i++) {
            if (i == state) {
                stateTvList.get(i).setTextColor(Color.parseColor("#ff0cabdf"));
                stateTvList.get(i).setBackgroundResource(R.drawable.task_radio_shape);
            } else {
                stateTvList.get(i).setTextColor(Color.parseColor("#ffffffff"));
                stateTvList.get(i).setBackgroundResource(0);
            }
        }
        map.put("" + index, state);
    }

    private void setState(int state) {
        if (index == 0) {
            patrolFragment.setTaskState(state);
        } else if (index == 1) {
            faultFragment.setTaskState(state);
        } else if (index == 2) {
            repairFragment.setTaskState(state);
        } else if (index == 3) {
            maintainFragment.setTaskState(state);
        }
    }

    private void setStateName(int state) {
        if (state == 0 || state == 3) {
            executedTv.setText("待执行");
            executionTv.setText("执行中");
            completedTv.setText("已结束");
        } else if (state == 1 || state == 2) {
            executedTv.setText("待处理");
            executionTv.setText("处理中");
            completedTv.setText("已结束");
        }
    }
}
