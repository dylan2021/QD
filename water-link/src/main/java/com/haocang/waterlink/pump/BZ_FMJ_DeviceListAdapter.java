package com.haocang.waterlink.pump;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.waterlink.R;
import com.haocang.waterlink.utils.TextUtilsMy;

/**
 * 泵站,阀门井 设备列表
 */
public class BZ_FMJ_DeviceListAdapter extends BaseAdapter<BZ_FMJ_Bean.ItemsBean> {
    private boolean isTypeBZ;

    public BZ_FMJ_DeviceListAdapter(final int layoutId, boolean isBZ) {
        super(layoutId);
        isTypeBZ = isBZ;
    }

    @Override
    protected void convert(final BaseHolder holder, final BZ_FMJ_Bean.ItemsBean item) {
        if (item == null) {
            return;
        }
        holder.setText(R.id.bz_fmj_item_tv_title, item.name);//设备名称
        holder.setText(R.id.bz_fmj_item_tv_1, "设备编号", item.code);
        holder.setText(R.id.bz_fmj_item_tv_2, "设备类型", item.typeName);

        //状态
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