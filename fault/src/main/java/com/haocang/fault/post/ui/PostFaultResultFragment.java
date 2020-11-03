package com.haocang.fault.post.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.fault.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题： 缺陷提交成功.
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/5/216:45
 * 修 改 者：
 * 修改时间：
 */
@Route(path = LibConfig.AROUTE_FAULT_RESULT)
public class PostFaultResultFragment extends Fragment implements View.OnClickListener {

    private TextView titleNameTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_post_fault_result, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText("缺陷管理");
        view.findViewById(R.id.fault_see_tv).setOnClickListener(this);
        view.findViewById(R.id.patrol_backhome_tv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fault_see_tv) {
            Map<String, Object> map = new HashMap<>();
            map.put("faultId", getActivity().getIntent().getStringExtra("faultId"));
            map.put("fragmentUri", LibConfig.AROUTE_FAULT_POST_MANAGER_DETAILS);
            ARouterUtil.toFragment(map);
        } else if (v.getId() == R.id.patrol_backhome_tv) {
//            ARouterUtil.toActivity(null, "/home/navigation");
//            AppApplication.getInstance().finishAllActivity();
            AppApplication.getInstance().backHome(getActivity());
        }

    }
}
