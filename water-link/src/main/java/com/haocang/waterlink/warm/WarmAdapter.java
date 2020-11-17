package com.haocang.waterlink.warm;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.waterlink.R;
import com.haocang.waterlink.experiment.bean.ExperimentListBean;
import com.haocang.waterlink.utils.TextUtilsMy;

/**
 * Dylan
 */
public class WarmAdapter extends BaseAdapter<WarmBean.ItemsBean> {

    public WarmAdapter(final int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(final BaseHolder holder, final WarmBean.ItemsBean item) {
        if (item == null) {
            return;
        }
        holder.setText(R.id.bz_fmj_item_tv_title, item.getPumpName());
        holder.setText(R.id.bz_fmj_item_tv_1, "设备编号", item.getPumpName());
        holder.setText(R.id.bz_fmj_item_tv_2, "设备类型", item.getPumpName());

        //状态
        //holder.setText(R.id.bz_fmj_item_tv_status, TextUtilsMy.getStatusStr(item.isStatus()));
        //holder.setTextColor(R.id.bz_fmj_item_tv_status, TextUtilsMy.getStatusColor(item.isStatus()));
    }
    public void clear() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }
}