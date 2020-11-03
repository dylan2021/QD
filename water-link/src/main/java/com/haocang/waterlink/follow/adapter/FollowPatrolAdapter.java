package com.haocang.waterlink.follow.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.base.utils.ConcernUtil;
import com.haocang.patrol.manage.bean.PatrolConstans;
import com.haocang.waterlink.R;
import com.haocang.waterlink.self.bean.FolloContentEntity;

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
 * 创 建 者：hy
 * 创建时间：${DATA} 9:09
 * 修 改 者：
 * 修改时间：
 */
public class FollowPatrolAdapter extends BaseAdapter<FolloContentEntity> {
    private Context ctx;
    private ConcernUtil.AddConcernInterface addConcernInterface;

    public FollowPatrolAdapter(int layoutId, Context ctx) {
        super(layoutId);
        this.ctx = ctx;
    }

    @Override
    protected void convert(BaseHolder holder, final FolloContentEntity food, int position) {
        holder.setText(R.id.patrol_task_name, food.getName());
        holder.setText(R.id.time_tv, food.getNewDateTime());
        holder.setText(R.id.person_tv, food.getExecutorName());
        holder.setText(R.id.state_tv, food.getStatus());
        if ("Outside".equals(food.getType())) {
            holder.setImageResource(R.id.patrol_iv, R.drawable.icon_follow_map);
        } else {
            holder.setImageResource(R.id.patrol_iv, R.drawable.icon_follow_person);
        }
        ImageView followBtn = holder.itemView.findViewById(R.id.follow_ibt);
        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog(ConcernUtil.PATROL_TYPE, food.getItemId());
            }
        });
        TextView stateTv = holder.itemView.findViewById(R.id.state_tv);
        stateTv.setText(food.getStatus());
        if ("未分配".equals(food.getStatus())) {
            stateTv.setTextColor(Color.parseColor("#fff5a623"));
        } else if ("待处理".equals(food.getStatus())) {
            stateTv.setTextColor(Color.parseColor("#ff80bf22"));
        } else if ("处理中".equals(food.getStatus())) {
            stateTv.setTextColor(Color.parseColor("#ff3781e8"));
        } else if ("已完成".equals(food.getStatus())) {
            stateTv.setTextColor(Color.parseColor("#ffc8c8c8"));
        } else if ("挂起".equals(food.getStatus())) {
            stateTv.setTextColor(Color.parseColor("#ffff3347"));
        } else if ("异常".equals(food.getStatus())) {
            stateTv.setTextColor(Color.parseColor("#fff94837"));
        }
    }

    /**
     * 提示
     */
    private void promptDialog(final String type, final int id) {
        new AlertView("提示", "是否确定取消关注？", "取消",
                new String[]{"确定"}, null, ctx, AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position >= 0) {
                    canclerFollow(type, id);
                }
            }
        }).show();
    }

    private void canclerFollow(final String type, final int id) {
        new ConcernUtil()
                .setConcernType(type)
                .setContexts(ctx)
                .setConcernId(id)
                .setConcernSuccess(addConcernInterface)
                .abolishConcern();
    }

    public void setConcernInterface(ConcernUtil.AddConcernInterface addConcernInterface) {
        this.addConcernInterface = addConcernInterface;
    }
}
