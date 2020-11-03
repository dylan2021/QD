package com.haocang.base.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haocang.base.R;
import com.haocang.base.bean.CalendarViewEntity;

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
 * 创建时间：2018/10/26 13:23
 * 修 改 者：
 * 修改时间：
 */
public class CalendarViewAdapter extends BaseAdapter<CalendarViewEntity> {

    public CalendarViewAdapter(int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(BaseHolder holder, CalendarViewEntity item, int position) {
        TextView showTimeTv = holder.itemView.findViewById(R.id.date_tv);
        showTimeTv.setText(item.getShowTime());
        View view = holder.itemView;
        TextView todayTv = view.findViewById(R.id.today_tv);//todo 是否是今天
        View alarmDotTv = view.findViewById(R.id.red_dot_v);//todo 是否有未读报警
        TextView timerCountTv = view.findViewById(R.id.timer_count_tv);//todo 定时抓拍数量
        TextView alarmCountTv = view.findViewById(R.id.alarm_count_tv);//todo 报警抓拍数量
        LinearLayout todayLl = view.findViewById(R.id.is_today_ll);
        if (item.isToday()) {
            todayTv.setVisibility(View.VISIBLE);
        } else {
            todayTv.setVisibility(View.GONE);
        }
        if (item.getRead()) {
            alarmDotTv.setVisibility(View.VISIBLE);
        } else {
            alarmDotTv.setVisibility(View.GONE);
        }
        if (item.getTimerCount() != null ) {
            timerCountTv.setVisibility(View.VISIBLE);
            timerCountTv.setText(item.getTimerCount() + "");
        } else {
            timerCountTv.setVisibility(View.INVISIBLE);
            timerCountTv.setText("0");
        }
        if (item.getAlarmCount() != null) {
            alarmCountTv.setVisibility(View.VISIBLE);
            alarmCountTv.setText(item.getAlarmCount() + "");
        } else {
            alarmCountTv.setVisibility(View.INVISIBLE);
            alarmCountTv.setText("0");
        }
        if (!TextUtils.isEmpty(item.getSelectDate())) {
            todayLl.setBackgroundResource(R.drawable.ic_circular);
            showTimeTv.setTextColor(Color.parseColor("#5E5E5E"));
        } else {
            todayLl.setBackgroundResource(0);// todo  0 代表去掉背景图片
            showTimeTv.setTextColor(Color.parseColor("#ff9b9b9b"));
        }
    }

}
