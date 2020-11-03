package com.haocang.base.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;

import com.haocang.base.R;
import com.haocang.base.bean.PictureEntity;
import com.haocang.base.config.LibConfig;

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
 * 创 建 者：he
 * 创建时间：2018/3/2714:26
 * 修 改 者：
 * 修改时间：
 */
public class ImageViewAdapter extends BaseAdapter<PictureEntity> {

    private Context ctx;

    public ImageViewAdapter(Context ctx) {
        super(R.layout.adapter_image);
        this.ctx = ctx;
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, int position) {
        ImageView imageView = (ImageView)holder.itemView.findViewById(R.id.image_iv);
        if (position >= LibConfig.MAXIMAGE) {//图片已选完时，隐藏添加按钮
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
        Uri uri = mList.get(position).getImageUri();
//        Glide.with(ctx).load(uri).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
    }

    private void setFirst(BaseHolder holder, PictureEntity item) {
    }

    private void setSecound(BaseHolder holder) {
    }

}
