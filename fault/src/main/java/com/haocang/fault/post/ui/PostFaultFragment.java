package com.haocang.fault.post.ui;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.haocang.base.utils.ARouterUtil;
import com.haocang.base.utils.AnalysisQRCodeUtil;
import com.haocang.base.utils.BDSendTraceUtil;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.ScanCodeUtils;
import com.haocang.base.utils.SpeechService;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.widgets.MyGridLayoutManager;
import com.haocang.base.zxing.app.CaptureActivity;
import com.haocang.fault.R;
import com.haocang.fault.constants.FaultConstants;
import com.haocang.fault.post.iview.PostView;
import com.haocang.fault.post.presenter.PostPresenter;
import com.haocang.fault.post.presenter.impl.PostPresenterImpl;
import com.haocang.fault.post.ui.utils.FaultPostRemarksFragment;
import com.haocang.fault.post.ui.utils.PostEquipmentFragment;
import com.haocang.fault.post.ui.utils.PostFaultSeverityFragment;
import com.haocang.fault.post.ui.utils.PostFaultTypeFragment;
import com.haocang.fault.post.ui.utils.ProcessPositionFragment;
import com.haocang.offline.util.OffLineOutApiUtil;
import com.nanchen.compresshelper.CompressHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import camera2library.camera.Camera2RecordActivity;
import me.iwf.photopicker.utils.FileUtils;


/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001
 * 网址：http://www.haocang.com/
 * 标        题：缺陷申报
 * 部        门：产品研发部
 * 版        本： 1.0
 * 创  建  者：whhc
 * 创建时间：2018/4/14下午4:14
 * 修  改  者：
 * <p>
 * <p>
 * 修改时间：
 */
@Route(path = FaultConstants.ArouterPath.FAULT_POST)
public class PostFaultFragment extends Fragment implements View.OnClickListener, PermissionsProcessingUtil.OnPermissionsCallback, PostView {
    private boolean isQECode;
    private RecyclerView recyclerview;
    private PictureAdapter pictureAdapter;

    public static final int REQUESTCODE = 12301;

    private PostPresenter presenter;

    private Map<String, Object> parameterMap = new HashMap<>();//参数

    private TextView faultTypeTv;

    private TextView faultEquipmentTv;//相关设备

    private TextView faultSeverityTv;//严重程度

    private TextView faultProcessingTv;//处理人;

    private TextView titleNameTv;//title名称

    private EditText remarkEt;//备注

