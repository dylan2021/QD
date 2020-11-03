package com.haocang.base.version.presenter.impl;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.haocang.base.bean.AppVersion;
import com.haocang.base.bean.PatchEntity;
import com.haocang.base.config.LibConfig;
import com.haocang.base.utils.FileUtils;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.version.model.VersionUpdateModel;
import com.haocang.base.version.model.impl.VersionUpdateModelImpl;
import com.haocang.base.version.presenter.VersionUpdatePresenter;
import com.haocang.base.version.view.VersionUpdateView;


/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区龙兰路277号一号楼9楼
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/8/13 11:42
 * 修 改 者：Rick.Lau
 * 修改时间：2018/10/10
 */
public class VersionUpdatePresenterImpl implements VersionUpdatePresenter {
    private VersionUpdateView updateView;
    private VersionUpdateModel updateModel;

    public VersionUpdatePresenterImpl(VersionUpdateView updateView) {
        this.updateView = updateView;
        updateModel = new VersionUpdateModelImpl();
    }

    @Override
    public void getVersionData(final String tag) {
        /**
         *  先判断是否有差分包的版本
         */
        String oldVersion = "Android-" + getVersionName();
        getPatchVersion(oldVersion, tag);
    }

    @Override
    public void getVersionDataOld(final String tag) {
        /**
         *  先判断是否有差分包的版本
         */
        String oldVersion = "Android-" + getVersionName();
        getPatchVersionOld(oldVersion, tag);
    }

    private void setFileSize(String sizeSr) {
        if (!TextUtils.isEmpty(sizeSr)) {
            long size = Long.parseLong(sizeSr) / 1000000;
            updateView.getVersionSize(size + "MB");
        }
    }

    /**
     * 获取差分包版本
     */
    private void getPatchVersion(String oldApkName, String tag) {
        updateModel.getPatchContent(oldApkName, updateView.getContexts(), new GetEntityListener<PatchEntity>() {
            @Override
            public void success(PatchEntity entity) {
                /**
                 * 如果 entity不为空的情况下 就有差分包 ，否则全部下载更新升级
                 */
                if (entity != null) {
                    setFileSize(entity.getSize());
                    updateView.apkUpdateContent(entity.getRemark());
                    updateView.setVersionNo(entity.getNewVersionNo());
                    updateView.apkDownLoadPath(entity.getPatchFullPath());
                    updateView.isForceUpdate(isForceUpdate(entity.getForcedUpgrade()));
                    updateView.showPatchDialog();
                } else {
                    getNewVersion(tag);
                }
            }

            @Override
            public void fail(String err) {

            }
        });
    }

    /**
     * 获取差分包版本
     */
    private void getPatchVersionOld(String oldApkName, String tag) {
        updateModel.getPatchContentOld(oldApkName, updateView.getContexts(), new GetEntityListener<PatchEntity>() {
            @Override
            public void success(PatchEntity entity) {
                /**
                 * 如果 entity不为空的情况下 就有差分包 ，否则全部下载更新升级
                 */
                if (entity != null) {
                    setFileSize(entity.getSize());
                    updateView.apkUpdateContent(entity.getRemark());
                    updateView.setVersionNo(entity.getNewVersionNo());
                    updateView.apkDownLoadPath(entity.getPatchFullPath());
                    updateView.isForceUpdate(isForceUpdate(entity.getForcedUpgrade()));
                    updateView.showPatchDialog();
                } else {
                    getNewVersion(tag);
                }
            }

            @Override
            public void fail(String err) {

            }
        });
    }

    /**
     * 获取新版本
     *
     * @param tag
     */
    private void getNewVersion(String tag) {
        updateModel.getVersionUpdateModel(tag, updateView.getContexts(), new GetEntityListener<AppVersion>() {
            @Override
            public void success(AppVersion entity) {
                try {
                    if (entity != null && !TextUtils.isEmpty(entity.getVersionNo()) && isNewVersion(getVersionName(), entity.getVersionNo())) {
                        updateView.apkDownLoadPath(entity.getApkPath());
                        updateView.apkUpdateContent(entity.getRemark());
                        setFileSize(entity.getSize());
                        updateView.isForceUpdate(isForceUpdate(entity.getForcedUpgrade()));
                        updateView.setVersionNo(entity.getVersionNo());
                        updateView.showVersionDialog();
                    } else {
                        updateView.noNewVersion();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail(String err) {

            }
        });
    }


    /**
     * @param forcedUpgrade 1.强制更新， 0.
     */
    private boolean isForceUpdate(int forcedUpgrade) {
        if (forcedUpgrade == 1) {
            return true;
        }
//        else if (!TextUtils.isEmpty(compatibleVersion) && isNewVersion(compatibleVersion)) {
//            //兼容的最低版本高于当前app版本则 强制升级
//            return true;
//        }
        else {
            return false;
        }
    }

    /**
     * 是否有新的版本
     */
    /**
     * 是否有新的版本
     */
    private boolean isNewVersion(String oldVersion, String newVersion) {
        oldVersion = oldVersion.replaceAll("[^(0-9)|\\.]", "");
        newVersion = newVersion.replaceAll("[^(0-9)|\\.]", "");
        if (oldVersion.equals(newVersion)) {
            return false;
        }
        String[] version1Array = oldVersion.split("\\.");
        String[] version2Array = newVersion.split("\\.");
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return false;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return true;
                }
            }
            return false;
        } else {
            return diff > 0 ? false : true;
        }
    }


    /**
     * 后台版本号是否大于当前app版本号
     *
     * @param newCode
     * @param oldSr
     * @return
     */
    private boolean isVersionCodeIdentical(String newCode, String oldSr) {
        boolean isVersion = false;//后台版本号是否大于当前app版本号
        int n = Integer.parseInt(newCode);
        int old = Integer.parseInt(oldSr);
        if (n > old) {
            isVersion = true;
        }
        return isVersion;
    }


    //版本名
    private String getVersionName() {
        return getPackageInfo().versionName;
    }

    //版本号
    private int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    //获取版本info
    private PackageInfo getPackageInfo() {
        PackageInfo pi = null;

        try {
            PackageManager pm = updateView.getContexts().getPackageManager();
            pi = pm.getPackageInfo(updateView.getContexts().getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * 判断abc 的顺序
     *
     * @param sr
     * @return
     */
    private int compareABC(String sr) {
        String[] abc = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        for (int i = 0; i < abc.length; i++) {
            if (abc[i].equalsIgnoreCase(sr)) {
                return i;
            }
        }
        return 0;
    }

    private String getOldApkPath() {
        String isOldApkName;
        String folderPath = FileUtils.savePatchPath(LibConfig.NEWAPK_FILE_PATH);
        String apkPath = FileUtils.getFilesPath(folderPath);
        if (!TextUtils.isEmpty(apkPath)) {
            isOldApkName = apkPath;
        } else {
            isOldApkName = "";
        }
        return isOldApkName;
    }

    public void getVersionInfo(final String tag) {
        updateModel.getVersionUpdateModel(tag, updateView.getContexts(), new GetEntityListener<AppVersion>() {
            @Override
            public void success(AppVersion entity) {
                try {
                    if (entity != null && !TextUtils.isEmpty(entity.getVersionNo()) && isNewVersion(tag, entity.getVersionNo())) {
                        updateView.senHaveVersion(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail(String err) {

            }
        });
    }
}
