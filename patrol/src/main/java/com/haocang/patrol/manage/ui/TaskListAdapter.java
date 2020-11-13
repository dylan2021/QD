package com.haocang.patrol.manage.ui;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.bean.PatrolTaskListDTO;

/**
 * 泵站列表
 */
public class TaskListAdapter extends BaseAdapter<PatrolTaskListDTO> {
    private boolean isTaskList;

    public TaskListAdapter(final int layoutId, boolean isTaskList) {
        super(layoutId);
        this.isTaskList = isTaskList;
    }

    @Override
    protected void convert(final BaseHolder holder, final PatrolTaskListDTO item) {
        if (item == null) {
            return;
        }
        String itemTotalStr = "";
        String name = item.getName();
        int position = holder.getLayoutPosition() + 1;
        if (isTaskList) {
            itemTotalStr = "任务编号：" + item.getExecutorId()
                    + "\n状　　态：未完成"
                    + "\n执  行  人：" + item.getExecutorName()
                    + "\n开始时间：2020-11-22 08:01:30"
                    + "\n结束时间：2020-11-22 17:32:30"
                    + "\n巡  检  点：" + position * 2 + "/12"
                    + "\n发现缺陷：" + (position + 1);
        } else {
            itemTotalStr = "巡检点编号：" + item.getExecutorId()
                    + "\n巡检点描述：" + name
                    + "\n巡检步骤：" + position * 2 + "/8"
                    + "\n发现缺陷：" + (position*2 + 3)
                    + "\n区域位置：" + item.getExecutorName();
        }
        holder.setText(R.id.item_tv_title, name);
        holder.setText(R.id.bz_fmj_item_tv_1, itemTotalStr);

    }

    public void clear() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }
}