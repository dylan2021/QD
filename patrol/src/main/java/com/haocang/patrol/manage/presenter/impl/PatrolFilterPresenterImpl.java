package com.haocang.patrol.manage.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.haocang.base.config.AppApplication;
import com.haocang.base.picker.PickerView;
import com.haocang.base.utils.TimeUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.bean.filter.FilterEntity;
import com.haocang.patrol.manage.iview.PatrolFilterView;
import com.haocang.patrol.manage.presenter.PatrolFilterPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题：
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/4/8下午3:30
 * 修  改  者：
 * 修改时间：
 */
public class PatrolFilterPresenterImpl implements PatrolFilterPresenter {
    public static final int REQUEST = 9257;
    private PatrolFilterView mView;
    private Date selectDate;

    public void setView(final PatrolFilterView view) {
        mView = view;
    }

    /**
     * 初始化时间
     *
     * @param curDateStr 前一次设置的时间，当之前没设置过，则为当前时间
     * @param statusKeys 选中的状态，多个状态以逗号分隔
     */
    public void initData(final String curDateStr, final String statusKeys) {
        if (!TextUtils.isEmpty(curDateStr)) {
            selectDate = TimeUtil.getDateByStr(curDateStr);
        } else {
            selectDate = new Date();
        }
        mView.setShowTime(TimeUtil.getDateStryyMM(selectDate));

        initLabels(statusKeys);
        initTaskLabels(statusKeys);
        if (mView.getPickerview() != null) {
            setPickerViewDate();
            mView.getPickerview().setOnPickerViewChangeListener(new PickerView.OnPickerViewListener() {
                @Override
                public void onChange(PickerView pickerView) {
                    String time = pickerView.getYear() + "-" + pickerView.getMonth() + "-" + pickerView.getDay();
                    try {
                        selectDate = TimeUtil.ConverToDate(time);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void setPickerViewDate() {
        String data = TimeUtil.getDateStryyyyMMdd(selectDate);
        String[] dataSr = data.split("-");
        mView.getPickerview().setYear(Integer.parseInt(dataSr[0]));
        mView.getPickerview().setMonth(Integer.parseInt(dataSr[1]));
        mView.getPickerview().setDay(Integer.parseInt(dataSr[2]));
    }

    /**
     * @param statusKeys 选中的状态，以逗号分隔.
     */
    private void initLabels(final String statusKeys) {
        String[] patrolStatusLabelArray = AppApplication.getContext().getResources().getStringArray(R.array.patrol_status_labels);
        String[] patrolStatusKeysArray = AppApplication.getContext().getResources().getStringArray(R.array.patrol_status_keys);
        List<FilterEntity> list = new ArrayList<>();
        FilterEntity entity = null;
        for (int i = 0; i < patrolStatusLabelArray.length && i < patrolStatusKeysArray.length; i++) {
            entity = new FilterEntity();
            entity.setLabelKey(patrolStatusKeysArray[i]);
            entity.setLabelName(patrolStatusLabelArray[i]);
            if (!TextUtils.isEmpty(statusKeys) && statusKeys.contains(entity.getLabelKey())) {
                entity.setSelect(true);
            }
            list.add(entity);
        }
        mView.renderList(list);
    }

    private void initTaskLabels(String statusKeys) {
        String[] patrolStatusLabelArray = AppApplication.getContext().getResources().getStringArray(R.array.patrol_task_labels);
        String[] patrolStatusKeysArray = AppApplication.getContext().getResources().getStringArray(R.array.patrol_task_keys);
        List<FilterEntity> list = new ArrayList<>();
        FilterEntity entity = null;
        for (int i = 0; i < patrolStatusLabelArray.length && i < patrolStatusKeysArray.length; i++) {
            entity = new FilterEntity();
            entity.setLabelKey(patrolStatusKeysArray[i]);
            entity.setLabelName(patrolStatusLabelArray[i]);
            if (!TextUtils.isEmpty(statusKeys) && statusKeys.contains(entity.getLabelKey())) {
                entity.setSelect(true);
            }
            list.add(entity);
        }
        mView.renderTaskList(list);
    }

    /**
     * @TODO，待优化
     */
    @Override
    public void getPatrolStateList() {

        List<FilterEntity> list = new ArrayList<>();
        FilterEntity entity = new FilterEntity();
        entity.setLabelName("未分配");
        list.add(entity);
        entity = new FilterEntity();
        entity.setLabelName("待执行");
        list.add(entity);
        entity = new FilterEntity();
        entity.setLabelName("执行中");
        list.add(entity);
        entity = new FilterEntity();
        entity.setLabelName("已完成");
        list.add(entity);
        entity = new FilterEntity();
        entity.setLabelName("异常");
        list.add(entity);
        mView.renderList(list);
    }

    @Override
    public void reset() {
        selectDate = new Date();
        mView.setShowTime(TimeUtil.getDateStryyMM(selectDate));
        if (mView.getPickerview() != null) {
            setPickerViewDate();
        }
    }

    /**
     * 点击完成，把信息回传给列表
     */
    @Override
    public void complete() {
        Intent intent = new Intent();
        intent.putExtra("endTime", TimeUtil.getDateTimeStr(selectDate));
        intent.putExtra("excuteStates", mView.getSelectStatusKeys());
        intent.putExtra("curDate", selectDate);
        intent.putExtra("allExecutor", mView.getPersonId());
        intent.putExtra("curDateStr", TimeUtil.getDateStr(selectDate));
        ((Activity) getContext()).setResult(REQUEST, intent);
        ((Activity) getContext()).finish();
    }

    /**
     * @return
     */
    public Context getContext() {
        return mView.getContext();
    }

    /**
     * 上一个周期
     */
    @Override
    public void pre() {
        selectDate = TimeUtil.getLastMonth(selectDate);
        mView.setShowTime(TimeUtil.getDateStryyMM(getDate(selectDate)));
    }

    /**
     * 下一个周期
     */
    @Override
    public void next() {
        selectDate = TimeUtil.getNextMonth(selectDate);
        mView.setShowTime(TimeUtil.getDateStryyMM(selectDate));
    }

    /**
     * @return
     */
    private String getStartTime() {
        return TimeUtil.getLocalTimeStr(TimeUtil.getWeekFirstDay(selectDate));
    }

    /**
     * @return
     */
    private String getEndTime() {
        return TimeUtil.getLocalTimeStr(TimeUtil.getWeekLastDay(selectDate));
    }

    /**
     * @return
     */
    private String getShowTime() {
        Date startTime = TimeUtil.getWeekFirstDay(selectDate);
        Date endTime = TimeUtil.getWeekLastDay(selectDate);
        return TimeUtil.converStrMMDD(startTime) + " - " + TimeUtil.converStrMMDD(endTime);
    }

    private Date getDate(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
    }

}