    private TextView charLenthTv;
    /**
     * 工艺位置
     */
    private TextView processTv; //


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fault_post_fragment, null);
        initView(view);
        return view;
    }

    private void initView(final View view) {
        presenter = new PostPresenterImpl(this);
        recyclerview = view.findViewById(R.id.recyclerview);
        pictureAdapter = new PictureAdapter(getActivity());
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.fault_post));
        remarkEt = view.findViewById(R.id.remark_et);
        recyclerview.setLayoutManager(new MyGridLayoutManager(getActivity(), 3));
        recyclerview.setAdapter(pictureAdapter);
        charLenthTv = view.findViewById(R.id.char_lenth_tv);
        if (!TextUtils.isEmpty(getPatrolId())) {
            parameterMap.put("patrolId", Integer.parseInt(getPatrolId()));
        }
        if (!TextUtils.isEmpty(getMaintainId())) {
            parameterMap.put("maintainId", getMaintainId());
        }
        view.findViewById(R.id.scan_code_iv).setOnClickListener(this);
        view.findViewById(R.id.add_pic_iv).setOnClickListener(this);
        view.findViewById(R.id.processing_ll).setOnClickListener(this);
        view.findViewById(R.id.fault_type_ll).setOnClickListener(this);
        view.findViewById(R.id.fault_equipment_ll).setOnClickListener(this);
        view.findViewById(R.id.voicer_view).setOnClickListener(this);
        view.findViewById(R.id.fault_severity_ll).setOnClickListener(this);
        view.findViewById(R.id.fault_process_ll).setOnClickListener(this);
        view.findViewById(R.id.submit_tv).setOnClickListener(this);
        faultTypeTv = view.findViewById(R.id.fault_type);
        faultEquipmentTv = view.findViewById(R.id.fault_equipment_tv);
        faultSeverityTv = view.findViewById(R.id.fault_level_tv);
        faultProcessingTv = view.findViewById(R.id.fault_processing_tv);
        processTv = view.findViewById(R.id.fault_process_tv);
        getEquipment(getActivity().getIntent());
        remarkEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                charLenthTv.setText(s.length() + "/200");
            }
        });
    }

    public String getPatrolId() {
        return getActivity().getIntent().getStringExtra("patrolId");
    }

    @Override
    public void onResume() {
        super.onResume();
        setOrgId();
    }

    @Override
    public void onClick(final View v) {
        int id = v.getId();
        if (id == R.id.voicer_view) {
            if (OffLineOutApiUtil.isNetWork()) {
                ToastUtil.makeText(getActivity(), "暂无网络无法使用语音功能");
                return;
            }
            PermissionsProcessingUtil.setPermissions(this, LibConfig.AUDIO, new PermissionsProcessingUtil.OnPermissionsCallback() {
                @Override
                public void callBack(boolean b, String result) {
                    if (b) {
                        SpeechService.btnVoice(getActivity(), new SpeechService.OnSpeechResult() {
                            @Override
                            public void onSpeechResult(String s) {
                                String oldResult = remarkEt.getText().toString();
                                remarkEt.setText(oldResult + s);
                            }
                        });
                    } else {
                        ToastUtil.makeText(getActivity(), getResources().getString(R.string.permissions_audio));
                    }
                }
            });
        } else if (id == R.id.scan_code_iv) {
            PermissionsProcessingUtil.setPermissions(this, LibConfig.CAMERA, this);
        } else if (id == R.id.add_pic_iv) {
            if (pictureAdapter.getItemCount() < 6) {
                showMulti();
            } else {
                ToastUtil.makeText(getActivity(), "最多添加6张照片");
            }
        } else if (id == R.id.processing_ll) {
            onClickProcessing();
        } else if (id == R.id.fault_type_ll) {
            onClickFaultType();
        } else if (id == R.id.fault_equipment_ll) {
            onClickEquipment();
        } else if (id == R.id.fault_severity_ll) {
            onClickSeverity();
        } else if (id == R.id.submit_tv) {
            parameterMap.put("remark", remarkEt.getText().toString().trim());
            presenter.submit();
        } else if (id == R.id.fault_process_ll) {
            Map<String, Object> map = new HashMap<>();
            map.put("fragmentUri", LibConfig.AROUTE_PROCESS_POSITION);
            ARouterUtil.startActivityForResult(map, getActivity(), ProcessPositionFragment.REQUEST);
        }

    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void callBack(boolean flag, String permission) {
        if (flag) {
            ScanCodeUtils.openScanCode(getActivity());
        } else {
            ToastUtil.makeText(getActivity(), getString(R.string.permissions_camera));
        }
    }


    private void showMulti() {
        final String[] stateLabel = getActivity().getResources().getStringArray(R.array.falut_multi_media);
        new AlertView(null, null, "取消", null,
                stateLabel, getActivity(), AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                //如果是-1说明点击的取消,点击取消时，不做任何事
                if (position == -1) {
                    return;
                } else if (position == 0) {
                    PermissionsProcessingUtil.setPermissions(PostFaultFragment.this, LibConfig.CAMERA, cameraCallBack);
                } else if (position == 1) {
                    openVideo();
                } else {
                    openAlbum();
                }
            }
        }).show();
    }

    private PermissionsProcessingUtil.OnPermissionsCallback cameraCallBack = new PermissionsProcessingUtil.OnPermissionsCallback() {
        @Override
        public void callBack(boolean flag, String permission) {
            if (flag) {
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra("fragmentName", CameraFragment.class.getName());
                getActivity().startActivityForResult(intent, 1122);
            } else {
                ToastUtil.makeText(getActivity(), getString(R.string.permissions_camera));
            }
        }
    };

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

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == 1004 && data != null) {
            handleImageOfKitKat(data);
        } else if (Camera2RecordActivity.RESULTCODE == requestCode && data != null) {
            //添加照片或者视频
            String videoPath = data.getStringExtra("videoPath");
            String picturePath = data.getStringExtra("picturePath");
            addItemPicture(picturePath);
            addItemVideo(videoPath);

        } else if (PostFaultTypeFragment.FAULT_TYPE == requestCode && data != null) {
            //添加故障类型id
            getFaultType(data);
        } else if (PostFaultSeverityFragment.FAULT_SEVERITY == requestCode && data != null) {
            //严重等级
            getSeverity(data);
        } else if (PostEquipmentFragment.REQUESTCODE == requestCode && data != null) {
            //相关设备
            getEquipment(data);
        } else if (PostProcessingPersonFragment.REQUESTCODE == requestCode && data != null) {
            getProcessingPerson(data);
        } else if (ProcessPositionFragment.REQUEST == requestCode && data != null) {
            getProcess(data);
        } else if (requestCode == LibConfig.SCAN_CODE && data != null) {
            String result = data.getStringExtra(CaptureActivity.SCAN_RESULT);
            int id = AnalysisQRCodeUtil.getEquipmentId(result);
            if (id > 0) {
                presenter.getEquipmentData(id + "");
            } else {
                ToastUtil.makeText(getActivity(), "请扫描正确的二维码");
            }
        } else if (resultCode == 1039 && data != null) {
            String video = data.getStringExtra("videoUrl");
            addItemVideo(video);
        } else if (resultCode == 1122 && data != null) {
            String pic = data.getStringExtra("picturePath");
//            Bitmap bitmap = ImageUtil.fileInputStream(pic);
            Bitmap bitmap = BitmapFactory.decodeFile(pic);
            if (bitmap != null) {
                Intent intent = new Intent();
                intent.putExtra("person", AppApplication.getInstance().getUserEntity().getName());
                if (BDSendTraceUtil.getInstance().getLocation() != null) {
                    intent.putExtra("address", BDSendTraceUtil.getInstance().getLocation().getAddrStr());
                } else {
                    intent.putExtra("address", "");
                }
                intent.putExtra("processName", processTv.getText().toString());
                String sr = "";
                if (!TextUtils.isEmpty(faultTypeTv.getText().toString())) {
                    sr = faultTypeTv.getText().toString();
                }
                if (!TextUtils.isEmpty(faultEquipmentTv.getText().toString())) {
                    sr += " " + faultEquipmentTv.getText().toString();
                }
                intent.putExtra("equName", sr);
                if (!TextUtils.isEmpty(LibConfig.weather)) {
                    intent.putExtra("weather", LibConfig.weather + " " + LibConfig.temperature);
                } else {
                    intent.putExtra("weather", "");
                }
                Bitmap map = ImageUtil.createWaterMaskLeftBottom(getActivity(), bitmap, Constants.loadBitmapFromView(new WaterMark(getActivity(), intent, R.layout.fault_mark_view).getWaterMarkView()), 20, 0);
                File file = FileUtils.saveBitmapFile(map);
                addItemPicture(file.getPath());
            }
        }

    }

    /**
     * 获取工艺位置
     */
    private void getProcess(final Intent data) {
        String processName = data.getStringExtra("processName");
        String processId = data.getStringExtra("processId");
        processTv.setText(processName);
        parameterMap.put("processId", processId);
    }

    /**
     * 故障类型.
     *
     * @param data
     */
    private void getFaultType(final Intent data) {
        faultTypeTv.setText(getName(data));
        parameterMap.put("faultTypeId", getIds(data));
    }

    /**
     * 获取严重等级.
     *
     * @param data
     */
    private void getSeverity(final Intent data) {
        parameterMap.put("severityType", getIds(data));
        faultSeverityTv.setText(getName(data));
    }

    /**
     * 获取相关设备
     */
    private void getEquipment(Intent data) {
        faultEquipmentTv.setText(getName(data));
        parameterMap.put("equId", getIds(data));
        if (!TextUtils.isEmpty(data.getStringExtra("processId"))) {
            parameterMap.put("processId", Integer.parseInt(data.getStringExtra("processId")));
            processTv.setText(data.getStringExtra("processName"));
        }
    }

    /**
     * 获取处理人
     */
    private void getProcessingPerson(Intent data) {
        parameterMap.put("processingPersonId", getIds(data));
        String orgName = data.getStringExtra("orgName");
        faultProcessingTv.setText(orgName + "   " + getName(data));
    }

    /**
     * 获取id
     *
     * @param data
     * @return
     */
    private int getIds(Intent data) {
        if (!TextUtils.isEmpty(data.getStringExtra("id"))) {
            return Integer.parseInt(data.getStringExtra("id"));
        } else {
            return -1;
        }
    }

    /**
     * 获取名称
     *
     * @param data
     * @return
     */
    private String getName(final Intent data) {
        if (!TextUtils.isEmpty(data.getStringExtra("name"))) {
            return data.getStringExtra("name");
        } else {
            return "";
        }
    }

    /**
     * 添加照片
     *
     * @param picturePath
     */
    private void addItemPicture(String picturePath) {
        if (!TextUtils.isEmpty(picturePath)) {
            PictureInfo entity = new PictureInfo();
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
            PictureInfo entity = new PictureInfo();
            entity.setType(1);
            entity.setVideoPath(videoPath);
            pictureAdapter.addItem(entity);
            pictureAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public Context getContexts() {
        return getActivity();
    }

    @Override
    public Map<String, Object> getParameter() {
        return parameterMap;
    }

    @Override
    public List<String> getfileList() {
        return pictureAdapter.getFileList();
    }

    /**
     * 巡检提交缺陷回传.
     */
    private static final int FROM_PATROL_RESULT_CODE = 9002;

    /**
     * @param id
     */
    @Override
    public void createSuccess(final String id) {
        String patrolId = getPatrolId();
        if (!TextUtils.isEmpty(patrolId)) {
            Intent intent = new Intent();
            intent.putExtra("faultId", id);
            getActivity().setResult(FROM_PATROL_RESULT_CODE, intent);
            getActivity().finish();
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("faultId", id);
            map.put("fragmentUri", LibConfig.AROUTE_FAULT_RESULT);
            ARouterUtil.toFragment(map);
            clearView();
            getActivity().finish();
        }
    }

    @Override
    public void setEquipmentId(final int id) {
        parameterMap.put("equId", id);
    }

    @Override
    public void setEquipmentName(String name) {
        faultEquipmentTv.setText(name);
    }

    @Override
    public void setProcessName(String name) {
        processTv.setText(name);
    }

    @Override
    public void setProcessId(int id) {
        parameterMap.put("processId", id);

    }

    @Override
    public void setOffLineSuccess() {
        ToastUtil.makeText(getActivity(), "已为您缓存到本地");
        String patrolId = getPatrolId();
        if (!TextUtils.isEmpty(patrolId)) {
            Intent intent = new Intent();
            intent.putExtra("faultId", "1");
            getActivity().setResult(FROM_PATROL_RESULT_CODE, intent);
            getActivity().finish();
        } else if (OffLineOutApiUtil.isOffLine()) {
            getActivity().finish();
        } else {
            clearView();
        }

    }


    public void setOrgId() {
        parameterMap.put("orgId", AppApplication.getInstance().getUserEntity().getOrgId());
    }

    private void clearView() {
        parameterMap.clear();
        pictureAdapter.clear();
        faultTypeTv.setText(null);
        faultEquipmentTv.setText(null);
        faultSeverityTv.setText(null);
        processTv.setText(null);
        faultProcessingTv.setText(null);
    }


    private String getMaintainId() {
        return getActivity().getIntent().getStringExtra("maintainId");
    }

    /**
     * 处理人.
     */
    private void onClickProcessing() {
        Map<String, Object> map = new HashMap<>();
        map.put("fragmentUri", LibConfig.AROUTE_FAULT_POST_PROCESSING);
        map.put("orgId", (int) AppApplication.getInstance().getUserEntity().getOrgId());
        ARouterUtil.startActivityForResult(map, getActivity(), PostProcessingPersonFragment.REQUESTCODE);
    }

    /**
     * 添加备注
     */
    private void onClickRemark() {
        Map<String, Object> map = new HashMap<>();
        map.put("fragmentUri", LibConfig.AROUTE_FAULT_POST_REMARKS);
        map.put("remark", parameterMap.get("remark") != null ? parameterMap.get("remark") : "");
        ARouterUtil.startActivityForResult(map, getActivity(), FaultPostRemarksFragment.REQUEST);
    }

    /**
     * 缺陷类型.
     */
    private void onClickFaultType() {
        Map<String, Object> map = new HashMap<>();
        map.put("fragmentUri", LibConfig.AROUTE_FAULT_TYPE);
        map.put("type", PostFaultTypeFragment.FAULT_TYPE);
        ARouterUtil.startActivityForResult(map, getActivity(), PostFaultTypeFragment.FAULT_TYPE);
    }

    /**
     * 点击相关设备
     */
    private void onClickEquipment() {
        Map<String, Object> map = new HashMap<>();
        map.put("fragmentUri", LibConfig.AROUTE_FAULT_EQUIPMENT);
        ARouterUtil.startActivityForResult(map, getActivity(), PostEquipmentFragment.REQUESTCODE);
    }

    /**
     * 点击严重程度.
     */
    private void onClickSeverity() {
        Map<String, Object> map = new HashMap<>();
        map.put("fragmentUri", LibConfig.AROUTE_FAULT_SEVERITY);
        ARouterUtil.startActivityForResult(map, getActivity(), PostFaultSeverityFragment.FAULT_SEVERITY);
    }

    private void openVideo() {
        Intent intent = new Intent(getActivity(), RecordedActivity.class);
        intent.putExtra("person", AppApplication.getInstance().getUserEntity().getName());
        if (BDSendTraceUtil.getInstance().getLocation() != null) {
            intent.putExtra("address", BDSendTraceUtil.getInstance().getLocation().getAddrStr());
        } else {
            intent.putExtra("address", "");
        }
        intent.putExtra("processName", processTv.getText().toString());
        String sr = "";
        if (!TextUtils.isEmpty(faultTypeTv.getText().toString())) {
            sr = faultTypeTv.getText().toString();
        }
        if (!TextUtils.isEmpty(faultEquipmentTv.getText().toString())) {
            sr += " " + faultEquipmentTv.getText().toString();
        }
        intent.putExtra("equName", sr);
        if (!TextUtils.isEmpty(LibConfig.weather)) {
            intent.putExtra("weather", LibConfig.weather + " " + LibConfig.temperature);
        } else {
            intent.putExtra("weather", "");
        }
        startActivityForResult(intent, 1039);
    }
}
