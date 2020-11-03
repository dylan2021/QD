package com.haocang.waterlink.login.iview;

import android.content.Context;

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
 * 创建时间：2018/1/1017:22
 * 修 改 者：
 * 修改时间：
 */
public interface LoginView {
    /**
     * @return 获取用户名.
     */
    String getUserName();

    /**
     * @return 获取密码.
     */
    String getPassWord();

    /**
     * @return 获取上下文参数.
     */
    Context getContexts();

    /**
     * 账号为空
     */
    void usernameEmpty();

    /**
     * 密码为空.
     */
    void passWordEmpty(); //

    /**
     * 用户名错误.
     */
    void usernameError(); //用户名错误


    /**
     * 密码错误.
     */
    void passwordError(); //密码错误

    /**
     * 租期是否有效.
     */
    void isLeaseValid(); //租凭期限是否有效

    /**
     * 登陆成功.
     */
    void loginSuccess(); //登陆成功

    /**
     * 切换明文 .
     *
     * @param flag .
     */
    void togglePassword(boolean flag); //切换明密文

    /**
     * 密码长度不符合逻辑.
     */
    void setPasswordLength();

    /**
     * 格式错误.
     */
    void formattingError();

    /**
     * 连续输错三次.
     */
    void inputError();
}
