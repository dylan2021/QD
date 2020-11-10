package com.haocang.waterlink.pump;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.waterlink.R;
import com.haocang.waterlink.utils.TextUtilsMy;
import com.haocang.waterlink.widgets.BorderLabelTextView;
import com.luozm.captcha.Utils;

/**
 * 泵站,阀门井 设备列表
 */
public class BZ_FMJ_WarmListAdapter extends BaseAdapter<BZ_FMJ_Bean.ItemsBean> {
    private boolean isTypeBZ;

    public BZ_FMJ_WarmListAdapter(final int layoutId, boolean isBZ) {
        super(layoutId);
        isTypeBZ = isBZ;
    }

    @Override
    protected void convert(final BaseHolder holder, final BZ_FMJ_Bean.ItemsBean item) {
        if (item == null) {
            return;
        }
        holder.setText(R.id.bz_fmj_item_tv_title, item.alarmName);
        //解除方法；Auto：自动 AutoOrManual:自动或人工   Manual  人工

        String itemTotalStr =
                 "解除方法：" + TextUtilsMy.getWarmMethod(item.disarmMethod)
                + "\n发生时间：" + TextUtilsMy.getTime(item.alarmTriggerTime)
                + "\n持续时间：" + item.duration;
        holder.setText(R.id.bz_fmj_item_tv_1, itemTotalStr);

        BorderLabelTextView statusTv = (BorderLabelTextView) holder.getView(R.id.status_tv);

        String alarmStatus = item.alarmStatus;
        statusTv.setText(TextUtilsMy.getWarmStatus(alarmStatus));
        int color = TextUtilsMy.getWarmStatusColor(alarmStatus);
        statusTv.setTextColor(color);
        statusTv.setStrokeColor(color);
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }
}