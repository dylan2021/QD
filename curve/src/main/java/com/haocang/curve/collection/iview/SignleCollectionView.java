package com.haocang.curve.collection.iview;

import android.content.Context;

import com.haocang.curve.collection.bean.PointList;
import com.haocang.curve.collection.bean.SignleCurve;

import java.util.List;

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
 * 创建时间：2018/6/4上午9:38
 * 修  改  者：
 * 修改时间：
 */
public interface SignleCollectionView {
    /**
     * @return 上下文参数.
     */
    Context getContext();

    /**
     * 获取查询关键字.
     *
     * @return
     */
    String getQueryName();

    /**
     * @param list 列表.
     */
    void renderList(List<SignleCurve> list);

    /**
     * @param list 列表.
     */
    void renderPointList(List<PointList> list);

    /**
     * 点击数据.
     *
     * @param entity 数据.
     */
    void onItemClick(SignleCurve entity);

    /**
     * 点击数据.
     *
     * @param entity 数据.
     */
    void onItemClick(PointList entity);
    /**
     *
     * @return
     */
    List<SignleCurve> getSelectedList();

    List<PointList> getSelectedPointList();

    List<String> getSelectedIdList();
}
