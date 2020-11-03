package com.haocang.fault.post.ui.utils;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.bean.LabelEntity;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.ProgressBarDialog;
import com.haocang.fault.R;
import com.haocang.fault.post.adapter.PostProcessingPersonAdapter;
import com.haocang.offline.dao.SynDataManager;
import com.haocang.offline.util.OffLineOutApiUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：工艺位置列表
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/5/917:48
 * 修 改 者：
 * 修改时间：
 */

@Route(path = LibConfig.AROUTE_PROCESS_POSITION)
public class ProcessPositionFragment extends Fragment implements OkHttpClientManager.OnNetworkResponse, BaseAdapter.OnItemClickListener, View.OnClickListener {

    public static final int REQUEST = 2389;
    private RecyclerView equimentRv;
    private ImageView noDataIv;//没有数据的时候显示暂无数据图片.
    private int page = 1;
    private boolean isRefresh = true;//区分是刷新还是加载更多
    private PostProcessingPersonAdapter mAdapter;
    private RecyclerView mRecyclerView;


    private TextView titleNameTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_process_position, null);
        initView(view);
        return view;

    }

    private void initView(View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.falut_process_postion));
        mRecyclerView = view.findViewById(R.id.patrol_allocate_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        noDataIv = view.findViewById(R.id.no_data_iv);
        mAdapter = new PostProcessingPersonAdapter(R.layout.adapter_post_processing);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        view.findViewById(R.id.common_complete_tv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.common_complete_tv) {
            LabelEntity labelEntity = mAdapter.getSelect();
            if (labelEntity != null) {
                backFaults();
            }
            getActivity().finish();
        }
    }

    private void backFaults() {
        Intent intent = new Intent();
        intent.putExtra("processId", mAdapter.getSelect().getId() + "");
        intent.putExtra("processName", mAdapter.getSelect().getLabel());
        getActivity().setResult(REQUEST, intent);

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

    @Override
    public void onNetworkResponse(String result) {
        List<LabelEntity> list = new ArrayList<>();
        try {
            if (TextUtils.isEmpty(result)) {
                return;
            }
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                LabelEntity labelEntity = new LabelEntity();
                JSONObject object = array.optJSONObject(i);
                labelEntity.setLabel(object.optString("name"));
                labelEntity.setId(object.optInt("id"));
                list.add(labelEntity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mAdapter.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorResponse(Response response) {

    }


    @Override
    public void onResume() {
        super.onResume();
        mAdapter.clear();
        if (!OffLineOutApiUtil.isNetWork()) {
            getData();
        } else {
            SynDataManager synDataManager = new SynDataManager();
            onNetworkResponse(synDataManager.getProcessArray());
        }

    }

    private void getData() {
        new OkHttpClientManager()
                .setUrl(MethodConstants.Uaa.BASE_PROCESSE)
                .setLoadDialog(new ProgressBarDialog(getActivity()))
                .setOnNetWorkReponse(this)
                .builder();
    }
}
