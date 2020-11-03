package com.haocang.patrol.patrolinhouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.LayoutRes;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haocang.base.adapter.BaseRecyclerAdapter;
import com.haocang.base.adapter.SmartViewHolder;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.TimeUtil;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.haocang.patrol.R;
import com.haocang.patrol.patrolinhouse.bean.PatrolPointDetailDTO;
import com.haocang.patrol.patrolinhouse.ui.PatrolPointDetailScanListFragment;

import java.util.Collection;

/**
 * Created by william on 2018/4/4.
 */

public class PatrolPointAdapter extends BaseRecyclerAdapter<PatrolPointDetailDTO> {
    private Context mContext;
    private Integer taskId;
    private String statu;
    public PatrolOnItemClickListent onItemClickListent;

    public PatrolPointAdapter(final Collection<PatrolPointDetailDTO> collection, final int layoutId) {
        super(collection, layoutId);
    }

    public PatrolPointAdapter(final @LayoutRes int layoutId, final Context ctx) {
        super(layoutId);
        mContext = ctx;
    }

    @Override
    public SmartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    protected void onBindViewHolder(final SmartViewHolder holder, final PatrolPointDetailDTO food, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("executing".equals(statu) && null != food.getResultUpdateTime()) {
//                    intent.putExtra("pointId", food.getId()
                    onItemClickListent.onItemClickListent(food);

                } else {
                    Gson gson = new Gson();
                    String patrolPointDetailDTOStr = gson.toJson(food, PatrolPointDetailDTO.class);
                    Intent intent = new Intent(mContext, CommonActivity.class);
                    intent.putExtra("patrolPointDetailDTOStr", patrolPointDetailDTOStr);
                    intent.putExtra("taskId", taskId);
                    if (OffLineOutApiUtil.isOffLine()) {
                        intent.putExtra("pointId", food.getPatrolPointId());
                    } else {
                        intent.putExtra("pointId", food.getId());
                    }
                    intent.putExtra("patrolPointId", food.getPatrolPointId());
                    intent.putExtra("pointName", food.getPatrolPoint());
                    intent.putExtra("fragmentName", PatrolPointDetailScanListFragment.class.getName());
                    mContext.startActivity(intent);
                }


            }
        });
        TextView nametv = holder.itemView.findViewById(R.id.patrol_pointname_tv);
        nametv.setTextColor(mContext.getResources().getColor(R.color.patrol_name_common));
        holder.text(R.id.patrol_pointname_tv, food.getPatrolPoint());
        TextView patrolTimeTv = holder.itemView.findViewById(R.id.patrol_time_tv);
        if (food.getResultUpdateTime() == null) {
            patrolTimeTv.setText(R.string.patrol_undo);
            patrolTimeTv.setTextColor(Color.parseColor("#F5A623"));
        } else {
            patrolTimeTv.setTextColor(Color.parseColor("#C8C8C8"));
            patrolTimeTv.setText(TimeUtil.getDateStrMMddHHmm(food.getResultUpdateTime()));
        }
        holder.text(R.id.fault_count_tv, food.getFaultCount() + "");
        holder.text(R.id.step_count_tv, food.getRecordCount() + " / " + food.getStepCount());
        if (food.getResultUpdateTime() == null) {
            nametv.setTextColor(mContext.getResources().getColor(R.color.patrol_name_common));
            holder.image(R.id.patrol_fault_iv, R.mipmap.patrol_fault);
            holder.image(R.id.patrol_complete_iv, R.mipmap.patrol_complete);
        } else {
            nametv.setTextColor(mContext.getResources().getColor(R.color.patrol_name_gray));
            holder.image(R.id.patrol_fault_iv, R.mipmap.patrol_fault_gray);
            holder.image(R.id.patrol_complete_iv, R.mipmap.patrol_complete_gray);
        }
    }

    /**
     * @param taskId 获取任务ID.
     */
    public void setTaskId(final Integer taskId) {
        this.taskId = taskId;
    }


    /**
     * 除当前巡检点外，其他巡检点是否已经完成
     *
     * @param patrolPointId 巡检点ID
     * @return
     */
    public boolean isTaskCompleteOthers(final int patrolPointId) {
        boolean completeFlag = true;
        for (PatrolPointDetailDTO point : mList) {
            /**
             * 当前巡检点不放入比较
             */
            if (point.getId() != patrolPointId && point.getStepCount() != point.getRecordCount()) {
                completeFlag = false;
                break;
            }
        }
        return completeFlag;
    }

    public boolean offLineisTaskCompleteOthers(final int patrolPointId) {
        boolean completeFlag = true;
        for (PatrolPointDetailDTO point : mList) {
            /**
             * 当前巡检点不放入比较
             */
            if (point.getPatrolPointId() != patrolPointId && point.getStepCount() != point.getRecordCount()) {
                completeFlag = false;
                break;
            }
        }
        return completeFlag;
    }

    /**
     * 获取状态 执行中的可以直接
     *
     * @param statu 状态
     * @return
     */
    public void setStatu(final String statu) {
        this.statu = statu;
    }


    public interface PatrolOnItemClickListent {
        void onItemClickListent(PatrolPointDetailDTO dto);
    }


    /**
     * @param onItemClickListent 点击回调事件监听
     */
    public void setOnItemClickListent(final PatrolOnItemClickListent onItemClickListent) {
        this.onItemClickListent = onItemClickListent;
    }
}
