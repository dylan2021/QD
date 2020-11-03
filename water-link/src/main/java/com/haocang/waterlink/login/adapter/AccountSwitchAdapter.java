package com.haocang.waterlink.login.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.haocang.base.adapter.BaseAdapter;
import com.haocang.base.adapter.BaseHolder;
import com.haocang.base.bean.UserEntity;
import com.haocang.base.config.AppApplication;
import com.haocang.equiment.ar.ui.RoundArImageView;
import com.haocang.offline.bean.user.OffLineUserEntity;
import com.haocang.waterlink.R;
import com.haocang.waterlink.widgets.ImageViewPlus;

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
 * 创建时间：${DATA} 10:32
 * 修 改 者：
 * 修改时间：
 */
public class AccountSwitchAdapter extends BaseAdapter<OffLineUserEntity> {
    private Context mCtx;

    public AccountSwitchAdapter(int layoutId, Context mCtx) {
        super(layoutId);
        this.mCtx = mCtx;
    }

    @Override
    protected void convert(BaseHolder holder, OffLineUserEntity item, int position) {
        holder.setText(R.id.user_name_tv, item.getName());
        holder.setText(R.id.org_name_tv, item.getOrgName());
        holder.setText(R.id.phone_tv, item.getTel());
        TextView ovelTv = holder.itemView.findViewById(R.id.ovel_tv);
        ImageViewPlus imageView = holder.itemView.findViewById(R.id.rount_img);
        if (!TextUtils.isEmpty(item.getImageUrl())) {
            Glide.with(mCtx).load(item.getImageUrl()).apply(options).into(imageView);
        } else {
            imageView.setImageResource(R.mipmap.icon_account_profilebig);
        }
        if (item.getTel().equals(AppApplication.getInstance().getUserEntity().getTel())) {
            ovelTv.setVisibility(View.VISIBLE);
        } else {
            ovelTv.setVisibility(View.GONE);
        }
// new UserDaoManager().deleteUser(item);
    }

    RequestOptions options = new RequestOptions()
            .placeholder(R.mipmap.icon_account_profilebig)// 正在加载中的图片
            .error(R.mipmap.icon_account_profilebig) // 加载失败的图片
            .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
}
