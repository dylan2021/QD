package com.haocang.fault.post.iview;

import android.content.Context;

import java.util.List;
import java.util.Map;

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
 * 创建时间：2018/4/2514:14
 * 修 改 者：
 * 修改时间：
 */
public interface PostView {
    Context getContexts();

    Map<String, Object> getParameter();//所有的参数

    List<String> getfileList();

    void createSuccess(String id);//创建工单成功

    void setEquipmentId(int id);//扫码成功后才会调用

    void setEquipmentName(String name);//扫码 才会用到这个接口

    void setProcessName(String name);//只有扫码才会用

    void setProcessId(int id);

    /**
     * 离线模式，提交成功
     */
    void setOffLineSuccess();
}
