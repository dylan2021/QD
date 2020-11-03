package com.haocang.repair.progress.presenter.impl;

import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.repair.constants.RepairMethod;
import com.haocang.repair.progress.bean.ProcessingProgressVo;
import com.haocang.repair.progress.iview.RepairProgressView;
import com.haocang.repair.progress.presenter.RepairProgressPresenter;

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
 * 创建时间：2018/5/14上午9:10
 * 修  改  者：
 * 修改时间：
 */
public class    RepairProgressPresenterImpl
        implements RepairProgressPresenter {

    /**
     * 维修进度交互接口.
     */
    private RepairProgressView mProgressView;

    /**
     * 获取进度列表.
     */
    @Override
    public void getProgressList() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", mProgressView.getRepairId());
        CommonModel<ProcessingProgressVo> progressModel = new CommonModelImpl<>();
        progressModel
                .setContext(mProgressView.getContext())
                .setParamMap(map)
                .setEntityType(ProcessingProgressVo.class)
                .setUrl(RepairMethod.REPAIR_PROGRESS + "/" + mProgressView.getRepairId())
                .setEntityListener(new GetEntityListener<ProcessingProgressVo>() {
                    @Override
                    public void success(final ProcessingProgressVo entity) {
                        mProgressView.renderData(entity);
                    }

                    @Override
                    public void fail(final String err) {

                    }
                })
                .getEntityNew();
    }

    /**
     * @param view 界面交互接口
     */
    @Override
    public void setView(final RepairProgressView view) {
        mProgressView = view;
    }
}
