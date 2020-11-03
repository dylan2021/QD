package com.haocang.curve.share.presenter.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.haocang.base.utils.QQShareUtil;
import com.haocang.base.utils.WeChatShareUtil;
import com.haocang.base.zxing.util.Util;
import com.haocang.curve.R;
import com.haocang.curve.share.bean.CurveShareEntity;
import com.haocang.curve.share.iview.CurveShareView;
import com.haocang.curve.share.presenter.CurveSharePresenter;

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
 * 创建时间：2018/6/11 11:09
 * 修 改 者：
 * 修改时间：
 */
public class CurveSharePresenterImpl implements CurveSharePresenter {
    private CurveShareView curveShareView;
    /**
     * 是否二维码分享
     */
    private boolean isQRCodeShare = false;

    public CurveSharePresenterImpl(CurveShareView curveShareView) {
        this.curveShareView = curveShareView;
    }

    @Override
    public void initMenu() {
        Context ctx = curveShareView.getActivitys();
        List<CurveShareEntity> list = new ArrayList<>();
        CurveShareEntity entity = new CurveShareEntity();
        entity.setMentName("微信");
        entity.setType(CurveShareEntity.SHARE_WECHAT);
        entity.setIcon(R.drawable.ic_wechat);
        CurveShareEntity entity2 = new CurveShareEntity();
        entity2.setMentName("QQ");
        entity2.setType(CurveShareEntity.SHARE_QQ);
        entity2.setIcon(R.drawable.ic_qq);
        list.add(entity);
        list.add(entity2);
        curveShareView.setShareMenuList(list);
    }

    @Override
    public void onClickQRCode() {
        isQRCodeShare = !isQRCodeShare;
        curveShareView.isQRCodeSelect(isQRCodeShare);
    }

    @Override
    public void startShare(CurveShareEntity entity) {
        if (entity.getType() == CurveShareEntity.SHARE_WECHAT) {
            wechatShare();
        } else if (entity.getType() == CurveShareEntity.SHARE_QQ) {
            qqShare();
        }
    }

    /**
     * 微信分享
     */
    private void wechatShare() {
        if (isQRCodeShare) {
            new WeChatShareUtil(curveShareView.getActivitys())
                    .sharePictureToWeChat(Util.Create2DCode(curveShareView.getUrl()), curveShareView.getActivitys());
        } else {
            new WeChatShareUtil(curveShareView.getActivitys())
                    .setContent(curveShareView.getContents())
                    .setTitle(curveShareView.getTitles())
                    .setUrl(curveShareView.getUrl())
                    .senUrlMsg(BitmapFactory.decodeResource(curveShareView.getActivitys().getResources(), com.haocang.base.R.drawable.ic_share_curve));
        }
    }

    /**
     * qq分享
     */
    private void qqShare() {
        if (isQRCodeShare) {
            new QQShareUtil(curveShareView.getActivitys())
                    .setUrl(curveShareView.getUrl())
                    .shareImage();
        } else {
            new QQShareUtil(curveShareView.getActivitys())
                    .setContents(curveShareView.getContents())
                    .setTitle(curveShareView.getTitles())
                    .setUrl(curveShareView.getUrl())
                    .shareImageText();
        }

    }
}
