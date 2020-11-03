package com.haocang.curve.share.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.utils.TimeUtil;
import com.haocang.curve.R;
import com.haocang.curve.share.iview.ShareTimeSetUpView;
import com.haocang.curve.share.presenter.ShareTimeSetUpPresenter;
import com.haocang.curve.share.presenter.impl.ShareTimeSetUpPresenterImpl;

import java.util.ArrayList;
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
 * 标 题： 时效性设置.
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/6/21 16:32
 * 修 改 者：
 * 修改时间：
 */
@Route(path = ArouterPathConstants.Curve.CURVE_SHAPRE_SETUP)
public class ShareTimeSetUpFragment extends Fragment implements View.OnClickListener, ShareTimeSetUpView {
    private TextView showStartTimeTv;
    private TextView showEndTimeTv;
    private ShareTimeSetUpPresenter presenter;


    private TextView titleCommonTv;

    private LinearLayout moreTimeLl;

    private List<ImageView> ivList = new ArrayList<>();

    private ImageView hourIv;
    private ImageView dayIv;
    private ImageView weekIv;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_share_time_setup, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        titleCommonTv = view.findViewById(R.id.title_common_tv);
        titleCommonTv.setText("分享时效性设置");
        showStartTimeTv = view.findViewById(R.id.show_starTime_tv);
        showEndTimeTv = view.findViewById(R.id.show_endTime_tv);
        moreTimeLl = view.findViewById(R.id.more_time_setup_ll);
        view.findViewById(R.id.hour_ll).setOnClickListener(this);
        view.findViewById(R.id.day_ll).setOnClickListener(this);
        view.findViewById(R.id.week_ll).setOnClickListener(this);
        view.findViewById(R.id.startTime_ll).setOnClickListener(this);
        view.findViewById(R.id.endTime_ll).setOnClickListener(this);
        view.findViewById(R.id.submit_tv).setOnClickListener(this);
        view.findViewById(R.id.more_time_ll).setOnClickListener(this);
        hourIv = view.findViewById(R.id.hour_iv);
        dayIv = view.findViewById(R.id.day_iv);
        weekIv = view.findViewById(R.id.week_iv);
        ivList.add(hourIv);
        ivList.add(dayIv);
        ivList.add(weekIv);
        presenter = new ShareTimeSetUpPresenterImpl(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.initData();
    }

    @Override
    public void onClick(View v) {
        presenter.onClick(v);
    }


    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public void setStarTime(String starTime) {
        showStartTimeTv.setText(starTime);
    }

    @Override
    public void setEndTime(String endTime) {
        showEndTimeTv.setText(endTime);
    }

    @Override
    public TextView getTextView() {
        return showStartTimeTv;
    }

    @Override
    public void displayMore() {
        moreTimeLl.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMore() {
        moreTimeLl.setVisibility(View.INVISIBLE);
    }

    @Override
    public void displaySelect(int index) {
        for (int i = 0; i < ivList.size(); i++) {
            if (index == i) {
                ivList.get(i).setVisibility(View.VISIBLE);
            } else {
                ivList.get(i).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public String getCycle() {
        return getActivity().getIntent().getStringExtra("cycle");
    }

    @Override
    public String getPointIds() {
        return getActivity().getIntent().getStringExtra("pointIds");
    }

    @Override
    public String getTitles() {
        return getActivity().getIntent().getStringExtra("titles");
    }

    @Override
    public String getContents() {
        return getActivity().getIntent().getStringExtra("contents");
    }


    @Override
    public void closeFragment() {
        getActivity().finish();
    }

    @Override
    public Date getCurdate() {
        return TimeUtil.getDateByStr(getActivity().getIntent().getStringExtra("month") + " 00:00:00");
    }

}
