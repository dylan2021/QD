package com.haocang.base.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haocang.base.R;
import com.haocang.base.adapter.PictureNewPreviewAdapter;
import com.haocang.base.bean.PictureEntity;
import com.haocang.base.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
 * 创建时间：2018/10/15 15:49
 * 修 改 者：
 * 修改时间：
 */
public class PictureNewPreviewFragment extends Fragment {
    private ViewPager myViewPager;
    private PictureNewPreviewAdapter adapter;
    private ImageView deleteIv;
    private int position = 0;
    private TextView titleNameTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_new_picture_preview, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText("");
        position = getPositions();
        deleteIv = view.findViewById(R.id.common_iv);
        myViewPager = view.findViewById(R.id.viewpager);
        adapter = new PictureNewPreviewAdapter(getActivity());
        myViewPager.setAdapter(adapter);
        adapter.addAll(getPictureUrl());
        myViewPager.setCurrentItem(position);
        adapter.notifyDataSetChanged();
        if (!TextUtils.isEmpty(getDisplayDelete())) {
            deleteIv.setVisibility(View.GONE);
            deleteIv.setBackgroundResource(R.drawable.ic_delete_s);
            deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PictureEntity entity = adapter.fileList.get(position);
                    if (entity.getFileType() == PictureEntity.LOCAL_VIDEO) {
                        deleteFile(entity.getVideoPath());
                    } else if (entity.getFileType() == PictureEntity.LOCAL_IMAGE) {
                        deleteFile(entity.getLocalImgPath());
                    }
                    adapter.removes(position);
                    if (position > 0) {
                        position--;
                    } else {
                        position = 0;
                    }
                    myViewPager.setCurrentItem(position);
                    if (adapter.getCount() == 0) {
                        getActivity().finish();
                    }

                }
            });
        }
        myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int positio, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int positio) {
                position = positio;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<PictureEntity> getPictureUrl() {
        String s = getActivity().getIntent().getStringExtra("url");
        List<PictureEntity> retList = new Gson().fromJson(s,
                new TypeToken<List<PictureEntity>>() {
                }.getType());
        List<PictureEntity> list = new ArrayList<>();
        list.add(retList.get(position));
        return list;
    }

    /**
     * 不为空的时候 删除按钮需要显示
     *
     * @return
     */
    private String getDisplayDelete() {
        return getActivity().getIntent().getStringExtra("displayDelete");
    }

    private void deleteFile(String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            File file = new File(fileName);
            // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
            if (file.exists() && file.isFile()) {
                if (file.delete()) {
                    System.out.println("删除单个文件" + fileName + "成功！");
                } else {
                    System.out.println("删除单个文件" + fileName + "失败！");
                }
            } else {
                System.out.println("删除单个文件失败：" + fileName + "不存在！");
            }
        } else {
            ToastUtil.makeText(getActivity(), "无法删除当前图片");
        }

    }

    private int getPositions() {
        return Integer.parseInt(getActivity().getIntent().getStringExtra("position"));
    }

}
