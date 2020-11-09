package com.haocang.waterlink.pump;

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
    private boolean isTypeBZ;

    public BZ_FMJ_Adapter(final int layoutId, boolean isBZ) {
        super(layoutId);
        isTypeBZ = isBZ;
    }

    @Override
    protected void convert(final BaseHolder holder, final BZ_FMJ_ListBean.ItemsBean item) {
        if (item == null) {
            return;
        }
        holder.setText(R.id.bz_fmj_item_tv_1, "区域", item.getProcessName());
        holder.setText(R.id.bz_fmj_item_tv_status, TextUtilsMy.getStatusStr(item.isStatus()));
        holder.setTextColor(R.id.bz_fmj_item_tv_status, TextUtilsMy.getStatusColor(item.isStatus()));
        if (isTypeBZ) {//泵站
            holder.setText(R.id.bz_fmj_item_tv_title, item.getPumpName());
            holder.setText(R.id.bz_fmj_item_tv_2, "监理单位", item.getSupervisorUnit());
            holder.setText(R.id.bz_fmj_item_tv_3, "施工单位(土建)", item.getContractor());
            holder.setText(R.id.bz_fmj_item_tv_4, "施工单位(外电)", item.getConstructors());
        } else {//阀门井
            holder.setText(R.id.bz_fmj_item_tv_title, item.getWellName());
            holder.setText(R.id.bz_fmj_item_tv_2, "阀门井类别",
                    TextUtilsMy.getWellType(item.getWellType()));

            holder.setText(R.id.bz_fmj_item_tv_3, "地面高 (m)", item.getGroundHeight());

            String designTube = item.getDesignTube();
            String topHeight = item.getTopHeight();
            String bottemHeight = item.getBottemHeight();
            String buriedDepth = item.getBuriedDepth();
            String itemTotalStr="设计管 (m)："+designTube
                    +"\n井顶高 (m)："+topHeight
                    +"\n井底高 (m)："+bottemHeight
                    +"\n埋　深 (m)："+buriedDepth;
            holder.setText(R.id.bz_fmj_item_tv_4, itemTotalStr);
        }
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }
}