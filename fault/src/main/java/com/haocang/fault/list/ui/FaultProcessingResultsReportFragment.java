package com.haocang.fault.list.ui;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.cj.videoeditor.Constants;
import com.example.cj.videoeditor.WaterMark;
import com.example.cj.videoeditor.activity.RecordedActivity;
import com.example.cj.videoeditor.picture.ImageUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.adapter.PictureAdapter;
import com.haocang.base.bean.PictureEntity;
import com.haocang.base.config.AppApplication;
import com.haocang.base.config.LibConfig;
import com.haocang.base.ui.CameraFragment;
import com.haocang.base.ui.CommonActivity;
import com.haocang.base.utils.BDSendTraceUtil;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.SpeechService;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.utils.TimeTransformUtil;
import com.haocang.base.utils.TimeUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.widgets.MyGridLayoutManager;
import com.haocang.fault.R;
import com.haocang.fault.list.iview.FaultProcessingResultView;
import com.haocang.fault.list.presenter.FaultProcessingResultPresenter;
import com.haocang.fault.list.presenter.impl.FaultProcessingResultPresenterImpl;
import com.haocang.fault.list.utils.ResultsReasonDialog;
import com.nanchen.compresshelper.CompressHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import camera2library.camera.Camera2RecordActivity;
import me.iwf.photopicker.utils.FileUtils;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：处理结果填报.
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/2714:30
 * 修 改 者：
 * 修改时间：
 */
@Route(path = LibConfig.AROUTE_PROCESSING_RESULT)
public class FaultProcessingResultsReportFragment extends Fragment implements View.OnClickListener, TextWatcher, SpeechService.OnSpeechResult, PermissionsProcessingUtil.OnPermissionsCallback, FaultProcessingResultView, ResultsReasonDialog.OnDataInterface {


    private TextView titleNameTv;
    private TextView commonTv;
    private RecyclerView recyclerview;
    private PictureAdapter pictureAdapter;
    public static final int REQUESTCODE = 12301;

    private FaultProcessingResultPresenter presenter;


    private int faultRecordId = -1;//处理结果
    private int hangupReason = -1;//挂起
    private int faultCloseReason = -1;//关闭

    private String compleTime = TimeTransformUtil.getUploadGMTTime(TimeUtil.getStrByTime(new Date()));

    private TextView processingResultTv;//处理结果
    private EditText reasonEdt;//缺陷原因
    private EditText remarksEt;
    private TextView timeTv;

