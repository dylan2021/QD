package com.haocang.waterlink.experiment;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.haocang.base.adapter.PictureAdapter;
import com.haocang.base.bean.FileEntity;
import com.haocang.base.bean.PictureInfo;
import com.haocang.base.config.LibConfig;
import com.haocang.base.utils.PermissionsProcessingUtil;
import com.haocang.base.utils.ToastUtil;
import com.haocang.base.utils.UploadUtil;
import com.haocang.base.widgets.MyGridLayoutManager;
import com.haocang.patrol.patrolinhouse.bean.PatrolPictureEntity;
import com.haocang.waterlink.R;
import com.haocang.waterlink.experiment.adapter.HomeExperimentDetailAdapter;
import com.haocang.waterlink.experiment.bean.ExperimentDetailBean;
import com.haocang.waterlink.experiment.presenter.ExperimentDetailPresenter;
import com.haocang.waterlink.experiment.presenter.ExperimentDetailPresenterImpl;
import com.haocang.waterlink.utils.ImageUtil;
import com.haocang.waterlink.utils.TextUtilsMy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import camera2library.camera.Camera2RecordActivity;

@Route(path = "/experiment/experiment/detail")
public class ExperimentDetailFragment extends Fragment implements
        View.OnClickListener {

    private int formId;
    private String cycleId;
    private TextView title;
    private RecyclerView equimentRv;
    private HomeExperimentDetailAdapter adapter;
    private ExperimentDetailPresenter presenter;
    private Map<String, Object> mMap;
    private TextView time;

    private ExperimentDetailBean experimentDetailBean;
    private View footView;
    private PictureAdapter pictureAdapter;
    private RecyclerView recyclerview;
    private TextView rightTextView;
    private FragmentActivity context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        formId = context.getIntent().getExtras().getInt("formId");
        cycleId = context.getIntent().getExtras().getString("cycleId");
        View view = inflater.from(context).inflate(R.layout.fragment_experiment_detail, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        experimentDetailBean = new ExperimentDetailBean();

        List<ExperimentDetailBean.MpointsBean> mpointsBeans = new ArrayList<>();
        experimentDetailBean.setMpoints(mpointsBeans);

        title = view.findViewById(R.id.title_common_tv);
        title.setText("");

        time = view.findViewById(R.id.time);
        time.setOnClickListener(this);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        time.setText(format.format(new Date()));

        equimentRv = view.findViewById(R.id.recyclerview);
        rightTextView = view.findViewById(R.id.common_tv);
        rightTextView.setText("提交");
        rightTextView.setVisibility(View.VISIBLE);
        rightTextView.setOnClickListener(this);
        adapter = new HomeExperimentDetailAdapter(R.layout.adapter_home_experiment_detail);
        equimentRv.setItemViewCacheSize(100);

        equimentRv.setLayoutManager(new LinearLayoutManager(context));
        footView = View.inflate(context, R.layout.experiment_foot, null);
        adapter.setFootView(footView);
        footView.findViewById(R.id.add_pic_iv).setOnClickListener(this);
        recyclerview = footView.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new MyGridLayoutManager(context, 3));
        pictureAdapter = new PictureAdapter(context);
        recyclerview.setAdapter(pictureAdapter);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);


        //请求数据
        presenter = new ExperimentDetailPresenterImpl();
        presenter.setView(this);
        mMap = new HashMap<>();
        mMap.put("formId", formId);
        mMap.put("cycleId", cycleId);
        mMap.put("recordDate", format.format(calendar.getTime()) + "T16:00:00.000Z");

        presenter.getDataList(mMap);
    }

    public void showPickTimeView() {
        Calendar c = Calendar.getInstance();
        String[] split = mMap.get("recordDate").toString().split("T");
        try {
            c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(split[0]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        TimePickerView pvTime
                = new TimePickerBuilder(getContext(),
                new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(final Date date, final View v) {
                        experimentDetailBean = new ExperimentDetailBean();
                        List<ExperimentDetailBean.MpointsBean> mpointsBeans = new ArrayList<>();
                        experimentDetailBean.setMpoints(mpointsBeans);
                        time.setText(new SimpleDateFormat("yyyy-MM-dd").format(date));
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                        mMap.put("recordDate", new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()) + "T16:00:00.000Z");
                        presenter.getDataList(mMap);
                    }
                }).isDialog(true).build();
        pvTime.setDate(c);
        pvTime.show();

    }

    public void setData(String entity) {
//        Map<String,String> mMap = new HashMap<>();
//        mMap.put("time","time");
        try {
            JSONObject mObj = new JSONObject(entity);
            Log.d("图片数据", "图片数据" + mObj.toString());
            if (mObj.getJSONObject("record").has("formId")) {
                experimentDetailBean.setFormId(mObj.getJSONObject("record").getInt("formId"));
            } else {
                experimentDetailBean.setFormId(mObj.getJSONObject("record").getInt("id"));
            }
            title.setText(mObj.getJSONObject("record").getString("formName"));
            experimentDetailBean.setRecordDate(mMap.get("recordDate").toString());
//            experimentDetailBean.setThumbnailUrl(t);
//            experimentDetailBean.setUrl(t);

            JSONObject groups = mObj.getJSONObject("items").getJSONObject("groups");
            Iterator<String> keys = groups.keys();
            while (keys.hasNext()) {
                JSONArray arr = groups.getJSONArray(keys.next());
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    String mpointName = obj.getString("mpointName");
                    JSONArray datas = obj.getJSONArray("datas");
                    for (int j = 0; j < datas.length(); j++) {
                        String time = datas.getJSONObject(j).getString("time");
                        String value = datas.getJSONObject(j).getString("value");
                        String status = datas.getJSONObject(j).getString("value");
                        int mpointId = obj.getInt("mpointId");
                        if (value.equals("")) {
                            status = "DELETE";
                        }
                        ExperimentDetailBean.MpointsBean mpointsBean = new ExperimentDetailBean.MpointsBean();
                        mpointsBean.setFalseTime(time);
                        mpointsBean.setTime(time);
                        mpointsBean.setValue(value);
                        mpointsBean.setMpointId(mpointId);
                        mpointsBean.setStatus(status);
                        mpointsBean.mpointName = mpointName;
                        experimentDetailBean.getMpoints().add(mpointsBean);
                        mpointName = null;
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.clear();
        adapter.notifyDataSetChanged();
        adapter.addAll(experimentDetailBean.getMpoints());
        adapter.notifyDataSetChanged();
        equimentRv.setAdapter(adapter);
        Log.e("adapter", adapter.mList.size() + "");
        Log.e("experimentDetailBean", experimentDetailBean.toString());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.time) {
            showPickTimeView();
        } else if (v.getId() == R.id.add_pic_iv) {
            if (pictureAdapter.getItemCount() < 4) {
                showMulti();
            } else {
                ToastUtil.makeText(context, "最多添加4张照片");
            }
            // hideNameInputMethod();
            // hidePhoneInputMethod();
        } else if (v.getId() == R.id.common_tv) {//提交
            List<String> fileList = pictureAdapter.getFileList();
            if (TextUtilsMy.isEmptyList(fileList)) {
                com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.
                        parseObject(com.alibaba.fastjson.JSONObject.toJSON(experimentDetailBean).toString());
                //没有图片,直接上传数据
                presenter.submit(jsonObject.toJSONString());
            } else {
                //有图片,先上传图片
                upLoadFile();
            }
        }
    }

    //上传文件
    private void upLoadFile() {
        List<String> fileList = pictureAdapter.getFileList();
        new UploadUtil(context).setUploadData(fileList).setUploadSuccess(new UploadUtil.UploadSuccess() {
            @Override
            public void uploadSuccess(List<FileEntity> fileList) {
                Log.e("fileEntity", fileList.toString());
                String thumbnailUrl = "";
                String url = "";
                for (int i = 0; i < fileList.size(); i++) {
                    thumbnailUrl = thumbnailUrl + fileList.get(i).getThumbFullPath() + ",";
                    url = url + fileList.get(i).getFullPath() + ",";
                }

                if (thumbnailUrl.length() > 0) {
                    thumbnailUrl = thumbnailUrl.substring(0, thumbnailUrl.length() - 1);
                }

                if (url.length() > 0) {
                    url = url.substring(0, url.length() - 1);
                }

                experimentDetailBean.setUrl(url);
                experimentDetailBean.setThumbnailUrl(thumbnailUrl);

                com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(
                        com.alibaba.fastjson.JSONObject.toJSON(experimentDetailBean).toString());
                presenter.submit(jsonObject.toJSONString());
            }

            @Override
            public void uploadError() {

            }
        }).startUploadFileEX();
    }


    //    private void hideNameInputMethod() {
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(nameEdt.getWindowToken(), 0); //强制隐藏键盘
//    }
//
//    private void hidePhoneInputMethod() {
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(phoneEdt.getWindowToken(), 0); //强制隐藏键盘
//    }
    private void showMulti() {
        final String[] stateLabel = context.getResources().
                getStringArray(R.array.multi_media);
        new AlertView(null, null, "取消", null,
                stateLabel, context, AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(final Object o, final int position) {
                //-1==取消,不做任何事
                if (position == -1) {
                    return;
                } else if (position == 0) {//相机
                    PermissionsProcessingUtil.setPermissions(
                            ExperimentDetailFragment.this, LibConfig.CAMERA, cameraCallBack);
                } else {//从相册选择
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    context.startActivityForResult(intent, 1004);
                }
            }
        }).show();
    }

    private PermissionsProcessingUtil.OnPermissionsCallback cameraCallBack = new PermissionsProcessingUtil.OnPermissionsCallback() {
        @Override
        public void callBack(boolean flag, String permission) {
            if (flag) {
                Camera2RecordActivity.start(context);
            } else {
                ToastUtil.makeText(context, getString(R.string.permissions_camera));
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Camera2RecordActivity.RESULTCODE == requestCode && data != null) {
            //添加照片或者视频
            String videoPath = data.getStringExtra("videoPath");
            String picturePath = data.getStringExtra("picturePath");
            addLocPicture(picturePath);//添加本地图片
            addItemVideo(videoPath);//添加本地视频
        } else if (requestCode == 1004 && data != null) {
            handleImageOfKitKat(data);
        }
    }

    private void handleImageOfKitKat(Intent data) {
        String imageUrl = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
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
        addLocPicture(imageUrl);
    }

    private String getImagePath(Uri externalContentUri, String selection) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(externalContentUri, null, selection, null, null, null);
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
     * 添加照片
     *
     * @param picturePath
     */
    private void addLocPicture(String picturePath) {
        if (!TextUtils.isEmpty(picturePath)) {
            PictureInfo picInfo = new PictureInfo();
            File file = new File(picturePath);
//            File newFile = CompressHelper.getDefault(context).compressToFile(file);//压缩图片
            picInfo.setType(0);
            picInfo.setLocalImgPath(file.getPath());
            picInfo.setLocalImgPath(picturePath);
            pictureAdapter.addItem(picInfo);
            pictureAdapter.notifyDataSetChanged();
        }
    }

    //todo Dylan 请求后台数据后,拿出图片/视频路径集合, 遍历,这里添加图片或者视频,
     /*   if (mList.get(position).getStepImgList() != null && mList.get(position).getStepImgList().size() > 0) {
            List<PatrolPictureEntity> picList = mList.get(position).getStepImgList();*/
    private void addImgPic(List<PatrolPictureEntity> picList ) {

        for (PatrolPictureEntity entity : picList) {
            String imgUrl = entity.getImgUrl();
            if (imgUrl != null && (ImageUtil.isImageSuffix(imgUrl))) {
                PictureInfo pictureInfo = new PictureInfo();
                pictureInfo.setType(0);
                pictureInfo.setImgUrl(imgUrl);
                pictureAdapter.addItem(pictureInfo);
            } else {
                PictureInfo pictureInfo = new PictureInfo();
                pictureInfo.setType(1);
                pictureInfo.setNetWordVideoPath(imgUrl);
                pictureAdapter.addItem(pictureInfo);
            }
            pictureAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 添加视频
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

    public void submitSuccess() {
        ToastUtil.makeText(context, "录入成功");
        context.finish();
    }
}
