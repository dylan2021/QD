package com.haocang.curve.share.iview;

import android.app.Activity;

import com.haocang.curve.share.bean.CurveShareEntity;

import java.util.List;

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
 * 创建时间：2018/6/11 11:06
 * 修 改 者：
 * 修改时间：
 */
public interface CurveShareView {

    Activity getActivitys();

    /**
     * 要分享的url
     *
     * @return
     */
    String getUrl();

    /**
     * 设置分享菜单
     *
     * @param list
     */
    void setShareMenuList(List<CurveShareEntity> list);

    /**
     * 二维码分享是否选中
     *
     * @param flag
     */
    void isQRCodeSelect(boolean flag);

    /**
     * @return
     */
    String getTitles();


    /**
     * @return
     */
    String getContents();


}
