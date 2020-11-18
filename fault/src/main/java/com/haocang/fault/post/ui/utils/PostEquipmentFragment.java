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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.bean.LabelEntity;
import com.haocang.base.config.LibConfig;
import com.haocang.base.http.AddParameters;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.ProgressBarDialog;
import com.haocang.base.utils.ToastUtil;
import com.haocang.equiment.constants.EquipmentMethod;
import com.haocang.fault.R;
import com.haocang.fault.post.adapter.PostProcessingPersonAdapter;
import com.haocang.offline.dao.SynDataManager;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

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
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/2813:34
 * 修 改 者：
 * 修改时间：
 */
@Route(path = LibConfig.AROUTE_FAULT_EQUIPMENT)
public class PostEquipmentFragment extends Fragment implements OkHttpClientManager.OnNetworkResponse, BaseRefreshListener, BaseAdapter.OnItemClickListener, View.OnClickListener {
    private RecyclerView equimentRv;
    private ImageView noDataIv;//没有数据的时候显示暂无数据图片.
    private PullToRefreshLayout pullToRefreshLayout;
    private int page = 1;
    private boolean isRefresh = true;//区分是刷新还是加载更多
    private PostProcessingPersonAdapter mAdapter;
    private RecyclerView mRecyclerView;
    public static final int REQUESTCODE = 1032;
    private TextView titleNameTv;
    private EditText queryEt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_post_equipment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.fault_equipment));
        mRecyclerView = view.findViewById(R.id.patrol_allocate_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        noDataIv = view.findViewById(R.id.no_data_iv);
        pullToRefreshLayout.setRefreshListener(this);
        mAdapter = new PostProcessingPersonAdapter(R.layout.adapter_post_processing);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        view.findViewById(R.id.common_complete_tv).setOnClickListener(this);
        view.findViewById(R.id.search_v).setOnClickListener(this);
        queryEt = view.findViewById(R.id.patrol_query_et);
        queryEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    private void getData() {
        AddParameters addParameters = new AddParameters();
        addParameters.addParam("currentPage", page);
        addParameters.addParam("pageSize", "20");
        if (!queryEt.getText().toString().equals("")) {
            addParameters.addParam("queryName", queryEt.getText().toString());
        }
        new OkHttpClientManager()
                .setOnNetWorkReponse(this)
                .setLoadDialog(new ProgressBarDialog(getActivity()))
                .setUrl(EquipmentMethod.EQUIMENT_LIST + addParameters.formGet())
                .setRequestMethod(LibConfig.HTTP_GET)
                .builder();
    }

    @Override
    public void onNetworkResponse(String result) {
        try {
            JSONObject object = new JSONObject(result);
            String json = object.optString("items");
            JSONArray arr = new JSONArray(json);
            List<LabelEntity> list = new ArrayList<>();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject js = new JSONObject(arr.getString(i));
                LabelEntity labelEntity = new LabelEntity();
                labelEntity.setId(js.optInt("id"));
                labelEntity.setLabel(js.optString("name"));
                labelEntity.setOrgName(js.optString("processName"));
                labelEntity.setCode(js.optString("code"));
                labelEntity.setProcessName(js.optString("processName"));
                labelEntity.setOrgNames(js.optString("orgName"));
                labelEntity.setProcessId(js.optInt("processId"));
                list.add(labelEntity);
            }
            if (isRefresh) {
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
                pullToRefreshLayout.finishRefresh();
            } else {
                pullToRefreshLayout.finishLoadMore();
            }
            if (list != null && list.size() > 0) {
                mAdapter.addAll(list);
                mAdapter.notifyDataSetChanged();
            } else if (!isRefresh) {
                ToastUtil.makeText(getActivity(), getString(R.string.equiment_all_data));
            }
            if (mAdapter.getItemCount() == 0) {
                noDataIv.setVisibility(View.VISIBLE);
            } else {
                noDataIv.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(Response response) {

    }

    @Override
    public void refresh() {
        if (!OffLineOutApiUtil.isNetWork()) {
            page = 1;
            isRefresh = true;
            getData();
        } else {
            pullToRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void loadMore() {
        if (!OffLineOutApiUtil.isNetWork()) {
            isRefresh = false;
            page++;
            getData();
        } else {
            pullToRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!OffLineOutApiUtil.isNetWork()) {
            page = 1;
            isRefresh = true;
            getData();
        } else {
            mAdapter.clear();
            pullToRefreshLayout.finishRefresh();
            pullToRefreshLayout.finishLoadMore();
            SynDataManager synDataManager = new SynDataManager();
            mAdapter.addAll(synDataManager.getEquipmentList());
            mAdapter.notifyDataSetChanged();
        }
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
    public void onClick(View v) {
        if (v.getId() == R.id.search_v) {
        } else {
            complete();
        }
    }

    //搜索
    private void search() {
        if (!OffLineOutApiUtil.isNetWork()) {
            refresh();
        } else {
            String str = queryEt.getText().toString();
            if (!TextUtils.isEmpty(str)) {
                List<LabelEntity> list = getQueryList(str);
                mAdapter.clear();
                mAdapter.addAll(list);
                mAdapter.notifyDataSetChanged();
            } else {
                onResume();
            }
        }
    }

    private void complete() {
        LabelEntity entity = mAdapter.getSelect();
        if (entity != null) {
            Intent intent = new Intent();
            intent.putExtra("name", entity.getLabel());
            intent.putExtra("id", entity.getId() + "");
            intent.putExtra("processId", entity.getProcessId() + "");
            intent.putExtra("orgName", entity.getOrgNames());
            intent.putExtra("processName", entity.getProcessName());
            intent.putExtra("code", entity.getCode());
            getActivity().setResult(REQUESTCODE, intent);
        }
        getActivity().finish();
    }

    //搜索数据返回
    public List<LabelEntity> getQueryList(String queryName) {
        List<LabelEntity> li = new ArrayList<>();
        SynDataManager synDataManager = new SynDataManager();
        for (LabelEntity entity : synDataManager.getEquipmentList()) {
            if (entity.getLabel().contains(queryName) || entity.getCode().contains(queryName)) {
                li.add(entity);
            }
        }
        return li;
    }
}
