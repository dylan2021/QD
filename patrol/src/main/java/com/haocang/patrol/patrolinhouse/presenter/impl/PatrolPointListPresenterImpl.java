package com.haocang.patrol.patrolinhouse.presenter.impl;

import android.content.Context;
import android.content.Intent;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.AnalysisQRCodeUtil;
import com.haocang.base.utils.BDSendTraceUtil;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.offline.bean.patrol.PatrolPointEntity;
import com.haocang.offline.bean.patrol.PatrolTaskEntity;
import com.haocang.offline.bean.patrol.PatrolTaskPlanPath;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.constants.PatrolMethod;
import com.haocang.patrol.patrolinhouse.bean.PatrolPlanPath;
import com.haocang.patrol.patrolinhouse.bean.PatrolPointDetailDTO;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskDetailDTO;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskPointDTO;
import com.haocang.patrol.patrolinhouse.iview.PatrolDetailView;
import com.haocang.patrol.patrolinhouse.presenter.PatrolPointListPresenter;
import com.haocang.patrol.patrolinhouse.ui.PatrolPointDetailListFragment;
import com.haocang.patrol.patrolinhouse.ui.PatrolPointDetailScanListFragment;
import com.haocang.patrol.patroloutside.service.PatrolTraceService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by william on 2018/4/4.
 */

public class PatrolPointListPresenterImpl implements PatrolPointListPresenter {

    /**
     *
     */
    private PatrolDetailView patrolDetailView;

    /**
     * 获取巡检任务数据
     */
    @Override
    public void getPatrolDetailData() {
        if (OffLineOutApiUtil.isOffLine()) {
            getOffLinePatrolDetail();
        } else {
            CommonModel<PatrolTaskDetailDTO> progressModel = new CommonModelImpl<>();
            progressModel
                    .setContext(patrolDetailView.getContext())
                    .setEntityType(PatrolTaskDetailDTO.class)
                    .setHasDialog(false)
                    .setUrl(PatrolMethod.PATROL_TASK_DETAIL + "/" + patrolDetailView.getTaskId())
                    .setEntityListener(new GetEntityListener<PatrolTaskDetailDTO>() {
                        @Override
                        public void success(final PatrolTaskDetailDTO entity) {
                            patrolDetailView.renderData(entity);
                        }

                        @Override
                        public void fail(final String err) {

                        }
                    }).getEntityNew();

        }
    }

    /**
     * 获取离线数据
     */
    private void getOffLinePatrolDetail() {
        /**
         * 获取任务详情
         */
        PatrolTaskEntity taskEntity = OffLineOutApiUtil.getPatrolTaskData(patrolDetailView.getTaskId());
        String gson = new Gson().toJson(taskEntity);
        PatrolTaskDetailDTO taskDetailDTO = new Gson().fromJson(gson, PatrolTaskDetailDTO.class);
        /**
         * 获取巡检点列表
         */
        List<PatrolPointDetailDTO> patrolPointDetailDTOs = new ArrayList<>();
        List<PatrolPointEntity> list = OffLineOutApiUtil.getPatrolPointList(patrolDetailView.getTaskId());
        String point = new Gson().toJson(list);
        Type type = new TypeToken<List<PatrolPointDetailDTO>>() {
        }.getType();
        List<PatrolPointDetailDTO> pointDetail = new Gson().fromJson(point, type);
        patrolPointDetailDTOs.addAll(pointDetail);
        /**
         * 巡检点列表绑定到任务详情
         */
        taskDetailDTO.setPatrolPointDetailDTOs(patrolPointDetailDTOs);
        /**
         *获取计划路线
         */
        List<PatrolTaskPlanPath> planList = OffLineOutApiUtil.getTaskPlanPathList(patrolDetailView.getTaskId());
        String planSr = new Gson().toJson(planList);
        Type types = new TypeToken<List<PatrolPlanPath>>() {
        }.getType();
        List<PatrolPlanPath> pathlist = new Gson().fromJson(planSr, types);
        taskDetailDTO.setPatrolTaskPlanPaths(pathlist);
        patrolDetailView.renderData(taskDetailDTO);
    }

    /**
     * @param detailView 设置View接口.
     */
    @Override
    public void setPatrolDetailView(final PatrolDetailView detailView) {
        this.patrolDetailView = detailView;
    }

    /**
     * 二维码回调.
     */
    @Override
    public void getScanCode() {
        String qrCode = patrolDetailView.getQRCode();
        if (OffLineOutApiUtil.isOffLine()) {
            offLineScanQRCode(qrCode);
        } else {
            /**
             * 扫码.
             */
            scanQRCode(AnalysisQRCodeUtil.getPatrolPointId(patrolDetailView.getQRCode()), new GetEntityListener<PatrolTaskPointDTO>() {
                @Override
                public void success(final PatrolTaskPointDTO entity) {
                    if (entity != null) {
                        scanSuccess(entity);
                    }
                }

                @Override
                public void fail(final String err) {
                    ToastUtil.makeText(patrolDetailView.getContext(),
                            patrolDetailView.getContext()
                                    .getString(R.string.patrol_point_notintask));
                }
            });


        }

    }

