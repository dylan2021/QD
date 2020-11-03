package com.haocang.base.utils;

import android.content.Context;

import com.haocang.base.http.OkHttpClientManager;

import java.io.File;

import ren.solid.library.utils.FileUtils;
import ren.solid.skinloader.listener.ILoaderListener;
import ren.solid.skinloader.load.SkinManager;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区龙兰路277号一号楼9楼
 * 网址：http://www.haocang.com/
 * 标 题： todo 皮肤分为两种方式1.加载本地内置皮肤，2.加载网络皮肤
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：hy
 * 创建时间：2018/11/6 9:24
 * 修 改 者：
 * 修改时间：
 */

public class SkinUtil {

    /**
     * 皮肤下载
     * url 下载地址
     * dirFileName 文件名称
     */
    public static void skinDownload(Context mCtx, String url, String dirFileName) {
        String dirFile = createDirFile();
        if (com.haocang.base.utils.FileUtils.fileIsExists(dirFile + File.separator + dirFileName)) {
            //todo 如果本地有皮肤包 则不在进行下载
            skinChange(mCtx, dirFile + File.separator + dirFileName);
            return;
        }
        new OkHttpClientManager()
                .downloadBuild(url, dirFile, dirFileName, new OkHttpClientManager.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess() {
                        skinChange(mCtx, dirFile);
                    }

                    @Override
                    public void onDownloading(int progress) {

                    }

                    @Override
                    public void onDownloadFailed() {

                    }
                });
    }

    /**
     * 创建皮肤文件夹
     */
    public static String createDirFile() {
        return com.haocang.base.utils.FileUtils.saveUnderFile();
    }

    private static void skinChange(Context mCtx, String skinFullPath) {
        File skin = new File(skinFullPath);
        if (!skin.exists()) {
            ToastUtil.makeText(mCtx, "切换皮肤失败");
            return;
        }
        SkinManager.getInstance().load(skin.getAbsolutePath(), new ILoaderListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess() {
                ToastUtil.makeText(mCtx, "皮肤切换成功");
            }

            @Override
            public void onFailed() {

            }
        });
    }


    /**
     * todo assets文件夹下的皮肤
     *
     * @param mCtx skinName 文件名称 和assets名称保持一致
     */
    public static void localSkinChange(Context mCtx, String skinName) {
        String skinDirPath = createDirFile() + File.separator + skinName;
        FileUtils.moveRawToDir(mCtx, skinName, skinDirPath);//把assets下的文件复制到sd卡下面
        File skin = new File(skinDirPath);
        if (!skin.exists()) {
            ToastUtil.makeText(mCtx, "切换皮肤失败");
            return;
        }
        SkinManager.getInstance().load(skin.getAbsolutePath(), new ILoaderListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess() {
                ToastUtil.makeText(mCtx, "皮肤切换成功");
            }

            @Override
            public void onFailed() {

            }
        });
    }


}
