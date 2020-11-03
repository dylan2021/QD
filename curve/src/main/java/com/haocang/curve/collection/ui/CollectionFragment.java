package com.haocang.curve.collection.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.curve.R;

import java.util.HashMap;
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
 * 创建时间：2018/5/30下午3:39
 * 修  改  者：
 * 修改时间：
 */
@Route(path = ArouterPathConstants.Curve.CURVE_COLLECTION)
public class CollectionFragment
        extends Fragment implements View.OnClickListener {

    /**
     * 上下文参数.
     */
    private Context mContext;
    /**
     * 数据组合曲线标签.
     */
    private Button curveMutiTv;
    /**
     * 单数据曲线标签.
     */
    private Button curveSignleTv;

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
                inflater.inflate(R.layout.curve_collection_fragment, null);
        mContext = getActivity();
        initView(view);
        return view;
    }

    /**
     * @param view 根View.
     */
    private void initView(final View view) {
        TextView completeTv = view.findViewById(R.id.common_complete_tv);
        completeTv.setText(R.string.curve_more_select);
        curveMutiTv = view.findViewById(R.id.curve_muti_tv);
        curveSignleTv = view.findViewById(R.id.curve_signle_tv);
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(mContext.getString(R.string.curve_pick_collection));
        selectSecond();
        completeTv.setOnClickListener(this);
        view.findViewById(R.id.curve_muti_tv).setOnClickListener(this);
        view.findViewById(R.id.curve_signle_tv).setOnClickListener(this);
    }


    /**
     * 跳转到对应到Fragment.
     *
     * @param fragment 厂外巡检
     */
    private void toFragment(final Fragment fragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.patrol_container_fl, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * @param v
     */
    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.curve_muti_tv) {
            selectFirst();
        } else if (v.getId() == R.id.curve_signle_tv) {
            selectSecond();
        } else if (v.getId() == R.id.common_complete_tv) {
            toMore();
        }
    }

    /**
     * 选择更多.
     */
    private void toMore() {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(getIsHomeJump())) {
            map.put("type", "type");
        }
        map.put("fragmentUri", ArouterPathConstants.Curve.CURVE_POINT_LIST);
        map.put("selectedPointsStr", getActivity().getIntent().getStringExtra("selectedPointsStr"));
        ARouterUtil.startActivityForResult(map, getActivity(), 3002);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3002 && data != null) {
            String selectedPointsStr = data.getStringExtra("selectedPointsStr");
            if (!TextUtils.isEmpty(selectedPointsStr)) {
                Intent intent = new Intent();
                intent.putExtra("selectedPointsStr", selectedPointsStr);
                getActivity().setResult(3002, intent);
                getActivity().finish();
            }
        }
    }

    /**
     *
     */
    private void selectSecond() {
        curveSignleTv.setTextColor(getResources().getColor(R.color.curve_tab_select));
        curveMutiTv.setTextColor(getResources().getColor(R.color.curve_tab_unselect));
        toFragment(new SignleCollectionFragment());
    }

    private void selectFirst() {
        curveMutiTv.setTextColor(getResources()
                .getColor(R.color.curve_tab_select));
        curveSignleTv.setTextColor(getResources()
                .getColor(R.color.curve_tab_unselect));
        toFragment(new MultiCollectionFragment());
//        ARouterUtil.toFragment();
    }

    private String getIsHomeJump() {
        return getActivity().getIntent().getStringExtra("main");
    }

}
