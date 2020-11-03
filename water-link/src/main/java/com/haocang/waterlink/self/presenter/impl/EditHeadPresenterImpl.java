package com.haocang.waterlink.self.presenter.impl;

import android.content.Intent;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.haocang.waterlink.R;
import com.haocang.waterlink.self.iview.EditHeadView;
import com.haocang.waterlink.self.presenter.EditHeadPresenter;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
 * 创建时间：2018/1/259:46
 * 修 改 者：
 * 修改时间：
 */
public class EditHeadPresenterImpl implements EditHeadPresenter {
    /**
     * 为P层提供所需要的数据.
     */
    private EditHeadView editIView;

    /**
     * 获取到选择图片后的uri.
     */
    private Uri resultUri;

    /**
     * 设置缩放比例.
     */
    private static final int MAXSCALE = 5;

    /**
     * 设置动画.
     */
    private static final int TOCROP = 666;

    /**
     * 获取Fragment.
     */
    private Fragment fragment;

    /**
     * 宽和高.
     */
    private static final int WIDTHOFHEIGHT = 1000;

    /**
     * ....
     */
    private static final float THUMBNAIL = 0.5f;

    /**
     * 构造方法.
     *
     * @param editIView 界面所实现的接口.
     */
    public EditHeadPresenterImpl(final EditHeadView editIView) {
        this.editIView = editIView;
        this.fragment = editIView.getFragment();
    }


    /**
     * 处理返回的图片与裁剪之后图片逻辑.
     *
     * @param requestCode 标示符
     * @param resultCode  //标识
     * @param data        数据
     */
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        switch (requestCode) {
//            case RxPhotoTool.GET_IMAGE_FROM_PHONE://选择相册之后的处理
//                if (resultCode == fragment.getActivity().RESULT_OK) {
////                    RxPhotoTool.cropImage(ActivityUser.this, );// 裁剪图片
//                    initUCrop(data.getData());
//                }
//
//                break;
//            case RxPhotoTool.GET_IMAGE_BY_CAMERA://选择照相机之后的处理
//                if (resultCode == fragment.getActivity().RESULT_OK) {
//                   /* data.getExtras().get("data");*/
////                    RxPhotoTool.cropImage(ActivityUser.this, RxPhotoTool.imageUriFromCamera);// 裁剪图片
//                    initUCrop(RxPhotoTool.imageUriFromCamera);
//                }
//
//                break;
//            case RxPhotoTool.CROP_IMAGE://普通裁剪后的处理
//                Glide.with(fragment.getActivity())
//                        .load(RxPhotoTool.cropImageUri)
//                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
//                        .bitmapTransform(new CropCircleTransformation(fragment.getActivity()))
//                        .thumbnail(THUMBNAIL)
//                        .placeholder(R.mipmap.ic_launcher) //默认图片
//                        .priority(Priority.LOW)
//                        .error(R.mipmap.ic_launcher)//异常时候图片
//                        .fallback(R.mipmap.ic_launcher);
////                        .into(editIView.getImageView());
////                RequestUpdateAvatar(new File(RxPhotoTool.getRealFilePath(mContext, RxPhotoTool.cropImageUri)));
//                break;
//
//            case UCrop.REQUEST_CROP://UCrop裁剪之后的处理k
//                if (resultCode == fragment.getActivity().RESULT_OK) {
//                    resultUri = UCrop.getOutput(data);
//                    editIView.setUri(resultUri);
//                    editIView.setFile(roadImageView(resultUri));
//                    RxSPTool.putContent(fragment.getActivity(), "AVATAR", resultUri.toString());
//                } else if (resultCode == UCrop.RESULT_ERROR) {
//                    final Throwable cropError = UCrop.getError(data);
//                }
//                break;
//            case UCrop.RESULT_ERROR://UCrop裁剪错误之后的处理
//                final Throwable cropError = UCrop.getError(data);
//                break;
            default:
                break;
        }
    }


    /**
     * 裁剪图片.
     *
     * @param uri 图片路径.
     */
    private void initUCrop(final Uri uri) {
        //Uri destinationUri = RxPhotoTool.createImagePathUri(this);

        SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        long time = System.currentTimeMillis();
        String imageName = timeFormatter.format(new Date(time));

        Uri destinationUri = Uri.fromFile(new File(fragment.getActivity().getCacheDir(), imageName + ".jpeg"));

        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置隐藏底部容器，默认显示
        //options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(fragment.getActivity(), R.color.color_primary));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(fragment.getActivity(), R.color.color_primarydark));

        //开始设置
        //设置最大缩放比例
        options.setMaxScaleMultiplier(MAXSCALE);
        //设置图片在切换比例时的动画
        options.setImageToCropBoundsAnimDuration(TOCROP);
        //设置裁剪窗口是否为椭圆
        //options.setOvalDimmedLayer(true);
        //设置是否展示矩形裁剪框
        // options.setShowCropFrame(false);
        //设置裁剪框横竖线的宽度
        //options.setCropGridStrokeWidth(20);
        //设置裁剪框横竖线的颜色
        //options.setCropGridColor(Color.GREEN);
        //设置竖线的数量
        //options.setCropGridColumnCount(2);
        //设置横线的数量
        //options.setCropGridRowCount(1);

        UCrop.of(uri, destinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(WIDTHOFHEIGHT, WIDTHOFHEIGHT)
                .withOptions(options)
                .start(fragment.getActivity(), fragment);
    }

    /**
     * 从Uri中加载图片 并将其转化成File文件返回.
     *
     * @return 返回file文件
     */
//    private File roadImageView(final Uri uri) {
//        Glide.with(fragment.getActivity()).
//                load(uri).
//                diskCacheStrategy(DiskCacheStrategy.RESULT).
////                bitmapTransform(new CropCircleTransformation(fragment.getActivity())).//圆形显示
//                thumbnail(0.5f).
//                placeholder(R.mipmap.ic_launcher).
//                priority(Priority.LOW).
//                error(R.mipmap.ic_launcher).
//                fallback(R.mipmap.ic_launcher).
//                into(imageView);
//        return (new File(RxPhotoTool.getImageAbsolutePath(fragment.getActivity(), uri)));
//    }


    private void permissionsProcessing() {
//        PermissionsProcessingUtil.appPermissions(fragment, LibConfig.CAMERA);
    }

}
