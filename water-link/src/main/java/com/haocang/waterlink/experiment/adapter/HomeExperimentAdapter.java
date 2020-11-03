package com.haocang.waterlink.experiment.adapter;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.base.bean.EquimentEntity;
import com.haocang.waterlink.R;
import com.haocang.waterlink.experiment.bean.ExperimentListBean;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区龙兰路277号一号楼9楼
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/5/22 10:00
 * 修 改 者：
 * 修改时间：
 */
public class HomeExperimentAdapter extends BaseAdapter<ExperimentListBean.ItemsBean> {

    public HomeExperimentAdapter(final int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(final BaseHolder holder, final ExperimentListBean.ItemsBean item) {
        holder.setText(R.id.name,item.getFormName())
                .setText(R.id.area,item.getSiteName())
                .setText(R.id.professional,"业务时间:"+item.getFormLatestdate().replace("T"," ").replace("Z",""))
                .setText(R.id.cycle,item.getCycleName())
                .setText(R.id.cycle_time,"录入时间:"+item.getUpdateTime().replace("T"," ").replace("Z",""));
//        holder.setText(R.id.patrol_allocator_tv, item.getName());
//        holder.setText(R.id.patrol_allocate_group_tv, item.getProcessName());
    }


    public void clear() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }
}