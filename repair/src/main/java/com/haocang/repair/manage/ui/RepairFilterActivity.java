package com.haocang.repair.manage.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.bean.ScreeEntity;
import com.haocang.base.config.LibConstants;
import com.haocang.repair.R;
import com.haocang.repair.manage.adapter.RepairFilterAdapter;
import com.haocang.repair.manage.iview.RepairFilterView;
import com.haocang.repair.manage.presenter.impl.RepairFilterPresenterImpl;

import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：维修管理列表筛选
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：william
 * 创建时间：2018/4/2611:20
 * 修 改 者：
 * 修改时间：
 */
public class RepairFilterActivity extends Activity
        implements View.OnClickListener, RepairFilterView {

    /**
     * 维修筛选Presenter.
     */
    private RepairFilterPresenterImpl presenter;
    /**
     * 时间显示文本.
     */
    private TextView timeTv;
    /**
     * 创建人列表视图.
     */
    private RecyclerView createPersonRecyclerView;
    /**
     * 创建人适配器.
     */
    private RepairFilterAdapter createPersonAdapter;
    /**
     * 状态列表视图.
     */
    private RecyclerView stateRecyclerView;
    /**
     * 状态适配器.
     */
    private RepairFilterAdapter stateAdapter;
    /**
     * 执行人列表视图.
     */
    private RecyclerView excutePersonRecyclerView;
    /**
     * 执行人适配器.
     */
    private RepairFilterAdapter excutePersonAdapter;
    /**
     * 返回码.
     */
    public static final int REQUEST = 4756;

    /**
     * 选中时间
     */
    private String selectDateStr;

//    /**
//     * 状态ID列表
//     */
//    private String stateIds;
//
//    /**
//     *
//     */
//    private String severityIds;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repair_filter_activity);
        initView();
    }

    /**
     * 初始化组件.
     */
    private void initView() {
        findViewById(R.id.repair_left_arrow_iv).setOnClickListener(this);
        findViewById(R.id.repair_right_arrow_iv).setOnClickListener(this);
        findViewById(R.id.reset_tv).setOnClickListener(this);
        findViewById(R.id.complete_tv).setOnClickListener(this);
        timeTv = findViewById(R.id.repair_time_tv);
        findViewById(R.id.scree_ll).setOnClickListener(this);
        presenter = new RepairFilterPresenterImpl(this);
        createPersonRecyclerView =
                findViewById(R.id.repair_create_person_recyclerview);
        createPersonRecyclerView.
                setLayoutManager(new GridLayoutManager(this,
                        LibConstants.Base.PICTURE_ADAPTER_COLUMN_COUNT));
        createPersonAdapter =
                new RepairFilterAdapter(R.layout.repair_filter_adapter);
        createPersonRecyclerView.setAdapter(createPersonAdapter);
        createPersonAdapter
                .setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(final View view,
                                        final int position,
                                        final Object item) {
                        ScreeEntity s = (ScreeEntity) item;
                        if (s.isSelector()) {
                            s.setSelector(false);
                        } else {
                            s.setSelector(true);
                        }
                        createPersonAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onLongClick(final View view,
                                            final int position,
                                            final Object item) {

                    }
                });

        excutePersonRecyclerView
                = findViewById(R.id.repair_excute_person_recyclerview);
        excutePersonRecyclerView
                .setLayoutManager(new GridLayoutManager(this,
                        LibConstants.Base.PICTURE_ADAPTER_COLUMN_COUNT));
        excutePersonAdapter =
                new RepairFilterAdapter(R.layout.repair_filter_adapter);
        excutePersonRecyclerView.setAdapter(excutePersonAdapter);
        excutePersonAdapter
                .setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(final View view,
                                        final int position,
                                        final Object item) {
                        ScreeEntity s = (ScreeEntity) item;
                        if (s.isSelector()) {
                            s.setSelector(false);
                        } else {
                            s.setSelector(true);
                        }
                        excutePersonAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onLongClick(final View view,
                                            final int position,
                                            final Object item) {

                    }
                });

        stateRecyclerView = findViewById(R.id.repair_state_recyclerview);
        stateRecyclerView.setLayoutManager(new GridLayoutManager(this,
                LibConstants.Base.PICTURE_ADAPTER_COLUMN_COUNT));
        stateAdapter = new RepairFilterAdapter(R.layout.repair_filter_adapter);
        stateRecyclerView.setAdapter(stateAdapter);
        stateAdapter
                .setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(final View view,
                                        final int position,
                                        final Object item) {
                        ScreeEntity s = (ScreeEntity) item;
                        if (s.isSelector()) {
                            s.setSelector(false);
                        } else {
                            s.setSelector(true);
                        }
                        stateAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onLongClick(final View view,
                                            final int position,
                                            final Object item) {

                    }
                });
        Intent intent = getIntent();
        presenter.initData(intent.getStringExtra("selectDateStr"));
    }

    // 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity

    /**
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        finish();
        return true;
    }

    /**
     * @param v
     */
    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.repair_left_arrow_iv) {
            presenter.pre();
        } else if (v.getId() == R.id.repair_right_arrow_iv) {
            presenter.next();
        } else if (v.getId() == R.id.reset_tv) {
            stateAdapter.resetData();
            createPersonAdapter.resetData();
            excutePersonAdapter.resetData();
        } else if (v.getId() == R.id.complete_tv) {
            presenter.complete();
        }
    }

    /**
     * @return
     */
    @Override
    public Context getContexts() {
        return this;
    }

    /**
     * @param showTime
     */
    @Override
    public void setShowTime(final String showTime) {
        timeTv.setText(showTime);
    }

    /**
     * @param list 状态列表.
     */
    @Override
    public void setFilterState(final List<ScreeEntity> list) {
        createPersonAdapter.clear();
        stateAdapter.addAll(list);
        stateAdapter.notifyDataSetChanged();
    }

    /**
     * @param list 创建人.
     */
    @Override
    public void setFilterCreatePerson(final List<ScreeEntity> list) {
        createPersonAdapter.clear();
        createPersonAdapter.addAll(list);
        createPersonAdapter.notifyDataSetChanged();
    }

    /**
     * @param list 处理人.
     */
    @Override
    public void setFilterExcutePerson(final List<ScreeEntity> list) {
        createPersonAdapter.clear();
        excutePersonAdapter.addAll(list);
        excutePersonAdapter.notifyDataSetChanged();
    }

    /**
     * @return
     */
    @Override
    public String getStateIds() {
        return getIntent().getStringExtra("stateIds");
    }

    /**
     * @return
     */
    @Override
    public String getCreatePersonIds() {
        return getIntent().getStringExtra("createPersonIds");
    }

    /**
     * @return
     */
    @Override
    public String getExcutePersonIds() {
        return getIntent().getStringExtra("excutePersonIds");
    }

    /**
     * 设置选中时间
     *
     * @param selectDateStr 选中时间
     */
    @Override
    public void setSelectDateStr(String selectDateStr) {
        this.selectDateStr = selectDateStr;
    }

    @Override
    public String getSelectCreatePersonIds() {
        return createPersonAdapter.getIds();
    }

    @Override
    public String getSelectStateIds() {
        return stateAdapter.getIds();
    }

    @Override
    public String getSelectProcessPersonIds() {
        return excutePersonAdapter.getIds();
    }

    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

}
