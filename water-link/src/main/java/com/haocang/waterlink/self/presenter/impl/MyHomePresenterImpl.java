package com.haocang.waterlink.self.presenter.impl;

import android.content.Intent;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;

import com.haocang.base.bean.FileEntity;
import com.haocang.base.utils.GetEntityListener;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.utils.UploadUtil;
import com.haocang.waterlink.self.iview.MyHomeView;
import com.haocang.waterlink.self.model.SelfModel;
import com.haocang.waterlink.self.model.impl.SelfModelImpl;
import com.haocang.waterlink.self.presenter.MyHomePresenter;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

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
 * 创建时间：2018/1/3011:16
 * 修 改 者：
 * 修改时间：
 */
public class MyHomePresenterImpl
        implements MyHomePresenter, UploadUtil.UploadSuccess {
    /**
     * 为P层提供数据.
     */
    private MyHomeView myHomeView;
    /**
     * 访问数据层.
     */
    private SelfModel selfModel;

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
     * 构造方法.
     *
     * @param myHomeView .
     */
    public MyHomePresenterImpl(final MyHomeView myHomeView) {
        this.myHomeView = myHomeView;
        selfModel = new SelfModelImpl();
        fragment = myHomeView.getFragment();
    }


    @Override
    public void uploadSuccess(final List<FileEntity> fileList) {
        String imgUrl = "";
        for (FileEntity file : fileList) {
            imgUrl = imgUrl + file.getFullPath() + ",";
        }
        if (!TextUtils.isEmpty(imgUrl)) {
            imgUrl = imgUrl.substring(0, imgUrl.length() - 1);
        }
        selfModel.submitImage(myHomeView.getContexts(), imgUrl, new GetEntityListener<String>() {
            @Override
            public void success(final String entity) {
                ToastUtil.makeText(myHomeView.getContexts(), "上传头像成功");
                myHomeView.setSuccess();
            }

            @Override
            public void fail(final String err) {

            }
        });
    }

    @Override
    public void uploadError() {

    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                if (photos != null && photos.size() > 0) {
                    File file = new File(photos.get(0));
                    File newFile = CompressHelper.getDefault(myHomeView.getContexts()).compressToFile(file);
                    List<String> list = new ArrayList<>();
                    list.add(newFile.getPath());
                    new UploadUtil(myHomeView.getContexts()).setUploadData(list).setUploadSuccess(this).startUploadFile();
                }

            }
        }
    }


//    /**
//     * 成功.
//     *
//     * @param entity
//     */
//    @Override
//    public void success(final UserEntity entity) {
//        myHomeView.setAccountData(entity);
//    }

//    @Override
//    public void fail(final String err) {
//
//    }


}
