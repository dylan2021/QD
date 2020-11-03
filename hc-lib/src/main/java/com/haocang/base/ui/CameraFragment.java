package com.haocang.base.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ClickListener;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.haocang.base.R;
import com.haocang.base.config.LibConfig;
import com.haocang.base.utils.FileUtils;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.ToastUtil;

import java.io.ByteArrayOutputStream;
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
 * 创建时间：2018/4/2317:24
 * 修 改 者：
 * 修改时间：
 */
public class CameraFragment extends Fragment
        implements ErrorListener, JCameraListener, PermissionsProcessingUtil.OnPermissionsCallback {
    private JCameraView jCameraView;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_camera, null);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        PermissionsProcessingUtil.setPermissions(this, LibConfig.STORAGE, this);
        jCameraView = view.findViewById(R.id.jcameraview);
        //设置视频保存路径
        jCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "JCamera");
        //设置只能录像或只能拍照或两种都可以（默认两种都可以）
        jCameraView.setFeatures(JCameraView.BUTTON_STATE_ONLY_CAPTURE);
        //设置视频质量
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);
        jCameraView.setErrorLisenter(this);
        jCameraView.setJCameraLisenter(this);
        jCameraView.setLeftClickListener(new ClickListener() {
            @Override
            public void onClick() {
                getActivity().finish();
            }
        });
        jCameraView.setRightClickListener(new ClickListener() {
            @Override
            public void onClick() {
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        jCameraView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        jCameraView.onPause();
    }

    @Override
    public void onError() {
        //打开Camera失败回调
    }

    @Override
    public void AudioPermissionError() {
        //没有录取权限回调
    }

    @Override
    public void captureSuccess(final Bitmap bitmap) {
//获取图片bitmap
        Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra("picturePath", FileUtils.saveBitmap(bitmap));
        getActivity().setResult(1122, intent);
        getActivity().finish();
    }

    @Override
    public void recordSuccess(String url, Bitmap firstFrame) {
        //获取视频路径
        Intent intent = new Intent();
        //把返回数据存入Intent
        intent.putExtra("videoPath", url);
        intent.putExtra("videoThumbnail", tranBitmap(firstFrame));
        //设置返回数据
        getActivity().setResult(getActivity().RESULT_OK, intent);
        getActivity().finish();
    }

    private byte[] tranBitmap(Bitmap firstFrame) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        firstFrame.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bitmapByte = baos.toByteArray();
        return bitmapByte;
    }

    @Override
    public void callBack(boolean flag, String permission) {
        if (!flag) {
            ToastUtil.makeText(getActivity(), "此功能必须开启存储权限");
            getActivity().finish();
        }
    }

}
