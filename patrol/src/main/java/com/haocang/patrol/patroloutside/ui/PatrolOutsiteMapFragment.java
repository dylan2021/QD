package com.haocang.patrol.patroloutside.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.utils.BDSendTraceUtil;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.ScanCodeUtils;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.zxing.app.CaptureActivity;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.bean.PatrolConstans;
import com.haocang.patrol.patrolinhouse.bean.PatrolActualPath;
import com.haocang.patrol.patrolinhouse.bean.PatrolPlanPath;
import com.haocang.patrol.patrolinhouse.bean.PatrolPointDetailDTO;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskDetailDTO;
import com.haocang.patrol.patrolinhouse.iview.PatrolDetailView;
import com.haocang.patrol.patrolinhouse.presenter.impl.PatrolPointListPresenterImpl;
import com.haocang.patrol.patroloutside.iview.PatrolOutdiseView;
import com.haocang.patrol.patroloutside.nfcutil.PatrolNfcActivity;
import com.haocang.patrol.patroloutside.presenter.PatrolOutsidePresenter;
import com.haocang.patrol.patroloutside.presenter.impl.PatrolOutsidePresenterImpl;
import com.haocang.patrol.patroloutside.service.PatrolTraceService;

import java.util.ArrayList;
import java.util.List;

/**
 * 厂外巡检，地图显示
 * Created by william on 2018/4/3.
 */

public class PatrolOutsiteMapFragment
        extends Fragment
        implements PatrolDetailView, PatrolOutdiseView,
        View.OnClickListener, BaiduMap.OnMarkerClickListener {
    /**
     *
     */
    private MapView mMapView = null;
    /**
     *
     */
    private View mPatrolScanLl;

    private View mPatrolNfcLl;
    /**
     *
     */
    private Context mContext;
    /**
     *
     */
    private PatrolPointListPresenterImpl mPresenter;
    /**
     *
     */
    private String qrCode;
    /**
     *
     */
    private PatrolTaskDetailDTO patrolTaskDetailDTO;
    /**
     *
     */
    private PatrolOutsidePresenter mPatrolOutsidePresenter;

    /**
     *
     */
    private LatLngBounds.Builder builder = new LatLngBounds.Builder();

    /**
     *
     */
    private View mylocationLl;

    private PatrolStateInterface stateInterface;


    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view =
                inflater.inflate(R.layout.patrol_outsidemap_fragment, null);
        mContext = getActivity();
        initView(view);
        return view;
    }

    /**
     * 初始化界面.
     *
     * @param view 根View
     */
    private void initView(final View view) {
        mMapView = view.findViewById(R.id.bmapView);
        mylocationLl = view.findViewById(R.id.patrol_locate_position_iv);
        mPatrolScanLl = view.findViewById(R.id.patrol_scan_ll);
        mPatrolNfcLl = view.findViewById(R.id.patrol_nfc_iv);
        mPatrolNfcLl.setOnClickListener(this);
        view.findViewById(R.id.patrol_scan_bt).setOnClickListener(this);
        view.findViewById(R.id.patrol_scan_ll).setOnClickListener(this);
        view.findViewById(R.id.patrol_locate_position_iv).setOnClickListener(this);
        mPresenter = new PatrolPointListPresenterImpl();
        mPresenter.setPatrolDetailView(this);
        mPatrolOutsidePresenter = new PatrolOutsidePresenterImpl(this);
        mMapView.showScaleControl(false);
        mMapView.showZoomControls(false);
        mMapView.getMap().setOnMarkerClickListener(this);
        mMapView.getMap().setMyLocationEnabled(true);
        setMyLocation();
//        addMyLocation();
        // 中的接口，您可以在地图上展示实时位置信息，核心代码如下：
    }

    /**
     * 当前位置.
     */
    private Marker myLocation;
    /**
     *
     */
    private Overlay myLocationText;


    /**
     *
     */
