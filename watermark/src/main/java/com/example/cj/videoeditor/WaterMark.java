package com.example.cj.videoeditor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WaterMark {
    private TextView equNameTv;
    private TextView processTv;
    private TextView timeTv;
    private TextView addressTv;
    private TextView personTv;
    private TextView weatherTv;
    private LinearLayout contentLl;
    private Intent intent;

    public WaterMark(Context ctx, Intent intent) {
        this.intent = intent;
        View view = LayoutInflater.from(ctx).inflate(R.layout.view_water, null);
        equNameTv = view.findViewById(R.id.equ_name_tv);
        equNameTv.setText(getEquipName());
        processTv = view.findViewById(R.id.process_tv);
        processTv.setText("区域位置：" + getProcessName());
        timeTv = view.findViewById(R.id.time_tv);
        timeTv.setText("拍摄时间：" + getDateTimeStr(new Date()));
        addressTv = view.findViewById(R.id.address_tv);
        addressTv.setText("地         点：" + getAddress());
        personTv = view.findViewById(R.id.person_tv);
        personTv.setText("人         员：" + getPerson());
        weatherTv = view.findViewById(R.id.weather_tv);
        weatherTv.setText("天         气：" + getWeather());
        contentLl = view.findViewById(R.id.content_ll);
    }

    public WaterMark(Context ctx, Intent intent, int v) {
        View view = LayoutInflater.from(ctx).inflate(v, null);
        this.intent = intent;
        equNameTv = view.findViewById(R.id.equ_name_tv);
        equNameTv.setText(getEquipName());
        processTv = view.findViewById(R.id.process_tv);
        processTv.setText("区域位置：" + getProcessName());
        timeTv = view.findViewById(R.id.time_tv);
        timeTv.setText("拍摄时间：" + getDateTimeStr(new Date()));
        addressTv = view.findViewById(R.id.address_tv);
        addressTv.setText("地         点：" + getAddress());
        personTv = view.findViewById(R.id.person_tv);
        personTv.setText("人         员：" + getPerson());
        contentLl = view.findViewById(R.id.content_ll);
        weatherTv = view.findViewById(R.id.weather_tv);
        weatherTv.setText("天         气：" + getWeather());
    }

    public WaterMark setTextSize(int size) {
        equNameTv.setTextSize(size);
        processTv.setTextSize(size);
        addressTv.setTextSize(size);
        timeTv.setTextSize(size);
        personTv.setTextSize(size);
        return this;
    }

    public View getWaterMarkView() {
        return contentLl;
    }

    private String getEquipName() {
        return intent.getStringExtra("equName");
    }

    private String getProcessName() {
        return intent.getStringExtra("processName");
    }

    private String getAddress() {
        return intent.getStringExtra("address");
    }

    private String getPerson() {
        return intent.getStringExtra("person");
    }

    private String getWeather() {
        return intent.getStringExtra("weather");
    }

    public static String getDateTimeStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

}
