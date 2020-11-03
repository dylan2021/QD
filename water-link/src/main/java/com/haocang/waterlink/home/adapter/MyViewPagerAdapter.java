package com.haocang.waterlink.home.adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.GridView;

import java.util.Map;

/**
 * 实现ViewPager页卡切换的适配器.
 *
 * @author Administrator
 */
public class MyViewPagerAdapter extends PagerAdapter {
    /**
     * 存储数据.
     */
    private Map<Integer, GridView> map;

    /**
     * 构造方法.
     *
     * @param context 上下文参数.
     * @param map     集合.
     */
    public MyViewPagerAdapter(final Context context, final Map<Integer, GridView> map) {
        this.map = map;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return map.size();
    }

    @Override
    public boolean isViewFromObject(final View arg0, final Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public Object instantiateItem(final View view, final int position) {
        if (view != null && map != null && map.size() > position) {
            ((ViewPager) view).addView(map.get(position));
        }
        return map.get(position);
    }

    @Override
    public void destroyItem(final View arg0, final int arg1, final Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

}