    private TextView charLengthTv;//字符最大数值

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fault_processing_result, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.falut_processing_result_repari));
        commonTv = view.findViewById(R.id.common_complete_tv);
        commonTv.setVisibility(View.VISIBLE);
        commonTv.setOnClickListener(this);
        commonTv.setText(getText(R.string.base_complete));
        view.findViewById(R.id.add_pic_iv).setOnClickListener(this);
        view.findViewById(R.id.processing_result_ll).setOnClickListener(this);
        view.findViewById(R.id.processing_time_ll).setOnClickListener(this);
        timeTv = view.findViewById(R.id.processing_time_tv);
        recyclerview = view.findViewById(R.id.recyclerview);
        pictureAdapter = new PictureAdapter(getActivity());
        recyclerview.setLayoutManager(new MyGridLayoutManager(getActivity(), 3));
        recyclerview.setAdapter(pictureAdapter);
        processingResultTv = view.findViewById(R.id.processing_result_tv);
        reasonEdt = view.findViewById(R.id.reason_edt);
        remarksEt = view.findViewById(R.id.fault_scene_et);
        presenter = new FaultProcessingResultPresenterImpl(this);
        view.findViewById(R.id.audio_ll).setOnClickListener(this);
        charLengthTv = view.findViewById(R.id.char_lenth_tv);
        remarksEt.addTextChangedListener(this);
        timeTv.setText(TimeUtil.getDateTimeStr(new Date()));
        getFaultArray();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.common_complete_tv) {
            presenter.submitResults();
        } else if (id == R.id.add_pic_iv) {
            isMaxPictureCount();
        } else if (id == R.id.processing_result_ll) {
            showDialogResult();
        } else if (id == R.id.audio_ll) {
            PermissionsProcessingUtil.setPermissions(this, LibConfig.AUDIO, this);
        } else if (id == R.id.processing_time_ll) {
            boolean[] showTime = {true, true, true, true, true, true};
            TimePickerView pvTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(final Date date, final View v) {
                    if (date.before(new Date())) {

                        if (compareTime(date)) {
                            timeTv.setText(TimeUtil.getDateTimeStr(date));
//                        mRecordVo.setFinishDate(TimeUtil.getDateTimeStrWithoutSpace(date));
                            compleTime = TimeTransformUtil.getUploadGMTTime(TimeUtil.getStrByTime(date));
                        } else {
                            ToastUtil.makeText(getActivity(), "不能早于工单开始时间");
                        }

                    } else {
                        ToastUtil.makeText(getActivity(), "不可选择未来时间");
                    }

                }
            })
                    .setType(showTime)
                    .build();
            pvTime.show();
        }
    }

    private void isMaxPictureCount() {
        if (pictureAdapter.getItemCount() < 6) {
            showMulti();
        } else {
            ToastUtil.makeText(getActivity(), getString(R.string.falut_max_picture));
        }
    }

    @Override
    public void callBack(boolean flag, String permission) {
        if (flag && LibConfig.CAMERA.equals(permission)) {
            Camera2RecordActivity.start(getActivity());
        } else if (!flag && LibConfig.CAMERA.equals(permission)) {
            ToastUtil.makeText(getActivity(), getString(R.string.permissions_photo));
        } else if (flag && LibConfig.AUDIO.equals(permission)) {
            SpeechService.btnVoice(getActivity(), this);
        } else if (!flag && LibConfig.AUDIO.equals(permission)) {
            ToastUtil.makeText(getActivity(), getResources().getString(R.string.permissions_audio));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        presenter.onActivityResult(requestCode, resultCode, data);
        if (Camera2RecordActivity.RESULTCODE == resultCode && data != null) {
            String videoPath = data.getStringExtra("videoPath");
            String picturePath = data.getStringExtra("picturePath");
            addItemPicture(picturePath);
            addItemVideo(videoPath);
        } else if (requestCode == 1004 && data != null) {
            handleImageOfKitKat(data);
        }
        if (resultCode == 1039 && data != null) {
            String video = data.getStringExtra("videoUrl");
            addItemVideo(video);
        } else if (resultCode == 1122 && data != null) {
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
                intent.putExtra("processName", getActivity().getIntent().getStringExtra("processName") + "");
                intent.putExtra("equName", getActivity().getIntent().getStringExtra("equName") + "");
                if (!TextUtils.isEmpty(LibConfig.weather)) {
                    intent.putExtra("weather", LibConfig.weather + " " + LibConfig.temperature);
                } else {
                    intent.putExtra("weather", "");
                }
                Bitmap map = ImageUtil.createWaterMaskLeftBottom(getActivity(), bitmap, Constants.loadBitmapFromView(new WaterMark(getActivity(), intent, R.layout.fault_mark_view).getWaterMarkView()), 0, 0);
                File file = FileUtils.saveBitmapFile(map);
                addItemPicture(file.getPath());
            }
        }
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

    private void showMulti() {
        final String[] stateLabel = getActivity().getResources().getStringArray(R.array.falut_multi_media);
        new AlertView(null, null, "取消", null,
                stateLabel, getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(final Object o, final int position) {
                //如果是-1说明点击的取消,点击取消时，不做任何事
                if (position == -1) {
                    return;
                } else if (position == 0) {
                    PermissionsProcessingUtil.setPermissions(FaultProcessingResultsReportFragment.this, LibConfig.CAMERA, cameraCallBack);
                } else if (position == 1) {
                    openVideo();
                } else {
                    openAlbum();
                }
            }
        }).show();
    }

    private void openVideo() {
        Intent intent = new Intent(getActivity(), RecordedActivity.class);
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
        intent.putExtra("processName", getActivity().getIntent().getStringExtra("processName") + "");
        intent.putExtra("equName", getActivity().getIntent().getStringExtra("equName") + "");
        startActivityForResult(intent, 1039);
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        getActivity().startActivityForResult(intent, 1004);
    }


    private void addItemPicture(String picturePath) {
        if (!TextUtils.isEmpty(picturePath)) {
            PictureEntity entity = new PictureEntity();
            File file = new File(picturePath);
            File newFile = CompressHelper.getDefault(getActivity()).compressToFile(file);
            entity.setLocalImgPath(newFile.getPath());
            entity.setType(0);
            pictureAdapter.addItem(entity);
        }
    }

    private void addItemVideo(String videoPath) {
        if (!TextUtils.isEmpty(videoPath)) {
            PictureEntity entity = new PictureEntity();
            entity.setType(1);
            entity.setVideoPath(videoPath);
            pictureAdapter.addItem(entity);
        }
    }

    private PermissionsProcessingUtil.OnPermissionsCallback cameraCallBack = new PermissionsProcessingUtil.OnPermissionsCallback() {
        @Override
        public void callBack(boolean flag, String permission) {
            if (flag) {
//                Camera2RecordActivity.start(getActivity());
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("fragmentName", CameraFragment.class.getName());
                getActivity().startActivityForResult(intent, 1122);
            } else {
                ToastUtil.makeText(getActivity(), getString(R.string.permissions_camera));
            }
        }
    };


    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public int getFaultRecordId() {
        return getFaultId() != null ? Integer.parseInt(getFaultId()) : 0;
    }

    @Override
    public int getProcessingResult() {
        return faultRecordId;
    }

    @Override
    public int getFaultCloseReason() {
        return faultCloseReason;
    }

    @Override
    public int getHangupReason() {
        return hangupReason;
    }

    @Override
    public String getFaultReason() {
        return reasonEdt.getText().toString();
    }


    @Override
    public String getRemarks() {
        return remarksEt.getText().toString();
    }

    @Override
    public String getCompleTime() {
        return compleTime;
    }

    @Override
    public void submitSuccess() {
        ToastUtil.makeText(getActivity(), "提交成功");
        getActivity().finish();
    }


    @Override
    public void resultEmpty() {
        ToastUtil.makeText(getActivity(), "处理结果不能为空");
    }

    @Override
    public void setCloseReasonOrHangUp(String reason, int type) {
        ResultsReasonDialog dialog = new ResultsReasonDialog(getActivity(), reason, type);
        dialog.setOnDataInterface(this);
        dialog.show();
        dialog.setDialogWindowAttr(dialog, getActivity());
    }

    @Override
    public List<String> getfileList() {
        return pictureAdapter.getFileList();
    }

    @Override
    public String getNetWorkList() {
        return pictureAdapter.getNetWordFile();
    }

    @Override
    public String getNetWordThumbnailUrl() {
        return pictureAdapter.getNetWordThumbnailUrl();
    }


    /**
     * 处理结果
     */
    private void showDialogResult() {
        final String[] stateLabel = getActivity().getResources().getStringArray(R.array.processing_results);
        final int[] stateLabelId = getActivity().getResources().getIntArray(R.array.processing_results_id);
        new AlertView(null, null, "取消", null,
                stateLabel, getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(final Object o, final int position) {
                //如果是-1说明点击的取消,点击取消时，不做任何事
                if (position == -1) {
                    return;
                }
                hangupReason = -1;//如果重新选择了状态需要吧之前的挂起原因id清空
                faultRecordId = stateLabelId[position];
                processingResultTv.setText(stateLabel[position]);
                if (faultRecordId == 2) {
                    setCloseReasonOrHangUp("挂起", faultRecordId);
                } else if (faultRecordId == 3) {
                    setCloseReasonOrHangUp("关闭", faultRecordId);
                }
            }
        }).show();
    }

    /**
     * @param type 区分 是挂起 还是关闭   2挂起  ，3关闭
     * @param id   返回 id
     */
    @Override
    public void setSelectType(int type, int id) {
        if (type == 2) {
            faultCloseReason = -1;
            hangupReason = id;
        } else if (type == 3) {
            hangupReason = -1;
            faultCloseReason = id;
        }
    }


    private String getFaultId() {
        return getActivity().getIntent().getStringExtra("faultId");
    }


    private void getFaultArray() {
        String arry = getActivity().getIntent().getStringExtra("faultArray");
        if (!TextUtils.isEmpty(arry)) {
            try {
                JSONArray array = new JSONArray(arry);
                if (array.length() > 0) {
                    JSONObject object = array.getJSONObject(0);
                    faultRecordId = object.optInt("processingResult");
                    processingResultTv.setText(object.optString("processingResultName"));
                    if (object.optString("faultReason") != null && !"null".equals(object.optString("faultReason"))) {
                        reasonEdt.setText(object.optString("faultReason"));
                    }
                    hangupReason = object.optInt("faultHangupRenson") > 0 ? object.optInt("faultHangupRenson") : -1;
                    remarksEt.setText(object.optString("remark"));
                    if (!object.isNull("finishTime")) {
                        compleTime = object.optString("finishTime");
                        timeTv.setText(object.optString("finishTime"));
                    }
                    if (!object.isNull("pictureVideos")) {
                        getImgUrl(object.optString("pictureVideos"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getImgUrl(String imgUrl) {
        if (TextUtils.isEmpty(imgUrl)) {
            return;
        }
        Type type = new TypeToken<List<PictureEntity>>() {
        }.getType();
        List<PictureEntity> list = new Gson().fromJson(imgUrl, type);
        pictureAdapter.addAll(list);
        pictureAdapter.notifyDataSetChanged();
    }


    private void addItem(final String path) {
        PictureEntity entity = new PictureEntity();
        if (StringUtils.isPicture(path)) {
            entity.setLocalImgPath(path);
            entity.setType(0);
            pictureAdapter.addItem(entity);
        } else if (path.contains(".mp4")) {
            entity.setNetWordVideoPath(path);
            entity.setType(1);
            pictureAdapter.addItem(entity);
        }

    }

    @Override
    public void onSpeechResult(String result) {
        String oldResult = remarksEt.getText().toString();
        remarksEt.setText(oldResult + result);
        remarksEt.setSelection(remarksEt.getText().length());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        charLengthTv.setText(remarksEt.getText().length() + "/200");
    }

    private String getStartTime() {
        return getActivity().getIntent().getStringExtra("startTime");
    }


    private boolean compareTime(Date compleDate) {


        Date startData = TimeUtil.getDateByStr(TimeTransformUtil.getShowLocalTime(getStartTime()));
        TimeUtil.getTranData(getStartTime());
        if (startData.before(compleDate)) {
            //开始时间在完成时间之前
            return true;
        }
        return false;
    }
}
