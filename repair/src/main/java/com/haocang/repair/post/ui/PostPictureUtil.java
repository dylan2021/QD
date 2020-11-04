package com.haocang.repair.post.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.example.cj.videoeditor.Constants;
import com.example.cj.videoeditor.WaterMark;
import com.example.cj.videoeditor.activity.RecordedActivity;
import com.example.cj.videoeditor.picture.ImageUtil;
import com.haocang.base.adapter.PictureAdapter;
import com.haocang.base.bean.PictureInfo;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.ui.CameraFragment;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.BDSendTraceUtil;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.widgets.MyGridLayoutManager;
import com.haocang.repair.R;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.util.List;

import camera2library.camera.Camera2RecordActivity;
import me.iwf.photopicker.utils.FileUtils;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题：
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/5/16上午8:42
 * 修  改  者：
 * 修改时间：
 */
public class PostPictureUtil implements View.OnClickListener, PermissionsProcessingUtil.OnPermissionsCallback {

    private RecyclerView mRecyclerview;
    private PictureAdapter mPictureAdapter;
    private Fragment mFragment;
    public static final int REQUESTCODE = 2009;

    /**
     * @param fragment 使用图片上传工具的Fragment.
     * @param view     根View
     */
    public PostPictureUtil(final Fragment fragment, final View view) {
        mFragment = fragment;
        mRecyclerview = view.findViewById(R.id.recyclerview);
        mPictureAdapter = new PictureAdapter(getContext());
        mRecyclerview.setLayoutManager(new MyGridLayoutManager(getContext(), 3));
        mRecyclerview.setAdapter(mPictureAdapter);
        view.findViewById(R.id.add_pic_iv).setOnClickListener(this);
    }

    /**
     *
     */
    public PostPictureUtil() {

    }

    /**
     * 获取上下文参数.
     *
     * @return
     */
    public Context getContext() {
        return mFragment.getActivity();
    }

    /**
     * @param view
     */
    @Override
    public void onClick(final View view) {
        int id = view.getId();
        if (id == R.id.add_pic_iv) {
            showMulti();
        }
    }

