package com.haocang.waterlink.self.ui;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.haocang.base.adapter.PictureAdapter;
import com.haocang.base.bean.PictureEntity;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.PictureUtils;
import com.haocang.base.utils.SpeechService;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.widgets.MyGridLayoutManager;
import com.haocang.waterlink.R;
import com.haocang.waterlink.self.iview.FeedBackView;
import com.haocang.waterlink.self.presenter.FeedbackPresenter;
import com.haocang.waterlink.self.presenter.impl.FeedbackPresenterImpl;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.util.List;

import camera2library.camera.Camera2RecordActivity;


/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题： 意见反馈
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/3/2615:12
 * 修 改 者：
 * 修改时间：
 */
public class FeedbackFragment extends Fragment
        implements View.OnClickListener, PermissionsProcessingUtil.OnPermissionsCallback,
        SpeechService.OnSpeechResult, TextWatcher, FeedBackView, View.OnFocusChangeListener {

    private TextView titleNameTv;


    private EditText feedbakEt;

    private TextView charLengthTv;//字符最大数值

    private RecyclerView recyclerview;


    private PictureUtils pictureUtils;

    private FeedbackPresenter presenter;

    private PictureAdapter pictureAdapter;

    private EditText nameEdt;

    private EditText phoneEdt;


    private int type = 0;

    private EditText contentEdt;

    private TextView typeTv;

    private View nameView;

    private View phoneView;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_feedback, null);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        presenter = new FeedbackPresenterImpl(this);
        typeTv = view.findViewById(R.id.type_tv);
        titleNameTv = view.findViewById(R.id.title_common_tv);
        feedbakEt = view.findViewById(R.id.feedbak_et);
        charLengthTv = view.findViewById(R.id.char_lenth_tv);
        recyclerview = view.findViewById(R.id.recyclerview);
        view.findViewById(R.id.audio_ll).setOnClickListener(this);
        titleNameTv.setText(getResources().getString(R.string.feedback));
        feedbakEt.addTextChangedListener(this);
        recyclerview.setLayoutManager(new MyGridLayoutManager(getActivity(), 3));
        view.findViewById(R.id.add_pic_iv).setOnClickListener(this);
        pictureAdapter = new PictureAdapter(getActivity());
        recyclerview.setAdapter(pictureAdapter);
        nameEdt = view.findViewById(R.id.name_edt);
        phoneEdt = view.findViewById(R.id.phone_et);
        view.findViewById(R.id.submit_tv).setOnClickListener(this);
        view.findViewById(R.id.video_view).setOnClickListener(this);
        view.findViewById(R.id.feedback_ll).setOnClickListener(this);
        if (AppApplication.getInstance().getUserEntity() != null) {
            nameEdt.setText(AppApplication.getInstance().getUserEntity().getName());
            phoneEdt.setText(AppApplication.getInstance().getUserEntity().getTel());
        }
        contentEdt = view.findViewById(R.id.feedbak_et);
        nameEdt.setOnFocusChangeListener(this);
        phoneEdt.setOnFocusChangeListener(this);
        nameView = view.findViewById(R.id.name_view);
        phoneView = view.findViewById(R.id.phone_view);
    }


    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.submit_tv) {
            presenter.submit();
        } else if (view.getId() == R.id.video_view) {
            PermissionsProcessingUtil.setPermissions(this, LibConfig.AUDIO, this);
        } else if (view.getId() == R.id.add_pic_iv) {
            if (pictureAdapter.getItemCount() < 4) {
                showMulti();
            } else {
                ToastUtil.makeText(getActivity(), "最多添加4张照片");
            }
            hideNameInputMethod();
            hidePhoneInputMethod();
        } else if (view.getId() == R.id.feedback_ll) {
            hideNameInputMethod();
            hidePhoneInputMethod();
            feedbackType();
        }

    }


    private PermissionsProcessingUtil.OnPermissionsCallback cameraCallBack = new PermissionsProcessingUtil.OnPermissionsCallback() {
        @Override
        public void callBack(boolean flag, String permission) {
            if (flag) {
                Camera2RecordActivity.start(getActivity());
            } else {
                ToastUtil.makeText(getActivity(), getString(R.string.permissions_camera));
            }
        }
    };

