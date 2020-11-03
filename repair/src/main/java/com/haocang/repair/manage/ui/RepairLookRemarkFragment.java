package com.haocang.repair.manage.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.repair.R;

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
 * 创建时间：2018/5/8下午2:15
 * 修  改  者：
 * 修改时间：
 */
@Route(path = ArouterPathConstants.Repair.REPAIR_LOOK_REMARK)
public class RepairLookRemarkFragment extends Fragment {

    /**
     * 备注框.
     */
    private TextView remarkTv;

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repair_look_remark, null);
        initView(view);
        return view;
    }

    /**
     * 初始化界面.
     *
     * @param view 根View
     */
    private void initView(final View view) {
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getTitle());
        view.findViewById(R.id.common_complete_tv).setVisibility(View.GONE);
        TextView labelTv = view.findViewById(R.id.repair_remark_label_tv);
        labelTv.setText(getLabel());
        remarkTv = view.findViewById(R.id.repair_remark_et);
        if (TextUtils.isEmpty(getRemark())) {
            remarkTv.setText(getString(R.string.repair_no_content));
        } else {
            remarkTv.setText(getRemark());
        }
        remarkTv.setMovementMethod(new ScrollingMovementMethod());
    }

    /**
     * 获取标题名称.
     *
     * @return
     */
    public String getTitle() {
        String title = getActivity().getIntent().getStringExtra("title");
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.repair_look_remark);
        }
        return title;
    }

    /**
     * 获取标签.
     *
     * @return
     */
    public String getLabel() {
        String title = getActivity().getIntent().getStringExtra("label");
        if (TextUtils.isEmpty(title)) {
            title = getString(R.string.repair_remark);
        }
        return title;
    }

    /**
     * 获取备注.
     *
     * @return
     */
    public String getRemark() {
        return getActivity().getIntent().getStringExtra("remark");
    }
}
