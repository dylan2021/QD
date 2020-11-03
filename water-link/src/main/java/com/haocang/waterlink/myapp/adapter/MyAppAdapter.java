package com.haocang.waterlink.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.tabs.TabLayout;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.utils.ToastUtil;
import com.haocang.waterlink.R;
import com.haocang.waterlink.home.bean.MenuEntity;

import java.util.ArrayList;
import java.util.List;

public class MyAppAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final static int APP_TYPE = 0;//应用类型
    public final static int APP_CONTENT = 1;//应用内容
    private Context ctx;
    public List<MenuEntity> mList = new ArrayList<>();

    public void add(final MenuEntity entity) {
        mList.add(entity);
    }

    public void addAll(final List<MenuEntity> list) {
        mList.addAll(list);
    }

    public void clear() {
        mList.clear();
    }


    public MyAppAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == APP_TYPE) {
            View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_menu_type, parent, false);
            return new AppTypeHolder(view);
        } else {
            View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_menu, parent, false);
            return new AppContextHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AppTypeHolder) {
            setAppType((AppTypeHolder) holder, position);
        } else if (holder instanceof AppContextHolder) {
            setAppContent((AppContextHolder) holder, position);
        }
    }

    private RequestOptions options = new RequestOptions()
//            .placeholder(com.haocang.equiment.R.drawable.ic_picture_default)// 正在加载中的图片
            .error(com.haocang.equiment.R.drawable.ic_picture_default) // 加载失败的图片
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC); // 磁盘缓存策略


    private void setAppContent(final AppContextHolder appContextHolder, final int position) {
        final MenuEntity entity = mList.get(position);
        appContextHolder.nameTv.setText(entity.getName());
        Glide.with(ctx).load(entity.getIcon()).apply(options).into(appContextHolder.iconIv);
        appContextHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemOnclick.onClick(v, position, mList.get(position));
            }
        });
    }

    private void setAppType(final AppTypeHolder appTypeHolder, final int position) {
        final MenuEntity entity = mList.get(position);
        appTypeHolder.nameTv.setText(entity.getName());
    }


    /**
     * 我的菜单
     */
    class AppContextHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;
        private ImageView iconIv;

        private AppContextHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name_tv);
            iconIv = itemView.findViewById(R.id.icon_iv);
        }
    }

    class AppTypeHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;

        private AppTypeHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name_tv);
        }
    }

    class AppHeadHolder extends RecyclerView.ViewHolder {
        private TabLayout tabLayout;

        private AppHeadHolder(View itemView) {
            super(itemView);
            tabLayout = itemView.findViewById(R.id.tablayout);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    @Override
    public int getItemViewType(final int position) {
        int type = mList.get(position).getAppType();
        if (type == APP_TYPE) {
            return APP_TYPE;
        } else {
            return APP_CONTENT;
        }
    }

    private BaseAdapter.OnItemClickListener onItemOnclick;

    public void setOnItemOnclick(BaseAdapter.OnItemClickListener onItemOnclick) {
        this.onItemOnclick = onItemOnclick;
    }

}