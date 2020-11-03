package com.haocang.waterlink.myapp.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.MenuEntity;

public class MyAppHomeAdapter extends BaseAdapter<MenuEntity> {
    private Context ctx;

    public MyAppHomeAdapter(int layoutId, Context ctx) {
        super(layoutId);
        this.ctx = ctx;
    }

    @Override
    protected void convert(BaseHolder holder, MenuEntity item, int position) {
        ImageView iconIv = holder.itemView.findViewById(R.id.home_app_iv);
//        if(item.getId()==0){
//            iconIv.setBackgroundResource(R.mipmap.icon_home_menu_more);
//        }else{
        Glide.with(ctx).load(item.getIcon()).apply(options).into(iconIv);
//        }

    }

    private RequestOptions options = new RequestOptions()
            .placeholder(com.haocang.equiment.R.drawable.ic_picture_default)// 正在加载中的图片
            .error(com.haocang.equiment.R.drawable.ic_picture_default) // 加载失败的图片
            .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略


}
