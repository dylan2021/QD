package com.haocang.fault.list.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.config.LibConfig;
import com.haocang.fault.R;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题： 设备维修
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/2713:56
 * 修 改 者：
 * 修改时间：
 */

@Route(path = LibConfig.AROUTE_FAULT_SUBMIT_SUCCESS)
public class FaultSubmitSuccessFragment extends Fragment implements View.OnClickListener {
    private TextView titleNameTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_fault_submit_success, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getText(R.string.falut_equipments));
        view.findViewById(R.id.fault_see_tv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        getActivity().finish();
    }
}
