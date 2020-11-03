package com.haocang.base.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.haocang.base.config.AppApplication;

public class DensityUtil {
    /**
     * dp转像素
     */
    public static int dip2px(Context context, float dpValue) {
        int h = 0;
        int w = 0;
        h = AppApplication.getContext().getResources().getDisplayMetrics().heightPixels;
        w = AppApplication.getContext().getResources().getDisplayMetrics().widthPixels;
        float scale = context.getResources().getDisplayMetrics().density;
//        if (scale == 2) {
//            if (Math.sqrt(h ^ 2 + w ^ 2) < 300) {
//                scale = 1.5f;
//            }
//        }
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 像素转dp
     */
    public static int px2dip(Context context, float pxValue) {
        int h = 0;
        int w = 0;
        h = AppApplication.getContext().getResources().getDisplayMetrics().heightPixels;
        w = AppApplication.getContext().getResources().getDisplayMetrics().widthPixels;
        double scale = context.getResources().getDisplayMetrics().density;
//        if (scale == 2) {
//            if (Math.sqrt(h ^ 2 + w ^ 2) < 300)
//                scale = 1.5;
//        }
        return (int) (pxValue / scale + 0.5);
    }

    public static void setListViewHeightBasedOnChildren(GridView gridview) {
        // 获取listview的adapter
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, gridview);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        gridview.setLayoutParams(params);
    }

    /**
     * 获取dimens定义的大小
     *
     * @param dimensionId
     * @return
     */
    public static int getPixelById(int dimensionId) {
        return AppApplication.getInstance().getResources().getDimensionPixelSize(dimensionId);
    }


}
