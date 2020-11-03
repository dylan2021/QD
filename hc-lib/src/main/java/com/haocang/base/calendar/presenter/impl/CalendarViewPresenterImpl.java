package com.haocang.base.calendar.presenter.impl;

import android.provider.CalendarContract;

import com.haocang.base.bean.CalendarViewEntity;
import com.haocang.base.calendar.CalendarView;
import com.haocang.base.calendar.presenter.CalendarViewPresenter;
import com.haocang.base.calendar.view.CalendarIView;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;

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
 * 公司地址：中国上海市徐汇区龙兰路277号一号楼9楼
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/10/26 10:45
 * 修 改 者：
 * 修改时间：
 */
public class CalendarViewPresenterImpl implements CalendarViewPresenter {
    private CalendarIView calendarIView;
    private Date cureDate = new Date();

    public CalendarViewPresenterImpl(CalendarIView calendarIView) {
        this.calendarIView = calendarIView;
    }

    @Override
    public void lastMonthDate() {
        cureDate = TimeUtil.getLastMonth(cureDate);
        getCalendarDate();
    }

    @Override
    public void nextMonthDate() {
        cureDate = TimeUtil.getNextMonth(cureDate);
        getCalendarDate();
    }

    @Override
    public void showCalendarView() {
        getCalendarDate();
    }

    private void getCalendarDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(cureDate);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        int i = cal.get(Calendar.DAY_OF_WEEK);//todo 判断今天星期几
//        String[] str = {"", "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
//        System.out.println(str[i]);
        int dayCount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//todo 判断这个月有多少天
//        ToastUtil.makeText(calendarIView.getContexts(), str[i] + "这个月" + dayCount + "天");
        calendarIView.setCalendarToDay(TimeUtil.getDateStryyyyMM(cureDate));
        List<CalendarViewEntity> list = new ArrayList<>();
        for (int j = 1; j < i; j++) {
            CalendarViewEntity entity = new CalendarViewEntity();
            entity.setShowTime("");
            list.add(entity);
        }
        for (int k = 1; k <= dayCount; k++) {
            CalendarViewEntity entity = new CalendarViewEntity();
            if (k < 10) {
                entity.setDateTime(TimeUtil.getDateStryyMM(cureDate) + "-0" + k);
            } else {
                entity.setDateTime(TimeUtil.getDateStryyMM(cureDate) + "-" + k);
            }
            if (entity.getDateTime().equals(TimeUtil.getDayStr(new Date()))) {
                entity.setToday(true);
                entity.setSelectDate(entity.getDateTime());
            }
            entity.setShowTime(k + "");
            list.add(entity);
        }
        calendarIView.setCalendarData(list);
        calendarIView.setMonth(cureDate);
    }
}
