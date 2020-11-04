package com.haocang.fault.list.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.adapter.PictureAdapter;
import com.haocang.base.bean.PictureInfo;
import com.haocang.base.config.LibConfig;
import com.haocang.base.utils.StringUtils;
import com.haocang.base.widgets.MyGridLayoutManager;
import com.haocang.fault.R;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.
 * 版权所有(C)2015-2020
 * 公司名称：上海昊沧系统控制技术有限责任公司
 * 产品规格书 Version 0.01
 * Document ID: SEP002 内部使用 Page 14/ 49
 * 公司地址：中国上海市徐汇区云锦路 500 号绿地汇中心 A 座 20 楼 2001
 * 网址：http://www.haocang.com/
 * 标 题：缺陷现场记录
 * 部 门：产品研发部
 * 版 本： 1.0
 * 创 建 者：he
 * 创建时间：2018/4/2617:39
 * 修 改 者：
 * 修改时间：
 */
@Route(path = LibConfig.AROUTE_FAULT_POST_SCENE)
public class FaultSceneFragment extends Fragment {

    private TextView titleNameTv;

    private TextView remarkTv;

    private PictureAdapter pictureAdapter;
    private RecyclerView recyclerview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_fault_scene, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getString(R.string.falut_new_scene));
        remarkTv = view.findViewById(R.id.fault_scene_tv);
        if (!TextUtils.isEmpty(getRemark())) {
            remarkTv.setText(getRemark());
        }
        recyclerview = view.findViewById(R.id.recyclerview);
        pictureAdapter = new PictureAdapter(getActivity());
        recyclerview.setLayoutManager(new MyGridLayoutManager(getActivity(), 3));
        recyclerview.setAdapter(pictureAdapter);
        pictureAdapter.setDisplay();
        getImgUrl();
    }

    private String getRemark() {
        return getActivity().getIntent().getStringExtra("remark");
    }

    private void getImgUrl() {
        final String imgUrl = getActivity().getIntent().getStringExtra("imgUrl");
        if (TextUtils.isEmpty(imgUrl)) {
            return;
        }
        Type type = new TypeToken<List<PictureInfo>>() {
        }.getType();
        List<PictureInfo> list = new Gson().fromJson(imgUrl, type);
        pictureAdapter.addAll(list);
        pictureAdapter.notifyDataSetChanged();
    }


    private void addItem(String path) {
        PictureInfo entity = new PictureInfo();
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
