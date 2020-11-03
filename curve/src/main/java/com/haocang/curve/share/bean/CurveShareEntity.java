package com.haocang.curve.share.bean;

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
 * 创建时间：2018/6/11 11:01
 * 修 改 者：
 * 修改时间：
 */
public class CurveShareEntity {
    public static final int SHARE_WECHAT = 0;
    public static final int SHARE_QQ = 1;
    private String mentName;
    private int icon;
    /**
     * 0，微信，1,qq
     */
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getMentName() {
        return mentName;
    }

    public void setMentName(String mentName) {
        this.mentName = mentName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }


}
