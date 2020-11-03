package com.haocang.patrol.patrolinhouse.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.bean.FileEntity;
import com.haocang.base.bean.PictureEntity;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.GetListListener;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.utils.UploadUtil;
import com.haocang.offline.bean.patrol.PatrolPointEntity;
import com.haocang.offline.bean.patrol.PatrolStepsEntity;
import com.haocang.offline.bean.patrol.PatrolTaskEntity;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.constants.PatrolMethod;
import com.haocang.patrol.patrolinhouse.bean.PatrolPictureEntity;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskPointStep;
import com.haocang.patrol.patrolinhouse.iview.PatrolPointDetailListView;
import com.haocang.patrol.patrolinhouse.presenter.PatrolPointDetailListPresenter;
import com.haocang.patrol.patrolinhouse.ui.PatrolResultFragment;
import com.haocang.patrol.patroloutside.service.PatrolTraceService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
 * 创建时间：2018/4/8下午7:03
 * 修  改  者：
 * 修改时间：
 */
public class PatrolPointDetailListPresenterImpl implements PatrolPointDetailListPresenter {

    private PatrolPointDetailListView mPatrolPointDetailListView;
    private List<PatrolTaskPointStep> upSteps;

    public PatrolPointDetailListPresenterImpl(final PatrolPointDetailListView patrolPointDetailListView) {
        mPatrolPointDetailListView = patrolPointDetailListView;
    }

    /**
     *
     */
    @Override
    public void complete() {
        if (mPatrolPointDetailListView.isPointCompleteAll()) {
            mPatrolPointDetailListView.setClickable(false);
//            uploadPointSteps();
            comple();
        } else {
            new AlertView(mPatrolPointDetailListView.getContexts().getString(R.string.patrol_unupload_content_tiptitle),
                    mPatrolPointDetailListView.getContexts().getString(R.string.patrol_unupload_content_tip),
                    null, new String[]{mPatrolPointDetailListView.getContexts().getString(R.string.yes),
                    mPatrolPointDetailListView.getContexts().getString(R.string.no)},
                    null, mPatrolPointDetailListView.getContexts(),
                    AlertView.Style.ActionSheet, new OnItemClickListener() {
                @Override
                public void onItemClick(final Object o, final int position) {
                    if (position == 0) {
                        mPatrolPointDetailListView.setClickable(false);
                        comple();
                    } else {
                        mPatrolPointDetailListView.setClickable(true);
                    }
                }
            }).show();
        }

    }

    private boolean isExemption = false;

    /**
     * 免检
     */
    @Override
    public void completeExemption() {
        isExemption = true;
        comple();

    }

    private void comple() {
        upSteps = new ArrayList<>();
        List<PatrolTaskPointStep> steps = new ArrayList<>();
        steps.addAll(mPatrolPointDetailListView.getPatrolPointSteps());
        uploadFile(steps);
    }


    private void uploadFile(final List<PatrolTaskPointStep> steps) {
        if (steps.size() <= 0) {
            uploadPointSteps();
            return;
        }
        List<PictureEntity> list = steps.get(0).getFileList();
        if (list == null || list.size() == 0 || OffLineOutApiUtil.isOffLine()) {
            upSteps.add(steps.get(0));
            steps.remove(0);
            uploadFile(steps);
            return;
        }
        List<String> upList = new ArrayList<>();
        for (PictureEntity entity : list) {
            if (entity.getType() == 0) {
                //照片
                upList.add(entity.getLocalImgPath());
            } else {
                //视频
                upList.add(entity.getVideoPath());
            }
        }
        new UploadUtil(mPatrolPointDetailListView.getContexts()).setUploadData(upList).setUploadSuccess(new UploadUtil.UploadSuccess() {
            @Override
            public void uploadSuccess(List<FileEntity> list) {
                List<PatrolPictureEntity> picList = new ArrayList<>();
                for (FileEntity entity : list) {
                    PatrolPictureEntity pictureEntity = new PatrolPictureEntity();
                    pictureEntity.setImgThumbnailUrl(entity.getThumbPath());
                    pictureEntity.setImgUrl(entity.getPath());
                    picList.add(pictureEntity);
                }
                if (steps.get(0).getStepImgList() != null && steps.get(0).getStepImgList().size() > 0) {
                    List<PatrolPictureEntity> patrolList = steps.get(0).getStepImgList();
                    patrolList.addAll(picList);
                    steps.get(0).setStepImgList(patrolList);
                } else {
                    steps.get(0).setStepImgList(picList);
                }
                upSteps.add(steps.get(0));
                steps.remove(0);
                uploadFile(steps);
            }

            @Override
            public void uploadError() {

            }
        }).startUploadMultipleFile();
    }


