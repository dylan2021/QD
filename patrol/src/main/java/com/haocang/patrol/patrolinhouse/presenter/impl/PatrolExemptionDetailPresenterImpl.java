package com.haocang.patrol.patrolinhouse.presenter.impl;

import android.app.Activity;
import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.GetListListener;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.constants.PatrolMethod;
import com.haocang.patrol.patrolinhouse.bean.PatrolTaskPointStep;
import com.haocang.patrol.patrolinhouse.iview.PatrolExemptionView;
import com.haocang.patrol.patrolinhouse.presenter.PatrolExemptionDetailPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：${DATA} 14:29
 * 修 改 者：
 * 修改时间：
 */
public class PatrolExemptionDetailPresenterImpl implements PatrolExemptionDetailPresenter {
    private PatrolExemptionView exemptionView;

    public PatrolExemptionDetailPresenterImpl(PatrolExemptionView exemptionView) {
        this.exemptionView = exemptionView;
    }

    @Override
    public void getPatrolExemptionList() {
        Map<String, Object> map = new HashMap<>();
        map.put("pointId", exemptionView.getPointId());
        map.put("taskId", exemptionView.getTaskId());
        map.put("queryName",
                StringUtils.utfCode(exemptionView.getQueryName()));
        CommonModel<PatrolTaskPointStep> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<PatrolTaskPointStep>>() {
        }.getType();
        progressModel
                .setContext(exemptionView.getContexts())
                .setParamMap(map)
                .setListType(type)
                .setUrl(PatrolMethod.PATROL_TASK_POINT_DETAIL)
                .setListListener(new GetListListener<PatrolTaskPointStep>() {
                    @Override
                    public void success(final List<PatrolTaskPointStep> list) {
                        exemptionView.setRenderList(list);
                    }
                })
                .getList();
    }

    @Override
    public void submitData() {
        Map<String, Object> map = new HashMap<>();
        map.put("patrolTaskId", exemptionView.getTaskId());
        map.put("patrolHasResultCount", exemptionView.getHasResultCount());
        map.put("faultCount", 0);
        map.put("newFaultCount", 0);
        map.put("id", exemptionView.getPatrolPointId());
        if (!TextUtils.isEmpty(exemptionView.getLastResultUpdateTime())) {
            map.put("resultUpdateTime", TimeTransformUtil.getUploadGMTTime(exemptionView.getLastResultUpdateTime()));
        }
        List<PatrolTaskPointStep> steps = exemptionView.getPatrolPointSteps();
        JSONArray array = new JSONArray();
        JSONObject object = null;
        for (PatrolTaskPointStep step : steps) {
            object = new JSONObject();
            try {
                object.put("id", step.getId());
                object.put("stepComment", step.getStepComment());
                object.put("stepResult", step.getStepResult());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(object);
        }
        map.put("patrolTaskPointSteps", array);

        CommonModel<String> model = new CommonModelImpl<>();
        model.setContext(exemptionView.getContexts())
                .setParamMap(map)
                .setEntityType(Integer.class)
                .setUrl(PatrolMethod.PATROL_TASKPOINT_UPDATEALL)
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(final Integer entity) {
                        exemptionView.submitSucess();
                    }

                    @Override
                    public void fail(final String err) {
                        ToastUtil.makeText(exemptionView.getContexts(),
                                exemptionView.getContexts().getString(R.string.patrol_upload_failed));
                    }
                })
                .putEntity();

    }
}
