package com.haocang.repair.manage.presenter.impl;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.bigkoo.alertview.AlertView;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.utils.ConcernUtil;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.GetListListener;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.repair.R;
import com.haocang.repair.constants.RepairMethod;
import com.haocang.repair.manage.bean.RepairVo;
import com.haocang.repair.manage.iview.RepairManageView;
import com.haocang.repair.manage.presenter.RepairListPresenter;
import com.haocang.repair.manage.ui.RepairFilterActivity;

import java.lang.reflect.Type;
import java.util.Date;
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
 * 创 建 者：he
 * 创建时间：2018/5/314:27
 * 修 改 者：
 * 修改时间：
 */
public class RepairListPresenterImpl implements RepairListPresenter {
    /**
     * 维修UI接口.
     */
    private RepairManageView repairListView;
//    /**
//     * 维修获取数据接口.
//     */
//    private RepairListModel repairListModel;

    /**
     * 构造函数.
     *
     * @param view UI交互接口
     */
    public RepairListPresenterImpl(final RepairManageView view) {
        this.repairListView = view;
//        repairListModel = new RepairListModelImpl();
    }

    /**
     * 获取维修列表.
     */
    @Override
    public void getRepairList() {
        Map<String, Object> map = getParamMap();
        CommonModel<RepairVo> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<RepairVo>>() {
        }.getType();
        progressModel
                .setContext(repairListView.getContexts())
                .setParamMap(map)
                .setHasDialog(false)
                .setListType(type)
                .setUrl(RepairMethod.REPAIR_LIST)
                .setListListener(new GetListListener<RepairVo>() {
                    @Override
                    public void success(final List<RepairVo> list) {
                        repairListView.renderList(list);
                    }
                })
                .getList();

    }

    private Map<String, Object> getParamMap() {
        int page = repairListView.getCurrentPage();
        String quertName = repairListView.queryName();
        Map<String, Object> map = new HashMap<>();
        map.put("currentPage", page);
        if (!TextUtils.isEmpty(repairListView.getProcessingPersonIds())) {
            map.put("processingPersonIds",
                    repairListView.getProcessingPersonIds());
        }
        if (!TextUtils.isEmpty(quertName)) {
            map.put("queryName", StringUtils.utfCode(quertName));
        }
        if (!TextUtils.isEmpty(repairListView.getStateIds())) {
            map.put("states", repairListView.getStateIds());
        }
        if (!TextUtils.isEmpty(repairListView.getStartTime())) {
            map.put("startDate", TimeTransformUtil
                    .getUploadGMTTime(repairListView.getStartTime()));
        }
        if (!TextUtils.isEmpty(repairListView.getEndTime())) {
            map.put("endDate", TimeTransformUtil
                    .getUploadGMTTime(repairListView.getEndTime()));
        }
        if (repairListView.getEquId() != null) {
            map.put("equId", repairListView.getEquId());
        }
        if (repairListView.getProcessId() != null) {
            map.put("processIds", repairListView.getProcessId());
        }
        return map;

    }

    /**
     * 显示筛选界面.
     */
    @Override
    public void showFilterView() {
        Intent intent = new Intent(getContext(),
                RepairFilterActivity.class);
        if (repairListView.getSelectDateStr() != null) {
            intent.putExtra("selectDateStr",
                    repairListView.getSelectDateStr());
        }
        if (repairListView.getStateIds() != null) {
            intent.putExtra("stateIds",
                    repairListView.getStateIds());
        }
        if (repairListView.getCreatePersonIds() != null) {
            intent.putExtra("createPersonIds",
                    repairListView.getCreatePersonIds());
        }
        if (repairListView.getExcutePersonIds() != null) {
            intent.putExtra("excutePersonIds",
                    repairListView.getExcutePersonIds());
        }
        repairListView.getActivity()
                .startActivityForResult(intent, RepairFilterActivity.REQUEST);
    }

