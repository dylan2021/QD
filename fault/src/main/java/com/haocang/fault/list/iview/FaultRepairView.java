package com.haocang.fault.list.iview;

import android.content.Context;

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
 * 创建时间：2018/5/89:45
 * 修 改 者：
 * 修改时间：
 */
public interface FaultRepairView {

    Context getContexts();

    Map<String, Object> getMap();

    void isEmpty(String error);

    void submitSuccess();//提交成功

    void setEquipmentName(String name);

    void setEquipmentId(int id);

    void setEquipmentCode(String code);//设备编号

    void setOrgName(String orgName);

    void setOrgId(int id);

    void setProcessName(String name);

    void setProcessId(int id);

}
