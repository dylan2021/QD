package com.haocang.fault.list.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.base.utils.TimeUtil;
import com.haocang.fault.R;
import com.haocang.fault.list.bean.FaultProcessingProgressEntity;

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
public class FaultProcessingAdapter extends BaseAdapter<FaultProcessingProgressEntity> {
    public FaultProcessingAdapter(int layoutId) {
        super(layoutId);
    }


    @Override
    protected void convert(BaseHolder holder, FaultProcessingProgressEntity item, int position) {
        View view = holder.itemView;
        if (!TextUtils.isEmpty(item.getTime())) {
            String time = TimeTransformUtil.getShowLocalTime(item.getTime());
            holder.setText(R.id.date_tv, TimeUtil.converStr_MMDD(TimeUtil.getTranData(time)));
        } else {
            holder.setText(R.id.date_tv, "");
        }
        String repaired = item.getRepaired();
        holder.setText(R.id.name_tv, item.getProcessingPersonName() + "  " + item.getProcessingResult() + "  " + item.getNextProcessingPersonName() + " " + repaired);
        ImageView bgIv = view.findViewById(R.id.fault_iv);
        ImageView rightIv = view.findViewById(R.id.fault_processing_iv);
        if (mList.size() - 1 == position) {
            holder.itemView.findViewById(R.id.fault_bg_v).setVisibility(View.GONE);
            holder.setText(R.id.name_tv, item.getProcessingPersonName() + "  " + item.getProcessingResult());//最后一条是任务分派
        } else {
            holder.itemView.findViewById(R.id.fault_bg_v).setVisibility(View.VISIBLE);
        }
        if (position == 0) {
            bgIv.setBackgroundResource(R.drawable.ic_progress_blue);
            holder.setTextColor(R.id.date_tv, Color.parseColor("#323232"));
            holder.setTextColor(R.id.name_tv, Color.parseColor("#323232"));
        } else {
            bgIv.setBackgroundResource(R.drawable.ic_progress);
            holder.setTextColor(R.id.date_tv, Color.parseColor("#c8c7c7"));
            holder.setTextColor(R.id.name_tv, Color.parseColor("#c8c7c7"));
        }
        //处理中和已完成才可以看详情
        if (item.getProcessResult() == 4 || item.getProcessResult() == 1 || item.getProcessResult() == 2 || item.getProcessResult() == 3) {
            rightIv.setVisibility(View.VISIBLE);
        } else {
            rightIv.setVisibility(View.GONE);
        }
    }
}
