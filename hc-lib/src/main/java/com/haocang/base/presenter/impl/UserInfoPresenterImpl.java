package com.haocang.base.presenter.impl;

import android.util.Log;

import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.bean.UserEntity;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.iview.UserInfoView;
import com.haocang.base.presenter.UserInfoPresenter;
import com.haocang.base.utils.GetEntityListener;

import java.util.HashMap;
import java.util.Map;

public class UserInfoPresenterImpl implements UserInfoPresenter {
    private UserInfoView userInfoView;

    public UserInfoPresenterImpl(UserInfoView userInfoView) {
        this.userInfoView = userInfoView;
    }

    @Override
    public void getUserInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("webOper", false);
        map.put("appOper", true);//判断app有那些操作权限 例如 远程下控
        CommonModel<UserEntity> progressModel = new CommonModelImpl<>();
        progressModel
                .setContext(userInfoView.getContext())
                .setEntityType(UserEntity.class)
                .setUrl(MethodConstants.Uaa.PERSONAL)
                .setParamMap(map)
                .setEntityListener(new GetEntityListener<UserEntity>() {
                    @Override
                    public void success(final UserEntity entity) {
                        userInfoView.setUserInfo(entity);
                    }

                    @Override
                    public void fail(final String err) {

                    }
                }).getEntityNew();
    }
}
