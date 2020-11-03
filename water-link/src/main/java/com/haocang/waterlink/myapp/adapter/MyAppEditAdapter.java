package com.haocang.waterlink.myapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.MenuEntity;


public class MyAppEditAdapter extends BaseAdapter<MenuEntity> {
    private Context ctx;

    public MyAppEditAdapter(int layoutId, Context ctx) {
        super(layoutId);
        this.ctx = ctx;
    }
    //    @Override
//    public int getItemCount() {
//        return mList.size() <= 11 ? mList.size() : 11;
//    }

    @Override
    protected void convert(BaseHolder holder, MenuEntity entity, int position) {
        ImageView iconIv = holder.itemView.findViewById(R.id.icon_iv);
        TextView nameTv = holder.itemView.findViewById(R.id.name_tv);
        ImageView selectIv = holder.itemView.findViewById(R.id.select_iv);
        LinearLayout iconLl = holder.itemView.findViewById(R.id.icon_ll);
        if (entity.getId() == 0) {
            //添加
            selectIv.setVisibility(View.GONE);
            iconLl.setBackgroundResource(R.mipmap.icon_home_menu_edit_add);
            iconIv.setBackgroundResource(0);
            nameTv.setTextColor(Color.parseColor("#0CABDF"));
        } else if (!TextUtils.isEmpty(entity.getIcon())) {
            selectIv.setVisibility(View.VISIBLE);
            iconLl.setBackgroundResource(R.mipmap.icon_home_menu_bg);
            Glide.with(ctx).load(entity.getIcon()).apply(options).into(iconIv);
            nameTv.setTextColor(Color.parseColor("#58637C"));
        } else {
            iconLl.setBackgroundResource(R.mipmap.icon_home_menu_bg);
            selectIv.setVisibility(View.VISIBLE);
            Glide.with(ctx).load(R.mipmap.ic_launcher).apply(options).into(iconIv);
            nameTv.setTextColor(Color.parseColor("#58637C"));
        }
        if (TextUtils.isEmpty(entity.getName())) {
            nameTv.setText("");
        } else {
            nameTv.setText(entity.getName());
        }
        if (entity.isShowHomepage()) {
            selectIv.setBackgroundResource(R.mipmap.icon_home_menu_delete);
        } else {
            selectIv.setBackgroundResource(R.mipmap.icon_home_menu_add);
        }
    }

    public void removeItem(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    public String getIds() {
        String ids = "";
        if (mList != null && mList.size() > 0) {
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getId() > 0) {
                    ids += mList.get(i).getId() + ",";
                }
            }
        }
        if (!TextUtils.isEmpty(ids)) {
            ids = ids.substring(0, ids.length() - 1);
        }
        return ids;
    }

    public void add(int index, MenuEntity entity) {
        mList.add(index, entity);
    }

    RequestOptions options = new RequestOptions()
            .placeholder(R.mipmap.ic_launcher)// 正在加载中的图片
            .error(R.mipmap.ic_launcher) // 加载失败的图片
            .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略

}