    private Map<String, Object> getUploadParams() {
        Map<String, Object> map = new HashMap<>();
        map.put("patrolTaskId", mPatrolPointDetailListView.getTaskId());
        map.put("patrolHasResultCount", mPatrolPointDetailListView.getHasResultCount());
        map.put("faultCount", mPatrolPointDetailListView.getFaultCount());
        map.put("newFaultCount", mPatrolPointDetailListView.getNewFaultCount());
        map.put("id", mPatrolPointDetailListView.getPatrolPointId());
        if (!TextUtils.isEmpty(mPatrolPointDetailListView.getLastResultUpdateTime())) {
            map.put("resultUpdateTime", TimeTransformUtil.getUploadGMTTime(mPatrolPointDetailListView.getLastResultUpdateTime()));
        }
        JSONArray array = new JSONArray();
        JSONObject object;
        for (PatrolTaskPointStep step : upSteps) {
            object = new JSONObject();
            try {
                object.put("id", step.getId());
                object.put("stepComment", step.getStepComment());
                object.put("stepResult", step.getStepResult());
                if (step.getStepImgList() != null && step.getStepImgList().size() > 0) {
                    JSONArray picArray = new JSONArray();
                    for (PatrolPictureEntity pictureEntity : step.getStepImgList()) {
                        JSONObject oj = new JSONObject();
                        oj.put("imgUrl", pictureEntity.getImgUrl());
                        oj.put("imgThumbnailUrl", pictureEntity.getImgThumbnailUrl());
                        picArray.put(oj);
                    }
                    object.put("stepImgList", picArray);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(object);
        }
        map.put("patrolTaskPointSteps", array);
        Log.i("sssssss", array.toString());
        return map;
    }

    private void uploadPointSteps(Map<String, Object> map) {
        CommonModel<String> model = new CommonModelImpl<>();
        model.setContext(mPatrolPointDetailListView.getContexts())
                .setParamMap(map)
                .setEntityType(Integer.class)
                .setUrl(PatrolMethod.PATROL_TASKPOINT_UPDATEALL)
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(final Integer entity) {
                        mPatrolPointDetailListView.exemptionSubmitSuccess();
                    }

                    @Override
                    public void fail(final String err) {
                        try {
                            JSONObject object = new JSONObject(err);
                            String sr = object.optString("title");
                            mPatrolPointDetailListView.submitFail(sr);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .putEntity();
    }

    private void uploadPointSteps() {
        if (OffLineOutApiUtil.isOffLine()) {//离线模式
            cachePointStepsSuccess();
        } else {
//            Map<String, Object> map = getUploadParams();
//            CommonModel<String> model = new CommonModelImpl<>();
//            model.setContext(mPatrolPointDetailListView.getContexts())
//                    .setParamMap(map)
//                    .setEntityType(String.class)
//                    .setUrl(PatrolMethod.PATROL_TASKPOINT_UPDATEALL)
//                    .setEntityListener(new GetEntityListener<String>() {
//                        @Override
//                        public void success(final String entity) {
//                            if (isExemption) {
//                                isExemption = false;
//                                mPatrolPointDetailListView.exemptionSubmitSuccess();
//                            } else {
//                                if (entity == null) {
//                                    ToastUtil.makeText(mPatrolPointDetailListView.getContexts(), "测点不存在");
//                                }
//                                mPatrolPointDetailListView.submitSuccess();
//                                uploadPointStepsSuccess();
//                            }
//                        }
//
//                        @Override
//                        public void fail(final String err) {
//                            try {
//                                JSONObject object = new JSONObject(err);
//                                String sr = object.optString("title");
//                                mPatrolPointDetailListView.submitFail(sr);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    })
//                    .putEntity();
            AddParameters addParameters = new AddParameters();
            addParameters.addParam(getUploadParams());
            new OkHttpClientManager()
                    .setUrl(PatrolMethod.PATROL_TASKPOINT_UPDATEALL)
                    .setRequestBody(addParameters.formBody())
                    .setRequestMethod(LibConfig.HTTP_PUT)
                    .setOnNetWorkReponse(new OkHttpClientManager.OnNetworkResponse() {
                        @Override
                        public void onNetworkResponse(String s) {
                            if (isExemption) {
                                isExemption = false;
                                mPatrolPointDetailListView.exemptionSubmitSuccess();
                            } else {
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    if (!jsonObject.isNull("message")) {
                                        ToastUtil.makeText(mPatrolPointDetailListView.getContexts(), "测点" + jsonObject.optString("message") + "不存在");
                                    }
                                    mPatrolPointDetailListView.submitSuccess();
                                    uploadPointStepsSuccess();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                mPatrolPointDetailListView.submitSuccess();
                                uploadPointStepsSuccess();
                            }
                        }

                        @Override
                        public void onErrorResponse(Response response) {

                        }
                    }).builder();


        }

    }


    /**
     * 缓存离线巡检点数据
     */
    private void cachePointStepsSuccess() {
        int taskId = mPatrolPointDetailListView.getTaskId();//任务id
        int pointId = mPatrolPointDetailListView.getPointId();//巡检点id
        PatrolPointEntity pointEntity = OffLineOutApiUtil.getPatrolPointData(taskId, pointId);//获取相应的巡检点信息
        pointEntity.setFaultCount(mPatrolPointDetailListView.getFaultCount());
        pointEntity.setPatrolHasResultCount(mPatrolPointDetailListView.getHasResultCount());
        pointEntity.setNewFaultCount(mPatrolPointDetailListView.getNewFaultCount());
        if (!TextUtils.isEmpty(mPatrolPointDetailListView.getLastResultUpdateTime())) {
            pointEntity.setResultUpdateTime(new Date());
            PatrolTaskEntity taskEntity = OffLineOutApiUtil.getPatrolTaskData(taskId);
            taskEntity.setResultUpdateTime(new Date());
            OffLineOutApiUtil.modifyTask(taskEntity);
        } else {
            /**
             * 没修改过的才能加1
             */
            pointEntity.setResultUpdateTime(new Date());
            PatrolTaskEntity taskEntity = OffLineOutApiUtil.getPatrolTaskData(taskId);
            taskEntity.setResultUpdateTime(new Date());
            taskEntity.setInspectedCount(taskEntity.getInspectedCount() + 1);
            OffLineOutApiUtil.modifyTask(taskEntity);//修改工单结果数量
        }

        List<PatrolTaskPointStep> steps = new ArrayList<>();
        steps.addAll(mPatrolPointDetailListView.getPatrolPointSteps());
        int recordCount = 0;//巡检步骤完成总数
        for (PatrolTaskPointStep step : steps) {
            PatrolStepsEntity entity = OffLineOutApiUtil.getStepsData(step.getPatrolTaskId(), step.getPatrolPointId(), step.getPatrolStepId());
            if (entity == null) {
                continue;
            }
            entity.setStepComment(step.getStepComment());
            entity.setStepResult(step.getStepResult());
            if (!TextUtils.isEmpty(step.getStepResult())) {
                recordCount += 1;
            }
            entity.setStepResultType(step.getStepResultType());
            OffLineOutApiUtil.modifyStep(entity);//修改巡检步骤
        }
        pointEntity.setRecordCount(recordCount);
        OffLineOutApiUtil.modifyPoint(pointEntity);//修改巡检点状态
        uploadPointStepsSuccess();
    }


    /**
     * 上传巡检结果成功后，判断是否是否是最后一个巡检点，并且所有巡检点的巡检步骤都提交了
     * 1.如果没有全部提交，直接关闭
     * 2.如果全部提交，弹出巡检结果页面
     */
    private void uploadPointStepsSuccess() {
        if (mPatrolPointDetailListView.isTaskComplete()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Context ctx = mPatrolPointDetailListView.getContexts();
                    new AlertView("结束巡检",
                            "巡检步骤已全部提交，是否结束巡检？",
                            null, new String[]{"是",
                            "否"},
                            null,
                            ctx,
                            AlertView.Style.ActionSheet, new OnItemClickListener() {
                        @Override
                        public void onItemClick(final Object o, final int position) {
                            if (position == 0) {
                                finishTask();
                            } else {
                                finish();
                            }
                        }
                    }).show();
                }
            }, 200);

        } else {
            finish();
        }

    }

    /**
     * 结束巡检
     */
    public void finish() {
        ((Activity) mPatrolPointDetailListView.getContexts()).finish();
    }

    /**
     * 结束巡检
     */
    private void finishTask() {
        if (OffLineOutApiUtil.isOffLine()) {
            int taskId = mPatrolPointDetailListView.getTaskId();
            PatrolTaskEntity entity = OffLineOutApiUtil.getPatrolTaskData(taskId);
            entity.setExecuteStatus("finished");
            entity.setActualEndTime(new Date());
            entity.setFaultCount(OffLineOutApiUtil.getTaskFaultCount(taskId));
            entity.setStepCount(OffLineOutApiUtil.getStepCount(taskId));
            entity.setHasResultCount(OffLineOutApiUtil.getRecordCount(taskId));
            OffLineOutApiUtil.modifyTask(entity);
            toTaskResult();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("taskId", mPatrolPointDetailListView.getTaskId());
        map.put("isFinished", 1);
        CommonModel<Integer> commonModel = new CommonModelImpl<>();
        commonModel
                .setContext(mPatrolPointDetailListView.getContexts())
                .setUrl(PatrolMethod.PATROL_TASK_UPDATE)
                .setParamMap(map)
                .setEntityType(Integer.class)
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(final Integer entity) {
                        toTaskResult();
                    }

                    @Override
                    public void fail(final String err) {

                    }
                }).putEntity();
    }

