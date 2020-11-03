package com.haocang.waterlink.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import com.haocang.base.config.LibConfig;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.FileUtils;
import com.haocang.waterlink.R;

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
 * 创建时间：2018/2/210:56
 * 修 改 者：
 * 修改时间：
 */
public class DownloadService extends Service {
    /**
     * APK的下载路径.
     */
    private String apkDownloadUrl;
    /**
     * 任务栏通知.
     */
    private NotificationManager mNotificationManager;
    /**
     * 任务栏通知.
     */
    private Notification mNotification;

    /**
     * 下载的进度最大数.
     */
    private static final int PROGRESSMAX = 100;

    /**
     * 下载进度为10的倍数才刷新ui一次.
     */
    private static final int NUMBERS = 10;
    /**
     * progress会重复等于某个数值。刷新次数太频繁会影响UI.
     */
    private int index = 0;

    /**
     * 初始化.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

    }


    /**
     * 执行.
     *
     * @param intent  s
     * @param flags   s
     * @param startId s
     *                * @return
     */
    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        if (intent == null) {
            notifyMsg("温馨提醒", "文件下载失败", 0);
            stopSelf();
        }
        apkDownloadUrl = intent.getStringExtra("apkUrl"); //获取下载APK的链接
        downloadFile(apkDownloadUrl); //下载APK
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 实现父类的方法.
     *
     * @param intent .
     * @return
     */
    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }


    /**
     * 刷新通知栏.
     *
     * @param title    标题.
     * @param content  内容
     * @param progress 进度
     */
    private void notifyMsg(final String title, final String content, final int progress) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this); //为了向下兼容，这里采用了v7包下的NotificationCompat来构造
        builder.setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).setContentTitle(title);
        if (progress > 0 && progress < PROGRESSMAX) {
            //下载进行中
            builder.setProgress(PROGRESSMAX, progress, false);
        } else {
            builder.setProgress(PROGRESSMAX, PROGRESSMAX, false);
        }
        builder.setAutoCancel(true);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentText(content);
        if (progress >= PROGRESSMAX) {
            //下载完成
            builder.setContentIntent(getInstallIntent());
        }
        mNotification = builder.build();
        mNotificationManager.notify(0, mNotification);


    }

    /**
     * 安装apk文件.
     *
     * @return 返回一个对象.
     */
    private PendingIntent getInstallIntent() {

        File file = new File(FileUtils.saveUnderFile() + LibConfig.APP_NAME);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (file != null) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(this, "com.haocang.hsdd.fileprovider", file);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }


    /**
     * 下载apk文件.
     *
     * @param url 路径
     */
    private void downloadFile(final String url) {


        new OkHttpClientManager().downloadBuild(url, FileUtils.saveUnderFile(), "Mango",
                new OkHttpClientManager.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess() {

                    }

                    @Override
                    public void onDownloading(final int progress) {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        String reuslt = progress + "";
                        bundle.putString("result", reuslt);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onDownloadFailed() {

                    }
                });

    }


    /**
     * 刷新ui.
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int progress = bundle.getString("result") != null ? Integer.parseInt(bundle.getString("result")) : 0;
            //progress*100为当前文件下载进度，total为文件大小
            if (progress % NUMBERS == 0) {
                //避免频繁刷新View，这里设置每下载10%提醒更新一次进度
                if (index == progress) {
                    return;
                }
                if (progress >= PROGRESSMAX) {
                    notifyMsg("温馨提醒", "文件下载已完成,点击安装", PROGRESSMAX);
                } else {
                    index = progress;
                    notifyMsg("温馨提醒", "文件正在下载..", progress);
                }
            }
        }
    };

}
