package com.haocang.patrol.manage.ui;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.bean.PatrolTaskListDTO;

import java.util.List;

/**
 * 泵站列表
 */
public class TaskListAdapter extends BaseAdapter<PatrolTaskListDTO> {
    private boolean isTypeBZ;

    public TaskListAdapter(final int layoutId, boolean isBZ) {
        super(layoutId);
        isTypeBZ = isBZ;
    }

    @Override
    protected void convert(final BaseHolder holder, final PatrolTaskListDTO item) {
        if (item == null) {
            return;
        }
        holder.setText(R.id.bz_fmj_item_tv_title, item.getName());

        String itemTotalStr = "任务编号：" + item.getExecutorId()
                + "\n状　　态：已完成"
                + "\n执  行  人：" + item.getExecutorName()
                + "\n开始时间：2020-11-22 08:01:30"
                + "\n结束时间：2020-11-22 17:32:30"
                + "\n巡  检  点：6/12"
                + "\n发现缺陷：3";
        holder.setText(R.id.bz_fmj_item_tv_1, itemTotalStr);
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }
}