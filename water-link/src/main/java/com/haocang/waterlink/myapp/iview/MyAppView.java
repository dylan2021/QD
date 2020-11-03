package com.haocang.waterlink.myapp.iview;

import com.haocang.waterlink.home.bean.MenuEntity;

import java.util.List;

public interface MyAppView {

    void setMyAllAppList(List<MenuEntity> list);

    void setTabList(List<MenuEntity> list);

    void setIndexList(List<Integer> list);

    void setHomePage(List<MenuEntity> list);
}
