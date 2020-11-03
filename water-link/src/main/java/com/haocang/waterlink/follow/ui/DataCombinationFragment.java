package com.haocang.waterlink.follow.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.GetListListener;
import com.haocang.curve.collection.bean.AppChartDTO;
import com.haocang.curve.constants.CurveMethod;
import com.haocang.curve.more.bean.PointEntity;
import com.haocang.waterlink.R;
import com.haocang.waterlink.follow.adapter.FollowDataAdapter;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：数据组合
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：${DATA} 16:09
 * 修 改 者：
 * 修改时间：
 */
public class DataCombinationFragment extends Fragment implements BaseRefreshListener {
    private RecyclerView recyclerView;
    private FollowDataAdapter adapter;
    private PullToRefreshLayout pullToRefreshLayout;
    private FrameLayout frameLayout;
    private LinearLayoutManager linearLayoutManager;
    private ImageView radioIv;

    public boolean isFirstFlag() {
        return firstFlag;
    }

    public void setFirstFlag(boolean firstFlag) {
        this.firstFlag = firstFlag;
    }

    private boolean firstFlag = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_all_follow, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        if(radioIv!=null){
            radioIv.setVisibility(View.VISIBLE);
        }
        TextView noDataTv = view.findViewById(R.id.noData_tv);
        noDataTv.setText("暂无数据");
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        pullToRefreshLayout.setRefreshListener(this);
        recyclerView = view.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new FollowDataAdapter(R.layout.adapter_data_combination);
        recyclerView.setAdapter(adapter);
        frameLayout = view.findViewById(R.id.no_data_fl);
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int i, Object o) {
                AppChartDTO entity = (AppChartDTO) o;
                List<PointEntity> pointList = entity.getList();
                Type type = new TypeToken<List<PointEntity>>() {
                }.getType();
                String selectedPointsStr = new Gson().toJson(pointList, type);
                Map<String, Object> map = new HashMap<>();
                map.put("selectedPointsStr", selectedPointsStr);
                map.put("combineName", entity.getCombineName());
                ARouterUtil.toActivity(map, ArouterPathConstants.Curve.CURVE_MAIN);
            }

            @Override
            public void onLongClick(View view, int i, Object o) {

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (getUserVisibleHint()) {
                    boolean b = recyclerView.canScrollVertically(-1);
                    if (b && getScollYDistance() > 100) {
                        radioIv.setVisibility(View.GONE);
                    } else if (!b) {
                        radioIv.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        if (firstFlag) {
            getData();
        }
    }


    public int getScollYDistance() {
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = linearLayoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }

    @Override
    public void refresh() {
        pullToRefreshLayout.finishRefresh();
    }

    @Override
    public void loadMore() {
        pullToRefreshLayout.finishLoadMore();
    }

    public void getList() {
        CommonModel<AppChartDTO> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<AppChartDTO>>() {
        }.getType();
        progressModel
                .setContext(getActivity())
                .setListType(type)
                .setUrl(CurveMethod.COMBINES_COLLECTION)
                .setListListener(new GetListListener<AppChartDTO>() {
                    @Override
                    public void success(final List<AppChartDTO> list) {
                        if (list != null && list.size() > 0) {
                            adapter.clear();
                            adapter.addAll(list);
                            adapter.notifyDataSetChanged();
                            frameLayout.setVisibility(View.GONE);
                        } else {
                            frameLayout.setVisibility(View.VISIBLE);
                        }
                    }
                })
                .getList();
    }

    public void setImageView(ImageView radioIv) {
        this.radioIv = radioIv;
    }

    public void getData() {
        getList();
    }
}
