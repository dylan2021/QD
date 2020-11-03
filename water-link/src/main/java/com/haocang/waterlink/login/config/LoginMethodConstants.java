package com.haocang.waterlink.login.config;

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
 * 创建时间：2018/8/24 9:05
 * 修 改 者：
 * 修改时间：
 */
public class LoginMethodConstants {

    public static final String ACCOUNT_SWITCHING = "/login/accountswitching";//路由  地址

    public static final String PASSWORD_FORGET = "uaa/api/users/forget-password";//不需要登录修改密码

    public static final String PASSWORD_NEW = "uaa/api/users/new-password";//首次登录修改密码

    public static final String PASSWORD_CHANGE = "uaa/api/users/change-password";//登录后修改 密码 ，需要输入旧密码

    public static final String GET_CAPTCHA = "message/api/captchas";//获取验证码

    public static final String CAPTCHA_VALIDITY = "message/api/captchas";//验证验证码是否有效

    public static final String GET_BULLETINS = "message/api/bulletins";//获取公告

    public static final String CLOSE_BULLETINS = "message/api/bulletins/close";//关闭公告

    public static final String PAASWORD_STRENGT = "uaa/api/users/validate-password-strength";//密码校验强度

    public static final String VALIDATE_TEL = "uaa/api/users/validate-tel";//校验手机号是否存在

    public static final String WEBSOCKET_URL = "uaa/api/websocket/address";//


}
