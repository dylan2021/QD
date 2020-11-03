package com.haocang.patrol.manage.presenter.impl;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.MethodConstants;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.GetListListener;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.utils.ToastUtil;
import com.haocang.patrol.constants.PatrolMethod;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.bean.UserDTO;
import com.haocang.patrol.manage.iview.AllocateView;
import com.haocang.patrol.manage.presenter.PatrolAllocatePresenter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by william on 2018/4/3.
 */

public class PatrolAllocatePresenterImpl implements PatrolAllocatePresenter, GetListListener<UserDTO> {
    //    private PageNavigator pageNavigator = new PageNavigator();
    private AllocateView allocateView;


    @Override
    public void setAllocateView(final AllocateView allocateView) {
        this.allocateView = allocateView;
    }

    /**
     *
     */
    @Override
    public void getAllocatorList() {
        Map<String, Object> map = new HashMap<>();
        if (allocateView.getOrgId() != null) {
            map.put("orgId", allocateView.getOrgId());
        }
        if (!TextUtils.isEmpty(allocateView.getQueryName())) {
            map.put("queryName", StringUtils.utfCode(allocateView.getQueryName()));
        }
        CommonModel<UserDTO> progressModel
                = new CommonModelImpl<>();
        Type type = new TypeToken<List<UserDTO>>() {
        }.getType();
        progressModel
                .setContext(allocateView.getContext())
                .setParamMap(map)
                .setHasDialog(false)
                .setListType(type)
                .setUrl(MethodConstants.Uaa.USER_LIST)
                .setListListener(new GetListListener<UserDTO>() {
                    @Override
                    public void success(final List<UserDTO> list) {
                        allocateView.renderList(list);
                    }
                })
                .getList();

//        allocateTaskModel.getAllocatorListData(allocateView.getContext(), map, this);
    }

    /**
     *
     */
    @Override
    public void allocatePatrolTask() {
        Map<String, Object> map = new HashMap<>();
        map.put("taskId", allocateView.getTaskId());
        map.put("executorId", allocateView.getExecutorId());
        CommonModel<Integer> commonModel = new CommonModelImpl<>();
        commonModel
                .setContext(allocateView.getContext())
                .setUrl(PatrolMethod.PATROL_TASK_UPDATE)
                .setParamMap(map)
                .setEntityType(Integer.class)
                .setEntityListener(new GetEntityListener<Integer>() {
                    @Override
                    public void success(final Integer entity) {
                        allocateView.success(entity + "");
//                        repairListView.updateChangedItem();
                    }

                    @Override
                    public void fail(final String err) {
                        ToastUtil.makeText(allocateView.getContext(),
                                allocateView.getContext().getString(R.string.patrol_assign_fail));
                    }
                }).putEntity();
    }


    @Override
    public void success(final List<UserDTO> list) {
        allocateView.renderList(list);
    }
}
