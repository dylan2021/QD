package com.haocang.patrol.patrolinhouse.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.utils.ToastUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.patrolinhouse.adapter.PatrolExemptionAdapter;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskPointStep;
import com.haocang.patrol.patrolinhouse.iview.PatrolExemptionView;
import com.haocang.patrol.patrolinhouse.presenter.PatrolExemptionDetailPresenter;
import com.haocang.patrol.patrolinhouse.presenter.impl.PatrolExemptionDetailPresenterImpl;

import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：${DATA} 13:56
 * 修 改 者：
 * 修改时间：
 */
public class PatrolExemptionDetailListFragment extends Fragment implements PatrolExemptionView, View.OnClickListener {
    private EditText queryEt;
    private PatrolExemptionDetailPresenter presenter;
    private PatrolExemptionAdapter adapter;
    private RecyclerView recyclerView;
    private TextView titleNameTv;
    private boolean isSelect = false;
    private ImageView allSelectIv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_patrol_exemption_list, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getActivity().getIntent().getStringExtra("pointName"));
        queryEt = view.findViewById(R.id.query_et);
        queryEt.setHint("请输入巡检步骤");
        view.findViewById(R.id.delete_ibtn).setOnClickListener(this);
        queryEt.setHintTextColor(Color.parseColor("#ff9b9b9b"));
        recyclerView = view.findViewById(R.id.patrol_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter = new PatrolExemptionDetailPresenterImpl(this);
        adapter = new PatrolExemptionAdapter(R.layout.adapter_exemption_item, getActivity());
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i, Object o) {
                PatrolTaskPointStep item = (PatrolTaskPointStep) o;
                if ("abnormal".equals(item.getStepResult())) {
                    ToastUtil.makeText(getActivity(), "已报缺巡检步骤不能免检");
                    return;
                }
                if (item.isSelect()) {
                    item.setSelect(false);
                    item.setStepResult(item.getLastStepResult());
                } else {
                    item.setSelect(true);
                    item.setStepResult("/");
                }
                adapter.notifyDataSetChanged();
                if (isAllSelect()) {
                    isSelect = true;
                    allSelectIv.setBackgroundResource(R.drawable.icon_patrol_exemption_t);
                } else {
                    isSelect = false;
                    allSelectIv.setBackgroundResource(R.drawable.icon_patrol_exemption_f);
                }
            }

            @Override
            public void onLongClick(View view, int i, Object o) {
            }
        });
        recyclerView.setAdapter(adapter);
        allSelectIv = view.findViewById(R.id.all_select_iv);
        allSelectIv.setOnClickListener(this);
        view.findViewById(R.id.comple_ibtn).setOnClickListener(this);
        view.findViewById(R.id.all_select_tv).setOnClickListener(this);
        presenter.getPatrolExemptionList();
    }


    @Override
    public Integer getPointId() {
        return getActivity().getIntent().getIntExtra("pointId", -1);
    }

    @Override
    public Integer getTaskId() {
        return getActivity().getIntent().getIntExtra("taskId", -1);
    }

    @Override
    public String getQueryName() {
        return queryEt.getText().toString().trim();
    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public Integer getFaultCount() {
        return adapter.getFaultCount();
    }

    @Override
    public Integer getHasResultCount() {
        return adapter.getHasResultCount();
    }

    @Override
    public Integer getPatrolPointId() {
        int id = getActivity().getIntent().getIntExtra("id", -1);
        return id;
    }

    @Override
    public String getLastResultUpdateTime() {
        return getActivity().getIntent().getStringExtra("resultUpdateTime");
    }

    @Override
    public List<PatrolTaskPointStep> getPatrolPointSteps() {
        return adapter.getPointSteps();
    }

    @Override
    public void setRenderList(List<PatrolTaskPointStep> list) {
        int exmptionStep = 0;
        for (PatrolTaskPointStep step : list) {
            if ("/".equals(step.getStepResult())) {
                exmptionStep++;
                step.setSelect(true);
            }
            step.setLastStepResult(step.getStepResult());
        }
        if (exmptionStep == list.size()) {
            isSelect = true;
            allSelectIv.setBackgroundResource(R.drawable.icon_patrol_exemption_t);
        }
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void submitSucess() {
        ToastUtil.makeText(getActivity(), "提交成功");
        getActivity().setResult(2019);
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.all_select_iv || v.getId() == R.id.all_select_tv) {
            selectAll();
        } else if (v.getId() == R.id.comple_ibtn) {
            presenter.submitData();
        }else if(v.getId()==R.id.delete_ibtn){
            queryEt.setText("");
        }
    }

    private boolean isAllSelect() {
        int length = 0;
        for (PatrolTaskPointStep step : adapter.mList) {
            if (step.isSelect()) {
                length++;
            }
        }
        if (length == adapter.mList.size()) {
            return true;
        } else {
            return false;
        }
    }

    private void selectAll() {
        //全选
        if (isSelect) {
            adapter.allSelect(false);
            isSelect = false;
            allSelectIv.setBackgroundResource(R.drawable.icon_patrol_exemption_f);
        } else {
            isSelect = true;
            adapter.allSelect(true);
            allSelectIv.setBackgroundResource(R.drawable.icon_patrol_exemption_t);
        }
    }
}