//    private MyLocationData locData;

    /**
     * 设置我的位置.
     */
    private void setMyLocation() {
        if (!BDSendTraceUtil.getInstance().isLocationValid()) {
            ToastUtil.makeText(mContext,
                    mContext.getString(R.string.patrol_locate_failed));
        } else {
            BDLocation location = BDSendTraceUtil
                    .getInstance()
                    .getLocation();
            LatLng cept = new LatLng(location.getLatitude(),
                    location.getLongitude());
            mMapView.getMap().setMyLocationEnabled(true);
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(PatrolConstans.MAP_LOCATION_ACCURACY)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .latitude(location.getLatitude())
                    .satellitesNum(9)
                    .longitude(location.getLongitude()).build();
//            BitmapDescriptor b = BitmapDescriptorFactory.fromResource(R.mipmap.patrol_mylocation);
            MyLocationConfiguration configuration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
//            mMapView.getMap().setMyLocationConfiguration(configuration);
// 设置定位数据
            mMapView.getMap().setMyLocationData(locData);
//            addMyLocation();
            updateMapCenterLocation(cept);
        }
    }

    public void addMyLocation() {

        if (BDSendTraceUtil.getInstance().isLocationValid()) {
            BDLocation location = BDSendTraceUtil.getInstance()
                    .getLocation();
            LatLng cept = new LatLng(location.getLatitude(),
                    location.getLongitude());
            if (myLocation != null) {
                myLocation.remove();
            }
            if (myLocationText != null) {
                myLocationText.remove();
            }
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.mipmap.patrol_mylocation);
            MarkerOptions ooD = new MarkerOptions()
                    .position(cept)
                    .icon(bitmap)
                    .zIndex(0)
                    .period(PatrolConstans.MARKOPTION_PERIOD);
            myLocation = (Marker) (mMapView.getMap().addOverlay(ooD));
            OverlayOptions textOption = new TextOptions()
                    .fontSize(PatrolConstans.MAP_TEXTOPTION_FONTSIZE)
                    .fontColor(mContext.getResources().getColor(R.color.patrol_blue))
                    .bgColor(mContext.getResources().getColor(R.color.patrol_mapwindow_bg))
                    .text(mContext.getString(R.string.patrol_mylocation)).position(cept);
            myLocationText = mMapView.getMap().addOverlay(textOption);
        }

    }

    /**
     * 定位地图到中心坐标点.
     *
     * @param cenpt 中心点坐标.
     */
    private void updateMapCenterLocation(final LatLng cenpt) {
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
//                .zoom(PatrolConstans.DEFAULT_ZOOM_LEVEL)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
                .newMapStatus(mMapStatus);
        try {
            // 改变地图状态
            if (mMapView != null && mMapView.getMap() != null) {
                mMapView.getMap().setMapStatus(mMapStatusUpdate);
            }
        } catch (Exception e) {

        }
    }

    /**
     * 单位1000.
     */
    private final double unitK = 1000.0;

    /**
     * @param taskDetailDTO 巡检任务详情.
     */
    @SuppressLint("StringFormatMatches")
    @Override
    public void renderData(final PatrolTaskDetailDTO taskDetailDTO) {
        removeOverlay();
        this.patrolTaskDetailDTO = taskDetailDTO;
        Integer validPath = patrolTaskDetailDTO.getValidPathLength();
        if (validPath != null && validPath > patrolTaskDetailDTO.getActualPathLength()) {
            validPath = patrolTaskDetailDTO.getActualPathLength();
        }
        if (validPath == null) {
            validPath = 0;
        }
        if (stateInterface != null) {
            /**
             * 1.如果是执行中的巡检，倒计时显示距离结束时间的倒计时.
             * 2.如果是其他状态，直接显示状态
             */
            if (taskDetailDTO.isStart()) {
                stateInterface.setStartDownTime(taskDetailDTO.getTimeCountdown());
            } else {
                stateInterface.setState(taskDetailDTO.getShowStatus(), taskDetailDTO.getExecuteStatus());
            }

            stateInterface.setCompleCount(taskDetailDTO.getInspectedCount() +
                    "/" + taskDetailDTO.getPointCount());
            stateInterface.setMileage(validPath / unitK + "KM");

        }

        drawPlanPath(taskDetailDTO.getPatrolTaskPlanPaths());
        drawPoints(taskDetailDTO.getPatrolPointDetailDTOs());
        drawActualPath(taskDetailDTO.getPatrolActualPath());
        if (taskDetailDTO != null && taskDetailDTO.isStart()) {
            sendTrace();
        }
        setScanLlVisibility(taskDetailDTO);
        handler.postDelayed(updateMapRunnable,
                PatrolConstans.UPDATE_MAP_DELAY);
        if (hasPatrolAuth()) {
            mPatrolOutsidePresenter.startCheckArrivedPoint();
        }
        if (isNfc()) {
            qrCode = getActivity().getIntent().getStringExtra("qrCode");
            mPresenter.getScanCode();
            getActivity().getIntent().putExtra("nfc", false);
        }

    }

    /**
     * 是否是nfc 进入
     *
     * @return
     */
    private boolean isNfc() {
        return getActivity().getIntent().getBooleanExtra("nfc", false);
    }

    private void removeOverlay() {
        if (overlaysList != null && overlaysList.size() > 0) {
            for (Overlay o : overlaysList) {
                if (o != null) {
                    o.remove();
                }
            }
            overlaysList.clear();
        }
    }

    /**
     * 延时更新地图handler.
     */
    private Handler handler = new Handler();

    private boolean isUpdateMap = true;
    /**
     *
     */
    private Runnable updateMapRunnable = new Runnable() {
        public void run() {
            if (mMapView != null && mMapView.getMap() != null && isUpdateMap) {
                mMapView.getMap().setMapStatus(MapStatusUpdateFactory
                        .newLatLngBounds(builder.build()));
            }

        }

    };


    /**
     * 绘制巡检点
     *
     * @param patrolPointDetailDTOs 巡检点列表
     */
    private void drawPoints(final List<PatrolPointDetailDTO> patrolPointDetailDTOs) {
        if (patrolPointDetailDTOs != null && patrolPointDetailDTOs.size() > 0) {
            for (int i = 0; i < patrolPointDetailDTOs.size(); i++) {
                PatrolPointDetailDTO dto = patrolPointDetailDTOs.get(i);
                LatLng location = new LatLng(dto.getLatitude(), dto.getLongitude());
                addPoint(location, R.mipmap.patrol_point_location, dto);
                builder.include(location);
            }

        }
    }

    private List<Overlay> overlaysList = new ArrayList<>();

    /**
     * 添加点到地图上
     *
     * @param cept       点的坐标
     * @param resourceId 图标资源文件
     * @param dto        坐标相关信息
     */
    private void addPoint(final LatLng cept, final int resourceId, final PatrolPointDetailDTO dto) {
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(resourceId);
        MarkerOptions ooD = new MarkerOptions()
                .position(cept)
                .icon(bitmap)
                .zIndex(0)
                .period(PatrolConstans.MARKOPTION_PERIOD);
        Marker mMarkerD = (Marker) (mMapView.getMap().addOverlay(ooD));
        overlaysList.add(mMarkerD);
        /**
         * 设置Marker的Title为巡检点的ID，方面点击时间后查找
         */
        mMarkerD.setTitle(dto.getId() + "");
//        mMapView.getMap().addOverlay(mMarkerD);
        OverlayOptions textOption = new TextOptions()
                .fontSize(PatrolConstans.MAP_TEXTOPTION_FONTSIZE)
                .fontColor(mContext.getResources().getColor(R.color.patrol_blue))
                .bgColor(mContext.getResources().getColor(R.color.patrol_mapwindow_bg))
                .text(dto.getPatrolPoint()).position(cept);
        overlaysList.add(mMapView.getMap().addOverlay(textOption));
    }

    /**
     * 绘制计划路线
     *
     * @param patrolPlanPaths 记录路线点
     */
    private void drawPlanPath(final List<PatrolPlanPath> patrolPlanPaths) {
        if (patrolPlanPaths != null && patrolPlanPaths.size() > 0) {
            List<LatLng> points = new ArrayList<>();
            for (int i = 0; i < patrolPlanPaths.size(); i++) {
                LatLng cept = new LatLng(patrolPlanPaths.get(i).getLatitude(),
                        patrolPlanPaths.get(i).getLongitude());
                if (i < patrolPlanPaths.size()) {
                    points.add(cept);
                }
                builder.include(cept);
            }
            if (points.size() > 1) {
                OverlayOptions ploylineOption = new PolylineOptions()
                        .points(points)
                        .width(PatrolConstans.PLOYLINE_WIDTH)
                        .color(mContext.getResources().getColor(R.color.patrol_actual_legend));
                overlaysList.add(mMapView.getMap().addOverlay(ploylineOption));
            }
        }
    }

    /**
     * 绘制巡检
     *
     * @param patrolActualPaths 实际轨迹列表
     */
    private void drawActualPath(final List<PatrolActualPath> patrolActualPaths) {
        if (patrolActualPaths != null && patrolActualPaths.size() > 0) {
            List<LatLng> points = new ArrayList<>();
            for (int i = 0; i < patrolActualPaths.size(); i++) {
                LatLng cept = new LatLng(patrolActualPaths.get(i).getLatitude(),
                        patrolActualPaths.get(i).getLongitude());
                if (i < patrolActualPaths.size()) {
                    points.add(cept);
                }
                builder.include(cept);
            }
            if (points.size() > 1) {
                OverlayOptions ploylineOption = new PolylineOptions()
                        .points(points)
                        .width(PatrolConstans.PLOYLINE_WIDTH)
                        .color(Color.parseColor("#6B7279"));
                overlaysList.add(mMapView.getMap().addOverlay(ploylineOption));
            }

        }
    }

    /**
     * @TODO
     */
    private void sendTrace() {
        Intent intent = new Intent(AppApplication.getContext(), PatrolTraceService.class);
        intent.putExtra("taskId", getTaskId());
        AppApplication.getContext().startService(intent);
    }

    /**
     * 设置扫码布局的可见性
     *
     * @param detailDTO 巡检任务详情
     */
    private void setScanLlVisibility(final PatrolTaskDetailDTO detailDTO) {
        if (detailDTO.canStart()) {
            mPatrolScanLl.setVisibility(View.VISIBLE);
            mylocationLl.setVisibility(View.VISIBLE);
            mPatrolNfcLl.setVisibility(View.GONE);
        } else {
            mPatrolScanLl.setVisibility(View.GONE);
            mylocationLl.setVisibility(View.GONE);
            mPatrolNfcLl.setVisibility(View.GONE);
        }
    }

    /**
     * 获取任务ID.
     *
     * @return
     */
    @Override
    public Integer getTaskId() {
        if (getActivity() == null || getActivity().getIntent() == null) {
            return -1;
        }
        int taskId = getActivity().getIntent().getIntExtra("taskId", -1);
        return taskId;
    }

    /**
     *
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        handler.removeCallbacks(updateMapRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
//        if (OffLineOutApiUtil.isOffLine()) {
//
//        } else {
        isUpdateMap = true;
        mPresenter.getPatrolDetailData();
//        }
    }


    /**
     *
     */
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        mPatrolOutsidePresenter.stopCheckArrivedPoint();
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LibConfig.SCAN_CODE && data != null) {
            qrCode = data.getStringExtra(CaptureActivity.SCAN_RESULT);
            mPresenter.getScanCode();
        } else if (requestCode == PatrolNfcActivity.PATROL_NFC && data != null) {
            qrCode = data.getStringExtra("NFCInfo");
            mPresenter.getScanCode();
        }
    }

    /**
     * 获取二维码
     *
     * @return
     */
    @Override
    public String getQRCode() {
        return qrCode;
    }

    /**
     * @return
     */
    @Override
    public boolean isFromMap() {
        return true;
    }

    /**
     * @param patrolPointId 巡检点ID
     * @return
     */
    @Override
    public boolean isTaskCompleteOthers(final Integer patrolPointId) {
        return mPatrolOutsidePresenter.isTaskCompleteOthers(patrolPointId);
    }

    @Override
    public boolean offLineIsTaskCompleteOthers(Integer patrolPointId) {
        return mPatrolOutsidePresenter.offLineIsTaskCompleteOthers(patrolPointId);
    }


    /**
     * @param view
     */
    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.patrol_scan_bt) {
            PermissionsProcessingUtil.setPermissions(this, LibConfig.CAMERA, new PermissionsProcessingUtil.OnPermissionsCallback() {
                @Override
                public void callBack(final boolean flag, final String permission) {
                    if (flag) {
                        ScanCodeUtils.openScanCode(getActivity());
                    } else {
                        ToastUtil.makeText(getActivity(), getString(R.string.permissions_camera));
                    }
                }
            });
        } else if (view.getId() == R.id.patrol_locate_position_iv) {
            setMyLocation();
        } else if (view.getId() == R.id.patrol_nfc_iv) {
            Intent intent = new Intent(getActivity(), PatrolNfcActivity.class);
            getActivity().startActivityForResult(intent, PatrolNfcActivity.PATROL_NFC);
        }
    }

    /**
     * 获取巡检点列表
     *
     * @return
     */
    @Override
    public List<PatrolPointDetailDTO> getPatrolPointList() {
        return patrolTaskDetailDTO != null ? patrolTaskDetailDTO.getPatrolPointDetailDTOs() : new ArrayList<PatrolPointDetailDTO>();
    }

    /**
     * @return 获取巡检任务名称.
     */
    @Override
    public String getTaskName() {
        String taskName = getActivity().getIntent().getStringExtra("taskName");
        return taskName;
    }

    @Override
    public void arrive(final PatrolPointDetailDTO arrivedPoint) {
        qrCode = "PP" + arrivedPoint.getId() + "";
        mPresenter.getScanCode();
    }

    @Override
    public void offLineArrive(PatrolPointDetailDTO arrivedPoint) {
        qrCode = "PP" + arrivedPoint.getPatrolPointId() + "";
        mPresenter.getScanCode();
    }

    @Override
    public void getPatrolDetailData() {
        mPresenter.getPatrolDetailData();
    }

    @Override
    public String getPatroEndTime() {
        return TimeUtil.getDateTimeStr(patrolTaskDetailDTO.getEndTimeFormat());
    }

    /**
     * todo  弹框的时候 需要震动
     */
    @Override
    public void showVibrator() {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(getActivity().VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
    }

    /**
     * 当前是否为可执行的
     *
     * @return
     */
    @Override
    public boolean hasPatrolAuth() {
        return patrolTaskDetailDTO != null ? patrolTaskDetailDTO.canStart() : false;
    }

    @Override
    public String getTaskEndTime() {
        return patrolTaskDetailDTO != null ? TimeUtil.getDateTimeStr(patrolTaskDetailDTO.getEndTimeFormat()) : null;
    }

    @Override
    public String getProcessName(int pointId) {
        if (patrolTaskDetailDTO != null && patrolTaskDetailDTO.getPatrolPointDetailDTOs() != null && patrolTaskDetailDTO.getPatrolPointDetailDTOs().size() > 0) {
            String processName = "";
            for (int i = 0; i < patrolTaskDetailDTO.getPatrolPointDetailDTOs().size(); i++) {
                PatrolPointDetailDTO entity = patrolTaskDetailDTO.getPatrolPointDetailDTOs().get(i);
                if (entity.getId() == pointId) {
                    processName = entity.getRelatedProcessNames();
                }
            }
            return processName;
        } else {
            return "";
        }
    }

    /**
     * 巡检点图层点击事件
     *
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        PatrolPointDetailDTO dto = mPatrolOutsidePresenter.onMarkerClick(marker.getTitle());
        if (dto != null) {
            mPresenter.onItemClick(dto);
        }
        return false;
    }


    public void setPatrolData(PatrolStateInterface stateInterface) {
        this.stateInterface = stateInterface;
    }

    public interface PatrolStateInterface {
        void setState(String showState, String state);

        void setCompleCount(String compleCount);

        void setMileage(String mileage);

        void setStartDownTime(String time);
    }
}
