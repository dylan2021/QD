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
 * 创建时间：2018/1/1216:13
 * 修 改 者：
 * 修改时间：
 */
public interface ModifyPasswordView {

    Context getContexts();

    /**
     * 修改密码成功.
     */
    void modifySuccess();


    /**
     * @return 获取新的密码.
     */
    String getNewPwd();

    String getVerifycationCodeId();

    /**
     * @return 确认密码.
     */
    String getConfirmPwd();


    void setPwdStrength();//密码必须包含字母和数字

    /**
     * 修改失败.
     */
    void twicePwdError();

    /**
     * 新密码是否为空.
     */
    void pwdEmpty();

    /**
     * 确认密码是否为空
     */
    void confirmPwdEmpty();


    /**
     * 密码不相同
     */
    void pwdInequality();

    /**
     * 密码最小等于6位
     */
    void minLength();

    void pwdNotSame();//新旧密码不能相同

    /**
     * @return 获取手机号码.
     */
    String getTel();
}
