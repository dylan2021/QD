package com.haocang.fault.post.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.bean.EquimentEntity;
import com.haocang.base.bean.FileEntity;
import com.haocang.base.bean.LabelEntity;
import com.haocang.base.utils.EquipmentModelUtil;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.utils.UploadUtil;
import com.haocang.fault.constants.FaultMethod;
import com.haocang.fault.post.iview.PostView;
import com.haocang.fault.post.presenter.PostPresenter;
import com.haocang.fault.post.ui.PostFaultFragment;
import com.haocang.offline.util.OffLineOutApiUtil;

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
 * 创建时间：2018/4/2514:13
 * 修 改 者：
 * 修改时间：
 */
public class PostPresenterImpl implements PostPresenter, UploadUtil.UploadSuccess, GetEntityListener<String> {
    private PostView postView;
//    private PostModel postModel;

    public PostPresenterImpl(PostView postView) {
        this.postView = postView;
//        postModel = new PostModelImpl();
    }

    /**
     *
     */
    @Override
    public void submit() {
        if (OffLineOutApiUtil.isNetWork()) {
            if (isEmpty()){
                dialog();
            }
            return;
        }
        if (postView.getfileList() != null && postView.getfileList().size() > 0 && isEmpty()) {
            upLoadFile();
        } else if (isEmpty()) {
            postFault();
        }
    }

    private void postFault() {

        createFault(postView.getParameter());

    }

    private void createFault(Map<String, Object> map) {
        CommonModel<Integer> progressModel
                = new CommonModelImpl<>();
        progressModel
                .setContext(postView.getContexts())
                .setParamMap(postView.getParameter())
                .setUrl(FaultMethod.FAULT_ADD)
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(Integer id) {
                        postView.createSuccess(id + "");
                    }

                    @Override
                    public void fail(String err) {

                    }
                }).postEntity();
    }

    /**
     * @param id 根据设备id获取设备台账详情信息
     */
    @Override
    public void getEquipmentData(String id) {
        if (OffLineOutApiUtil.isOffLine()) {
            LabelEntity labelEntity = OffLineOutApiUtil.getEquipmentData(Integer.parseInt(id));
            if (labelEntity == null) {
                ToastUtil.makeText(postView.getContexts(), "未找到相关设备");
                return;
            }
            postView.setEquipmentId((int) labelEntity.getId());
            postView.setEquipmentName(labelEntity.getLabel());
            postView.setProcessId(labelEntity.getProcessId());
            postView.setProcessName(labelEntity.getProcessName());
            return;
        }
        final Context ctx = postView.getContexts();
        EquipmentModelUtil.getDetailsData(ctx, id, new GetEntityListener<EquimentEntity>() {
            @Override
            public void success(EquimentEntity entity) {
                postView.setEquipmentId(entity.getId());
                postView.setEquipmentName(entity.getName());
                postView.setProcessId(entity.getProcessId());
                postView.setProcessName(entity.getProcessName());
            }

            @Override
            public void fail(String err) {
                ToastUtil.makeText(ctx, err);
            }
        });
    }


    private void upLoadFile() {
        new UploadUtil(postView.getContexts()).setUploadData(postView.getfileList()).setUploadSuccess(this).startUploadMultipleFile();
    }

    private boolean isEmpty() {
        Context ctx = postView.getContexts();
        Map<String, Object> map = postView.getParameter();
        int faultTypeId = map.get("faultTypeId") != null ? (int) map.get("faultTypeId") : -1;
        int equiId = map.get("equId") != null ? (int) map.get("equId") : -1;
        int severityType = map.get("severityType") != null ? (int) map.get("severityType") : -1;
        int processingPersonId = map.get("processingPersonId") != null ? (int) map.get("processingPersonId") : -1;
        int processPostion = -1;
        if (map.get("processId") != null) {
            String processId = map.get("processId").toString();
            processPostion = map.get("processId") != null ? Integer.parseInt(processId) : -1;
        }
        String remark = map.get("remark") != null ? map.get("remark").toString() : "";
        if (faultTypeId < 0) {
            ToastUtil.makeText(ctx, "请选择一个缺陷类型");
            return false;
        } else if (severityType < 0) {
            ToastUtil.makeText(ctx, "请选择严重程度");
            return false;
        } else if (processPostion < 0) {
            ToastUtil.makeText(ctx, "请选择区域位置");
            return false;
        }
//        else if (OffLineOutApiUtil.isNetWork() && TextUtils.isEmpty(remark)) {
//            ToastUtil.makeText(ctx, "请填写备注");
//            return false;
//        }
        else {
            return true;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (PostFaultFragment.REQUESTCODE == requestCode && data != null) {
            String videoPath = data.getStringExtra("videoPath");
            byte[] videoThubnail = data.getByteArrayExtra("videoThumbnail");
            String picturePath = data.getStringExtra("picturePath");
        }
    }

    @Override
    public void uploadSuccess(List<FileEntity> fileList) {
        String imgUrl = "";
        String imgThumbnailUrl = "";
        for (FileEntity file : fileList) {
            imgUrl = imgUrl + file.getFullPath() + ",";
            imgThumbnailUrl = imgThumbnailUrl + file.getThumbFullPath() + ",";
        }
        if (!TextUtils.isEmpty(imgUrl)) {
            imgUrl = imgUrl.substring(0, imgUrl.length() - 1);
            imgThumbnailUrl = imgThumbnailUrl.substring(0, imgThumbnailUrl.length() - 1);
        }
        Map<String, Object> map = postView.getParameter();
        map.put("imgUrl", imgUrl);
        map.put("imgThumbnailUrl", imgThumbnailUrl);
        createFault(map);
//        postModel.submit(postView.getContexts(), map, this);
    }

    @Override
    public void uploadError() {
        ToastUtil.makeText(postView.getContexts(), "上传图片失败");
        createFault(postView.getParameter());
//        postModel.submit(postView.getContexts(), postView.getParameter(), this);
    }


    @Override
    public void fail(String err) {

    }


    @Override
    public void success(String entity) {
        postView.createSuccess(entity);
    }


    private void dialog() {
        OffLineOutApiUtil.preservation(postView.getContexts(), postView.getParameter(), postView.getfileList(), new GetEntityListener<String>() {
            @Override
            public void success(String entity) {
                postView.setOffLineSuccess();
            }

            @Override
            public void fail(String err) {

            }
        });
    }


}
