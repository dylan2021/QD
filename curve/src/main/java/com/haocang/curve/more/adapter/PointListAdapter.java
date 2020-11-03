package com.haocang.curve.more.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haocang.curve.R;
import com.haocang.curve.more.bean.PointEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区龙兰路277号一号楼9楼
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/5/31 13:51
 * 修 改 者：
 * 修改时间：
 */
public class PointListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PointEntity> mList = new ArrayList<>();
    private Context ctx;
    public static final int SELECTED = 1;//标题
    public static final int SINGULAR = 2;//数据
    public OnItemClickListener onItemClickListener;

    public PointListAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SELECTED) {
            View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_point_title, parent, false);
            return new PointTitleHolder(view);
        } else if (viewType == SINGULAR) {
            View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_point_list, parent, false);
            return new PointListHolder(view);
        }
        return null;
    }

    private void setPointTitle(PointTitleHolder holder, int position) {
        PointEntity entity = mList.get(position);
        holder.titleNameTv.setText(entity.getTitleName());
    }

    private void setPointList(PointListHolder holder, int position) {
        final PointEntity entity = mList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClickListener(entity);
            }
        });
        holder.siteNameTv.setText(entity.getSiteName());
        holder.nameTv.setText(entity.getMpointName());
        if (entity.isSelect()) {
            holder.selectIv.setVisibility(View.VISIBLE);
        } else {
            holder.selectIv.setVisibility(View.GONE);
        }
    }


    public class PointTitleHolder extends RecyclerView.ViewHolder {
        private TextView titleNameTv;

        public PointTitleHolder(View itemView) {
            super(itemView);
            titleNameTv = itemView.findViewById(R.id.title_name_tv);
        }
    }

    public class PointListHolder extends RecyclerView.ViewHolder {
        private ImageView selectIv;
        private TextView nameTv;
        private TextView siteNameTv;

        public PointListHolder(View itemView) {
            super(itemView);
            selectIv = itemView.findViewById(R.id.select_iv);
            nameTv = itemView.findViewById(R.id.point_name_tv);
            siteNameTv = itemView.findViewById(R.id.site_name_tv);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PointTitleHolder) {
            setPointTitle((PointTitleHolder) holder, position);
        } else if (holder instanceof PointListHolder) {
            setPointList((PointListHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    @Override
    public int getItemViewType(int position) {
        int type = mList.get(position).getType();
        if (type == SELECTED) {
            return SELECTED;
        } else {
            return SINGULAR;
        }
    }

    public void addAll(List<PointEntity> list) {
        mList.addAll(list);
    }

    public void addItem(PointEntity entity) {
        mList.add(entity);
    }

    public void clear() {
        mList.clear();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(PointEntity entity);
    }

    /**
     * 获取选中的测点列表
     *
     * @return
     */
    public List<PointEntity> getSelectPointList() {
        List<PointEntity> list = new ArrayList<>();
        for (PointEntity entity : mList) {
            if (entity.isSelect()) {
                list.add(entity);
            }
        }
        return list;
    }
}
