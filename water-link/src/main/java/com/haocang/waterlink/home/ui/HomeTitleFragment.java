package com.haocang.waterlink.home.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haocang.base.config.LibConfig;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.ScanCodeUtils;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.zxing.app.CaptureActivity;
import com.haocang.waterlink.R;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：  头部标题栏 。扫码
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/3/1518:17
 * 修 改 者：
 * 修改时间：
 */
public class HomeTitleFragment extends Fragment implements View.OnClickListener, PermissionsProcessingUtil.OnPermissionsCallback {

    private View rootView;
    private TextView titleTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_home_title, null);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        view.findViewById(R.id.sccan_iv).setOnClickListener(this);
        rootView = view.findViewById(R.id.hometitle_root_view);
        titleTv = view.findViewById(R.id.home_title_tv);
    }

    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.sccan_iv) {
            PermissionsProcessingUtil
                    .setPermissions(this, LibConfig.CAMERA, this);
        }
    }

    @Override
    public void onActivityResult(final int requestCode,
                                 final int resultCode,
                                 final Intent data) {
        if (requestCode == LibConfig.SCAN_CODE && data != null) {
            String result = data.getStringExtra(CaptureActivity.SCAN_RESULT);
            ScanCodeUtils.qrCodeType(result, getActivity());
        }
    }


    @Override
    public void callBack(boolean flag, String permission) {
        if (flag) {
            ScanCodeUtils.openScanCode(getActivity());
        } else {
            ToastUtil.makeText(getActivity(), getString(R.string.permissions_photo));
        }
    }

    public void setBackgroundColor(float alpha) {
        if (alpha > 100) {
            titleTv.setVisibility(View.VISIBLE);
        } else {
            titleTv.setVisibility(View.GONE);
        }
        rootView.setBackgroundColor(Color.argb((int) alpha, 12, 171, 223));

    }

    public void setAlpha(float alpha) {
        rootView.setAlpha(alpha);
    }
}
