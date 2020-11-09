package com.haocang.waterlink.pump.adapter;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.base.bean.EquimentEntity;
import com.haocang.waterlink.R;
import com.haocang.waterlink.pump.BZ_FMJ_ListBean;
import com.haocang.waterlink.utils.TextUtilsMy;

/**
 * 泵站列表
 */
public class BZ_FMJ_Adapter extends BaseAdapter<BZ_FMJ_ListBean.ItemsBean> {

    public BZ_FMJ_Adapter(final int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(final BaseHolder holder, final BZ_FMJ_ListBean.ItemsBean item) {
        holder.setText(R.id.bz_fmj_item_tv_title, item.getPumpName());
        holder.setText(R.id.bz_fmj_item_tv_1, "区域", item.getProcessName());
        holder.setText(R.id.bz_fmj_item_tv_2, "监理单位", item.getSupervisorUnit());
        holder.setText(R.id.bz_fmj_item_tv_3, "施工单位(土建)", item.getContractor());
        holder.setText(R.id.bz_fmj_item_tv_4, "施工单位(外电)", item.getConstructors());
        holder.setText(R.id.bz_fmj_item_tv_status, TextUtilsMy.getStatusStr(item.isStatus()));
        holder.setTextColor(R.id.bz_fmj_item_tv_status, TextUtilsMy.getStatusColor(item.isStatus()));
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }
}