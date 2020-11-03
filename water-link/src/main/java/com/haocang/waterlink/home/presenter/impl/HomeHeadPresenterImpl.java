package com.haocang.waterlink.home.presenter.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.BDSendTraceUtil;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.GetListListener;
import com.haocang.base.utils.HanziToPinyin;
import com.haocang.base.utils.TimeUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.constant.bean.HomeConstants;
import com.haocang.waterlink.home.bean.BulletinsEntity;
import com.haocang.waterlink.home.bean.MenuEntity;
import com.haocang.waterlink.home.bean.WeatherEntity;
import com.haocang.waterlink.home.iview.HomeHeadView;
import com.haocang.waterlink.home.presenter.HomeHeadPresenter;
import com.haocang.waterlink.login.config.LoginMethodConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

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
 * 创建时间：2018/8/3下午4:50
 * 修  改  者：
 * 修改时间：
 */
public class HomeHeadPresenterImpl implements HomeHeadPresenter {

    private HomeHeadView mHomeHeadView;

    private boolean startCheckLocationThreadFlag = false;

    private SharedPreferences sp;

    public HomeHeadPresenterImpl(HomeHeadView homeHeadView) {
        mHomeHeadView = homeHeadView;
        sp = mHomeHeadView.getContext().getSharedPreferences(LibConfig.HOME_SETUP, Context.MODE_PRIVATE);
    }

    /**
     *
     */
    private Handler handler = new Handler();
    /**
     *
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            BDLocation location = BDSendTraceUtil.getInstance().getLocation();
            if (location != null) {
                String city = location.getCity();
                getWeatherData(city);
            } else {
                startCheckLocationThreadFlag = false;
                handler.postDelayed(this,
                        HomeConstants.CHECK_LOCATION_INTERVAL);
            }
        }
    };


    @Override
    public void getWeatherData() {
        WeatherEntity tempWeather = getWeather();
        if (tempWeather != null && TimeUtil.getDateStryyyyMMdd(new Date()).equals(tempWeather.getIsDay())) {
            mHomeHeadView.renderData(tempWeather);
            return;
        }
        BDLocation location = BDSendTraceUtil.getInstance().getLocation();
        if (location != null) {
            String city = location.getCity();
            getWeatherData(city);
        } else {
            handler.postDelayed(runnable,
                    HomeConstants.CHECK_LOCATION_INTERVAL);
            startCheckLocationThreadFlag = true;
        }
    }

    @Override
    public void stopCheckLocationThread() {
        if (startCheckLocationThreadFlag) {
            handler.removeCallbacks(runnable);
        }

    }

    @Override
    public void getBulletins() {
        CommonModel<BulletinsEntity> model = new CommonModelImpl<>();
        Type type = new TypeToken<List<BulletinsEntity>>() {
        }.getType();
        model.setEntityType(BulletinsEntity.class)
                .setUrl(LoginMethodConstants.GET_BULLETINS)
                .setHasDialog(false)
                .setListType(type)
                .setListListener(listener)
                .getList();
    }

    @Override
    public void closeBulletins() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", mHomeHeadView.getBulletinsId());
        map.put("type", mHomeHeadView.getBulletinsType());
        CommonModel<Integer> model = new CommonModelImpl<>();
        model.setParamMap(map)
                .setEntityType(Integer.class)
                .setHasDialog(false)
                .setUrl(LoginMethodConstants.CLOSE_BULLETINS)
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(final Integer entity) {
                        mHomeHeadView.closeSuccess();
                    }

                    @Override
                    public void fail(final String err) {
                    }
                })
                .putEntity();

    }

    @Override
    public void getWsAddress() {
        CommonModel<Integer> model = new CommonModelImpl<>();
        model.setHasDialog(false)
                .setUrl(LoginMethodConstants.WEBSOCKET_URL)
                .setEntityListener(new GetEntityListener<String>() {
                    @Override
                    public void success(final String result) {
                        mHomeHeadView.setWsAddress(result);
                    }

                    @Override
                    public void fail(final String err) {
                    }
                })
                .getEntityNew();
    }

    private GetListListener<BulletinsEntity> listener = new GetListListener<BulletinsEntity>() {
        @Override
        public void success(List<BulletinsEntity> list) {
            mHomeHeadView.renderBulletins(list);
        }
    };

    private void getWeatherData(String city) {
        try {
            if (mHomeHeadView.getContext() != null && city.endsWith(mHomeHeadView.getContext().getString(R.string.home_city))) {
                city = city.substring(0, city.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        new OkHttpClientManager()
//                .setLoadDialog()
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {

                    @Override
                    public void onNetworkResponse(final String result) {
                        try {
                            WeatherEntity weatherEntity = new WeatherEntity();
                            JSONObject json = new JSONObject(result);

                            JSONObject weather = json.getJSONObject("data");
                            weatherEntity.setCity(weather.getString("city"));
                            weatherEntity.setTemprature(weather.getString("wendu"));
                            JSONArray forecast = weather.getJSONArray("forecast");
                            JSONObject todayWeather = forecast.getJSONObject(0);
                            weatherEntity.setType(todayWeather.getString("type"));
                            weatherEntity.setIsDay(TimeUtil.getDateStryyyyMMdd(new Date()));
                            weatherEntity.setCityPinyin(HanziToPinyin.getPinYin(weatherEntity.getCity()).toUpperCase());
                            saveWheather(weatherEntity);
                            mHomeHeadView.renderData(weatherEntity);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(final Response response) {
                    }
                })
                .sendGet("http://wthrcdn.etouch.cn/weather_mini?city=" + city);
    }

    public void saveWheather(WeatherEntity weatherEntity) {
        try {
            SharedPreferences.Editor edit = sp.edit();
            String weatherEntityStr = new Gson().toJson(weatherEntity);
            edit.putString(HomeConstants.WHEATHER, weatherEntityStr);
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WeatherEntity getWeather() {
        WeatherEntity weatherEntity = null;
        String weatherEntityStr = sp.getString(HomeConstants.WHEATHER, "");
        if (!TextUtils.isEmpty(weatherEntityStr)) {
            weatherEntity = new Gson().fromJson(weatherEntityStr, WeatherEntity.class);
        }
        return weatherEntity;
    }
}