    /**
     * @param qrCode
     * @param listener
     */
    private void scanQRCode(Integer qrCode, final GetEntityListener<PatrolTaskPointDTO> listener) {
        Map<String, Object> map = new HashMap<>();
        map.put("qrCode", qrCode);
        map.put("pointId", qrCode);
        map.put("taskId", patrolDetailView.getTaskId());
        CommonModel<PatrolTaskPointDTO> progressModel = new CommonModelImpl<>();
        progressModel
                .setContext(patrolDetailView.getContext())
                .setEntityType(PatrolTaskPointDTO.class)
                .setParamMap(map)
                .setUrl(PatrolMethod.PATROL_TASK_SCAN_QRCODE)
                .setEntityListener(listener)
                .getEntityNew();
    }

    /**
     * 离线模式扫码或者在巡检点附近
     *
     * @param qrCode
     */
    private void offLineScanQRCode(String qrCode) {
        int pointId = AnalysisQRCodeUtil.getPatrolPointId(qrCode);//巡检点id
        boolean flag = OffLineOutApiUtil.isPatrolPoint(patrolDetailView.getTaskId(), pointId);//判断巡检点是否再工单之内
        if (flag) {
            PatrolPointEntity entity = OffLineOutApiUtil.getPatrolPointData(patrolDetailView.getTaskId(), pointId);
            addTaskStartTime();
            if (entity != null) {
                PatrolTaskPointDTO dto = new PatrolTaskPointDTO();
                dto.setId(entity.getId());
                dto.setLatitude(entity.getLatitude());
                dto.setLongitude(entity.getLongitude());
                dto.setPatrolPointId(entity.getPatrolPointId());
                dto.setFaultCount(entity.getFaultCount());
                dto.setPatrolPointName(entity.getPatrolPoint());
                if (entity.getResultUpdateTime() != null) {
                    dto.setResultUpdateTime(entity.getResultUpdateTime());
                }
                scanSuccess(dto);
            }
        } else {
            ToastUtil.makeText(patrolDetailView.getContext(),
                    patrolDetailView.getContext()
                            .getString(R.string.patrol_point_notintask));
        }
    }

    /**
     * 任务开始时间没有的时候才去添加
     * 添加任务开始时间
     */
    private void addTaskStartTime() {
        PatrolTaskEntity taskEntity = OffLineOutApiUtil.getPatrolTaskData(patrolDetailView.getTaskId());
        if (taskEntity.getActualStartTime() == null) {
            taskEntity.setActualStartTime(new Date());//实际执行时间
            taskEntity.setExecuteStatus("executing");//改变执行状态
            taskEntity.setUpdateTime(TimeUtil.getTime());//做个唯一标识 标记这个任务是否做过
            OffLineOutApiUtil.modifyTask(taskEntity);
        }
    }

    /**
     * 扫码成功后逻辑.
     *
     * @param dto 巡检点信息
     */
    private void scanSuccess(final PatrolTaskPointDTO dto) {
        /**
         * 如果是地图巡检，需要上传轨迹（只有扫码成功才开始记录轨迹）
         */
        if (OffLineOutApiUtil.isOffLine()) {
            toPointDetailFragment(dto);
            sendTrace();
        } else if (patrolDetailView.isFromMap() && dto.getResultUpdateTime() == null) {
            if (isNearPoint(dto)) {
                sendTrace();
                toPointDetailFragment(dto);
            } else {
                ToastUtil.makeText(getContext(),
                        getContext().getString(R.string.patrol_not_near_point));
            }
        } else {
            toPointDetailFragment(dto);
        }
    }

    /**
     * @return
     */
    public Context getContext() {
        return patrolDetailView.getContext();
    }

    /**
     * 上传巡检轨迹.
     */
    public void sendTrace() {
        Intent intent = new Intent(AppApplication.getContext(),
                PatrolTraceService.class);
        intent.putExtra("taskId", patrolDetailView.getTaskId());
        intent.putExtra("endTime", patrolDetailView.getTaskEndTime());
        AppApplication.getContext().startService(intent);
    }

