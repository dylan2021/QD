package com.haocang.base.version.view;

import android.content.Context;

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
 * 创建时间：2018/8/13 13:03
 * 修 改 者：
 * 修改时间：
 */
public interface VersionUpdateView {

    Context getContexts();

    /**
     * 有新的版本
     */
    void showVersionDialog();

    /**
     * 是否需要强制升级
     */
    void isForceUpdate(boolean isForceUpdate);

    /**
     * 修改内容
     *
     * @param content
     */
    void apkUpdateContent(String content);

    void getVersionSize(String size);

    /**
     * 下载路径
     *
     * @param path
     */
    void apkDownLoadPath(String path);

    void setVersionNo(String versionNo);

    void senHaveVersion(boolean b);

    void noNewVersion();

    /**
     * 有新的差分包
     */
    void showPatchDialog();
}
