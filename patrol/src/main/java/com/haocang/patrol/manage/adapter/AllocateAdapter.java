
package com.haocang.patrol.manage.adapter;

import android.os.Handler;
import android.os.Looper;
import androidx.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;

import com.haocang.base.adapter.BaseRecyclerAdapter;
import com.haocang.base.adapter.SmartViewHolder;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.bean.UserDTO;

import java.util.Collection;

/**
 * Created by william on 2018/4/3.
 */

public class AllocateAdapter extends BaseRecyclerAdapter<UserDTO> {

    /**
     * @param collection 数据列表.
     * @param layoutId   用到的资源ID
     */
    public AllocateAdapter(final Collection<UserDTO> collection,
                           final int layoutId) {
        super(collection, layoutId);
    }

    /**
     * @param layoutId 资源ID.
     */
    public AllocateAdapter(@LayoutRes final int layoutId) {
        super(layoutId);
    }

    /**
     * 绑定View.
     *
     * @param holder
     * @param user
     * @param position
     */
    @Override
    protected void onBindViewHolder(final SmartViewHolder holder,
                                    final UserDTO user, final int position) {
        if (user.isSelect()) {
            holder.image(R.id.patrol_allocate_iv, R.drawable.patrol_select);
        } else {
            holder.image(R.id.patrol_allocate_iv, R.drawable.patrol_unselect);
        }
        holder.text(R.id.patrol_allocator_tv, user.getLabel());
        holder.text(R.id.patrol_allocate_group_tv, user.getOrgName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                user.setSelect(!user.isSelect());
                clearOther(position);
                refresh();
            }
        });
    }

    /**
     * 刷新列表，因为recycleview不能直接用notifydatasetchanged，需要下面这样写才生效.
     */
    private void refresh() {
        new Handler(Looper.getMainLooper()).

                post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
    }

    /**
     * 当前选择之外的行全部设置为未选中.
     *
     * @param position 选中行
     */
    private void clearOther(final int position) {
        for (int i = 0; i < mList.size(); i++) {
            if (i != position) {
                mList.get(i).setSelect(false);
            }
        }
    }

    /**
     * @param view 需要改变的View
     * @param user 实体类
     */
    private void setSelectIv(final View view, final UserDTO user) {
        if (user.isSelect()) {
            ((ImageView) view).setImageResource(R.drawable.patrol_select);
        } else {
            ((ImageView) view).setImageResource(R.drawable.patrol_unselect);
        }

    }

    /**
     * @return 返回选中的ID.
     */
    public Integer getSelectId() {
        Integer selectId = null;
        if (mList != null) {
            for (UserDTO user : mList) {
                if (user.isSelect()) {
                    selectId = user.getValue();
                }
            }
        }
        return selectId;
    }
}
