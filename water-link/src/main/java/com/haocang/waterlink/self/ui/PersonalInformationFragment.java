package com.haocang.waterlink.self.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haocang.base.ui.CommonActivity;
import com.haocang.waterlink.R;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：个人信息
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/1/249:42
 * 修 改 者：
 * 修改时间：
 */
public class PersonalInformationFragment extends Fragment implements View.OnClickListener {
    /**
     * 标题.
     */
    private TextView titleNameTv;

    /**
     * 初始化.
     *
     * @param inflater           .
     * @param container          .
     * @param savedInstanceState .
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_personal_information, null);
        initView(view);
        return view;
    }

    /**
     * 绑定控件.
     *
     * @param view .
     */
    private void initView(final View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getResources().getString(R.string.personal_information));
        view.findViewById(R.id.username_ll).setOnClickListener(this);
        view.findViewById(R.id.modify_ll).setOnClickListener(this);
//        RxBarTool.noTitle(getActivity());

    }

    /**
     * 点击事件.
     *
     * @param view .
     */
    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.username_ll) {
            toFragment(EditHeadFragment.class.getName());
        } else if (view.getId() == R.id.modify_ll) {

        }
    }

    /**
     * 跳转.
     *
     * @param fragmentName 跳转地址.
     */
    private void toFragment(final String fragmentName) {
        Intent intent = new Intent(getActivity(), CommonActivity.class);
        intent.putExtra("fragmentName", fragmentName);
        startActivity(intent);
    }
}
