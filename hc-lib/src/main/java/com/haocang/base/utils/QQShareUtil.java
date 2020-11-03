package com.haocang.base.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.haocang.base.R;
import com.haocang.base.zxing.util.Util;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

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
 * 创建时间：2018/6/11 13:46
 * 修 改 者：
 * 修改时间：
 */
public class QQShareUtil {
    private Activity activity;
    public static Tencent mTencent;
    public static String mAppid = "101735975";//申请的id
    public String url;//链接
    private String title;
    private String contents;
    public String localPath;//本地图片路径

    public QQShareUtil(Activity activity) {
        this.activity = activity;
        mAppid = new MetaDataUtil().getMetaData(activity, "qqShareKey", mAppid);
        if (mTencent == null) {
            mTencent = Tencent.createInstance(mAppid, activity);
        }
    }

    public QQShareUtil setTitle(String title) {
        this.title = title;
        return this;
    }

    public QQShareUtil setContents(String contetns) {
        this.contents = contetns;
        return this;
    }

    /**
     * 设置链接路径
     *
     * @param url
     * @return
     */
    public QQShareUtil setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 设置本地图片路径
     *
     * @param localUrl
     * @return
     */
    public QQShareUtil setLocalImge(String localUrl) {
        localPath = localUrl;
        return this;
    }

    /**
     * 分享图文
     */
    public void shareImageText() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);//分享的类型
        params.putString(QQShare.SHARE_TO_QQ_TITLE, title);//分享标题
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, contents);//要分享的内容摘要
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);//内容地址
//        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530250401&di=86638c272d8e2ba29b9700b4bd02bcdb&imgtype=jpg&er=1&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F019cd6554bbff1000001bf720201b3.jpg");//分享的网络图片URL
        Bitmap bmp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_share_curve);
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, Util.saveImage(bmp).getPath());//分享本地图片url
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "Mango");//应用名称
//        mTencent.shareToQQ(activity, params, qqShareListener);
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != mTencent) {
                    mTencent.shareToQQ(activity, params, qqShareListener);
                }
            }
        });

    }


    public void shareImage() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);//分享图片
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, Util.saveImage(Util.Create2DCode(url)).getPath());//分享本地图片url
        params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "Mango");//应用名称
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != mTencent) {
                    mTencent.shareToQQ(activity, params, qqShareListener);
                }
            }
        });

    }

    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            //分享取消
        }

        @Override
        public void onComplete(Object response) {
            // 分享成功
        }

        @Override
        public void onError(UiError e) {
            // 分享失败
        }
    };

}
