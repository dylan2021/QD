package com.haocang.curve.main.ui;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import androidx.core.view.ViewCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.ui.BaseActivity;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.BaseDialog;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.curve.R;
import com.haocang.curve.main.bean.CurveConstans;
import com.haocang.curve.main.iview.CurveView;
import com.haocang.curve.main.presenter.impl.CurvePresenterImpl;
import com.haocang.curve.more.bean.PointEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wendu.dsbridge.DWebView;


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
 * 创建时间：2018/5/31下午3:23
 * 修  改  者：
 * 修改时间：
 */
@Route(path = ArouterPathConstants.Curve.CURVE_MAIN)
public class CurveActivity extends BaseActivity
        implements View.OnClickListener, CurveView {
    /**
     * 当前选择的时间.
     */
    private TextView timeTv;
    private TextView titletv;
    /**
     * 周期选择.
     */
    private Spinner sycleSp;
    /**
     * 曲线webview.
     */
    private DWebView curveWv;
    /**
     *
     */
    private CurvePresenterImpl curvePresenter;

    /**
     *
     */
    private TextView cycleTv;

    /**
     * 更多曲线
     */
    private static final int REQUESTCODE = 3002;

    private List<PointEntity> selectedPoints;

    private View pickMoreLl;

    private JSONObject object;

    /**
     *
     */
    @Override
    protected void doOnCreate() {

        setContentView(R.layout.curve_activity);
//        setTaskBar();
        ARouter.getInstance().inject(this);
        curvePresenter = new CurvePresenterImpl();
        curvePresenter.setView(this);
        setView();
    }


    /**
     *
     */
    private void setView() {
        titletv = findViewById(R.id.curve_title_tv);
        if (getIntent().getStringExtra("combineName") != null) {
            titletv.setText(getIntent().getStringExtra("combineName"));
        }
        timeTv = findViewById(R.id.curve_time_tv);
        sycleSp = findViewById(R.id.curve_cycle_sp);
        cycleTv = findViewById(R.id.curve_cycle_tv);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.curve_cycle));
        sycleSp.setAdapter(adapter);
        pickMoreLl = findViewById(R.id.curve_pick_more_ll);
        findViewById(R.id.common_iv).setOnClickListener(this);
        findViewById(R.id.common_iv).setVisibility(View.GONE);
        findViewById(R.id.curve_calendar_iv).setOnClickListener(this);
        findViewById(R.id.curve_left_arrow_iv).setOnClickListener(this);
        findViewById(R.id.curve_right_arrow_iv).setOnClickListener(this);
        findViewById(R.id.curve_refresh_iv).setOnClickListener(this);
        findViewById(R.id.curve_refresh_tv).setOnClickListener(this);
        findViewById(R.id.curve_pick_collection_iv).setOnClickListener(this);
        findViewById(R.id.curve_pick_colletion_tv).setOnClickListener(this);
        findViewById(R.id.curve_pick_collection_ll).setOnClickListener(this);
        findViewById(R.id.back_to_current_tv).setOnClickListener(this);
        findViewById(R.id.curve_back_to_current_iv).setOnClickListener(this);
        findViewById(R.id.curve_pick_more_ll).setOnClickListener(this);
        timeTv.setOnClickListener(this);
        cycleTv.setOnClickListener(this);
        curveWv = findViewById(R.id.curve_webview);
        curveWv.setBackgroundColor(Color.WHITE);
        curveWv.getSettings().setAppCacheEnabled(false);//是否使用缓存
        DWebView.setWebContentsDebuggingEnabled(true);
