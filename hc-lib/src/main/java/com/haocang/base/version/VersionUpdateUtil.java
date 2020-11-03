package com.haocang.base.version;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.allenliu.versionchecklib.callback.APKDownloadListener;
import com.allenliu.versionchecklib.utils.AppUtils;
import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.NotificationBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadingDialogListener;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.bigkoo.alertview.AlertView;
import com.haocang.base.R;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.ErrorCode;
import com.haocang.base.config.LibConfig;
import com.haocang.base.http.OkHttpClientManager;
import com.haocang.base.utils.BaseDialog;
import com.haocang.base.utils.FileUtils;
import com.haocang.base.version.presenter.VersionUpdatePresenter;
import com.haocang.base.version.presenter.impl.VersionUpdatePresenterImpl;
import com.haocang.base.version.view.VersionUpdateView;
import com.nothome.delta.GDiffPatcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


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
 * 创建时间：2018/8/10 14:48
 * 修 改 者：
 * 修改时间：
 */
public class VersionUpdateUtil implements VersionUpdateView {
    private Context ctx;
    private String size;
    /**
     * 是否需要强制更新
     */
    private boolean isForceUpdate = false;

    /**
     * apk下载路径
     */
    private String apkDownloadPath;
    /**
     * 内容描述
     */
    private String apkUpdateContent;

    private VersionUpdatePresenter presenter;


    private TextView tvProgress;
    private ProgressBar progressBar;
    private String versionNo;

    public interface SendMessge {
        void sendMessge();
    }

    public SendMessge sendMessge;

    public VersionUpdateUtil sendMessge(SendMessge sendMessge) {
        this.sendMessge = sendMessge;
        return this;
    }


    public VersionUpdateUtil(Context ctx) {
        this.ctx = ctx;
        presenter = new VersionUpdatePresenterImpl(this);
    }

    public static VersionUpdateUtil getInstance(Context ctx) {
        return new VersionUpdateUtil(ctx);
    }

    /**
     * 是否有新的版本
     */
    public void isNewVersion(final String tag) {
        presenter.getVersionData(tag);
    }

    public void isNewVersionOld(final String tag) {
        presenter.getVersionDataOld(tag);
    }


    @Override
    public Context getContexts() {
        return ctx;
    }

