package com.haocang.repair.post.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.bean.FileEntity;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.utils.UploadUtil;
import com.haocang.repair.R;
import com.haocang.repair.constants.RepairMethod;
import com.haocang.repair.manage.bean.RepairConstans;
import com.haocang.repair.manage.bean.RepairRecordVo;
import com.haocang.repair.post.bean.RepairRecordDto;
import com.haocang.repair.post.iview.PostRepairResultView;
import com.haocang.repair.post.presenter.PostRepairResultPresenter;
import com.haocang.repair.post.widgets.PickReasonDialog;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 创建时间：2018/5/14下午6:11
 * 修  改  者：
 * 修改时间：
 */
public class RepairPostResultPresenterImpl implements PostRepairResultPresenter, UploadUtil.UploadSuccess {

    private PostRepairResultView postRepairResultView;
    private Map<String, Object> paramMap = new HashMap<>();
    /**
     *
     */
    private RepairRecordVo mRecordVo;

    /**
     * @param view
     */
    @Override
    public void setView(final PostRepairResultView view) {
        postRepairResultView = view;
        mRecordVo = new RepairRecordVo();
    }

    /**
     *
     */
    @Override
    public void showPickResultView() {
        String[] repairResultArr = getContext().getResources().getStringArray(R.array.repair_result_types);
        new AlertView(getContext().getString(R.string.repair_pick_result),
                null,
                getContext().getString(R.string.repair_cancel), null,
                repairResultArr, getContext(),
                AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(final Object o, final int position) {
                Integer resultKey = null;
                String resultName = null;
                if (position == 0) {
                    resultName = getContext().getString(R.string.repair_state_processing);
                    resultKey = RepairConstans.STATE_PROCESSING;
                    postRepairResultView.setRepairResult(resultKey, resultName);
                    mRecordVo.setRepairResult(resultKey);
                    mRecordVo.setRepairResultName(resultName);
                } else if (position == 1) {
//                    resultName = getContext().getString(R.string.repair_state_hang_up);
                    resultKey = RepairConstans.STATE_HANGUP;
                    setCloseReasonOrHangUp("挂起", resultKey);
                } else if (position == 2) {
                    resultName = getContext().getString(R.string.repair_state_complete);
                    resultKey = RepairConstans.STATE_COMPLETE;
                    postRepairResultView.setRepairResult(resultKey, resultName);
                    mRecordVo.setRepairResult(resultKey);
                    mRecordVo.setRepairResultName(resultName);
                } else if (position == 3) {
//                    resultName = getContext().getString(R.string.repair_state_close);
                    resultKey = RepairConstans.STATE_CLOSE;
                    setCloseReasonOrHangUp("关闭", resultKey);
                }
//                paramMap.put("repairCloseReason", resultKey);
            }
        }).show();
    }

    /**
     * @param reason 原因.
     * @param type   类型，是挂起，还是关闭
     */
    public void setCloseReasonOrHangUp(final String reason, final Integer type) {
        PickReasonDialog dialog = new PickReasonDialog(getContext(), reason, type);
        dialog.setOnDataInterface(new PickReasonDialog.OnDataInterface() {

            @Override
            public void setSelectType(final int type, final int id, final String name) {
                if (type == RepairConstans.STATE_HANGUP) {
                    mRecordVo.setRepairHangupReason(id);
                    String resultName = getContext().getString(R.string.repair_state_hang_up);
                    int resultKey = RepairConstans.STATE_HANGUP;
                    mRecordVo.setRepairHangupReason(id);
                    mRecordVo.setRepairResult(resultKey);
                    mRecordVo.setRepairResultName(resultName);
                    postRepairResultView.setRepairResult(resultKey, resultName);
                } else if (type == RepairConstans.STATE_CLOSE) {
                    String resultName = getContext().getString(R.string.repair_state_close);
                    int resultKey = RepairConstans.STATE_CLOSE;
                    mRecordVo.setRepairCloseReason(id);
                    mRecordVo.setRepairResult(resultKey);
                    mRecordVo.setRepairResultName(resultName);
                    postRepairResultView.setRepairResult(resultKey, resultName);
                }
            }
        });
        dialog.show();
        dialog.setDialogWindowAttr(dialog, getContext());
    }

    /**
     * @return
     */
    public Context getContext() {
        return postRepairResultView.getContext();
    }

    /**
     * '
     */
    @Override
    public void showPickMethodView() {
        String[] repairResultArr = getContext().getResources().getStringArray(R.array.repair_method_types);
        new AlertView(getContext().getString(R.string.repair_pick_method),
                null,
                getContext().getString(R.string.repair_cancel), null,
                repairResultArr, getContext(),
                AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(final Object o, final int position) {
                int resultKey = RepairConstans.METHOD_REPLACE;
                String resultName = getContext().getString(R.string.repair_method_replace);
                if (position == 0) {
                    resultName = getContext().getString(R.string.repair_method_replace);
                    resultKey = RepairConstans.METHOD_REPLACE;

                } else if (position == 1) {
                    resultName = getContext().getString(R.string.repair_method_repair);
                    resultKey = RepairConstans.METHOD_REPAIR;
                } else if (position == 2) {
                    resultName = getContext().getString(R.string.repair_method_others);
                    resultKey = RepairConstans.METHOD_OTHER;
                } else {
                    return;
                }
                postRepairResultView.setRepairMethod(resultKey, resultName);
                mRecordVo.setRepairAdopt(resultKey);
                mRecordVo.setRepairAdoptName(resultName);
            }
        }).show();
    }

    @Override
    public void showCompleteTimeView() {
        boolean[] showTime = {true, true, true, true, true, true};
        TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(final Date date, final View v) {
                if (date.before(new Date())) {
                    if (compareTime(date)) {
                        postRepairResultView.setCompleteTime(TimeUtil.getDateTimeStr(date));
                        mRecordVo.setFinishDate(TimeUtil.getDateSTimetr(date));
                    } else {
                        ToastUtil.makeText(postRepairResultView.getContext(),"不能早于工单开始时间");
                    }

                } else {
                    postRepairResultView.setNoFutureTime();
                }

            }
        })
                .setType(showTime)
                .build();
        pvTime.show();
    }

    private boolean compareTime(Date compleDate) {

        if (postRepairResultView.getCreateData().before(compleDate)) {
            //开始时间在完成时间之前
            return true;
        }
        return false;
    }

    @Override
    public void toRemarkFragment() {
        Map<String, Object> map = new HashMap<>();
        map.put("fragmentUri", LibConfig.AROUTE_FAULT_POST_REMARKS);
        String remark = getRemarks();
        if (remark != null) {
            map.put("remark", remark);
        }
        map.put("title", getContext().getString(R.string.repair_analysis));
        map.put("label", getContext().getString(R.string.repair_add_analysis));
        ARouterUtil.startActivityForResult(map,
                (Activity) getContext(), RepairConstans.StartActivityCode.REMARK_REQUEST_CODE);
    }

    public String getRemarks() {
        String remark = "";
        if (mRecordVo != null) {
            remark = mRecordVo.getFaultAnalysis();
        }
        return remark;
    }

    @Override
    public void getRepairResult() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("repairRecordId", postRepairResultView.getRepairRecordId());
        CommonModel<RepairRecordDto> progressModel = new CommonModelImpl<>();
        progressModel
                .setContext(postRepairResultView.getContext())
