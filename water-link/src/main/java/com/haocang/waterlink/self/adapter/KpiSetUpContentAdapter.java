package com.haocang.waterlink.self.adapter;

import android.view.View;
import android.widget.ImageView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.EquimentKpiEntity;

import java.util.ArrayList;
import java.util.List;

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
 * 创建时间：2018/3/2116:00
 * 修 改 者：
 * 修改时间：
 */
public class KpiSetUpContentAdapter extends BaseAdapter<EquimentKpiEntity> {

    public KpiSetUpContentAdapter() {
        super(R.layout.adapter_kpi_setup_equiment);
    }


    @Override
    protected void convert(final BaseHolder holder, final EquimentKpiEntity item) {
        holder.setText(R.id.name_tv, item.getTitle());
        holder.setText(R.id.content_tv, item.getDescription());
        ImageView selectIv = holder.itemView.findViewById(R.id.select_iv);
        if (item.isChecked()) {
            selectIv.setVisibility(View.VISIBLE);
        } else {
            selectIv.setVisibility(View.GONE);
        }
    }

    public List<EquimentKpiEntity> getEquimentList() {
        List<EquimentKpiEntity> list = new ArrayList<>();
        return mList != null ? mList : list;
    }
}
