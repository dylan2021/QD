package com.haocang.waterlink.home.presenter.impl;

import android.content.Context;

import com.haocang.base.utils.GetListListener;
import com.haocang.maonlib.base.config.HCLicConstant;
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
    /**
     * V层.
     */
    private HomeMenuView homeIView;

    /**
     * 构造方法.
     *
     * @param homeIView .
     */
    public HomeMenuPresenterImpl(final HomeMenuView homeIView) {
        this.homeIView = homeIView;
    }

    String key[] = {"model"
            ,"pump"
            ,"shaft"
            ,"curve"
            ,"curve"
            ,"patrol"
            ,"repair"
            ,"faultpost"
            ,"fault"
            ,"experiment"
//            ,"monitor"
//            ,"maintain"
    };
    int id[] = {
            9042
            ,9042
            ,9043
            ,132
            ,131
            ,134
            ,135
            ,136
            ,137
            ,138
//            ,137
//            ,138
    };
    int appType[] = {0
            ,0
            ,0
            ,0
            ,0
            ,0
            ,0
            ,0
            ,0
            ,0
//            ,0
//            ,0
    };
    int icon[] = {
            R.drawable.model
            ,R.drawable.pump
            ,R.drawable.shaft
            ,R.drawable.water
            ,R.drawable.power
            ,R.drawable.map
            ,R.drawable.repair_menu
            ,R.drawable.fault_upload
            ,R.drawable.presented
            ,R.drawable.experiment
//            ,0,0,0,0,0
//            ,0
//            ,0
    };
    String name[] = {
            "模型信息"
            ,"泵站工况"
            ,"阀门井工况"
            ,"水质查询"
            ,"能耗查询"
            ,"巡检管理"
            ,"维修管理"
            ,"缺陷申报"
            ,"派工管理"
            ,"人工实验抄录"
//            ,"实时监测"
//            ,"养护管理"
    };
    String url[] = {
            "/model/modelmsg"
            ,"/pump/pumplist"
            ,"/pump/pumplist"
            ,"/curve/main"
            ,"/curve/two"
            ,"/patrol/list"
            ,"/repair/list"
            ,"/fault/post"
            ,"/fault/list"
            ,"/experiment/experiment"
//            ,"/monitor/home/"
//            ,"/maintain/list"
    };


//    String key[] = {"curve","curve","patrol","faultpost","fault","repair"
//            ,"model"
//            ,"pump"
////            ,"monitor"
////            ,"maintain"
//    };
//    int id[] = {9042,9042,132,131,134,135
//            ,136
//            ,137
////            ,137
////            ,138
//    };
//    int appType[] = {0,0,0,0,0,0
//            ,0
//            ,0
////            ,0
////            ,0
//    };
//    int icon[] = {
//        R.drawable.water,R.drawable.power,0,0,0,0,0
//            ,0
////            ,0
//    };
//    String iconUrl[] = {
//            "uaa/api/static/image/menu/app/water.png"
//            ,"uaa/api/static/image/menu/app/power.png"
//            ,"uaa/api/static/image/menu/app/patrol.png"
//            ,"uaa/api/static/image/menu/app/faultpost.png"
//            ,"uaa/api/static/image/menu/app/fault.png"
//            ,"uaa/api/static/image/menu/app/repair.png"
//            ,"uaa/api/static/image/menu/app/repair.png"
//            ,"uaa/api/static/image/menu/app/repair.png"
////            ,"uaa/api/static/image/menu/app/repair.png"
////            ,"uaa/api/static/image/menu/app/maintain.png"
//    };
//    String name[] = {"水质数据查询","能耗数据查询","巡检管理","缺陷申报","派工管理","维修管理"
//            ,"模型信息"
//            ,"泵站工况"
////            ,"实时监测"
////            ,"养护管理"
//    };
//    String url[] = {"/curve/main","/curve/two","/patrol/list","/fault/post","/fault/list","/repair/list"
//            ,"/model/modelmsg"
//            ,"/pump/pumplist"
////            ,"/monitor/home/"
////            ,"/maintain/list"
//    };

    @Override
    public void getHomeMenuData() {

        List<MenuEntity> list = new ArrayList<>();

        for (int i = 0; i < key.length; i++) {
            MenuEntity entity = new MenuEntity();
            entity.setKey(key[i]);
            entity.setId(id[i]);
            entity.setAppType(appType[i]);
            entity.setIcon(icon[i]+"");
//            entity.setIconUrl(HCLicConstant.ADDRESS_IP+iconUrl[i]);
            entity.setName(name[i]);
            entity.setTarget("_self");
            entity.setUrl(url[i]);
            list.add(entity);
        }
        listener.success(list);
        
        


//        MenuEntity entity2 = new MenuEntity();
//        entity2.setKey("curve");
//        entity2.setAppType(0);
//        entity2.setId(9042);
//        entity2.setIcon("https://sh.hc-yun.com:22001/uaa/api/static/image/menu/app/curve.png");
//        entity2.setIconUrl("https://sh.hc-yun.com:22001/uaa/api/static/image/menu/app/curve.png");
//        entity2.setName("能耗数据查询");
//        entity2.setTarget("_self");
//        entity2.setUrl("/curve/two");
////        entity2.set
//        //能耗数据查询
////        entity1.setState("2");
//        list.add(entity2);
//
//
//        MenuEntity entity3 = new MenuEntity();
//        entity3.setKey("patrol");
//        entity3.setAppType(0);
//        entity3.setId(132);
//        entity3.setIcon("http://122.114.2.195:17401/uaa/api/static/image/menu/app/patrol.png");
//        entity3.setIconUrl("http://122.114.2.195:17401/uaa/api/static/image/menu/app/patrol.png");
//        entity3.setName("巡检管理");
//        entity3.setTarget("_self");
//        entity3.setUrl("/patrol/list");
//        list.add(entity3);




//        CommonModel<MenuEntity> progressModel = new CommonModelImpl<>();
//        Type type = new TypeToken<List<MenuEntity>>() {
//        }.getType();
//        progressModel.setContext(homeIView.getContexts())
//                .setListType(type)
//                .setHasDialog(false)
////                .setUrl(MethodConstants.Uaa.MENUS_APP)
//                .setUrl(HomeMethodConfig.HOME_MENUS)
//                .setListListener(listener)
//                .getList();


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
