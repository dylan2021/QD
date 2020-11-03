package com.haocang.waterlink.home.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.base.utils.TimeUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.HomeTaskEntity;

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
 * 创建时间：2018/1/1716:53
 * 修 改 者：
 * 修改时间：
 */
public class HomeTaskView extends LinearLayout implements View.OnClickListener {
    /**
     * 任务状态.
     */
    private TextView taskStatuTv;
    /**
     * 任务名称.
     */
    private TextView taskNameTv;
    /**
     * 任务时间.
     */
    private TextView taskTimeTv;
    /**
     * 上下文参数.
     */
    private Context ctx;
    /**
     * 任务数据.
     */
    private HomeTaskEntity taskEntity;
    /**
     * 回调.
     */
    private OnTaskItemClickListener listener;

    /**
     * 构造方法.
     *
     * @param context  上下文参数
     * @param listener 回调
     */
    public HomeTaskView(final Context context, final OnTaskItemClickListener listener) {
        super(context);
        ctx = context;
        this.listener = listener;
        init(context);
    }

    /**
     * 初始化.
     *
     * @param context 上下文参数
     */
    private void init(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_task_item, null);
        taskNameTv = view.findViewById(R.id.taskName_tv);
        taskStatuTv = view.findViewById(R.id.taskStatu_tv);
        taskTimeTv = view.findViewById(R.id.taskTime_tv);
        view.findViewById(R.id.task_ll).setOnClickListener(this);
        this.addView(view);
    }

    /**
     * 设置数据.
     *
     * @param taskEntity t数据源.
     */
    public void setTaskEntity(final HomeTaskEntity taskEntity) {
        this.taskEntity = taskEntity;
        setTaskName(taskEntity.getName());
        setTaskTime(taskEntity);
        setTaskStatuTv(taskEntity.getProperty());

    }

    /**
     * 设置任务名称.
     *
     * @param taskName 数据.
     */
    private void setTaskName(final String taskName) {
        taskNameTv.setText(taskName);
    }

    /**
     * 设置任务时间.
     *
     * @param taskTime 数据.
     */
    private void setTaskTime(final HomeTaskEntity taskTime) {
        if (taskTime.getType() == 1) {
            String dayTime = null;
            if (!TextUtils.isEmpty(taskTime.getShowTime())) {
                String showLocalTime = TimeTransformUtil.getShowLocalTime(taskTime.getShowTime());
                dayTime = TimeUtil.getDayStr(TimeUtil.getTranData(showLocalTime)) + " ";
            }
            if (!TextUtils.isEmpty(taskTime.getStartTime())) {
                String time = TimeTransformUtil.getShowLocalTime(taskTime.getStartTime());
                dayTime += TimeUtil.getDateStrHHmm(TimeUtil.getNewTranData(time));
            }
            taskTimeTv.setText(dayTime);
        } else {
            taskTimeTv.setText(TimeTransformUtil.getShowLocalTime2(taskTime.getShowTime()));
        }

    }

    /**
     * 任务状态.
     *
     * @param taskStatu 数据.
     */
    @SuppressLint("ResourceType")
    private void setTaskStatuTv(final String taskStatu) {
        GradientDrawable drawable = (GradientDrawable) taskStatuTv.getBackground();
//        if (taskEntity.getId() % 2 == 0) {
        drawable.setStroke(2, getResources().getColor(R.color.color_task_green));
//            taskStatuTv.setText("执行");
        taskStatuTv.setTextColor(getResources().getColor(R.color.color_task_green));
//        } else {
//            drawable.setStroke(1, getResources().getColor(R.color.color_task_red));
        taskStatuTv.setText(R.string.task_excute);
//        taskStatuTv.setText(taskEntity.getStatusName());
//            taskStatuTv.setTextColor(getResources().getColor(R.color.color_task_red));
//    }
    }

    /**
     * 点击事件.
     *
     * @param view .
     */
    @Override
    public void onClick(final View view) {
        listener.onTaskItem(taskEntity);
//        ToastUtil.makeText(ctx, taskEntity.getId());
    }

    /**
     * 为每个item设置点击事件.
     */
    public interface OnTaskItemClickListener {
        /**
         * 实现的方法.
         *
         * @param taskEntity .
         */
        void onTaskItem(HomeTaskEntity taskEntity);
    }

}
