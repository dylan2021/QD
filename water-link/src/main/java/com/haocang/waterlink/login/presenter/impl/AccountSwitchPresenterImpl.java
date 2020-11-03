package com.haocang.waterlink.login.presenter.impl;

import com.haocang.offline.bean.user.OffLineUserEntity;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.haocang.waterlink.login.iview.AccountSwitchView;
import com.haocang.waterlink.login.presenter.AccountSwitchPresenter;

import java.util.ArrayList;
import java.util.List;

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
 * 创 建 者：hy
 * 创建时间：${DATA} 10:53
 * 修 改 者：
 * 修改时间：
 */
public class AccountSwitchPresenterImpl implements AccountSwitchPresenter {
    private AccountSwitchView accountSwitchView;

    public AccountSwitchPresenterImpl(AccountSwitchView accountSwitchView) {
        this.accountSwitchView = accountSwitchView;
    }

    @Override
    public void getUserList() {
        List<OffLineUserEntity> myList = new ArrayList<>();
        List<OffLineUserEntity> list = OffLineOutApiUtil.getUserList();
        for (int i = list.size() - 1; i >= 0; i--) {
            myList.add(list.get(i));
        }
        if (myList.size() >= 6) {
            accountSwitchView.setRecyclerCount(3);
            accountSwitchView.setUserList(myList.subList(0, 6));
        } else if (myList.size() >= 3) {
            accountSwitchView.setRecyclerCount(3);
            accountSwitchView.setUserList(myList);
        } else if (myList.size() <= 2) {
            accountSwitchView.setRecyclerCount(myList.size());
            accountSwitchView.setUserList(myList);
        }
    }
}
