package com.haocang.waterlink.myapp.presenter.impl;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.LibConfig;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.GetListListener;
import com.haocang.waterlink.constant.HomeUrlConst;
import com.haocang.waterlink.home.bean.MenuEntity;
import com.haocang.waterlink.myapp.iview.MyAppEditView;
import com.haocang.waterlink.myapp.presenter.MyAppEditPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Response;

public class MyAppEditPresenterImpl implements MyAppEditPresenter {
    private MyAppEditView myAppEditView;

    public MyAppEditPresenterImpl(MyAppEditView myAppEditView) {
        this.myAppEditView = myAppEditView;
    }

    @Override
    public void submit() {
        JSONObject object = new JSONObject();
        try {
            object.put("ids", myAppEditView.getIds());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AddParameters addParameters = new AddParameters();
        new OkHttpClientManager()
                .setUrl(HomeUrlConst.HOME_MENUS)
                .setRequestMethod(LibConfig.HTTP_POST)
                .setRequestBody(addParameters.formBodyByObject(object))
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(String s) {
                        if (!TextUtils.isEmpty(s)) {
                            myAppEditView.success();
                        }
                    }

                    @Override
                    public void onErrorResponse(Response response) {

                    }
                }).builder();

    }

    @Override
    public void getMyAppList() {
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
                            for (MenuEntity entity : list) {
                                entity.setShowHomepage(true);
                            }
                        }
                        myAppEditView.setAppList(list);

                    }
                })
                .getList();

    }

}
