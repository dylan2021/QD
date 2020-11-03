package com.haocang.patrol.patroloutside.presenter.impl;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;
import com.haocang.base.utils.BDSendTraceUtil;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.patrolinhouse.bean.PatrolPointDetailDTO;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskPointDTO;
import com.haocang.patrol.patroloutside.iview.PatrolOutdiseView;
import com.haocang.patrol.patroloutside.presenter.PatrolOutsidePresenter;
import com.haocang.patrol.patroloutside.service.PatrolTraceService;

import java.util.ArrayList;
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
 * 创建时间：2018/4/19上午11:08
 * 修  改  者：
 * 修改时间：
 */
public class PatrolOutsidePresenterImpl implements PatrolOutsidePresenter {

    /**
     *
     */
    private PatrolOutdiseView patrolOutdiseView;
    /**
     *
     */
    private Map<String, String> statusMap;


    /**
     * @param view 场外巡检界面交互接口
     */
    public PatrolOutsidePresenterImpl(final PatrolOutdiseView view) {
        this.patrolOutdiseView = view;
        String[] patrolStatusLabelArray = AppApplication.getContext()
                .getResources().getStringArray(R.array.patrol_status_labels_all);
        String[] patrolStatusKeysArray = AppApplication.getContext()
                .getResources().getStringArray(R.array.patrol_status_keys_all);
        statusMap = new HashMap<>();
        for (int i = 0; i < patrolStatusLabelArray.length
                && i < patrolStatusKeysArray.length; i++) {
            statusMap.put(patrolStatusKeysArray[i], patrolStatusLabelArray[i]);
        }
    }


