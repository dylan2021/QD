package com.haocang.repair.manage.ui;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.RequestBuilder;
import com.haocang.base.adapter.PictureAdapter;
import com.haocang.base.bean.PictureEntity;
import com.haocang.base.config.ArouterPathConstants;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.widgets.MyGridLayoutManager;
import com.haocang.repair.R;

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
 * 创建时间：2018/5/16下午6:20
 * 修  改  者：
 * 修改时间：
 */
@Route(path = ArouterPathConstants.Repair.REPAIR_FAULT_SENCES)
public class FaultSenceFragment extends Fragment {

    /**
     * 暂无现场记录图片.
     */
    private TextView faultNodateTv;
    /**
     *
     */
    private PictureAdapter pictureAdapter;
    /**
     * 列表视图.
     */
    private RecyclerView recyclerview;

    private TextView remarkTv;

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repair_fault_sence_fragment, null);
        initView(view);
        return view;
    }

    /**
     * @param view 根View.
     */
    private void initView(final View view) {
        TextView titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.repair_scene_record));
        remarkTv = view.findViewById(R.id.repair_remark_et);
        remarkTv.setMovementMethod(new ScrollingMovementMethod());
        pictureAdapter = new PictureAdapter(getActivity());
        recyclerview = view.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(
                new MyGridLayoutManager(getActivity(), 3));
        recyclerview.setAdapter(pictureAdapter);
        pictureAdapter.setDisplay();
        remarkTv.setText(getRemark());
        setImg();
    }

    /**
     * @return
     */
    public String getRemark() {
        return getActivity().getIntent().getStringExtra("remark");
    }

    @SuppressLint("StaticFieldLeak")
    public void setImg() {
        final String[] imgUrlArr =
                getActivity().getIntent().getStringArrayExtra("imgUrl");
        if (imgUrlArr != null) {
            new AsyncTask<Void, Void, String>() {
                private RequestBuilder<Drawable> builder = null;

                @Override
                protected String doInBackground(final Void... voids) {
                    for (String imgUrl : imgUrlArr) {
                        addItem(imgUrl);
                    }
                    return null;
                }

                protected void onPostExecute(final String s) {
                    pictureAdapter.notifyDataSetChanged();
                }
            }.execute();
        }
    }

    private void addItem(final String path) {
        PictureEntity entity = new PictureEntity();
        if (StringUtils.isPicture(path)) {
            entity.setLocalImgPath(path);
            entity.setType(0);
            pictureAdapter.addItemWithoutNotifyList(entity);
        } else if (path.contains(".mp4")) {
            entity.setNetWordVideoPath(path);
            entity.setType(1);
            pictureAdapter.addItemWithoutNotifyList(entity);
        }

    }

}
