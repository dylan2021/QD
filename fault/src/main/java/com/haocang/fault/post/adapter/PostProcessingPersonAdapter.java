package com.haocang.fault.post.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.base.bean.LabelEntity;
import com.haocang.fault.R;

import java.util.ArrayList;
import java.util.List;

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
 * 创建时间：2018/4/269:52
 * 修 改 者：
 * 修改时间：
 */
public class PostProcessingPersonAdapter extends BaseAdapter<LabelEntity> {

    public PostProcessingPersonAdapter(int layoutId) {
        super(layoutId);
    }

    @Override
    protected void convert(BaseHolder holder, LabelEntity item) {
        TextView signTv = holder.itemView.findViewById(R.id.signin_tv);
        if (item.isSelect()) {
            holder.setImageResource(R.id.patrol_allocate_iv, R.drawable.patrol_select);
        } else {
            holder.setImageResource(R.id.patrol_allocate_iv, R.drawable.patrol_unselect);
        }
        if (signTv != null) {
            if (!TextUtils.isEmpty(item.getOrgNames())) {
                signTv.setVisibility(View.VISIBLE);
            } else {
                signTv.setVisibility(View.GONE);
            }
        }
        holder.setText(R.id.patrol_allocator_tv, item.getLabel());
        holder.setText(R.id.patrol_allocate_group_tv, item.getOrgName());
    }


    /**
     * 当前选择之外的行全部设置为未选中
     *
     * @param position
     */
    public void clearOther(final int position) {
        for (int i = 0; i < mList.size(); i++) {
            if (i != position) {
                mList.get(i).setSelect(false);
            }
        }
    }


    public LabelEntity getSelect() {
        LabelEntity entity = null;
        if (mList != null) {
            for (LabelEntity user : mList) {
                if (user.isSelect()) {
                    entity = user;
                }
            }
        }
        return entity;
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
        }
        notifyDataSetChanged();
    }


}
