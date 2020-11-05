package com.haocang.waterlink.experiment.presenter;

import android.util.Log;

import com.haocang.base.base.CommonModel;
import com.haocang.base.base.impl.CommonModelImpl;
import com.haocang.base.config.LibConfig;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.GetListStringListener;
import com.haocang.waterlink.constant.HomeMethodConfig;
import com.haocang.waterlink.experiment.ExperimentDetailFragment;
import com.haocang.waterlink.experiment.ExperimentFragment;
import com.haocang.waterlink.experiment.bean.ExperimentListBean;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class ExperimentDetailPresenterImpl implements ExperimentDetailPresenter {

    ExperimentDetailFragment mFragment;

    public void setView(ExperimentDetailFragment fragment) {
        this.mFragment = fragment;
    }

    public void getDataList(Map<String, Object> paramsMap) {
        CommonModel<String> progressModel = new CommonModelImpl<>();
        progressModel.setContext(mFragment.getActivity())
                .setUrl(HomeMethodConfig.EXPERIMENT_DATA)
                .setParamMap(paramsMap)
//                .setRequestMethod(LibConfig.HTTP_POST)
                .setStringListener(new GetListStringListener() {
                    @Override
                    public void success(String list) {
                        Log.d("图片上传", "加载:" + list);
                        mFragment.setData(list);
                    }

                })
                .getString();
    }

    public void submit(String object) {
        CommonModel<String> progressModel = new CommonModelImpl<>();
        progressModel
                .setContext(mFragment.getActivity())
                .setUrl(HomeMethodConfig.EXPERIMENT_DATA)
//                .setParamMap(paramsMap)
//                .setRequestMethod(LibConfig.HTTP_POST)
                .setStringListener(new GetListStringListener() {
                    @Override
                    public void success(String list) {
//                        Log.e("list",list.toString());
                        mFragment.submitSuccess();
                    }

                })
                .submit(object);
    }
}
