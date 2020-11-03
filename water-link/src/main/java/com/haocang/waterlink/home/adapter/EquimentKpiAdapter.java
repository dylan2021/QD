package com.haocang.waterlink.home.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.EquimentKpiEntity;

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
 * 创建时间：2018/3/1914:40
 * 修 改 者：
 * 修改时间：
 */
public class EquimentKpiAdapter extends BaseAdapter<EquimentKpiEntity> {


    public EquimentKpiAdapter() {
        super(R.layout.adapter_equiment_kpi);
    }

    @Override
    protected void convert(final BaseHolder holder,
                           final EquimentKpiEntity item, final int position) {

        holder.setText(R.id.name_tv, item.getTitle());
        TextView valueTv = holder.itemView.findViewById(R.id.value_tv);
        TextView noDataTv = holder.itemView.findViewById(R.id.novalue_tv);
        if (!TextUtils.isEmpty(item.getKpiValue())) {
            valueTv.setText(item.getKpiValue());
            holder.setText(R.id.unti_tv, "%");
            noDataTv.setVisibility(View.GONE);
            valueTv.setVisibility(View.VISIBLE);
        } else {
            noDataTv.setText("无记录");
            holder.setText(R.id.unti_tv, "");
            noDataTv.setVisibility(View.VISIBLE);
            valueTv.setVisibility(View.GONE);
        }
    }
}
