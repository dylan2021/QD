package com.haocang.waterlink.self.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.waterlink.R;
import com.haocang.waterlink.self.bean.KpiHomeContentEntity;

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
 * 创建时间：2018/3/2213:11
 * 修 改 者：
 * 修改时间：
 */
public class KpiHomeAdapter extends BaseAdapter<KpiHomeContentEntity> {
    public final static int EQUIMENT = 0;//设备台账
    public final static int ROUTE = 1;//巡检
    public final static int DEFECT = 2;//缺陷
    public final static int REPAIR = 3;//维修
    public final static int CURING = 4;//养护

    public KpiHomeAdapter() {
        super(R.layout.adapter_kpi_home_equiment);
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (mList.get(viewType).getType()) {
            case EQUIMENT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_kpi_home_equiment, parent, false);
                break;
            case ROUTE:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_kpi_setup_route, parent, false);
                break;
            case DEFECT:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_kpi_setup_defect, parent, false);
                break;
            case REPAIR:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_kpi_setup_repair, parent, false);
                break;
            case CURING:
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_kpi_setup_curing, parent, false);
                break;
        }
        BaseHolder holder = new BaseHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final BaseHolder holder, final int position) {
        final KpiHomeContentEntity entity = mList.get(position);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(holder.itemView, position, entity);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onLongClick(holder.itemView, position, entity);
                    return true;
                }
            });
        }
        switch (getItemViewType(entity.getType())) {
            case EQUIMENT:
                setEquimentData(holder, entity);
                break;
            case ROUTE:
                setRouteTaskData();
                break;
            case DEFECT:
                setDefectData();
                break;
            case REPAIR:
                setRepairData();
                break;
            case CURING:
                setCuringData();
                break;
        }
    }

    @Override
    public int getItemViewType(final int position) {
        if (position == EQUIMENT) {
            return EQUIMENT;
        } else if (position == ROUTE) {
            return ROUTE;
        } else if (position == DEFECT) {
            return DEFECT;
        } else if (position == REPAIR) {
            return REPAIR;
        } else {
            return CURING;
        }
    }


    /**
     * 绑定设备台账数据.
     *
     * @param holder
     * @param entity
     */
    private void setEquimentData(final BaseHolder holder, final KpiHomeContentEntity entity) {
        holder.setText(R.id.value_tv, "93");
        holder.setText(R.id.unti_tv, "%");
        holder.setText(R.id.name_tv, "设备使用率");
    }

    /**
     * 绑定巡检数据.
     */
    private void setRouteTaskData() {

    }

    /**
     * 绑定消缺数据.
     */
    private void setDefectData() {

    }

    /**
     * 绑定维修数据.
     */
    private void setRepairData() {

    }

    /**
     * 绑定养护数据.
     */
    private void setCuringData() {

    }

}
