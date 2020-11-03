package com.haocang.curve.more.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.curve.R;
import com.haocang.curve.main.bean.CurveConstans;
import com.haocang.curve.more.adapter.PointListAdapter;
import com.haocang.curve.more.bean.PointEntity;
import com.haocang.curve.more.iview.PointListView;
import com.haocang.curve.more.presenter.PointListPresenter;
import com.haocang.curve.more.presenter.impl.PointListPresenterImpl;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区龙兰路277号一号楼9楼
 * 网址：http://www.haocang.com/
 * 标 题：曲线列表
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/5/31 9:58
 * 修 改 者：
 * 修改时间：
 */
@Route(path = ArouterPathConstants.Curve.CURVE_POINT_LIST)
public class PointListFragment extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener, PointListView, BaseRefreshListener {
    private TextView titleNameTv;
    private ImageView commonIv;
    private EditText queryEt;
    private RecyclerView recyclerView;
    private PointListAdapter adapter;
    private int page = 1;
    private PointListPresenter presenter;
    private PullToRefreshLayout pullToRefreshLayout;
    private boolean isRefresh = true;//区分是刷新还是加载更多
    private ImageView noDataIv;//没有数据的时候显示暂无数据图片.
    private String siteId;
    private String categoryId;
    private String processList;
    private List<PointEntity> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_point_list, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        String selectedPointsStr = getActivity().getIntent().getStringExtra("selectedPointsStr");
        if (!TextUtils.isEmpty(selectedPointsStr)) {
            Type type = new TypeToken<List<PointEntity>>() {
            }.getType();
            list = new Gson().fromJson(selectedPointsStr, type);
        }
        noDataIv = view.findViewById(R.id.no_data_iv);
        presenter = new PointListPresenterImpl(this);
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.curve_more));
        commonIv = view.findViewById(R.id.common_iv);
        commonIv.setVisibility(View.VISIBLE);
        commonIv.setOnClickListener(this);
        commonIv.setBackgroundResource(R.mipmap.screen_icon);
        queryEt = view.findViewById(R.id.query_et);
        view.findViewById(R.id.see_curve_tv).setOnClickListener(this);
        queryEt.setOnEditorActionListener(this);
        view.findViewById(R.id.collection_curve_tv).setOnClickListener(this);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PointListAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        pullToRefreshLayout.setRefreshListener(this);
        adapter.setOnItemClickListener(new PointListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(PointEntity entity) {
                if (adapter.getSelectPointList().size() >= CurveConstans.MAX_CURVE_COUNT && !entity.isSelect()) {
                    ToastUtil.makeText(getContext(), getString(R.string.curve_max_limit_tip));
                } else {
                    entity.setSelect(!entity.isSelect());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        refresh();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.common_iv) {
            toActivity();
        } else if (v.getId() == R.id.see_curve_tv) {
            //查看曲线
//            adapter.getSelectPointList();
//            Map<String, Object> map = new HashMap<>();
//            map.put("selectedPointsStr", new Gson().toJson(adapter.getSelectPointList()));
//            ARouterUtil.toActivity(map, ArouterPathConstants.Curve.CURVE_MAIN);
            if (!TextUtils.isEmpty(getType())) {//todo  如果是首页跳进去 finish 是回不到 曲线界面的
                Map<String, Object> map = new HashMap<>();
                map.put("selectedPointsStr", new Gson().toJson(adapter.getSelectPointList()));
                ARouterUtil.toActivity(map, ArouterPathConstants.Curve.CURVE_MAIN);
                getActivity().finish();
            } else {
                Intent intent = new Intent();
                intent.putExtra("selectedPointsStr", new Gson().toJson(adapter.getSelectPointList()));
                getActivity().setResult(3002, intent);
                getActivity().finish();
            }

        } else if (v.getId() == R.id.collection_curve_tv) {
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", ArouterPathConstants.Curve.CURVE_ADD_COLLECTION);
            List<PointEntity> points = adapter.getSelectPointList();
            if (points != null && points.size() > 0) {
                Type type = new TypeToken<List<PointEntity>>() {
                }.getType();
                String selectedPointsStr = new Gson().toJson(points, type);
                map.put("selectedPointsStr", selectedPointsStr);
                ARouterUtil.toFragment(map);
            } else {
                ToastUtil.makeText(getActivity(), "至少选择一条曲线");
            }

        }
    }

    private String getType() {
        return getActivity().getIntent().getStringExtra("type");
    }

    private void toActivity() {
        Intent intent = new Intent(getActivity(), PointScreePopupWindowActivity.class);
        intent.putExtra("siteId", siteId);
        intent.putExtra("categoryId", categoryId);
        intent.putExtra("processList", processList);
        startActivityForResult(intent, 10031);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 10031 && data != null) {
            categoryId = data.getStringExtra("categoryId");
            siteId = data.getStringExtra("siteId");
            processList = data.getStringExtra("processList");
            refresh();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            refresh();
            return true;
        }
        return false;
    }

    @Override
    public String getQueryName() {
        return queryEt.getText().toString().trim();
    }

    @Override
    public String getCurrentPage() {
        return page + "";
    }

    @Override
    public String getPageSize() {
        return "10";
    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public String getSiteId() {
        return siteId;
    }

    @Override
    public String getCategoryId() {
        return categoryId;
    }

    @Override
    public List<PointEntity> getSelectList() {
        return list;
    }

    private String getSelectSr() {
        return getActivity().getIntent().getStringExtra("selectedPointsStr");
    }

    @Override
    public void setPointList(List<PointEntity> list) {
        if (isRefresh) {
            adapter.clear();
            adapter.notifyDataSetChanged();
            pullToRefreshLayout.finishRefresh();
        } else {
            pullToRefreshLayout.finishLoadMore();
        }
        if (list != null && list.size() > 0) {
            adapter.addAll(list);
            adapter.notifyDataSetChanged();
        } else if (!isRefresh) {
            ToastUtil.makeText(getActivity(), getString(R.string.equiment_all_data));
        }
        if (adapter.getItemCount() == 0) {
            noDataIv.setVisibility(View.VISIBLE);
        } else {
            noDataIv.setVisibility(View.GONE);
        }
    }

    @Override
    public void refresh() {
        page = 1;
        isRefresh = true;
        presenter.getPointListData();
    }

    @Override
    public void loadMore() {
        isRefresh = false;
        page++;
        presenter.getPointListData();
    }
}