    /**
     * 查询巡检服务是否在运行.
     *
     * @return
     */
    public boolean isServiceRunning() {
        String serviceName = PatrolTraceService.class.getName();
        ActivityManager myManager = (ActivityManager) patrolOutdiseView.getContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService =
                (ArrayList<ActivityManager.RunningServiceInfo>) myManager
                        .getRunningServices(30);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service
                    .getClassName().toString().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param excuteStatus 执行状态.
     * @return
     */
    @Override
    public String getShowNameByStatus(final String excuteStatus) {
        return statusMap.get(excuteStatus);
    }

    /**
     * @param title 点击的标记的title，设置为巡检点的ID.
     * @return
     */
    @Override
    public PatrolPointDetailDTO onMarkerClick(final String title) {
        PatrolPointDetailDTO dto = null;
        List<PatrolPointDetailDTO> points =
                patrolOutdiseView.getPatrolPointList();
        if (points != null && points.size() > 0) {
            for (PatrolPointDetailDTO entity : points) {
                if (title != null && title.equals(entity.getId() + "")) {
                    dto = entity;
                    break;
                }
            }
        }
        return dto;
    }

    /**
     * 获取上下文参数.
     *
     * @return 上下文参数
     */
    public Context getContext() {
        return patrolOutdiseView.getContext();
    }

    /**
     *
     */
    private Handler handler = new Handler();
    /**
     *
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            patrolOutdiseView.getPatrolDetailData();
            arrivedPoint = getArrivePatrolPoint();
            if (arrivedPoint != null) {
                showEnterPatrolDialog();
            }
            handler.postDelayed(this,
                    LibConstants.Patrol.CHECK_ARRIVE_POINT_INTERVAL);
        }
    };

    /**
     *
     */
    private AlertView arrivePointDialog = null;

    /**
     *
     */
    private PatrolPointDetailDTO arrivedPoint;

    /**
     * 显示是否进入巡检点提示.
     */
    private void showEnterPatrolDialog() {
        if (arrivePointDialog == null) {
            arrivePointDialog
                    = new AlertView(getContext().getString(R.string.patrol_arrive_point_tip),
                    "",
                    null, new String[]{getContext().getString(R.string.yes),
                    getContext().getString(R.string.no)},
                    null, getContext(),
                    AlertView.Style.ActionSheet, new OnItemClickListener() {
                @Override
                public void onItemClick(final Object o, final int position) {
                    if (position == 0) {
                        if (patrolOutdiseView != null && arrivedPoint != null)
                            offLine();
                    }
                    arrivePointDialog.dismiss();
                }
            });
        }
        if (arrivePointDialog != null && !arrivePointDialog.isShowing()) {
            arrivePointDialog.show();
            patrolOutdiseView.showVibrator();
        }

    }

    /**
     * 离线模式 的 pointId字段不一致，离线模式的patrolPointId等于有网模式的 id
     */
    private void offLine() {
        if (OffLineOutApiUtil.isOffLine()) {
            patrolOutdiseView.offLineArrive(arrivedPoint);
        } else {
            patrolOutdiseView.arrive(arrivedPoint);
        }
    }


//    /**
//     * 跳转巡检点填写界面
//     *
//     * @param dto 巡检点
//     */
//    private void toPointDetailPage(final PatrolPointDetailDTO dto) {
//        Intent intent = new Intent(getContext(), CommonActivity.class);
//        intent.putExtra("taskId", patrolOutdiseView.getTaskId());
//        intent.putExtra("pointId", dto.getId());
//        intent.putExtra("id", dto.getId());
//        intent.putExtra("faultCount", dto.getFaultCount());
//        intent.putExtra("pointName", dto.getPatrolPoint());
//        intent.putExtra("fragmentName", PatrolPointDetailListFragment.class.getName());
//        intent.putExtra("taskCompleteFlag", isTaskCompleteOthers(dto.getId()));
//        intent.putExtra("isMapPatrol", true);
//        intent.putExtra("taskName", patrolOutdiseView.getTaskName());
//        if (dto.getResultUpdateTime() != null) {
//            intent.putExtra("resultUpdateTime", TimeUtil.getDateTimeStr(dto.getResultUpdateTime()));
//        }
//        getContext().startActivity(intent);
//    }

    /**
     * 是否开始检查是否在巡检点附近.
     */
    private boolean hasStartCheckArrivedPoint = false;

    /**
     * 开始检查是否在巡检点附近.
     */
    @Override
    public void startCheckArrivedPoint() {
        if (!hasStartCheckArrivedPoint) {
            hasStartCheckArrivedPoint = true;
            arrivedPoint = getArrivePatrolPoint();
            if (arrivedPoint != null) {
                showEnterPatrolDialog();
            }
            handler.postDelayed(runnable,
                    LibConstants.Patrol.CHECK_ARRIVE_POINT_INTERVAL);
        }

    }

    /**
     * 停止检查是否在巡检点附近.
     */
    @Override
    public void stopCheckArrivedPoint() {
        hasStartCheckArrivedPoint = false;
        handler.removeCallbacks(runnable);
    }

    /**
     * @param patrolPointDetailDTO 巡检点.
     * @return
     */
    @Override
    public boolean isNearPoint(final PatrolTaskPointDTO patrolPointDetailDTO) {
        if (OffLineOutApiUtil.isOffLine()) {
            return true;
        }
        boolean isNear = false;
        BDLocation location = BDSendTraceUtil.getInstance().getLocation();
        if (location != null) {
            LatLng currentLocation = new LatLng(location.getLatitude(),
                    location.getLongitude());
            LatLng pointLocation = new LatLng(patrolPointDetailDTO.getLatitude(),
                    patrolPointDetailDTO.getLongitude());
            double distance = DistanceUtil.getDistance(currentLocation,
                    pointLocation);
            if (distance < LibConfig.POTROL_LIMIT_DISTANCE) {
                isNear = true;
            }
        }

        return isNear;
    }

    /**
     * 除当前巡检点外，其他巡检点的步骤是否已巡检完.
     *
     * @param patrolPointId 巡检点ID
     * @return
     */
    @Override
    public boolean isTaskCompleteOthers(final int patrolPointId) {
        boolean completeFlag = true;
        List<PatrolPointDetailDTO> mList
                = patrolOutdiseView.getPatrolPointList();
        for (PatrolPointDetailDTO point : mList) {
            /**
             * 当前巡检点不放入比较.
             */
            if (point.getId() != patrolPointId
                    && point.getStepCount() != point.getRecordCount()) {
                completeFlag = false;
                break;
            }
        }
        return completeFlag;
    }

    @Override
    public boolean offLineIsTaskCompleteOthers(int patrolPointId) {
        boolean completeFlag = true;
        List<PatrolPointDetailDTO> mList
                = patrolOutdiseView.getPatrolPointList();
        for (PatrolPointDetailDTO point : mList) {
            /**
             * 当前巡检点不放入比较.
             */
            if (point.getPatrolPointId() != patrolPointId
                    && point.getStepCount() != point.getRecordCount()) {
                completeFlag = false;
                break;
            }
        }
        return completeFlag;
    }

    /**
     * @return 到达的巡检点.
     */
    public PatrolPointDetailDTO getArrivePatrolPoint() {
        PatrolPointDetailDTO arrivedPointDto = null;
        List<PatrolPointDetailDTO> pointList
                = patrolOutdiseView.getPatrolPointList();
        if (pointList != null) {
            for (PatrolPointDetailDTO patrolPointDetailDTO : pointList) {
                if (patrolPointDetailDTO.getResultUpdateTime() == null) {
                    BDLocation location
                            = BDSendTraceUtil.getInstance().getLocation();
                    if (location != null) {
                        LatLng currentLocation =
                                new LatLng(location.getLatitude(),
                                        location.getLongitude());
                        LatLng pointLocation =
                                new LatLng(patrolPointDetailDTO.getLatitude(),
                                        patrolPointDetailDTO.getLongitude());
                        double distance = DistanceUtil
                                .getDistance(currentLocation, pointLocation);
                        if (distance < LibConfig.POTROL_LIMIT_DISTANCE) {
                            arrivedPointDto = patrolPointDetailDTO;
                        }
                    }

                }

            }
        }
        return arrivedPointDto;
    }

}
