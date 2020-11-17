package com.haocang.waterlink.home.presenter.impl;

import android.content.Context;

import com.haocang.base.utils.GetListListener;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.MenuEntity;
import com.haocang.waterlink.home.iview.HomeMenuView;
import com.haocang.waterlink.home.presenter.HomeMenuPresenter;

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
 * 创 建 者：he
 * 创建时间：2018/1/1618:25
 * 修 改 者：
 * 修改时间：
 */
public class HomeMenuPresenterImpl implements HomeMenuPresenter {
    private HomeMenuView homeIView;

    public HomeMenuPresenterImpl(final HomeMenuView homeIView) {
        this.homeIView = homeIView;
    }

    String key[] = {"model"
            , "pump"
            , "shaft"
            , "curve"
            , "curve"
            , "patrol"
            , "repair"
            , "faultpost"
            , "fault"
            , "experiment"
            , "warm"
            , "warm"
            , "warm"
    };
    int id[] = {
            9042
            , 9042
            , 9043
            , 132
            , 131
            , 134
            , 135
            , 136
            , 137
            , 138
            , 1
            , 2
            , 3
    };
    int appType[] = {0
            , 0
            , 0
            , 0
            , 0
            , 0
            , 0
            , 0
            , 0
            , 0
            , 0
            , 0
            , 0
    };
    int icon[] = {
            R.drawable.model
            , R.drawable.pump
            , R.drawable.shaft
            , R.drawable.water
            , R.drawable.power
            , R.drawable.map
            , R.drawable.repair_menu
            , R.drawable.fault_upload
            , R.drawable.presented
            , R.drawable.experiment
            , R.drawable.ic_warm_0
            , R.drawable.ic_warm_1
            , R.drawable.ic_warm_2
    };
    String name[] = {
            "专题图"
            , "泵站工况"
            , "阀门井工况"
            , "水质查询"
            , "能耗查询"
            , "巡检管理"
            , "维修管理"
            , "缺陷申报"
            , "派工管理"
            , "数据录入"
            , "实时报警"
            , "报警数据"
            , "实时状态"
    };
    String url[] = {
            "/model/modelmsg"
            , "/pump/pumplist"
            , "/pump/pumplist"
            , "/curve/main"
            , "/curve/two"
            , "/patrol/list"
            , "/repair/list"
            , "/fault/post"
            , "/fault/list"
            , "/experiment/experiment"
            , "/warm/warmStatus"
            , "/warm/warmStatus"
            , "/warm/warmStatus"
    };

    @Override
    public void getHomeMenuData() {
        List<MenuEntity> list = new ArrayList<>();
        for (int i = 0; i < key.length; i++) {
            MenuEntity entity = new MenuEntity();
            entity.setKey(key[i]);
            entity.setId(id[i]);
            entity.setAppType(appType[i]);
            entity.setIcon(icon[i] + "");
//            entity.setIconUrl(HCLicConstant.ADDRESS_IP+iconUrl[i]);
            entity.setName(name[i]);
            entity.setTarget("_self");
            entity.setUrl(url[i]);
            list.add(entity);
        }
        listener.success(list);
    }

    @Override
    public void getFaultList() {

    }

    @Override
    public void getToDayPatrolList() {

    }

    @Override
    public void getOffLinePatrolList() {

    }

    private GetListListener<MenuEntity> listener = new GetListListener<MenuEntity>() {
        @Override
        public void success(List<MenuEntity> list) {
            setData(list);
        }
    };

    private void setData(final List<MenuEntity> list) {
        Context ctx = homeIView.getContexts();
        if (ctx == null) {
            return;
        }
        homeIView.setData(list);
    }


}
