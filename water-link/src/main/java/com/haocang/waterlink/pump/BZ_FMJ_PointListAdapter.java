package com.haocang.waterlink.pump;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.base.utils.TimeUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.utils.TextUtilsMy;

/**
 * 泵站列表
 */
public class BZ_FMJ_PointListAdapter extends BaseAdapter<BZ_FMJ_ListBean.EquMpointsBean> {
    private boolean isTypeBZ;

    public BZ_FMJ_PointListAdapter(final int layoutId, boolean isBZ) {
        super(layoutId);
        isTypeBZ = isBZ;
    }

    @Override
    protected void convert(final BaseHolder holder, final BZ_FMJ_ListBean.EquMpointsBean item) {
        if (item == null) {
            return;
        }
        holder.setText(R.id.bz_fmj_item_tv_title, item.mpointName);

        String itemTotalStr = "测点编号：" + item.mpointId
                + "\n区域位置：" + item.siteName
                + "\n当  前  值：" + TextUtilsMy.makeUp2(item.value, item.unit)
                + "\n业务时间：" + TextUtilsMy.getTime(item.datadt)
                + "\n信号类型：" + TextUtilsMy.getDatType(item.datype)
                + "\n数据来源：" + TextUtilsMy.getDataSource(item.datasource)
                + "\n数据分类：" + item.categoryName;
        holder.setText(R.id.bz_fmj_item_tv_1, itemTotalStr);
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }
}