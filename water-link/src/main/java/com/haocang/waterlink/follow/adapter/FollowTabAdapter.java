package com.haocang.waterlink.follow.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.MenuEntity;

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
 * 创建时间：${DATA} 13:54
 * 修 改 者：
 * 修改时间：
 */
public class FollowTabAdapter extends BaseAdapter<MenuEntity> {
    private Activity activity;

    public FollowTabAdapter(int layoutId, Activity activity) {
        super(layoutId);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseHolder holder, MenuEntity item, int position) {
        TextView nameTv = holder.itemView.findViewById(R.id.name_tv);
        TextView tabTv = holder.itemView.findViewById(R.id.tab_tv);
        nameTv.setWidth(getWidth(activity) / 4);
        nameTv.setGravity(Gravity.CENTER);
        nameTv.setText(item.getName());
        if (item.getReson() > 0) {
            tabTv.setVisibility(View.GONE);
            nameTv.setTextSize(20);
            nameTv.setTextColor(Color.parseColor("#ffffff"));
            nameTv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, -10, 0, 0);//4个参数按顺序分别是左上右下
            nameTv.setLayoutParams(layoutParams);
        } else {
            tabTv.setVisibility(View.GONE);
            nameTv.setTextSize(15);
            nameTv.setTextColor(Color.parseColor("#ffffff"));
            nameTv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 0);//4个参数按顺序分别是左上右下
            nameTv.setLayoutParams(layoutParams);
        }
    }

    private int getWidth(Activity activity) {
        WindowManager wm1 = activity.getWindowManager();
        int width1 = wm1.getDefaultDisplay().getWidth();
        return width1;
    }

    /**
     * 设置选中
     */
    public void setSelect(int position) {
        for (int i = 0; i < mList.size(); i++) {
            MenuEntity entity = mList.get(i);
            if (position == i) {
                entity.setReson(2);//todo 大于0 就行了
            } else {
                entity.setReson(0);
            }
        }
        notifyDataSetChanged();
    }
}
