package com.haocang.base.config;

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
 * 创建时间：2018/2/715:46
 * 修 改 者：
 * 修改时间：
 */
public class ErrorCode {

    public static final int DATA = 001;
    public static final int NO_USER = 002;//用户名不存在
    public static final int PASSWORDERRO = 003;//密码错误
    public static final int EXPIRE = 004;//超过有效期
    public static final int MORETHAN = 005;//登录错误超过5次,请稍候重试
    public static final int ERROOR = 006;//访问接口报错
    public static final int NO_PERMISSIONS = 401;//没有权限
    public static final int ERROR_CONTROL = 101;//下控错误信息
    public static final int DATA_CONTROL = 102;//下控成功信息


    public static final int READTIME = 007;//网络超时

    public class MessgeError {
        public static final String MESSAGE_ISFIRST = "massage_isTheFirstMessage";//已经是第一条数据
        public static final String MESSAGE_ISLAST = "massage_isTheLastMessage";//已经是最后一条数据了
    }
}
