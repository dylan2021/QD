package com.haocang.base.picker;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haocang.base.R;

import java.util.ArrayList;
import java.util.Calendar;
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
 * 创建时间：${DATA} 14:25
 * 修 改 者：
 * 修改时间：
 */
public class PickerView extends RelativeLayout {

    /**
     * UI
     */
    private PickerItemView pivYear;
    private PickerItemView pivMonth;
    private PickerItemView pivDay;
    private TextView tvYear;
    private TextView tvMonth;
    private TextView tvDay;

    /**
     * 变量
     */
    private List<String> years;
    private List<String> months;
    private List<String> days;

    private int year;
    private int month;
    private int day;
    private OnPickerViewListener onPickerViewChangeListener;

    public PickerView(Context context) {
        super(context);
        initView(context);
    }

    public PickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public PickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public PickerView(Context context, int layoutReSourceId) {
        super(context);
        initView(context, layoutReSourceId);
    }

    private void initView(Context context, int layoutReSourceId) {
        LayoutInflater.from(context).inflate(layoutReSourceId, this);
        initView();
    }

    private void initView(Context context) {
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.layout_picker, this);
        initView();
    }

    /**
     * 部分机型在popupWindow中，如果没有show popupWindow的话高度是一直为0的
     * 所以需要重写一下popupWindow的show方法中重新调用一下initView进行初始化，否则会显示错误
     */
    public void initView() {
        pivYear = findViewById(R.id.piv_year);
        pivMonth = findViewById(R.id.piv_month);
        pivDay = findViewById(R.id.piv_day);
        tvYear = findViewById(R.id.tv_year);
        tvMonth = findViewById(R.id.tv_month);
        tvDay = findViewById(R.id.tv_day);
        initData();
        initPIV();
        initWidth();
    }

    /**
     * 动态的写一下各个item的宽度
     */
    private void initWidth() {
        post(new Runnable() {
            @Override
            public void run() {
                float size = (float) ((getHeight() / 5) * 0.65);
                LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) pivYear.getLayoutParams();
                params1.width = (int) (size * 3);
                pivYear.setLayoutParams(params1);
                LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) pivMonth.getLayoutParams();
                params2.width = (int) (size * 1.7);
                pivMonth.setLayoutParams(params2);
                pivDay.setLayoutParams(params2);
                LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) pivMonth.getLayoutParams();
                params2.width = (int) (size * 2.4);

                /**
                 * 设置中间分隔字体的大小
                 */
//                tvYear.setTextSize(size);
//                tvMonth.setTextSize(size);
//                tvDay.setTextSize(size);
//                tvHour.setTextSize(size);
//                tvMinute.setTextSize(size);
//                tvSecond.setTextSize(size);
//                tvMillisecond.setTextSize(size);
            }
        });
    }

    /**
     * 响应监听
     */
    private void onChange() {
        if (onPickerViewChangeListener != null) {
            onPickerViewChangeListener.onChange(PickerView.this);
        }
    }

    /**
     * 初始化修改监听
     */
    private void initOnChange() {
        pivYear.setOnPitchOnChangeListener(new PickerItemView.OnStringListener() {
            @Override
            public void onClick(String str) {
                try {
                    year = Integer.parseInt(pivYear.getPitchOn());
                    updateDay(str, pivMonth.getPitchOn());
                    onChange();
                } catch (Exception e) {

                }
            }
        });
        pivMonth.setOnPitchOnChangeListener(new PickerItemView.OnStringListener() {
            @Override
            public void onClick(String str) {
                try {
                    month = Integer.parseInt(pivMonth.getPitchOn());
                    updateDay(pivYear.getPitchOn(), str);
                    onChange();
                } catch (Exception e) {

                }
            }
        });
        pivDay.setOnPitchOnChangeListener(new PickerItemView.OnStringListener() {
            @Override
            public void onClick(String str) {
                try {
                    day = Integer.parseInt(pivDay.getPitchOn());
                    onChange();
                } catch (Exception e) {

                }
            }
        });
    }

    /**
     * 初始化每一个Item的初始值
     */
    private void initPIV() {
        //年
        years = new ArrayList<>();
        for (int i = 1970; i < 2101; i++) {
            years.add(i + "");
        }
        pivYear.setList(years);

        //月
        months = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            months.add(int2Str2(i));
        }
        pivMonth.setList(months);

        //日
        days = getDayNumber(years.get(0), months.get(0));
        pivDay.setList(days);


        //设置监听
        initOnChange();
    }

    /**
     * 动态更新日期天数
     *
     * @param year
     * @param month
     */
    public void updateDay(String year, String month) {
        try {
            List<String> strings = getDayNumber(year, month);
            if (strings.size() != pivDay.getList().size()) {
                pivDay.setList(strings);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取一个月的天数
     */
    private List<String> getDayNumber(String year, String monthStr) {
        int num = 30;
        try {
            int month = Integer.parseInt(monthStr);
            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                num = 31;
            }
            if (month == 2) {
                if (getLeapYear(year)) {
                    num = 29;
                } else {
                    num = 28;
                }
            }
        } catch (Exception e) {
            num = 31;
        }

        List<String> strs = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            strs.add(int2Str2(i));
        }
        return strs;
    }

    /**
     * 设置初始值
     */
    private void initData() {
        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH) + 1;
        day = now.get(Calendar.DAY_OF_MONTH);
        update();
    }

    /**
     * 更新
     */
    private void update() {
        //设置初始值
        pivYear.setPitchOn(year + "");
        pivMonth.setPitchOn(int2Str2(month));
        pivDay.setPitchOn(int2Str2(day));
    }

    /**
     * 设置修改监听
     *
     * @param onPickerViewChangeListener
     */
    public void setOnPickerViewChangeListener(OnPickerViewListener onPickerViewChangeListener) {
        this.onPickerViewChangeListener = onPickerViewChangeListener;
    }

    /**
     * 根据需要，传入单位文本，如果为null或者空字符则表示不需要该项，
     * 如：只传时、分，则整个控件只有选择小时和分钟
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @param millisecond
     */
    public void setNameFormat(String year, String month, String day, String hour, String minute, String second, String millisecond) {
        //年
        if (TextUtils.isEmpty(year)) {
            tvYear.setVisibility(GONE);
            pivYear.setVisibility(GONE);
        } else {
            tvYear.setText(year);
            tvYear.setVisibility(VISIBLE);
            pivYear.setVisibility(VISIBLE);
        }

        //月
        if (TextUtils.isEmpty(month)) {
            tvMonth.setVisibility(GONE);
            pivMonth.setVisibility(GONE);
        } else {
            tvMonth.setText(month);
            tvMonth.setVisibility(VISIBLE);
            pivMonth.setVisibility(VISIBLE);
        }

        //日
        if (TextUtils.isEmpty(day)) {
            tvDay.setVisibility(GONE);
            pivDay.setVisibility(GONE);
        } else {
            tvDay.setText(day);
            tvDay.setVisibility(VISIBLE);
            pivDay.setVisibility(VISIBLE);
        }

    }

    /**
     * 根据高度设置最大最小字体的大小，范围是20-80
     *
     * @param minPercent
     * @param maxPercent
     */
    public void setFontSize(int minPercent, int maxPercent) {
        pivYear.setFontSize(minPercent, maxPercent);
        pivMonth.setFontSize(minPercent, maxPercent);
        pivDay.setFontSize(minPercent, maxPercent);
    }

    /**
     * 设置文字颜色
     *
     * @param normal 未选中颜色
     * @param press  选中颜色
     */
    public void setFontColor(int normal, int press) {
        pivYear.setFontColor(normal, press);
        pivMonth.setFontColor(normal, press);
        pivDay.setFontColor(normal, press);
    }

    /**
     * 设置分隔字体的样式
     *
     * @param fontSize  字体大小，空为默认
     * @param fontColor 颜色大小，空位默认
     */
    public void setSeparateTvStyle(Integer fontSize, Integer fontColor) {
        if (fontSize != null) {
            tvYear.setTextSize(fontSize);
            tvMonth.setTextSize(fontSize);
            tvDay.setTextSize(fontSize);
        }
        if (fontColor != null) {
            tvYear.setTextColor(fontColor);
            tvMonth.setTextColor(fontColor);
            tvDay.setTextColor(fontColor);
        }
    }

    /**
     * 计算是否是闰年
     * （能被4整除且不是整百年份的是闰年，能被4整除且是整百的只有能被400整除的才是闰年，即2000是闰年，但1900不是
     *
     * @param year
     * @return
     */
    private boolean getLeapYear(String year) {
        int num = Integer.parseInt(year);
        if (num % 4 == 0) {
            if (num % 100 == 0 && num % 400 != 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 1-->01
     */
    public static String int2Str2(int num) {
        String str = "";
        if (num < 10) {
            str = "0" + num;
        } else {
            str = "" + num;
        }
        return str;
    }

    /**
     * 1-->001
     */
    public static String int2Str3(int num) {
        String str = "";
        if (num < 10) {
            str = "00" + num;
        } else {
            if (num < 100) {
                str = "0" + num;
            } else {
                str = "" + num;
            }
        }
        return str;
    }

    public static interface OnPickerViewListener {
        public void onChange(PickerView pickerView);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        update();
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
        update();
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
        update();
    }


}
