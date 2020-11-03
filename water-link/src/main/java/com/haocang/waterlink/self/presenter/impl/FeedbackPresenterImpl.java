package com.haocang.waterlink.self.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.bean.FileEntity;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.utils.UploadUtil;
import com.haocang.waterlink.self.iview.FeedBackView;
import com.haocang.waterlink.self.presenter.FeedbackPresenter;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/3/2716:48
 * 修 改 者：
 * 修改时间：
 */
public class FeedbackPresenterImpl implements FeedbackPresenter, UploadUtil.UploadSuccess {
    /**
     * 为P层提供数据.
     */
    private FeedBackView feedBackView;
    private Map<String, Object> map = new HashMap<>();
//    private JSONObject jsonObject = new JSONObject();

    public FeedbackPresenterImpl(final FeedBackView feedBackView) {
        this.feedBackView = feedBackView;
    }


    @Override
    public void onActivityResult(final int requestCode,
                                 final int resultCode,
                                 final Intent data) {

    }

    @Override
    public void submit() {
        Context ctx = feedBackView.getContexts();
        int type = feedBackView.getType();
        String content = feedBackView.getContent();
        List<String> list = feedBackView.getFileList();
        String name = feedBackView.getName();
        String phone = feedBackView.getPhone();
        map.put("content", content);
        map.put("name", name);
        map.put("phone", phone);
        map.put("type", type);
//        try {
//            jsonObject.put("content", content);
//            jsonObject.put("name", name);
//            jsonObject.put("phone", phone);
//            jsonObject.put("type", type);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        if (list != null && list.size() > 0 && isEmpty(name, phone)) {
            upLoadFile();
        } else if (isEmpty(name, phone)) {
//            feedbackModel.submit(ctx, map, listener);

//            feedbackModel.submitJson(ctx, jsonObject, listener);
            postFeedback(map);

        }

    }

    private void postFeedback(Map<String, Object> map) {
        CommonModel<String> model = new CommonModelImpl<>();
        model.setContext(feedBackView.getContexts())
                .setParamMap(map)
                .setEntityType(Integer.class)
                .setUrl(MethodConstants.Base.BASE_FEEDBACK )
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(final Integer entity) {
                        feedBackView.submitSuccess();
                    }

                    @Override
                    public void fail(final String err) {

                    }
                })
                .postEntity();
    }


    private void upLoadFile() {
        new UploadUtil(feedBackView.getContexts()).setUploadData(feedBackView.getFileList()).setUploadSuccess(this).startUploadMultipleFile();
    }

    private boolean isEmpty(final String name, final String phone) {
        Context ctx = feedBackView.getContexts();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.makeText(ctx, "请输入您的姓名");
            return false;
        } else if (TextUtils.isEmpty(phone)) {
            ToastUtil.makeText(ctx, "请输入您的手机号码");
            return false;
        } else if (TextUtils.isEmpty(feedBackView.getContent())) {
            ToastUtil.makeText(ctx, "请输入反馈内容");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void uploadSuccess(final List<FileEntity> fileList) {
        String imgUrl = "";
//        String imgThumbnailUrl = "";
        for (FileEntity file : fileList) {
            imgUrl = imgUrl + file.getPath() + ",";
//            imgThumbnailUrl = imgThumbnailUrl + file.getThumbFullPath() + ",";
        }
        if (!TextUtils.isEmpty(imgUrl)) {
            imgUrl = imgUrl.substring(0, imgUrl.length() - 1);
//            imgThumbnailUrl = imgThumbnailUrl.substring(0, imgThumbnailUrl.length() - 1);
        }
//        String[] sr = new String[fileList.size()];
//        for (int i = 0; i < fileList.size(); i++) {
//            sr[i] = fileList.get(i).getPath();
//        }
//        map.put("attachments", listSr.toArray());
//        try {
//            jsonObject.put("attachments", imgUrl);
//            jsonObject.put("platform", "APP");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Map<String,Object> map = new HashMap<>();
        map.put("attachments",imgUrl);
        map.put("platform", "APP");
        postFeedback(map);
//        feedbackModel.submitJson(feedBackView.getContexts(), jsonObject, listener);
    }

    @Override
    public void uploadError() {
        ToastUtil.makeText(feedBackView.getContexts(), "图片上传失败");
        postFeedback(map);
//        feedbackModel.submit(feedBackView.getContexts(), map, listener);
    }
}
