package com.haocang.base.bean;

import android.widget.ImageView;
import android.widget.TextView;

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
 * 创建时间：2018/1/1815:55
 * 修 改 者：
 * 修改时间：
 */
public class Navigate {
    private String name; /*导航栏名称*/
    private int commonIconRes; /*未选中时图标地址*/
    private int selectIconRes; /*选中时图标地址*/
    private ImageView navigateIv; /*导航栏图标显示*/
    private TextView navigateTv; /*导航栏名字显示*/


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCommonIconRes() {
        return commonIconRes;
    }

    public void setCommonIconRes(int commonIconRes) {
        this.commonIconRes = commonIconRes;
    }

    public int getSelectIconRes() {
        return selectIconRes;
    }

    public void setSelectIconRes(int selectIconRes) {
        this.selectIconRes = selectIconRes;
    }

    public ImageView getNavigateIv() {
        return navigateIv;
    }

    public void setNavigateIv(ImageView navigateIv) {
        this.navigateIv = navigateIv;
    }

    public TextView getNavigateTv() {
        return navigateTv;
    }

    public void setNavigateTv(TextView navigateTv) {
        this.navigateTv = navigateTv;
    }
}
