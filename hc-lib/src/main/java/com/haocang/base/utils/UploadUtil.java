package com.haocang.base.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.bean.FileEntity;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：上传图片.
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/2810:46
 * 修 改 者：
 * 修改时间：
 */
public class UploadUtil implements OkHttpClientManager.OnNetworkResponse {

    private Context mCtx;
    private List<String> list;
    public UploadSuccess uploadSuccess;

    private List<FileEntity> fileList = new ArrayList<>();//返回文件路径.

    public UploadUtil(Context mCtx) {
        this.mCtx = mCtx;
    }

    public UploadUtil setUploadData(List<String> list) {
        this.list = list;
        return this;
    }

    public UploadUtil setUploadSuccess(UploadSuccess uploadSuccess) {
        this.uploadSuccess = uploadSuccess;
        return this;
    }

    /**
     * 上传文件不过是通过遍历list,多次调用上传接口， 推荐用下面的 上传多个文件
     */
    public void startUploadFile() {
        if (list != null && list.size() > 0) {
            upLoadFile();
        } else {
            uploadSuccess.uploadSuccess(fileList);
        }
    }

    /**
     * 上传文件不过是通过遍历list,多次调用上传接口， 推荐用下面的 上传多个文件
     */
    public void startUploadFileEX() {
        if (list != null && list.size() > 0) {
            upLoadFileEX();
        } else {
            uploadSuccess.uploadSuccess(fileList);
        }
    }

    /**
     * 上传多个文件
     */
    public void startUploadMultipleFile() {
        AddParameters addParameters = new AddParameters();
        Map<String, Object> map = new HashMap<>();
        for (String path : list) {
            File file = new File(path);
            map.put(file.getName(), file);
        }
        addParameters.addParam(map);
        new OkHttpClientManager()
                .setLoadDialog(new ProgressBarDialog(mCtx))
                .setOnNetWorkReponse(multipleOnNetworkResponse)
                .setUrl(MethodConstants.Equiment.BASE_BATCH_UPLOAD)
                .setRequestMethod(LibConfig.HTTP_POST)
                .setRequestBody(addParameters.formUploadBody())
                .builder();
    }

    /**
     * 上传多个文件
     */
    OkHttpClientManager.OnNetworkResponse multipleOnNetworkResponse = new OkHttpClientManager.OnNetworkResponse() {
        @Override
        public void onNetworkResponse(String result) {
            Type type = new TypeToken<List<FileEntity>>() {
            }.getType();
            List<FileEntity> list = new Gson().fromJson(result, type);
            uploadSuccess.uploadSuccess(list);
        }

        @Override
        public void onErrorResponse(Response response) {

        }
    };


    private void upLoadFileEX() {
        AddParameters para = new AddParameters();
        Map<String, Object> map = new HashMap<>();

        String pathName = list.get(0);
        list.remove(0);
        //后台请求来的
        if (pathName.startsWith("http")) {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFullPath(pathName);
            fileList.add(fileEntity);
            startUploadFileEX();
            return;
        } else {//本地上传
            File file = new File(pathName);
            map.put("file", file);
            para.addParam(map);
        }
        Log.d("图片上传", "文件:" + map.toString());
        new OkHttpClientManager()
                .setLoadDialog(new ProgressBarDialog(mCtx))
                .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                    @Override
                    public void onNetworkResponse(String result) {
                        Log.d("图片上传", "上传返回: " + result.toString());
                        try {
/*                          "name" : "S@3T@QKBKODWAIPX4N2~FIO.png",
                            "size" : 60638,
                            "path" : "group1/M00/00/0C/wKgB6V-jn6SAQ4fZAADs3uy_8Rs241.png",
                             "fullPath" : "http://www.qdhsdd.com:18188/group1/M00/00/0C/wKgB6V-jn6SAQ4fZAADs3uy_8Rs241.png",
                            "thumbPath" : "group1/M00/00/0D/wKgB6V-jn6SAYlUNAAAS_EDLMdw510.png",
                             "thumbFullPath" : "http://www.qdhsdd.com:18188/group1/M00/00/0D/wKgB6V-jn6SAYlUNAAAS_EDLMdw510.png"*/
                            JSONObject object = new JSONObject(result);
                            FileEntity fileEntity = new FileEntity();
                            fileEntity.setFullPath(object.optString("fullPath"));
                            fileEntity.setName(object.optString("name"));
                            fileEntity.setPath(object.optString("path"));
                            fileEntity.setSize(object.optInt("size"));
                            if (!object.isNull("thumbPath")) {
                                fileEntity.setThumbPath(object.getString("thumbPath"));
                            } else {
                                fileEntity.setThumbPath("");
                            }
                            if (!object.isNull("thumbFullPath")) {
                                fileEntity.setThumbFullPath(object.getString("thumbFullPath"));
                            } else {
                                fileEntity.setThumbFullPath("");
                            }
                            fileList.add(fileEntity);
                            startUploadFileEX();
                        } catch (JSONException e) {
                            uploadSuccess.uploadError();
                            Log.d("图片上传", "失败: " + e.toString());
                        }
                    }

                    @Override
                    public void onErrorResponse(Response response) {
                        Log.d("图片上传", "失败2: " + response.toString());
                        uploadSuccess.uploadError();
                    }
                })
                .setUrl(MethodConstants.Equiment.FILE_UPLOAD_EX)
                .setRequestMethod(LibConfig.HTTP_POST)
                .setRequestBody(para.formUploadBody())
                .builder();
    }

    private void upLoadFile() {
        AddParameters addParameters = new AddParameters();
        Map<String, Object> map = new HashMap<>();
        File file = new File(list.get(0));
        map.put("file", file);
        addParameters.addParam(map);
        list.remove(0);
        new OkHttpClientManager()
                .setLoadDialog(new ProgressBarDialog(mCtx))
                .setOnNetWorkReponse(this)
                .setUrl(MethodConstants.Equiment.FILE_UPLOAD)
                .setRequestMethod(LibConfig.HTTP_POST)
                .setRequestBody(addParameters.formUploadBody())
                .builder();
    }


    @Override
    public void onNetworkResponse(String result) {
        try {
            JSONObject object = new JSONObject(result);
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFullPath(object.optString("fullPath"));
            fileEntity.setName(object.optString("name"));
            fileEntity.setPath(object.optString("path"));
            fileEntity.setSize(object.optInt("size"));
            if (!object.isNull("thumbPath")) {
                fileEntity.setThumbPath(object.getString("thumbPath"));
            } else {
                fileEntity.setThumbPath("");
            }
            if (!object.isNull("thumbFullPath")) {
                fileEntity.setThumbFullPath(object.getString("thumbFullPath"));
            } else {
                fileEntity.setThumbFullPath("");
            }
            fileList.add(fileEntity);
            startUploadFile();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(Response response) {
        uploadSuccess.uploadError();

    }


    public interface UploadSuccess {
        /**
         * 上传文件成功
         */
        void uploadSuccess(List<FileEntity> fileList);

        /**
         * 上传文件失败
         */
        void uploadError();

    }


}
