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
    private Map<String, String> statusMap;

    public PatrolAdapter(@LayoutRes final int layoutId,
                         final Context ctx) {
        super(layoutId);
        mContext = ctx;
        String[] patrolStatusLabelArray = AppApplication.getContext().getResources().getStringArray(R.array.patrol_status_labels_all);
        String[] patrolStatusKeysArray = AppApplication.getContext().getResources().getStringArray(R.array.patrol_status_keys_all);
        statusMap = new HashMap<>();
        for (int i = 0; i < patrolStatusLabelArray.length && i < patrolStatusKeysArray.length; i++) {
            statusMap.put(patrolStatusKeysArray[i], patrolStatusLabelArray[i]);
        }
    }

    @Override
    protected void onBindViewHolder(final SmartViewHolder holder, final PatrolTaskListDTO food,
                                    final int position) {

        TextView stateTv = holder.itemView.findViewById(R.id.patrol_state_tv);
        stateTv.setText("我的爱的的发");
        holder.text(R.id.name_tv, "姓名");
        holder.text(R.id.patrol_dt_tv, "shijian");
        holder.text(R.id.patrol_date_tv, "时间2");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommonActivity.class);
                intent.putExtra("taskId", mList.get(position).getId());
                intent.putExtra("taskName", food.getName());
                intent.putExtra("state", mList.get(position).getExecuteStatus());
                mContext.startActivity(intent);
            }
        });
    }

    public void clear() {
        mList.clear();
    }


}