//                .setParamMap(map)
                .setEntityType(RepairRecordDto.class)
                .setUrl(RepairMethod.REPAIR_REPORT_DETAIL + "/" + postRepairResultView.getRepairRecordId())
                .setEntityListener(new GetEntityListener<RepairRecordDto>() {
                    @Override
                    public void success(final RepairRecordDto entity) {
                        if (entity != null) {
                            mRecordVo = entity.getRepairRecordVo();
                            mRecordVo.setFinishDate(TimeUtil.getDateSTimetr(new Date()));
                        }
                        postRepairResultView.setDetailData(entity);
                    }

                    @Override
                    public void fail(final String err) {

                    }
                })
                .getEntityNew();
    }

    @Override
    public void submit() {
        if (check() && postRepairResultView.getfileList() != null && postRepairResultView.getfileList().size() > 0) {
            upLoadFile();
        } else {
            uploadData(null, null);
        }


    }

    /**
     *
     */
    private void upLoadFile() {
        new UploadUtil(postRepairResultView.getContext())
                .setUploadData(postRepairResultView.getfileList())
                .setUploadSuccess(this).startUploadMultipleFile();
    }

    /**
     * @param imgUrl          图片路径.
     * @param imgThumbnailUrl 缩略图.
     */
    private void uploadData(final String imgUrl, final String imgThumbnailUrl) {
        if (check()) {
            String repairRecordId = postRepairResultView.getRepairRecordId();
            if (!TextUtils.isEmpty(repairRecordId)) {
                paramMap.put("id", repairRecordId);
            } else {
                ToastUtil.makeText(getContext(), getContext().getString(R.string.repair_upload_fail));
                return;
            }
            paramMap.put("repairResult", mRecordVo.getRepairResult());
            paramMap.put("repairAdopt", mRecordVo.getRepairAdopt());
            paramMap.put("finishDate", TimeTransformUtil.getUploadGMTTime(mRecordVo.getFinishDate()));
            paramMap.put("repairCloseReason", mRecordVo.getRepairCloseReason());
            paramMap.put("repairHangupReason", mRecordVo.getRepairHangupReason());
            paramMap.put("faultAnalysis", mRecordVo.getFaultAnalysis());
            if (imgUrl != null) {
                paramMap.put("imgUrl", imgUrl);
            } else if (postRepairResultView.getNetWorkList() != null) {
                paramMap.put("imgUrl", postRepairResultView.getNetWorkList());
            }
            if (imgThumbnailUrl != null) {
                paramMap.put("imgThumbnailUrl", imgThumbnailUrl);
            }
            CommonModel<Integer> model = new CommonModelImpl<>();
            model.setContext(postRepairResultView.getContext())
                    .setParamMap(paramMap)
                    .setEntityType(Integer.class)
                    .setUrl(RepairMethod.REPAIR_REPORT_RESULT)
                    .setEntityListener(new GetEntityListener<Integer>() {
                        @Override
                        public void success(final Integer entity) {
                            ((Activity) getContext()).finish();
                        }

                        @Override
                        public void fail(final String err) {

                        }
                    })
                    .putEntity();
        }

    }

    /**
     * 提交前校验.
     *
     * @return
     */
    private boolean check() {
        boolean checkResult = true;
        String repairRecordId = postRepairResultView.getRepairRecordId();
        if (TextUtils.isEmpty(repairRecordId)) {
            ToastUtil.makeText(getContext(), getContext().getString(R.string.repair_upload_fail));
            checkResult = false;
        } else if (mRecordVo.getRepairResult() == null || RepairConstans.STATE_UNPROCESS == mRecordVo.getRepairResult()) {
            checkResult = false;
            ToastUtil.makeText(getContext(), getContext().getString(R.string.repair_report_result_tip));
        } else if (mRecordVo.getRepairAdopt() == null) {
            checkResult = false;
            ToastUtil.makeText(getContext(), getContext().getString(R.string.repair_report_adopt_tip));
        }
//        else if (TextUtils.isEmpty(mRecordVo.getFinishDate())) {
//            checkResult = false;
//            ToastUtil.makeText(getContext(), getContext().getString(R.string.repair_report_finishdate_tip));
//        } else if (TextUtils.isEmpty(mRecordVo.getFaultAnalysis())) {
//            checkResult = false;
//            ToastUtil.makeText(getContext(), getContext().getString(R.string.repair_report_analysis_tip));
//        }
        return checkResult;
    }

    private boolean isEmpty() {
//        int faultRecordId = faultProcessingResultView.getFaultRecordId();//处理结果
//        String faultReason = faultProcessingResultView.getFaultReason();//缺陷原因
//        int faultCloseReason = faultProcessingResultView.getFaultCloseReason();//关闭
//        int hangupReason = faultProcessingResultView.getHangupReason();//挂起
//        if (faultRecordId < 0) {
//            faultProcessingResultView.resultEmpty();
//            return false;
//        } else if (faultRecordId == 2 && faultCloseReason < 0) {
//            //挂起
//            faultProcessingResultView.setCloseReasonOrHangUp("挂起", faultRecordId);
//            return false;
//        } else if (faultRecordId == 3 && hangupReason < 0) {
//            //关闭
//            faultProcessingResultView.setCloseReasonOrHangUp("关闭", faultRecordId);
//            return false;
//        }
        return true;
    }

    /**
     * @param remark 备注
     */
    @Override
    public void setRemark(final String remark) {
        mRecordVo.setFaultAnalysis(remark);
    }

    /**
     * @param fileList
     */
    @Override
    public void uploadSuccess(final List<FileEntity> fileList) {
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
        /**
         * 网络图片或者视频
         */
        String netWorkPath = postRepairResultView.getNetWorkList();//网络图片或者视频
        if (!TextUtils.isEmpty(netWorkPath)) {
            imgUrl = netWorkPath + "," + imgUrl;
            imgThumbnailUrl = postRepairResultView.getNetWordThumbnailUrl() + "," + imgThumbnailUrl;
        }
        uploadData(imgUrl, imgThumbnailUrl);
    }

    @Override
    public void uploadError() {
    }
}
