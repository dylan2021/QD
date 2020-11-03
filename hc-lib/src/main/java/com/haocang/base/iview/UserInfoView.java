package com.haocang.base.iview;

import android.content.Context;

import com.haocang.base.bean.UserEntity;

public interface UserInfoView {
    Context getContext();

    void setUserInfo(UserEntity entity);
}
