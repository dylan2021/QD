package com.haocang.base.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.haocang.base.R;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.utils.ProgressBarDialog;
import com.haocang.base.widgets.CustomerVideoView;

import java.io.File;

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
 * 创建时间：2018/4/2516:22
 * 修 改 者：
 * 修改时间：
 */
@Route(path = ArouterPathConstants.Common.PICTURE_PREVIEW)
public class PicturePreviewFragment extends Fragment {
    private ImageView pictureIv;

    private CustomerVideoView videoView;
    private ProgressBarDialog dialog;


    private ImageView deleteIv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_picture_preview, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        deleteIv = view.findViewById(R.id.common_iv);
        if (!TextUtils.isEmpty(getDisplayDelete())) {
            deleteIv.setVisibility(View.VISIBLE);
            deleteIv.setBackgroundResource(R.drawable.ic_delete_s);
            deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(getPicturePath())) {
                        deleteFile(getPicturePath());
                    } else if (!TextUtils.isEmpty(getVideoPath())) {
                        deleteFile(getVideoPath());
                    }
                    getActivity().finish();
                }
            });
        }
        pictureIv = view.findViewById(R.id.picture_iv);
        if (!TextUtils.isEmpty(getPicturePath())) {
            pictureIv.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(getPicturePath()).apply(options).into(pictureIv);
        } else if (!TextUtils.isEmpty(getVideoPath()) || !TextUtils.isEmpty(getVideoNetworkPath())) {
            dialog = new ProgressBarDialog(getActivity());
            dialog.show();
            videoView = view.findViewById(R.id.video_view);
            playVideo();
            videoView.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 加载服务器视频用uri。本地视频直接设置路径即可
     */
    private void playVideo() {
        if (!TextUtils.isEmpty(getVideoNetworkPath())) {
            Uri uri = Uri.parse(getVideoNetworkPath());
            videoView.setVideoURI(uri);
        } else {
            videoView.setVideoPath(getVideoPath());
        }
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                dialog.cancel();
            }
        });
    }

    private String getPicturePath() {
        return getActivity().getIntent().getStringExtra("picturePath");
    }

    private String getVideoNetworkPath() {
        return getActivity().getIntent().getStringExtra("networkVideoPath");
    }

    private String getVideoPath() {
        return getActivity().getIntent().getStringExtra("videoPath");
    }

    /**
     * 不为空的时候 删除按钮需要显示
     *
     * @return
     */
    private String getDisplayDelete() {
        return getActivity().getIntent().getStringExtra("displayDelete");
    }


    private void deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
        }
    }


    RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.ic_picture_default)// 正在加载中的图片
            .error(R.drawable.ic_picture_default) // 加载失败的图片
            .diskCacheStrategy(DiskCacheStrategy.ALL); // 磁盘缓存策略
}