    /**
     * 跳转至巡检结果
     */
    private void toTaskResult() {
        stopUploadTrace();
        Intent intent = new Intent(mPatrolPointDetailListView.getContexts(), CommonActivity.class);
        intent.putExtra("fragmentName", PatrolResultFragment.class.getName());
        intent.putExtra("taskId", mPatrolPointDetailListView.getTaskId());
        intent.putExtra("isMapPatrol", mPatrolPointDetailListView.isMapPatrol());
        intent.putExtra("taskName", mPatrolPointDetailListView.getTaskName());
        mPatrolPointDetailListView.getContexts().startActivity(intent);
        finish();
    }

    private void stopUploadTrace() {
        Intent intent = new Intent(AppApplication.getContext(), PatrolTraceService.class);
        AppApplication.getContext().stopService(intent);
    }

    @Override
    public void getPatrolPointDetailList() {
        if (OffLineOutApiUtil.isOffLine()) {
            List<PatrolStepsEntity> list = new ArrayList<>();
            list.addAll(OffLineOutApiUtil.getPatrolStepList(mPatrolPointDetailListView.getTaskId(), mPatrolPointDetailListView.getPointId(), mPatrolPointDetailListView.getQueryName()));
            String stepSr = new Gson().toJson(list);
            Type type = new TypeToken<List<PatrolTaskPointStep>>() {
            }.getType();
            List<PatrolTaskPointStep> setp = new Gson().fromJson(stepSr, type);
            mPatrolPointDetailListView.renderList(setp);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("pointId", mPatrolPointDetailListView.getPointId());
            map.put("taskId", mPatrolPointDetailListView.getTaskId());
            map.put("queryName",
                    StringUtils.utfCode(mPatrolPointDetailListView.getQueryName()));
            CommonModel<PatrolTaskPointStep> progressModel
                    = new CommonModelImpl<>();
            Type type = new TypeToken<List<PatrolTaskPointStep>>() {
            }.getType();
            progressModel
                    .setContext(mPatrolPointDetailListView.getContexts())
                    .setParamMap(map)
                    .setHasDialog(false)
                    .setListType(type)
                    .setUrl(PatrolMethod.PATROL_TASK_POINT_DETAIL)
                    .setListListener(new GetListListener<PatrolTaskPointStep>() {
                        @Override
                        public void success(final List<PatrolTaskPointStep> list) {
                            mPatrolPointDetailListView.renderList(list);
                        }
                    })
                    .getList();

        }

    }


}
