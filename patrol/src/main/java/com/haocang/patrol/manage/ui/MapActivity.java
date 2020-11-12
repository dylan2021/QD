package com.haocang.patrol.manage.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.haocang.base.ui.BaseActivity;
import com.haocang.patrol.R;

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
    private String name;

    @Override
    protected void doOnCreate() {
        setContentView(R.layout.activity_map);
        i = getIntent();
        position = i.getIntExtra("id", 0);
        name = i.getStringExtra("name");
        setView();
        initLoc();
    }


    private void setView() {
        TextView titleNameTv = findViewById(R.id.title_common_tv);
        titleNameTv.setText(i.getStringExtra("taskName"));

        mMapView = findViewById(R.id.map_view);
        mBaiduMap = mMapView.getMap();
    }


    //定位
    private void initLoc() {
        mBaiduMap.setMyLocationEnabled(true);

        //构建折线点坐标
        LatLng endP;
        LatLng p1 = new LatLng(36.317105, 120.151746);
        LatLng p2 = new LatLng(36.31347, 120.149446);
        LatLng p3 = new LatLng(36.311841, 120.146895);
        LatLng p4 = new LatLng(36.310707, 120.147542);

        LatLng p5 = new LatLng(36.307973, 120.14941);
        LatLng p6 = new LatLng(36.305501, 120.15038);
        LatLng p7 = new LatLng(36.303974, 120.151979);
        List<LatLng> points = new ArrayList<LatLng>();

        if (position == 0) {
            endP = new LatLng(36.323517, 120.154135);
            points.add(endP);
            points.add(p1);
            points.add(p2);
            points.add(p3);
            points.add(p4);
        } else {
            endP = new LatLng(36.300513, 120.152267);
            points.add(p4);
            points.add(p5);
            points.add(p6);
            points.add(p7);
            points.add(endP);

        }
        MapStatus mMapStatus = new MapStatus.Builder()
                //要移动的点
                .target(p4)
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
                .position(p4)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);

        //人图标
        BitmapDescriptor b = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_loc_my);
        OverlayOptions optionWPL = new MarkerOptions()
                .position(endP)
                .icon(b);
        mBaiduMap.addOverlay(optionWPL);

        //名字
        endP = new LatLng(endP.latitude - 0.0004, endP.longitude);
        OverlayOptions mTextOptions = new TextOptions()
                .text(name) //文字内容
                .bgColor(mainColor) //背景色
                .fontSize(24) //字号
                .fontColor(Color.parseColor("#ffffff")) //文字颜色
                .rotate(0) //旋转角度
                .position(endP);

//在地图上显示文字覆盖物
         mBaiduMap.addOverlay(mTextOptions);
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

