package com.haocang.waterlink.self.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haocang.waterlink.R;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：首页模块设置
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/1/3114:43
 * 修 改 者：
 * 修改时间：
 */
public class HomeModeleSettingFragment extends Fragment {
    /**
     * 标题控件.
     */
    private TextView titleNameTv;


    /**
     * 初始化加载.
     *
     * @param inflater           .
     * @param container          .
     * @param savedInstanceState .
     * @return .
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_home_setting, null);
        initView(view);
        return view;
    }


    /**
     * 初始化子控件.
     *
     * @param view .
     */
    private void initView(final View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getResources().getString(R.string.home_set_up));
    }
}
