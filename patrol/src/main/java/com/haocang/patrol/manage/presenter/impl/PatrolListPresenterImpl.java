package com.haocang.patrol.manage.presenter.impl;

import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.bean.PageNavigator;
import com.haocang.base.utils.GetListListener;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.base.utils.TimeUtil;
import com.haocang.offline.bean.patrol.PatrolTaskEntity;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.constants.PatrolMethod;
import com.haocang.patrol.manage.bean.PatrolTaskListDTO;
import com.haocang.patrol.manage.iview.PatrolListView;
import com.haocang.patrol.manage.presenter.PatrolListPresenter;
import com.haocang.patrol.manage.ui.PatrolFilterActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by william on 2018/4/2.
 */

public class PatrolListPresenterImpl
        implements PatrolListPresenter,
        GetListListener<PatrolTaskListDTO> {
    private PageNavigator pageNavigator = new PageNavigator();
    private PatrolListView mView;

    public PatrolListPresenterImpl(final PatrolListView view) {
        mView = view;
    }

    //
    @Override
    public void getPatrolList() {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(mView.getStartTime())) {
            map.put("executeDateStart",
                    TimeTransformUtil.getUploadGMTTime(mView.getStartTime()));
        }
        map.put("executeDateEnd",
                TimeTransformUtil.getUploadGMTTime(mView.getEndTime()));
        map.put("currentPage", mView.getPage());
        map.put("executeStatus", mView.getExcuteStatus());
        map.put("flag", "1");//todo 接口做兼容
        map.put("queryName", StringUtils.utfCode(mView.getQueryName()));
        if ("1".equals(mView.getAllExecutor())) {
            map.put("allExecutor", mView.getAllExecutor());
        }
        CommonModel<PatrolTaskListDTO> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<PatrolTaskListDTO>>() {
        }.getType();
        progressModel
                .setContext(mView.getContext())
                .setParamMap(map)
                .setHasDialog(false)
                .setListType(type)
                .setUrl(PatrolMethod.PATROL_LIST)
                .setListListener(new GetListListener<PatrolTaskListDTO>() {
                    @Override
                    public void success(final List<PatrolTaskListDTO> list) {
                        mView.renderList(list);
                    }
                })
                .getList();
//        patrolListModel.getPatrolListData(mView.getContext(), map, this);
    }

    @Override
    public void getOffLinePatrolList() {
        List<PatrolTaskEntity> list = new ArrayList<>();
        if (!TextUtils.isEmpty(mView.getQueryName())) {
            list.addAll(OffLineOutApiUtil.getPatrolTaskList(mView.getQueryName()));
        } else {
            list.addAll(OffLineOutApiUtil.getPatrolTaskList());
        }
        List<PatrolTaskEntity> patrolList = modifyState(list);
        if (patrolList != null && patrolList.size() > 0) {
            String task = new Gson().toJson(patrolList);
            Type type = new TypeToken<List<PatrolTaskListDTO>>() {
            }.getType();
            List<PatrolTaskListDTO> taskList = new Gson().fromJson(task, type);
            mView.offLineRenderList(taskList);
        } else {
            mView.offLineRenderList(null);
        }
    }

    /**
     * 任务时间已过的修改任务状态
     */
    private List<PatrolTaskEntity> modifyState(List<PatrolTaskEntity> list) {
        List<PatrolTaskEntity> taskList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            PatrolTaskEntity entity = list.get(i);
            String time = TimeUtil.getDateStr4(entity.getEndTime());
            if (TimeUtil.getTranData(time).before(new Date()) && !entity.getExecuteStatus().equals("finished")) {//如果任务时间结束时间再当前时间之前的话修改状态为异常
                entity.setExecuteStatus("abnormal");
                OffLineOutApiUtil.modifyTask(entity);
            } else {
                taskList.add(entity);
            }
        }
        return taskList;
    }

    @Override
    public void setDefalutCondition() {
        Date date = new Date();
        Date startTime = TimeUtil.getWeekFirstDay(date);
        Date endTime = TimeUtil.getWeekLastDay(date);
//        mView.setStartTime(TimeUtil.getDateTimeStr(startTime));
        mView.setEndTime(TimeUtil.getDateTimeStr(date));
        String[] patrolStatusKeyArr = mView.getContext().getResources()
                .getStringArray(R.array.patrol_default_status_keys);
        StringBuilder keys = new StringBuilder();
        for (String key : patrolStatusKeyArr) {
            keys.append(key + ",");
        }
        if (keys.length() > 0) {
            keys = keys.deleteCharAt(keys.length() - 1);
        }
        mView.setExcuteStatus(keys.toString());
    }

    @Override
    public void showFilterView() {
        Intent intent = new Intent(mView.getActivity(), PatrolFilterActivity.class);
        if (mView.getCurDateStr() != null) {
            intent.putExtra("curDateStr", mView.getCurDateStr());
        }
        if (mView.getExcuteStatus() != null) {
            intent.putExtra("excuteStatus", mView.getExcuteStatus());
        }
        if ("1".equals(mView.getAllExecutor())) {
            intent.putExtra("allExecutor", mView.getAllExecutor());
        }
        mView.getActivity()
                .startActivityForResult(intent, PatrolFilterActivity.REQUEST);
    }

    @Override
    public void getItem(final int changePosition) {
        Map<String, Object> map = new HashMap<>();
        map.put("executeDateStart", TimeTransformUtil.getUploadGMTTime(mView.getStartTime()));
        map.put("executeDateEnd", TimeTransformUtil.getUploadGMTTime(mView.getEndTime()));
        map.put("currentPage", changePosition);
        map.put("pageSize", 1);
        map.put("executeStatus", mView.getExcuteStatus());
        map.put("queryName", StringUtils.utfCode(mView.getQueryName()));
        CommonModel<PatrolTaskListDTO> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<PatrolTaskListDTO>>() {
        }.getType();
        progressModel
                .setContext(mView.getContext())
                .setParamMap(map)
                .setHasDialog(false)
                .setListType(type)
                .setUrl(PatrolMethod.PATROL_LIST)
                .setListListener(new GetListListener<PatrolTaskListDTO>() {
                    @Override
                    public void success(final List<PatrolTaskListDTO> list) {
                        mView.renderChangedItem(list);
                    }
                })
                .getList();

    }

    /**
     * @param list
     */
    @Override
    public void success(final List<PatrolTaskListDTO> list) {
        mView.renderList(list);
    }
}
