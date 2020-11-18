package com.haocang.curve.collection.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haocang.curve.R;
import com.haocang.curve.collection.bean.SignleCurve;
import com.haocang.curve.collection.iview.SignleCollectionView;
import com.haocang.curve.main.bean.CurveConstans;
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
public class GignleCollectionAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SignleCurve> mList = new ArrayList<>();
    private SignleCollectionView signleCollectionView;
    public GignleCollectionAdapter(final SignleCollectionView view) {
        signleCollectionView = view;
    }
    @Override
    public int getItemViewType(final int position) {
        int type = CurveConstans.TYPE_TITLE;
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
                    .inflate(R.layout.adapter_point_list, parent, false);
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

    private void setPointTitle(final PointTitleHolder holder,
                               final int position) {
        SignleCurve entity = mList.get(position);
        holder.titleNameTv.setText(entity.getTitleName());
    }

    private void setPointList(final PointListHolder holder,
                              final int position) {
        final SignleCurve entity = mList.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                signleCollectionView.onItemClick(entity);
            }
        });
        holder.siteNameTv.setText(entity.getSiteName());
        holder.nameTv.setText(entity.getName());
        if (entity.isSelect()) {
            holder.selectIv.setVisibility(View.VISIBLE);
        } else {
            holder.selectIv.setVisibility(View.GONE);
        }
    }

    public List<PointEntity> getSelectList() {
        List<PointEntity> list = new ArrayList<>();
        if (mList != null && mList.size() > 0) {
            for (SignleCurve entity : mList) {
                if (entity.isSelect()) {
                    PointEntity bean = new PointEntity();
                    bean.setId(entity.getId());
                    bean.setMpointId(entity.getId() + "");
                    bean.setMpointName(entity.getName());
                    bean.setSiteName(entity.getSiteName());
                    list.add(bean);
                }
            }
        }
        return list;
    }

    public class PointTitleHolder extends RecyclerView.ViewHolder {
        private TextView titleNameTv;

        /**
         * @param itemView itemView
         */
        public PointTitleHolder(final View itemView) {
            super(itemView);
            titleNameTv = itemView.findViewById(R.id.title_name_tv);
        }
    }

    public class PointListHolder extends RecyclerView.ViewHolder {
        /**
         *
         */
        private ImageView selectIv;
        /**
         *
         */
        private TextView nameTv;
        /**
         *
         */
        private TextView siteNameTv;

        /**
         * @param itemView itemView
         */
        public PointListHolder(final View itemView) {
            super(itemView);
            selectIv = itemView.findViewById(R.id.select_iv);
            nameTv = itemView.findViewById(R.id.point_name_tv);
            siteNameTv = itemView.findViewById(R.id.site_name_tv);
        }
    }

    public class NoneHolder extends RecyclerView.ViewHolder {
        /**
         * @param itemView itemView
         */
        public NoneHolder(final View itemView) {
            super(itemView);
        }
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * @param list 数据列表
     */
    public void addAll(final List<SignleCurve> list) {
        mList.addAll(list);
    }

    /**
     * 添加单个
     *
     * @param entity 要添加的数据
     */
    public void addItem(final SignleCurve entity) {
        mList.add(entity);
    }

    /**
     *
     */
    public void clear() {
        mList.clear();
    }

    /**
     * @return
     */
    public Context getContext() {
        return signleCollectionView.getContext();
    }
}
