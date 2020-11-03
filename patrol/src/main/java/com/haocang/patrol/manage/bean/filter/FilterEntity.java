package com.haocang.patrol.manage.bean.filter;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题：
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/4/8下午1:59
 * 修  改  者：
 * 修改时间：
 */
public class FilterEntity {
    /**
     * 标签名称.
     */
    private String labelName;
    /**
     * 标签ID.
     */
    private int labelId;
    /**
     * 标签的key.
     */
    private String labelKey;
    /**
     * 是否选中。
     */
    private boolean isSelect;

    /**
     * 返回是否选.
     *
     * @return 是否选中
     */
    public boolean isSelect() {
        return isSelect;
    }

    /**
     * 设置选中.
     *
     * @param select 选中标志
     */
    public void setSelect(final boolean select) {
        isSelect = select;
    }

    /**
     * 返回标签名称.
     *
     * @return
     */
    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }
}
