package com.haocang.fault.post.ui.utils;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.config.LibConfig;
import com.haocang.fault.R;
import com.haocang.fault.post.adapter.PostProcessingPersonAdapter;
import com.haocang.base.bean.LabelEntity;

import java.util.ArrayList;
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
 * 创 建 者：he
 * 创建时间：2018/4/2814:37
 * 修 改 者：
 * 修改时间：
 */

@Route(path = LibConfig.AROUTE_FAULT_TYPE)
public class PostFaultTypeFragment extends Fragment implements View.OnClickListener, BaseAdapter.OnItemClickListener {
    /**
     * 列表适配器
     */
    private PostProcessingPersonAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private TextView titleNameTv;

    public static final int FAULT_TYPE = 2002;//故障类型


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_post_fault_type, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.patrol_allocate_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new PostProcessingPersonAdapter(R.layout.adapter_post_processing);
        mRecyclerView.setAdapter(mAdapter);
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.falut_type_title));
        view.findViewById(R.id.common_complete_tv).setOnClickListener(this);
        mAdapter.setOnItemClickListener(this);
    }


    private void renderList() {
        String[] nameArray = getActivity().getResources().getStringArray(R.array.fault_type);
        String[] idArray = getActivity().getResources().getStringArray(R.array.fault_type_id);
        List<LabelEntity> list = new ArrayList<>();
        for (int i = 0; i < nameArray.length && i < idArray.length; i++) {
            LabelEntity userEntity = new LabelEntity();
            userEntity.setLabel(nameArray[i]);
            userEntity.setId(i + 1);
            list.add(userEntity);
        }
        mAdapter.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.clear();
        renderList();
    }

    @Override
    public void onClick(View v) {
        LabelEntity entity = mAdapter.getSelect();
        if (entity != null) {
            backCode(entity);
        }
        getActivity().finish();
    }


    private void backCode(LabelEntity entity) {
        Intent intent = new Intent();
        intent.putExtra("name", entity.getLabel());
        intent.putExtra("id", entity.getId() + "");
        getActivity().setResult(FAULT_TYPE, intent);


    }


    @Override
    public void onClick(View view, int position, Object item) {
        LabelEntity entity = (LabelEntity) item;
        if (entity.isSelect()) {
            entity.setSelect(false);
        } else {
            entity.setSelect(true);
            mAdapter.clearOther(position);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLongClick(View view, int position, Object item) {

    }
}
