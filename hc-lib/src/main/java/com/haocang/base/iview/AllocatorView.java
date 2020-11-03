package com.haocang.base.iview;

import android.content.Context;

import com.haocang.base.bean.LabelEntity;

import java.util.List;

public interface AllocatorView {

    String getQueryName();

    Integer getOrgId();

    Context getContext();

    void renderList(List<LabelEntity> list);


}
