package com.haocang.repair.progress.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.repair.R;
import com.haocang.repair.progress.bean.ProcessingProgressVo;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题： 处理进度
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/2710:28
 * 修 改 者：
 * 修改时间：
 */
public class RepairProgressAdapter extends BaseAdapter<ProcessingProgressVo.ProcessingProgress> {
    /**
     * @param layoutId layout的ID
     */
    public RepairProgressAdapter(final int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(final BaseHolder holder,
                           final ProcessingProgressVo.ProcessingProgress item,
                           final int position) {
        View view = holder.itemView;
        holder.setText(R.id.date_tv, item.getShowTime());
        String detailInfo = item.getProcessingPersonName() + "  " + item.getProcessingResult();
        if (item.getNextProcessingPersonName() != null) {
            detailInfo += "  " + item.getNextProcessingPersonName();
        }
        holder.setText(R.id.name_tv, detailInfo);
        ImageView bgIv = view.findViewById(R.id.fault_iv);
        if (position == mList.size() - 1) {
            holder.itemView.findViewById(R.id.fault_bg_v).setVisibility(View.GONE);
        } else {
            holder.itemView.findViewById(R.id.fault_bg_v).setVisibility(View.VISIBLE);
        }
        if (item.hasDetail()) {
            view.findViewById(R.id.repair_progress_right_iv).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.repair_progress_right_iv).setVisibility(View.GONE);
        }
        if (position == 0) {
            bgIv.setBackgroundResource(R.drawable.repair_progress_blue_ic);
            holder.setTextColor(R.id.date_tv, Color.parseColor("#323232"));
            holder.setTextColor(R.id.name_tv, Color.parseColor("#323232"));
        } else {
            bgIv.setBackgroundResource(R.drawable.repair_progress_ic);
            holder.setTextColor(R.id.date_tv, Color.parseColor("#c8c7c7"));
            holder.setTextColor(R.id.name_tv, Color.parseColor("#c8c7c7"));
        }
    }
}
