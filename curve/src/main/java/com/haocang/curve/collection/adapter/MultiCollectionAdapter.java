package com.haocang.curve.collection.adapter;

import android.content.Context;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.curve.R;
import com.haocang.curve.collection.bean.AppChartDTO;
import com.haocang.curve.collection.iview.MultiCollectionView;

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
 * 创建时间：2018/6/4下午2:59
 * 修  改  者：
 * 修改时间：
 */
public class MultiCollectionAdapter extends BaseAdapter<AppChartDTO> {

    /**
     * 和主界面交互接口.
     */
    private MultiCollectionView multiCollectionView;

    /**
     * @param view     和主界面交互接口.
     * @param layoutId 布局ID
     */
    public MultiCollectionAdapter(final MultiCollectionView view,
                                  final int layoutId) {
        super(layoutId);
        multiCollectionView = view;
    }

    /**
     * @param holder   列表视图holder
     * @param item     绑定视图实体类
     * @param position 位置
     */
    @Override
    protected void convert(final BaseHolder holder, final AppChartDTO item, final int position) {
        holder.setText(R.id.name_tv, item.getCombineName());
//        holder.setText(R.id.repair_state_tv, item.getStateName());
        holder.setText(R.id.content_tv, item.getMpointNames());
    }

    /**
     * 获取上下文参数.
     *
     * @return
     */
    public Context getContext() {
        return multiCollectionView.getContext();
    }
}
