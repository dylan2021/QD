package com.haocang.base.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.haocang.base.R;
import com.haocang.base.bean.LeftNavigationEntity;

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
 * 创建时间：2018/3/2017:30
 * 修 改 者：
 * 修改时间：
 */
public class LeftNavigationAdapter extends BaseAdapter<LeftNavigationEntity> {
    public LeftNavigationAdapter() {
        super(R.layout.adapter_left_navigation);
    }


    @Override
    protected void convert(BaseHolder holder, LeftNavigationEntity item) {
        TextView nameTv = (TextView)holder.itemView.findViewById(R.id.name_tv);
        View bgv = holder.itemView.findViewById(R.id.line_v);
        nameTv.setText(item.getName());
//        holder.setText(R.id.name_tv, item.getName()).setTextColor(Color.parseColor());
        if (item.isClicks()) {
            nameTv.setTextColor(Color.parseColor("#0cabdf"));
            bgv.setBackgroundResource(R.color.color_title_blue_bg);
        } else {
            nameTv.setTextColor(Color.parseColor("#989898"));
            bgv.setBackgroundResource(R.color.color_underline_bg);
        }


    }

    public void setUnchecked(int position) {
        for (int i = 0; i < mList.size(); i++) {
            if (i == position) {
                mList.get(i).setClicks(true);
            } else {
                mList.get(i).setClicks(false);
            }
        }
        notifyDataSetChanged();
    }
}
