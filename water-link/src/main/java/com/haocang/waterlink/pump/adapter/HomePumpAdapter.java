package com.haocang.waterlink.pump.adapter;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.base.bean.EquimentEntity;
import com.haocang.waterlink.R;

/**
 * 泵站列表
 */
public class HomePumpAdapter extends BaseAdapter<EquimentEntity> {

    public HomePumpAdapter(final int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(final BaseHolder holder, final EquimentEntity item) {
        holder.setText(R.id.patrol_allocator_tv, item.getName());
        holder.setText(R.id.patrol_allocate_group_tv, item.getProcessName());
    }
    public void clear() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }
}