    private void showMulti() {
        final String[] stateLabel = mFragment.getActivity().getResources().getStringArray(R.array.multi_media3);
        new AlertView(null, null, "取消", null,
                stateLabel, mFragment.getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(final Object o, final int position) {
                //如果是-1说明点击的取消,点击取消时，不做任何事
                if (position == -1) {
                    return;
                } else if (position == 0) {
                    PermissionsProcessingUtil.setPermissions(mFragment, LibConfig.CAMERA, cameraCallBack);
                } else if (position == 1) {
                    openVideo();
                } else {
                    openAlbum();
                }
            }
        }).show();
    }

    private void openVideo() {
        Intent intent = new Intent(mFragment.getActivity(), RecordedActivity.class);
        intent.putExtra("person", AppApplication.getInstance().getUserEntity().getName());
        if (BDSendTraceUtil.getInstance().getLocation() != null) {
            intent.putExtra("address", BDSendTraceUtil.getInstance().getLocation().getAddrStr());
        } else {
            intent.putExtra("address", "");
        }
        if (!TextUtils.isEmpty(LibConfig.weather)) {
            intent.putExtra("weather", LibConfig.weather + " " + LibConfig.temperature);
        } else {
            intent.putExtra("weather", "");
        }
        intent.putExtra("processName", mFragment.getActivity().getIntent().getStringExtra("process"));
        intent.putExtra("equName", mFragment.getActivity().getIntent().getStringExtra("equName"));
        mFragment.getActivity().startActivityForResult(intent, 1039);
    }

    private PermissionsProcessingUtil.OnPermissionsCallback cameraCallBack = new PermissionsProcessingUtil.OnPermissionsCallback() {
        @Override
        public void callBack(boolean flag, String permission) {
            if (flag) {
//                Camera2RecordActivity.start(mFragment.getActivity());
                Intent intent = new Intent(mFragment.getActivity(), CommonActivity.class);
                intent.putExtra("fragmentName", CameraFragment.class.getName());
                mFragment.getActivity().startActivityForResult(intent, 1122);
            } else {
                ToastUtil.makeText(mFragment.getActivity(), "请开启相机权限");
            }
        }
    };

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        mFragment.getActivity().startActivityForResult(intent, 1004);
    }

    private void handleImageOfKitKat(Intent data) {
        String imageUrl = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(mFragment.getActivity(), uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {//判断uri是不是media格式
                String id = docId.split(":")[1];//是media格式的话将uri进行二次解析取出id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imageUrl = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imageUrl = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imageUrl = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imageUrl = uri.getPath();
        }
        addItemPicture(imageUrl);
    }

    private String getImagePath(Uri externalContentUri, String selection) {
        String path = null;
        Cursor cursor = mFragment.getActivity().getContentResolver().query(externalContentUri, null, selection, null, null, null);
        if (cursor == null) {
            return path;
        }
        while (cursor.moveToNext()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        }
        cursor.close();
        return path;

    }


    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (REQUESTCODE == requestCode && data != null) {
            //添加照片或者视频
            String videoPath = data.getStringExtra("videoPath");
            String picturePath = data.getStringExtra("picturePath");
            addItemPicture(picturePath);
            addItemVideo(videoPath);

        } else if (requestCode == 1004 && data != null) {
            handleImageOfKitKat(data);
        } else if (resultCode == 1039 && data != null) {
            String video = data.getStringExtra("videoUrl");
            addItemVideo(video);
        } else if (requestCode == 1122 && data != null) {
            String pic = data.getStringExtra("picturePath");
            Bitmap bitmap = ImageUtil.fileInputStream(pic);
            if (bitmap != null) {
                Intent intent = new Intent();
                intent.putExtra("person", AppApplication.getInstance().getUserEntity().getName());
                if (BDSendTraceUtil.getInstance().getLocation() != null) {
                    intent.putExtra("address", BDSendTraceUtil.getInstance().getLocation().getAddrStr());
                } else {
                    intent.putExtra("address", "");
                }
                intent.putExtra("processName", mFragment.getActivity().getIntent().getStringExtra("process"));
                intent.putExtra("equName", mFragment.getActivity().getIntent().getStringExtra("equName"));
                if (!TextUtils.isEmpty(LibConfig.weather)) {
                    intent.putExtra("weather", LibConfig.weather + " " + LibConfig.temperature);
                } else {
                    intent.putExtra("weather", "");
                }
                Bitmap map = ImageUtil.createWaterMaskLeftBottom(mFragment.getActivity(), bitmap, Constants.loadBitmapFromView(new WaterMark(mFragment.getActivity(), intent, R.layout.repair_mark_view).getWaterMarkView()), 0, 0);
                File file = FileUtils.saveBitmapFile(map);
                addItemPicture(file.getPath());
            }
        }
    }

    /**
     * 添加照片.
     *
     * @param picturePath 图片路径
     */
    private void addItemPicture(final String picturePath) {
        if (!TextUtils.isEmpty(picturePath)) {
            PictureInfo entity = new PictureInfo();
            File file = new File(picturePath);
            File newFile = CompressHelper.getDefault(mFragment.getActivity()).compressToFile(file);
            entity.setLocalImgPath(newFile.getPath());
            entity.setType(0);
            mPictureAdapter.addItem(entity);
            mPictureAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 添加视频.
     *
     * @param videoPath 视频路径
     */
    private void addItemVideo(final String videoPath) {
        if (!TextUtils.isEmpty(videoPath)) {
            PictureInfo entity = new PictureInfo();
            entity.setType(1);
            entity.setVideoPath(videoPath);
            mPictureAdapter.addItem(entity);
            mPictureAdapter.notifyDataSetChanged();
        }
    }


    /**
     * @param flag
     * @param permission
     */
    @Override
    public void callBack(final boolean flag, final String permission) {
        if (flag) {
            if (mPictureAdapter.getItemCount() < 6) {
//                Intent intent = new Intent(getContext(), CommonActivity.class);
//                intent.putExtra("fragmentName", CameraFragment.class.getName());
                Camera2RecordActivity.start((Activity) getContext());
//                mFragment.getActivity().startActivityForResult(intent, REQUESTCODE);
            } else {
                ToastUtil.makeText(getContext(), "最多添加6张照片");
            }
        } else {
            ToastUtil.makeText(getContext(),
                    getContext().getString(R.string.permissions_camera));
        }
    }

    /**
     * @return
     */
    public List<String> getfileList() {
        return mPictureAdapter.getFileList();
    }

    /**
     *
     */
    public void clearView() {
        mPictureAdapter.clear();
    }

    /**
     *
     */
    @SuppressLint("StaticFieldLeak")
    public void setUrlList(final List<PictureInfo> list) {

        if (list != null && list.size() > 0) {
            mPictureAdapter.addAll(list);
            mPictureAdapter.notifyDataSetChanged();
        }
    }

    private void addItem(final String path) {
        PictureInfo entity = new PictureInfo();
        if (StringUtils.isPicture(path)) {
            entity.setLocalImgPath(path);
            entity.setType(0);
            mPictureAdapter.addItemWithoutNotifyList(entity);
        } else if (path.contains(".mp4")) {
            entity.setNetWordVideoPath(path);
            entity.setType(1);
            mPictureAdapter.addItemWithoutNotifyList(entity);
        }

    }

    /**
     * @return
     */
    public String getNetWorkList() {
        return mPictureAdapter.getNetWordFile();
    }

    public String getNetWordThumbnailUrl() {
        return mPictureAdapter.getNetWordThumbnailUrl();
    }
}
