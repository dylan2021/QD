package com.haocang.base.base.impl;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.haocang.base.base.CommonModel;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.GetListListener;
import com.haocang.base.utils.GetListStringListener;
import com.haocang.base.utils.GetListWithTotalListener;
import com.haocang.base.utils.ProgressBarDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题：
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/5/14上午10:07
 * 修  改  者：
 * 修改时间：
 */
public class CommonModelImpl<T> implements CommonModel<T> {
    /**
     * 上下文参数.
     */
    private Context mContext;
    /**
     * 参数.
     */
    private Map<String, Object> paramMap;

    /**
     * 获取详情回调.
     */
    private GetEntityListener<T> mEntityListener;

    /**
     * 详情实体类类类型
     */
    private Class<?> classType;

    /**
     * 列表转换类类型
     */
    private Type listType;

    /**
     * 获取列表回调.
     */
    private GetListListener<T> mListListener;

    /**
     * 获取列表回调.
     */
    private GetListWithTotalListener<T> mListWithTotalListener;

    /**
     *
     */
    private String mMethodUrl;

    private boolean hasDialog = true;


    private GetListStringListener stringListener;

    /**
     * @param context 上下文参数
     * @return
     */
    @Override
    public CommonModel setContext(final Context context) {
        mContext = context;
        return this;
    }

    /**
     * @param map 参数map
     * @return
     */
    @Override
    public CommonModel setParamMap(final Map<String, Object> map) {
        paramMap = map;
        return this;
    }

    /**
     * @return
     */
    @Override
    public CommonModel setStringListener(final GetListStringListener listener) {
        stringListener = listener;
        return this;
    }
    /**
     * @param listener 列表监听
     * @return
     */
    @Override
    public CommonModel setListListener(final GetListListener<T> listener) {
        mListListener = listener;
        return this;
    }

    /**
     * @param listener 列表监听
     * @return
     */
    @Override
    public CommonModel setListListenerWithTotal(final GetListWithTotalListener<T> listener) {
        mListWithTotalListener = listener;
        return this;
    }

    /**
     * @param listener 返回监听
     * @return
     */
    @Override
    public CommonModel setEntityListener(final GetEntityListener<T> listener) {
        mEntityListener = listener;
        return this;
    }

    /**
     * @param url 后台地址.
     * @return
     */
    @Override
    public CommonModel setUrl(final String url) {
        mMethodUrl = url;
        return this;
    }

