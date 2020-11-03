package com.haocang.fault.list.presenter.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.haocang.base.bean.ScreeEntity;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.base.utils.TimeUtil;
import com.haocang.fault.R;
import com.haocang.fault.list.iview.FaultFilterView;
import com.haocang.fault.list.presenter.FaultFilterPresenter;

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
public class FaultFilterPresenterImpl implements FaultFilterPresenter {

    private FaultFilterView faultFilterView;
    private Date selectDate;
    private int founderState = 1;//1为未选中 0选中
    private int processingPersonState = 1;//1未选中 0 选中

    public FaultFilterPresenterImpl(FaultFilterView faultFilterView) {
        this.faultFilterView = faultFilterView;
        String founder = faultFilterView.getFounder();
        if (!TextUtils.isEmpty(founder)) {
            founderState = Integer.parseInt(founder);
            faultFilterView.setFounder(founderState);
        }
        String processingPerson = faultFilterView.getProcessingPerson();
        if (!TextUtils.isEmpty(processingPerson)) {
            processingPersonState = Integer.parseInt(processingPerson);
            faultFilterView.setProcessingPerson(processingPersonState);
        }
    }

    /**
     * 初始化时间
     *
     * @param curDateStr 前一次设置的时间，当之前没设置过，则为当前时间
     */
    public void initData(final String curDateStr) {
        if (!TextUtils.isEmpty(curDateStr)) {
            selectDate = TimeUtil.getDateByStr(TimeTransformUtil.getShowLocalTime(curDateStr));
        } else {
            selectDate = new Date();
        }
        faultFilterView.setShowTime(TimeUtil.getDateStryyMM(getDate(selectDate)));
        faultFilterView.setFilterStatu(setStatuData());
        faultFilterView.setFilterSeverity(setSeverityData());
        Date startTime = TimeUtil.getWeekFirstDay(selectDate);
        Date endTime = TimeUtil.getWeekLastDay(selectDate);
//        faultFilterView.setStartTime(TimeUtil.getDateTimeStr(startTime));
//        faultFilterView.setEndTime(TimeUtil.getDateTimeStr(endTime));
        faultFilterView.setEndTime(TimeUtil.getDateTimeStr(getDate(selectDate)));
    }

    /**
     * 上一个周期
     */
    @Override
    public void pre() {
//        selectDate = TimeUtil.getLastWeek(selectDate);

//
//        Date startTime = TimeUtil.getWeekFirstDay(selectDate);
//        Date endTime = TimeUtil.getWeekLastDay(selectDate);
//        faultFilterView.setStartTime(TimeUtil.getDateTimeStr(startTime));
        selectDate = TimeUtil.getLastMonth(selectDate);
        faultFilterView.setShowTime(TimeUtil.getDateStryyMM(selectDate));
        Log.i("test", TimeUtil.getDateTimeStr(getDate(selectDate)));
        faultFilterView.setEndTime(TimeUtil.getDateTimeStr(getDate(selectDate)));
    }

    private Date getDate(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
    }


    /**
     * 下一个周期
     */
    @Override
    public void next() {
//        selectDate = TimeUtil.getNextWeek(selectDate);
//        faultFilterView.setShowTime(getShowTime());
//        Date startTime = TimeUtil.getWeekFirstDay(selectDate);
//        Date endTime = TimeUtil.getWeekLastDay(selectDate);
//        faultFilterView.setStartTime(TimeUtil.getDateTimeStr(startTime));
//        faultFilterView.setEndTime(TimeUtil.getDateTimeStr(endTime));
        selectDate = TimeUtil.getNextMonth(selectDate);
        faultFilterView.setShowTime(TimeUtil.getDateStryyMM(selectDate));
        Log.i("test", TimeUtil.getDateTimeStr(getDate(selectDate)));
        faultFilterView.setEndTime(TimeUtil.getDateTimeStr(getDate(selectDate)));
    }


    @Override
    public void founder() {
        if (founderState == 1) {
            founderState = 0;
            faultFilterView.setFounder(0);
        } else {
            founderState = 1;
            faultFilterView.setFounder(1);
        }

    }

    @Override
    public void processingPerson() {
        if (processingPersonState == 1) {
            processingPersonState = 0;
            faultFilterView.setProcessingPerson(processingPersonState);
        } else {
            processingPersonState = 1;
            faultFilterView.setProcessingPerson(processingPersonState);
        }

    }

    @Override
    public void reset() {
        selectDate = new Date();
        founderState = 1;
        processingPersonState = 1;
        faultFilterView.setFounder(founderState);
        faultFilterView.setProcessingPerson(processingPersonState);
    }


    /**
     * @return
     */
    private String getShowTime() {
        Date startTime = TimeUtil.getWeekFirstDay(selectDate);
        Date endTime = TimeUtil.getWeekLastDay(selectDate);
        return TimeUtil.converStrMMDD(startTime) + " - " + TimeUtil.converStrMMDD(endTime);
    }

    private List<ScreeEntity> setStatuData() {
        Context ctx = faultFilterView.getContexts();
        String statuIds = faultFilterView.getStatuIds();//选中的
        List<ScreeEntity> list = new ArrayList<>();
        int[] idSr = ctx.getResources().getIntArray(R.array.fault_filter_statu_id);
        String[] nameSr = ctx.getResources().getStringArray(R.array.fault_filter_statu);
        for (int i = 0; i < idSr.length && i < nameSr.length; i++) {
            ScreeEntity entity = new ScreeEntity();
            entity.setName(nameSr[i]);
            entity.setId(idSr[i]);
            if (!TextUtils.isEmpty(statuIds) && statuIds.contains(entity.getId() + "")) {
                entity.setSelector(true);
            } else {
                entity.setSelector(false);
            }
            list.add(entity);
        }
        return list;
    }

    private List<ScreeEntity> setSeverityData() {
        String severityIds = faultFilterView.getSeverityIds();//选中的
        Context ctx = faultFilterView.getContexts();
        List<ScreeEntity> list = new ArrayList<>();
        int[] idSr = ctx.getResources().getIntArray(R.array.fault_type_id);
        String[] nameSr = ctx.getResources().getStringArray(R.array.fault_type);
        for (int i = 0; i < idSr.length && i < nameSr.length; i++) {
            ScreeEntity entity = new ScreeEntity();
            entity.setName(nameSr[i]);
            entity.setId(idSr[i]);
            if (!TextUtils.isEmpty(severityIds) && severityIds.contains(entity.getId() + "")) {
                entity.setSelector(true);
            } else {
                entity.setSelector(false);
            }
            list.add(entity);
        }
        return list;
    }


}
