package com.haocang.curve.addcollect.presenter.impl;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.utils.ToastUtil;
import com.haocang.curve.R;
import com.haocang.curve.addcollect.iview.CollectView;
import com.haocang.curve.addcollect.presenter.CollectPresenter;
import com.haocang.curve.constants.CurveMethod;
import com.haocang.curve.main.bean.CurveConstans;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题：添加收藏P层实现
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/6/6下午5:59
 * 修  改  者：
 * 修改时间：
 */
public class CollectPresenterImpl implements CollectPresenter {

    /**
     *
     */
    private CollectView collectView;


    /**
     * @param view 和主页面交互接口.
     */
    public CollectPresenterImpl(final CollectView view) {
        collectView = view;
    }

    /**
     *
     */
    @Override
    public void complete() {
        int collectType = collectView.getCollectType();
        if (collectType == CurveConstans.COLLECT_TYPE_MULTI) {
            String collectName = collectView.getMultiCollectName();
            if (!TextUtils.isEmpty(collectName)) {
                collectMulti();
            } else {
                ToastUtil.makeText(getContext(),
                        getContext().getString(R.string.multi_curve_notnull_tip));
            }
        } else if (collectType == CurveConstans.COLLECT_TYPE_SINGLE) {
            collectSingle();
        }
    }

    /**
     * 收藏单曲线.
     */
    private void collectSingle() {
        Map<String, Object> map = new HashMap<>();
        map.put("mpointIds", collectView.getMpointIds());
//        map.put("combineName", collectView.getMultiCollectName());
        CommonModel<String> progressModel
                = new CommonModelImpl<>();
        progressModel
                .setContext(getContext())
                .setParamMap(map)
                .setUrl(CurveMethod.SIGNLE_COLLECTION)
                .setEntityListener(new GetEntityListener<String>() {
                    @Override
                    public void success(final String result) {
                        ToastUtil.makeText(getContext(), getContext().getString(R.string.curve_collect_success));
                        ((Activity) getContext()).finish();
                    }

                    @Override
                    public void fail(final String err) {
                        ToastUtil.makeText(getContext(), getContext().getString(R.string.curve_collect_failed));
                    }
                })
                .postEntity();
    }


    /**
     * 收藏组合曲线.
     */
    private void collectMulti() {
        Map<String, Object> map = new HashMap<>();
        map.put("mpointIds", collectView.getMpointIds());
        map.put("combineName", collectView.getMultiCollectName());
        CommonModel<Integer> progressModel
                = new CommonModelImpl<>();
        progressModel
                .setContext(getContext())
                .setParamMap(map)
                .setUrl(CurveMethod.COMBINES_COLLECTION)
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(final Integer result) {
                        ToastUtil.makeText(getContext(), getContext().getString(R.string.curve_collect_success));
                        ((Activity) getContext()).finish();
                    }

                    @Override
                    public void fail(final String err) {
                        ToastUtil.makeText(getContext(), getContext().getString(R.string.curve_collect_failed));
                    }
                })
                .postEntity();
    }

    /**
     * 获取上下文参数.
     *
     * @return 上下文参数
     */
    public Context getContext() {
        return collectView.getContext();
    }
}
