package com.haocang.fault.list.ui;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.adapter.PictureAdapter;
import com.haocang.base.bean.PictureInfo;
import com.haocang.base.config.LibConfig;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.base.widgets.MyGridLayoutManager;
import com.haocang.fault.R;
import com.haocang.fault.list.bean.FaultProcessingResultsEntity;
import com.haocang.fault.list.iview.FaultProcessingResultsView;
import com.haocang.fault.list.presenter.FaultProcessingResultsPresenter;
import com.haocang.fault.list.presenter.impl.FaultProcessingResultsPresenterImpl;

import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：流程处理结果详情
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/2711:02
 * 修 改 者：
 * 修改时间：
 */
@Route(path = LibConfig.AROUTE_FAULT_PROCESSING_RESULTS)
public class FaultProcessingResultsFragment extends Fragment implements FaultProcessingResultsView {
    private TextView titleNameTv;
    private TextView faultProcessingResultTv;//处理结果
    private TextView faultReasonTv;//缺陷原因
    private TextView faultPersonTv;//处理人
    private TextView faultProcessingDateTv;//处理时间
    private TextView faultRemarkTv;//备注
    private TextView faultNodateTv;//暂无现场记录图片
    private PictureAdapter pictureAdapter;
    private RecyclerView recyclerview;
    private FaultProcessingResultsPresenter faultProcessingResultsPresenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_processing_results, null);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.falut_processing_results));
        faultProcessingResultTv = view.findViewById(R.id.fault_processing_result_tv);
        faultReasonTv = view.findViewById(R.id.faultReason_tv);
        faultPersonTv = view.findViewById(R.id.fault_person_tv);
        faultProcessingDateTv = view.findViewById(R.id.processing_date_tv);
        faultRemarkTv = view.findViewById(R.id.fault_remark_tv);
        recyclerview = view.findViewById(R.id.recyclerview);
        faultNodateTv = view.findViewById(R.id.no_pic_tv);
        pictureAdapter = new PictureAdapter(getActivity());
        recyclerview.setLayoutManager(new MyGridLayoutManager(getActivity(), 3));
        recyclerview.setAdapter(pictureAdapter);
        pictureAdapter.setDisplay();
        faultProcessingResultsPresenter = new FaultProcessingResultsPresenterImpl(this);
        faultProcessingResultsPresenter.getFaultProcessingData();
    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public String getFaultRecordId() {
        return getFaultRecordIds();
    }

    private String getFaultRecordIds() {
        return getActivity().getIntent().getStringExtra("faultContrailId");
    }

    @Override
    public void setFaultData(FaultProcessingResultsEntity entity) {
        if (entity != null) {
            faultProcessingResultTv.setText(entity.getProcessingResultName());
            faultReasonTv.setText(entity.getFaultReason());
            faultPersonTv.setText(entity.getProcessingPersonName());
            if (!TextUtils.isEmpty(entity.getUpdateDate())) {
                faultProcessingDateTv.setText(TimeTransformUtil.getShowLocalTime(entity.getUpdateDate()));
            }
            faultRemarkTv.setText(entity.getRemark());
            getImgUrl(entity.getImgUrl());
        }
    }

    private void getImgUrl(List<String> imgUrl) {
        pictureAdapter.clear();
        if (imgUrl != null && imgUrl.size() > 0) {
            try {
                for (int i = 0; i < imgUrl.size(); i++) {
                    addItem(imgUrl.get(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            faultNodateTv.setVisibility(View.VISIBLE);
        }
        pictureAdapter.notifyDataSetChanged();
    }

    private void addItem(final String path) {
        PictureInfo entity = new PictureInfo();
        if (StringUtils.isPicture(path)) {
            entity.setLocalImgPath(path);
            entity.setType(0);
            pictureAdapter.addItem(entity);
        } else if (path.contains(".mp4")) {
            entity.setNetWordVideoPath(path);
            entity.setType(PictureInfo.VIDEO);
            pictureAdapter.addItem(entity);
        }
    }
}