    /**
     * 设置默认条件.
     */
    @Override
    public void setDefaultFilterCondition() {
        Date date = new Date();
//        repairListView.setSelectDateStr(TimeUtil.getDateTimeStr(date));
        Date startTime = TimeUtil.getWeekFirstDay(date);
        Date endTime = TimeUtil.getWeekLastDay(date);
//        repairListView.setStartTime(TimeUtil.getDateTimeStr(startTime));
//        repairListView.setEndTime(TimeUtil.getDateTimeStr(endTime));
        String[] patrolStatusKeyArr = getContext().getResources()
                .getStringArray(R.array.repair_default_status_keys);
        StringBuilder keys = new StringBuilder();
        for (String key : patrolStatusKeyArr) {
            keys.append(key + ",");
        }
        if (keys.length() > 0) {
            keys = keys.deleteCharAt(keys.length() - 1);
        }
        repairListView.setStates(keys.toString());
//        repairListView.setState();

    }

    /**
     * @param selectItemId       要分配的维修单ID
     * @param processingPersonId 处理人ID
     */
    @Override
    public void taskAssign(final int selectItemId,
                           final int processingPersonId) {
        Context ctx = repairListView.getContexts();
        Map<String, Object> map = new HashMap<>();
        map.put("repairId", selectItemId);
        map.put("processingPersonId", processingPersonId);
        CommonModel<Integer> commonModel = new CommonModelImpl<>();
        commonModel
                .setContext(repairListView.getContexts())
                .setUrl(RepairMethod.ASSIGN_TASK)
                .setParamMap(map)
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(final Integer entity) {
                        ToastUtil.makeText(getContext(),
                                getContext().getString(R.string.repair_assign_succuss));
                        getRepairList();
//                        repairListView.updateChangedItem();
                    }

                    @Override
                    public void fail(final String err) {
                        ToastUtil.makeText(getContext(),
                                getContext().getString(R.string.repair_assign_fail));
                    }
                }).putEntity();
    }

    @Override
    public void getChangedRepairItem(final int changedItemPosition) {
        Map<String, Object> map = getParamMap();
        CommonModel<RepairVo> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<RepairVo>>() {
        }.getType();
        progressModel
                .setContext(repairListView.getContexts())
                .setParamMap(map)
                .setHasDialog(false)
                .setListType(type)
                .setUrl(RepairMethod.REPAIR_LIST)
                .setListListener(new GetListListener<RepairVo>() {
                    @Override
                    public void success(final List<RepairVo> list) {
                        repairListView.renderChangedItem(list);
                    }
                })
                .getList();
    }

    @Override
    public void concern(final boolean concernFlag, final Integer id) {
        if (concernFlag) {
            new ConcernUtil()
                    .setContexts(getContext())
                    .setConcernId(id)
                    .setConcernType(ConcernUtil.REPAIR_TYPE)
                    .setConcernSuccess(concernInterface)
                    .addConcern();


        } else {
//            new ConcernUtil()
//                    .setContexts(getContext())
//                    .setConcernId(id)
//                    .setConcernType(ConcernUtil.REPAIR_TYPE)
//                    .setConcernSuccess(concernInterface)
//                    .abolishConcern();
            promptDialog(id);
        }
    }

    /**
     *
     */
    private ConcernUtil.AddConcernInterface concernInterface = new ConcernUtil.AddConcernInterface() {

        @Override
        public void concernSucess() {
            getRepairList();
//            repairListView.updateChangedItem();
        }

        @Override
        public void concernError() {
            ToastUtil.makeText(getContext(), getContext().getString(R.string.concern_failed));
        }

        @Override
        public void abolishConcern() {
            getRepairList();
//            repairListView.updateChangedItem();
        }
    };

    /**
     * 获取上下文参数.
     *
     * @return 返回上下文参数
     */
    public Context getContext() {
        return repairListView.getContexts();
    }

    private void promptDialog(final int id) {
        new AlertView("提示", "是否确定取消关注？", "取消",
                new String[]{"确定"}, null, repairListView.getContexts(), AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position >= 0) {
                    new ConcernUtil()
                            .setConcernId(id)
                            .setConcernType(ConcernUtil.REPAIR_TYPE)
                            .setContexts(repairListView.getContexts())
                            .setConcernSuccess(concernInterface)
                            .abolishConcern();
                }
            }
        }).show();
    }
}
