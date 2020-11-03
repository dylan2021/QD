package com.haocang.base.adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * 创建时间：2018/3/1914:59
 * 修 改 者：
 * 修改时间：
 */
public class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder> {
    public OnItemClickListener mOnItemClickListener;
    public List<T> mList = new ArrayList<>();
    private int layoutId;
    public View footView;
    private int max_count = 10;//最大显示数
    private Boolean isFootView = false;//是否添加了FootView

    //两个final int类型表示ViewType的两种类型
    private final int NORMAL_TYPE = 0;
    private final int FOOT_TYPE = 1111;

    public BaseAdapter(int layoutId) {
        this.layoutId = layoutId;
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("position",position+"");
        Log.e("max_count",max_count+"");
        if (position == max_count) {
            return FOOT_TYPE;
        }
        return NORMAL_TYPE;
    }

    public void addAll(List<T> mList) {
        if (mList != null && mList.size() > 0) {
            this.mList.addAll(mList);
        }
        max_count = mList.size();
    }

    public void add(T entity) {
        if (entity != null) {
            mList.add(entity);
        }
        max_count = mList.size();
    }

    public void clear() {
        if (mList != null) {
            mList.clear();
            max_count = mList.size();
            notifyDataSetChanged();
        }
    }

    //onCreateViewHolder用来给rv创建缓存
    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        Log.e("viewType",viewType+"");
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        if (viewType == FOOT_TYPE&&footView!=null){
            return new BaseHolder(footView, FOOT_TYPE);
        }
        BaseHolder holder = new BaseHolder(view);
        return holder;
    }

    //onBindViewHolder给缓存控件设置数据
    @Override
    public void onBindViewHolder(final BaseHolder holder, final int position) {
        if ((getItemViewType(position) == FOOT_TYPE)) {
//            holder.footView.setText(footViewText);
            convert(holder);
        } else {
            final T item = mList.get(position);
            if (mOnItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(holder.itemView, position, item);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        mOnItemClickListener.onLongClick(holder.itemView, position, item);
                        return true;
                    }
                });
            }
            convert(holder, item, position);
        }
    }

    /**
     * @param holder 列表视图holder.
     */
    protected void convert(final BaseHolder holder) {
        //什么都没有做

    }
    /**
     * @param holder 列表视图holder.
     * @param item   绑定视图实体类
     */
    protected void convert(final BaseHolder holder, T item) {
        //什么都没有做

    }

    /**
     * 实现类可选择实现convert(final BaseHolder holder, T item, final int position).
     * 或
     * convert(BaseHolder holder, T item)
     *
     * @param holder   列表视图holder
     * @param item     绑定视图实体类
     * @param position 位置
     */
    protected void convert(final BaseHolder holder, T item, final int position) {
        //什么都没有做
        convert(holder, item);
    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        if (footView!=null){
            return mList == null ? 1 : mList.size()+1;
        }
        return mList == null ? 0 : mList.size();
    }


    public interface OnItemClickListener<T> {
        void onClick(View view, int position, T item);

        void onLongClick(View view, int position, T item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


}