//    private void photo() {
//        if (pictureAdapter.getItemCount() < 4) {
//
//        } else {
//            ToastUtil.makeText(getActivity(), "最多添加4张照片");
//        }
//    }

    @Override
    public void callBack(boolean flag, String permission) {
        if (flag) {
            SpeechService.btnVoice(getActivity(), this);
        } else {
            ToastUtil.makeText(getActivity(), getResources().getString(R.string.permissions_audio));
        }
    }

    /**
     * @param result 语音识别结果返回.
     */
    @Override
    public void onSpeechResult(String result) {
        String oldResult = feedbakEt.getText().toString();
        feedbakEt.setText(oldResult + result);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        charLengthTv.setText(feedbakEt.getText().length() + "/200");
        contentEdt.setSelection(contentEdt.getText().length());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public String getName() {
        return nameEdt.getText().toString();
    }

    @Override
    public String getPhone() {
        return phoneEdt.getText().toString();
    }

    @Override
    public Integer getType() {
        return type;
    }

    @Override
    public String getContent() {
        return contentEdt.getText().toString();
    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public List<String> getFileList() {
        return pictureAdapter.getFileList();
    }

    @Override
    public void submitSuccess() {
        new AlertView("意见提交成功", "感谢您提交的意见，我们将尽快回复", "取消",
                new String[]{"返回我的"}, null, getActivity(), AlertView.Style.Alert, new com.bigkoo.alertview.OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position >= 0) {
                    getActivity().finish();
                }
            }
        }).show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Camera2RecordActivity.RESULTCODE == requestCode && data != null) {
            //添加照片或者视频
            String videoPath = data.getStringExtra("videoPath");
            String picturePath = data.getStringExtra("picturePath");
            addItemPicture(picturePath);
            addItemVideo(videoPath);
        } else if (requestCode == 1004 && data != null) {
            handleImageOfKitKat(data);
        }
    }

    /**
     * 添加照片
     *
     * @param picturePath
     */
    private void addItemPicture(String picturePath) {
        if (!TextUtils.isEmpty(picturePath)) {
            PictureEntity entity = new PictureEntity();
            File file = new File(picturePath);
            File newFile = CompressHelper.getDefault(getActivity()).compressToFile(file);//压缩图片
            entity.setLocalImgPath(newFile.getPath());
            entity.setType(0);
            pictureAdapter.addItem(entity);
            pictureAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 添加视频
     *
     * @param videoPath
     */
    private void addItemVideo(String videoPath) {
        if (!TextUtils.isEmpty(videoPath)) {
            PictureEntity entity = new PictureEntity();
            entity.setType(1);
            entity.setVideoPath(videoPath);
            pictureAdapter.addItem(entity);
            pictureAdapter.notifyDataSetChanged();
        }
    }

    private void showMulti() {
        final String[] stateLabel = getActivity().getResources().getStringArray(R.array.multi_media);
        new AlertView(null, null, "取消", null,
                stateLabel, getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(final Object o, final int position) {
                //如果是-1说明点击的取消,点击取消时，不做任何事
                if (position == -1) {
                    return;
                } else if (position == 0) {
                    PermissionsProcessingUtil.setPermissions(FeedbackFragment.this, LibConfig.CAMERA, cameraCallBack);
                } else {
                    openAlbum();
                }
            }
        }).show();
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        getActivity().startActivityForResult(intent, 1004);
    }

    private void handleImageOfKitKat(Intent data) {
        String imageUrl = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(getActivity(), uri)) {
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
        Cursor cursor = getActivity().getContentResolver().query(externalContentUri, null, selection, null, null, null);
        if (cursor == null) {
            return path;
        }
        while (cursor.moveToNext()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        }
        cursor.close();
        return path;

    }


    private void feedbackType() {
        new AlertView(null, null, "取消", null, types
                , getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(final Object o, final int position) {
                //如果是-1说明点击的取消,点击取消时，不做任何事
                if (position == -1) {
                    return;
                } else {
//                    type = position + 1;
                    type = position;
                    typeTv.setText(types[position]);
                }
            }
        }).show();
    }

    private String[] types = {"业务需求", "系统问题", "其它问题"};

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        if (id == R.id.name_edt) {
            setHintColor(nameView, hasFocus);
        } else if (id == R.id.phone_et) {
            setHintColor(phoneView, hasFocus);
        }
    }

    private void setHintColor(View view, boolean hasFocus) {
        if (hasFocus) {
            view.setBackgroundColor(Color.parseColor("#0cabdf"));
        } else {
            view.setBackgroundColor(Color.parseColor("#f9f9f9"));
        }
    }

    private void hideNameInputMethod() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(nameEdt.getWindowToken(), 0); //强制隐藏键盘
    }

    private void hidePhoneInputMethod() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(phoneEdt.getWindowToken(), 0); //强制隐藏键盘
    }
}