package com.haocang.curve.main.presenter;

import android.view.View;

import com.haocang.curve.main.iview.CurveView;

import org.json.JSONObject;

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
 * 创建时间：2018/6/1下午2:52
 * 修  改  者：
 * 修改时间：
 */
public interface CurvePresenter {

    /**
     * 显示更多弹框.
     *
     * @param view 更多按钮
     */
    void showMore(View view);

    /**
     *
     */
    /**
     * 设置和界面交互接口.
     *
     * @param view 和界面交互接口
     */
    void setView(CurveView view);

    /**
     * 设置时间
     */
    void showPickTimeView();

    /**
     * 上一个周期.
     */
    void pre();

    /**
     * 下一个周期.
     */
    void next();

    /**
     * 显示选择周期弹框.
     */
    void showSelectCycleView();

    /**
     * 返回当前.
     */
    void backToCurrent();

    /**
     * 选择收藏.
     */
    void pickCollection();

    /**
     * 加载数据.
     */
    void loadData();

    /**
     * @return
     */
    String getStartTimeStr();

    /**
     * @return
     */
    String getEndTimeStr();

    /**
     * 设置默认选项
     */
    void setDefault();


    void submitTaggin(JSONObject jsonObject);
}
