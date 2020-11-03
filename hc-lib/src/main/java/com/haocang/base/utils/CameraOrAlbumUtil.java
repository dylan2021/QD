package com.haocang.base.utils;

import android.app.Activity;
import android.content.Intent;
import androidx.fragment.app.Fragment;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.haocang.base.R;
import com.haocang.base.config.LibConfig;

import camera2library.camera.Camera2RecordActivity;

public class CameraOrAlbumUtil {

    private Activity activity;

    public void openCameraOrAlbum(Activity activity, Fragment fragment) {
        this.activity = activity;
        final String[] stateLabel = activity.getResources().getStringArray(R.array.multi_media2);
        new AlertView(null, null, "取消", null,
                stateLabel, activity, AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(final Object o, final int position) {
                //如果是-1说明点击的取消,点击取消时，不做任何事
                if (position == -1) {
                    return;
                } else if (position == 0) {
                    PermissionsProcessingUtil.setPermissions(fragment, LibConfig.CAMERA, cameraCallBack);
                } else {
                    openAlbum(activity);
                }
            }
        }).show();

    }


    private PermissionsProcessingUtil.OnPermissionsCallback cameraCallBack = new PermissionsProcessingUtil.OnPermissionsCallback() {
        @Override
        public void callBack(boolean flag, String permission) {
            if (flag) {
                Camera2RecordActivity.start(activity);
            } else {
                ToastUtil.makeText(activity, "请开启相机权限");
            }
        }
    };


    private void openAlbum(Activity activity) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        activity.startActivityForResult(intent, 1004);
    }



}
