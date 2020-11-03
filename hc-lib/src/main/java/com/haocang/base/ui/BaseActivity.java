package com.haocang.base.ui;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.haocang.base.R;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;
import com.haocang.base.config.OnReceiveVoiceListener;
import com.haocang.base.utils.DensityUtil;
import com.haocang.base.utils.NetBroadcastReceiver;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.SpeechServiceSingleton;
import com.haocang.base.utils.StatusBarUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.utils.VoiceUtil;

/**
 * activity基类.
 */
public abstract class BaseActivity
        extends FragmentActivity implements SensorEventListener,
        SpeechServiceSingleton.OnSpeechResult,
        PermissionsProcessingUtil.OnPermissionsCallback, VoiceUtil.OnSpeakCompleteListener {

    /**
     * 标记按下返回是否需要弹出再按一次退出应用.
     */
    public static final String SLID_FLAG = "slideFlag";
    /**
     * 获取一个fragment.
     */
    protected Fragment fragment;
    private int distance = 100;
    private NetBroadcastReceiver netWorkStateReceiver;

    private long exitTime = 0;
    private SensorManager mSensorManager;//定义sensor管理器
    private Vibrator vibrator;           //震动
    private Context mContext;
    private VoiceUtil voiceUtil;
    /**
     * 网络类型
     */
    private int netMobile;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    protected void onCreate(final Bundle savedInstanceState) {
        mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        StatusBarUtil.setTranslucentStatus(this);
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }
        super.onCreate(savedInstanceState);
        voiceUtil = new VoiceUtil(this);
        voiceUtil.setmOnSpeakCompleteListener(this);
        String fragmentName = getIntent().getStringExtra("fragmentName");
        if (!TextUtils.isEmpty(fragmentName)) {
            try {
                fragment = (Fragment) Class.forName(fragmentName).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        distance = DensityUtil.dip2px(this, distance);
        AppApplication.getInstance().addActivity(this);
        //获取传感器管理服务
        mSensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        //震动
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        doOnCreate();
    }

    protected void clearAllFragment() {
        while (getSupportFragmentManager().popBackStackImmediate()) {
            getSupportFragmentManager().popBackStack();
        }
    }

    /**
     * 设置任务栏的透明.
     */
    protected void setTaskBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            Window window = getWindow();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //根据上面设置是否对状态栏单独设置颜色
//            if (LibConfig.IS_UPDATE_TASK_FONT_COLOR) {
//                window.setStatusBarColor(Color.parseColor("#ffffff"));
//            } else {
            window.setStatusBarColor(Color.TRANSPARENT);
//            }
        }
    }


    protected abstract void doOnCreate();

    protected void actionBarBack() {
        back();
    }

    public void back(View v) {
        back();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("RestrictedApi")
    public void back() {
        boolean firstFlag = getIntent().getBooleanExtra("FIRST_FLAG", false);
        if (!firstFlag) {
            getSupportFragmentManager().getFragments();
            if (getSupportFragmentManager().popBackStackImmediate()) {
                getSupportFragmentManager().popBackStack();
            } else {
                AppApplication.getInstance().finishActivity(this);
            }
        } else if (firstFlag && (System.currentTimeMillis() - exitTime) > LibConstants.Base.EXIT_PROGRAM_TIP_INTERVAL) {
            ToastUtil.makeText(getApplicationContext(), getApplicationContext().getString(R.string.exit_program_tip));
            exitTime = System.currentTimeMillis();
        } else {
//            AppApplication.getInstance().finishActivity(this);  //todo 关闭app
//            moveTaskToBack(false);
            AppApplication.getInstance().setUpgradeTips(true);
            Intent home = new Intent(Intent.ACTION_MAIN); //todo 改成回到桌面
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
        }

    }

    @Override
    protected void onPause() {
        //取消注册
        mSensorManager.unregisterListener(this);
//        stopFlowMode();
        if (speechServiceSingleton != null) {
            speechServiceSingleton.dismiss();
        }
        super.onPause();
    }

    public void setVoiceParam() {

        if (voiceUtil != null) {
            voiceUtil.setParam();
        }
    }

    @Override
    protected void onResume() {
//        setVoiceParam();
        //加速度传感器 注册监听
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                //还有SENSOR_DELAY_UI、SENSOR_DELAY_FASTEST、SENSOR_DELAY_GAME等，
                //根据不同应用，需要的反应速率不同，具体根据实际情况设定
                SensorManager.SENSOR_DELAY_NORMAL);
//        if (AppApplication.getInstance().isFlowMode()) {
//            startFlowMode();
//        }
        super.onResume();
    }

    @SuppressLint("RestrictedApi")
    public void pop() {
        getSupportFragmentManager().getFragments();
        if (getSupportFragmentManager().popBackStackImmediate()) {
            // getSupportFragmentManager().popBackStack();
        }
    }


    protected Fragment getFragmentByName(final String fragmentName) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) Class.forName(fragmentName).newInstance();
        } catch (Exception e) {

        }
        return fragment;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            voiceUtil.onDestroy();
            if (speechServiceSingleton != null) {
                speechServiceSingleton.destroy();
            }
        } catch (Exception e) {

        }
        AppApplication.getInstance().finishActivity(this);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private long lastShakeTime = System.currentTimeMillis();
    private long firstShakeTime = -1;
    private long secondShakeTime = -1;
    private int shakeCount = 0;
    private boolean isShake = false;


    //可以得到传感器实时测量出来的变化值
    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        int sensorType = event.sensor.getType();
        //values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = event.values;
        long interval = System.currentTimeMillis() - lastShakeTime;
        OnReceiveVoiceListener listener = AppApplication.getInstance().getOnReceiveVoiceListener();
        if (listener != null) {
            if (sensorType == Sensor.TYPE_ACCELEROMETER) {
                /*因为一般正常情况下，任意轴数值最大就在9.8~10之间，只有在你突然摇动手机
                 *的时候，瞬时加速度才会突然增大或减少，所以，经过实际测试，只需监听任一轴的
                 * 加速度大于14的时候，改变你需要的设置就OK了
                 */
                if (isShake && (Math.abs(values[0]) > 0 && Math.abs(values[1]) > 0 && Math.abs(values[2]) > 0)) {
                    isShake = false;
                    shakeCount = 0;
                    lastShakeTime = System.currentTimeMillis();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isShake = false;
                            vibrator.vibrate(500);
                            if (ContextCompat.checkSelfPermission(mContext, LibConfig.AUDIO)
                                    != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(BaseActivity.this,
                                        new String[]{LibConfig.AUDIO},
                                        PERMISSIONS_REQUEST_AUDIO);
                            } else {
                                showSpeechDialog();

                            }
                        }
                    }, 200);

                } else if ((Math.abs(values[0]) > AppApplication.getInstance().getThreshhold()
                        || Math.abs(values[1]) > AppApplication.getInstance().getThreshhold()
                        || Math.abs(values[2]) > AppApplication.getInstance().getThreshhold())) {
                    if (shakeCount == 0 && (System.currentTimeMillis() - lastShakeTime) > LibConfig.SHAKE_INTERVAL) {
                        firstShakeTime = System.currentTimeMillis();
                        shakeCount = 1;
                    } else if (shakeCount > 0
                            && (System.currentTimeMillis() - firstShakeTime) > LibConfig.CONTINUITY_INTERVAL) {
//                    secondShakeTime = System.currentTimeMillis();
                        if ((System.currentTimeMillis() - firstShakeTime) > LibConfig.CONTINUITY_MAX_INTERVAL) {
                            shakeCount = 1;
                            firstShakeTime = System.currentTimeMillis();
                        } else {
                            shakeCount = 2;
                            secondShakeTime = System.currentTimeMillis();
                            isShake = true;
                        }

                    } else if (shakeCount > 1 && (System.currentTimeMillis() - secondShakeTime) > LibConfig.CONTINUITY_INTERVAL) {
                        isShake = true;
                    }
                }
            }
        }
    }

    //    private RecognizerDialog speechDialog;
    private SpeechServiceSingleton speechServiceSingleton;

    protected void showSpeechDialog() {
        if (speechServiceSingleton == null) {
            speechServiceSingleton = new SpeechServiceSingleton(this, this);
        }
        speechServiceSingleton.show(startFlowModeFlag);

    }

    private static final int PERMISSIONS_REQUEST_AUDIO = 1;

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //当传感器精度改变时回调该方法，Do nothing.
    }

    @Override
    public void callBack(final boolean flag, final String permission) {
        if (flag) {
            showSpeechDialog();
//            SpeechService.btnVoice(this, this);
        } else {
            ToastUtil.makeText(this, getResources().getString(R.string.permissions_audio));
        }
    }


    @Override
    public void onSpeechResult(final String result) {
        OnReceiveVoiceListener listener = AppApplication.getInstance().getOnReceiveVoiceListener();
        if (listener != null) {
            listener.receiveVoice(result);
        }
    }

    protected String getRouterUrl(String result) {
        String router = null;

        return router;
    }

    public void speak(String words) {
        stopFlowMode();
        if (voiceUtil != null) {
            voiceUtil.speak(words);
        }
    }

    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            showSpeechDialog();
            try {
                handler.postDelayed(runnable, LibConfig.VOICE_DIALOG_REFRESHTIME);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };


    private boolean startFlowModeFlag;

    public void startFlowMode() {
        if (AppApplication.getInstance().isFlowMode()) {
            startFlowModeFlag = true;
            handler.postDelayed(runnable, LibConfig.VOICE_DIALOG_REFRESHTIME);
        }

    }

//    public int getVoiceDialogRefreshTime() {
//        return voiceDialogRefreshTime;
//    }
//
//    public void setVoiceDialogRefreshTime(int voiceDialogRefreshTime) {
//        this.voiceDialogRefreshTime = voiceDialogRefreshTime;
//    }

//    private int voiceDialogRefreshTime = 500;

    public void stopFlowMode() {
        if (startFlowModeFlag) {
            try {
                startFlowModeFlag = false;
                handler.removeCallbacks(runnable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void speakComplete() {
        if (AppApplication.getInstance().isFlowMode()) {
            startFlowMode();
        }
    }


}
