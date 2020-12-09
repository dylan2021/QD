package com.haocang.waterlink.login.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;
import com.haocang.base.utils.ToastUtil;
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
 * 创 建 者：hy
 * 创建时间：${DATA} 13:44
 * 修 改 者：
 * 修改时间：
 */
public class SetUpIPAddressFragment extends Fragment implements View.OnClickListener {
    private EditText editText;
    public static final String ADDRESS_IP = "https://mg.hc-yun.com:443/";//正式库
    public static final String ADDRESS_IP2 = "https://hcmgysx.hc-yun.com/";//预上线环境
    public static final String ADDRESS_IP3 = "https://sh.hc-yun.com:22001/";//测试库地址
    public static final String ADDRESS_IP4 = "http://kaifa.hc-yun.com:31013/";//开发库

    public static final String WS_ADDRESS = "wss://ws.mg.hc-yun.com:443/websocket/websocket";//正式库
    public static final String WS_ADDRESS_2 = "wss://ws.hcmgysx.hc-yun.com:443/websocket/websocket";//预上线
    public static final String WS_ADDRESS_3 = "wss://ws.dt.hc-yun.com:443/websocket/websocket";//测试环境
    public static final String WS_ADDRESS_4 = "wss://ws.kaifa.hc-yun.com:31013/websocket/websocket";//开发库

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_setup_ipaddress, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        editText = view.findViewById(R.id.ip_edit);
        view.findViewById(R.id.submit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = editText.getText().toString();
                if (isNotCorrectIP(ip)) {
                    ToastUtil.makeText(getActivity(), "请输入正确的IP地址(以'/'结尾)");
                    return;
                }
                LibConstants.setAddressIp(ip);
                LibConfig.setCookie("");
                getActivity().finish();
            }
        });
        view.findViewById(R.id.official_repository).setOnClickListener(this);
        view.findViewById(R.id.test_Library).setOnClickListener(this);
        view.findViewById(R.id.development_library).setOnClickListener(this);
        view.findViewById(R.id.pre_online).setOnClickListener(this);
    }

    private boolean isNotCorrectIP(String ip) {
        if (!TextUtils.isEmpty(ip) && ip.startsWith("http") && ip.endsWith("/")) {
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.official_repository) {
            //正式库
            LibConstants.setAddressIp(ADDRESS_IP);
            LibConstants.setWsAddressIp(WS_ADDRESS);
        } else if (v.getId() == R.id.test_Library) {
            //测试库
            LibConstants.setAddressIp(ADDRESS_IP3);
            LibConstants.setWsAddressIp(WS_ADDRESS_3);
        } else if (v.getId() == R.id.development_library) {
            //开发库
            LibConstants.setAddressIp(ADDRESS_IP4);
            LibConstants.setWsAddressIp(WS_ADDRESS_4);
        } else if (v.getId() == R.id.pre_online) {
            //预上线
            LibConstants.setAddressIp(ADDRESS_IP2);
            LibConstants.setWsAddressIp(WS_ADDRESS_2);
        }
        ToastUtil.makeText(getActivity(), "切换成功");
        LibConfig.setCookie("");
        getActivity().finish();
    }
}
