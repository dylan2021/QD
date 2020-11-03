package com.haocang.base.utils;

import android.content.Context;

import com.haocang.base.bean.FileEntity;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * 上传带水印带文件
 */
public class UploadWaterFileUtil implements OkHttpClientManager.OnNetworkResponse {
    private Context mCtx;
    private List<String> list;
    private String waterMarkStr;
    public UploadWaterSuccess uploadSuccess;
    private Object tag;
    private List<FileEntity> fileList = new ArrayList<>();//返回文件路径.

    public UploadWaterFileUtil(Context mCtx) {
        this.mCtx = mCtx;
    }

    public UploadWaterFileUtil setUploadData(List<String> list) {
        this.list = list;
        return this;
    }

    public UploadWaterFileUtil setUploadSuccess(UploadWaterSuccess uploadSuccess) {
        this.uploadSuccess = uploadSuccess;
        return this;
    }

    public UploadWaterFileUtil setWaterMark(String waterMarkStr) {
        this.waterMarkStr = waterMarkStr;
        return this;
    }

    public UploadWaterFileUtil setTag(Object tag) {
        this.tag = tag;
        return this;
    }


    public void startUpLoadFile() {
        for (String path : list) {
            AddParameters addParameters = new AddParameters();
            Map<String, Object> map = new HashMap<>();
            File file = new File(path);
            map.put("file", file);
            map.put("waterMarkStr", waterMarkStr);
            addParameters.addParam(map);
            new OkHttpClientManager()
                    .setLoadDialog(new ProgressBarDialog(mCtx))
                    .setOnNetWorkReponse(this)
                    .setUrl(MethodConstants.Equiment.BASE_FILES_UPLOAD)//+ "?waterMarkStr=" + waterMarkStr
                    .setRequestMethod(LibConfig.HTTP_POST)
                    .setRequestBody(addParameters.formBodyWithData())
                    .builder();
        }

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
            if (fileList.size() == list.size()) {
                if (tag != null) {
                    uploadSuccess.uploadSuccess(fileList, tag);
                } else {
                    uploadSuccess.uploadSuccess(fileList);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(Response response) {
        FileEntity fileEntity = new FileEntity();
        fileList.add(fileEntity);
        if (fileList.size() == list.size()) {
            if (tag != null) {
                uploadSuccess.uploadSuccess(fileList, tag);
            } else {
                uploadSuccess.uploadSuccess(fileList);
            }
        }
    }


    public interface UploadWaterSuccess {
        /**
         * 上传文件成功
         */
        void uploadSuccess(List<FileEntity> fileList);

        void uploadSuccess(List<FileEntity> fileList, Object tag);

        /**
         * 上传文件失败
         */
        void uploadError();
    }

}
