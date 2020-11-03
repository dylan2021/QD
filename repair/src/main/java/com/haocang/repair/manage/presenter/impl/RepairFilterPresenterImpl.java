package com.haocang.repair.manage.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.haocang.base.bean.ScreeEntity;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.repair.R;
import com.haocang.repair.manage.iview.RepairFilterView;
import com.haocang.repair.manage.presenter.RepairFilterPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
 * 创 建 者：he
 * 创建时间：2018/5/39:54
 * 修 改 者：
 * 修改时间：
 */
public class RepairFilterPresenterImpl implements RepairFilterPresenter {


    /**
     * 返回码.
     */
    public static final int REQUEST = 4756;

    /**
     *
     */
    private RepairFilterView repairFilterView;
    /**
     *
     */
    private Date selectDate;

    /**
     * @param filterView 和UI进行交互的接口.
     */
    public RepairFilterPresenterImpl(final RepairFilterView filterView) {
        this.repairFilterView = filterView;
    }

    /**
     * 初始化时间.
     *
     * @param curDateStr 前一次设置的时间，当之前没设置过，则为当前时间
     */
    public void initData(final String curDateStr) {
        if (!TextUtils.isEmpty(curDateStr)) {
            selectDate = TimeUtil.getDateByStr(curDateStr);
        } else {
            selectDate = new Date();
        }
        repairFilterView.setShowTime(TimeUtil.getDateStryyMM(selectDate));
        repairFilterView.setSelectDateStr(TimeUtil.getDateTimeStr(selectDate));
        repairFilterView.setFilterState(setStateData());
        repairFilterView.setFilterCreatePerson(getCreatePersonData());
        repairFilterView.setFilterExcutePerson(getExcutePersonData());
    }

    /**
     * 上一个周期.
     */
    @Override
    public void pre() {
        selectDate = TimeUtil.getLastMonth(selectDate);
        repairFilterView.setShowTime(getShowTime());
        repairFilterView.setSelectDateStr(TimeUtil.getDateTimeStr(selectDate));
//        Date startTime = TimeUtil.getWeekFirstDay(selectDate);
//        Date endTime = TimeUtil.getWeekLastDay(selectDate);
        repairFilterView.setShowTime(TimeUtil.getDateStryyMM(selectDate));
        selectDate = getDate(selectDate);
    }

    /**
     * 下一个周期.
     */
    @Override
    public void next() {
//        Date curDate = TimeUtil.getWeekLastDay(new Date());//本周最后一天
//        Date seleDate = TimeUtil.getWeekLastDay(selectDate);//
//        if (seleDate.after(curDate)) {
//            ToastUtil.makeText(repairFilterView.getContexts(), "不能选择未来时间");
//            selectDate = new Date();
//        }
        selectDate = TimeUtil.getNextMonth(selectDate);
        repairFilterView.setShowTime(TimeUtil.getDateStryyMM(selectDate));
        repairFilterView.setSelectDateStr(TimeUtil.getDateTimeStr(selectDate));
        selectDate = getDate(selectDate);
    }


    /**
     * @return 获取显示时间.
     */
    private String getShowTime() {
        Date startTime = TimeUtil.getWeekFirstDay(selectDate);
        Date endTime = TimeUtil.getWeekLastDay(selectDate);
        return TimeUtil.converStrMMDD(startTime)
                + " - "
                + TimeUtil.converStrMMDD(endTime);
    }

    /**
     * @return 设置状态数据.
     */
    private List<ScreeEntity> setStateData() {
        String stateIds = repairFilterView.getStateIds();
        Context ctx = repairFilterView.getContexts();
        List<ScreeEntity> list = new ArrayList<>();
        int[] idSr = ctx.getResources()
                .getIntArray(R.array.repair_filter_state_id);
        String[] nameSr = ctx.getResources()
                .getStringArray(R.array.repair_filter_state);
        for (int i = 0; i < idSr.length && i < nameSr.length; i++) {
            ScreeEntity entity = new ScreeEntity();
            entity.setName(nameSr[i]);
            entity.setId(idSr[i]);
            if (!TextUtils.isEmpty(stateIds)
                    && stateIds.contains(entity.getId() + "")) {
                entity.setSelector(true);
            } else {
                entity.setSelector(false);
            }
            list.add(entity);
        }
        return list;
    }

    /**
     * 初始化创建人列表.
     *
     * @return
     */
    private List<ScreeEntity> getCreatePersonData() {
        String createPersonIds = repairFilterView.getCreatePersonIds();
        List<ScreeEntity> list = new ArrayList<>();
        ScreeEntity entity = new ScreeEntity();
        entity.setName(getContext().getString(R.string.repair_all_person));
//        entity.setId(getContext().getString(R.string.repair_all_person_id));
        entity.setId(0);
        if (!TextUtils.isEmpty(createPersonIds)
                && createPersonIds.contains("")) {
            entity.setSelector(true);
        } else {
            entity.setSelector(false);
        }
        list.add(entity);
        return list;
    }

    /**
     * @return 返回上下文参数.
     */
    public Context getContext() {
        return repairFilterView.getContexts();
    }

    /**
     * @return 返回执行人列表.
     */
    private List<ScreeEntity> getExcutePersonData() {
        List<ScreeEntity> list = new ArrayList<>();
        ScreeEntity entity = new ScreeEntity();
        String excutePersonIds = repairFilterView.getExcutePersonIds();
        entity.setName(getContext().getString(R.string.repair_all_person));
        entity.setId(0);
        if (!TextUtils.isEmpty(excutePersonIds) && excutePersonIds.contains("")) {
            entity.setSelector(true);
        } else {
            entity.setSelector(false);
        }
        list.add(entity);
        return list;
    }


    /**
     *
     */
    public void complete() {
        Intent intent = new Intent();
        intent.putExtra("selectDateStr",
                TimeUtil.getDateTimeStr(selectDate));
        intent.putExtra("processingPersonIds", repairFilterView.getSelectProcessPersonIds());
        intent.putExtra("stateIds", repairFilterView.getSelectStateIds());
        Date startTime = TimeUtil.getWeekFirstDay(selectDate);
        Date endTime = TimeUtil.getWeekLastDay(selectDate);
//        intent.putExtra("startTime", TimeUtil.getDateTimeStr(startTime));
        intent.putExtra("endTime", TimeUtil.getDateTimeStr(getDate(selectDate)));
        ((Activity) getContext()).setResult(REQUEST, intent);
        ((Activity) getContext()).finish();
    }

    private Date getDate(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
    }
}