    @Override
    public void showVersionDialog() {
        BaseDialog baseDialog = new BaseDialog(ctx, R.style.BaseDialog, R.layout.dialog_version);
        TextView textView = baseDialog.findViewById(R.id.tv_msg);
        TextView versionNoTv = baseDialog.findViewById(R.id.tv_version_no);
        TextView sizeTv = baseDialog.findViewById(R.id.size_tv);
        sizeTv.setText(size);
        versionNoTv.setText(versionNo);
        textView.setText(apkUpdateContent);
        baseDialog.findViewById(R.id.versionchecklib_version_dialog_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 差分包保存的文件夹
                 */
                String patchPath = FileUtils.savePatchPath(LibConfig.FILE_PATH);
                /**
                 * 开始下载
                 */
                downApk(apkDownloadPath, patchPath, versionNo + ".apk");
                /**
                 * 关闭升级弹框
                 */
                baseDialog.dismiss();
            }
        });
        setForceUpdate(baseDialog);
        baseDialog.show();
    }


    @Override
    public void isForceUpdate(boolean isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }

    @Override
    public void apkUpdateContent(String content) {
        this.apkUpdateContent = content;
    }

    @Override
    public void getVersionSize(String size) {
        this.size = size;

    }

    @Override
    public void apkDownLoadPath(String path) {
        this.apkDownloadPath = path;
    }

    @Override
    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    @Override
    public void senHaveVersion(boolean b) {

    }

    @Override
    public void noNewVersion() {
        if (sendMessge != null) {
            sendMessge.sendMessge();
        }
    }

    /**
     * 是否强制升级
     *
     * @param baseDialog
     */
    private void setForceUpdate(BaseDialog baseDialog) {
        if (isForceUpdate) {
            baseDialog.setCanceledOnTouchOutside(false);
            baseDialog.setCancelable(false);
        } else {
            baseDialog.setCanceledOnTouchOutside(true);
            baseDialog.setCancelable(true);
        }
    }


    @Override
    public void showPatchDialog() {
        BaseDialog baseDialog = new BaseDialog(ctx, R.style.BaseDialog, R.layout.dialog_version);
        setForceUpdate(baseDialog);
        TextView textView = baseDialog.findViewById(R.id.tv_msg);
        TextView versionNoTv = baseDialog.findViewById(R.id.tv_version_no);
        TextView sizeTv = baseDialog.findViewById(R.id.size_tv);
        versionNoTv.setText(versionNo);
        textView.setText(apkUpdateContent);
        sizeTv.setText(size);
        baseDialog.findViewById(R.id.versionchecklib_version_dialog_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 差分包保存的文件夹
                 */
                String patchPath = FileUtils.savePatchPath(LibConfig.FILE_PATH);
                /**
                 * 开始下载差分包
                 */
                downPatch(apkDownloadPath, patchPath, versionNo);
                /**
                 * 关闭升级弹框
                 */
                baseDialog.dismiss();
            }
        });
        setForceUpdate(baseDialog);
        baseDialog.show();
    }


    /**
     * @param url       下载的路径
     * @param patchPath 差分包下载以后 保存的路径
     * @param fileName  差分包名称 使用版本号代替
     */
    private void downPatch(String url, String patchPath, String fileName) {
        BaseDialog baseDialog = new BaseDialog(ctx, R.style.BaseDialog, R.layout.dialog_download);
        tvProgress = baseDialog.findViewById(R.id.tv_progress);
        progressBar = baseDialog.findViewById(R.id.pb);
        setForceUpdate(baseDialog);
        baseDialog.show();
        new OkHttpClientManager()
                .downloadBuild(url, patchPath, fileName, new OkHttpClientManager.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess() {
                        String patchFilePath = patchPath + fileName;//差分包路径+差分包名称（版本号命名）=差分包文件路径
                        String filesPath = FileUtils.savePatchPath(LibConfig.NEWAPK_FILE_PATH);//生成的apk文件夹路径
                        String newApkPath = filesPath + versionNo + ".apk";//生成的apk路径
                        String oldApkPath = ctx.getPackageResourcePath();//获取安装包的路径
                        File newApkFile = mergeFile(oldApkPath, patchFilePath, newApkPath);//差分包下载后 生成一个新的apk
                        if (baseDialog != null) {
                            baseDialog.cancel();
                        }
                        AppUtils.installApk(ctx, newApkFile);//安装新生成的apk

                    }

                    @Override
                    public void onDownloading(int progress) {
                        Log.i("ttttttt", "下载进度" + progress);
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putInt("progress", progress);
                        message.setData(bundle);
                        handler.sendMessage(message);

                    }

                    @Override
                    public void onDownloadFailed() {
                        Log.i("ttttttt", "下载失败");
                        if (baseDialog != null) {
                            baseDialog.cancel();
                        }
                    }
                });
    }

    private void downApk(String url, String patchPath, String fileName) {
        BaseDialog baseDialog = new BaseDialog(ctx, R.style.BaseDialog, R.layout.dialog_download);
        tvProgress = baseDialog.findViewById(R.id.tv_progress);
        progressBar = baseDialog.findViewById(R.id.pb);
        setForceUpdate(baseDialog);
        baseDialog.show();
        new OkHttpClientManager()
                .downloadBuild(url, patchPath, fileName, new OkHttpClientManager.OnDownloadListener() {
                    @Override
                    public void onDownloadSuccess() {
                        String patchFilePath = patchPath + fileName;//差分包路径+差分包名称（版本号命名）=差分包文件路径
                        String filesPath = FileUtils.savePatchPath(LibConfig.NEWAPK_FILE_PATH);//生成的apk文件夹路径
                        if (baseDialog != null) {
                            baseDialog.cancel();
                        }
                        AppUtils.installApk(ctx, new File(patchFilePath));//安装新生成的apk

                    }

                    @Override
                    public void onDownloading(int progress) {
                        Log.i("ttttttt", "下载进度" + progress);
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putInt("progress", progress);
                        message.setData(bundle);
                        handler.sendMessage(message);

                    }

                    @Override
                    public void onDownloadFailed() {
                        Log.i("ttttttt", "下载失败");
                        if (baseDialog != null) {
                            baseDialog.cancel();
                        }
                    }
                });
    }

    /**
     * @param oldFile 旧apk路径
     * @param patch   差分包路径
     * @param newFile 新的文件路径
     * @return 合并后差分包apk
     */
    private File mergeFile(final String oldFile, final String patch,
                           String newFile) {
        GDiffPatcher patcher = new GDiffPatcher();
        File deffFile = new File(patch);
        File updatedFile = new File(newFile);
        try {
            updatedFile.createNewFile();
            patcher.patch(new File(oldFile), deffFile, updatedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updatedFile;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int progress = bundle.getInt("progress");
            progressBar.setProgress(progress);
            tvProgress.setText(progress + "/100");
        }
    };
}
