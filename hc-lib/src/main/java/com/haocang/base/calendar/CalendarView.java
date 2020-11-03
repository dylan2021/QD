package com.haocang.base.calendar;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haocang.base.R;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.CalendarViewAdapter;
import com.haocang.base.bean.CalendarViewEntity;
import com.haocang.base.calendar.presenter.CalendarViewPresenter;
import com.haocang.base.calendar.presenter.impl.CalendarViewPresenterImpl;
import com.haocang.base.calendar.view.CalendarIView;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 创建时间：2018/10/25 17:46
 * 修 改 者：
 * 修改时间：
 */
public class CalendarView extends RelativeLayout implements CalendarIView, View.OnClickListener {
    private RecyclerView recyclerview;
    private CalendarViewAdapter adapter;
    private CalendarViewPresenter presenter;
    private Context mCtx;
    private TextView todayTv;
    private OnSelectCalendarDate onSelectCalendarDate;

    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mCtx = context;
        setVisibility(View.GONE);
        LayoutInflater.from(context).inflate(R.layout.view_calendar, this);
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new GridLayoutManager(context, 7));
        recyclerview.setHasFixedSize(true);
        adapter = new CalendarViewAdapter(R.layout.adapter_calendar_view);
        recyclerview.setAdapter(adapter);
        findViewById(R.id.last_iv).setOnClickListener(this);
        findViewById(R.id.next_iv).setOnClickListener(this);
        findViewById(R.id.blank_ll).setOnClickListener(this);
        todayTv = findViewById(R.id.date_tv);
        presenter = new CalendarViewPresenterImpl(this);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, Object item) {
                CalendarViewEntity entity = (CalendarViewEntity) item;
                if (onSelectCalendarDate != null && !TextUtils.isEmpty(entity.getDateTime())) {
                    onSelectCalendarDate.selectCalendarDate(entity.getDateTime());
                    fileData(entity);
                }

            }

            @Override
            public void onLongClick(View view, int position, Object item) {

            }
        });
        presenter.showCalendarView();
    }

    private void fileData(CalendarViewEntity entity) {
        for (CalendarViewEntity e : adapter.mList) {
            if (e == entity) {
                e.setSelectDate(entity.getDateTime());
            } else {
                e.setSelectDate(null);
            }
        }
        adapter.notifyDataSetChanged();
        setVisibility(View.GONE);
    }


    /**
     * 弹出当前日历
     */
    public void showCalendarView() {
        setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.last_iv) {
            presenter.lastMonthDate();
        } else if (v.getId() == R.id.next_iv) {
            presenter.nextMonthDate();
        } else if (v.getId() == R.id.blank_ll) {
        }
    }

    @Override
    public Context getContexts() {
        return mCtx;
    }

    @Override
    public void setCalendarToDay(String today) {
        todayTv.setText(today);
    }

    @Override
    public void setCalendarData(List<CalendarViewEntity> list) {
        adapter.clear();
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setMonth(Date curDate) {
        if (onSelectCalendarDate != null) {
            onSelectCalendarDate.setMonthDate(curDate);
        }
    }

    /**
     * 把本月未读报警传递回来
     */
    public void getCurrentMonthRadAlarm(List<CalendarViewEntity> list) {
        if (list != null && list.size() > 0) {
            Map<String, CalendarViewEntity> map = new HashMap<>();
            for (CalendarViewEntity entity : list) {
                map.put(entity.getThumbdt(), entity);
            }
            for (int i = 0; i < adapter.getItemCount(); i++) {
                CalendarViewEntity localEntity = adapter.mList.get(i);
                if (map.containsKey(localEntity.getDateTime())) {
                    CalendarViewEntity entity = map.get(localEntity.getDateTime());
                    localEntity.setAlarmCount(entity.getAlarmCount());
                    localEntity.setTimerCount(entity.getTimerCount());
                    localEntity.setRead(entity.getRead());
                } else {
                    localEntity.setAlarmCount(null);
                    localEntity.setTimerCount(null);
                    localEntity.setRead(false);
                }
//                for (int k = 0; k < list.size(); k++) {
//                    if (!TextUtils.isEmpty(localEntity.getDateTime()) && localEntity.getDateTime().equals(list.get(k).getThumbdt()) && !TextUtils.isEmpty(localEntity.getAlarmcount())) {
//                        localEntity.setAlarm(true);
//                    } else {
//                        localEntity.setAlarm(false);
//                    }
//                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    public interface OnSelectCalendarDate {
        void selectCalendarDate(String date);//把选择好的日期传过去

        void setMonthDate(Date date);//把月份传出去
    }

    public void setOnSelectCalendarDate(OnSelectCalendarDate onSelectCalendarDate) {
        this.onSelectCalendarDate = onSelectCalendarDate;
    }
}
