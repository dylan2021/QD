package com.haocang.patrol.manage.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.alertview.AlertView;
import com.haocang.base.adapter.BaseRecyclerAdapter;
import com.haocang.base.adapter.SmartViewHolder;
import com.haocang.base.config.AppApplication;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.ConcernUtil;
import com.haocang.base.utils.TimeUtil;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.manage.bean.PatrolConstans;
import com.haocang.patrol.manage.bean.PatrolTaskListDTO;
import com.haocang.patrol.manage.ui.AllocateFragment;
import com.haocang.patrol.patrolinhouse.ui.PatrolInHouseFragment;
import com.haocang.patrol.patroloutside.ui.PatrolOutsideFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by william on 2018/4/3.
 */

public class PatrolAdapter extends BaseRecyclerAdapter<PatrolTaskListDTO> {
    private Context mContext;

    public PatrolAdapter(@LayoutRes final int layoutId,
                         final Context ctx) {
        super(layoutId);
        mContext = ctx;
    }

    @Override
    protected void onBindViewHolder(final SmartViewHolder holder, final PatrolTaskListDTO info,
                                    final int position) {
        if (info==null) {
            return;
        }
        holder.text(R.id.name_tv, info.getName());

        holder.text(R.id.patrol_dt_tv, info.getExecutorId()+"");
        holder.text(R.id.patrol_date_tv, info.getExecutorName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommonActivity.class);
                intent.putExtra("taskName", info.getName());
                mContext.startActivity(intent);
            }
        });
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }


}
