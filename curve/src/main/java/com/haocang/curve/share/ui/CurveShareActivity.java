package com.haocang.curve.share.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.curve.R;
import com.haocang.curve.share.adapter.CurveShareAdapter;
import com.haocang.curve.share.bean.CurveShareEntity;
import com.haocang.curve.share.iview.CurveShareView;
import com.haocang.curve.share.presenter.CurveSharePresenter;
import com.haocang.curve.share.presenter.impl.CurveSharePresenterImpl;

import java.util.List;


@Route(path = ArouterPathConstants.Curve.CURVE_SHARE)
public class CurveShareActivity extends Activity implements CurveShareView, BaseAdapter.OnItemClickListener, View.OnClickListener {

    private RecyclerView recyclerview;

    private CurveShareAdapter mAdapter;

    private CurveSharePresenter presenter;

    private ImageView selectIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curve_share);
        setTaskBar();
        initView();
    }

    private void initView() {
        presenter = new CurveSharePresenterImpl(this);
        recyclerview = findViewById(R.id.recyclerview);
        mAdapter = new CurveShareAdapter(R.layout.adapter_curve_share);
        recyclerview.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerview.setAdapter(mAdapter);
        presenter.initMenu();
        mAdapter.setOnItemClickListener(this);
        findViewById(R.id.onclick_ll).setOnClickListener(this);
        findViewById(R.id.ll).setOnClickListener(this);
        selectIv = findViewById(R.id.select_iv);
    }

    /**
     * 设置任务栏的透明.
     */
    protected void setTaskBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    // 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    public Activity getActivitys() {
        return this;
    }

    @Override
    public String getUrl() {
        return getIntent().getStringExtra("curveUrl");
    }

    @Override
    public void setShareMenuList(List<CurveShareEntity> list) {
        mAdapter.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void isQRCodeSelect(boolean flag) {
        if (flag) {
            selectIv.setVisibility(View.VISIBLE);
        } else {
            selectIv.setVisibility(View.GONE);
        }
    }

    @Override
    public String getTitles() {
        return getIntent().getStringExtra("titles");
    }

    @Override
    public String getContents() {
        return getIntent().getStringExtra("contents");
    }


    @Override
    public void onClick(View view, int position, Object item) {
        CurveShareEntity entity = (CurveShareEntity) item;
        presenter.startShare(entity);
        finish();
    }

    @Override
    public void onLongClick(View view, int position, Object item) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.onclick_ll) {
            presenter.onClickQRCode();
        }
    }

}
