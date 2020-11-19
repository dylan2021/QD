package com.haocang.curve.collection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haocang.curve.R;
import com.haocang.curve.collection.bean.PointList;
import com.haocang.curve.collection.bean.SignleCurve;
import com.haocang.curve.collection.iview.SignleCollectionView;
import com.haocang.curve.main.bean.CurveConstans;
import com.haocang.curve.main.util.MyUtils;
import com.haocang.curve.more.bean.PointEntity;

import org.feezu.liuli.timeselector.Utils.TextUtil;

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
public class GignlePointCollectionAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PointList> mList = new ArrayList<>();
    private SignleCollectionView signleCollectionView;
    public GignlePointCollectionAdapter(final SignleCollectionView view) {
        signleCollectionView = view;
    }
    @Override
    public int getItemViewType(final int position) {
        int type = 0;
        if (mList != null) {
            if (mList.get(position).getType() == CurveConstans.TYPE_TITLE) {
                type = CurveConstans.TYPE_TITLE;
            } else if (mList.get(position).getType() == CurveConstans.TYPE_NONE) {
                type = CurveConstans.TYPE_NONE;
            } else {
                type = CurveConstans.TYPE_DATA;
            }

        } else {
            type = CurveConstans.TYPE_NONE;
        }
        return type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                                      final int viewType) {
        if (viewType == CurveConstans.TYPE_TITLE) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.adapter_point_title, parent, false);
            return new PointTitleHolder(view);
        } else if (viewType == CurveConstans.TYPE_NONE) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.curve_none_item, parent, false);
            return new NoneHolder(view);
        } else {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.adapter_point_point_list, parent, false);
            return new PointListHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder,
                                 final int position) {
        if (holder instanceof PointTitleHolder) {
            setPointTitle((PointTitleHolder) holder, position);
        } else if (holder instanceof PointListHolder) {
            setPointList((PointListHolder) holder, position);
        } else if (holder instanceof NoneHolder) {

        }
    }

    /**
     * @param holder   holder.
     * @param position 位置
     */
    private void setPointTitle(final PointTitleHolder holder,
                               final int position) {
        PointList entity = mList.get(position);
        holder.titleNameTv.setText(entity.getMpointName());
    }

    /**
     * @param holder   holder.
     * @param position 位置
     */
    private void setPointList(final PointListHolder holder,
                              final int position) {
        final PointList entity = mList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                signleCollectionView.onItemClick(entity);
//                onItemClickListener.onItemClickListener(entity);/
            }
        });
        holder.siteNameTv.setText(entity.getSiteName());
        holder.nameTv.setText(entity.getMpointName());
        holder.siteCodeTv.setText(entity.getMpointId());
        String unit = MyUtils.removeNull(entity.getUnit());
        holder.siteNumTv.setText("当前值:" + entity.getValue() + unit);
        String datadt = entity.getDatadt();
        if (datadt != null) {
            holder.siteTimeTv.setText(MyUtils.getTimeT8(datadt));
        }
        String dataSource = entity.getDatasource();
        if (dataSource.equals("AUTO")) {
            dataSource = "自动采集";
        }
        if (dataSource.equals("INPUT")) {
            dataSource = "人工录入";
        }
        if (dataSource.equals("CALC")) {
            dataSource = "数据计算";
        }
        holder.fromSiteTv.setText(dataSource);
        holder.fromTypeTv.setText(entity.getCategoryName());

        if (entity.isSelect()) {
            holder.selectIv.setVisibility(View.VISIBLE);
        } else {
            holder.selectIv.setVisibility(View.GONE);
        }
    }

    public List<PointEntity> getSelectList() {
        List<PointEntity> list = new ArrayList<>();
        if (mList != null && mList.size() > 0) {
            for (PointList entity : mList) {
                if (entity.isSelect()) {
                    PointEntity bean = new PointEntity();
                    bean.setId(entity.getId());
                    bean.setMpointId(entity.getId() + "");
                    bean.setMpointName(entity.getMpointName());
                    bean.setSiteName(entity.getSiteName());
                    list.add(bean);
                }
            }
        }
        return list;
    }

    public class PointTitleHolder extends RecyclerView.ViewHolder {
        private TextView titleNameTv;
        public PointTitleHolder(final View itemView) {
            super(itemView);
            titleNameTv = itemView.findViewById(R.id.title_name_tv);
        }
    }

    public class PointListHolder extends RecyclerView.ViewHolder {
        private ImageView selectIv;
        private TextView nameTv;
        private TextView siteNameTv;

        private TextView siteCodeTv;

        private TextView siteNumTv;

        private TextView siteTimeTv;

        private TextView fromSiteTv;

        private TextView fromTypeTv;
        public PointListHolder(final View itemView) {
            super(itemView);
            selectIv = itemView.findViewById(R.id.select_iv);
            nameTv = itemView.findViewById(R.id.point_name_tv);
            siteNameTv = itemView.findViewById(R.id.site_name_tv);
            siteCodeTv = itemView.findViewById(R.id.site_code_tv);
            siteNumTv = itemView.findViewById(R.id.site_num_tv);
            siteTimeTv = itemView.findViewById(R.id.site_time_tv);
            fromSiteTv = itemView.findViewById(R.id.from_site_tv);
            fromTypeTv = itemView.findViewById(R.id.from_type_tv);
        }
    }

    public class NoneHolder extends RecyclerView.ViewHolder {
        public NoneHolder(final View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void addAll(final List<PointList> list) {
        mList.addAll(list);
    }

    public void addItem(final PointList entity) {
        mList.add(entity);
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public Context getContext() {
        return signleCollectionView.getContext();
    }
}
