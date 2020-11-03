package com.haocang.repair.manage.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.repair.R;
import com.haocang.repair.manage.bean.RepairConstans;
import com.haocang.repair.manage.bean.RepairVo;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/2613:56
 * 修 改 者：
 * 修改时间：
 */
public class RepairListAdapter extends BaseAdapter<RepairVo> {

    /**
     *
     */
    private Context ctx;

    /**
     *
     */
    private int changedItemPosition = -1;

    public RepairListAdapter(final Context ctx, final int layoutId) {
        super(layoutId);
        this.ctx = ctx;
    }

    @Override
    protected void convert(final BaseHolder holder, final RepairVo item, final int position) {
        holder.setText(R.id.name_tv, item.getEquName());
//        holder.setImageResource(R.id.repair_state_iv, getStateResource(item.getState()));
        holder.setText(R.id.state_tv, item.getStateName());
        holder.setTextColor(R.id.state_tv, Color.parseColor(getStateColor(item.getState())));
        holder.setText(R.id.process_tv, item.getProcessName());
        holder.setText(R.id.person_tv,
                item.getProcessingPersonName());
        String dateStr = TimeTransformUtil.getShowLocalDate(item.getCreateDate());
        holder.setText(R.id.repair_date_tv, dateStr);
        LinearLayout assignmentLl
                = holder.itemView.findViewById(R.id.repair_assignment_ll);
        ImageButton followIv = holder.itemView.findViewById(R.id.follow_ibtn); //添加关注
        followIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                changedItemPosition = position;
                concerned(item);
            }
        });
        if (item.getConcerned() != null
                && item.getConcerned()) {
            followIv.setBackgroundResource(R.drawable.icon_repair_star_blue);
        } else {
            followIv.setBackgroundResource(R.drawable.icon_repair_star_grey);
        }
        assignmentLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                changedItemPosition = position;
                Map<String, Object> map = new HashMap<>();
                map.put("selectItemId", item.getId() + "");
                map.put("fragmentUri",
                        LibConfig.AROUTE_FAULT_POST_PROCESSING);
                if (AppApplication.getInstance().getUserEntity().getId()
                        == item.getProcessingPersonId()) {
                    map.put("isCurrentUserExcute", true);
                }
                ARouterUtil.startActivityForResult(map, (Activity) ctx,
                        LibConstants.Fault.PICKPERSON_REQUEST_CODE);
            }
        });

        if (item.canExcute()) {
            assignmentLl.setVisibility(View.VISIBLE);
        } else {
            assignmentLl.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * @param item 维修
     */
    private void concerned(final RepairVo item) {
        onConcernListener.concern(!item.getConcerned(), item.getId());
    }


    /**
     *
     */
    private OnConcernListener onConcernListener;

    /**
     * @param listener 监听.
     */
    public void setOnConcernListener(final OnConcernListener listener) {
        onConcernListener = listener;
    }

    public interface OnConcernListener {
        /**
         * @param concernFlag true为关注，false为取消关注.
         * @param id          维修ID
         */
        void concern(boolean concernFlag, Integer id);
    }

    private int getStateResource(final Integer state) {
        int resourceId = R.drawable.repair_state_wait_processing;
        if (RepairConstans.STATE_UNASSIGNED == state) {
            resourceId = R.drawable.repair_state_unallocate;
        } else if (RepairConstans.STATE_UNPROCESS == state) {
            resourceId = R.drawable.repair_state_wait_processing;
        } else if (RepairConstans.STATE_PROCESSING == state) {
            resourceId = R.drawable.repair_state_processing;
        } else if (RepairConstans.STATE_HANGUP == state) {
            resourceId = R.drawable.repair_state_hang_up;
        } else if (RepairConstans.STATE_CLOSE == state) {
            resourceId = R.drawable.repair_state_close;
        } else if (RepairConstans.STATE_COMPLETE == state) {
            resourceId = R.drawable.repair_state_complete;
        }
        return resourceId;
    }

    private String getStateColor(final Integer state) {
        String resource = "#F5A623";
        if (RepairConstans.STATE_UNASSIGNED == state) {
            resource = "#F5A623";
        } else if (RepairConstans.STATE_UNPROCESS == state) {
            resource = "#80BF22";
        } else if (RepairConstans.STATE_PROCESSING == state) {
            resource = "#3781E8";
        } else if (RepairConstans.STATE_HANGUP == state) {
            resource = "#C8C8C8";
        } else if (RepairConstans.STATE_CLOSE == state) {
            resource = "#C8C8C8";
        } else if (RepairConstans.STATE_COMPLETE == state) {
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

    public void setChangedItem(final RepairVo repairVo) {
        if (mList != null && changedItemPosition < mList.size()) {
            mList.set(changedItemPosition, repairVo);
            changedItemPosition = -1;
            notifyDataSetChanged();
        }
    }

}
