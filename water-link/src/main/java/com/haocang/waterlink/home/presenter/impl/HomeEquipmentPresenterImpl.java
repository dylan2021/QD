package com.haocang.waterlink.home.presenter.impl;

import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.bean.EquimentEntity;
import com.haocang.base.utils.GetListListener;
import com.haocang.equiment.constants.EquipmentMethod;
import com.haocang.waterlink.home.iview.HomeEquipmentView;
import com.haocang.waterlink.home.presenter.HomeEquipmentPresenter;

import java.lang.reflect.Type;
import java.util.List;

public class HomeEquipmentPresenterImpl implements HomeEquipmentPresenter {
    private HomeEquipmentView homeEquipmentView;

    public HomeEquipmentPresenterImpl(HomeEquipmentView homeEquipmentView) {
        this.homeEquipmentView = homeEquipmentView;
    }

    @Override
    public void getEquimentList() {
        CommonModel<EquimentEntity> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<EquimentEntity>>() {
        }.getType();
        progressModel
                .setContext(homeEquipmentView.getContext())
                .setParamMap(homeEquipmentView.getParamMap())
                .setListType(type)
                .setHasDialog(false)
                .setUrl(EquipmentMethod.EQUIMENT_LIST)
                .setListListener(new GetListListener<EquimentEntity>() {
                    @Override
                    public void success(final List<EquimentEntity> list) {
                        homeEquipmentView.render(list);
                    }
                })
                .getList();

    }
}
