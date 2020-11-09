package com.haocang.base.adapter;

import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haocang.base.R;

import java.util.HashMap;

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
 * 创建时间：2018/3/1914:58
 * 修 改 者：
 * 修改时间：
 */
public class BaseHolder extends RecyclerView.ViewHolder {
    //不写死控件变量，而采用Map方式
    private HashMap<Integer, View> mViews = new HashMap<>();

    public BaseHolder(View itemView) {
        super(itemView);
    }

    private final int NORMAL_TYPE = 0;
    private final int FOOT_TYPE = 1111;
    public TextView textView;
    public View footView;
    //在MyViewHolder构造方法中添加一个新的参数，int ViewType,然后判断。
    public BaseHolder(View itemView,int viewType) {
        super(itemView);
        //如果是normalView那么给textView(就是item的内容)赋值。
        if (viewType == NORMAL_TYPE) {
//            itemView;
//            textView = itemView.findViewById(R.id.textView);
            //如果是footView那么给footView赋值。
        } else if (viewType == FOOT_TYPE) {
            footView = itemView;
        }
    }

//    private TextView textView;

    /**
     * 获取控件的方法
     */
    public <T> T getView(Integer viewId) {
        //根据保存变量的类型 强转为该类型
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            //缓存
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 传入文本控件id和设置的文本值，设置文本
     */
    public BaseHolder setText(final Integer viewId, String value) {
        textView = getView(viewId);
        if (value == null || "null".equals(value)) {
            value = "";
        }
        if (textView != null) {
            textView.setText(value);
        }
        return this;
    }
    public BaseHolder setText(final Integer viewId,String key, String value) {
        textView = getView(viewId);
        if (value == null || "null".equals(value)) {
            value = "";
        }
        if (textView != null) {
            textView.setText(key+"："+value);
        }
        return this;
    }
    public BaseHolder setTextColor(Integer viewId, int color) {
        textView = getView(viewId);
        textView.setTextColor(color);
        return this;
    }

    public BaseHolder setTextBackground(Drawable drawable) {
        textView.setBackgroundDrawable(drawable);
        return this;
    }

    /**
     * 隐藏控件.
     *
     * @param flag
     * @return
     */
    public BaseHolder setVisible(boolean flag) {
        if (flag) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
        return this;
    }


    /**
     * 传入图片控件id和资源id，设置图片
     */
    public BaseHolder setImageResource(Integer viewId, Integer resId) {
        ImageView imageView = getView(viewId);
        if (imageView != null) {
            imageView.setBackgroundResource(resId);
        }
        return this;
    }
}
