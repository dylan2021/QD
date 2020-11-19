package com.haocang.fault.list.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.ConcernUtil;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.fault.R;
import com.haocang.fault.list.bean.FaultManagerEntity;
import com.haocang.fault.list.utils.FaultRoundArImageView;

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
public class FaultManagerListAdapter extends BaseAdapter<FaultManagerEntity> {

    private Activity activity;

    public FaultManagerListAdapter(Activity activity, int layoutId) {
        super(layoutId);
        this.activity = activity;
    }

    RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.ic_fault_picture_no)// 正在加载中的图片
            .error(R.drawable.ic_fault_picture_no) // 加载失败的图片
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略

    @Override
    protected void convert(final BaseHolder holder, final FaultManagerEntity item) {
        holder.setText(R.id.fault_type_tv, item.getFaultTypeName());
        holder.setTextColor(R.id.fault_type_tv, Color.parseColor(item.getFaultTypeColor()));
        if (!TextUtils.isEmpty(item.getCreateDate()) && item.getCreateDate().length() > 10) {
            holder.setText(R.id.fault_date_tv, TimeTransformUtil.getShowLocalTime(item.getCreateDate()).substring(0, 10));
        } else {
            holder.setText(R.id.fault_date_tv, item.getCreateDate());
        }
        holder.setText(R.id.fault_severity_tv, "等级：" + item.getSeverityTypeName());
        holder.setText(R.id.person_tv, "处理人：" + item.getProcessingPersonName());
//        holder.setText(R.id.statu_iv, "( " + item.getNewState() + " )");
        holder.setText(R.id.statu_tv, getStateResource(item.getState()));
        holder.setTextColor(R.id.statu_tv, Color.parseColor(getStateColor(item.getState())));
        FaultRoundArImageView pic = holder.itemView.findViewById(R.id.fault_iv);
        Log.d("没有数据", "数据" + item.getPictureVideos());
        if (null == holder || null == item.getPictureVideos() || 0 == item.getPictureVideos().size()) {
            Glide.with(activity).load(R.drawable.ic_fault_picture_no).apply(options).into(pic);
        } else {
            Glide.with(activity).load(item.getPictureVideos().get(0).getThumbnailUrl()).apply(options).into(pic);
        }
        holder.setText(R.id.equi_tv, item.getEquName());
        LinearLayout assignmentTv = holder.itemView.findViewById(R.id.assignment_ll);
        if (item.canExcute()) {
            assignmentTv.setVisibility(View.VISIBLE);
        } else {
            assignmentTv.setVisibility(View.INVISIBLE);
        }
        assignmentTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("faultId", item.getId() + "");
                map.put("selectItemId", item.getId() + "");
                map.put("fragmentUri", LibConfig.AROUTE_FAULT_POST_PROCESSING);
                if (AppApplication.getInstance().getUserEntity().getId()
                        == item.getProcessingPersonId()) {
                    map.put("isCurrentUserExcute", true);
                }
                ARouterUtil.startActivityForResult(map, activity,
                        LibConstants.Fault.PICKPERSON_REQUEST_CODE);
            }
        });
        ImageView followIv = holder.itemView.findViewById(R.id.follow_iv);//添加关注
        followIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concerned(item);
            }
        });
        if (item.getConcerned()) {
            followIv.setBackgroundResource(R.drawable.icon_fault_blue_stars);
        } else {
            followIv.setBackgroundResource(R.drawable.icon_fault_star_grey);
        }
    }

    private void concerned(final FaultManagerEntity item) {
        if (item.getConcerned()) {
            promptDialog(item);
        } else {
            new ConcernUtil()
                    .setContexts(activity)
                    .setConcernId(item.getId())
                    .setConcernType(ConcernUtil.FAULT_TYPE)
                    .setConcernSuccess(addConcernInterfac)
                    .addConcern();
        }
    }

    public void setAddConcernInterface(ConcernUtil.AddConcernInterface addConcernInterfac) {
        this.addConcernInterfac = addConcernInterfac;
    }

    private ConcernUtil.AddConcernInterface addConcernInterfac;

    private String getStateResource(final Integer state) {
        String resource = "未分配";
        if (FaultManagerEntity.STATE_UNASSIGNED == state) {
            resource = "未分配";
        } else if (FaultManagerEntity.STATE_UNPROCESS == state) {
            resource = "待处理";
        } else if (FaultManagerEntity.STATE_PROCESSING == state) {
            resource = "处理中";
        } else if (FaultManagerEntity.STATE_HANGUP == state) {
            resource = "挂起";
        } else if (FaultManagerEntity.STATE_CLOSE == state) {
            resource = "已关闭";
        } else if (FaultManagerEntity.STATE_COMPLETE == state) {
            resource = "已完成";
        }
        return resource;
    }

    private String getStateColor(final Integer state) {
        String resource = "#F5A623";
        if (FaultManagerEntity.STATE_UNASSIGNED == state) {
            resource = "#F5A623";
        } else if (FaultManagerEntity.STATE_UNPROCESS == state) {
            resource = "#80BF22";
        } else if (FaultManagerEntity.STATE_PROCESSING == state) {
            resource = "#3781E8";
        } else if (FaultManagerEntity.STATE_HANGUP == state) {
            resource = "#C8C8C8";
        } else if (FaultManagerEntity.STATE_CLOSE == state) {
            resource = "#C8C8C8";
        } else if (FaultManagerEntity.STATE_COMPLETE == state) {
            resource = "#C8C8C8";
        }
        return resource;
    }

    private void promptDialog(final FaultManagerEntity food) {
        new AlertView("提示", "是否确定取消关注？", "取消",
                new String[]{"确定"}, null, activity, AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position >= 0) {
                    food.setConcerned(false);
                    notifyDataSetChanged();
                    new ConcernUtil()
                            .setConcernId(food.getId())
                            .setConcernType(ConcernUtil.FAULT_TYPE)
                            .setContexts(activity)
                            .abolishConcern();
                }
            }
        }).show();
    }
}
