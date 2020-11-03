package com.haocang.patrol.patrolinhouse.presenter.impl;

import com.google.gson.Gson;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.offline.bean.patrol.PatrolTaskEntity;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.haocang.patrol.constants.PatrolMethod;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskResultDTO;
import com.haocang.patrol.patrolinhouse.iview.PatrolResultView;
import com.haocang.patrol.patrolinhouse.presenter.PatrolResultPresenter;

import java.util.Calendar;
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
 * 创建时间：2018/4/10上午11:35
 * 修  改  者：
 * 修改时间：
 */
public class PatrolResultPresenterImpl implements PatrolResultPresenter {

    private PatrolResultView mPatrolResultView;

    public PatrolResultPresenterImpl(final PatrolResultView patrolResultView) {
        mPatrolResultView = patrolResultView;
    }

    @Override
    public void getResultData() {
        if (OffLineOutApiUtil.isOffLine()) {
            getOffLineResultData();
        } else {
            getResulstData();
        }
    }

    private void getOffLineResultData() {
        PatrolTaskEntity entity = OffLineOutApiUtil.getPatrolTaskData(mPatrolResultView.getTaskId());
        if (entity != null) {
            String gson = new Gson().toJson(entity);
            PatrolTaskResultDTO taskResultDTO = new Gson().fromJson(gson, PatrolTaskResultDTO.class);
            mPatrolResultView.renderData(taskResultDTO);
        }

    }


    private void getResulstData() {
        CommonModel<PatrolTaskResultDTO> progressModel = new CommonModelImpl<>();
        progressModel
                .setContext(mPatrolResultView.getContext())
                .setEntityType(PatrolTaskResultDTO.class)
                .setUrl(PatrolMethod.PATROL_TASK_RESULT + "/" +  mPatrolResultView.getTaskId())
                .setEntityListener(new GetEntityListener<PatrolTaskResultDTO>() {
                    @Override
                    public void success(final PatrolTaskResultDTO entity) {
                        mPatrolResultView.renderData(entity);
                    }

                    @Override
                    public void fail(final String err) {

                    }
                }).getEntityNew();


    }

}
