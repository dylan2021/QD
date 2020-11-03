package com.haocang.patrol.manage.bean;

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
 * 创建时间：2018/5/12上午10:26
 * 修  改  者：
 * 修改时间：
 */
public class PatrolConstans {
    /**
     * 状态-未分派.
     */
    public static final String STATE_UNASSIGNED = "unallocated";
    /**
     * 状态-待处理.
     */
    public static final String STATE_TOBEEXCUTED = "toBeExecuted";
    /**
     * 状态-执行中
     */
    public static final String STATE_EXECUTING = "executing";
    /**
     * 状态-挂起.
     */
    public static final String STATE_FINISHG = "finished";
    /**
     * 状态-关闭.
     */
    public static final String STATE_ABNARMAL = "abnormal";
    /**
     * 状态-已完成.
     */
    public static final String STATE_INTERRUPT = "interrupt";

    /**
     * 异常.
     */
    public static final String ABNORMAL = "abnormal";

    /**
     * 地图默认缩放等级，默认17级.
     */
    public static final int DEFAULT_ZOOM_LEVEL = 17;

    /**
     *
     */
    public static final int MAP_LOCATION_ACCURACY = 10;

    /**
     *
     */
    public static final int MARKOPTION_PERIOD = 10;

    /**
     * 字体大小.
     */
    public static final int MAP_TEXTOPTION_FONTSIZE = 20;

    /**
     * 更新地图延时时间.
     */
    public static final int UPDATE_MAP_DELAY = 1000;

    /**
     * 画线宽度
     */
    public static final int PLOYLINE_WIDTH = 10;

}
