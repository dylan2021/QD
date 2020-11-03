package com.haocang.waterlink.home.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.google.android.material.appbar.AppBarLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.actionanalysis.utils.ActionAnalysisUtilNew;
import com.haocang.base.bean.UserEntity;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;
import com.haocang.base.config.OnReceiveVoiceListener;
import com.haocang.base.utils.BDSendTraceUtil;
import com.haocang.base.utils.NfcUtil;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.WebSocketUtil;
import com.haocang.base.version.VersionUpdateUtil;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.haocang.patrol.patroloutside.nfcutil.PatrolNfcPresenter;
import com.haocang.waterlink.R;
import com.haocang.waterlink.constant.WaterLinkConstant;
import com.haocang.waterlink.constant.bean.HomeConstants;
import com.haocang.waterlink.home.iview.HomeView;
import com.haocang.waterlink.self.ui.VoicerSetFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题： 首页
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/1/1516:53
 * 修 改 者：
 * 修改时间：
 */
@Route(path = LibConfig.AROUTE_HOME)
public class HomeFragment extends Fragment
        implements View.OnClickListener, HomeView, PermissionsProcessingUtil.OnPermissionsCallback {

    private SharedPreferences sp;

    private View view;


    private static final int DELAYED_TIME = 2000;

    private HomeTitleFragment titleFragment;

    private HomeHeadFragment homeHeadFragment;

    private HomeMenuFragment homeMenuFragment;

    /**
     * 初始化加载.
     *
     * @param inflater           .
     * @param container          .
     * @param savedInstanceState .
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        this.view = view;
        setMyConfig();
        setWsAddress();
        initView(view);
        return view;
    }

    private void setMyConfig() {
        UserEntity userEntity = AppApplication.getInstance().getUserEntity();
        String tel = userEntity.getTel();
        HomeConstants.HomeSetupKey.HOME_DATA = tel + "home_setup_data";
        HomeConstants.HomeSetupKey.HOME_TASK = tel + "task";
        HomeConstants.HomeSetupKey.HOME_KPI = tel + "kpi";
//        VoicerSetFragment.VOICER_MALE = tel + "xiaoyu"; 这是值
//        VoicerSetFragment.VOICER_FEMALE = tel + "xiaoyan";这是值
        VoicerSetFragment.VOICER_MAX = tel + "voicer_set";
        LibConfig.SP_KEY.VOICER = tel + "voicer";
        WaterLinkConstant.SP_KEY.VOICE_SET = tel + "voice_set";
    }

    private void setWsAddress() {
        if (!TextUtils.isEmpty(AppApplication.getInstance().getUserEntity().getWebsocketAddress())) {
            String address = AppApplication.getInstance().getUserEntity().getWebsocketAddress();
            String wsAddress = "";
            if (address.contains("https")) {
                wsAddress = address.replace("https", "wss");
            } else if (address.contains("http")) {
                wsAddress = address.replace("http", "wss");
            }
            address = wsAddress + "/websocket/websocket";
            LibConstants.setWsAddressIp(address);
        }
    }


    /**
     * 初始化控件.
     *
     * @param view 获取到的布局
     */
    private void initView(final View view) {
        sp = getActivity().getSharedPreferences(LibConfig.HOME_SETUP, Context.MODE_PRIVATE);
        renderFragment();
        getPermisson(getActivity());
        getPermissions();
        WebSocketUtil.getInstance().createStompClient();//连接websocket
        boolean voiceSetFlag = sp.getBoolean(WaterLinkConstant.SP_KEY.VOICE_SET, false);
        if (voiceSetFlag) {
            AppApplication.getInstance().setOnReceiveVoiceListener(new OnReceiveVoiceListener() {
                @Override
                public void receiveVoice(String sentence) {
                    new ActionAnalysisUtilNew().analysis(sentence);
                }
            });
            if (sp.getInt(VoicerSetFragment.VOICER_MAX, 0) >= 0) {
                AppApplication.getInstance().setThreshhold(sp.getInt(VoicerSetFragment.VOICER_MAX,
                        VoicerSetFragment.THRESHHOLD_DEFAULT_VALUE) + VoicerSetFragment.THRESHHOLD_MIN_VALUE);
            }
        }
        AppApplication.getInstance().setOnReadNFCTagListener(new NfcUtil.OnReadNFCTagListener() {
            @Override
            public void readNFCTag(String s) {
                PatrolNfcPresenter.getTasksList(s);
            }
        });
        offline();

    }

    private void offline() {
        try {
            OffLineOutApiUtil.synData(getActivity());//同步基本数据
            OffLineOutApiUtil.synPatrolTask(getActivity());//同步巡检任务
            OffLineOutApiUtil.uploadJob(getActivity());//如果有未上传的工单就开始上传工单
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renderFragment() {
//        if (sp.getBoolean(HomeConstants.HomeSetupKey.HOME_KPI, true)) {
//            view.findViewById(R.id.kpi_realtabcontent).setVisibility(View.VISIBLE);
//            showFragment(R.id.kpi_realtabcontent, new HomeNewKpiFragment());
//        } else {
//            view.findViewById(R.id.kpi_realtabcontent).setVisibility(View.GONE);
//        }
        if (titleFragment == null) {
            titleFragment = new HomeTitleFragment();
            showFragment(R.id.home_title_realtabcontent, titleFragment);
        }
        if (homeHeadFragment == null) {
            homeHeadFragment = new HomeHeadFragment();
            showFragment(R.id.home_head_content, homeHeadFragment);
        }
        if (homeMenuFragment == null) {
            homeMenuFragment = new HomeMenuFragment();
            showFragment(R.id.menu_realtabcontent, homeMenuFragment);
        }
//        if (sp.getBoolean(HomeConstants.HomeSetupKey.HOME_DATA, true)) {
//            view.findViewById(R.id.home_data_fl).setVisibility(View.VISIBLE);
//            showFragment(R.id.home_data_fl, new HomeDataFragment());
//        } else {
//            view.findViewById(R.id.home_data_fl).setVisibility(View.GONE);
//        }
        if (sp.getBoolean(HomeConstants.HomeSetupKey.HOME_TASK, true)) {
            view.findViewById(R.id.agency_realtabcontent).setVisibility(View.VISIBLE);
            showFragment(R.id.agency_realtabcontent, new HomeTaskFragment());
        } else {
            view.findViewById(R.id.agency_realtabcontent).setVisibility(View.GONE);
        }
        AppBarLayout mAppBarLayout = view.findViewById(R.id.home_appbar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //第一种
                int toolbarHeight = appBarLayout.getTotalScrollRange();
                int dy = Math.abs(verticalOffset);
                if (dy <= toolbarHeight) {
                    float scale = (float) dy / toolbarHeight;
                    float alpha = scale * 255;
                    titleFragment.setBackgroundColor(alpha);
                }
            }
        });
    }

    private void showFragment(int resId, Fragment fragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(resId, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }


    /**
     * 所有的点击事件统一处理.
     *
     * @param view .
     */
    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
//            case R.id.scan_iv:
//                break;
            default:
        }
    }

    /**
     * @return 获取到上下文参数.
     */
    @Override
    public Context getContexts() {
        return getActivity();
    }


    @Override
    public void setMessage() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppApplication.getInstance().isUpgradeTips()) {
            VersionUpdateUtil.getInstance(getActivity()).isNewVersion("Android");
            AppApplication.getInstance().setUpgradeTips(false);
        }
        renderFragment();
    }


    private void getPermissions() {
        PermissionsProcessingUtil.setPermissions(this, list.get(position), this);
    }

    List<String> list = new ArrayList<>();
    private int position = 0;

    private void getPermisson(Context context) {
        //            PackageManager pm = context.getPackageManager();
//            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
//            //得到自己的包名
//            String pkgName = pi.packageName;
//
//            PermissionGroupInfo pgi;
//            PackageInfo pkgInfo = pm.getPackageInfo(pkgName, PackageManager.GET_PERMISSIONS);
//            String sharedPkgList[] = pkgInfo.requestedPermissions;
        String callPermisson = Manifest.permission.CALL_PHONE;
        list.add(LibConfig.STORAGE);
        list.add(LibConfig.CAMERA);
        list.add(LibConfig.AUDIO);
        list.add(LibConfig.LOCATION);
        list.add(callPermisson);
//        PermissionsProcessingUtil.setPermissions(this, permName, this);
    }

    @Override
    public void callBack(boolean flag, String permission) {
        position++;
        if (position < list.size()) {
            PermissionsProcessingUtil.setPermissions(this, list.get(position), this);
        }
        if (permission.equals(LibConfig.LOCATION) && flag) {
            BDSendTraceUtil.getInstance().start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (homeHeadFragment != null) {
                            homeHeadFragment.getWeather();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, DELAYED_TIME);
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        titleFragment.onActivityResult(requestCode, resultCode, data);
    }
}
