package com.haocang.base.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import androidx.fragment.app.Fragment;
import android.util.Log;

import java.io.IOException;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.utils.ImageCaptureManager;

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
 * 创建时间：2018/3/2715:54
 * 修 改 者：
 * 修改时间：
 */
public class PictureUtils {

    /**
     * 打开相册，包含照相功能
     *
     * @param fragment
     * @param pictureMax
     */
    public static void openPicture(Fragment fragment, int pictureMax) {
        PhotoPicker.builder()
                .setPhotoCount(pictureMax)
                .setShowCamera(true)
                .setShowGif(false)
                .setPreviewEnabled(false)
                .setGridColumnCount(4)
                .start(fragment.getActivity(), fragment, PhotoPicker.REQUEST_CODE);

    }

    /**
     * 打开相册， 不包含相机.
     *
     * @param pictureMax
     */
    public static void openAlbum(Activity activity, int pictureMax) {
        PhotoPicker.builder()
                .setPhotoCount(pictureMax)
                .setShowCamera(false)
                .setShowGif(false)
                .setPreviewEnabled(false)
                .setGridColumnCount(4)
                .start(activity, PhotoPicker.REQUEST_CODE);
    }


    /**
     */
    public static void openCamera(Activity activity) {
        ImageCaptureManager captureManager = new ImageCaptureManager(activity);
        try {
            Intent intent = captureManager.dispatchTakePictureIntents();
            activity.startActivityForResult(intent, 2001);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            Log.e("PhotoPickerFragment", "No Activity Found to handle Intent", e);
        }
    }
}
