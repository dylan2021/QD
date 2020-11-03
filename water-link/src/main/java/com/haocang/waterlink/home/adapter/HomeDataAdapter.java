package com.haocang.waterlink.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.base.utils.StringUtils;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.HomeDataEntity;

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
 * 创建时间：2018/8/1下午6:42
 * 修  改  者：
 * 修改时间：
 */
public class HomeDataAdapter extends BaseAdapter<HomeDataEntity> {

    private final int NAME_LIMIT = 8;
    private Context mContext;

    public HomeDataAdapter(Context ctx) {
        super(R.layout.home_data_item);
        mContext = ctx;
    }

    @Override
    protected void convert(final BaseHolder holder,
                           final HomeDataEntity item, final int position) {
        holder.setText(R.id.name_tv, item.getMpointName());
        TextView valueTv = holder.itemView.findViewById(R.id.value_tv);
//        TextView noDataTv = holder.itemView.findViewById(R.id.novalue_tv);
        TextView unitTv = holder.itemView.findViewById(R.id.data_unti_tv);
//        if (position == 0) {
//            item.setRealValue("11234556.00");
//        }
        TextView locationTv = holder.itemView.findViewById(R.id.home_location_tv);
        if (!TextUtils.isEmpty(item.getValue())) {
            try {
                if (!TextUtils.isEmpty(item.getNumtail()) && !"0".equals(item.getNumtail())) {
                    String value = StringUtils.format2Decimal(item.getValue(), item.getNumtail());//todo 此处应该
                    valueTv.setText(value);
                } else {
                    valueTv.setText(item.getValue());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            valueTv.setVisibility(View.VISIBLE);
        } else {
            valueTv.setText("暂无数据");
            valueTv.setVisibility(View.GONE);
        }
        String siteName = item.getSiteName();
        if (siteName != null && siteName.length() > NAME_LIMIT) {
            siteName = siteName.substring(0, NAME_LIMIT);
        }
        locationTv.setText(siteName);
        unitTv.setText(item.getUnit());
    }

    public String getIds() {
        String ids = "";
        for (HomeDataEntity entity : mList) {
            ids += entity.getId() + ",";
        }
        return ids;
    }

    public void notifyData(HomeDataEntity entity) {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getId() == entity.getId()) {
                mList.remove(mList.get(i));
                mList.add(entity);
            }
        }
        notifyDataSetChanged();
    }
}
