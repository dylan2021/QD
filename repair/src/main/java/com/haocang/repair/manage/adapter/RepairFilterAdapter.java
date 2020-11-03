package com.haocang.repair.manage.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.base.bean.ScreeEntity;
import com.haocang.repair.R;

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
 * 创建时间：2018/5/310:39
 * 修 改 者：
 * 修改时间：
 */
public class RepairFilterAdapter extends BaseAdapter<ScreeEntity> {

    public RepairFilterAdapter(int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(final BaseHolder holder, ScreeEntity item) {
        LinearLayout bgLl = holder.itemView.findViewById(R.id.bg_ll);
        TextView nameTv = holder.itemView.findViewById(R.id.name_tv);
        View bgV = holder.itemView.findViewById(R.id.scree_v);
        nameTv.setText(item.getName());
        if (item.isSelector()) {
            bgLl.setBackgroundColor(Color.parseColor("#EDF9FD"));
            nameTv.setTextColor(Color.parseColor("#0cabdf"));
            bgV.setVisibility(View.VISIBLE);
        } else {
            bgLl.setBackgroundColor(Color.parseColor("#f0f0f0"));
            nameTv.setTextColor(Color.parseColor("#282828"));
            bgV.setVisibility(View.GONE);
        }
    }

    public String getIds() {
        String ids = "";
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isSelector()) {
                ids += mList.get(i).getId() + ",";
            }
        }
        if (!TextUtils.isEmpty(ids)) {
            ids = ids.substring(0, ids.length() - 1);
        }
        return ids;
    }

    public void resetData() {
        for (ScreeEntity entity : mList) {
            entity.setSelector(false);
        }
        notifyDataSetChanged();
    }
}