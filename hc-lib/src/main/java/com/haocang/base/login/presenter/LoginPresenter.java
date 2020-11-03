package com.haocang.base.login.presenter;

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
 * 创建时间：2018/1/1017:23
 * 修 改 者：
 * 修改时间：
 */
public interface LoginPresenter {
    /**
     * 登陆操作.
     */
    void login(); //输入账号或者密码

    /**
     * 设置明文或者密文密码显示.
     */
    void setDisplayPass();


    /**
     * 密码是否为空.
     */
    boolean isPasswordEmpty(); //密码是否为空

    /**
     * 账号是否为空.
     */
    boolean isUsernameEmpty(); //账号是否为空

    /**
     * 是否显示密码.
     *
     * @param flag .
     */
    void displayPass(boolean flag); //是否显示密码

    /**
     * 回掉.
     */
    interface OnLoginFinishedListener {


        /**
         * 用户名错误.
         */
        void usernameError();

        /**
         * 密码错误.
         */
        void passwordError(); //密码错误


        /**
         * 租期是否过期.
         */
        void isLeaseValid(); //租凭期限是否有效


        /**
         * 登陆成功.
         */
        void loginSuccess(); //登陆成功

        /**
         * 连续输入错误密码三次.
         */
        void inputError();
    }


}
