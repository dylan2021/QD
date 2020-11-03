package com.haocang.waterlink.home.iview;

import android.content.Context;

import com.haocang.base.bean.EquimentEntity;

import java.util.List;
import java.util.Map;

public interface HomeEquipmentView {
    Context getContext();

    Map<String, Object> getParamMap();

    void render(List<EquimentEntity> list);
}
