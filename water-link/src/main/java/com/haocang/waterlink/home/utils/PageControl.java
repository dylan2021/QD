package com.haocang.waterlink.home.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haocang.base.utils.DensityUtil;
import com.haocang.waterlink.R;

/**
 * 菜单页数.
 */
public class PageControl {
    /**
     * .
     */
    private LinearLayout layout;
    /**
     * 存储textView.
     */
    private TextView[] textViews;
    private TextView textView;
    private int pageSize;
    private int currentPage = 0;
    private Context mContext;

    public PageControl(final Context context, final LinearLayout layout, final int pageSize) {
        this.mContext = context;
        this.pageSize = pageSize;
        this.layout = layout;
        initDots();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final int currentPage) {
        this.currentPage = currentPage;
    }

    void initDots() {
        textViews = new TextView[pageSize];
        for (int i = 0; i < pageSize; i++) {

            textView = new TextView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 8),
                    DensityUtil.dip2px(mContext, 8));
            params.leftMargin = DensityUtil.dip2px(mContext, 5);
            params.rightMargin = DensityUtil.dip2px(mContext, 5);
            params.topMargin = DensityUtil.dip2px(mContext, 6);
            if (i == 0) {
                params.width = DensityUtil.dip2px(mContext, 8);
                params.height = DensityUtil.dip2px(mContext, 8);
            } else {
                params.width = DensityUtil.dip2px(mContext, 6);
                params.height = DensityUtil.dip2px(mContext, 6);
            }
            textView.setLayoutParams(params);
            // textView.setPadding(DensityUtil.dip2px(mContext, 10), 0, DensityUtil.dip2px(mContext, 10), 0);
            textViews[i] = textView;
            if (i == 0) {
                textViews[i].setBackgroundResource(R.mipmap.radio_sel);
            } else {
                textViews[i].setBackgroundResource(R.mipmap.radio);
            }
            layout.addView(textViews[i]);
        }
    }

    boolean isFirst() {
        return this.currentPage == 0;
    }

    boolean isLast() {
        return this.currentPage == pageSize;
    }

    public void selectPage(final int current) {

        for (int i = 0; i < textViews.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 8),
                    DensityUtil.dip2px(mContext, 8));
            params.leftMargin = DensityUtil.dip2px(mContext, 5);
            params.rightMargin = DensityUtil.dip2px(mContext, 5);
            params.topMargin = DensityUtil.dip2px(mContext, 6);
            params.bottomMargin = DensityUtil.dip2px(mContext, 6);
            textViews[current].setBackgroundResource(R.mipmap.radio_sel);
            if (current != i) {
                textViews[i].setBackgroundResource(R.mipmap.radio);
            }
            if (current == i) {
                params.width = DensityUtil.dip2px(mContext, 8);
                params.height = DensityUtil.dip2px(mContext, 8);
                params.gravity = Gravity.CENTER;
                textViews[i].setLayoutParams(params);
            } else {
                params.width = DensityUtil.dip2px(mContext, 6);
                params.height = DensityUtil.dip2px(mContext, 6);
                params.gravity = Gravity.CENTER;
                textViews[i].setLayoutParams(params);
            }

        }
    }

    void turnToNextPage() {
        if (!isLast()) {
            currentPage++;
            selectPage(currentPage);
        }
    }

    void turnToPrePage() {
        if (!isFirst()) {
            currentPage--;
            selectPage(currentPage);
        }
    }
}
