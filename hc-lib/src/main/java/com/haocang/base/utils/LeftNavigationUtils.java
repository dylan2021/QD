package com.haocang.base.utils;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.LeftNavigationAdapter;
import com.haocang.base.bean.LeftNavigationEntity;

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
 * 标 题：  左侧导航栏简易封装.
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/3/2210:27
 * 修 改 者：
 * 修改时间：
 */
public class LeftNavigationUtils implements BaseAdapter.OnItemClickListener {
    /**
     * 上下文参数.
     */
    private Context ctx;
    /**
     * 导航栏适配器.
     */
    private LeftNavigationAdapter navigationAdapter;

    /**
     * 列表.
     */
    private RecyclerView leftRecyclerView;
    /**
     * 列表数据.
     */
    private List<LeftNavigationEntity> navigationList = new ArrayList<>();

    /**
     * 把点击的结果返回回去.
     */
    private LeftOnItemClickListener itemClickListener;

    public LeftNavigationUtils(Context ctx) {
        this.ctx = ctx;
    }

    public LeftNavigationUtils setRecyclerView(RecyclerView recyclerView) {
        this.leftRecyclerView = recyclerView;
        return this;
    }

    public LeftNavigationUtils setNavigationData(List<LeftNavigationEntity> list) {
        navigationList = list;
        return this;
    }

    public LeftNavigationUtils setOnItemClickListener(LeftOnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        return this;
    }

    public LeftNavigationUtils builder() {
        if (!isEmpty()) {
            leftRecyclerView.setLayoutManager(new LinearLayoutManager(ctx));
            navigationAdapter = new LeftNavigationAdapter();
            leftRecyclerView.setAdapter(navigationAdapter);
            navigationList.get(0).setClicks(true);
            navigationAdapter.addAll(navigationList);
            navigationAdapter.setOnItemClickListener(this);
        }
        return this;
    }

    @Override
    public void onClick(View view, int position, Object item) {
        navigationAdapter.setUnchecked(position);
        itemClickListener.itemClick(view, position, item);
    }

    @Override
    public void onLongClick(View view, int position, Object item) {

    }

    private boolean isEmpty() {
        if (ctx == null) {
            return true;
        } else if (leftRecyclerView == null) {
            return true;
        } else if (itemClickListener == null) {
            return true;
        } else if (navigationList == null || navigationList.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public interface LeftOnItemClickListener {
        void itemClick(View view, int position, Object item);
    }
}
