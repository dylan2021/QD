package com.haocang.patrol.manage.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.haocang.base.picker.PickerView;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.bean.filter.FilterEntity;
import com.haocang.patrol.manage.iview.PatrolFilterView;
import com.haocang.patrol.manage.presenter.impl.PatrolFilterPresenterImpl;
import com.haocang.patrol.manage.widgets.LabelView;

import java.util.List;
import java.util.Map;

/**
 *
 */
public class PatrolFilterActivity extends Activity implements
        PatrolFilterView, View.OnClickListener {

    public static final int REQUEST = 9257;
    private LabelView mStateLableView;
    private LabelView patrolTaskLableView;
    private PatrolFilterPresenterImpl mFilterPresenter;
    private FrameLayout frameLayout;
    private int allExecutor = -1;//所有人是否为选中 1选中
    private TextView contextTv;
    private View v;
    private PickerView pickerView;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patrol_filter_activity);
        initView();
    }

    /**
     *
     */
    private void initView() {
        contextTv = findViewById(R.id.all_person_tv);
        v = findViewById(R.id.scree_v);
        frameLayout = findViewById(R.id.fl_bg);
        mStateLableView = findViewById(R.id.patrol_state_lableView);
        patrolTaskLableView = findViewById(R.id.patrol_task_lableview);
        mFilterPresenter = new PatrolFilterPresenterImpl();
        mFilterPresenter.setView(this);
        mStateLableView.setConditionInterface(new LabelView.ConditionInterface() {
            @Override
            public void condition(final Map<String, String> map) {

            }

            @Override
            public void OnItemClick() {
                patrolTaskLableView.reset();
            }
        });
        patrolTaskLableView.setConditionInterface(new LabelView.ConditionInterface() {
            @Override
            public void condition(Map<String, String> map) {

            }

            @Override
            public void OnItemClick() {
                mStateLableView.reset();
            }
        });
        pickerView = findViewById(R.id.pickerview);
        findViewById(R.id.patrol_reset_tv).setOnClickListener(this);
        findViewById(R.id.patrol_complete_tv).setOnClickListener(this);
        findViewById(R.id.all_person_tv).setOnClickListener(this);
        if (!TextUtils.isEmpty(getPersonAll())) {
            allExecutor = Integer.parseInt(getPersonAll());
            if (allExecutor == 1) {
                contextTv.setTextColor(Color.parseColor("#0cabdf"));
                frameLayout.setBackgroundResource(R.drawable.patrol_fileter2_shape);
                v.setVisibility(View.VISIBLE);
            }

        }
        Intent intent = getIntent();
        mFilterPresenter.initData(intent.getStringExtra("curDateStr"), intent.getStringExtra("excuteStatus"));
    }

    public void onResume() {
        super.onResume();

    }

    /**
     * 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
     */
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        finish();
        return true;
    }

    @Override
    public void renderList(final List<FilterEntity> list) {
        mStateLableView.setData(list, this, "任务查看");
    }

    @Override
    public void renderTaskList(List<FilterEntity> list) {
        patrolTaskLableView.setData(list, this, "任务执行");
    }

    @Override
    public String getSelectStatusKeys() {
        String key = "";
        if (!TextUtils.isEmpty(mStateLableView.getSelectKeys())) {
            key = mStateLableView.getSelectKeys();
        } else if (!TextUtils.isEmpty(patrolTaskLableView.getSelectKeys())) {
            key = patrolTaskLableView.getSelectKeys();
        }
        return key;
    }

    @Override
    public void setShowTime(final String showTime) {
    }

    @Override
    public String getPersonId() {
        return allExecutor + "";
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public PickerView getPickerview() {
        return pickerView;
    }


    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.patrol_reset_tv) {
            reset();
        } else if (v.getId() == R.id.patrol_complete_tv) {
            complete();
        } else if (v.getId() == R.id.all_person_tv) {
            display();
        }
    }


    private void complete() {
        mFilterPresenter.complete();
        finish();
        overridePendingTransition(0, 0);//取消动画避免闪烁
    }

    private void reset() {
        mStateLableView.reset();
        mFilterPresenter.reset();
        patrolTaskLableView.allSelect();
        allExecutor = 1;
        display();
    }


    private void display() {

        if (allExecutor == 1) {
            v.setVisibility(View.GONE);
            contextTv.setTextColor(Color.parseColor("#282828"));
            frameLayout.setBackgroundResource(R.drawable.patrol_filter_shape);
            allExecutor = 0;
        } else {
            contextTv.setTextColor(Color.parseColor("#0cabdf"));
            frameLayout.setBackgroundResource(R.drawable.patrol_fileter2_shape);
            v.setVisibility(View.VISIBLE);
            allExecutor = 1;
        }
    }

    private String getPersonAll() {
        return getIntent().getStringExtra("allExecutor");
    }

}
