package com.haocang.base.http;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.haocang.base.config.AppApplication;
import com.haocang.base.config.ErrorCode;
import com.haocang.base.config.LibConfig;
import com.haocang.base.config.LibConstants;
import com.haocang.base.utils.FileUtils;
import com.haocang.base.utils.ProgressBarDialog;
import com.haocang.base.utils.SystemUtil;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
 * 创建时间：2018/1/814:26
 * 修 改 者：
 * 修改时间：
 */
public class OkHttpClientManager {

    public static final int CONNECT_TIMEOUT = 30000;
    public static final int READ_TIMEOUT = 30000;
    public static final int WRITE_TIMEOUT = 30000;
    public static OkHttpClient mOkHttpClient;

    private String url;//api路径
    private RequestBody requestBody;
    private OnNetworkResponse onNetworkResponse;//
    private OnNetworkResponseControl onNetworkResponseControl;//下控中使用
    private ProgressBarDialog loacDialog;
    private int requestInt = 1;//请求方式默认get
    private static Response mResponse;
    private boolean ingnore = true;//默认401重新登录

    private int connectTimeOut = 50;
    private int readTimeOut = 50;

    /**
     * todo 考虑到每次请求都是一次性的，
     * todo 所以我修改了ConnectionPool的keepAliveDuration时间，让每次连接1秒后就关闭。
     */
    public OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
                .build();//设置30秒超时
    }

    public OkHttpClientManager(int connectTimeOut, int readTimeOut) {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeOut, TimeUnit.SECONDS)
                .readTimeout(readTimeOut, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
                .build();//设置30秒超时
    }

    public OkHttpClientManager setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 设置 当访问出现401的时候 需不需重新登录
     *
     * @param ingnore
     * @return
     */
    public OkHttpClientManager setIngnore(boolean ingnore) {
        this.ingnore = ingnore;
        return this;
    }


    public OkHttpClientManager setLoadDialog(ProgressBarDialog dialog) {
        this.loacDialog = dialog;
        try {
            if (dialog != null) {
                dialog.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public OkHttpClientManager setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public OkHttpClientManager setOnNetWorkReponse(OnNetworkResponse onNetworkResponse) {
        this.onNetworkResponse = onNetworkResponse;
        return this;
    }


    public OkHttpClientManager setOnNetWordReponse(OnNetworkResponseControl onNetworkResponseControl) {
        this.onNetworkResponseControl = onNetworkResponseControl;
        return this;
    }

    /**
     * 设置请求方式.
     *
     * @return
     */
    public OkHttpClientManager setRequestMethod(int request) {
        this.requestInt = request;
        return this;
    }

    public void builder() {
        Request request = requestType();
        mOkHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (loacDialog != null) {
                            try {
                                loacDialog.cancel();
                            } catch (Exception c) {
                                e.printStackTrace();
                            }
                        }
                        Message message = new Message();
                        message.what = ErrorCode.READTIME;
                        mHandler.sendMessage(message);

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        mResponse = response;
                        getHeaders(response);
                        if (mResponse.code() == 401) {
                            Log.i("result", "错误码" + response.code() + response.request().url() + "无权限访问---" + TimeUtil.getCurdate());
                            onNetworkResponse.onNetworkResponse("无权限访问");
                            getHeadersForJump(response);
                        } else if (response.isSuccessful()) {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            String result = response.body().string();
                            Log.i("result", response.request().url() + result + "---" + TimeUtil.getCurdate());
                            bundle.putString("result", result);
                            message.what = ErrorCode.DATA;
                            message.setData(bundle);
                            mHandler.sendMessage(message);
                        } else {
                            Log.i("result", "错误码" + response.code() + response.request().url() + "接口报错了---" + TimeUtil.getCurdate());
                            Message message = new Message();
                            message.what = ErrorCode.ERROOR;
                            mHandler.sendMessage(message);
                        }
                    }
                });
    }

    public void builderForControl() {
        Request request = requestType();
        mOkHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (loacDialog != null) {
                            try {
                                loacDialog.cancel();
                            } catch (Exception c) {
                                e.printStackTrace();
                            }
                        }
                        Message message = new Message();
                        message.what = ErrorCode.READTIME;
                        mHandler.sendMessage(message);

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        mResponse = response;
                        getHeaders(response);
                        if (mResponse.code() == 401) {
                            getHeadersForJump(response);
                        } else if (response.isSuccessful()) {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            String result = response.body().string();
                            bundle.putString("result", result);
                            message.what = ErrorCode.DATA_CONTROL;
                            message.setData(bundle);
                            mHandler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            String result = response.body().string();
                            bundle.putString("resultError", result);
                            message.what = ErrorCode.ERROR_CONTROL;
                            message.setData(bundle);
                            mHandler.sendMessage(message);
                        }
                    }
                });
    }

    /**
     * //todo 每次请求接口的时候 刷新 refreshToken
     */
    private void getHeaders(Response response) {
        Headers responseHeaders = response.headers();
        int responseHeadersLength = responseHeaders.size();
        for (int i = 0; i < responseHeadersLength; i++) {
            String headerName = responseHeaders.name(i);
            String headerValue = responseHeaders.get(headerName);
            if ("session_token".equals(headerName)) {
//                LibConfig.sessionToken = ";session_token=" + headerValue;
//                LibConfig.setCookie(LibConfig.accessToken + LibConfig.sessionToken);
            }
        }
    }

    private void getHeadersForJump(Response response) {
        Headers responseHeaders = response.headers();
        int responseHeadersLength = responseHeaders.size();
        for (int i = 0; i < responseHeadersLength; i++) {
            String headerName = responseHeaders.name(i);
            String headerValue = responseHeaders.get(headerName);
            if ("uaa_auth_sessionExpired".equals(headerValue)) {
                Bundle bundle = new Bundle();
                bundle.putString("result", "登录超时,即将重新登录");
                Message message = new Message();
                message.what = ErrorCode.NO_PERMISSIONS;
                message.setData(bundle);
                mHandler.sendMessage(message);
            } else if ("uaa_auth_authorityChanged".equals(headerValue)) {
                Bundle bundle = new Bundle();
                bundle.putString("result", "您的角色权限被修改,即将重新登录");
                Message message = new Message();
                message.setData(bundle);
                message.what = ErrorCode.NO_PERMISSIONS;
                mHandler.sendMessage(message);
            } else if ("uaa_auth_passwordChanged".equals(headerValue)) {
                Bundle bundle = new Bundle();
                bundle.putString("result", "您的密码已被修改,即将重新登录");
                Message message = new Message();
                message.what = ErrorCode.NO_PERMISSIONS;
                message.setData(bundle);
                mHandler.sendMessage(message);
            }
        }

    }

    /**
     * 请求类型.
     *
     * @return
     */
    private Request requestType() {
        Request.Builder request = new Request.Builder();
        request.addHeader("User-Agent", SystemUtil.getDevice())
                .addHeader("Cookie", LibConfig.getCookie());
        String routePath = LibConfig.addRoute(url);
        if (!TextUtils.isEmpty(routePath)) {
            request.addHeader("Referer", routePath);
        }
        if (requestInt == LibConfig.HTTP_POST) {
            return httpPost(request);
        } else if (requestInt == LibConfig.HTTP_PUT) {
            return httPut(request);
        } else if (requestInt == LibConfig.HTTP_DELETE) {
            return httpDelete(request);
        } else {
            return httpGet(request);
        }
    }


    private Request httpPost(Request.Builder request) {
        return request.url(getUrl()).post(requestBody).build();
    }

    private Request httpGet(Request.Builder request) {
        return request.get().url(getUrl()).build();
    }

    private Request httPut(Request.Builder request) {
        return request.url(getUrl()).put(requestBody).build();
    }

    private Request httpDelete(Request.Builder request) {
        return request.url(LibConstants.ADDRESS_IP + url).delete(requestBody).build();
    }

    private boolean isWholeUrl = false;

    public String getUrl() {
        String wholeUrl = LibConstants.ADDRESS_IP + url;
        if (isWholeUrl) {
            wholeUrl = url;
            isWholeUrl = false;
        }
        return wholeUrl;
    }

    public OkHttpClientManager setWholeUrl(String wholeUrl) {
        isWholeUrl = true;
        url = wholeUrl;
        return this;
    }

    public void sendGet(String url) {
        Request request = new Request.Builder()
                .get().url(url)
                .addHeader("Cookie", LibConfig.getCookie())
                .build();
        mOkHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Message message = new Message();
                        message.what = ErrorCode.ERROOR;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        mResponse = response;
                        if (response.isSuccessful()) {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            String result = response.body().string();
                            bundle.putString("result", result);
                            message.what = ErrorCode.DATA;
                            message.setData(bundle);
                            mHandler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.what = ErrorCode.ERROOR;
                            mHandler.sendMessage(message);
                        }
                    }
                });
    }

    /**
     * 登录报表
     *
     * @param url
     */
    public void sendReport(String url) {
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        mOkHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        onNetworkResponse.onErrorResponse(null);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Headers headers = response.headers();
                        int requestHeadersLength = headers.size();
                        Map<String, Object> map = new HashMap<>();
                        for (int i = 0; i < requestHeadersLength; i++) {
                            String headerValue = headers.value(i);
                            map.put(i + "", headerValue);
                        }
                        LibConfig.addReportHeads(map);//todo 添加请求头
                        mResponse = response;
                        if (response.isSuccessful()) {
                            Message message = new Message();
                            Bundle bundle = new Bundle();
                            message.what = ErrorCode.DATA;
                            message.setData(bundle);
                            mHandler.sendMessage(message);
                        } else {
                            Message message = new Message();
                            message.what = ErrorCode.ERROOR;
                            mHandler.sendMessage(message);
                        }
                    }
                });
    }


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                if (loacDialog != null) {
                    loacDialog.cancel();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Bundle bundle = msg.getData();
            switch (msg.what) {
                case ErrorCode.DATA_CONTROL:
                    String resultControl = bundle.getString("result");
                    onNetworkResponseControl.onNetworkResponse(resultControl);
                    break;
                case ErrorCode.DATA:
                    String result = bundle.getString("result");
                    onNetworkResponse.onNetworkResponse(result);
                    break;
                case ErrorCode.ERROOR:
                    onNetworkResponse.onErrorResponse(mResponse);
                    break;
                case ErrorCode.READTIME:
//                    ToastUtil.makeText(AppApplication.getInstance().getApplicationContext(), "网络连接超时，请检查网络");
                    break;
                case ErrorCode.NO_PERMISSIONS:
                    if (ingnore) {
                        String resultError = bundle.getString("result");
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("fragmentUri", LibConfig.AROUTE_LOGIN);
//                        map.put("exit", "exit");
//                        map.put("result", resultError);
//                        ARouterUtil.toFragment(map);
                        ToastUtil.makeText(AppApplication.getInstance().getApplicationContext(), resultError);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                ARouterUtil.toActivity(null, "/gui/activity");
//                                android.os.Process.killProcess(android.os.Process.myPid());
//                                System.exit(0);
                                Intent LaunchIntent = AppApplication.getInstance().getApplicationContext().getPackageManager().getLaunchIntentForPackage(AppApplication.getInstance().getPackageName());
                                LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                AppApplication.getInstance().getApplicationContext().startActivity(LaunchIntent);

                            }
                        }, 1000);
                    }
                    break;
                case ErrorCode.ERROR_CONTROL:
                    String resultControlError = bundle.getString("resultError");
                    onNetworkResponseControl.onNetworkResponseError(resultControlError);
                    break;
            }
        }
    };


    /**
     * @param url     下载连接
     * @param saveDir 储存下载文件的SDCard目录
     *                fileName 文件名称
     */
    public void downloadBuild(final String url, final String saveDir, String fileName, final OnDownloadListener listener) {
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 下载失败
                listener.onDownloadFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                String savePath = FileUtils.isExistDir(saveDir);
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
//                    File file = new File(savePath, getNameFromUrl(url));
                    File file = new File(savePath, fileName);
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        // 下载中
                        listener.onDownloading(progress);
                    }
                    fos.flush();
                    // 下载完成
                    listener.onDownloadSuccess();
                } catch (Exception e) {
                    listener.onDownloadFailed();
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static final MediaType STREAM = MediaType.parse("application/octet-stream");

    /**
     * 上传文件
     *
     * @param actionUrl 接口地址
     * @param filePath  本地文件地址
     */
    public void upLoadFile(String actionUrl, String filePath) {
        //创建File
        File file = new File(filePath);
        //创建RequestBody
        RequestBody body = RequestBody.create(STREAM, file);
        //创建Request
        final Request request = new Request.Builder().url(actionUrl).post(body).build();
        final Call call = mOkHttpClient.newBuilder().build().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = response.body().string();
                } else {
                }
            }
        });
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }


    public interface OnNetworkResponse {
        void onNetworkResponse(String result);//成功返回的结果

        void onErrorResponse(Response response);//失败

    }

    public interface OnNetworkResponseControl {
        void onNetworkResponseError(String result);//错误的返回结果

        void onNetworkResponse(String result);//成功返回的结果

    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         */
        void onDownloadSuccess();

        /**
         * @param progress 下载进度
         */
        void onDownloading(int progress);

        /**
         * 下载失败
         */
        void onDownloadFailed();

    }


}
