package com.haocang.base.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.haocang.base.R;
import com.haocang.base.config.LibConfig;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

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
 * 创建时间：2018/6/11 16:15
 * 修 改 者：
 * 修改时间：
 */
public class WeChatShareUtil {
    private Activity activity;
    private IWXAPI wxApi;
    private static int THUMB_SIZE = 200;
    private String WX_APP_ID = "wxed2472863a1f722d";
    private String title;
    private String content;
    private String url;

    public WeChatShareUtil(Activity activity) {
        this.activity = activity;
        WX_APP_ID = new MetaDataUtil().getMetaData(activity, "wechatShareKey", WX_APP_ID);
        wxApi = WXAPIFactory.createWXAPI(activity, WX_APP_ID);

        wxApi.registerApp(WX_APP_ID);
    }

    public WeChatShareUtil setUrl(String url) {
        this.url = url;
        return this;
    }

    public WeChatShareUtil setTitle(String title) {
        this.title = title;
        return this;
    }

    public WeChatShareUtil setContent(String content) {
        this.content = content;
        return this;
    }


    public void senUrlMsg(Bitmap thumb) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = content;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(thumb, 120, 120, true);// 压缩图片大小 最大支持 30kb以内，不然无法分享
        thumb.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        IWXAPI api = WXAPIFactory.createWXAPI(activity, WX_APP_ID);
        api.sendReq(req);
    }

    /**
     * 分享图片到微信
     *
     * @param bmp
     * @param activity
     */
    public void sharePictureToWeChat(Bitmap bmp, Activity activity) {

        WXImageObject imgobj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgobj;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, (int) (THUMB_SIZE * getBitmapHBRate(bmp)), true);// Bitmap.createBitmap(bmp,THUMB_SIZE,THUMB_SIZE,true);
        bmp.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        IWXAPI api = WXAPIFactory.createWXAPI(activity, WX_APP_ID);
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static double getBitmapHBRate(Bitmap bmp) {
        if (bmp != null) {
            return bmp.getHeight() * 1.0 / bmp.getWidth();
        }
        return 1;

    }
}
