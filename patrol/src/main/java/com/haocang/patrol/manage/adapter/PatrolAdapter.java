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

    private int changedItemPosition = -1;

    public PatrolAdapter(final Collection<PatrolTaskListDTO> collection,
                         final int layoutId) {
        super(collection, layoutId);
    }

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
    protected void onBindViewHolder(final SmartViewHolder holder,
                                    final PatrolTaskListDTO food,
                                    final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                changedItemPosition = position;
                Intent intent = new Intent(mContext, CommonActivity.class);
                intent.putExtra("taskId", mList.get(position).getId());
                intent.putExtra("taskName", food.getName());
                intent.putExtra("state", mList.get(position).getExecuteStatus());
                if (food.isOutsidePatrol()) {
                    intent.putExtra("fragmentName", PatrolOutsideFragment.class.getName());
                    intent.putExtra("patroEndTime", TimeUtil.getDateStr4(food.getEndTime()));
                    intent.putExtra("mapFlag", true);
                    mContext.startActivity(intent);
                } else {
                    intent.putExtra("fragmentName", PatrolInHouseFragment.class.getName());
                    mContext.startActivity(intent);
                }

            }
        });
        if (food.isOutsidePatrol()) {
            holder.image(R.id.patrol_icon_iv, R.mipmap.patrol_map_type);
        } else {
            holder.image(R.id.patrol_icon_iv, R.mipmap.patrol_inhouse_type);
        }
        TextView stateTv = holder.itemView.findViewById(R.id.patrol_state_tv);
        stateTv.setTextColor(Color.parseColor(getStateColor(food.getExecuteStatus())));
        stateTv.setText(getStateResource(food.getExecuteStatus()));
        holder.text(R.id.name_tv, food.getName());
        holder.text(R.id.patrol_point_tv, food.getInspectedCount() + "/" + food.getPatrolPointCount());
        holder.text(R.id.patrol_dt_tv, TimeUtil.getDateStr4(food.getStartTime()));
        holder.text(R.id.patrol_date_tv, TimeUtil.getDateStr4(food.getEndTime()));
        holder.text(R.id.person_tv, food.getExecutorName());

        ImageButton patrolTv = (ImageButton) holder.findViewById(R.id.follow_ibtn);
        if (food.isConcerned()) {
            patrolTv.setBackgroundResource(R.drawable.icon_patrol_star_blue);
        } else {
            patrolTv.setBackgroundResource(R.drawable.icon_patrol_star_grey);
        }
        if (OffLineOutApiUtil.isOffLine) {
            patrolTv.setVisibility(View.INVISIBLE);
        } else {
            patrolTv.setVisibility(View.VISIBLE);
        }
        holder.findViewById(R.id.follow_ibtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (food.isConcerned()) {
                    promptDialog(food);

                } else {
                    new ConcernUtil()
                            .setConcernId(food.getId())
                            .setContexts(mContext)
                            .setConcernType(ConcernUtil.PATROL_TYPE)
                            .addConcern();
                    food.setConcerned(true);
                }
                notifyDataSetChanged();

            }
        });
        //外层判断是否有分配权限， 内层判断是否离线模式
        if (food.canAllocate()) {
            if (OffLineOutApiUtil.isOffLine) {
                holder.findViewById(R.id.patrol_assignment_ll).setVisibility(View.INVISIBLE);
            } else {
                holder.findViewById(R.id.patrol_assignment_ll).setVisibility(View.VISIBLE);
            }
        } else {
            holder.findViewById(R.id.patrol_assignment_ll).setVisibility(View.INVISIBLE);
        }
        holder.findViewById(R.id.patrol_assignment_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(mList.get(position));
                Intent intent = new Intent(mContext, CommonActivity.class);
                intent.putExtra("taskId", mList.get(position).getId());
                intent.putExtra("taskName", mList.get(position).getName());
                intent.putExtra("orgId", mList.get(position).getOrgId());
                changedItemPosition = position;
//                intent.putExtra("orgId")
//                Fragment fragment = (Fragment) ARouter.getInstance().build("/patrol/allocate").navigation();
                intent.putExtra("fragmentName", AllocateFragment.class.getName());
                mContext.startActivity(intent);
            }
        });
    }

    public void clear() {
        mList.clear();
    }

    private String getStateResource(final String state) {
        String resource = "";
        if (PatrolConstans.STATE_UNASSIGNED.equals(state)) {
            resource = "未分配";
        } else if (PatrolConstans.STATE_TOBEEXCUTED.equals(state)) {
            resource = "待执行";
        } else if (PatrolConstans.STATE_EXECUTING.equals(state)) {
            resource = "执行中";
        } else if (PatrolConstans.STATE_FINISHG.equals(state)) {
            resource = "已完成";
        } else if (PatrolConstans.STATE_ABNARMAL.equals(state)) {
            resource = "异常";
        } else if (PatrolConstans.STATE_INTERRUPT.equals(state)) {
            resource = "终止";
        }
        return resource;
    }

    private String getStateColor(final String state) {
        String resource = "#ff80bf22";
        if (PatrolConstans.STATE_UNASSIGNED.equals(state)) {
            resource = "#F5A623";
        } else if (PatrolConstans.STATE_TOBEEXCUTED.equals(state)) {
            resource = "#80BF22";
        } else if (PatrolConstans.STATE_EXECUTING.equals(state)) {
            resource = "#80BF22";
        } else if (PatrolConstans.STATE_INTERRUPT.equals(state)) {
            resource = "#C8C8C8";
        } else if (PatrolConstans.STATE_ABNARMAL.equals(state)) {
            resource = "#C8C8C8";
        } else if (PatrolConstans.STATE_FINISHG.equals(state)) {
            resource = "#C8C8C8";
        }
        return resource;
    }

    public int getChangedItemPosition() {
        return changedItemPosition;
    }

    public void setChangedItemPosition(final int changedItem) {
        this.changedItemPosition = changedItem;
    }

    public void setChangedItem(final PatrolTaskListDTO repairVo) {
        if (mList != null && changedItemPosition < mList.size()) {
            mList.set(changedItemPosition, repairVo);
            changedItemPosition = -1;
            notifyDataSetChanged();
        }
    }

    private void promptDialog(final PatrolTaskListDTO food) {
        new AlertView("提示", "是否确定取消关注？", "取消",
                new String[]{"确定"}, null, mContext, AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position >= 0) {
                    food.setConcerned(false);
                    notifyDataSetChanged();
                    new ConcernUtil()
                            .setConcernId(food.getId())
                            .setConcernType(ConcernUtil.PATROL_TYPE)
                            .setContexts(mContext)
                            .abolishConcern();
                }
            }
        }).show();
    }
}
