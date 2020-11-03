package com.haocang.base.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.haocang.base.config.LibConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

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
 * 创建时间：2018/2/214:26
 * 修 改 者：
 * 修改时间：
 */
public class FileUtils {
    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    public static String isExistDir(String saveDir) throws IOException {
        // 下载位置
        File downloadFile = new File(saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    /**
     * @return 文件下载路径.
     */
    public static String saveUnderFile() {
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) { // SD卡根目录的hello.text
            File file = new File(Environment.getExternalStorageDirectory() + LibConfig.FILE_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
            filePath = Environment.getExternalStorageDirectory() + LibConfig.FILE_PATH;
        } else {
            // 系统下载缓存根目录的hello.text
            File file = new File(Environment.getDownloadCacheDirectory() + LibConfig.FILE_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
            filePath = Environment.getDownloadCacheDirectory() + LibConfig.FILE_PATH;
        }
        return filePath;
    }


    /**
     * 通过uri获取到真实路径.
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 根据视频文件的路径获取视频的缩略图
     * filePath 使用 files.getPath() 或 files.getAbsolutePath() 都可以
     */
    public static Bitmap getVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 根据视频文件的网络路径获取视频的缩略图
     */
    public static Bitmap getNetWorkVideoThumbnail(String filePath) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(filePath, new HashMap<String, String>());
            } else {
                retriever.setDataSource(filePath);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static String saveBitmap(Bitmap bitmap) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "mango");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断文件是否存在
     *
     * @param strFile 文件路径
     * @return
     */
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(f);
            }
//            file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 生成路径
     *
     * @return
     */
    public static String savePatchPath(String path) {
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) { // SD卡根目录的hello.text
            File file = new File(Environment.getExternalStorageDirectory() + path);
            if (!file.exists()) {
                file.mkdirs();
            }
            filePath = Environment.getExternalStorageDirectory() + path;
        } else {
            // 系统下载缓存根目录的hello.text
            File file = new File(Environment.getDownloadCacheDirectory() + path);
            if (!file.exists()) {
                file.mkdirs();
            }
            filePath = Environment.getDownloadCacheDirectory() + path;
        }
        return filePath;
    }

    /**
     * ss
     *
     * @param file
     * @param fileName
     */
    public static void deleteSpecifiedFile(File file, String fileName) {
        File[] files = file.listFiles();
        try {
            for (File f : files) {
                if (!f.getName().contains(fileName)) {
                    f.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getFilesAllName(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files == null) {
            Log.e("error", "空目录");
            return null;
        }
        String s = "";
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains("Android-")) {
                s = files[i].getName();
            }
        }
        return s;
    }

    public static String getFilesPath(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        if (files == null) {
            Log.e("error", "空目录");
            return null;
        }
        String s = "";
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains("Android-")) {
                s = files[i].getPath();
            }
        }
        return s;
    }
}