//        curveWv.loadUrl("http://192.168.30.149:5500/APP");
        curveWv.loadUrl("file:///android_asset/index.html");
        setPoint();
        curvePresenter.setDefault();
        curvePresenter.loadData();
        curveWv.addJavascriptObject(new JsApi(), null);
    }


    public class JsApi {

        @JavascriptInterface()
        public void addTagging(Object msg) {
            try {
                JSONObject jsonObject = new JSONObject(msg.toString());
                String time = jsonObject.optString("x");
                String value = jsonObject.optString("y");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
                String sd = sdf.format(new Date(Long.parseLong(String.valueOf(time))));   // 时间戳转换成时间
                String mpointId = jsonObject.optString("mpointId");
                object = new JSONObject();
                object.put("value", value);
                object.put("point", mpointId);
                object.put("time", TimeTransformUtil.getUploadGMTTime(sd));
//                showTagginDialog(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void showTagginDialog(final JSONObject object) {
        final BaseDialog baseDialog = new BaseDialog(this, R.style.BaseDialog, R.layout.dialog_taggin);
        baseDialog.setCanceledOnTouchOutside(true);
        final EditText remarkEt = baseDialog.findViewById(R.id.remark_et);
        TextView submitTv = baseDialog.findViewById(R.id.submit_tv);
        submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(remarkEt.getText().toString())) {
                    try {
                        object.put("remark", remarkEt.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    curvePresenter.submitTaggin(object);
                    closeKeybord(baseDialog, remarkEt);
                } else {
                    ToastUtil.makeText(CurveActivity.this, "请输入内容");
                }

            }
        });
        openKeybord(remarkEt);
        Window window = baseDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        baseDialog.show();
    }


    private void setPoint() {
        String selectedPointsStr = getIntent().getStringExtra("selectedPointsStr");
        if (!TextUtils.isEmpty(selectedPointsStr)) {
            Type type = new TypeToken<List<PointEntity>>() {
            }.getType();
            setSelectedPoints((List<PointEntity>) new Gson().fromJson(selectedPointsStr, type));
        }
    }

    /**
     * @return 选中的测点列表.
     */
    public List<PointEntity> getSelectedPoints() {
        if (selectedPoints == null) {
            setPoint();
        }
        return selectedPoints;
    }

    /**
     * @param v
     */
    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.common_iv) {
            curvePresenter.showMore(findViewById(R.id.common_iv));
        } else if (v.getId() == R.id.curve_calendar_iv
                || v.getId() == R.id.curve_time_tv) {
            curvePresenter.showPickTimeView();
        } else if (v.getId() == R.id.curve_left_arrow_iv) {
            curvePresenter.pre();
        } else if (v.getId() == R.id.curve_right_arrow_iv) {
            curvePresenter.next();
        } else if (v.getId() == R.id.curve_cycle_tv) {
            curvePresenter.showSelectCycleView();
        } else if (v.getId() == R.id.back_to_current_tv
                || v.getId() == R.id.curve_back_to_current_iv) {
            curvePresenter.backToCurrent();
        } else if (v.getId() == R.id.curve_pick_collection_iv
                || v.getId() == R.id.curve_pick_colletion_tv
                || v.getId() == R.id.curve_pick_collection_ll) {
            curvePresenter.pickCollection();
        } else if (v.getId() == R.id.curve_refresh_iv
                || v.getId() == R.id.curve_refresh_tv) {
            curvePresenter.loadData();
        } else if (v.getId() == R.id.curve_pick_more_ll) {
            toMore();
        }
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(final int requestCode,
                                 final int resultCode,
                                 final Intent data) {
        if ((requestCode == CurveConstans.PICK_COLLECTION_REQUEST_CODE
                || resultCode == CurveConstans.PICK_COLLECTION_REQUEST_CODE) && data != null) {
            setPoint();
            String selectedPointsStr = data.getStringExtra("selectedPointsStr");
            if (!TextUtils.isEmpty(selectedPointsStr)) {
                Type type = new TypeToken<List<PointEntity>>() {
                }.getType();
                setSelectedPoints((List<PointEntity>) new Gson().fromJson(selectedPointsStr, type));
            }
            String combineName = data.getStringExtra("combineName");
            setCombineName(combineName);
//            if (!TextUtils.isEmpty(combineName)) {
//                setCombineName(combineName);
//            }
            curvePresenter.loadData();
        } else if (requestCode == 3002 && data != null) {
            String selectedPointsStr = data.getStringExtra("selectedPointsStr");
            if (!TextUtils.isEmpty(selectedPointsStr)) {
                Type type = new TypeToken<List<PointEntity>>() {
                }.getType();
                List<PointEntity> list = new Gson().fromJson(selectedPointsStr, type);
                for (PointEntity e : list) {
                    if (e.getMpointId() == null) {
                        e.setMpointId(e.getId() + "");
                    }
                }
                setSelectedPoints(list);

//                selectedPoints = new Gson().fromJson(selectedPointsStr, type);
            }
            curvePresenter.loadData();
            setCombineName(null);
        }
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        ScreenUtil.lockOrientation(this);
//        ScreenUtil.unlockOrientation(this);
    }

    /**
     * @return 上下文参数
     */
    @Override
    public Context getContext() {
        return this;
    }

    /**
     * @param dayStr 选择的时间.
     */
    @Override
    public void setSelectTime(final String dayStr) {
        timeTv.setText(dayStr);
    }

    /**
     * @param cycle 周期.
     */
    @Override
    public void setCycle(final String cycle) {
        cycleTv.setText(cycle);
    }

    /**
     * @return
     */
    @Override
    public String getSelectPoints() {
        String mPointIds = "";
        if (getSelectedPoints() != null && getSelectedPoints().size() > 0) {
            for (PointEntity point : getSelectedPoints()) {
                if (point.getMpointId() != null) {
                    mPointIds += point.getMpointId() + ",";
                    continue;
                } else if (point.getId() > 0) {
                    mPointIds += point.getId() + ",";
                    continue;
                }
            }
            if (mPointIds.length() > 0) {
                mPointIds = mPointIds.substring(0, mPointIds.length() - 1);
            }
        }
        return mPointIds;
    }

    private String getPointName() {
        String pointNames = "";
        if (getSelectedPoints() != null && getSelectedPoints().size() > 0) {
            for (PointEntity point : getSelectedPoints()) {
                if (point.getMpointId() != null) {
                    pointNames += point.getMpointName() + ",";
                    continue;
                } else if (point.getId() > 0) {
                    pointNames += point.getMpointName() + ",";
                    continue;
                }
            }
            if (pointNames.length() > 0) {
                pointNames = pointNames.substring(0, pointNames.length() - 1);
            }
        }
        return pointNames;
    }

    @Override
    public void toMore() {
        Map<String, Object> map = new HashMap<>();
        map.put("fragmentUri", ArouterPathConstants.Curve.CURVE_POINT_LIST);
        map.put("selectedPointsStr", new Gson().toJson(selectedPoints));
        ARouterUtil.startActivityForResult(map, this, REQUESTCODE);
    }

    @Override
    public void toShare() {
//        Map<String, Object> map = new HashMap<>();
//        ARouterUtil.toActivity(null, ArouterPathConstants.Curve.CURVE_SHARE);
        Map<String, Object> map = new HashMap<>();
        map.put("cycle", curvePresenter.getCycle());
        map.put("pointIds", getSelectPoints());
        map.put("contents", getPointName());
        map.put("month", timeTv.getText().toString());//获取月份
        map.put("titles", "曲线分享");
        map.put("fragmentUri", ArouterPathConstants.Curve.CURVE_SHAPRE_SETUP);
        ARouterUtil.toFragment(map);
    }

    @Override
    public void setData(JSONArray array, String datasStr) {
        pickMoreLl.setVisibility(View.GONE);
        curveWv.setVisibility(View.VISIBLE);
        JSONObject type = new JSONObject();
        Type classType = new TypeToken<List<PointEntity>>() {
        }.getType();
        List<PointEntity> list = new Gson().fromJson(datasStr, classType);
        setSelectedPoints(list);
        try {
            type.put("start", curvePresenter.getStartTimeStr());
            type.put("end", curvePresenter.getEndTimeStr());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        curveWv.callHandler("drawLine", new Object[]{array, type});


    }

    @Override
    public List<PointEntity> getSelectPointList() {
        return selectedPoints;
    }

    Map<String, PointEntity> pointMap = new HashMap<>();

    @Override
    public void setSelectedPoints(List<PointEntity> list) {
        selectedPoints = list;
        if (selectedPoints != null) {
            pointMap.clear();
            for (PointEntity pointEntity : selectedPoints) {
                pointMap.put(pointEntity.getMpointId(), pointEntity);
            }
        }
    }


    @Override
    public void setNoData() {
//        pickMoreLl.setVisibility(View.VISIBLE);
        curveWv.setVisibility(View.GONE);
    }


    private String combineName;

    @Override
    public void setCombineName(String combineName) {
        this.combineName = combineName;
        if (!TextUtils.isEmpty(combineName)) {
            titletv.setText(combineName);
        } else {
            titletv.setText(getString(R.string.curve_title));
            this.combineName = null;
        }

    }

    @Override
    public String getCombineName() {
        return combineName;
    }

    @Override
    public void startTaggin() {
        if (object != null) {
            showTagginDialog(object);
        } else {
            ToastUtil.makeText(this, "请选择需要添加备注的点");
        }

    }

    @Override
    public void refreshCurve() {
        curvePresenter.loadData();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("TAG", "I'm Android 2.3");

    }

    protected void setTaskBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            //由于setStatusBarColor()这个API最低版本支持21, 本人的是15,所以如果要设置颜色,自行到style中通过配置文件设置
            window.setStatusBarColor(getResources().getColor(R.color.color_blue));
            ViewGroup mContentView = findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
                ViewCompat.setFitsSystemWindows(mChildView, true);
            }
        }
    }

    private void openKeybord(final EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        final InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 300);

    }

    private void closeKeybord(final BaseDialog baseDialog, final EditText editText) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    InputMethodManager m = (InputMethodManager)
                            editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    m.hideSoftInputFromWindow(editText.getWindowToken(), 0);
//            m.hideSoftInputFromWindow(context.getWindowToken(), 0);
                    View view = getCurrentFocus();
                    if (view == null) {
                        view = new View(CurveActivity.this);
                    }
                    m.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    baseDialog.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 300);

    }

}