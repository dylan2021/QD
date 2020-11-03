package com.haocang.fault.list.presenter.impl;

import android.text.TextUtils;

import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.bean.FileEntity;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.utils.UploadUtil;
import com.haocang.fault.R;
import com.haocang.fault.constants.FaultMethod;
import com.haocang.fault.list.iview.FaultProcessingResultView;
import com.haocang.fault.list.presenter.FaultProcessingResultPresenter;

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
 * 创建时间：2018/5/109:43
 * 修 改 者：
 * 修改时间：
 */
public class FaultProcessingResultPresenterImpl implements FaultProcessingResultPresenter, UploadUtil.UploadSuccess {
    private FaultProcessingResultView faultProcessingResultView;

    public FaultProcessingResultPresenterImpl(FaultProcessingResultView faultProcessingResultView) {
        this.faultProcessingResultView = faultProcessingResultView;
    }

    private void upLoadFile() {
        new UploadUtil(faultProcessingResultView.getContexts()).setUploadData(faultProcessingResultView.getfileList()).setUploadSuccess(this).startUploadMultipleFile();
    }

    @Override
    public void submitResults() {
        if (faultProcessingResultView.getfileList() != null && faultProcessingResultView.getfileList().size() > 0 && isEmpty()) {
            upLoadFile();
        } else if (isEmpty()) {
            addParameter();
        }
    }

    private boolean isEmpty() {
        int faultRecordId = faultProcessingResultView.getProcessingResult();//处理结果
        int faultCloseReason = faultProcessingResultView.getFaultCloseReason();//关闭
        int hangupReason = faultProcessingResultView.getHangupReason();//挂起
        if (faultRecordId < 0) {
            faultProcessingResultView.resultEmpty();
            return false;
        } else if (faultRecordId == 2 && hangupReason < 0) {
            //挂起
            faultProcessingResultView.setCloseReasonOrHangUp("挂起", faultRecordId);
            return false;
        } else if (faultRecordId == 3 && faultCloseReason < 0) {
            //关闭
            faultProcessingResultView.setCloseReasonOrHangUp("关闭", faultRecordId);
            return false;
        }
        return true;
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
        String netWorkPath = faultProcessingResultView.getNetWorkList();//网络图片或者视频
        if (!TextUtils.isEmpty(netWorkPath)) {
            imgUrl = netWorkPath + "," + imgUrl;
            imgThumbnailUrl = faultProcessingResultView.getNetWordThumbnailUrl() + "," + imgThumbnailUrl;
        }
        addParameter(imgUrl, imgThumbnailUrl);
    }

    @Override
    public void uploadError() {
        addParameter();
    }

    /**
     * 添加参数
     */
    private void addParameter() {
        String netWorkPath = faultProcessingResultView.getNetWorkList();//网络图片或者视频
        Map<String, Object> map = getParameter(netWorkPath, null);
        submitResult(map);
    }

    /**
     * @param imgUrl          图片地址.
     * @param imgThumbnailUrl 缩略图地址
     */
    private void addParameter(final String imgUrl, final String imgThumbnailUrl) {
        Map<String, Object> map = getParameter(imgUrl, imgThumbnailUrl);
        submitResult(map);
    }

    private void submitResult(Map<String, Object> map) {
        CommonModel<Integer> progressModel
                = new CommonModelImpl<>();
        progressModel
                .setContext(faultProcessingResultView.getContexts())
                .setParamMap(map)
                .setUrl(FaultMethod.FAULT_CONTRAIL)
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(Integer entity) {
                        faultProcessingResultView.submitSuccess();
                    }

                    @Override
                    public void fail(String err) {
                        ToastUtil.makeText(faultProcessingResultView.getContexts(),
                                faultProcessingResultView.getContexts().getString(R.string.fault_upload_failed));
                    }
                }).putEntity();
    }

    private Map<String, Object> getParameter(final String imgUrl, final String imgThumbnailUrl) {
        int faultCloseReason = faultProcessingResultView.getFaultCloseReason();
        String faultReason = faultProcessingResultView.getFaultReason();
        int faultRecordId = faultProcessingResultView.getFaultRecordId();
        int hangupReason = faultProcessingResultView.getHangupReason();
        int processingResult = faultProcessingResultView.getProcessingResult();
        String remark = faultProcessingResultView.getRemarks();
        Map<String, Object> map = new HashMap<>();
        map.put("faultReason", faultReason);
        map.put("faultRecordId", faultRecordId);
        if (imgThumbnailUrl != null) {
            map.put("imgThumbnailUrl", imgThumbnailUrl);
        }
        if (imgUrl != null) {
            map.put("imgUrl", imgUrl);
        }
        if (faultCloseReason > 0) {
            map.put("faultCloseReason", faultCloseReason);
        }
        if (hangupReason > 0) {
            map.put("hangupReason", hangupReason);
        }
        map.put("processingResult", processingResult);
        map.put("remark", remark);
        map.put("finishTime", faultProcessingResultView.getCompleTime());
        return map;
    }

}
