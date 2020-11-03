package com.haocang.repair.manage.bean;

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
public class RepairConstans {
    /**
     * 状态-未分派.
     */
    public static final Integer STATE_UNASSIGNED = 5;
    /**
     * 状态-未处理.
     */
    public static final int STATE_UNPROCESS = 0;
    /**
     * 状态-处理中
     */
    public static final Integer STATE_PROCESSING = 1;
    /**
     * 状态-挂起.
     */
    public static final Integer STATE_HANGUP = 2;
    /**
     * 状态-关闭.
     */
    public static final Integer STATE_CLOSE = 3;
    /**
     * 状态-已完成.
     */
    public static final Integer STATE_COMPLETE = 4;


    /**
     * 维修措施-更换.
     */
    public static final int METHOD_REPLACE = 1;
    /**
     * 维修措施-维修.
     */
    public static final int METHOD_REPAIR = 2;
    /**
     * 维修措施-其他.
     */
    public static final int METHOD_OTHER = 3;

    public static class StartActivityCode {
        /**
         * 备注结果码
         */
        public static final int REMARK_RESULT_CODE = 8909;
        /**
         * 备注请求码.
         */
        public static final int REMARK_REQUEST_CODE = 8010;
    }
}
