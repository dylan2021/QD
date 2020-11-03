package com.haocang.waterlink.follow.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.alertview.AlertView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.base.utils.ConcernUtil;
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
 * 创建时间：${DATA} 9:48
 * 修 改 者：
 * 修改时间：
 */
public class FollowFaultAdapter extends BaseAdapter<FolloContentEntity> {
    private Context ctx;
    private ConcernUtil.AddConcernInterface addConcernInterface;

    public FollowFaultAdapter(int layoutId, Context ctx) {
        super(layoutId);
        this.ctx = ctx;
    }

    @Override
    protected void convert(BaseHolder holder, final FolloContentEntity item, int position) {
        holder.setText(R.id.equipment_name_tv, item.getName());
        holder.setText(R.id.fault_code_tv, item.getNo());
        holder.setText(R.id.fault_level_tv, "等级：" + item.getSeverity());
        if ("轻微".equals(item.getSeverity())) {
            holder.setTextColor(R.id.fault_level_tv, Color.parseColor("#ff0cabdf"));
        } else if ("一般".equals(item.getSeverity())) {
            holder.setTextColor(R.id.fault_level_tv, Color.parseColor("#fff5a623"));
        } else {
            holder.setTextColor(R.id.fault_level_tv, Color.parseColor("#ffff3347"));
        }
        holder.setText(R.id.fault_person_tv, item.getProcessingPersonName());
        ImageView faultIv = holder.itemView.findViewById(R.id.equipment_iv);
        if (!TextUtils.isEmpty(item.getImgUrl())) {
            String[] imgSr = item.getImgUrl().split(",");
            Glide.with(ctx).load(imgSr[0]).apply(options).into(faultIv);
        } else {
            Glide.with(ctx).load(R.drawable.ic_follow_picture_no).apply(options).into(faultIv);
        }
        holder.itemView.findViewById(R.id.follow_ibt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog(ConcernUtil.FAULT_TYPE, item.getItemId());
            }
        });
    }

    private RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.ic_follow_picture_no)// 正在加载中的图片
            .error(R.drawable.ic_follow_picture_no) // 加载失败的图片
            .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略

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
