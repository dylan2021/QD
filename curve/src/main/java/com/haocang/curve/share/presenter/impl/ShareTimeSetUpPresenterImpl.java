package com.haocang.curve.share.presenter.impl;

import android.view.Gravity;
import android.view.View;

import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.widgets.view.SelectData;
import com.haocang.curve.R;
import com.haocang.curve.constants.CurveConstants;
import com.haocang.curve.main.bean.CurveConstans;
import com.haocang.curve.share.iview.ShareTimeSetUpView;
import com.haocang.curve.share.model.ShareModel;
import com.haocang.curve.share.model.impl.ShareModelImpl;
import com.haocang.curve.share.presenter.ShareTimeSetUpPresenter;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
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
 * 创建时间：2018/6/21 17:43
 * 修 改 者：
 * 修改时间：
 */
public class ShareTimeSetUpPresenterImpl implements ShareTimeSetUpPresenter {
    private ShareTimeSetUpView shareTimeSetUpView;
    private boolean isDisplayMore = true;
    private final static int HOUR = 0;
    private final static int DAY = 1;
    private final static int WEEK = 2;
    private int day = 86400;//天
    private int week = 604800;//周
    private int month = 2592000;//月
    private Date startCurdate;//分享时效 开始时间

    private Date endCurdate;//分享时效结束时间，默认比开始时间晚一个小时
    private ShareModel shareModel;

    public ShareTimeSetUpPresenterImpl(ShareTimeSetUpView shareTimeSetUpView) {
        this.shareTimeSetUpView = shareTimeSetUpView;
        shareModel = new ShareModelImpl();
        shareTimeSetUpView.displaySelect(HOUR);

    }

    @Override
    public void initData() {
        startCurdate = new Date();
        endCurdate = TimeUtil.geNextHour(startCurdate);
        shareTimeSetUpView.setStarTime(TimeUtil.getDateStr4(startCurdate));
        shareTimeSetUpView.setEndTime(TimeUtil.getDateStr4(endCurdate));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.hour_ll) {
            startCurdate = new Date();
            endCurdate = TimeUtil.geNextHour(startCurdate);
            shareTimeSetUpView.displaySelect(HOUR);
            setShowTime();
        } else if (v.getId() == R.id.day_ll) {
            startCurdate = new Date();
            endCurdate = TimeUtil.geNextDay(startCurdate);
            shareTimeSetUpView.displaySelect(DAY);
            setShowTime();
        } else if (v.getId() == R.id.week_ll) {
            startCurdate = new Date();
            endCurdate = TimeUtil.geNextDay2(startCurdate);
            shareTimeSetUpView.displaySelect(WEEK);
            setShowTime();
        } else if (v.getId() == R.id.startTime_ll) {
            startTime();
        } else if (v.getId() == R.id.endTime_ll) {
            endTime();
        } else if (v.getId() == R.id.more_time_ll) {
            isDisPlayMoreTime();
        } else if (v.getId() == R.id.submit_tv) {

            Map<String, Object> map = new HashMap<>();
            map.put("cycle", shareTimeSetUpView.getCycle());
            map.put("shareCycle", calLastedTime());
            Date beginDate = shareTimeSetUpView.getCurdate();//曲线主界面选择的日期
            String uploadDate = "";
            if (shareTimeSetUpView.getCycle().equals(CurveConstants.Cycle.DAY)) {  //天
                uploadDate = TimeUtil.getDayStr(beginDate) + " 00:00:00";
            }
            if (shareTimeSetUpView.getCycle().equals(CurveConstants.Cycle.WEEK)) { //周
                uploadDate = TimeUtil.getDateTimeStr(TimeUtil.convertWeekByDate(beginDate));
            }
            if (shareTimeSetUpView.getCycle().equals(CurveConstants.Cycle.MONTH)) {//月
                uploadDate = TimeUtil.getDateTimeStr(TimeUtil.getMonthFirstDay(beginDate));
            }
            map.put("beginDate", TimeTransformUtil.getUploadGMTTime(uploadDate));
            map.put("mpointId", shareTimeSetUpView.getPointIds());
            shareModel.shareCurve(shareTimeSetUpView.getContexts(), map, listener);
        }
    }

    GetEntityListener<String> listener = new GetEntityListener<String>() {
        @Override
        public void success(String url) {
            Map<String, Object> map = new HashMap<>();
            map.put("titles", shareTimeSetUpView.getTitles());
            map.put("contents", shareTimeSetUpView.getContents());
            map.put("curveUrl", url);
            ARouterUtil.toActivity(map, ArouterPathConstants.Curve.CURVE_SHARE);
            shareTimeSetUpView.closeFragment();
        }

        @Override
        public void fail(String err) {

        }
    };

    /**
     * 计算两个日期之间相差得秒数
     *
     * @return
     */
    private int calLastedTime() {
        long a = startCurdate.getTime();
        long b = endCurdate.getTime();
        int c = (int) ((b - a) / 1000);
        return c;
    }

    private void setShowTime() {
        shareTimeSetUpView.setStarTime(TimeUtil.getDateStr4(startCurdate));
        shareTimeSetUpView.setEndTime(TimeUtil.getDateStr4(endCurdate));
    }

    /**
     * 是否显示更多时间
     */
    private void isDisPlayMoreTime() {
        shareTimeSetUpView.displaySelect(-1);
        if (isDisplayMore) {
            isDisplayMore = false;
            shareTimeSetUpView.displayMore();
        } else {
            isDisplayMore = true;
            shareTimeSetUpView.hideMore();
        }
    }

    private void startTime() {
        SelectData selectData = new SelectData(shareTimeSetUpView.getContexts(), true);
        selectData.showAtLocation(shareTimeSetUpView.getTextView(), Gravity.BOTTOM, 0, 0);
        selectData.setDateClickListener(new SelectData.OnDateClickListener() {
            @Override
            public void onClick(String year, String month, String day, String hour, String minute) {
                String startTime = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":59";
                Date data = TimeUtil.getTranData(startTime);
                if (data.after(endCurdate)) {
                    ToastUtil.makeText(shareTimeSetUpView.getContexts(), "开始时间不能晚于结束时间");
                } else if (!data.after(new Date())) {
                    ToastUtil.makeText(shareTimeSetUpView.getContexts(), "开始时间不能晚于当前时间");
                } else {
                    shareTimeSetUpView.setStarTime(startTime);
                }

            }
        });
    }

    private void endTime() {
        SelectData selectData = new SelectData(shareTimeSetUpView.getContexts(), true);
        selectData.showAtLocation(shareTimeSetUpView.getTextView(), Gravity.BOTTOM, 0, 0);
        selectData.setDateClickListener(new SelectData.OnDateClickListener() {
            @Override
            public void onClick(String year, String month, String day, String hour, String minute) {
                String endTime = year + "-" + month + "-" + day + " " + hour + ":" + minute;
                Date data = TimeUtil.getTranData(endTime);
                if (data.before(startCurdate)) {
                    ToastUtil.makeText(shareTimeSetUpView.getContexts(), "结束时间必须晚于开始时间");
                } else {
                    shareTimeSetUpView.setEndTime(endTime);
                }

            }
        });
    }
}
