package com.haocang.waterlink.self.iview;

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
 * 创建时间：2018/4/1116:28
 * 修 改 者：
 * 修改时间：
 */
public interface SelfModifyPassWordView {
    Context getContexts();

    /**
     * 获取账号
     *
     * @return
     */
    String getAccount();

    /**
     * 获取旧密码
     *
     * @return
     */
    String getOldPwd();

    /**
     * 获取新密码.
     *
     * @return
     */
    String getNewPwd();

    /**
     * 确认密码.
     *
     * @return
     */
    String getConfirmPwd();

    /**
     * 获取本地密码.
     *
     * @return
     */
    String getLocalPwd();

    /**
     * 旧密码为空
     */
    void oldPwdEmpty();

    /**
     * 新密码为空.
     */
    void newPwdEmtpy();


    /**
     * 确认密码为空.
     */
    void confirmPwdEmpty();

    /**
     * 修改成功.
     */
    void updataSucess();

    void updataError();

    /**
     * 旧密码不正确
     */
    void oldPwdIncorrect();

    /**
     * 新密码长度不能少于6位
     */
    void newPwdLength();


    /**
     * 两次输入密码不一致.
     */
    void pwdInequality();

    String getUserId();

    /**
     * 新旧密码相同
     */
    void pwDidentical();

    void setPwdStrength();//密码必须包含字母和数字
}
