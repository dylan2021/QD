package com.haocang.curve.addcollect.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.utils.ToastUtil;
import com.haocang.curve.R;
import com.haocang.curve.addcollect.adapter.CollectAdapter;
import com.haocang.curve.addcollect.iview.CollectView;
import com.haocang.curve.addcollect.presenter.CollectPresenter;
import com.haocang.curve.addcollect.presenter.impl.CollectPresenterImpl;
import com.haocang.curve.main.bean.CurveConstans;
import com.haocang.curve.more.bean.PointEntity;

import java.lang.reflect.Type;
import java.util.List;


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
 * 创建时间：2018/6/5下午2:19
 * 修  改  者：
 * 修改时间：
 */
@Route(path = ArouterPathConstants.Curve.CURVE_ADD_COLLECTION)
public class AddCollectionFragment extends
        Fragment implements View.OnClickListener, CollectView {
    /**
     *
     */
    private ImageView collectSingleIv;
    /**
     *
     */
    private ImageView collectMultiIv;
    /**
     *
     */
    private List<PointEntity> selectedPoints;

    /**
     *
     */
    private CollectAdapter mAdapter;
    /**
     * 组合曲线名称输入框.
     */
    private EditText multiCurveNameEt;

    /**
     *
     */
    private CollectPresenter collectPresenter;

    /**
     * 收藏方式，有组合方式收藏和单曲线方式收藏.
     */
    private int collectType;

    private RecyclerView recyclerView;

    private TextView titleNameTv;

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
        View view =
                inflater.inflate(R.layout.curve_addcollection_fragment, null);
        initView(view);
        return view;
    }

    /**
     * @param view 根布局.
     */
    private void initView(final View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getResources().getString(R.string.curve_collect));
        collectSingleIv = view.findViewById(R.id.curve_collect_signle_iv);
        collectMultiIv = view.findViewById(R.id.curve_add_multicollection_iv);
        multiCurveNameEt = view.findViewById(R.id.multicurve_name_et);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CollectAdapter(R.layout.curve_collect_item);
        collectPresenter = new CollectPresenterImpl(this);
        if (getSelectedPoints() != null) {
            mAdapter.addAll(getSelectedPoints());
        }
        recyclerView.setAdapter(mAdapter);
        if (getSelectedPoints() != null && getSelectedPoints().size() > 1) {
            collectMulti();
        } else {
            collectSingle();
        }
        view.findViewById(R.id.curve_add_multicollection_ll).setOnClickListener(this);
        view.findViewById(R.id.curve_add_singlecollection_ll).setOnClickListener(this);
        view.findViewById(R.id.common_complete_tv).setOnClickListener(this);
    }

    /**
     * @return 选中的测点列表.
     */
    public List<PointEntity> getSelectedPoints() {
        if (selectedPoints == null) {
            String selectedPointsStr = getActivity().getIntent().getStringExtra("selectedPointsStr");
            if (!TextUtils.isEmpty(selectedPointsStr)) {
                Type type = new TypeToken<List<PointEntity>>() {
                }.getType();
                selectedPoints = new Gson().fromJson(selectedPointsStr, type);
            }
        }
        return selectedPoints;
    }

    /**
     * @param v 界面.
     */
    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.curve_add_multicollection_ll) {
            if (getSelectedPoints() != null && getSelectedPoints().size() > 1) {
                collectMulti();
            } else {
                ToastUtil.makeText(getActivity(), "单曲线不能添加数据组合");
            }
        } else if (v.getId() == R.id.curve_add_singlecollection_ll) {
            collectSingle();
        } else if (v.getId() == R.id.common_complete_tv) {
            collectPresenter.complete();
        }
    }

    /**
     * 收藏组合数据.
     */
    private void collectMulti() {
        collectSingleIv.setVisibility(View.INVISIBLE);
        collectMultiIv.setVisibility(View.VISIBLE);
        multiCurveNameEt.setVisibility(View.VISIBLE);
        collectType = CurveConstans.COLLECT_TYPE_MULTI;
    }

    /**
     * 收藏单数据.
     */
    private void collectSingle() {
        collectSingleIv.setVisibility(View.VISIBLE);
        collectMultiIv.setVisibility(View.INVISIBLE);
        multiCurveNameEt.setVisibility(View.GONE);
        collectType = CurveConstans.COLLECT_TYPE_SINGLE;
    }

    /**
     * 获取收藏类型.
     *
     * @return 收藏类型
     */
    @Override
    public int getCollectType() {
        return collectType;
    }

    /**
     * @return 获取组合名称.
     */
    @Override
    public String getMultiCollectName() {
        return multiCurveNameEt.getText().toString();
    }

    /**
     * 获取测点IDs.
     *
     * @return 测点id字符串，以逗号分隔
     */
    @Override
    public String getMpointIds() {
        String ids = "";
        if (getSelectedPoints() != null) {
            for (PointEntity entity : getSelectedPoints()) {
                if (entity.getMpointId() != null) {
                    ids += entity.getMpointId() + ",";
                } else {
                    ids += entity.getId() + ",";
                }
            }
        }
        if (ids.length() > 0) {
            ids = ids.substring(0, ids.length() - 1);
        }
        return ids;
    }
}
