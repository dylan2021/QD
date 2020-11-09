package com.haocang.waterlink.myapp.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.utils.GetListListener;
import com.haocang.waterlink.constant.HomeUrlConst;
import com.haocang.waterlink.constant.WaterLinkConstant;
import com.haocang.waterlink.home.bean.MenuEntity;
import com.haocang.waterlink.myapp.iview.MyAppView;
import com.haocang.waterlink.myapp.presenter.MyAppPresenter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyAppPresenterImpl implements MyAppPresenter {
    private MyAppView myAppView;

    public MyAppPresenterImpl(MyAppView myAppView) {
        this.myAppView = myAppView;
    }

    @Override
    public void getMyAllAppList() {
        CommonModel<MenuEntity> progressModel = new CommonModelImpl<>();
        Type type = new TypeToken<List<MenuEntity>>() {
        }.getType();
        progressModel
                .setListType(type)
                .setHasDialog(false)
                .setUrl(HomeUrlConst.HOME_ALL_MENU)
                .setListListener(new GetListListener<MenuEntity>() {
                    @Override
                    public void success(List<MenuEntity> list) {
                        if (list != null && list.size() > 0) {
                            dataFiltering(list);
                        }
                    }
                })
                .getList();
    }

    @Override
    public void getHomePageList() {
        CommonModel<MenuEntity> progressModel = new CommonModelImpl<>();
        Type type = new TypeToken<List<MenuEntity>>() {
        }.getType();
        progressModel
                .setListType(type)
                .setHasDialog(false)
                .setUrl(HomeUrlConst.HOME_MENUS)
                .setListListener(new GetListListener<MenuEntity>() {
                    @Override
                    public void success(List<MenuEntity> list) {
                        if (list != null && list.size() > 0) {
                            List<MenuEntity> menuEntityList = new ArrayList<>();
                            for (MenuEntity entity : list) {
                                if (WaterLinkConstant.VOICE_URL.equals(entity.getUrl())) {
                                    continue;
                                }
                                menuEntityList.add(entity);
                            }
                            myAppView.setHomePage(menuEntityList);

                        }

                    }
                })
                .getList();
    }


    private void dataFiltering(List<MenuEntity> list) {
        List<MenuEntity> firstMenuList = new ArrayList<>();
        List<MenuEntity> secondMenuList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MenuEntity entity = list.get(i);
            if (entity.getParentId() == 0) {
                entity.setAppType(0);
                firstMenuList.add(entity);
            } else {
                if (entity.getUrl().equals(WaterLinkConstant.VOICE_URL)) {
                    //如果是语音就不需要显示
                    continue;
                }
                entity.setAppType(1);
                secondMenuList.add(entity);
            }
        }
        myAppView.setTabList(firstMenuList);
        List<MenuEntity> menList = new ArrayList<>();
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < firstMenuList.size(); i++) {
            menList.add(firstMenuList.get(i));
            for (int k = 0; k < secondMenuList.size(); k++) {
                if (firstMenuList.get(i).getId() == secondMenuList.get(k).getParentId()) {
                    menList.add(secondMenuList.get(k));
                }
            }
            indexList.add(menList.size());
        }
        myAppView.setMyAllAppList(menList);
        myAppView.setIndexList(indexList);
    }


}
