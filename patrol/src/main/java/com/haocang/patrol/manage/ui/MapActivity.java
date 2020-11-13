package com.haocang.patrol.manage.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.haocang.base.ui.BaseActivity;
import com.haocang.patrol.R;

import org.feezu.liuli.timeselector.Utils.TextUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 地图轨迹
 */
public class MapActivity extends BaseActivity {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Intent i;
    private int position;
    private String name, code;
    private String taskName;

    @Override
    protected void doOnCreate() {
        setContentView(R.layout.activity_map);
        i = getIntent();
        position = i.getIntExtra("id", 0);
        taskName = i.getStringExtra("taskName");
        name = i.getStringExtra("name");
        code = i.getStringExtra("code");
        setView();
        initLoc();
    }


    private void setView() {
        TextView titleNameTv = findViewById(R.id.title_common_tv);
        titleNameTv.setText(taskName);
        mMapView = findViewById(R.id.map_view);
        mBaiduMap = mMapView.getMap();
    }


    //定位
    private void initLoc() {
        mBaiduMap.setMyLocationEnabled(true);

        //构建折线点坐标
        LatLng endP;
        LatLng p1 = new LatLng(36.317096,120.151824);
        LatLng p2 = new LatLng(36.31347, 120.149446);
        LatLng p3 = new LatLng(36.311841, 120.146895);
        LatLng startP = new LatLng(36.310707, 120.147542);

        LatLng p5 = new LatLng(36.307973, 120.14941);
        LatLng p6 = new LatLng(36.305501, 120.15038);
        LatLng p7 = new LatLng(36.303974, 120.151979);
        List<LatLng> points = new ArrayList<LatLng>();

        if (position == 0) {
            endP = new LatLng(36.318768, 120.151878);
            points.add(endP);
            points.add(p1);
            points.add(p2);
            points.add(p3);
            points.add(startP);
        } else {
            endP = new LatLng(36.300513, 120.152267);
            points.add(startP);
            points.add(p5);
            points.add(p6);
            points.add(p7);
            points.add(endP);
        }
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(startP)
                .zoom(16.5f)
                .build();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(mMapStatus));

        //设置折线的属性
        int mainColor = Color.parseColor("#4f94f7");
        OverlayOptions mOverlayOptions = new PolylineOptions()
                .width(10)
                .color(mainColor)
                .points(points);
        //绘制折线
        mBaiduMap.addOverlay(mOverlayOptions);


        //起点图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_map_start_blue);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(startP)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);


        //名字
        Bundle bundle = new Bundle();
        bundle.putString("name", name);

        //人图标
        BitmapDescriptor b = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_loc_my);
        OverlayOptions optionWPL = new MarkerOptions()
                .position(endP)
                .extraInfo(bundle)
                .icon(b);
        mBaiduMap.addOverlay(optionWPL);

        endP = new LatLng(endP.latitude - 0.0004, endP.longitude);
        OverlayOptions mTextOptions = new TextOptions()
                .text(name) //文字内容
                .bgColor(mainColor) //背景色
                .fontSize(24) //字号
                .extraInfo(bundle)
                .fontColor(Color.parseColor("#ffffff")) //文字颜色
                .rotate(0) //旋转角度
                .position(endP);

//在地图上显示文字覆盖物
        mBaiduMap.addOverlay(mTextOptions);

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mBaiduMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = marker.getExtraInfo();
                if (bundle != null) {
                    String name = bundle.getString("name");
                    if (!TextUtil.isEmpty(name)) {
                        mBaiduMap.hideInfoWindow();
                        //显示信息窗
                        View v = getLayoutInflater().inflate(R.layout.layout_info_map_window, null);
                        TextView titleTv = (TextView) v.findViewById(R.id.name_tv);
                        TextView content_tv = (TextView) v.findViewById(R.id.content_tv);
                        titleTv.setText("当前巡检人：" + name);
                        String itemTotalStr = "所属组织：青岛黄水东调"
                                + "\n所属部门：运维部"
                                + "\n工单名称：" + taskName
                                + "\n巡检编号：" + code
                                + "\n手  机  号：" + (position == 0 ? "15984786756" : "13765987269")
                                + "\n巡检内容：" + "查看泵站运行状态";
                        content_tv.setText(itemTotalStr);
                        BitmapDescriptor infoWB = BitmapDescriptorFactory.fromView(v);

                        //定义信息窗
                        InfoWindow infoWindow = new InfoWindow(infoWB, marker.getPosition(), -70, null
                        );
                        //显示信息窗
                        mBaiduMap.showInfoWindow(infoWindow);
                        return true;
                    }
                }

                return false;
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
}

