package com.haocang.repair.post.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.repair.R;
import com.haocang.repair.manage.bean.RepairConstans;
import com.haocang.repair.manage.bean.RepairRecordVo;
import com.haocang.repair.post.bean.RepairRecordDto;
import com.haocang.repair.post.iview.PostRepairResultView;
import com.haocang.repair.post.presenter.PostRepairResultPresenter;
import com.haocang.repair.post.presenter.impl.RepairPostResultPresenterImpl;

import java.util.Date;
import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题： 维修结果填写.
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：william
 * 创建时间：2018/5/216:45
 * 修 改 者：
 * 修改时间：
 */
@Route(path = ArouterPathConstants.Repair.REPAIR_POST_RESULT)
public class PostRepairResultFragment extends Fragment
        implements View.OnClickListener, PostRepairResultView {
    /**
     * 维修结果.
     */
    private TextView mResultTv;
    /**
     * 维修措施.
     */
    private TextView mMethodTv;
    /**
     * 完成时间.
     */
    private TextView mCompleteTimeTv;
    /**
     *
     */
    private PostRepairResultPresenter mPresenter;

    /**
     *
     */
    private PostPictureUtil postPictureUtil;

    private Date createDt = new Date();

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repair_post_result_fragment, null);
        initView(view);
        return view;
    }

    /**
     * @param view 根View.
     */
    private void initView(final View view) {
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.repair_processing_result_repari));
        TextView commonTv = view.findViewById(R.id.common_tv);
        commonTv.setVisibility(View.VISIBLE);
        commonTv.setOnClickListener(this);
        commonTv.setText(getText(R.string.base_complete));
        view.findViewById(R.id.add_pic_iv).setOnClickListener(this);
        postPictureUtil = new PostPictureUtil(this, view);
        mPresenter = new RepairPostResultPresenterImpl();
        mPresenter.setView(this);
        mResultTv = view.findViewById(R.id.repair_result_tv);
        mMethodTv = view.findViewById(R.id.repair_method_tv);
        mCompleteTimeTv = view.findViewById(R.id.repair_complete_time_tv);
        mCompleteTimeTv.setText(TimeUtil.getDateTimeStr(new Date()));
        mPresenter.getRepairResult();
        view.findViewById(R.id.repair_result_ll).setOnClickListener(this);
        view.findViewById(R.id.repair_method_ll).setOnClickListener(this);
        view.findViewById(R.id.repair_complete_time_ll).setOnClickListener(this);
        view.findViewById(R.id.repair_analysis_ll).setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        if (id == R.id.repair_result_ll) {
            mPresenter.showPickResultView();
        } else if (id == R.id.repair_method_ll) {
            mPresenter.showPickMethodView();
        } else if (id == R.id.repair_complete_time_ll) {
            mPresenter.showCompleteTimeView();
        } else if (id == R.id.repair_analysis_ll) {
            mPresenter.toRemarkFragment();
        } else if (id == R.id.common_tv) {
            mPresenter.submit();
        }
    }

    @Override
    public void onActivityResult(final int requestCode,
                                 final int resultCode,
                                 final Intent data) {
        postPictureUtil.onActivityResult(requestCode, resultCode, data);
        if ((RepairConstans.StartActivityCode.REMARK_RESULT_CODE == resultCode
                || RepairConstans.StartActivityCode.REMARK_REQUEST_CODE == resultCode) && data != null) {
            String remark = data.getStringExtra("name");
            mPresenter.setRemark(remark);
        }
    }


    /**
     * @param resultKey  结果
     * @param resultName 结果名称
     */
    @Override
    public void setRepairResult(final int resultKey, final String resultName) {
        mResultTv.setText(resultName);
    }

    /**
     * @param resultKey  维修措施
     * @param methodName 维修措施名称
     */
    @Override
    public void setRepairMethod(final int resultKey, final String methodName) {
        mMethodTv.setText(methodName);
    }

    /**
     * @param time 完成时间
     */
    @Override
    public void setCompleteTime(final String time) {
        mCompleteTimeTv.setText(time);
    }

    /**
     * @return
     */
    @Override
    public String getRepairRecordId() {
        return getActivity().getIntent().getStringExtra("repairRecordId");
    }

    /**
     * @param entity 维修记录
     */
    @Override
    public void setDetailData(final RepairRecordDto entity) {
        RepairRecordVo recordVo = entity.getRepairRecordVo();
        if (recordVo != null) {
            String repairResult = "";
            if (RepairConstans.STATE_UNPROCESS != recordVo.getRepairResult()) {
                repairResult = recordVo.getRepairResultName();
            }
            mResultTv.setText(repairResult);
            mMethodTv.setText(recordVo.getRepairAdoptName());
            String finishDate = TimeUtil
                    .getDateTimeStr(TimeUtil.getDateTimeWithoutSpace(recordVo.getFinishDate()));
            mCompleteTimeTv.setText(finishDate);
            if (entity != null && entity.getRepairRecordVo() != null) {
                postPictureUtil.setUrlList(entity.getRepairRecordVo().getPictureVideos());
            }
            createDt = TimeUtil.getDateByStr(TimeTransformUtil.getShowLocalTime(recordVo.getCreateDate()));
        }
    }

    @Override
    public void setNoFutureTime() {
        ToastUtil.makeText(getActivity(), "无法选择未来时间");
    }

    /**
     * @return
     */
    @Override
    public List<String> getfileList() {
        return postPictureUtil.getfileList();
    }

    @Override
    public String getNetWorkList() {
        return postPictureUtil.getNetWorkList();
    }

    @Override
    public String getNetWordThumbnailUrl() {
        return postPictureUtil.getNetWordThumbnailUrl();
    }

    @Override
    public Date getCreateData() {
        return createDt;
    }

}
