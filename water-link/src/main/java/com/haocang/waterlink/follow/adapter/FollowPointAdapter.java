package com.haocang.waterlink.follow.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.alertview.AlertView;
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
 * 创建时间：${DATA} 17:44
 * 修 改 者：
 * 修改时间：
 */
public class FollowPointAdapter extends BaseAdapter<FolloContentEntity> {
    private Context ctx;
    private ConcernUtil.AddConcernInterface addConcernInterface;

    public FollowPointAdapter(int layoutId, Context ctx) {
        super(layoutId);
        this.ctx = ctx;
    }

    @Override
    protected void convert(BaseHolder holder, final FolloContentEntity item, int position) {
        holder.setText(R.id.name_tv, item.getNewName());
        if (!TextUtils.isEmpty(item.getValue())) {
            holder.setText(R.id.value_tv, item.getValue());
            holder.setText(R.id.unit_tv, item.getUnit());
        } else {
            holder.setText(R.id.value_tv, "");
            holder.setText(R.id.unit_tv, "");
        }

        holder.setText(R.id.time_tv, item.getSiteName());
        ImageView followBtn = holder.itemView.findViewById(R.id.follow_ibt);
        followBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptDialog(ConcernUtil.M_POINT, item.getItemId());
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
}
