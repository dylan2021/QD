package com.haocang.repair.progress.presenter.impl;

import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.TimeUtil;
import com.haocang.repair.constants.RepairMethod;
import com.haocang.repair.post.bean.RepairRecordDto;
import com.haocang.repair.progress.bean.AppRepairRecordDetailVo;
import com.haocang.repair.progress.iview.RepairProgressDetailView;
import com.haocang.repair.progress.presenter.RepairProgressDetailPresenter;

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
 * 创建时间：2018/5/15下午5:30
 * 修  改  者：
 * 修改时间：
 */
public class RepairProgressDetailPresenterImpl implements RepairProgressDetailPresenter {

    /**
     *
     */
    private RepairProgressDetailView repairProgressDetailView;

    /**
     *
     */
    @Override
    public void getProgressDetail() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("repairContrailId", repairProgressDetailView.getRepairRecordId());
        CommonModel<RepairRecordDto> progressModel = new CommonModelImpl<>();
        progressModel
                .setContext(repairProgressDetailView.getContext())
//                .setParamMap(map)
                .setEntityType(AppRepairRecordDetailVo.class)
                .setUrl(RepairMethod.REPAIR_PROGRESS_DETAIL + "/" + repairProgressDetailView.getRepairRecordId())
                .setEntityListener(new GetEntityListener<AppRepairRecordDetailVo>() {
                    @Override
                    public void success(final AppRepairRecordDetailVo entity) {
                        if (entity != null) {
//                            RepairRecordVo mRecordVo = entity.getRepairRecordVo();
                            if (entity.getFinishDate() != null) {
                                entity.setFinishDate(TimeUtil.minusDateTime(entity.getFinishDate()));
                            }
                        }
                        repairProgressDetailView.setDetailData(entity);
                    }

                    @Override
                    public void fail(final String err) {

                    }
                })
                .getEntityNew();
    }

    /**
     * @param view 界面交互View.
     */
    @Override
    public void setView(final RepairProgressDetailView view) {
        repairProgressDetailView = view;
    }
}
