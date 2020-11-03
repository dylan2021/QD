package com.haocang.waterlink.self.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haocang.waterlink.R;
import com.haocang.waterlink.self.iview.EditHeadView;
import com.haocang.waterlink.self.presenter.EditHeadPresenter;
import com.haocang.waterlink.self.presenter.impl.EditHeadPresenterImpl;

import java.io.File;

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
 * 创建时间：2018/1/2416:36
 * 修 改 者：
 * 修改时间：
 */
public class EditHeadFragment extends Fragment
        implements View.OnClickListener, EditHeadView {

    /**
     * 逻辑层.
     */
    private EditHeadPresenter presenter;

    /**
     * 头像imageview.
     */
    private ImageView headIv;
    /**
     * title名称.
     */
    private TextView titleNameTv;


    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_edit_head, null);
        initView(view);
        return view;
    }

    /**
     * 初始化控件.
     *
     * @param view 父布局
     */
    private void initView(final View view) {
        headIv = view.findViewById(R.id.head_iv);
        titleNameTv = view.findViewById(R.id.title_common_tv);
        titleNameTv.setText(getResources().getString(R.string.editors_head));
        presenter = new EditHeadPresenterImpl(this);
        view.findViewById(R.id.more_v).setOnClickListener(this);

    }


    /**
     * 所有的点击事件逻辑再此处理.
     *
     * @param view
     */
    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.more_v) {
//                PermissionsProcessingUtil.appPermissions(this, LibConfig.CAMERA);
//                RxDialogChooseImage dialogChooseImage =
//                        new RxDialogChooseImage(this, TITLE);
//                dialogChooseImage.show();
        }

    }

    @Override
    public void setResult(final String result) {

    }

    @Override
    public void setFile(final File file) {
    }

    /**
     * @param uri 图片路径的uri.
     */
    @Override
    public void setUri(final Uri uri) {
        headIv.setImageURI(uri);
    }

    /**
     * @return 获取页面的Fragment, 用于onActivityResult接收.
     */
    @Override
    public Fragment getFragment() {
        return this;
    }

    /**
     * 打开相机或者相册后 的结果 回掉.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
