package com.haocang.curve.main.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.haocang.base.config.LibConfig;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.curve.R;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.http.AddParameters;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.DensityUtil;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.curve.constants.CurveConstants;
import com.haocang.curve.constants.CurveMethod;
import com.haocang.curve.main.bean.CurveConstans;
import com.haocang.curve.main.iview.CurveView;
import com.haocang.curve.main.model.CurveModel;
import com.haocang.curve.main.model.impl.CurveModelImpl;
import com.haocang.curve.main.presenter.CurvePresenter;
import com.haocang.curve.more.bean.PointEntity;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


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
 * 创建时间：2018/6/1下午2:52
 * 修  改  者：
 * 修改时间：
 */
public class CurvePresenterImpl implements CurvePresenter {

    /**
     *
     */
    private PowerMenu mPowerMenu;
    /**
     *
     */
    private final float radidus = 10f;
    /**
     *
     */
    private CurveView mCurveView;
    /**
     *
     */
    private Date curDate = new Date();

    /**
     * 周期，默认周期为天.
     */
    private int cycle = CurveConstans.CYCLE_DAY;

    /**
     * @param view 和界面交互接口.
     */
    @Override
    public void setView(final CurveView view) {
        mCurveView = view;
    }

    /**
     *
     */
    @Override
    public void showPickTimeView() {
        Calendar c = Calendar.getInstance();
        c.setTime(curDate);
        TimePickerView pvTime
                = new TimePickerBuilder(getContext(),
                new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(final Date date, final View v) {
                        setDate(date);
                        loadData();
                    }
                }).isDialog(true).build();
        pvTime.show();

    }

    /**
     * @param date 时间.
     */
    private void setDate(final Date date) {
        curDate = date;
        mCurveView.setSelectTime(TimeUtil.getDayStr(date));
    }

    /**
     *
     */
    @Override
    public void pre() {
        if (cycle == CurveConstans.CYCLE_DAY) {
            curDate = TimeUtil.getLastDay(curDate);
        } else if (cycle == CurveConstans.CYCLE_WEEK) {
            curDate = TimeUtil.getLastWeek(curDate);
        } else if (cycle == CurveConstans.CYCLE_MONTH) {
            curDate = TimeUtil.getLastMonth(curDate);
        }
        setDate(curDate);
        loadData();
    }

    /**
     *
     */
    @Override
    public void next() {
        Date tempDate = curDate;
        if (cycle == CurveConstans.CYCLE_DAY) {
            tempDate = TimeUtil.getNextDay(curDate);
        } else if (cycle == CurveConstans.CYCLE_WEEK) {
            tempDate = TimeUtil.getNextWeek(curDate);
        } else if (cycle == CurveConstans.CYCLE_MONTH) {
            tempDate = TimeUtil.getNextMonth(curDate);
        }
        if (tempDate.after(new Date())) {
            ToastUtil.makeText(getContext(),
                    getContext().getString(R.string.curve_time_beyond_tip));
        } else {
            setDate(tempDate);
        }
        loadData();
    }

    /**
     *
     */
    @Override
    public void showSelectCycleView() {
        String[] repairResultArr = getContext().getResources().getStringArray(R.array.curve_cycle);
        new AlertView(null,
                null,
                getContext().getString(R.string.curve_cancel), null,
                repairResultArr, getContext(),
                AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(final Object o, final int position) {
                if (position == 0) {
                    curDate = new Date();
                    cycle = CurveConstans.CYCLE_DAY;
                    mCurveView.setCycle(getContext().getString(R.string.day));
                    mCurveView.setSelectTime(TimeUtil.getDayStr(curDate));
                } else if (position == 1) {
                    cycle = CurveConstans.CYCLE_WEEK;
                    mCurveView.setCycle(getContext().getString(R.string.week));
                    curDate = TimeUtil.convertWeekByDate(new Date());
                    mCurveView.setSelectTime(TimeUtil.getDayStr(curDate));
                } else if (position == 2) {
                    cycle = CurveConstans.CYCLE_MONTH;
                    mCurveView.setCycle(getContext().getString(R.string.month));
                    curDate = TimeUtil.getMonthFirstDay(new Date());
                    mCurveView.setSelectTime(TimeUtil.getDayStr(curDate));
                }
                loadData();
            }
        }).show();

    }

    /**
     *
     */
    @Override
    public void backToCurrent() {
        setDate(new Date());
        loadData();
    }

    /**
     *
     */
    @Override
    public void pickCollection() {
        Map<String, Object> map = new HashMap<>();
        map.put("fragmentUri", ArouterPathConstants.Curve.CURVE_COLLECTION);
        List<PointEntity> selectList = mCurveView.getSelectPointList();
        if (selectList != null) {
            Type type = new TypeToken<List<PointEntity>>() {
            }.getType();
            map.put("selectedPointsStr", new Gson().toJson(selectList, type));
        }
        ARouterUtil.startActivityForResult(map,
                (Activity) getContext(),
                CurveConstans.PICK_COLLECTION_REQUEST_CODE);
    }

    @Override
    public void loadData() {
        CurveModel model = new CurveModelImpl(getContext());
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(mCurveView.getSelectPoints())) {
            map.put("ids", mCurveView.getSelectPoints());
        }
        map.put("beginDate", TimeTransformUtil.getUploadGMTTime(TimeUtil.getDateSTimetr(getStartTime())));
        map.put("cycle", getCycle());
        model.getCurveData(map, new GetEntityListener<JSONObject>() {

            @Override
            public void success(JSONObject object) {
                if (TextUtils.isEmpty(mCurveView.getSelectPoints()) && object != null && !object.has("pointList")) {
                    mCurveView.setNoData();
                } else if (object != null) {
                    if (object.has("combineName")) {
                        try {
                            mCurveView.setCombineName(object.getString("combineName"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (object.has("pointList")) {
                        Type type = new TypeToken<List<PointEntity>>() {
                        }.getType();
                        try {
                            List<PointEntity> list = new Gson().fromJson(object.getString("pointList"), type);
                            mCurveView.setSelectedPoints(list);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (object.has("items")) {
                        try {
                            JSONArray datas = object.getJSONArray("items");
                            String datasStr = object.getString("items");
                            mCurveView.setData(datas, datasStr);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

            @Override
            public void fail(String err) {

            }
        });
    }

    public String getCycle() {
        String curveCycle = CurveConstants.Cycle.DAY;
        if (cycle == CurveConstans.CYCLE_WEEK) {
            curveCycle = CurveConstants.Cycle.WEEK;
        } else if (cycle == CurveConstans.CYCLE_MONTH) {
            curveCycle = CurveConstants.Cycle.MONTH;
        }
        return curveCycle;
    }

    @Override
    public String getStartTimeStr() {
        return TimeUtil.getDateTimeStr(getStartTime());
    }

    /**
     * @return
     */
    private Date getStartTime() {
        Date tempDate = null;
        if (cycle == CurveConstans.CYCLE_DAY) {
            tempDate = TimeUtil.getDayStart(curDate);
        } else if (cycle == CurveConstans.CYCLE_WEEK) {
            tempDate = TimeUtil.getWeekFirstDay(curDate);
        } else if (cycle == CurveConstans.CYCLE_MONTH) {
            tempDate = TimeUtil.getMonthFirstDay(curDate);
        }
        return tempDate;
    }

    @Override
    public String getEndTimeStr() {
        return TimeUtil.getDateTimeStr(getEndTime());
    }

    @Override
    public void setDefault() {
        setDate(curDate);

    }

    @Override
    public void submitTaggin(JSONObject jsonObject) {
        AddParameters addParameters =new AddParameters();
        addParameters.addParam("curveRemark",jsonObject);
        new OkHttpClientManager()
                .setUrl(CurveMethod.LOONNG_REMARKS)
                .setRequestMethod(LibConfig.HTTP_POST)
                .setRequestBody(addParameters.formBodyByObject(jsonObject))
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(String s) {
                        mCurveView.refreshCurve();
                    }

                    @Override
                    public void onErrorResponse(Response response) {

                    }
                }).builder();

    }

    private Date getEndTime() {
        Date tempDate = null;
        if (cycle == CurveConstans.CYCLE_DAY) {
            tempDate = TimeUtil.getDayEnd(curDate);
        } else if (cycle == CurveConstans.CYCLE_WEEK) {
            tempDate = TimeUtil.getWeekLastDay(curDate);
        } else if (cycle == CurveConstans.CYCLE_MONTH) {
            tempDate = TimeUtil.getMonthLastTime(curDate);
        }
        return tempDate;
    }

    /**
     * 获取上下文参数.
     *
     * @return
     */
    public Context getContext() {
        return mCurveView.getContext();
    }

    /**
     * 显示更多.
     *
     * @param view 更多按钮
     */
    @Override
    public void showMore(final View view) {
        List<PowerMenuItem> menuItems = getMenuItems();
        mPowerMenu = new PowerMenu.Builder(getContext())
                .addItemList(menuItems)
                .setMenuRadius(radidus)
                .setMenuShadow(radidus)
                .setMenuColor(Color.WHITE)
                .setWidth(DensityUtil.dip2px(getContext(), 100))
                .setDivider(new ColorDrawable(getContext().getResources().getColor(R.color.color_gray3)))
                .setDividerHeight(DensityUtil.dip2px(getContext(), 1))
                .setTextColor(getContext().getResources().getColor(R.color.color_blank3))
                .setSelectedTextColor(R.color.color_title_blue_bg)
                .setOnMenuItemClickListener(new OnMenuItemClickListener<PowerMenuItem>() {
                    @Override
                    public void onItemClick(final int position, final PowerMenuItem item) {
                        mPowerMenu.setSelectedPosition(position);
                        mPowerMenu.dismiss();
                        if (position == 0) {
                            addCollection();
                        } else if (position == 1) {
                            mCurveView.toShare();
                        } else if (position == 2) {
                            mCurveView.toMore();
                        }else if(position==3){
                            mCurveView.startTaggin();
                        }
                    }
                })
                .build();
//        mPowerMenu.
        mPowerMenu.showAsDropDown(view);
    }

    /**
     *
     */
    private void addCollection() {

//        String combineName =
        if (TextUtils.isEmpty(mCurveView.getCombineName())) {
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", ArouterPathConstants.Curve.CURVE_ADD_COLLECTION);
            List<PointEntity> selectList = mCurveView.getSelectPointList();
            if (selectList != null) {
                Type type = new TypeToken<List<PointEntity>>() {
                }.getType();
                map.put("selectedPointsStr", new Gson().toJson(selectList, type));
            }
            ARouterUtil.toFragment(map);
        } else {
            ToastUtil.makeText(getContext(), getContext().getString(R.string.curve_is_already_combine));
        }
//                startActivityForResult(map,
//                (Activity) getContext(),
//                CurveConstans.PICK_COLLECTION_REQUEST_CODE);
    }

    /**
     * 获取
     *
     * @return
     */
    private List<PowerMenuItem> getMenuItems() {
        List<PowerMenuItem> menuItems = new ArrayList<>();
        menuItems.add(new PowerMenuItem(getContext().getString(R.string.curve_collect), false));
        menuItems.add(new PowerMenuItem(getContext().getString(R.string.curve_share), false));
        menuItems.add(new PowerMenuItem(getContext().getString(R.string.curve_more), false));
        menuItems.add(new PowerMenuItem("添加备注",false));
        return menuItems;
    }
}