    /**
     * @return
     */
    @Override
    public CommonModel getList() {
        AddParameters addParameters = new AddParameters();
        addParameters.addParam(paramMap);
        OkHttpClientManager okHttpClientManager = new OkHttpClientManager()
                .setUrl(mMethodUrl + addParameters.formGet())
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(final String result) {
                        Log.d("曲线查询", "返回:"+result);
                        try {
                            if (result.contains("items")) {
                                JSONObject object = new JSONObject(result);
                                String items = object.optString("items");
                                List<T> list = new Gson().fromJson(items, listType);
                                mListListener.success(list);
                            } else {
                                List<T> list = new Gson().fromJson(result, listType);
                                mListListener.success(list);
                            }
                        } catch (JSONException e) {
                            Log.d("曲线查询", "失败:"+result);
                            AppApplication.getInstance().finishAllActivity();
                        }
                    }

                    @Override
                    public void onErrorResponse(final Response response) {
                        if (errorInterface != null) {
                            errorInterface.error(response);
                        }
                    }
                });
        if (hasDialog) {
            okHttpClientManager.setLoadDialog(new ProgressBarDialog(mContext));
        }
        okHttpClientManager.builder();
        return this;
    }


    /**
     * @return
     */
    @Override
    public CommonModel getString() {
        AddParameters addParameters = new AddParameters();
        addParameters.addParam(paramMap);
        OkHttpClientManager okHttpClientManager = new OkHttpClientManager()
                .setUrl(mMethodUrl + addParameters.formGet())
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {

                    @Override
                    public void onNetworkResponse(final String result) {

                            if (result.contains("items")) {
//                                JSONObject object = new JSONObject(result);
//                                String items = object.optString("items");
//                                List<T> list = new Gson().fromJson(items, listType);
//                                GetListStringListener
                                stringListener.success(result);
                            } else {
//                                List<T> list = new Gson().fromJson(result, listType);
                                stringListener.success(result);
                            }
                    }

                    @Override
                    public void onErrorResponse(final Response response) {
                        if (errorInterface != null) {
                            errorInterface.error(response);
                        }
                    }
                });
        if (hasDialog) {
            okHttpClientManager.setLoadDialog(new ProgressBarDialog(mContext));
        }
        okHttpClientManager.builder();
        return this;
    }



    /**
     * @return
     */
    @Override
    public CommonModel submit(String object) {
        AddParameters addParameters = new AddParameters();
        addParameters.addParam(paramMap);
        OkHttpClientManager okHttpClientManager = new OkHttpClientManager()
                .setUrl(mMethodUrl)
                .setRequestMethod(LibConfig.HTTP_POST)
                .setRequestBody(addParameters.formBodyByString(object))
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {

                    @Override
                    public void onNetworkResponse(final String result) {

                        if (result.contains("items")) {
//                                JSONObject object = new JSONObject(result);
//                                String items = object.optString("items");
//                                List<T> list = new Gson().fromJson(items, listType);
//                                GetListStringListener
                            stringListener.success(result);
                        } else {
//                                List<T> list = new Gson().fromJson(result, listType);
                            stringListener.success(result);
                        }
                    }

                    @Override
                    public void onErrorResponse(final Response response) {
                        if (errorInterface != null) {
                            errorInterface.error(response);
                        }
                    }
                });
        if (hasDialog) {
            okHttpClientManager.setLoadDialog(new ProgressBarDialog(mContext));
        }
        okHttpClientManager.builder();
        return this;
    }


    /**
     * @return
     */
    @Override
    public CommonModel getListWithTotal() {
        AddParameters addParameters = new AddParameters();
        addParameters.addParam(paramMap);
        OkHttpClientManager okHttpClientManager = new OkHttpClientManager()
                .setUrl(mMethodUrl + addParameters.formGet())
                .setRequestBody(addParameters.formBody())
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {

                    @Override
                    public void onNetworkResponse(final String result) {
                        try {
                            if (result.contains("items")) {
                                JSONObject object = new JSONObject(result);
                                String items = object.optString("items");
                                List<T> list = new Gson().fromJson(items, listType);
                                int total = 0;
                                if (object.has("total")) {
                                    total = object.getInt("total");
                                }
                                mListWithTotalListener.success(list, total);
                            } else {
                                List<T> list = new Gson().fromJson(result, listType);
                                mListWithTotalListener.success(list, 0);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(final Response response) {

                    }
                });
        if (hasDialog) {
            okHttpClientManager.setLoadDialog(new ProgressBarDialog(mContext));
        }
        okHttpClientManager.builder();
        return this;
    }

    /**
     * @param type 类类型
     * @return
     */
    public CommonModel setEntityType(final Class<?> type) {
        classType = type;
        return this;
    }

    /**
     * @param type
     * @return
     */
    @Override
    public CommonModel setListType(final Type type) {
        listType = type;
        return this;
    }

    /**
     * @return
     */
    @Override
    public CommonModel putEntity() {
        AddParameters addParameters = new AddParameters();
        addParameters.addParam(paramMap);
        OkHttpClientManager okHttpClientManager = new OkHttpClientManager()
                .setUrl(mMethodUrl + addParameters.formGet())
                .setRequestMethod(LibConfig.HTTP_PUT)
                .setRequestBody(addParameters.formBody())
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(final String result) {
                        try {
                            JSONObject object = new JSONObject(result);
                            if (object.has("count")) {
                                mEntityListener.success((T) object.get("count"));
                            } else {
                                mEntityListener.success(null);
                            }
                        } catch (Exception e) {
                            mEntityListener.success(null);
                        }
//                        mEntityListener.success(entity);
                    }

                    @Override
                    public void onErrorResponse(final Response response) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("result", response.body().string());
                                        Message message = new Message();
                                        message.setData(bundle);
                                        handler.sendMessage(message);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }).start();
                            ;
                    }
                });
        if (hasDialog) {
            okHttpClientManager.setLoadDialog(new ProgressBarDialog(mContext));
        }
        okHttpClientManager.builder();
        return this;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String result = bundle.getString("result");
            mEntityListener.fail(result);
        }
    };


    /**
     * @return
     */
    @Override
    public CommonModel postEntity() {
        AddParameters addParameters = new AddParameters();
        addParameters.addParam(paramMap);
        OkHttpClientManager okHttpClientManager = new OkHttpClientManager()
                .setUrl(mMethodUrl + addParameters.formGet())
                .setRequestMethod(LibConfig.HTTP_POST)
                .setRequestBody(addParameters.formBody())
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(final String result) {
                        try {
                            JSONObject object = new JSONObject(result);
                            if (object.has("id")) {
                                mEntityListener.success((T) object.get("id"));
                            } else {
                                mEntityListener.success(null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onErrorResponse(final Response response) {
                        mEntityListener.fail("");
                    }
                });
        if (hasDialog) {
            okHttpClientManager.setLoadDialog(new ProgressBarDialog(mContext));
        }
        okHttpClientManager.builder();
        return this;
    }

    /**
     * @return
     */
    @Override
    public CommonModel postEntityWithWholeUrl() {
        AddParameters addParameters = new AddParameters();
        addParameters.addParam(paramMap);
        OkHttpClientManager okHttpClientManager = new OkHttpClientManager()
                .setWholeUrl(mMethodUrl + addParameters.formGet())
                .setRequestMethod(LibConfig.HTTP_POST)
                .setRequestBody(addParameters.formBodyWithData())
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(final String result) {
                        try {
                            JSONObject object = new JSONObject(result);
                            if (object.has("id")) {
                                mEntityListener.success((T) object.get("id"));
                            } else if (classType != null) {
                                T entity = (T) new Gson().fromJson(result, classType);
                                mEntityListener.success(entity);
                            } else {
                                mEntityListener.success(null);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onErrorResponse(final Response response) {
                        mEntityListener.fail("");
                    }
                });
        if (hasDialog) {
            okHttpClientManager.setLoadDialog(new ProgressBarDialog(mContext));
        }
        okHttpClientManager.builder();
        return this;
    }

    /**
     * @return
     */
    @Override
    public CommonModel getEntity() {
        AddParameters addParameters = new AddParameters();
        addParameters.addParam(paramMap);
        OkHttpClientManager okHttpClientManager = new OkHttpClientManager()
                .setUrl(mMethodUrl + addParameters.formGet())
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(String result) {
                        try {
                            if (result.contains("items")) {
                                JSONObject object = new JSONObject(result);
                                result = object.getString("items");
                            }
                            if (classType != null) {
                                T entity = (T) new Gson().fromJson(result, classType);
                                mEntityListener.success(entity);
                            } else {
                                T entity = (T) result;
                                mEntityListener.success(entity);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(final Response response) {
                    }
                });
        if (hasDialog) {
            okHttpClientManager.setLoadDialog(new ProgressBarDialog(mContext));
        }
        okHttpClientManager.builder();
        return this;
    }

    /**
     * @return
     */
    @Override
    public CommonModel getEntityNew() {
        AddParameters addParameters = new AddParameters();
        addParameters.addParam(paramMap);
        OkHttpClientManager okHttpClientManager = new OkHttpClientManager()
                .setUrl(mMethodUrl + addParameters.formGet())
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(String result) {
                        try {
                            if (classType != null) {
                                T entity = (T) new Gson().fromJson(result, classType);
                                mEntityListener.success(entity);
                            } else {
                                T entity = (T) result;
                                mEntityListener.success(entity);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(final Response response) {
                        if (mEntityListener != null) {
                            mEntityListener.fail(null);
                        }
                    }
                });
        if (hasDialog) {
            okHttpClientManager.setLoadDialog(new ProgressBarDialog(mContext));
        }
        okHttpClientManager.builder();
        return this;
    }


    public boolean isHasDialog() {
        return hasDialog;
    }

    public CommonModel setHasDialog(boolean hasDialog) {
        this.hasDialog = hasDialog;
        return this;
    }

    @Override
    public CommonModel setErrorInterface(ErrorInterface errorInterface) {
        this.errorInterface = errorInterface;
        return this;
    }


    public interface ErrorInterface {
        void error(Response response);
    }

    private ErrorInterface errorInterface;


}
