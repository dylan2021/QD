package com.haocang.base.presenter.impl;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.bean.LabelEntity;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.iview.AllocatorView;
import com.haocang.base.iview.OnlinePersonView;
import com.haocang.base.presenter.AllocatorPresenter;
import com.haocang.base.utils.GetListListener;
import com.haocang.base.utils.StringUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllocatorPresenterImpl implements AllocatorPresenter {

    private AllocatorView allocatorView;

    private OnlinePersonView onlinePersonView;
    private boolean authorized = true;

    public AllocatorPresenterImpl(final AllocatorView postProcessingView) {
        this.allocatorView = postProcessingView;
    }

    public AllocatorPresenterImpl(final AllocatorView postProcessingView, boolean authorized) {
        this.allocatorView = postProcessingView;
        this.authorized = authorized;
    }

    public AllocatorPresenterImpl(final AllocatorView postProcessingView, OnlinePersonView onlinePersonView, boolean authorized) {
        this.allocatorView = postProcessingView;
        this.onlinePersonView = onlinePersonView;
        this.authorized = authorized;
    }

    @Override
    public void getAllocatorList() {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(allocatorView.getQueryName())) {
            map.put("queryName", StringUtils.utfCode(allocatorView.getQueryName()));
        }
        if (allocatorView.getOrgId() != null) {
            map.put("orgId", allocatorView.getOrgId());
        }
        if (!authorized) {
            map.put("authorized", false);
            map.remove("orgId");
        }
        CommonModel<LabelEntity> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<LabelEntity>>() {
        }.getType();
        progressModel
                .setContext(allocatorView.getContext())
                .setParamMap(map)
                .setHasDialog(false)
                .setListType(type)
                .setUrl(MethodConstants.Uaa.USER_LIST)
                .setListListener(new GetListListener<LabelEntity>() {
                    @Override
                    public void success(final List<LabelEntity> list) {
                        allocatorView.renderList(list);
                    }
                })
                .getList();
    }

    @Override
    public void getOnLinePersonList() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageSize", "0");
        CommonModel<LabelEntity> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<LabelEntity>>() {
        }.getType();
        progressModel
                .setContext(allocatorView.getContext())
                .setParamMap(map)
                .setHasDialog(false)
                .setListType(type)
                .setUrl(MethodConstants.Portal.ONLINE_PERSON)
                .setListListener(new GetListListener<LabelEntity>() {
                    @Override
                    public void success(final List<LabelEntity> list) {
                        if (onlinePersonView != null) {
                            onlinePersonView.renderOnlinePersonList(list);
                        }
                    }
                })
                .getList();

    }

}
