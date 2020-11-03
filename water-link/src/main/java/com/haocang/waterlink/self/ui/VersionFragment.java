package com.haocang.waterlink.self.ui;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haocang.base.utils.ToastUtil;
import com.haocang.base.version.VersionUpdateUtil;
import com.haocang.waterlink.R;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/3/2811:13
 * 修 改 者：
 * 修改时间：
 */
public class VersionFragment extends Fragment {

    private TextView versionTv;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_version, null);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        versionTv = view.findViewById(R.id.version_tv);
        versionTv.setText("版本：" + getLocalVersion(getActivity()));
        view.findViewById(R.id.detection_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VersionUpdateUtil.getInstance(getActivity()).sendMessge(new VersionUpdateUtil.SendMessge() {
                    @Override
                    public void sendMessge() {
                        ToastUtil.makeText(getActivity(), "当前已是最新版本");
                    }
                }).isNewVersion("Android");
            }
        });
    }


    public static String getLocalVersion(final Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }
}
