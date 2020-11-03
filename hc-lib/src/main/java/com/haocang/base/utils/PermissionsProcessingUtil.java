package com.haocang.base.utils;

import androidx.fragment.app.Fragment;

import com.leeiidesu.permission.PermissionHelper;
import com.leeiidesu.permission.callback.OnPermissionResultListener;

import java.util.ArrayList;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：  危险权限处理
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/2/816:34
 * 修 改 者：
 * 修改时间：
 */
public class PermissionsProcessingUtil {
    /**
     * 处理结果返回.
     */
    private static OnPermissionsCallback permissionsCallback;


    /**
     * 危险权限处理.
     *
     * @param fragment   .
     * @param permission 权限类型.
     */
    public void appPermissions(final Fragment fragment, final String permission) {
        PermissionHelper.with(fragment) // FragmentActivity/v4.app.Fragment/FragmentManager
                .permissions(permission)
                .listener(new OnPermissionResultListener() {
                    @Override
                    public void onGranted() {
                        permissionsCallback.callBack(true, permission);
                    }

                    @Override
                    public void onFailed(ArrayList<String> deniedPermissions) {
                        permissionsCallback.callBack(false, permission);
                    }
                })
//                .showOnRationale("必需要许可权限才能使用该功能", "取消", "去设置") //用户勾选不再提示会显示对话框
//                .showOnDenied("必需要许可权限才能使用该功能", "取消", "去设置") //用户勾选不再提示会显示对话框
//                .showOnDenied("必需要许可权限才能使用该功能")
                .request();
    }

    public interface OnPermissionsCallback {
        void callBack(boolean flag, String permission);//true成功授予权限
    }


    public PermissionsProcessingUtil setPermissionsCallback(OnPermissionsCallback permissionsCallback) {
        this.permissionsCallback = permissionsCallback;
        return this;
    }

    /**
     * 授予权限.
     *
     * @param fragment
     * @param permissions
     */
    public static void setPermissions(Fragment fragment, String permissions, PermissionsProcessingUtil.OnPermissionsCallback callback) {
        new PermissionsProcessingUtil()
                .setPermissionsCallback(callback)
                .appPermissions(fragment, permissions);
    }


}
