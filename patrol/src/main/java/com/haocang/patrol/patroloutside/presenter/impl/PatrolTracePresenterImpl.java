package com.haocang.patrol.patroloutside.presenter.impl;


import com.baidu.location.BDLocation;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.utils.BDSendTraceUtil;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.base.utils.TimeUtil;
import com.haocang.offline.bean.patrol.PatrolActualPathEntity;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.haocang.patrol.constants.PatrolMethod;
import com.haocang.patrol.patroloutside.iview.PatrolTraceView;
import com.haocang.patrol.patroloutside.presenter.PatrolTracePresenter;

import java.util.Date;
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
 * 创建时间：2018/4/15下午10:19
 * 修  改  者：
 * 修改时间：
 */
public class PatrolTracePresenterImpl implements PatrolTracePresenter {

    /**
     *
     */
    private PatrolTraceView patrolTraceView;

    @Override
    public void sendTrace(BDLocation location) {

        if (BDSendTraceUtil.getInstance().isLocationValid()) {
            if (OffLineOutApiUtil.isOffLine()) {//离线的时候保存轨迹
                preservationTrace(location);
                return;
            }
            upladTrace(location);
        }
    }

    private void upladTrace(BDLocation location) {
        Map<String, Object> map = new HashMap<>();
        if (location != null) {
            map.put("latitude", location.getLatitude());
            map.put("longitude", location.getLongitude());
            map.put("place", location.getAddrStr());
            map.put("patrolTaskId", patrolTraceView.getTaskId());
            map.put("updateTime", TimeTransformUtil.getUploadGMTTime(TimeUtil.getDateTimeStr(new Date())));
            CommonModel<String> model = new CommonModelImpl<>();
            model.setContext(patrolTraceView.getContext())
                    .setParamMap(map)
                    .setEntityType(Integer.class)
                    .setHasDialog(false)
                    .setUrl(PatrolMethod.PATROL_TASK_UPLOADTRACE )
                    .setEntityListener(new GetEntityListener<Integer>() {
                        @Override
                        public void success(final Integer entity) {

                        }

                        @Override
                        public void fail(final String err) {

                        }
                    })
                    .postEntity();
        }

    }

    /**
     *
     */
    @Override
    public void sendTrace() {
        if (BDSendTraceUtil.getInstance().isLocationValid()) {
            BDLocation location = BDSendTraceUtil.getInstance().getLocation();
            if (OffLineOutApiUtil.isOffLine()) {//离线的时候保存轨迹
                preservationTrace(location);
                return;
            }
            upladTrace(location);
        }

    }

    @Override
    public void setPatrolTraceView(final PatrolTraceView patrolTraceView) {
        this.patrolTraceView = patrolTraceView;
    }

    private void preservationTrace(BDLocation location) {
        PatrolActualPathEntity actualPathEntity = new PatrolActualPathEntity();
        if (location != null) {
            actualPathEntity.setLatitude(location.getLatitude());
            actualPathEntity.setLongitude(location.getLongitude());
            actualPathEntity.setPlace(location.getAddrStr());
        }
        actualPathEntity.setPatrolTaskId(patrolTraceView.getTaskId());
        actualPathEntity.setUpdateTime(TimeTransformUtil.getUploadGMTTime(TimeUtil.getDateTimeStr(new Date())));
        OffLineOutApiUtil.preservationTrace(actualPathEntity);
    }
}
