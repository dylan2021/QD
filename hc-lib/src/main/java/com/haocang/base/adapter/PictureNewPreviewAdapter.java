package com.haocang.base.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.haocang.base.R;
import com.haocang.base.bean.PictureInfo;
import com.haocang.base.utils.ProgressBarDialog;


import java.util.ArrayList;
import java.util.List;


/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区龙兰路277号一号楼9楼
 * 网址：http://www.haocang.com/
 * 标 题：
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/10/15 15:55
 * 修 改 者：
 * 修改时间：
 */
public class PictureNewPreviewAdapter extends PagerAdapter {

    private Context context;
    public List<PictureInfo> fileList = new ArrayList<>();
    private boolean isDeleteDisplay = false;//是否需要显示删除按钮
    private ImageView pictureIv;
    private VideoView videoView;
    private ImageView playIv;
    private FrameLayout frameLayout;
    private ProgressBarDialog dialog;

    public PictureNewPreviewAdapter(Context ctx) {
        context = ctx;
    }

    public void addAll(List<PictureInfo> list) {
        fileList.clear();
        fileList.addAll(list);
    }

    @Override
    public int getCount() {
        return fileList.size();//总共需要创建的页数
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_picture_preview, null);
        container.addView(view);
        initData(position, view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View imageView = (View) object;
        container.removeView(imageView);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return -2;
    }

    private void initData(int position, View view) {
        PictureInfo entity = fileList.get(position);
        pictureIv = view.findViewById(R.id.picture_iv);
        if (entity.getFileType() == PictureInfo.IMAGE) {
            pictureIv.setVisibility(View.VISIBLE);
            Glide.with(context).load(entity.getImgUrl()).apply(options).into(pictureIv);
        } else if (entity.getFileType() == PictureInfo.LOCAL_IMAGE) {
            pictureIv.setVisibility(View.VISIBLE);
            Glide.with(context).load(entity.getLocalImgPath()).apply(options).into(pictureIv);
        } else if (entity.getFileType() == PictureInfo.VIDEO || entity.getFileType() == PictureInfo.LOCAL_VIDEO) {
            frameLayout = view.findViewById(R.id.video_fl);
            frameLayout.setVisibility(View.VISIBLE);
            videoView = view.findViewById(R.id.video_view);
            dialog = new ProgressBarDialog(context);
            playIv = view.findViewById(R.id.plays_iv);
            playVideo(entity);
        }

    }

    /**
     * 加载服务器视频用uri。本地视频直接设置路径即可
     */
    private void playVideo(PictureInfo entity) {
        if (entity.getFileType() == PictureInfo.LOCAL_VIDEO) {
            Uri uri = Uri.parse(entity.getVideoPath());
            videoView.setVideoURI(uri);
        } else {
            playVideoNet(entity);
        }
        playIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.start();
                playIv.setVisibility(View.GONE);
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //播放结束后的动作
                playIv.setVisibility(View.VISIBLE);
            }
        });

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
//                mp.start();
                mp.setLooping(false);
                if (dialog != null) {
                    dialog.cancel();
                }
            }
        });
    }

    /**
     * 播放网络视频， PictureEntity中定义了两个后台视频路径字段
     *
     * @param entity
     */
    private void playVideoNet(PictureInfo entity) {
        if (!TextUtils.isEmpty(entity.getVideoUrl())) {
            videoView.setVideoPath(entity.getVideoUrl());
            dialog.show();
        } else if (!TextUtils.isEmpty(entity.getNetWordVideoPath())) {
            videoView.setVideoPath(entity.getNetWordVideoPath());
            dialog.show();
        }
    }

    public void deleteFiles(boolean flag) {
        isDeleteDisplay = flag;
    }

    public void removes(int position) {
        fileList.remove(position);
        notifyDataSetChanged();
    }

    public void clear() {
        fileList.clear();
    }

    RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.ic_picture_default)// 正在加载中的图片
            .error(R.drawable.ic_picture_default) // 加载失败的图片
            .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略

}
