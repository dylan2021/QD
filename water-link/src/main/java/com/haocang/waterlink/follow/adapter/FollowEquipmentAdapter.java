package com.haocang.waterlink.follow.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.alertview.AlertView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.base.bean.EquimentEntity;
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
 * 创建时间：${DATA} 9:09
 * 修 改 者：
 * 修改时间：
 */
public class FollowEquipmentAdapter extends BaseAdapter<FolloContentEntity> {
    private Context ctx;
    private ConcernUtil.AddConcernInterface addConcernInterface;

    public FollowEquipmentAdapter(int layoutId, Context ctx) {
        super(layoutId);
        this.ctx = ctx;
    }

    @Override
    protected void convert(BaseHolder holder, final FolloContentEntity entity, int position) {
        ImageView equipIv = holder.itemView.findViewById(R.id.equipment_iv);
        Glide.with(ctx).load(entity.getImgUrl()).apply(options).into(equipIv);
        holder.setText(R.id.equipment_name_tv, entity.getName());
        holder.setText(R.id.equipment_code_tv, entity.getNo());
        holder.setText(R.id.equipment_process_tv, entity.getProcess());
        holder.setText(R.id.org_tv, entity.getOrgName());
        holder.itemView.findViewById(R.id.follow_ibt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog(ConcernUtil.EQUIPMENT_TYPE, entity.getItemId());
            }
        });
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

    private RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.icon_equipment_pic_empty)// 正在加载中的图片
            .error(R.drawable.icon_equipment_pic_empty) // 加载失败的图片
            .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
}
