package com.haocang.base.bean;

import android.net.Uri;

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
 * 创建时间：2018/3/2714:27
 * 修 改 者：
 * 修改时间：
 */
public class PictureEntity {
    public static final int LOCAL_IMAGE = 0;//todo  本地图片路径
    public static final int IMAGE = 1;//todo 后台图片路径
    public static final int LOCAL_VIDEO = 2;//todo 本地视频路径
    public static final int VIDEO = 3;//todo 后台视频路径

    private Uri imageUri;
    private String localImgPath;//本地图片路径
    private String videoPath;//本地视频路径

    private int type;//0 图片 1视频
    private String thumbnailUrl;//缩略图
    private String videoUrl;//后台视频路径url
    private String netWordVideoPath;//网络视频路径
    private String imgUrl;//后台图片路径
    private int fileType;//todo 0 本地图片 1 后台图片  2本地视频  3后台视频

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }


    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }


    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }


    public String getNetWordVideoPath() {
        return netWordVideoPath;
    }

    public void setNetWordVideoPath(String netWordVideoPath) {
        this.netWordVideoPath = netWordVideoPath;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getLocalImgPath() {
        return localImgPath;
    }

    public void setLocalImgPath(String localImgPath) {
        this.localImgPath = localImgPath;
    }


    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }


}
