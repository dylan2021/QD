package com.haocang.curve.collection.ui;

import android.app.Activity;
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
import android.widget.TextView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.curve.R;
import com.haocang.curve.collection.adapter.MultiCollectionAdapter;
import com.haocang.curve.collection.bean.AppChartDTO;
import com.haocang.curve.collection.iview.MultiCollectionView;
import com.haocang.curve.collection.presenter.MultiCollectionPresenter;
import com.haocang.curve.collection.presenter.impl.MultiCollectionPresenterImpl;
import com.haocang.curve.main.bean.CurveConstans;
import com.haocang.curve.more.bean.PointEntity;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题：
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/6/1下午5:50
 * 修  改  者：
 * 修改时间：
 */
public class MultiCollectionFragment
        extends Fragment implements MultiCollectionView,
        TextView.OnEditorActionListener, BaseRefreshListener, BaseAdapter.OnItemClickListener {

    /**
     *
     */
    private MultiCollectionPresenter multiCollectionPresenter;

    /**
     * 适配器.
     */
    private MultiCollectionAdapter mAdapter;

    /**
     *
     */
    private EditText queryEt;

    /**
     *
     */
    private PullToRefreshLayout pullToRefreshLayout;
    /**
     * 区分是刷新还是加载更多.
     */
    private boolean isRefresh = true;

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.curve_multicollection_fragment, null);
        initView(view);
        return view;
    }

    /**
     * 初始化界面.
     *
     * @param view 根View
     */
    private void initView(final View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        mAdapter = new MultiCollectionAdapter(this,
                R.layout.curve_multicollection_item);
        queryEt = view.findViewById(R.id.query_et);
        queryEt.setOnEditorActionListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        multiCollectionPresenter =
                new MultiCollectionPresenterImpl(this);
        multiCollectionPresenter.getList();
        pullToRefreshLayout = view.findViewById(R.id.pulltorefreshlayout);
        pullToRefreshLayout.setRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
    }

    /**
     *
     */
    @Override
    public void refresh() {
//        currentPage = 1;
        isRefresh = true;
        multiCollectionPresenter.getList();
    }

    /**
     *
     */
    @Override
    public void loadMore() {
//        currentPage++;
        isRefresh = false;
        multiCollectionPresenter.getList();
    }

    /**
     * @return
     */
    @Override
    public String getQueryName() {
        return queryEt.getText().toString();
    }

    /**
     * 渲染数据列表.
     *
     * @param list 数据列表
     */
    @Override
    public void renderList(final List<AppChartDTO> list) {
        mAdapter.clear();
        mAdapter.addAll(list);
        mAdapter.notifyDataSetChanged();
        pullToRefreshLayout.finishLoadMore();
        pullToRefreshLayout.finishRefresh();
    }

    /**
     * @param textView
     * @param actionId
     * @param keyEvent
     * @return
     */
    @Override
    public boolean onEditorAction(final TextView textView,
                                  final int actionId,
                                  final KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEND
                || (keyEvent != null
                && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            refresh();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view, int position, Object item) {
        AppChartDTO entity = (AppChartDTO) item;
        List<PointEntity> pointList = entity.getList();
//        try {
//            if (entity != null && !StringUtils.isEmpty(entity.getMpointIds())) {
//                String[] ids = entity.getMpointIds().split(",");
//                String[] names = entity.getMpointNames().split(",");
//                for (int i=0;i<ids.length&&i<names.length;i++) {
//                    PointEntity point = new PointEntity();
//                    point.setId(Integer.parseInt(ids[i]));
//                    point.setMpointId(ids[i]);
//                    point.setMpointName(names[i]);
//                    pointList.add(point);
//                }
//            }
//        } catch (Exception e) {
//
//        }

        Type type = new TypeToken<List<PointEntity>>() {
        }.getType();
        String selectedPointsStr = new Gson().toJson(pointList, type);
        if (!TextUtils.isEmpty(getIsHomeJump())) {
            Map<String, Object> map = new HashMap<>();
            map.put("selectedPointsStr", selectedPointsStr);
            map.put("combineName", entity.getCombineName());
            startActivityForResult(map, getActivity(), ArouterPathConstants.Curve.CURVE_MAIN, CurveConstans.PICK_COLLECTION_REQUEST_CODE);
        } else {
            Intent intent = new Intent();
            intent.putExtra("selectedPointsStr", selectedPointsStr);
            intent.putExtra("combineName", entity.getCombineName());
            ((Activity) getContext()).setResult(CurveConstans.PICK_COLLECTION_REQUEST_CODE, intent);
            ((Activity) getContext()).finish();
        }

    }

    @Override
    public void onLongClick(View view, int position, Object item) {

    }

    public void startActivityForResult(Map<String, Object> map, Activity activity, String path, int requestCode) {
        Postcard postcard = ARouter.getInstance().build(path);
        Iterator var4 = map.entrySet().iterator();

        while (var4.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry) var4.next();
            String key = (String) entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Boolean) {
                boolean b = (Boolean) value;
                postcard.withBoolean(key, b);
            } else if (value instanceof Integer) {
                int c = (Integer) value;
                postcard.withInt(key, c);
            } else if (entry.getValue() != null) {
                postcard.withString((String) entry.getKey(), entry.getValue().toString());
            }
        }

        postcard.navigation(activity, requestCode);
    }

    private String getIsHomeJump() {
        return getActivity().getIntent().getStringExtra("main");
    }
}