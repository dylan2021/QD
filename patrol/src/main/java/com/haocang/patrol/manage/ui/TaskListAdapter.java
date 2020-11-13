package com.haocang.patrol.manage.ui;

import android.widget.TextView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.bean.PatrolTaskListDTO;

import javax.xml.transform.Templates;

/**
 * 泵站列表
 */
public class TaskListAdapter extends BaseAdapter<PatrolTaskListDTO> {
    private int taskType;

    public TaskListAdapter(final int layoutId, int taskType) {
        super(layoutId);
        this.taskType = taskType;
    }

    @Override
    protected void convert(final BaseHolder holder, final PatrolTaskListDTO item) {
        if (item == null) {
            return;
        }
        String itemTotalStr = "";
        String name = item.getName();
        int position = holder.getLayoutPosition() + 1;
        if (0 == taskType) {
            itemTotalStr = "任务编号：" + item.getExecutorId()
                    + "\n状　　态：未完成"
                    + "\n执  行  人：" + item.getExecutorName()
                    + "\n开始时间：2020-11-22 08:01:30"
                    + "\n结束时间：2020-11-22 17:32:30"
                    + "\n巡  检  点：" + position * 2 + "/12"
                    + "\n发现缺陷：" + (position + 1);
        } else if (1 == taskType) {
            itemTotalStr = "巡检点编号：" + item.getExecutorId()
                    + "\n巡检点描述：" + name
                    + "\n巡检步骤：" + position * 2 + "/8"
                    + "\n发现缺陷：" + (position * 2 + 3)
                    + "\n区域位置：" + item.getExecutorName();
        } else {
            ((TextView) holder.getView(R.id.item_tv_title)).setCompoundDrawables(null, null, null, null);
            itemTotalStr = "步骤编号：" + item.getExecutorId()
                    + "\n步骤名称：" + name
                    + "\n结果记录：" + item.getExecutorName()
                    + "\n备注信息：" + name + item.getExecutorName()
                    + "\n照　　片：" + "暂无";
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