package com.haocang.waterlink.myapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.MenuEntity;

public class MyAppSearchAdapter extends BaseAdapter<MenuEntity> {
    private Context ctx;

    public MyAppSearchAdapter(int layoutId, Context ctx) {
        super(layoutId);
        this.ctx = ctx;

    }

    @Override
    protected void convert(BaseHolder holder, MenuEntity entity, int position) {
        ImageView iconIv = holder.itemView.findViewById(R.id.icon_iv);
        TextView nameTv = holder.itemView.findViewById(R.id.name_tv);
        if (!TextUtils.isEmpty(entity.getIcon())) {
            Glide.with(ctx).load(entity.getIcon()).apply(options).into(iconIv);
        } else {
            Glide.with(ctx).load(R.mipmap.ic_launcher).apply(options).into(iconIv);
        }
        if (TextUtils.isEmpty(entity.getName())) {
            nameTv.setText("");
        } else {
            nameTv.setText(entity.getName());
        }
    }

    RequestOptions options = new RequestOptions()
            .placeholder(R.mipmap.ic_launcher)// 正在加载中的图片
            .error(R.mipmap.ic_launcher) // 加载失败的图片
            .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略

}
