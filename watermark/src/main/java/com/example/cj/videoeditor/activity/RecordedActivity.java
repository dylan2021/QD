package com.example.cj.videoeditor.activity;

import android.content.Intent;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cj.videoeditor.Constants;
import com.example.cj.videoeditor.R;
import com.example.cj.videoeditor.WaterMark;
import com.example.cj.videoeditor.camera.SensorControler;
import com.example.cj.videoeditor.gpufilter.SlideGpuFilterGroup;
import com.example.cj.videoeditor.gpufilter.helper.MagicFilterType;
import com.example.cj.videoeditor.widget.CameraView;
import com.example.cj.videoeditor.widget.CircularProgressView;
import com.example.cj.videoeditor.widget.FocusImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by cj on 2017/7/25.
 * desc 视频录制
 * 主要包括 音视频录制、断点续录、对焦等功能
 */

public class RecordedActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener, SensorControler.CameraFocusListener, SlideGpuFilterGroup.OnFilterChangeListener {

    private CameraView mCameraView;
    private CircularProgressView mCapture;
    private FocusImageView mFocus;
    private ImageView mCameraChange;
    private static final int maxTime = 10000;//最长录制20s
    private boolean pausing = false;


    private boolean recordFlag = false;//是否正在录制

    private int WIDTH = 720, HEIGHT = 1280;

    private long timeStep = 50;//进度条刷新的时间
    long timeCount = 0;//用于记录录制时间
    private boolean autoPausing = false;
    ExecutorService executorService;
    private SensorControler mSensorControler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorde);
        executorService = Executors.newSingleThreadExecutor();
        mSensorControler = SensorControler.getInstance();
        mSensorControler.setCameraFocusListener(this);
        initView();
    }

    private void initView() {
        mCameraView = (CameraView) findViewById(R.id.camera_view);
        mCameraView.setWaterMark(Constants.loadBitmapFromView(new WaterMark(this, getIntent()).getWaterMarkView()));
        mCapture = (CircularProgressView) findViewById(R.id.mCapture);
        mFocus = (FocusImageView) findViewById(R.id.focusImageView);
        mCameraChange = (ImageView) findViewById(R.id.btn_camera_switch);
        mCameraView.setOnTouchListener(this);
        mCameraView.setOnFilterChangeListener(this);
        mCameraChange.setOnClickListener(this);
        mCapture.setTotal(maxTime);
        mCapture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!recordFlag) {
                            executorService.execute(recordRunnable);
                        } else if (!pausing) {
                            mCameraView.pause(false);
                            pausing = true;
                        } else {
                            mCameraView.resume(false);
                            pausing = false;
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        //释放了
                        recordFlag = false;
                        break;
                }
                return true;
            }

        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mCameraView.onTouch(event);
        if (mCameraView.getCameraId() == 1) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                float sRawX = event.getRawX();
                float sRawY = event.getRawY();
                float rawY = sRawY * Constants.screenWidth / Constants.screenHeight;
                float temp = sRawX;
                float rawX = rawY;
                rawY = (Constants.screenWidth - temp) * Constants.screenHeight / Constants.screenWidth;

                Point point = new Point((int) rawX, (int) rawY);
                mCameraView.onFocus(point, callback);
                mFocus.startFocus(new Point((int) sRawX, (int) sRawY));
                break;

        }
        return true;
    }

    Camera.AutoFocusCallback callback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {
            //聚焦之后根据结果修改图片
            Log.e("hero", "----onAutoFocus====" + success);
            if (success) {
                mFocus.onFocusSuccess();
            } else {
                //聚焦失败显示的图片
                mFocus.onFocusFailed();

            }
        }
    };

    @Override
    public void onFocus() {
        //切换滤镜
//        if (mCameraView.getCameraId() == 1) {
//            return;
//        }
//        Point point = new Point(Constants.screenWidth / 2, Constants.screenHeight / 2);
//        mCameraView.onFocus(point, callback);
    }

    @Override
    public void onBackPressed() {
        if (recordFlag) {
//            recordFlag = false;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraView.onResume();
        if (recordFlag && autoPausing) {
            mCameraView.resume(true);
            autoPausing = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (recordFlag && !pausing) {
            mCameraView.pause(true);
            autoPausing = true;
        }
        mCameraView.onPause();
    }

    @Override
    public void onFilterChange(final MagicFilterType type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (type == MagicFilterType.NONE) {
                } else {
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_camera_switch) {
            mCameraView.switchCamera();
            if (mCameraView.getCameraId() == 1) {
                //前置摄像头 使用美颜
                mCameraView.changeBeautyLevel(3);
            } else {
                //后置摄像头不使用美颜
                mCameraView.changeBeautyLevel(0);
            }
        } else if (id == R.id.mCapture) {
            if (!recordFlag) {
                executorService.execute(recordRunnable);
            } else if (!pausing) {
                mCameraView.pause(false);
                pausing = true;
            } else {
                mCameraView.resume(false);
                pausing = false;
            }
        }
    }

    Runnable recordRunnable = new Runnable() {
        @Override
        public void run() {
            recordFlag = true;
            pausing = false;
            autoPausing = false;
            timeCount = 0;
            long time = System.currentTimeMillis();
            String savePath = Constants.getPath("Mango/", time + ".mp4");

            try {
                mCameraView.setSavePath(savePath);
                mCameraView.startRecord();
                while (timeCount <= maxTime && recordFlag) {
                    if (pausing || autoPausing) {
                        continue;
                    }
                    mCapture.setProcess((int) timeCount);
                    Thread.sleep(timeStep);
                    timeCount += timeStep;
                }
                recordFlag = false;
                mCameraView.stopRecord();
                if (timeCount < 2000) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RecordedActivity.this, "录像时间太短", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    recordComplete(savePath);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void recordComplete(final String path) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mCapture.setProcess(0);
                Intent intent = new Intent();
                intent.putExtra("videoUrl", path);
                setResult(1039, intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);

            }
        });
    }

}
