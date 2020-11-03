package com.haocang.waterlink.experiment.presenter;

import android.util.Log;

import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.TimeUtil;
import com.haocang.repair.constants.RepairMethod;
import com.haocang.repair.post.bean.RepairRecordDto;
import com.haocang.waterlink.constant.HomeMethodConfig;
import com.haocang.waterlink.experiment.ExperimentFragment;
import com.haocang.waterlink.experiment.bean.ExperimentListBean;

import java.util.Date;
import java.util.Map;

public class ExperimentPresenterImpl implements ExperimentPresenter {

    ExperimentFragment mFragment;

    public void setView(ExperimentFragment fragment){
            this.mFragment = fragment;
    }

    public void getDataList(Map<String,Object> paramsMap) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("repairRecordId", postRepairResultView.getRepairRecordId());
        CommonModel<ExperimentListBean> progressModel = new CommonModelImpl<>();
        progressModel
                .setContext(mFragment.getActivity())
//                .setParamMap(map)
                .setEntityType(ExperimentListBean.class)
                .setUrl(HomeMethodConfig.EXPERIMENT_LIST)
                .setParamMap(paramsMap)
                .setEntityListener(new GetEntityListener<ExperimentListBean>() {
                    @Override
                    public void success(final ExperimentListBean entity) {

                        Log.e("==============",""+entity.toString());
                        mFragment.setData(entity);
//                        if (entity != null) {
//                            mRecordVo = entity.getRepairRecordVo();
//                            mRecordVo.setFinishDate(TimeUtil.getDateSTimetr(new Date()));
//                        }
//                        postRepairResultView.setDetailData(entity);
                    }

                    @Override
                    public void fail(final String err) {

                    }
                })
                .getEntityNew();
    }
}
