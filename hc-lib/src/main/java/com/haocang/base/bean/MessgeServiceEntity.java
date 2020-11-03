package com.haocang.base.bean;

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
 * 创建时间：2018/4/1310:15
 * 修 改 者：
 * 修改时间：
 */
public class MessgeServiceEntity {


    /**
     * id : 188
     * userId : 5
     * title : 测试
     * content : 荸荠（学名：Heleocharis dulcis (Burm. f.) Trin.），又名马蹄、水栗、乌芋、菩荠等，属单子叶莎草科
     */

    private int id;
    private long userId;
    private String title;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title != null ? title.replaceAll("<a>", "").replaceAll("</a>", "") : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content != null ? content.replaceAll("<a>", "").replaceAll("</a>", "") : content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
