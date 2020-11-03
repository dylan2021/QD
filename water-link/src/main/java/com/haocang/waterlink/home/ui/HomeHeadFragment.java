package com.haocang.waterlink.home.ui;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haocang.base.config.LibConfig;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.BulletinsEntity;
import com.haocang.waterlink.home.bean.WeatherEntity;
import com.haocang.waterlink.home.iview.HomeHeadView;
import com.haocang.waterlink.home.presenter.HomeHeadPresenter;
import com.haocang.waterlink.home.presenter.impl.HomeHeadPresenterImpl;
import com.haocang.waterlink.widgets.FiveTextView;

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
 * 创建时间：2018/7/31下午5:39
 * 修  改  者：
 * 修改时间：
 */
public class HomeHeadFragment extends Fragment implements HomeHeadView, View.OnClickListener {

    private HomeHeadPresenter homeHeadPresenter;
    private TextView degreeTv;
    private ImageView weatherTypeIv;
    private TextView cityTv;
    private TextView cityPinYinTv;
    private LinearLayout weatherLl;

    private FiveTextView scollTv;
    private LinearLayout displayLl;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.home_head_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        weatherLl = view.findViewById(R.id.weather_ll);
        degreeTv = view.findViewById(R.id.home_degree_tv);
        weatherTypeIv = view.findViewById(R.id.home_weather_iv);
        cityTv = view.findViewById(R.id.home_city_tv);
        cityPinYinTv = view.findViewById(R.id.home_city_pinyin_tv);
        scollTv = view.findViewById(R.id.tv_scoll);
        displayLl = view.findViewById(R.id.display_ll);
        view.findViewById(R.id.close_bulletins_itbn).setOnClickListener(this);
        homeHeadPresenter = new HomeHeadPresenterImpl(this);
        homeHeadPresenter.getWeatherData();
        homeHeadPresenter.getBulletins();
//        homeHeadPresenter.getWsAddress();


    }

    public void getWeather() {
        homeHeadPresenter.getWeatherData();
    }

    @Override
    public void renderData(WeatherEntity weatherEntity) {
        weatherLl.setVisibility(View.VISIBLE);
        degreeTv.setText(weatherEntity.getTemprature() + getString(R.string.home_degree));
        setWeatherTypeIv(weatherEntity.getType());
        cityTv.setText(weatherEntity.getCity());
        cityPinYinTv.setText(weatherEntity.getCityPinyin());
        LibConfig.weather = weatherEntity.getType();
        LibConfig.temperature = weatherEntity.getTemprature() + "°C";

    }

    private BulletinsEntity entity;

    @Override
    public void renderBulletins(List<BulletinsEntity> list) {
        if (list != null && list.size() > 0 && list.get(0).getScroll() == 1) {
            entity = list.get(0);
            displayLl.setVisibility(View.VISIBLE);
            scollTv.initScrollTextView(getActivity().getWindowManager(), list.get(0).getContent(), 2);//scoll_content为滚动的内容
            scollTv.starScroll();
        } else {
            displayLl.setVisibility(View.GONE);
        }
    }

    @Override
    public void closeSuccess() {
    }

    @Override
    public int getBulletinsId() {
        return entity.getId();
    }

    @Override
    public String getBulletinsType() {
        return "scroll";
    }

    @Override
    public void setWsAddress(String address) {
//        if (!TextUtils.isEmpty(address)) {
//            String wsAddress = "";
//            if (address.contains("http")) {
//                wsAddress = address.replace("http", "wss");
//            }
//            LibConstants.setWsAddressIp(wsAddress + "/websocket/websocket");
//
//        }
    }

    private void setWeatherTypeIv(String type) {
        if (type != null) {
            if (type.contains("雪")) {
                weatherTypeIv.setBackgroundResource(R.mipmap.icon_sunny);
            } else if (type.contains("多云")) {
                weatherTypeIv.setBackgroundResource(R.mipmap.icon_cloudy2);
            } else if (type.contains(getString(R.string.home_rain))) {
                weatherTypeIv.setBackgroundResource(R.mipmap.home_weather_rain);
            } else if (type.contains(getString(R.string.home_sunny))) {
                weatherTypeIv.setBackgroundResource(R.mipmap.home_weather_sunny);
            } else {
                weatherTypeIv.setBackgroundResource(R.mipmap.home_weather_shade);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        homeHeadPresenter.stopCheckLocationThread();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.close_bulletins_itbn) {
            homeHeadPresenter.closeBulletins();
            scollTv.stopScroll();
            handler.postDelayed(mViewGone, 0);
        }
    }

    Runnable mViewGone = new Runnable() {
        @Override
        public void run() {
            AlphaAnimation animationClose = new AlphaAnimation(1f, 0f);
            animationClose.setDuration(400);
            displayLl.setAnimation(animationClose);
            animationClose.startNow();
            displayLl.setVisibility(View.GONE);
        }
    };
    Handler handler = new Handler();
}