    /**
     * @param dto 巡检点.
     */
    private void toPointDetailFragment(final PatrolTaskPointDTO dto) {
        Intent intent = new Intent(getContext(), CommonActivity.class);
        intent.putExtra("taskId", patrolDetailView.getTaskId());
        intent.putExtra("pointId", dto.getPatrolPointId());
        intent.putExtra("id", dto.getId());
        intent.putExtra("faultCount", dto.getFaultCount());
        intent.putExtra("pointName", dto.getPatrolPointName());
        intent.putExtra("processName", patrolDetailView.getProcessName(dto.getPatrolPointId()));
        intent.putExtra("fragmentName",
                PatrolPointDetailListFragment.class.getName());
        if (OffLineOutApiUtil.isOffLine()) {
            intent.putExtra("taskCompleteFlag",
                    patrolDetailView.offLineIsTaskCompleteOthers(dto.getPatrolPointId()));
        } else {
            intent.putExtra("taskCompleteFlag",
                    patrolDetailView.isTaskCompleteOthers(dto.getPatrolPointId()));
        }

        intent.putExtra("isMapPatrol", patrolDetailView.isFromMap());
        intent.putExtra("taskName", patrolDetailView.getTaskName());
        if (dto.getResultUpdateTime() != null) {
            intent.putExtra("resultUpdateTime",
                    TimeUtil.getDateTimeStr(dto.getResultUpdateTime()));
        }
        patrolDetailView.getContext().startActivity(intent);
    }

    /**
     * @param patrolPointDetailDTO 巡检点
     * @return
     */
    @Override
    public boolean isNearPoint(final PatrolTaskPointDTO patrolPointDetailDTO) {
        if (OffLineOutApiUtil.isOffLine()) {
            return true;
        }
        boolean isNear = false;
        BDLocation location = BDSendTraceUtil.getInstance().getLocation();
        if (location != null && BDSendTraceUtil.getInstance().isLocationValid(location)) {
            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            LatLng pointLocation = new LatLng(patrolPointDetailDTO.getLatitude(), patrolPointDetailDTO.getLongitude());
            double distance = DistanceUtil.getDistance(currentLocation, pointLocation);
            if (distance < LibConfig.POTROL_LIMIT_DISTANCE) {
                isNear = true;
            }
        }

        return isNear;
    }

    /**
     * 点击巡检点.
     *
     * @param dto 巡检点
     */
    @Override
    public void onItemClick(final PatrolPointDetailDTO dto) {
        if (OffLineOutApiUtil.isOffLine()) {
            if (patrolDetailView.hasPatrolAuth() && dto != null && dto.hasPatroled()) {
                offLineScanQRCode("PP" + dto.getPatrolPointId());
            } else {
                toPointOffLineDetailScanPage(dto);
            }
            return;
        }
        if (patrolDetailView.hasPatrolAuth() && dto != null && dto.hasPatroled()) {
            scanQRCode(dto.getId(), new GetEntityListener<PatrolTaskPointDTO>() {
                @Override
                public void success(final PatrolTaskPointDTO entity) {
                    if (entity != null) {
                        toPointDetailFragment(entity);
                    }
                }

                @Override
                public void fail(final String err) {
//                            ToastUtil.makeText(patrolDetailView.getContext(),
//                                    patrolDetailView.getContext()
//                                            .getString(R.string.patrol_point_notintask));
                }
            });
//
        } else {
            toPointDetailScanPage(dto);
        }
    }


    /**
     * 跳转至巡检点查看页面
     *
     * @param entity 巡检点信息
     */
    private void toPointDetailScanPage(final PatrolPointDetailDTO entity) {
        Gson gson = new Gson();
        String patrolPointDetailDTOStr = gson.toJson(entity, PatrolPointDetailDTO.class);
        Intent intent = new Intent(patrolDetailView.getContext(), CommonActivity.class);
        intent.putExtra("patrolPointDetailDTOStr", patrolPointDetailDTOStr);
        intent.putExtra("taskId", patrolDetailView.getTaskId());
        intent.putExtra("pointId", entity.getId());
        intent.putExtra("pointName", entity.getPatrolPoint());
        intent.putExtra("fragmentName", PatrolPointDetailScanListFragment.class.getName());
        patrolDetailView.getContext().startActivity(intent);
    }

    private void toPointOffLineDetailScanPage(final PatrolPointDetailDTO entity) {
        Gson gson = new Gson();
        String patrolPointDetailDTOStr = gson.toJson(entity, PatrolPointDetailDTO.class);
        Intent intent = new Intent(patrolDetailView.getContext(), CommonActivity.class);
        intent.putExtra("patrolPointDetailDTOStr", patrolPointDetailDTOStr);
        intent.putExtra("taskId", patrolDetailView.getTaskId());
        intent.putExtra("pointId", entity.getPatrolPointId());
        intent.putExtra("pointName", entity.getPatrolPoint());
        intent.putExtra("fragmentName", PatrolPointDetailScanListFragment.class.getName());
        patrolDetailView.getContext().startActivity(intent);
    }
}
