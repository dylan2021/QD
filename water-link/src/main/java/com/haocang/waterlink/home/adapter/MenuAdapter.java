package com.haocang.waterlink.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.base.adapter.MyBaseAdapter;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.MenuEntity;

import ren.solid.skinloader.util.L;

/**
 * 菜单适配器.
 */
public class MenuAdapter extends BaseAdapter<MenuEntity> {
    private Context ctx;

    public MenuAdapter(int layoutId, Context ctx) {
        super(layoutId);
        this.ctx = ctx;
    }

    @Override
    protected void convert(BaseHolder holder, MenuEntity entity, int position) {
        ImageView iconIv = holder.itemView.findViewById(R.id.icon_iv);
        TextView nameTv = holder.itemView.findViewById(R.id.name_tv);
        LinearLayout iconLl = holder.itemView.findViewById(R.id.icon_ll);
        if (entity.getId() == 0) {
            //更多
            iconLl.setBackgroundResource(R.mipmap.icon_home_menu_more);
            Glide.with(ctx).load(R.mipmap.icon_home_menu_more).apply(options).into(iconIv);
            nameTv.setTextColor(Color.parseColor("#0CABDF"));
        } else if (!TextUtils.isEmpty(entity.getIcon()) && !entity.getIcon().equals("0")) {
            iconLl.setBackgroundResource(R.mipmap.icon_home_menu_bg);
            iconIv.setImageResource(Integer.valueOf(entity.getIcon()));
//            Glide.with(ctx).load( entity.getIcon()).apply(options).into(iconIv);
            nameTv.setTextColor(Color.parseColor("#58637C"));
        } else if (!TextUtils.isEmpty(entity.getIconUrl())) {
            iconLl.setBackgroundResource(R.mipmap.icon_home_menu_bg);
            Glide.with(ctx).load(entity.getIconUrl()).apply(options).into(iconIv);
            nameTv.setTextColor(Color.parseColor("#58637C"));
        } else {
            iconLl.setBackgroundResource(R.mipmap.icon_home_menu_bg);
            Glide.with(ctx).load(R.mipmap.ic_launcher).apply(options).into(iconIv);
            nameTv.setTextColor(Color.parseColor("#58637C"));
        }
        String name = entity.getName();
        if (TextUtils.isEmpty(name)) {
            nameTv.setText("");
        } else {
            nameTv.setText(name);
        }
    }

    RequestOptions options = new RequestOptions()
            .placeholder(R.mipmap.ic_launcher)// 正在加载中的图片
            .error(R.mipmap.ic_launcher); // 加载失败的图片
//            .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
}
