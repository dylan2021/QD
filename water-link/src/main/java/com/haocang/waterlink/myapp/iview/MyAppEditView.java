package com.haocang.waterlink.myapp.iview;

import com.haocang.waterlink.home.bean.MenuEntity;

import java.util.List;

public interface MyAppEditView {

    void success();

    String getIds();

    void setAppList(List<MenuEntity> list);
}
