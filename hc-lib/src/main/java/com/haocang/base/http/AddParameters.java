package com.haocang.base.http;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题： 发起网络请求的时候需要填的参数
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/1/814:37
 * 修 改 者：
 * 修改时间：
 */
public class AddParameters {


    /**
     * map参数.
     */
    private Map<String, Object> param = new HashMap<String, Object>();

    /**
     * 参数json类型.
     */
    private MediaType jsonType = MediaType
            .parse("application/json");

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");

    /**
     * @param key   参数名称
     * @param value 参数值
     */
    public void addParam(final String key, final Object value) {
        param.put(key, value);
    }

    public void addParam(final Map<String, Object> map) {
        if (map != null)
            param.putAll(map);
    }


    /**
     * @return 上传文件添加
     */
    public MultipartBody formUploadBody() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Map.Entry<String, Object> map : param.entrySet()) {
            File file = (File) map.getValue();
            builder.addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
        }
        MultipartBody requestBody = builder.build();
        return requestBody;
    }

    public MultipartBody formBodyWithData() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (Map.Entry<String, Object> map : param.entrySet()) {
            if (map.getValue() instanceof File) {
                File file = (File) map.getValue();
                builder.addFormDataPart(map.getKey(), file.getName(), RequestBody.create(MEDIA_TYPE_JPG, file));
            } else {
                if (map.getValue() != null) {
                    builder.addFormDataPart(map.getKey(), map.getValue() + "");
                }
            }

        }
        MultipartBody requestBody = builder.build();
        return requestBody;
    }

    /**
     * @return post中添加参数
     */
    public RequestBody formBody() {
        JSONObject object = new JSONObject();
        try {
            for (Map.Entry<String, Object> map : param.entrySet()) {
                object.put(map.getKey(), map.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody formBody = RequestBody.create(jsonType, object.toString());
        return formBody;
    }

    public RequestBody formBodyByArray(final JSONArray array) {
        RequestBody formBody = RequestBody.create(jsonType, array.toString());
        return formBody;
    }


    public String formGet() {
        String result = "";
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value != null && !"".equals(value)) {
                result += key + "=" + value + "&";
            }
        }
        if(!result.equals("")){
            result = "?"+result;
        }
        return result;
    }

    public RequestBody formBodyByObject(final JSONObject object) {
        RequestBody formBody = RequestBody.create(jsonType, object.toString());
        return formBody;
    }

    public RequestBody formBodyByString(final String object) {
        RequestBody formBody = RequestBody.create(jsonType, object);
        return formBody;
    }
}
