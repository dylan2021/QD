package com.haocang.base.version.model;

import android.content.Context;

import com.haocang.base.bean.AppVersion;
import com.haocang.base.bean.PatchEntity;
import com.haocang.base.utils.GetEntityListener;

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
 * 创建时间：2018/8/13 13:04
 * 修 改 者：Rick.Lau
 * 修改时间：2018/10/13
 */
public interface VersionUpdateModel {

    void getVersionUpdateModel(String tag, Context ctx, GetEntityListener<AppVersion> listener);

    void getPatchContent(String versionNo, Context ctx, GetEntityListener<PatchEntity> listener);

    void getPatchContentOld(String versionNo, Context ctx, GetEntityListener<PatchEntity> listener);
}
