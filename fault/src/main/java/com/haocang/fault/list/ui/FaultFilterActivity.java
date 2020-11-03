package com.haocang.fault.list.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.bean.ScreeEntity;
import com.haocang.fault.R;
import com.haocang.fault.list.adapter.FaultFilterAdapter;
import com.haocang.fault.list.iview.FaultFilterView;
import com.haocang.fault.list.presenter.impl.FaultFilterPresenterImpl;

import java.util.List;


public class FaultFilterActivity extends Activity implements View.OnClickListener, FaultFilterView {

    private FaultFilterPresenterImpl presenter;
    private TextView timeTv;

    private View createPersonV;//创建人选中时的图片


    private View personV;//处理人选中时的图片
    /**
     * 状态
     */
    private RecyclerView statuRecyclerView;
    private FaultFilterAdapter statuAdapter;

    private RecyclerView severityRecyclerView;
    private FaultFilterAdapter severityAdapter;

    public static final int REQUEST = 4756;

    private FrameLayout frameLayout;

    private String startTime;

    private String endTime;

    private int createUserIds = -1;

    private int processingPersonIds = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_filter);
        initView();
    }

    private void initView() {
        findViewById(R.id.fault_left_arrow_iv).setOnClickListener(this);
        findViewById(R.id.fault_right_arrow_iv).setOnClickListener(this);
        frameLayout = findViewById(R.id.fl_bg);
        findViewById(R.id.reset_tv).setOnClickListener(this);
        findViewById(R.id.complete_tv).setOnClickListener(this);
        timeTv = findViewById(R.id.falut_time_tv);
        findViewById(R.id.scree_ll).setOnClickListener(this);
        statuRecyclerView = findViewById(R.id.statu_recyclerview);
        statuRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        statuAdapter = new FaultFilterAdapter(R.layout.adapter_fault_filter);
        statuRecyclerView.setAdapter(statuAdapter);
        statuAdapter.setOnItemClickListener(statuOnItem);

        findViewById(R.id.create_all_person_tv).setOnClickListener(this);
        findViewById(R.id.all_person_tv).setOnClickListener(this);

        createPersonV = findViewById(R.id.scree_v);
        personV = findViewById(R.id.scree_v2);

        severityRecyclerView = findViewById(R.id.severity_recyclerview);
        severityRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        severityAdapter = new FaultFilterAdapter(R.layout.adapter_fault_filter);
        severityRecyclerView.setAdapter(severityAdapter);
        severityAdapter.setOnItemClickListener(severityOnItem);
        presenter = new FaultFilterPresenterImpl(this);
        Intent intent = getIntent();
        presenter.initData(intent.getStringExtra("endDate"));
    }


    public BaseAdapter.OnItemClickListener severityOnItem = new BaseAdapter.OnItemClickListener() {
        @Override
        public void onClick(View view, int position, Object item) {
            ScreeEntity s = (ScreeEntity) item;
            if (s.isSelector()) {
                s.setSelector(false);
            } else {
                s.setSelector(true);
            }
            severityAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLongClick(View view, int position, Object item) {
        }
    };

    public BaseAdapter.OnItemClickListener statuOnItem = new BaseAdapter.OnItemClickListener() {
        @Override
        public void onClick(View view, int position, Object item) {
            ScreeEntity s = (ScreeEntity) item;
            if (s.isSelector()) {
                s.setSelector(false);
            } else {
                s.setSelector(true);
            }
            statuAdapter.notifyDataSetChanged();
        }

        @Override
        public void onLongClick(View view, int position, Object item) {

        }
    };

    // 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fault_left_arrow_iv) {
            presenter.pre();
        } else if (v.getId() == R.id.fault_right_arrow_iv) {
            presenter.next();
        } else if (v.getId() == R.id.reset_tv) {
            severityAdapter.resetData();
            statuAdapter.resetData();
            presenter.reset();
//            display(personV, 1);
        } else if (v.getId() == R.id.complete_tv) {
            Intent intent = new Intent();
            intent.putExtra("startTime", startTime);
            intent.putExtra("endTime", endTime);
            intent.putExtra("statu", statuAdapter.getIds());
            intent.putExtra("faultTypes", severityAdapter.getIds());//缺陷类型
            if (createUserIds >= 0) {
                intent.putExtra("createUserIds", createUserIds + "");
            }
            if (processingPersonIds >= 0) {
                intent.putExtra("processingPersonIds", processingPersonIds + "");
            }
            setResult(REQUEST, intent);
            finish();
            overridePendingTransition(0, 0);//取消动画避免闪烁
        } else if (v.getId() == R.id.create_all_person_tv) {
            presenter.founder();//创建人
        } else if (v.getId() == R.id.all_person_tv) {
            presenter.processingPerson();//处理人
        }
    }

    @Override
    public Context getContexts() {
        return this;
    }

    @Override
    public void setShowTime(String showTime) {
        timeTv.setText(showTime);
    }

    @Override
    public void setFilterStatu(List<ScreeEntity> list) {
        statuAdapter.addAll(list);
        statuAdapter.notifyDataSetChanged();
    }

    @Override
    public void setFilterSeverity(List<ScreeEntity> list) {
        severityAdapter.addAll(list);
        severityAdapter.notifyDataSetChanged();
    }

    @Override
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String getStatuIds() {
        return getIntent().getStringExtra("states");
    }

    @Override
    public String getSeverityIds() {
        return getIntent().getStringExtra("faultTypes");
    }

    @Override
    public String getStartTime() {
        return getIntent().getStringExtra("startDate");
    }

    @Override
    public String getEndTime() {
        return getIntent().getStringExtra("endDate");
    }


    @Override
    public String getFounder() {
        return getIntent().getStringExtra("createUserIds");
    }

    @Override
    public String getProcessingPerson() {
        return getIntent().getStringExtra("processingPersonIds");
    }

    @Override
    public void setFounder(int state) {
        createUserIds = state;
        display(createPersonV, state);
    }

    @Override
    public void setProcessingPerson(int state) {
        if (state == 0) {
            processingPersonIds = 0;
        } else {
            processingPersonIds = -1;
        }
        display(personV, state);
    }

    private void display(View v, int state) {
        TextView contextTv = findViewById(R.id.all_person_tv);
        if (state == 1) {
            v.setVisibility(View.GONE);
            contextTv.setTextColor(Color.parseColor("#282828"));
            frameLayout.setBackgroundColor(Color.parseColor("#f0f0f0"));
        } else {
            contextTv.setTextColor(Color.parseColor("#0cabdf"));
            frameLayout.setBackgroundColor(Color.parseColor("#EDF9FD"));
            v.setVisibility(View.VISIBLE);
        }
    }
}
